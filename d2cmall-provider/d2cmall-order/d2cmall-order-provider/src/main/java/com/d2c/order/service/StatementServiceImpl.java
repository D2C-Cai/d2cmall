package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.model.StatementLog;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.StatementLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.StatementMapper;
import com.d2c.order.dto.StatementDto;
import com.d2c.order.model.Statement;
import com.d2c.order.model.Statement.StatementStatus;
import com.d2c.order.model.StatementItem;
import com.d2c.order.query.CompensationSearcher;
import com.d2c.order.query.StatementItemSearcher;
import com.d2c.order.query.StatementSearcher;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("statementService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StatementServiceImpl extends ListServiceImpl<Statement> implements StatementService {

    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private StatementItemService statementItemService;
    @Autowired
    private StatementLogService statementLogService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CompensationService compensationservice;

    @Override
    public Statement findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int createStatement(Statement statement) {
        // 插入数据库
        statement = this.insert(statement);
        // 回插statementItem
        int result = statementItemService.doSend(statement.getId(), statement.getYear(), statement.getMonth(),
                statement.getPeriodOfMonth(), statement.getFromType(), statement.getDesignerId(),
                statement.getCreator());
        if (result < 1) {
            throw new BusinessException("生成不成功");
        }
        // 关联该品牌该年该月的赔偿单
        if (!"line".equals(statement.getFromType())) {
            int compensationResult = compensationservice.updateStatement(statement.getId(), statement.getYear(),
                    statement.getMonth(), statement.getPeriodOfMonth(), statement.getDesignerId());
            if (compensationResult > 0) {
                this.updateCompensation(statement.getId());
            }
        }
        // 发送站内消息
        Brand brand = brandService.findById(statement.getDesignerId());
        MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
        if (memberInfo != null) {
            String subject = "对账单提醒";
            String content = "亲爱的" + statement.getDesignerName() + "品牌主理人，您有新的对账单，请及时处理。";
            PushBean pushBean = new PushBean(memberInfo.getId(), content, 14);
            pushBean.setAppUrl("/rest/order/statement/detail/" + statement.getId());
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 14, subject, content);
            msgBean.setAppUrl("/rest/order/statement/detail/" + statement.getId());
            msgUniteService.sendPush(pushBean, msgBean);
        }
        String info = "创建对账单并发送成功，对账单编号:" + statement.getSn();
        StatementLog statementLog = new StatementLog(StatementLog.LogType.Send.name(), info, statement.getId(), null,
                statement.getCreator());
        statementLogService.insert(statementLog);
        return result;
    }

    @Override
    public StatementDto findDtoById(Long id) {
        Statement statement = this.findOneById(id);
        if (statement == null) {
            return null;
        }
        StatementDto dto = new StatementDto();
        BeanUtils.copyProperties(statement, dto);
        StatementItemSearcher itemSearcher = new StatementItemSearcher();
        itemSearcher.setStatementId(statement.getId());
        List<StatementItem> statementItems = statementItemService.findBySearcher(itemSearcher, null).getList();
        dto.setStatementItems(statementItems);
        return dto;
    }

    @Override
    public Statement findBySn(String sn) {
        return statementMapper.findBySn(sn);
    }

    @Override
    public List<Statement> findByDesigner(int settleYear, int settleMonth, int periodOfMonth, String fromType,
                                          Long designerId) {
        return statementMapper.findByDesigner(settleYear, settleMonth, periodOfMonth, fromType, designerId);
    }

    @Override
    public List<Map<String, Object>> countByDesigner(int settleYear, int settleMonth, int periodOfMonth, String type,
                                                     Long designerId) {
        return statementMapper.countByDesigner(settleYear, settleMonth, periodOfMonth, type, designerId);
    }

    @Override
    public PageResult<StatementDto> findBySearcher(StatementSearcher searcher, PageModel page) {
        PageResult<StatementDto> pager = new PageResult<>(page);
        int totalCount = statementMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Statement> list = statementMapper.findBySearcher(searcher, page);
            List<StatementDto> dtos = new ArrayList<>();
            for (Statement statement : list) {
                StatementDto dto = new StatementDto();
                BeanUtils.copyProperties(statement, dto);
                StatementItemSearcher itemSearcher = new StatementItemSearcher();
                itemSearcher.setStatementId(statement.getId());
                List<StatementItem> statementItems = statementItemService.findBySearcher(itemSearcher, null).getList();
                dto.setStatementItems(statementItems);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<Statement> findSimpleBySearcher(StatementSearcher searcher, PageModel page) {
        PageResult<Statement> pager = new PageResult<>(page);
        int totalCount = statementMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Statement> list = statementMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(StatementSearcher searcher) {
        return statementMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Statement insert(Statement statement) {
        return this.save(statement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateDesignerMemo(Long statementId, String memo, String operator) {
        Statement statement = this.findById(statementId);
        int result = statementMapper.updateDesignerMemo(statementId, memo);
        if (result > 0) {
            String info = "设计师备注修改，原始：" + (statement.getDesignerMemo() == null ? "" : statement.getDesignerMemo())
                    + "， 修改为：" + memo;
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info, statementId, null,
                    operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateAdminMemo(Long statementId, String memo, String operator) {
        Statement statement = this.findById(statementId);
        int result = statementMapper.updateAdminMemo(statementId, memo);
        if (result > 0) {
            String info = "客服备注修改，原始：" + (statement.getAdminMemo() == null ? "" : statement.getAdminMemo()) + "， 修改为："
                    + memo;
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info, statementId, null,
                    operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSettle(BigDecimal settlePrice, BigDecimal tagPrice, Integer quantity, String operator, Long id) {
        return statementMapper.updateSettle(settlePrice, tagPrice, quantity, operator, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBack(Long statementId, String memo, String operator) {
        Statement statement = this.findById(statementId);
        if (statement != null) {
            if (!statement.getStatus().equals(Statement.StatementStatus.WAITCONFIRM.getCode())) {
                throw new BusinessException("请先接收对账单");
            }
            int result = statementMapper.doBack(statementId, memo);
            if (result > 0) {
                int success = statementItemService.doBack(statementId, operator);
                if (success > 0) {
                    String info = "设计师退回编号" + statement.getSn() + "的对账单，原因：" + memo;
                    StatementLog statementLog = new StatementLog(StatementLog.LogType.Back.name(), info, statementId,
                            null, operator);
                    statementLogService.insert(statementLog);
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doConfirm(Long statementId, String operator) {
        Statement statement = this.findById(statementId);
        if (statement != null) {
            if (!statement.getStatus().equals(Statement.StatementStatus.WAITCONFIRM.getCode())) {
                throw new BusinessException("请先接收对账单");
            }
            int result = this.updateStatus(statementId, Statement.StatementStatus.WAITPAY.getCode(),
                    Statement.StatementStatus.WAITCONFIRM.getCode());
            if (result > 0) {
                int success = statementItemService.doConfirm(statementId, operator);
                if (success > 0) {
                    String info = "设计师确认编号" + statement.getSn() + "的对账单";
                    StatementLog statementLog = new StatementLog(StatementLog.LogType.Confirm.name(), info, statementId,
                            null, operator);
                    statementLogService.insert(statementLog);
                    return 1;
                }
            }
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    private int updateStatus(Long id, Integer status, Integer oldStatus) {
        if (status == null || id == null) {
            return 0;
        } else {
            return statementMapper.updateStatus(id, status, oldStatus);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doPay(String payer, String paySn, Date payDate, Long id, String payBank, String payPic, String operator,
                     BigDecimal payMoney, String payMemo) {
        Statement statement = this.findById(id);
        int success = 0;
        if (statement != null) {
            if (statement.getStatus() != StatementStatus.WAITPAY.getCode()) {
                throw new BusinessException("对账单状态非待支付状态！");
            }
            success = statementMapper.doPay(payer, paySn, payDate, id, payBank, payPic, operator, payMoney, payMemo);
            if (success > 0) {
                String info = "编号：" + statement.getSn() + "，支付流水号：" + paySn + "，支付时间：" + DateUtil.day2str(payDate)
                        + "，支付金额：" + payMoney + "，备注：" + payMemo;
                StatementLog statementLog = new StatementLog(StatementLog.LogType.Pay.name(), info, id, null, operator);
                statementLogService.insert(statementLog);
            }
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSuccess(Long id, String operator) {
        Statement statement = this.findById(id);
        int success = 0;
        if (statement != null) {
            if (statement.getStatus() != 3) {
                throw new BusinessException("对账单状态非待结算状态！");
            }
            if (statement.getTotalPayAmount() == null) {
                throw new BusinessException("该对账单还未支付，结算不成功！");
            }
            if (statement.getTotalPayAmount().compareTo(statement.getSettleAmount()) != 0) {
                throw new BusinessException("结算金额和支付金额不一致，结算不成功！");
            }
            Date finishDate = new Date();
            success = statementMapper.doSuccess(id, operator, finishDate);
            if (success > 0) {
                statementItemService.doSuccess(id, operator);
                compensationservice.doBalanceByDesigner(id, operator);
                String info = "编号：" + statement.getSn() + " 结算完成，时间：" + DateUtil.day2str(finishDate);
                StatementLog statementLog = new StatementLog(StatementLog.LogType.Success.name(), info, id, null,
                        operator);
                statementLogService.insert(statementLog);
                // 发送站内消息
                Brand brand = brandService.findById(statement.getDesignerId());
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String subject = "采购单提醒";
                    String content = "亲爱的" + statement.getDesignerName() + "品牌主理人，您的" + statement.getYear() + "年"
                            + statement.getMonth() + "月对账单单号为" + statement.getSn() + "，已经结款了呢，请知悉！";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doReceive(Long statementId, String operator) {
        Statement statement = this.findById(statementId);
        if (statement != null) {
            int result = this.updateStatus(statementId, Statement.StatementStatus.WAITCONFIRM.getCode(),
                    Statement.StatementStatus.WAITSIGN.getCode());
            if (result > 0) {
                int success = statementItemService.doReceive(statementId, operator);
                if (success > 0) {
                    String info = "设计师接收编号" + statement.getSn() + "的对账单";
                    StatementLog statementLog = new StatementLog(StatementLog.LogType.Receive.name(), info, statementId,
                            null, operator);
                    statementLogService.insert(statementLog);
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSend(Long id, String username, String adminMemo) {
        int result = 0;
        Statement statement = this.findById(id);
        if (statement == null) {
            throw new BusinessException("对账单不存在");
        }
        result = statementItemService.reSend(id, username);
        if (result > 0) {
            result = statementMapper.doSend(id, username);
            // 重新发送的时候算一下
            this.updateCompensation(id);
            if (result > 0) {
                // 发送站内消息
                Brand brand = brandService.findById(statement.getDesignerId());
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                String subject = "对账单提醒";
                String content = "亲爱的" + statement.getDesignerName() + "品牌主理人，您有新的对账单，请及时处理";
                PushBean pushBean = new PushBean(memberInfo.getId(), content, 14);
                MsgBean msgBean = new MsgBean(memberInfo.getId(), 14, subject, content);
                msgUniteService.sendPush(pushBean, msgBean);
                String info = "发送对账单，对账单编号:" + statement.getSn() + "注：" + adminMemo;
                StatementLog statementLog = new StatementLog(StatementLog.LogType.Send.name(), info, id, null,
                        username);
                statementLogService.insert(statementLog);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doApply(Long id, Integer flag, String operator, String memo) {
        int result = statementMapper.doApply(id, flag, operator);
        if (result > 0) {
            String info = "";
            if (flag == 1) {
                info = "申请用款";
            } else {
                info = "取消用款";
            }
            if (StringUtils.isNotBlank(memo)) {
                info = info + "；备注：" + memo;
            }
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info, id, null, operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRetreat(Long id, String memo, String username) {
        Statement statement = this.findById(id);
        if (statement == null) {
            throw new BusinessException("该对账单不存在！");
        }
        if (statement.getStatus() != 1) {
            throw new BusinessException("撤退状态不正确，只能在待接收状态撤退,改对账单状态为" + statement.getStatusName());
        }
        int result = statementItemService.doRetreat(id, username);
        if (result > 0) {
            result = statementMapper.doRetreat(id, username);
            if (result > 0) {
                StatementLog statementLog = new StatementLog(StatementLog.LogType.RETREAT.name(),
                        "撤回对账单" + (StringUtil.isBlank(memo) ? "" : ("，备注：" + memo)), id, null, username);
                statementLogService.insert(statementLog);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateCompensation(Long id) {
        CompensationSearcher searcher = new CompensationSearcher();
        searcher.setStatementId(id);
        Map<String, Object> map = compensationservice.calculateBySearcher(searcher);
        if (map != null) {
            Integer compensationCount = Integer.parseInt(String.valueOf(map.get("count")));
            BigDecimal compensationAmount = new BigDecimal(String.valueOf(map.get("amount")));
            BigDecimal actualCompensationAmount = new BigDecimal(String.valueOf(map.get("actualAmount")));
            return statementMapper.updateCompensation(id, compensationCount, compensationAmount,
                    actualCompensationAmount);
        }
        return 0;
    }

}
