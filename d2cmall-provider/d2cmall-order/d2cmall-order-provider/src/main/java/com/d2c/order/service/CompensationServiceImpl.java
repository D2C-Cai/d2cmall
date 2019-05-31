package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.CompensationLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.CompensationLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CompensationMapper;
import com.d2c.order.dto.CompensationDto;
import com.d2c.order.dto.CompensationSummaryDto;
import com.d2c.order.model.Compensation;
import com.d2c.order.model.Compensation.CompensateMethod;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.Statement;
import com.d2c.order.query.CompensationSearcher;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(value = "compensationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CompensationServiceImpl extends ListServiceImpl<Compensation> implements CompensationService {

    @Autowired
    private CompensationMapper compensationMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private CompensationLogService compensationLogService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private StatementService statementService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Compensation insert(Compensation compensation, CompensateMethod method) {
        if (method == null) {
            method = CompensateMethod.NORMAL;
        }
        if ("store".equalsIgnoreCase(compensation.getType())) {
            compensation = this.doStoreCompensation(compensation);
        } else if ("designer".equalsIgnoreCase(compensation.getType())) {
            compensation = this.doDesignerCompensation(compensation, method);
        }
        return compensation;
    }

    /**
     * 设计师赔偿
     *
     * @param compensation
     * @return
     */
    private Compensation doDesignerCompensation(Compensation compensation, CompensateMethod method) {
        Brand brand = brandService.findById(compensation.getDesignerId());
        compensation.setDesignerName(brand.getDesigners());
        // 是否存在该调拨单该设计师的赔偿单，如果已经有了就不要再赔偿了
        Compensation oldCompensation = compensationMapper.findDesignerCompensation(compensation.getRequisitionItemId(),
                compensation.getDesignerId());
        if (oldCompensation == null) {
            compensation = this.doCalculate(compensation, method);
            if (compensation != null) {
                compensation = this.save(compensation);
                if (compensation.getId() != null) {
                    int result = requisitionItemService.doFine(compensation.getRequisitionItemId(),
                            compensation.getRequisitionSn(), 1);
                    try {
                        MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                        if (result > 0 && memberInfo != null) {
                            String subject = "采购单提醒";
                            String content = "亲爱的" + compensation.getBrandName() + "品牌主理人，因采购单"
                                    + compensation.getRequisitionSn() + "处理超时，根据D2C设计师商品调拨规则，您已超期"
                                    + DateUtil.daysBetween(compensation.getEstimateDate(), compensation.getCreateDate())
                                    + "天，须赔偿" + compensation.getAmount() + "元，如有疑问，可联系品牌对接人进行咨询！";
                            PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                            MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                                    SmsLogType.REMIND, content);
                            msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return compensation;
    }

    /**
     * 门店赔偿
     *
     * @param compensation
     * @return
     */
    private Compensation doStoreCompensation(Compensation compensation) {
        // 查找统一调拨单已在同一门店被扣过
        Compensation same = compensationMapper.findStoreRequisition(compensation.getRequisitionItemId(),
                compensation.getStoreId());
        if (same == null) {
            BigDecimal amount = new BigDecimal(100);
            compensation.setAmount(amount);
            compensation.setActualAmount(amount);
            compensation = this.save(compensation);
            if (compensation.getId() != null) {
                try {
                    JSONObject info = new JSONObject();
                    info.put("操作", "门店" + compensation.getStoreName() + "操作超时产生罚款");
                    info.put("操作时间", DateUtil.second2str(new Date()));
                    requisitionItemService.insertLog(compensation.getRequisitionItemId(),
                            compensation.getRequisitionSn(), "处理采购单", info.toJSONString(), "系统");
                    String subject = "调拨单提醒";
                    String content = "亲爱的" + compensation.getStoreName() + "门店管理员，因调拨单"
                            + compensation.getRequisitionSn() + "处理超时，根据D2C商品调拨规则，须扣款" + compensation.getAmount()
                            + "元！";
                    MemberInfo memberInfo = memberInfoService.findByStoreId(compensation.getStoreId());
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return compensation;
    }

    /**
     * 计算设计师发货的赔偿金额和调拨金额
     *
     * @param compensation
     * @return
     */
    private Compensation doCalculate(Compensation compensation, CompensateMethod method) {
        OrderItem orderItem = orderItemService.findById(compensation.getOrderItemId());
        if (orderItem != null) {
            try {
                BigDecimal amount = new BigDecimal(0);
                if (method.equals(CompensateMethod.NORMAL)) {
                    int daysBetween = DateUtil.daysBetween(compensation.getEstimateDate(), new Date());
                    if (daysBetween >= 1) {
                        amount = orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.1));
                        // 超出1天的部分3%算
                        daysBetween = daysBetween - 1;
                        if (daysBetween > 0) {
                            amount = amount.add(
                                    orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.03 * daysBetween)));
                        }
                    }
                    // 赔偿金额=min(30%*实付,300,赔偿金额)
                    amount = (amount.compareTo(orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3))) > 0
                            ? orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3))
                            : amount);
                    amount = (amount.compareTo(new BigDecimal(300)) > 0 ? new BigDecimal(300) : amount).setScale(0,
                            RoundingMode.HALF_EVEN);
                } else {
                    amount = (new BigDecimal(300)
                            .compareTo(orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3))) > 0
                            ? orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3))
                            : new BigDecimal(300)).setScale(0, RoundingMode.HALF_EVEN);
                }
                if (amount.compareTo(new BigDecimal(0)) > 0) {
                    compensation.setAmount(amount);
                    compensation.setActualAmount(amount);
                    compensation.setTradeAmount(orderItem.getActualDeliveryAmount());
                    return compensation;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public PageResult<CompensationSummaryDto> findSummary(CompensationSearcher searcher, PageModel page) {
        PageResult<CompensationSummaryDto> pager = new PageResult<>(page);
        int totalCount = compensationMapper.countSummary(searcher);
        if (totalCount > 0) {
            List<CompensationSummaryDto> list = compensationMapper.findSummary(searcher, page);
            List<CompensationSummaryDto> dtos = new ArrayList<>();
            for (CompensationSummaryDto dto : list) {
                Map<String, Object> map = compensationMapper.findPaySummary(dto.getDesignerId(), searcher);
                if (map.size() > 0) {
                    dto.setPayQuantity(Integer.valueOf(map.get("payQuantity").toString()));
                    dto.setPayAmount(new BigDecimal(map.get("payAmount").toString()));
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<CompensationDto> findBySearcher(CompensationSearcher searcher, PageModel page) {
        PageResult<CompensationDto> pager = new PageResult<>(page);
        int totalCount = compensationMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<CompensationDto> dtos = new ArrayList<>();
            List<Compensation> list = compensationMapper.findBySearcher(searcher, page);
            for (Compensation compensation : list) {
                CompensationDto dto = new CompensationDto();
                BeanUtils.copyProperties(compensation, dto);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doBalance(Long id, String operator) {
        int result = 0;
        Compensation compensation = this.findOneById(id);
        if (compensation == null) {
            throw new BusinessException("赔偿单不存在，赔偿不成功");
        }
        if (compensation.getStatus() != 0) {
            throw new BusinessException("赔偿单已赔偿或者已关闭");
        }
        if (compensation.getActualAmount() == null) {
            throw new BusinessException("赔偿单：" + compensation.getRequisitionSn() + "金额不能为空，赔偿不成功");
        }
        result = compensationMapper.doBalance(id, operator);
        if (result > 0) {
            // fine 暂时设计师用
            if ("designer".equals(compensation.getType())) {
                requisitionItemService.doFine(compensation.getRequisitionItemId(), compensation.getRequisitionSn(), 2);
            } else {
                JSONObject info = new JSONObject();
                info.put("操作", "门店" + compensation.getStoreName() + "操作超时产生赔偿单结算");
                requisitionItemService.insertLog(compensation.getRequisitionItemId(), compensation.getRequisitionSn(),
                        "处理采购单", info.toString(), operator);
            }
            CompensationLog compensationLog = new CompensationLog(id, null, "结算赔偿单", operator);
            compensationLogService.insert(compensationLog);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doBalanceByDesigner(Long statementId, String balanceMan) {
        Statement statement = statementService.findById(statementId);
        if (statement != null && statement.getStatus() != 8) {
            throw new BusinessException("对账单未结算，赔偿单结算不成功");
        }
        PageResult<Long> pager = new PageResult<>();
        PageModel page = new PageModel();
        CompensationSearcher searcher = new CompensationSearcher();
        searcher.setStatementId(statementId);
        do {
            pager = findByStatement(statementId, page);
            for (Long id : pager.getList()) {
                this.doBalance(id, balanceMan);
            }
        } while (pager.isNext());
        return 1;
    }

    @Override
    public PageResult<Long> findByStatement(Long statementId, PageModel page) {
        PageResult<Long> pager = new PageResult<>();
        int totalCount = compensationMapper.countByStatement(statementId);
        if (totalCount > 0) {
            List<Long> list = compensationMapper.findByStatement(statementId, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePrice(Long id, BigDecimal actualAmount, String operator) {
        Compensation compensation = this.findOneById(id);
        int result = 0;
        if (compensation != null) {
            if (compensation.getStatus() != 0) {
                throw new BusinessException("此赔偿单状态不是未处理状态");
            }
            if ("designer".equals(compensation.getType())) {
                Statement statement = statementService.findById(compensation.getStatementId());
                if (statement != null && statement.getStatus() != 0) {
                    throw new BusinessException("对账单不是待发送状态，赔偿单修改价格不成功");
                }
            }
            result = compensationMapper.updatePrice(id, actualAmount, operator);
            if (result > 0) {
                // TODO 要判断赔偿单的状态和对账单的状态吗
                if (compensation.getStatementId() != null) {
                    statementService.updateCompensation(compensation.getStatementId());
                }
                JSONObject obj = new JSONObject(true);
                obj.put("操作", "修改赔偿金额");
                obj.put("修改前", compensation.getActualAmount());
                obj.put("修改后", actualAmount);
                CompensationLog compensationLog = new CompensationLog(id, null, obj.toString(), operator);
                compensationLogService.insert(compensationLog);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateRemark(Long id, String remark, String operator) {
        Compensation compensation = this.findOneById(id);
        int result = compensationMapper.updateRemark(id, remark);
        if (result > 0) {
            JSONObject obj = new JSONObject(true);
            obj.put("操作", "修改备注");
            obj.put("修改前", compensation.getRemark());
            obj.put("修改后", remark);
            CompensationLog compensationLog = new CompensationLog(id, null, obj.toString(), operator);
            compensationLogService.insert(compensationLog);
        }
        return result;
    }

    @Override
    public int countBySearcher(CompensationSearcher searcher) {
        return compensationMapper.countBySearcher(searcher);
    }

    @Override
    public int countSummary(CompensationSearcher searcher) {
        return compensationMapper.countSummary(searcher);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateStatement(Long statementId, int year, int month, int periodOfMonth, Long designerId) {
        return compensationMapper.updateStatement(statementId, year, month, periodOfMonth, designerId);
    }

    @Override
    public Map<String, Object> calculateBySearcher(CompensationSearcher searcher) {
        return compensationMapper.calculateBySearcher(searcher);
    }

    @Override
    public Compensation findDesignerByOrderItemId(Long orderItemId) {
        return compensationMapper.findDesignerByOrderItemId(orderItemId);
    }

}
