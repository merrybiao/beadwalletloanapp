package com.waterelephant.utils.mapper;

import com.waterelephant.utils.mapper.delete.BwDeleteByExampleMapper;
import com.waterelephant.utils.mapper.delete.BwDeleteByPrimaryKeyMapper;
import com.waterelephant.utils.mapper.delete.BwDeleteMapper;
import com.waterelephant.utils.mapper.insert.BwInsertMapper;
import com.waterelephant.utils.mapper.select.BwSelectExampleMapper;
/**
 * 该mapper主要处理在原有表后面加“_new"后缀的表(例如：bw_xg_xxxx_new)
 * 增删改带_new的表只需做做以下简单操作：
 * 1、mapper上继承BwNewTabMapper
 * 2、BaseService类中增加bwMapper类的依赖
 * 3、在service业务方法中调用xxxNewTab()的方法即可
 * 调用以上方法 操作的是带"_new"的表
 * @author dinglinhao
 * @date 2018年11月26日17:37:30
 * @since JDK 1.8
 * @version 1.0
 *
 * @param <T>
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BwNewTabMapper<T> extends BwSelectExampleMapper<T>, 
											BwInsertMapper<T>, 
											BwDeleteMapper<T>,
											BwDeleteByPrimaryKeyMapper<T>, 
											BwDeleteByExampleMapper<T> {

}
