//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotBlank;
//
//import java.util.List;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///**
// * 附加信息
// *
// * @author xanthuim
// */
//
//public class TemplateInfo {
//    /**
//     * 贷款用途 int
//     * 1=家庭装修，2=旅游度假，3=租房，4=教育培训，5=婚庆，6=数码电器，7=健康医疗，8=购车，9=购房，10=经营周转，11=其他
//     */
//    private Integer loanPurpose;
//    /**
//     * 还款来源 int 是 1=工资收入，2=个体经营收入，3=其他非工资性收入
//     */
//    private Integer repayLoanSource;
//    /**
//     * 婚姻状态 int 1:已婚，有子女；2:已婚，无子女；3:未婚；4:其他（请备注）;5:已婚；6:离异；7:丧偶；8:复婚；
//     */
//    @NotNull(message = "maritalStatus不能为空")
//    private Integer maritalStatus;
//    /**
//     * 毕业年限 int 是 1=1年以下，2=1-3年，3=3-5年，4=5-10年，5=10年及以上
//     */
//    private Integer graduationTermLevel;
//
//    /**
//     * 邮箱 string
//     */
//    @NotBlank(message = "email不能为空")
//    @Email
//    private String email;
//    /**
//     * 负债情况 int 是 1=无，2=近一年债务总额<20万，3=近一年债务总额20万-50万，4=近一年债务总额50万-80万，5=近一年债务总额>80万
//     */
//    private Integer liabilitiesLevel;
//    /**
//     * 其他网络贷款情况 int 是
//     * 1=无，2=其他网贷在还借款<20万，3=其他网贷在还借款20万-50万，4=其他网贷在还借款50万-80万，5=其他网贷在还借款>80万
//     */
//    private Integer therLoanInfoType;
//    /**
//     * 近6个月征信逾期情况 int 是 1=信用良好，无逾期 2=逾期少于3次 3=逾期超过3次
//     */
//    private Integer recentCreditOverdueInfo;
//    /**
//     * 公司名称 string
//     */
//    @NotBlank(message = "company不能为空")
//    private String company;
//    /**
//     * 公司电话 string 是
//     */
//    private String companyPhone;
//
//    @Valid
//    @NotNull(message = "companyAddress不能为空")
//    private Address companyAddress;
//    /**
//     * 工作性质 int 是 1 普通工人，2 公司白领，3 私营业主，4 公务员，5 自由职业者
//     */
//    private Integer workNature;
//    /**
//     * 工作职级 int 是 1=普通员工/干部，2=中基层管理人员/科级，3=高层管理人员/处级，4=公司法人/股东，5=其他/非合同工
//     */
//    private Integer workRank;
//    /**
//     * 行业类别 int 是 1=农、林、牧、渔业，2=采矿业，3=制造业，4=电力、热力、燃气及水生产和供应业，
//     * 5=建筑业，6=批发和零售业，7=交通运输、仓储和邮政业，8=住宿和餐饮业，9=信息传输、软件和信息技术服务业，
//     * 10=金融业，11=房地产业，12=租赁和商务服务业，13=科学研究和技术服务业，14=水利、环境和公共设施管理业，
//     * 15=居民服务、修理和其他服务业，16=教育，17=卫生和社会工作，18=文化、体育和娱乐业，
//     * 19=公共管理、社会保障和社会组织，20=国际组织，21=其他
//     */
//    @NotNull(message = "industryCategory不能为空")
//    private Integer industryCategory;
//
//    /**
//     * 主体性质 int 是 1 自然人，2 法人 ，3 机构
//     */
//    private Integer mainNature;
//
//    @Valid
//    @NotNull(message = "currentAddress不能为空")
//    private Address currentAddress;
//
//    /**
//     * 用户联系
//     */
//    @Valid
//    @Size(message = "relations至少{min}个", min = 1)
//    private List<Relation> relations;
//
//    private CreditCard creditCard;
//
//    @Valid
//    private Identity identity;
//
//    /**
//     * QQ
//     */
//    //@NotBlank(message = "qq不能为空")
//    private String qq;
//    /**
//     * 微信
//     */
//    //@NotBlank(message = "wechat不能为空")
//    private String wechat;
//
//    public Integer getLoanPurpose() {
//        return loanPurpose;
//    }
//
//    public void setLoanPurpose( Integer loanPurpose) {
//        this.loanPurpose = loanPurpose;
//    }
//
//    public Integer getRepayLoanSource() {
//        return repayLoanSource;
//    }
//
//    public void setRepayLoanSource(Integer repayLoanSource) {
//        this.repayLoanSource = repayLoanSource;
//    }
//
//
//    public Integer getMaritalStatus() {
//        return maritalStatus;
//    }
//
//    public void setMaritalStatus(Integer maritalStatus) {
//        this.maritalStatus = maritalStatus;
//    }
//
//    public Integer getGraduationTermLevel() {
//        return graduationTermLevel;
//    }
//
//    public void setGraduationTermLevel(Integer graduationTermLevel) {
//        this.graduationTermLevel = graduationTermLevel;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getLiabilitiesLevel() {
//        return liabilitiesLevel;
//    }
//
//    public void setLiabilitiesLevel(Integer liabilitiesLevel) {
//        this.liabilitiesLevel = liabilitiesLevel;
//    }
//
//    public Integer getTherLoanInfoType() {
//        return therLoanInfoType;
//    }
//
//    public void setTherLoanInfoType(Integer therLoanInfoType) {
//        this.therLoanInfoType = therLoanInfoType;
//    }
//
//    public Integer getRecentCreditOverdueInfo() {
//        return recentCreditOverdueInfo;
//    }
//
//    public void setRecentCreditOverdueInfo(Integer recentCreditOverdueInfo) {
//        this.recentCreditOverdueInfo = recentCreditOverdueInfo;
//    }
//
//    public String getCompany() {
//        return company;
//    }
//
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public String getCompanyPhone() {
//        return companyPhone;
//    }
//
//    public void setCompanyPhone(String companyPhone) {
//        this.companyPhone = companyPhone;
//    }
//
//
//    public Address getCompanyAddress() {
//        return companyAddress;
//    }
//
//    public void setCompanyAddress(Address companyAddress) {
//        this.companyAddress = companyAddress;
//    }
//
//    public Integer getWorkNature() {
//        return workNature;
//    }
//
//    public void setWorkNature(Integer workNature) {
//        this.workNature = workNature;
//    }
//
//    public Integer getWorkRank() {
//        return workRank;
//    }
//
//    public void setWorkRank(Integer workRank) {
//        this.workRank = workRank;
//    }
//
//    public Integer getIndustryCategory() {
//        return industryCategory;
//    }
//
//    public void setIndustryCategory(Integer industryCategory) {
//        this.industryCategory = industryCategory;
//    }
//
//    public Integer getMainNature() {
//        return mainNature;
//    }
//
//    public void setMainNature(Integer mainNature) {
//        this.mainNature = mainNature;
//    }
//
//    public Address getCurrentAddress() {
//        return currentAddress;
//    }
//
//    public void setCurrentAddress(Address currentAddress) {
//        this.currentAddress = currentAddress;
//    }
//
//    public List<Relation> getRelations() {
//        return relations;
//    }
//
//    public void setRelations(List<Relation> relations) {
//        this.relations = relations;
//    }
//
//    public CreditCard getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(CreditCard creditCard) {
//        this.creditCard = creditCard;
//    }
//
//    public Identity getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(Identity identity) {
//        this.identity = identity;
//    }
//
//    public String getQq() {
//        return qq;
//    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }
//
//    public String getWechat() {
//        return wechat;
//    }
//
//    public void setWechat(String wechat) {
//        this.wechat = wechat;
//    }
//
//    public static class Identity {
//        /**
//         * 身份证正面ocr地址 string
//         */
//        @NotBlank(message = "idCardFront不能为空")
//        private String idCardFront;
//        /**
//         * 身份证反面ocr地址 string
//         */
//        @NotBlank(message = "idCardBack不能为空")
//        private String idCardBack;
//        /**
//         * 身份证号 string
//         */
//        @NotBlank(message = "idCardNo不能为空")
//        private String idCardNo;
//        /**
//         * 性别 string 男，女
//         */
//        @NotBlank(message = "gender不能为空")
//        private String gender;
//        /**
//         * 姓名 string
//         */
//        @NotBlank(message = "name不能为空")
//        private String name;
//        /**
//         * 民族 string
//         */
//        @NotBlank(message = "nation不能为空")
//        private String nation;
//        /**
//         * 正面地址 string
//         */
//        @NotBlank(message = "address不能为空")
//        private String address;
//        /**
//         * 发证机构 string
//         */
//        @NotBlank(message = "agency不能为空")
//        private String agency;
//        /**
//         * 有效期开始时间 string
//         */
//        @NotBlank(message = "validDateBegin不能为空")
//        private String validDateBegin;
//        /**
//         * 有效期结束时间 string
//         */
//        @NotBlank(message = "validDateEnd不能为空")
//        private String validDateEnd;
//        /**
//         * 人脸识别图片地址 array(JSON) url字符串数组
//         */
//        @Size(message = "faceDetectImageList至少{min}个", min = 1)
//        @NotNull(message = "faceDetectImageList不能为空")
//        private List<String> faceDetectImageList;
//        /**
//         * 人脸识别分数 double 是
//         */
//        private Double faceDetectScore;
//
//        public String getIdCardFront() {
//            return idCardFront;
//        }
//
//        public void setIdCardFront(String idCardFront) {
//            this.idCardFront = idCardFront;
//        }
//
//        public String getIdCardBack() {
//            return idCardBack;
//        }
//
//        public void setIdCardBack(String idCardBack) {
//            this.idCardBack = idCardBack;
//        }
//
//        public String getIdCardNo() {
//            return idCardNo;
//        }
//
//        public void setIdCardNo(String idCardNo) {
//            this.idCardNo = idCardNo;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getNation() {
//            return nation;
//        }
//
//        public void setNation(String nation) {
//            this.nation = nation;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getAgency() {
//            return agency;
//        }
//
//        public void setAgency(String agency) {
//            this.agency = agency;
//        }
//
//        public String getValidDateBegin() {
//            return validDateBegin;
//        }
//
//        public void setValidDateBegin(String validDateBegin) {
//            this.validDateBegin = validDateBegin;
//        }
//
//        public String getValidDateEnd() {
//            return validDateEnd;
//        }
//
//        public void setValidDateEnd(String validDateEnd) {
//            this.validDateEnd = validDateEnd;
//        }
//
//        public List<String> getFaceDetectImageList() {
//            return faceDetectImageList;
//        }
//
//        public void setFaceDetectImageList(List<String> faceDetectImageList) {
//            this.faceDetectImageList = faceDetectImageList;
//        }
//
//        public Double getFaceDetectScore() {
//            return faceDetectScore;
//        }
//
//        public void setFaceDetectScore(Double faceDetectScore) {
//            this.faceDetectScore = faceDetectScore;
//        }
//    }
//
//    public static class Address {
//        /**
//         * 公司所在省 string 是
//         */
//        private String province;
//        /**
//         * 公司所在市 string 是
//         */
//        private String city;
//        /**
//         * 公司所在区 string 是
//         */
//        private String area;
//        /**
//         * 公司详细地址 string 是
//         */
//        @NotBlank(message = "address不能为空")
//        private String address;
//
//        public String getProvince() {
//            return province;
//        }
//
//        public void setProvince(String province) {
//            this.province = province;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getArea() {
//            return area;
//        }
//
//        public void setArea(String area) {
//            this.area = area;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//    }
//
//    public static class Relation {
//        /**
//         * 用户联系人类型	string	是
//         * 关系：
//         * 1=父母，
//         * 2=配偶，
//         * 3=同事，
//         * 4=同学，
//         * 5=其他，
//         * 6=父亲，
//         * 7=母亲，
//         * 8=兄弟，
//         * 9=姐妹，
//         * 10=已成年子女，
//         * 11=朋友
//         */
//        private String relationType;
//        /**
//         * 用户联系人姓名 string 是
//         */
//        @NotBlank(message = "name不能为空")
//        private String name;
//        /**
//         * 用户联系人电话 是
//         */
//        @NotBlank(message = "phone不能为空")
//        private String phone;
//
//        public String getRelationType() {
//            return relationType;
//        }
//
//        public void setRelationType(String relationType) {
//            this.relationType = relationType;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//    }
//
//    public static class CreditCard {
//        /**
//         * 信用卡额度	int	是
//         * 1=¥5000及以下，
//         * 2=¥5001-10000，
//         * 3=¥100001-20000，
//         * 4=¥20001-30000，
//         * 5=¥30001-50000，
//         * 6=¥50001及以上
//         */
//        private Integer limitLevel;
//        /**
//         * 还款日 string 是
//         */
//        private String dueDate;
//
//        public Integer getLimitLevel() {
//            return limitLevel;
//        }
//
//        public void setLimitLevel(Integer limitLevel) {
//            this.limitLevel = limitLevel;
//        }
//
//        public String getDueDate() {
//            return dueDate;
//        }
//
//        public void setDueDate(String dueDate) {
//            this.dueDate = dueDate;
//        }
//    }
//}
