package com.waterelephant.service.impl;

import com.waterelephant.entity.SysDeptUser;
import com.waterelephant.service.SysDeptUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysDeptUserServiceImpl extends BaseCommonServiceImpl<SysDeptUser, Long> implements SysDeptUserService {

    @Override
    public Long findLastDeptIdByUserId(Long userId) {
        Example example = new Example(SysDeptUser.class);
//        example.setOrderByClause("deptId desc");
        example.orderBy("deptId").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        List<SysDeptUser> list = mapper.selectByExample(example);
        Long deptId = null;
        if (list != null && !list.isEmpty()) {
            deptId = list.get(0).getDeptId();
        }
        return deptId;
    }
}