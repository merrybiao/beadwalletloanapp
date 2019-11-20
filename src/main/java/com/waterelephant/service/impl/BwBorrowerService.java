package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.mapper.BwBorrowerMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author
 * @since 2018-07-23
 */
@Service
public class BwBorrowerService extends BaseService<BwBorrower, Long> implements IBwBorrowerService {
    private Logger logger = Logger.getLogger(BwBorrowerService.class);

    @Autowired
    private BwBorrowerMapper bwBorrowerMapper;

    @Deprecated
    @Override
    public BwBorrower findBwBorrowerByAttr(BwBorrower borrower) {
        if (null == borrower) {
            return null;
        }
        if (StringUtil.isEmpty(borrower.getId()) && StringUtil.isEmpty(borrower.getFuiouAcct())
                && StringUtil.isEmpty(borrower.getPhone()) && StringUtil.isEmpty(borrower.getPassword())
                && StringUtil.isEmpty(borrower.getInviteCode()) && StringUtil.isEmpty(borrower.getBorrowerId())
                && StringUtil.isEmpty(borrower.getName()) && StringUtil.isEmpty(borrower.getIdCard())
                && StringUtil.isEmpty(borrower.getAuthStep()) && StringUtil.isEmpty(borrower.getState())
                && StringUtil.isEmpty(borrower.getCreateTime()) && StringUtil.isEmpty(borrower.getUpdateTime())
                && StringUtil.isEmpty(borrower.getRegisterAddr()) && StringUtil.isEmpty(borrower.getAge())
                && StringUtil.isEmpty(borrower.getSex()) && StringUtil.isEmpty(borrower.getChannel())) {
            return null;
        }
        return mapper.selectOne(borrower);
    }

    @Override
    public int addBwBorrower(BwBorrower borrower) {
        return mapper.insert(borrower);
    }

    @Override
    public int updateBwBorrower(BwBorrower borrower) {
        return mapper.updateByPrimaryKey(borrower);
    }

    @Override
    public BwBorrower findBwBorrowerById(Object obj) {
        return mapper.selectByPrimaryKey(obj);
    }

    @Override
    public int updateBwBorrowerInviteCode(BwBorrower borrower) {
        String sql = "update bw_borrower b set b.invite_code=#{inviteCode} where b.id=#{id}";
        return sqlMapper.update(sql, borrower);
    }

