//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.dkdh360.BwDkdh360InstalledApps;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.stereotype.Repository;
//import tk.mybatis.mapper.common.Mapper;
//
//import java.util.List;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/27 15:06
// * @Description: APP列表Mapper
// */
//@Repository
//public interface BwDkdh360InstalledAppsMapper extends Mapper<BwDkdh360InstalledApps> {
//
//    /**
//     * 根据订单ID查询
//     *
//     * @param orderId 订单ID
//     * @return List<BwDkdh360InstalledApps>
//     */
//    @Select("select * from bw_dkdh360_installed_apps where order_id = #{orderId}")
//    List<BwDkdh360InstalledApps> findListByOrderId(Long orderId);
//}
