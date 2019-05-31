package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.model.CollectionCard;
import com.d2c.member.model.CollectionCardDef;
import com.d2c.member.model.CollectionCardRecord;
import com.d2c.member.model.CollectionCardRecord.StageEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.model.CollectCardTaskDO;
import com.d2c.member.mongo.model.CollectCardTaskDO.TaskType;
import com.d2c.member.mongo.services.CollectCardTaskService;
import com.d2c.member.service.CollectionCardDefService;
import com.d2c.member.service.CollectionCardRecordService;
import com.d2c.member.service.CollectionCardService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.service.tx.CollectionCardTxService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * 集卡活动
 *
 * @author Lain
 */
@Controller
@RequestMapping("/collection/card")
public class CollectionCardController extends BaseController {

    private final Long promotionId = 2L;
    @Autowired
    private CollectCardTaskService collectCardTaskService;
    @Autowired
    private CollectionCardService collectionCardService;
    @Autowired
    private CollectionCardDefService collectionCardDefService;
    @Autowired
    private CollectionCardRecordService collectionCardRecordService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Reference
    private CollectionCardTxService collectionCardTxService;
    private Date startDate520 = DateUtil.str2minute("2019-05-19 20:00:00");
    private Date endDate520 = DateUtil.str2minute("2019-05-21 00:00:00");

