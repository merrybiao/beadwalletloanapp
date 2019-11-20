package com.waterelephant.service;

import com.waterelephant.entity.SysDeptUser;

public interface SysDeptUserService extends BaseCommonService<SysDeptUser, Long> {

    /**
     * 根据用户查询最后一个部门ID
     *
     * @param userId
     * @return
     */
    Long findLastDeptIdByUserId(Long userId);
}