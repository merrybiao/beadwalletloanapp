package com.waterelephant.service.impl;

import com.waterelephant.entity.BwContactList;
import com.waterelephant.mapper.BwContactListMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwContactListService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ElasticSearchUtils;
import com.waterelephant.utils.SystemConstant;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 2018-07-18
 */
@Service
public class BwContactListService extends BaseService<BwContactList, Long> implements IBwContactListService {
    private Logger logger = Logger.getLogger(BwContactListService.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private BwContactListMapper bwContactListMapper;

    @Override
    public int addBwContactList(BwContactList bwContactList) {

        return mapper.insert(bwContactList);
    }

    @Override
    public int updateBwContactList(BwContactList bwContactList) {

        return mapper.updateByPrimaryKey(bwContactList);
    }

    @Override
    public BwContactList findBwContactListByAttr(BwContactList bwContactList) {

        return mapper.selectOne(bwContactList);
    }

    @Override
    public void addOrUpdateBwContactListByAttr(List<BwContactList> list, Long bId) {
        Date now = new Date();
        if (!CommUtils.isNull(list)) {
            String sql = "delete from bw_contact_list where borrower_id = " + bId;
            sqlMapper.delete(sql);
            for (BwContactList cl : list) {
                cl.setCreateTime(now);
                cl.setUpdateTime(now);
                try {
                    mapper.insert(cl);
                } catch (Exception e) {
                    logger.error("添加借款人通讯录出现异常,借款人id:" + cl.getBorrowerId() + ",好友名称:" + cl.getName() + ",好友电话:"
                            + cl.getPhone());
                }
            }
        }
    }

    @Override
    public void addOrUpdateBwContactLists(List<BwContactList> list, Long bId) {
        Date now = new Date();
        if (!CommUtils.isNull(list)) {
            String sql = "delete from bw_contact_list where borrower_id = " + bId;
            sqlMapper.delete(sql);
            for (BwContactList cl : list) {
                cl.setCreateTime(now);
                cl.setUpdateTime(now);
                try {
                    mapper.insert(cl);
                } catch (Exception e) {
                    logger.error("添加借款人通讯录出现异常,借款人id:" + cl.getBorrowerId() + ",好友名称:" + cl.getName() + ",好友电话:"
                            + cl.getPhone());
                }
            }
        }
    }

    /**
     * 批量插入通讯录，这个方法批量插入太慢了{@link BwContactListService#addOrUpdateBwContactLists(java.util.List, java.lang.Long)}
     *
     * @param list
     * @param bId
     */
    @Override
    public void batchAddContact(List<BwContactList> list, Long bId) {
        bwContactListMapper.deleteByBorrowerId(bId);
        Date now = new Date();
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
            BwContactListMapper mapper = sqlSession.getMapper(BwContactListMapper.class);

            for (BwContactList cl : list) {
                cl.setCreateTime(now);
                cl.setUpdateTime(now);
                mapper.insert(cl);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<BwContactList> findBwContactListByBorrowerId(Long borrowerId) {
        BwContactList record = new BwContactList();
        record.setBorrowerId(borrowerId);
        return this.mapper.select(record);
    }

    @Override
    public void updateToEs(BwContactList bwContactList) throws Exception {
        Client client = ElasticSearchUtils.getInstance().getClient();
        // System.out.println("============client的值：" + client);
        String id = bwContactList.getBorrowerId() + "_" + bwContactList.getPhone();
        // System.out.println(id);
        // System.out.println("createTime======================>" + bwContactList.getCreateTime());
        IndexRequest indexRequest = new IndexRequest(SystemConstant.ES_INDEX, "BwContactList", id)
                .source(XContentFactory.jsonBuilder().startObject().field("name", bwContactList.getName())
                        .field("phone", bwContactList.getPhone())
                        .field("create_time", bwContactList.getCreateTime().getTime())
                        .field("update_time", bwContactList.getUpdateTime().getTime())
                        .field("borrower_id", bwContactList.getBorrowerId()).endObject());
        UpdateRequest updateRequest = new UpdateRequest(SystemConstant.ES_INDEX, "BwContactList", id)
                .doc(XContentFactory.jsonBuilder().startObject().field("name", bwContactList.getName())
                        .field("phone", bwContactList.getPhone())
                        .field("create_time", bwContactList.getCreateTime().getTime())
                        .field("update_time", bwContactList.getUpdateTime().getTime())
                        .field("borrower_id", bwContactList.getBorrowerId()).endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }

    @Override
    public long findBwContactListByBorrowerIdEs(Long borrowerId) throws Exception {
        String sql = "select count(1) from bw_contact_list c where c.borrower_id = " + borrowerId;
        long count = sqlMapper.selectOne(sql, Long.class);
        return count;
    }

    private Map<String, Object> findBwContactListByIdEs(String id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Client client = ElasticSearchUtils.getInstance().getClient();
        SearchResponse actionGet = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwContactList")
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("_id", id))).execute().actionGet();
        SearchHits shs = actionGet.getHits();
        for (SearchHit searchHit : shs) {
            map = searchHit.getSource();
        }
        return map;
    }

    /***
     *
     * @return
     */
    @Override
    public BwContactList findByPhoneAndBwId(String phone, Long borrowerId) {
        String sql = "select c.id  from bw_contact_list c where c.borrower_id= " + borrowerId + " and c.phone= '" + phone + "'";
        return sqlMapper.selectOne(sql, BwContactList.class);
    }

}
