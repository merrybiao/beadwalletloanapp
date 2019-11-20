package com.waterelephant.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 授信单附件表
 * @author dinglinhao
 *
 */
@Data
@Table(name="bw_credit_adjunct")
public class BwCreditAdjunct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键
	private Long borrowerId;//用户id
	private Long relationId;//关联id(入关联类型为授信,则关联授信表id)
	private Integer relationType;//关联类型(1:授信)
	private Integer adjunctType;//附件类型：1身份证正面，2：身份证反面，3：持证照，4：工牌，5：社保，6：房产，7：车辆，8：亲属工牌，9：信用卡正面，10：信用卡反面，11：借款协议，12：富友水象金融专用账户协议，13：小微金融信息咨询及信用管理服务合同，14：征信授权书, 15:人脸识别校验字符串  60:营业执照，61：抵押物,62：结婚照，63：户口本，64：结婚证，65：门店合照
	private String adjunctPath;//附件路径
	private String adjunctDesc;//附件描述
	private Integer third;//认证三方(1:商汤  2:腾讯云)
	private LocalDateTime createTime;//创建时间
	private LocalDateTime updateTime;//修改时间


}