    /**
     * 我的首页
     *
     * @param model
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        model.put("share", 0);// 已经分享
        model.put("share520", 0);// 分享520页面
        model.put("count", 0);// 抽奖次数
        model.put("stageResult", new JSONObject());
        Map<Long, List<CollectionCard>> myCards = new HashMap<>();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            // 每日任务
            CollectCardTaskDO shareTask = collectCardTaskService.findMine(memberInfo.getId(),
                    TaskType.SHAREACTIVITY.name(), true);
            CollectCardTaskDO share520Task = collectCardTaskService.findMine(memberInfo.getId(),
                    TaskType.SHARE520.name(), true);
            model.put("share", shareTask != null ? 1 : 0);
            model.put("share520", share520Task != null ? 1 : 0);
            Integer count = 0;
            if (shareTask != null && shareTask.getStatus() == 1) {
                count++;
            }
            if (share520Task != null && share520Task.getStatus() == 1) {
                count++;
            }
            model.put("count", count);
            // 抽卡阶段
            List<CollectionCardRecord> list2 = collectionCardRecordService.findStageRecord(memberInfo.getId(),
                    promotionId);
            JSONObject stageResult = JSON.parseObject("{\"THREE\":0,\"SIX\":0,\"NINE\":0}");
            list2.forEach(r -> stageResult.put(r.getStage(), 1));
            model.put("stageResult", stageResult);
            // 卡片分组
            List<CollectionCard> list1 = collectionCardService.findByMemberId(memberInfo.getId(), promotionId);
            list1.forEach(c -> {
                if (myCards.get(c.getDefId()) == null) {
                    myCards.put(c.getDefId(), new ArrayList<CollectionCard>());
                }
                myCards.get(c.getDefId()).add(c);
            });
        } catch (NotLoginException e) {
        }
        model.put("myCardDefSize", myCards.size());
        // 活动所有卡片
        List<CollectionCardDef> defs = collectionCardDefService.findByPromotionId(promotionId);
        JSONArray allCardArray = new JSONArray();
        defs.forEach(d -> {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) JSON.toJSON(d);
            obj.put("items", myCards.get(d.getId()) == null ? new ArrayList<>() : myCards.get(d.getId()));
            allCardArray.add(obj);
        });
        model.put("allCardArray", allCardArray);
        // 最近记录
        List<CollectionCardRecord> recentlyAwards = collectionCardRecordService.findRecently(10);
        model.put("recentlyAwards", recentlyAwards == null ? new ArrayList<>() : recentlyAwards);
        return "society/collect_card";
    }

    /**
     * 翻得卡片
     *
     * @param model
     * @param selectIndex
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String selectCard(ModelMap model, Integer selectIndex, Long memberId) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        if (new Date().after(endDate520)) {
            throw new BusinessException("活动已经结束");
        }
        MemberDto member = this.getLoginDto(); // TODO
        // 防止并发提交的锁
        final String collectCardKey = "collect_card_" + member.getId();
        try {
            Boolean exist = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(collectCardKey.getBytes());
                }
            });
            if (exist != null && exist.booleanValue()) {
                throw new BusinessException("您操作过快，不要心急哦！");
            } else {
                redisTemplate.execute(new RedisCallback<Long>() {
                    @Override
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.set(collectCardKey.getBytes(), String.valueOf(member.getId()).getBytes());
                        return 1L;
                    }
                });
            }
            if (memberId == null) {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                // 是否有抽奖机会
                CollectCardTaskDO shareTask = collectCardTaskService.findMine(memberInfo.getId(),
                        TaskType.SHAREACTIVITY.name(), true);
                CollectCardTaskDO share520Task = collectCardTaskService.findMine(memberInfo.getId(),
                        TaskType.SHARE520.name(), true);
                if ((shareTask == null || shareTask.getStatus() == 0)
                        && (share520Task == null || share520Task.getStatus() == 0)) {
                    throw new BusinessException("你已经没有抽奖机会");
                }
            }
            // 抽奖
            CollectionCardDef awardDef = null;
            if (Math.random() > 0.1) {
                // 抽卡
                awardDef = collectionCardDefService.selectCard(promotionId);
                CollectionCard collectionCard = new CollectionCard(awardDef);
                if (memberId != null && memberId.intValue() != member.getId()) {
                    collectionCard.setFromId(member.getId());
                    collectionCard.setFromName(member.getNickname());
                    MemberInfo toMember = memberInfoService.findById(memberId);
                    collectionCard.setMemberId(toMember.getId());
                    collectionCard.setLoginCode(toMember.getLoginCode());
                    collectionCard.setNickName(toMember.getNickname());
                } else {
                    collectionCard.setMemberId(member.getId());
                    collectionCard.setLoginCode(member.getLoginCode());
                    collectionCard.setNickName(member.getNickname());
                }
                int point = (int) (Math.random() * 100);
                collectionCard.setPoint(point);
                collectionCardRecordService.doCollectionCard(collectionCard);
            } else {
                // 谢谢惠顾
                CollectionCardRecord collectionCardRecord = new CollectionCardRecord();
                collectionCardRecord.setAwardName("谢谢惠顾");
                if (memberId != null && memberId.intValue() != member.getId()) {
                    collectionCardRecord.setFromId(member.getId());
                    collectionCardRecord.setMemberId(memberId);
                    MemberInfo toMember = memberInfoService.findById(memberId);
                    collectionCardRecord.setMemberName(toMember.getNickname());
                } else {
                    collectionCardRecord.setMemberId(member.getId());
                    collectionCardRecord.setMemberName(member.getNickname());
                }
                collectionCardRecordService.insert(collectionCardRecord);
            }
            // 12卡打乱
            List<CollectionCardDef> allCardDefs = collectionCardDefService.findByPromotionId(promotionId);
            if (allCardDefs != null && allCardDefs.size() < 12) {
                java.util.stream.Stream.of(new Object[12 - allCardDefs.size()]).forEach(o -> allCardDefs.add(null));
            }
            Collections.shuffle(allCardDefs);
            // 打乱后对选择的卡进行排序
            Integer index = allCardDefs.indexOf(awardDef);
            CollectionCardDef tempDef = allCardDefs.get(selectIndex);
            allCardDefs.set(selectIndex, awardDef);
            allCardDefs.set(index, tempDef);
            model.put("resultCards", allCardDefs);
            model.put("awardCard", awardDef);
        } finally {
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(collectCardKey.getBytes());
                    return 1L;
                }
            });
        }
        return "";
    }

    /**
     * 我的卡片
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String myCard(ModelMap model) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        // 我已经抽到的卡片
        List<CollectionCard> list = collectionCardService.findByMemberId(memberInfo.getId(), promotionId);
        model.put("cardArray", list);
        return "society/collect_card_mine";
    }

    /**
     * 我的奖品
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/award", method = RequestMethod.GET)
    public String myAward(ModelMap model) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("result", result);
        List<CollectionCardRecord> list = collectionCardRecordService.findByMemberId(memberInfo.getId(), promotionId);
        JSONArray array = new JSONArray();
        if (list != null) {
            list.forEach(r -> array.add(r.toJson()));
        }
        model.put("awards", array);
        return "society/collect_card_mine";
    }

    /**
     * 去分享页
     *
     * @return
     */
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public String toShare(ModelMap model) {
        MemberDto member = this.getLoginDto();
        model.put("member", member);
        return "society/collect_card_inviteshare";
    }

