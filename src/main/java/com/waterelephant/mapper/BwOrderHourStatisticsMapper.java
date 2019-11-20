package com.waterelephant.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwOrderHourStatistics;

import tk.mybatis.mapper.common.Mapper;

public interface BwOrderHourStatisticsMapper extends Mapper<BwOrderHourStatistics> {
	
	@Select("<script>"
			+ "SELECT "
			+ "	s.channel_id,s.channel_name,s.order_count,DATE_FORMAT(s.end_time,'%H:%i') as report_time "
			+ " FROM ("
			+ " SELECT * FROM bw_order_hour_statistics t "
			+ " WHERE t.statistics_type = #{type}"
			+ " AND t.product_id =7"
			+ " AND t.channel_id IN"
			+ " <foreach item='channelId' index='index' collection='channels' open='(' separator=',' close=')'>" 
		    + " #{channelId} "
		    + " </foreach> "
			+ " ORDER BY id DESC" 
			+ ") s GROUP BY s.channel_id"
			+ "</script>")
	List<Map<String,Object>> queryOrderHourStatistics(@Param("type")Integer type,@Param("channels")Integer[] channels);
	
	@Select("<script>"
			+ "SELECT -1 as channel_id,'' as channel_name,sum(x.order_count) as order_count,x.report_time FROM ("
			+ " SELECT "
			+ "	s.channel_id,s.channel_name,s.order_count,DATE_FORMAT(s.end_time,'%H:%i') as report_time "
			+ " FROM ("
			+ " SELECT * FROM bw_order_hour_statistics t "
			+ " WHERE t.statistics_type = #{type}"
			+ " AND t.product_id =7"
			+ " AND t.channel_id NOT IN"
			+ " <foreach item='channelId' index='index' collection='channels' open='(' separator=',' close=')'>" 
		    + " #{channelId} "
		    + " </foreach> "
			+ " ORDER BY id DESC" 
			+ ") s GROUP BY s.channel_id"
			+ ") x</script>")
	List<Map<String,Object>> queryOtherChannelsOrderHourStatistics(@Param("type")Integer type,@Param("channels")Integer[] channels);
	
	@Select("<script>"
			+ "SELECT -1 as channel_id,'' as channel_name, t.`end_time`, SUM(t.`order_count`) as order_count"
			+ " FROM bw_order_hour_statistics t"
			+ " WHERE t.statistics_type=#{type}"
			+ " AND t.product_id=#{productId}"
			+ " AND t.channel_id NOT IN "
			+ " <foreach item='channelId' index='index' collection='channels' open='(' separator=',' close=')'>" 
		    + " #{channelId} "
		    + " </foreach> "
		    + " AND t.`end_time` BETWEEN #{startTime} AND #{endTime}"
			+ " GROUP BY t.`end_time`"
			+ " </script>")
	List<BwOrderHourStatistics> queryOtherChannelsOrderSum(@Param("productId")Integer productId,@Param("channels")Integer[] channels,@Param("type")Integer type,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@Select("SELECT s.channel_id,s.channel_name, s.statistics_type,GROUP_CONCAT(s.order_count) as order_count FROM ("
			+ " SELECT t.channel_id,t.channel_name, t.statistics_type,t.order_count FROM `bw_order_hour_statistics` t "
			+ " WHERE DATE_FORMAT(t.`end_time`,'%Y-%m-%d %i:%s') = #{dateTime} AND t.`channel_id` IN "
			+ " <foreach item='channelId' index='index' collection='channels' open='(' separator=',' close=')'>" 
		    + " #{channelId} "
		    + " </foreach> "
			+ " ORDER BY t.`channel_id` , t.`statistics_type`,t.`end_time` ASC" 
			+ ") s GROUP BY s.channel_id, s.statistics_type")
	List<Map<String,Object>> query1DaysOrderStatistics(@Param("channels") Integer[] channels,String dateTime);
}
