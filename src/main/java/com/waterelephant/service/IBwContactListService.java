package com.waterelephant.service;

import com.waterelephant.entity.BwContactList;

import java.util.Collection;
import java.util.List;

import tk.mybatis.mapper.common.Mapper;

/**
 * @since 2018-07-18
 */
public interface IBwContactListService {

    int addBwContactList(BwContactList bwContactList);

    int updateBwContactList(BwContactList bwContactList);

    BwContactList findBwContactListByAttr(BwContactList bwContactList);

    void addOrUpdateBwContactListByAttr(List<BwContactList> list, Long bId);

    void addOrUpdateBwContactLists(List<BwContactList> list, Long bId);

    List<BwContactList> findBwContactListByBorrowerId(Long borrowerId);

    long findBwContactListByBorrowerIdEs(Long borrowerId) throws Exception;

    void updateToEs(BwContactList bwContactList) throws Exception;

    BwContactList findByPhoneAndBwId(String phone, Long borrowerId);

    /**
     * 批处理添加。为了统一处理，请使用{@link com.waterelephant.sxyDrainage.service.CommonService#batch(Collection, Mapper)}
     *
     * @param list
     * @param bId
     * @see com.waterelephant.sxyDrainage.service.CommonService#batch(Collection, Mapper)
     */
    @Deprecated
    void batchAddContact(List<BwContactList> list, Long bId);

}