    /**
     * 分享出去的页面
     *
     * @param model
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/share/detail", method = RequestMethod.GET)
    public String middle(ModelMap model, Long memberId) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        if (memberInfo == null) {
            throw new BusinessException("该会员不存在");
        }
        model.put("shareHeadPic", memberInfo.getHeadPic());
        Map<Long, List<CollectionCard>> myCards = new HashMap<>();
        if (memberId != null) {
            // 卡片分组
            List<CollectionCard> list = collectionCardService.findByMemberId(memberId, promotionId);
            list.forEach(c -> {
                if (myCards.get(c.getDefId()) == null) {
                    myCards.put(c.getDefId(), new ArrayList<CollectionCard>());
                }
                myCards.get(c.getDefId()).add(c);
            });
        }
        model.put("myCardDefSize", myCards.size());
        // 活动所有卡片
        List<CollectionCardDef> defs = collectionCardDefService.findByPromotionId(promotionId);
        JSONArray allCardArray = new JSONArray();
        defs.forEach(d -> {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) JSON.toJSON(d);
            obj.put("items", myCards.get(d.getId()) == null ? new ArrayList<>() : myCards.get(d.getId()));
            allCardArray.add(obj);
        });
        return "society/collect_card_middle";
    }

    /**
     * 阶段性抽奖
     *
     * @param model
     * @param stage
     * @return
     */
    @RequestMapping(value = "/stage/award/{stage}", method = RequestMethod.POST)
    public String stageAward(ModelMap model, @PathVariable String stage) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (!StageEnum.keys.contains(stage)) {
            throw new BusinessException("请求异常");
        }
        if ("NINE".equals(stage)) {
            if (new Date().before(startDate520) || new Date().after(endDate520)) {
                throw new BusinessException("集九张卡片请于520当天抽奖");
            }
        }
        JSONObject award = processStageAward(stage);
        collectionCardTxService.doSendStageAward(memberInfo, StageEnum.valueOf(stage), award);
        model.put("award", award);
        return "";
    }

    private JSONObject processStageAward(String stage) {
        JSONObject obj = new JSONObject();
        double random = Math.random();
        if ("THREE".equals(stage)) {
            int red = (int) (random * 10 + 1);
            obj.put("type", "RED");
            obj.put("amount", red);
            obj.put("awardName", red + "元红包");
        } else if ("SIX".equals(stage)) {
            int red = (int) (random * 20 + 1);
            obj.put("type", "RED");
            obj.put("amount", red);
            obj.put("awardName", red + "元红包");
        } else if ("NINE".equals(stage)) {
            // if (random < 1) {
            obj.put("type", "RED");
            obj.put("amount", 100);
            obj.put("awardName", 100 + "元红包");
            // } else {
            // obj.put("type", "COUPON");
            // obj.put("couponId", 1722L);
            // obj.put("awardName", "全场五折优惠券");
            // }
        }
        return obj;
    }

}
