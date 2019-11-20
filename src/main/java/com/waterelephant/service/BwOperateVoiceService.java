package com.waterelephant.service;

import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwOperateVoice;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

/**
 * @since 2018-07-19
 */
public interface BwOperateVoiceService {

    // 保存
    int save(BwOperateVoice bwOperateVoice);

    // 近3个月内呼入最频繁电话联系总次数＜40次 且 近3个月内呼出最频繁电话联系总次数＜40次
    Map<String, Object> countVoiceTimes(Long borrowerId);

    // 近3个月内呼入呼出最频繁电话联系总次数
    Map<String, Object> countVoiceTimesEs(Long borrowerId) throws Exception;

    // 近3个月内呼入最频繁电话联系总次数
    Map<String, Object> countVoiceTimesEsInTotal(Long borrowerId) throws Exception;

    // 近3个月内呼出最频繁电话联系总次数
    Map<String, Object> countVoiceTimesEsOutTotal(Long borrowerId) throws Exception;

    // 统计近3个月内呼入最频繁电话联系总次数
    List<Map<String, Object>> countVoiceInTimes(Long borrowerId);

    List<Map<String, Object>> countVoiceInTimesEs(Long borrowerId) throws Exception;

    // 统计近3个月内呼出最频繁电话联系总次数
    List<Map<String, Object>> countVoiceOutTimes(Long borrowerId);

    List<Map<String, Object>> countVoiceOutTimesEs(Long borrowerId) throws Exception;

    // 统计运营商号码总通讯个数
    Map<String, Object> countVoiceContactList(Long borrowerId);

    Map<String, Object> countVoiceContactListEs(Long borrowerId) throws Exception;

    // 统计通话记录top10的总次数
    int SumVoiceTopTen(Long borrowerId);

    int SumVoiceTopTenEs(Long borrowerId) throws Exception;

    // 根据借款人id统计运营商的通讯总个数
    int sumContact(Long borrowerId);

    int sumContactEs(Long borrowerId) throws Exception;

    // 根据借款人统计呼入呼出号码中短号个数
    List<String> sumShortPhone(Long borrowerId);

    List<String> sumShortPhoneEs(Long borrowerId) throws Exception;

    // 统计3个月内呼入前10
    List<String> inCountVoicetopTen(Long borrowerId);

    List<String> inCountVoicetopTenEs(Long borrowerId) throws Exception;

    // 统计3个月内呼出前10
    List<String> outCountVoicetopTen(Long borrowerId);

    List<String> outCountVoicetopTenEs(Long borrowerId) throws Exception;

    // 根据手机号查询最近一次抓取的通话记录时间
    String getCallTimeByborrowerId(Long borrowerId);

    Date getCallTimeByborrowerIdEs(Long borrowerId);

    // 获取3个月通话记录
    List<BwOperateVoice> queryOperateVoice(Long borrowerId) throws Exception;

    List<BwContactList> queryContact(Long borrowerId);

    List<Map<String, Object>> countVoiceTimesEsTop10(Long borrowerId) throws Exception;

    long countVoiceTimesEsTotalNum(Long borrowerId);

    String findMaxDateOperateVoiceByBorrowerId(Long borrowerId);

    String getCallTimeByborrowerIdEsString(Long borrowerId) throws Exception;

    /**
     * (code:s2s)
     * <p>
     * 根据用户id查询
     *
     * @param borrowerId
     * @return
     */
    List<BwOperateVoice> findByBorrowerId(Long borrowerId);

    /**
     * 批处理通话记录。为了统一处理，请使用{@link com.waterelephant.sxyDrainage.service.CommonService#batch(Collection, Mapper)}
     *
     * @param operateVoices
     * @see com.waterelephant.sxyDrainage.service.CommonService#batch(Collection, Mapper)
     */
    @Deprecated
    void batchOperateVoice(List<BwOperateVoice> operateVoices);
}