    @Override
    public BwBorrower oldUserFilter(String idCard, String name) {
        String sql = "SELECT * FROM bw_borrower WHERE name = '" + name + "' AND id_card LIKE '" + idCard + "%'";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public Long findBorrowerIdByOrderNo(Long orderNo) {
        String sql = "select b.id from bw_order o LEFT JOIN bw_borrower b ON o.borrower_id=b.id where o.order_no=#{orderNo}  and product_type = 1 ";
        return sqlMapper.selectOne(sql, orderNo, Long.class);
    }

    /*
     * @Override public void updateRenew(Long orderId, BwOrder order, String orderNo, Double money, BwRepaymentPlan
     * bwRepaymentPlan) throws BusException { try { logger.info("-----复制工单一系列操作开始----------------");
     * if(!CommUtils.isNull(order)){ order.setOrderNo(orderNo); order.setCreateTime(new Date()); order.setUpdateTime(new
     * Date()); order.setStatusId(9L);//还款中 Long id=bwOrderService.add(order);
     * logger.info("-----保存工单----------------"+id);
     *
     * //还款计划
     *
     * if(bwRepaymentPlan!=null){ logger.info("-----新增还款计划-开始---------------"); bwRepaymentPlan.setId(null);
     * bwRepaymentPlan.setOrderId(id); bwRepaymentPlan.setCreateTime(new Date());
     * bwRepaymentPlan.setRepayTime(MyDateUtils.addMonths(bwRepaymentPlan. getRepayTime(), order.getRepayTerm()));
     * logger.info("-----新还款日期----------------"+bwRepaymentPlan.getRepayTime());
     *
     * Date now = new Date(); if(now.after(bwRepaymentPlan.getRepayTime())){ bwRepaymentPlan.setRepayType(2);//已经逾期
     * }else{ bwRepaymentPlan.setRepayType(1);//正常还款 }
     *
     * int planInt=bwRepaymentPlanService.addBwRepaymentPlan(bwRepaymentPlan);
     * logger.info("-----新增还款计划-结束-------------");
     *
     * }
     *
     * List<BwAdjunct> bwAdjunctList= bwAdjunctService.adjunctType(orderId); logger.info("-----更新附件-开始------------");
     * if(!CommUtils.isNull(bwAdjunctList)){ for (int i = 0; i < bwAdjunctList.size(); i++) { BwAdjunct
     * bwAdjunct=bwAdjunctList.get(i); bwAdjunct.setOrderId(Long.valueOf(id)); bwAdjunct.setCreateTime(new Date());
     * bwAdjunct.setUpdateTime(new Date()); bwAdjunctService.add(bwAdjunct);
     *
     * } }
     *
     * logger.info("-----生成新合同 并更新附件-开始------------"); // 生成人名章 BwBorrowerSeal seal =
     * generateContractService.generateNameSeal(order.getBorrowerId()); // 生成《小微金融信息咨询及信用管理服务合同》
     * generateContractService.generateInfoCreditContract(order, seal); // 生成《征信授权书》 //
     * generateContractService.generateAuthorizationContract(bwOrder); // 生成《征信授权书》(新的)
     * generateContractService.generateCredit(order, seal); // 生成《富友》 generateContractService.generateFuiou(order,
     * seal); logger.info("-----生成新合同并更新附件-结束------------");
     *
     *
     * logger.info("-----工单记录表更新开始------------"); List<BwCheckRecord>
     * RecordList=bwCheckRecordService.queryCheck(String.valueOf(orderId)); if(!CommUtils.isNull(RecordList)){ for (int
     * j = 0; j < RecordList.size(); j++) { BwCheckRecord bwCheckRecord=RecordList.get(j);
     * bwCheckRecord.setOrderId(Long.valueOf(id)); bwCheckRecord.setCreateTime(new Date());
     * bwCheckRecordService.add(bwCheckRecord); } } logger.info("-----工单记录表更新-结束------------");
     *
     * //获取个人基本信息 BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(String.valueOf(orderId));
     * if(!CommUtils.isNull(bwPersonInfo)){ bwPersonInfo.setOrderId(id); bwPersonInfo.setCreateTime(new Date());
     * bwPersonInfoService.add(bwPersonInfo); } //获取工作信息 BwWorkInfo bwWorkInfo = bwPersonInfoService.queryWork(orderId);
     * if(!CommUtils.isNull(bwWorkInfo)){ bwWorkInfo.setOrderId(id); bwWorkInfo.setCreateTime(new Date());
     * bwPersonInfoService.addWork(bwWorkInfo); } //复议内容 List<BwReconsider> reconsider =
     * bwRejectRecordService.reconsider(String.valueOf(orderId)); if(!CommUtils.isNull(reconsider)){ for (int s = 0; s <
     * reconsider.size(); s++) { BwReconsider bwReconsider=reconsider.get(s); bwReconsider.setOrderId(id);
     * bwReconsider.setCreateTime(new Date()); reconsiderService.add(bwReconsider); } } //社保 List<BwInsureInfo>
     * BwInsureInfoList =bwInsureInfoService.queryInfo(orderId); if(!CommUtils.isNull(BwInsureInfoList)){ for (int z =
     * 0; z < BwInsureInfoList.size(); z++) { BwInsureInfo bwInsureInfo=BwInsureInfoList.get(z);
     * bwInsureInfo.setOrderId(id); bwInsureInfo.setCreateTime(new Date()); bwInsureInfoService.add(bwInsureInfo); } }
     * //被拒记录 List<BwRejectRecord> bwRejectRecordList=bwRejectRecordService.queryRecord(String.valueOf(orderId)) ;
     * if(!CommUtils.isNull(bwRejectRecordList)){ for (int z = 0; z < bwRejectRecordList.size(); z++) { BwRejectRecord
     * bwRejectRecord=bwRejectRecordList.get(z); bwRejectRecord.setOrderId(id); bwRejectRecord.setCreateTime(new
     * Date()); bwRejectRecordService.add(bwRejectRecord); } } Map<String,Object> map=sqlMapper.selectOne(
     * "select b.name,b.phone from bw_borrower b left join bw_order o on o.borrower_id=b.id where o.id=" +orderId);
     * Map<String,Object> opinion=sqlMapper. selectOne("select count(id) cou,money from bw_renew where phone="
     * +map.get("phone").toString()); money= money*0.15; if(Integer.valueOf(opinion.get("cou").toString())>0){ //更新续贷记录
     * sqlMapper.update("update bw_renew set money="+(Double.parseDouble(opinion.get ("money").toString())+money)+
     * " , update_time=now() where phone="+map.get("phone").toString()); }else{ //新增续贷 sqlMapper.insert(
     * "insert into bw_renew(name,phone,money,create_time,update_time) values('"
     * +map.get("name")+"','"+map.get("phone")+"',"+money+",now(),now() )"); } //工单关联表复制 logger.info(
     * "-----工单关联表 复制开始----------orderId-----"+orderId);
     *
     * BwOrderRelation orderRelation1=bwOrderRelationService.queryOrderRelation(orderId,1L);//初审
     *
     * if(orderRelation1!=null){ orderRelation1.setId(null); orderRelation1.setOrderId(id);
     * orderRelation1.setCreateTime(new Date()); orderRelation1.setUpdateTime(new Date()); int
     * begain=bwOrderRelationService.add(orderRelation1); logger.info("-----工单关联表 初审 插入---------------"+begain); }
     *
     * BwOrderRelation orderRelation2=bwOrderRelationService.queryOrderRelation(orderId,2L);//终审
     * if(orderRelation2!=null){ orderRelation2.setId(null); orderRelation2.setOrderId(id);
     * orderRelation2.setCreateTime(new Date()); orderRelation2.setUpdateTime(new Date()); int
     * end=bwOrderRelationService.add(orderRelation2); logger.info("-----工单关联表 终审 插入----------------"+end); }
     * logger.info("-----工单关联表 复制结束---------------");
     *
     * logger.info("-----复制工单一系列操作结束----------------"); } } catch (Exception e) { throw new
     * BusException(e.getMessage()); }
     *
     * }
     */

    @Override
    public int updateBorrwerAuthStep(Long orderId) {
        String sql = "update bw_borrower b set b.auth_step = 1 where b.id=(select o.borrower_id from bw_order o "
                + "where o.id=" + orderId + ")";
        return sqlMapper.update(sql);
    }

    /**
     * 获得实名认证基本信息
     */
    @Override
    public Map<String, Object> getopen(String orderId) {
        String sql = "select fuiou_acct,phone,name,id_card,id from bw_borrower where id=(select borrower_id from bw_order  where id='"
                + orderId + "')";
        return sqlMapper.selectOne(sql);
    }

    @Override
    public BwBorrower findBwBorrowerByOrderId(Long orderId) {
        String sql = " select b.id,b.`name`,b.phone,b.fuiou_acct,b.id_card,b.create_time,b.age,b.register_addr  from bw_borrower b where b.id=(select o.borrower_id from bw_order o "
                + "where o.id = " + orderId + ")";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public BwBorrower findBwBorrowerIdByOrderId(Long orderId) {
        String sql = " select b.id ,b.name from bw_borrower b where b.id=(select o.borrower_id from bw_order o "
                + "where o.id = " + orderId + ")";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public int countInvite(Long borrowerId) {
        String sql = "select count(id) inviteCount from bw_borrower where borrower_id = #{borrowerId}";
        return sqlMapper.selectOne(sql, borrowerId, Integer.class);
    }

    @Override
    public BwBorrower findNameAndIdCardById(Long borrowerId) {
        String sql = "select b.`name`,b.id_card from bw_borrower b where b.id=#{borrowerId}";
        return sqlMapper.selectOne(sql, borrowerId, BwBorrower.class);
    }

    @Override
    public List<Map<String, Object>> getInvitedNumStatis(ActivityInfo activityInfo, Integer minInvitedNum) {
        Integer activityId = activityInfo.getActivityId();
        Date startTime = activityInfo.getStartTime();
        Date endTime = activityInfo.getEndTime();
        String startTimeStr = CommUtils.convertDateToString(startTime, SystemConstant.YMD_HMS);
        String endTimeStr = CommUtils.convertDateToString(endTime, SystemConstant.YMD) + " 23:59:59";
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("select borrower_id,count(id) invited_num from bw_borrower where borrower_id is not null");
        // 条件：邀请的好友在活动时间内注册
        sqlSB.append(" and create_time between '");
        sqlSB.append(startTimeStr);
        sqlSB.append("' and '");
        sqlSB.append(endTimeStr);
        sqlSB.append("'");
        // 条件：邀请的好友未派发过优惠券
        if (activityId != null) {
            sqlSB.append(
                    " and (select count(1) from activity_discount_distribute d where d.borrow_id=borrow_id and d.activity_id=");
            sqlSB.append(activityId);
            sqlSB.append(")=0");
        }
        // 条件：邀请的好友借过款且借款时间在活动期间内
        sqlSB.append(
                " and id in (select DISTINCT o.borrower_id from bw_repayment_plan r inner join bw_order o on r.order_id=o.id where r.create_time between '");
        sqlSB.append(startTimeStr);
        sqlSB.append("' and '");
        sqlSB.append(endTimeStr);
        sqlSB.append("')");
        sqlSB.append(" group by borrower_id");
        if (minInvitedNum != null) {
            sqlSB.append(" having count(borrower_id)>=");
            sqlSB.append(minInvitedNum);
        }
        logger.info("统计查询用户邀请好友人数SQL==>" + sqlSB.toString());
        return sqlMapper.selectList(sqlSB.toString());
    }

    @Override
    public List<BwBorrower> findBwBorrowerListByIdCard(BwBorrower borrower) {
        return this.mapper.select(borrower);
    }

    @Override
    public BwBorrower oldUserFilter(String phone, String idCard, String name) {
        String sql = "SELECT * FROM bw_borrower WHERE name = '" + name + "' and phone LIKE '" + phone
                + "%' AND id_card LIKE '" + idCard + "%'";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public BwBorrower oldUserFilter2(String name, String phone, String id_number) {
        String sql = "SELECT * FROM bw_borrower where name='" + name + "' and phone like '" + phone
                + "' and id_card like '" + id_number + "'";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public BwBorrower oldUserFilter3(String phone) {
        String sql = "SELECT * FROM bw_borrower where phone = '" + phone + "'";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#updateBwBorrowerSelective(com.waterelephant.entity.BwBorrower)
     */
    @Override
    public int updateBwBorrowerSelective(BwBorrower borrower) {
        return mapper.updateByPrimaryKeySelective(borrower);
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#deleteBorrower(com.waterelephant.entity.BwBorrower)
     */
    @Override
    public void deleteBorrower(BwBorrower borrower) {
        mapper.delete(borrower);
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#chackOldUser(java.lang.String, java.lang.String)
     */
    @Override
    public BwBorrower checkOldUser(String idCard, String name) {
        String sql = "SELECT * FROM bw_borrower WHERE name = '" + name + "' AND id_card LIKE '" + idCard + "'";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @SuppressWarnings("deprecation")
	@Override
    public List<BwBorrower> checkOldUserList(String idCard, String name) {
        String sql = "SELECT * FROM bw_borrower WHERE name = '" + name + "' AND id_card LIKE '" + idCard + "'";
        return sqlMapper.selectList(sql, BwBorrower.class);
    }

    @SuppressWarnings("deprecation")
	@Override
    public List<BwBorrower> findBorrowerByCreateTimeAndChannel(String startTime, String endTime, String channelId) {
        // select * from bw_borrower bb where bb.create_time <='2017-12-19
        // 00:00:00' and bb.create_time >='2017-10-01
        // 00:00:00' and channel=19
        // StringBuilder sBuilder = new StringBuilder();
        // sBuilder.append(" select * from bw_borrower bb");
        // sBuilder.append(" where bb.create_time <= '" + endTime + "'");
        // sBuilder.append(" and bb.create_time >='" + startTime + "'");
        // sBuilder.append(" and channel=" + channelId);

        String sql = "select * from bw_borrower bb" + " where bb.create_time <='" + endTime + "' and bb.create_time >='"
                + startTime + "' and channel=" + channelId;

        return sqlMapper.selectList(sql, BwBorrower.class);

    }

    @SuppressWarnings("deprecation")
	@Override
    public BwBorrower selectLogin(String phone, int flag) {
        String sql = "SELECT * FROM bw_borrower WHERE phone = '" + phone + "' AND flag = " + flag;
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#checkOldUserByChannel(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public BwBorrower checkOldUserByChannel(String phone, String idCard, String name, Integer channelId) {
        String sql = "SELECT * FROM bw_borrower where name='" + name + "' and phone like '" + phone
                + "' and id_card like '" + idCard + "' and channel=" + channelId;
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#findBorrowerAndPositionXY(java.lang.Long)
     */
    @Override
    public Map<String, Object> findBorrowerAndPositionXY(Long borrowerId) {
        String sql = "select bb.name, bb.phone, bb.id_card, bbp.position_x, bbp.position_y from bw_borrower bb left join bw_borrower_position bbp on bb.id=bbp.borrower_id where bb.id= "
                + borrowerId + " order by bbp.create_time desc limit 1";
        return sqlMapper.selectOne(sql);
    }

    @Override
    public List<BwBorrower> selectSignListByIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return null;
        }
        String sql = StringUtils.join(
                " select b.* from bw_borrower b left join bw_bank_card c on b.id=c.borrower_id where b.id_card ='",
                idCard, "' and c.sign_status >= 1");
        List<BwBorrower> list = sqlMapper.selectList(sql, BwBorrower.class);
        return list;
    }

    /**
     * @see com.waterelephant.service.IBwBorrowerService#findBwBorrowerByPhoneAndName(java.lang.String,
     * java.lang.String)
     */

    @Override
    public BwBorrower findBwBorrowerByPhoneAndName(String phone, String name) {
        String sql = "SELECT * FROM bw_borrower WHERE name = '" + name + "' AND phone LIKE '" + phone + "' limit 1";
        return sqlMapper.selectOne(sql, BwBorrower.class);
    }

    @Override
    public List<BwBorrower> findBwBorrowerByPhoneORIdcard(String phone, String idCard) {
        String sql = "SELECT * FROM bw_borrower WHERE phone = '" + phone + "' or id_card = '" + idCard + "'";
        return sqlMapper.selectList(sql, BwBorrower.class);
    }

    @Override
    public List<BwBorrower> findByPhoneOrIdcard(String phone, String idCard) {
        return bwBorrowerMapper.findByPhoneOrCard(phone, idCard);
    }

	@Override
	public Integer getAppid(Long borrorerId) {
		Example example = new Example(BwBorrower.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", borrorerId);
		example.setOrderByClause("create_time desc");
		List<BwBorrower> list = mapper.selectByExample(example);
		return null == list || list.isEmpty() ? -1 : list.get(0).getAppId();
	}

}
