package com.waterelephant.dto;

import java.util.List;

import com.waterelephant.entity.BwOperateVoice;

/**
 * 返回好贷运营商数据
 * @author song
 *
 */
public class DataBackDto {
	
		private List<BwOperateVoice> bwOperateVoices;
		private String tel;		 //电话号码
		private Long dataTime;  //当前时间戳
		
		public List<BwOperateVoice> getBwOperateVoices() {
			return bwOperateVoices;
		}
		public void setBwOperateVoices(List<BwOperateVoice> bwOperateVoices) {
			this.bwOperateVoices = bwOperateVoices;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public Long getDataTime() {
			return dataTime;
		}
		public void setDataTime(Long dataTime) {
			this.dataTime = dataTime;
		}
		
		
}
