package com.waterelephant.service.impl;

import com.waterelephant.entity.BwCollectionUser;
import com.waterelephant.service.BwCollectionUserService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.SystemConstant;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class BwCollectionUserServiceImpl extends BaseCommonServiceImpl<BwCollectionUser, Long> implements BwCollectionUserService {

    @Override
    public BwCollectionUser findLastByRepayId(Long repayId, Date beforeTime) {
        Example example = new Example(BwCollectionUser.class);
//      example.setOrderByClause("createTime desc");
        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        if (beforeTime != null) {
            criteria.andLessThanOrEqualTo("createTime", CommUtils.convertDateToString(beforeTime, SystemConstant.YMD_HMS));
        }
        criteria.andEqualTo("repayId", repayId);
        List<BwCollectionUser> list = mapper.selectByExample(example);
        BwCollectionUser bwCollectionUser = null;
        if (list != null && !list.isEmpty()) {
            bwCollectionUser = list.get(0);
        }
        return bwCollectionUser;
    }
}