package com.waterelephant.service.impl;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.mapper.BwOperateVoiceMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.utils.ElasticSearchUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.SystemConstant;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通话记录
 *
 * @since 2018-07-19
 */
@Service
public class BwOperateVoiceServiceImpl extends BaseService<BwOperateVoice, Long> implements BwOperateVoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BwOperateVoiceServiceImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int save(BwOperateVoice bwOperateVoice) {
        return mapper.insert(bwOperateVoice);
    }

    @Override
    public Map<String, Object> countVoiceTimes(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = " SELECT receive_phone,COUNT(receive_phone) AS num FROM bw_operate_voice WHERE call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "'" + "AND borrower_id = #{borrowerId} " + "GROUP BY receive_phone "
                + "ORDER BY num DESC LIMIT 1 ";
        return sqlMapper.selectOne(sql, borrowerId);
    }

    @Override
    public List<Map<String, Object>> countVoiceInTimes(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = "SELECT receive_phone,COUNT(receive_phone) AS num FROM bw_operate_voice WHERE call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "' " + "AND borrower_id = #{borrowerId} AND call_type = 2 "
                + "GROUP BY receive_phone " + "ORDER BY num DESC LIMIT 10 ";
        return sqlMapper.selectList(sql, borrowerId);
    }

    @Override
    public List<Map<String, Object>> countVoiceOutTimes(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = "SELECT receive_phone,COUNT(receive_phone) AS num FROM bw_operate_voice WHERE call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "' " + "AND borrower_id = #{borrowerId} AND call_type = 1 "
                + "GROUP BY receive_phone " + "ORDER BY num DESC LIMIT 10 ";
        return sqlMapper.selectList(sql, borrowerId);
    }

    @Override
    public Map<String, Object> countVoiceContactList(Long borrowerId) {
        String sql = "";
        return null;
    }

    @Override
    public int SumVoiceTopTen(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = "SELECT SUM(a.num)FROM ( "
                + "SELECT receive_phone,COUNT(receive_phone) AS num FROM bw_operate_voice WHERE call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "' " + "AND borrower_id = #{borrowerId} " + " "
                + " GROUP BY receive_phone " + "ORDER BY num DESC LIMIT 10 ) as a ";
        return sqlMapper.selectOne(sql, borrowerId, Integer.class);
    }

    @Override
    public int sumContact(Long borrowerId) {
        String sql = "SELECT count(a.id) FROM (SELECT id,receive_phone FROM bw_operate_voice WHERE borrower_id = #{borrowerId} "
                + "GROUP BY receive_phone ) as a ";
        return sqlMapper.selectOne(sql, borrowerId, Integer.class);
    }

    @Override
    public List<String> sumShortPhone(Long borrowerId) {
        String sql = "SELECT DISTINCT receive_phone FROM bw_operate_voice WHERE borrower_id = #{borrowerId} AND LENGTH(receive_phone) < 10 ";
        return sqlMapper.selectList(sql, borrowerId, String.class);
    }

    @Override
    public List<String> inCountVoicetopTen(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = "SELECT receive_phone,COUNT(receive_phone) num FROM bw_operate_voice WHERE borrower_id = #{borrowerId} AND call_type = 2 AND call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "'" + "GROUP BY receive_phone " + "ORDER BY num DESC " + "LIMIT 10 ";
        return sqlMapper.selectList(sql, borrowerId, String.class);
    }

    @Override
    public List<String> outCountVoicetopTen(Long borrowerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        String sql = "SELECT receive_phone,COUNT(receive_phone) num FROM bw_operate_voice WHERE borrower_id = #{borrowerId} AND call_type = 1 AND call_time BETWEEN '"
                + time1 + "' AND '" + time2 + "'" + "GROUP BY receive_phone " + "ORDER BY num DESC " + "LIMIT 10 ";
        return sqlMapper.selectList(sql, borrowerId, String.class);
    }

    @Override
    public String getCallTimeByborrowerId(Long borrowerId) {
        String sql = "SELECT call_time FROM bw_operate_voice WHERE borrower_id = #{borrowerId} ORDER BY call_time DESC LIMIT 1";
        return sqlMapper.selectOne(sql, borrowerId, String.class);
    }

    @Override
    public Map<String, Object> countVoiceTimesEs(Long borrowerId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(1)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> countVoiceInTimesEs(Long borrowerId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 2)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> countVoiceOutTimesEs(Long borrowerId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 1)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
            list.add(map);
        }
        return list;
    }

    @Override
    public Map<String, Object> countVoiceContactListEs(Long borrowerId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }

    @Override
    public int SumVoiceTopTenEs(Long borrowerId) throws Exception {
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));// 聚合时间字段
        SearchResponse search = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms phoneTerms = search.getAggregations().get("phoneAgg");
        int count = 0;
        for (Terms.Bucket entry : phoneTerms.getBuckets()) {
            count += entry.getDocCount();
        }
        return count;
    }

    @Override
    public int sumContactEs(Long borrowerId) throws Exception {
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        // System.out.println(end.getTime());
        // System.out.println(now.getTime());
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone")
                .size(Integer.MAX_VALUE).order(Terms.Order.count(false));// 聚合时间字段
        SearchResponse search = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms phoneTerms = search.getAggregations().get("phoneAgg");
        return phoneTerms.getBuckets().size();
    }

    @Override
    public List<String> sumShortPhoneEs(Long borrowerId) throws Exception {
        List<String> list = new ArrayList<String>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone")
                .size(Integer.MAX_VALUE);
        SearchResponse search = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.scriptQuery(new Script("doc['receive_phone'].value.size() < 10"))))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms phoneTerms = search.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : phoneTerms.getBuckets()) {
            list.add(entry.getKey().toString());
        }
        return list;
    }

    @Override
    public List<String> inCountVoicetopTenEs(Long borrowerId) throws Exception {
        List<String> list = new ArrayList<String>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 2)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            list.add(entry.getKey().toString());
        }
        return list;
    }

    @Override
    public List<String> outCountVoicetopTenEs(Long borrowerId) throws Exception {
        List<String> list = new ArrayList<String>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 1)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            list.add(entry.getKey().toString());
        }
        return list;
    }

    @Override
    public Date getCallTimeByborrowerIdEs(Long borrowerId) {
        String sql = "select MAX(c.call_time) from bw_operate_voice c where c.borrower_id = " + borrowerId;
        Date maxDate = sqlMapper.selectOne(sql, Date.class);
        return maxDate;
    }

    @Override
    public List<BwOperateVoice> queryOperateVoice(Long borrowerId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        String time1 = sdf.format(end);
        String time2 = sdf.format(now);
        // System.out.println("time1的值：" + time1);
        // System.out.println("time2的值：" + time2);
        StringBuffer sqlstr = new StringBuffer();
        sqlstr.append(
                "select t.trade_type,t.trade_time,t.call_time,t.trade_addr,t.receive_phone,t.call_type from bw_operate_voice t where");
        if (!CommUtils.isNull(borrowerId)) {
            sqlstr.append(" t.borrower_id=" + borrowerId);
            sqlstr.append(" AND call_time BETWEEN " + time1);
            sqlstr.append(" AND " + time2);
        }
        return sqlMapper.selectList(sqlstr.toString(), BwOperateVoice.class);
    }

    @Override
    public List<BwContactList> queryContact(Long borrowerId) {
        Client client;
        List<BwContactList> result = new ArrayList<BwContactList>();
        client = ElasticSearchUtils.getInstance().getClient();
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwContactList")
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId))).setFrom(0)
                .setSize(1000).execute().actionGet();
        SearchHits shs = actionGet.getHits();
        for (SearchHit it : shs) {
            BwContactList bwCon = new BwContactList();
            Map<String, Object> map = it.getSource();
            bwCon.setName(map.get("name") == null ? "" : map.get("name").toString());
            bwCon.setPhone(map.get("phone") == null ? "" : map.get("phone").toString());
            result.add(bwCon);
        }
        return result;
    }

    /**
     * 呼入呼出总次数前10个
     */
    @Override
    public List<Map<String, Object>> countVoiceTimesEsTop10(Long borrowerId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(10)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
            list.add(map);
        }
        return list;
    }

    @Override
    public long countVoiceTimesEsTotalNum(Long borrowerId) {
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        // System.out.println(end.getTime());
        // System.out.println(now.getTime());
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone")
                .size(Integer.MAX_VALUE).order(Terms.Order.count(false));// 聚合时间字段
        SearchResponse search = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms phoneTerms = search.getAggregations().get("phoneAgg");
        long total = 0;
        for (Terms.Bucket entry : phoneTerms.getBuckets()) {
            total += entry.getDocCount();
        }
        return total;
    }

    @Override
    public Map<String, Object> countVoiceTimesEsInTotal(Long borrowerId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(1)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 2)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
        }
        return map;
    }

    @Override
    public Map<String, Object> countVoiceTimesEsOutTotal(Long borrowerId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Date now = new Date();
        Date end = MyDateUtils.addMonths(now, -3);
        Client client = ElasticSearchUtils.getInstance().getClient();
        TermsBuilder phoneTermsBuilder = AggregationBuilders.terms("phoneAgg").field("receive_phone").size(1)
                .order(Terms.Order.count(false));
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("update_time").gt(end.getTime()).lt(now.getTime()))
                        .must(QueryBuilders.termQuery("borrower_id", borrowerId))
                        .must(QueryBuilders.termQuery("call_type", 1)))
                .addAggregation(phoneTermsBuilder).execute().actionGet();
        Terms terms = actionGet.getAggregations().get("phoneAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            map.put("receive_phone", entry.getKey());
            map.put("num", entry.getDocCount());
        }
        return map;
    }

    @Override
    public String findMaxDateOperateVoiceByBorrowerId(Long borrowerId) {
        String sql = "select c.call_time from bw_operate_voice c where c.borrower_id = " + borrowerId
                + "  ORDER BY c.call_time desc limit 1";
        String maxDate = sqlMapper.selectOne(sql, String.class);
        return maxDate;
    }

    @Override
    public String getCallTimeByborrowerIdEsString(Long borrowerId) throws Exception {
        String time = "0";
        Client client = ElasticSearchUtils.getInstance().getClient();
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoiceTemp")
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId))).setFrom(0)
                .setSize(1).addSort("update_time", SortOrder.DESC).execute().actionGet();
        SearchHits shs = actionGet.getHits();
        for (SearchHit searchHit : shs) {
            time = searchHit.getSource().get("update_time").toString();
        }
        return time;
    }

    /**
     * (code:s2s)
     *
     * @see com.waterelephant.service.BwOperateVoiceService#findByBorrowerId(java.lang.Long)
     */
    @Override
    public List<BwOperateVoice> findByBorrowerId(Long borrowerId) {
        BwOperateVoice bwOperateVoice = new BwOperateVoice();
        bwOperateVoice.setBorrower_id(borrowerId);
        return mapper.select(bwOperateVoice);
    }


    /**
     * 批处理通话记录
     *
     * @param operateVoices
     */
    @Override
    public void batchOperateVoice(List<BwOperateVoice> operateVoices) {
        long start = System.currentTimeMillis();
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
                false)) {
            BwOperateVoiceMapper mapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
            for (BwOperateVoice voice : operateVoices) {
                mapper.insert(voice);
            }
            sqlSession.commit();
        }
        long end = System.currentTimeMillis();
        LOGGER.info("batchOperateVoice处理{}条通话记录时间：{}毫秒", operateVoices.size(), end - start);
    }
}
