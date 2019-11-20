package com.waterelephant.zhengxin91.util;

import com.beadwallet.service.zhengxin91.entity.LoanInfo;

import java.util.List;

/**
 * Created by song on 2017/4/5.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/4/5 13:35
 */
public class ZhengXin {
	@SuppressWarnings("finally") public static boolean qiangGuiZe(List<LoanInfo> loanInfoList) {
		int i = 0;
		try {
			for (LoanInfo loanInfo : loanInfoList) {
				if (loanInfo.getArrearsAmount() != null && loanInfo.getRepayState() != null
						&& loanInfo.getArrearsAmount() > 0 && loanInfo.getCompanyCode() != null) {
					if (loanInfo.getCompanyCode().contains("人人催") || loanInfo.getCompanyCode().contains("风险名单")) {
						return false;
					}
					if ("0".equalsIgnoreCase(loanInfo.getRepayState() + "") || "1"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "2"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "9"
							.equalsIgnoreCase(loanInfo.getRepayState() + "")) {
						continue;
					}
					if ("3".equalsIgnoreCase(loanInfo.getRepayState() + "") || "4"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "5"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "6"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "7"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "8"
							.equalsIgnoreCase(loanInfo.getRepayState() + "")) {
						if (0 < loanInfo.getArrearsAmount() / 100000 && loanInfo.getArrearsAmount() / 100000 < 1000) {
							i += 1;
							if (i >= 5) {
								break;
							}
						}
						if (1000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 5000) {
							i += 2;
							if (i >= 5) {
								break;
							}
						}

						if (5000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 20000) {
							i += 3;
							if (i >= 5) {
								break;
							}
						}

						if (20000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 100000) {
							i += 4;
							if (i >= 5) {
								break;
							}
						}
						if (100000 <= loanInfo.getArrearsAmount() / 100000) {
							i += 5;
							if (i >= 5) {
								break;
							}
						}
					}
					//                if("4".equalsIgnoreCase(loanInfo.getRepayState()+"")){
					//                }
					//                if("5".equalsIgnoreCase(loanInfo.getRepayState()+"")
					//                        || "6".equalsIgnoreCase(loanInfo.getRepayState()+"")
					//                        || "7".equalsIgnoreCase(loanInfo.getRepayState()+"") ){
					//                }
					//                if("8".equalsIgnoreCase(loanInfo.getRepayState()+"")){
					//                }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (i >= 5) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 91征信 - 5月13号新定强规则
	 *
	 * @param loanInfoList
	 * @return
	 */
	public static boolean qiangGuiZe1003(List<LoanInfo> loanInfoList) {
		try {
			for (LoanInfo loanInfo : loanInfoList) {
				if (loanInfo.getRepayState() != null) {
					if ("3".equalsIgnoreCase(loanInfo.getRepayState() + "") ||"4"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "5"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "6"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "7"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "8"
							.equalsIgnoreCase(loanInfo.getRepayState() + "")) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 91征信 - 判断是否有逾期且逾期金额为0或还款状态未知
	 *
	 * @param loanInfoList
	 * @return
	 */
	public static boolean isUnclearSituation(List<LoanInfo> loanInfoList) {
		int i = 0;
		try {
			for (LoanInfo loanInfo : loanInfoList) {
				if (loanInfo.getArrearsAmount() == 0 && (loanInfo.getRepayState() != 1 || loanInfo.getRepayState() != 9)
						&& loanInfo.getCompanyCode() != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 91征信 - 多重负债
	 *
	 * @param loanInfoList
	 * @return
	 */
	public static boolean isMultipleLiabilities(List<LoanInfo> loanInfoList) {
		int i = 0;
		try {
			for (LoanInfo loanInfo : loanInfoList) {
				if (loanInfo.getArrearsAmount() != null && loanInfo.getRepayState() != null
						&& loanInfo.getArrearsAmount() > 0 && loanInfo.getCompanyCode() != null) {
					if ("0".equalsIgnoreCase(loanInfo.getRepayState() + "") || "1"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "2"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "9"
							.equalsIgnoreCase(loanInfo.getRepayState() + "")) {
						continue;
					}
					if ("3".equalsIgnoreCase(loanInfo.getRepayState() + "") || "4"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "5"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "6"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "7"
							.equalsIgnoreCase(loanInfo.getRepayState() + "") || "8"
							.equalsIgnoreCase(loanInfo.getRepayState() + "")) {
						if (0 < loanInfo.getArrearsAmount() / 100000 && loanInfo.getArrearsAmount() / 100000 < 1000) {
							i += 1;
							if (i >= 5) {
								break;
							}
						}
						if (1000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 5000) {
							i += 2;
							if (i >= 5) {
								break;
							}
						}

						if (5000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 20000) {
							i += 3;
							if (i >= 5) {
								break;
							}
						}

						if (20000 <= loanInfo.getArrearsAmount() / 100000
								&& loanInfo.getArrearsAmount() / 100000 < 100000) {
							i += 4;
							if (i >= 5) {
								break;
							}
						}
						if (100000 <= loanInfo.getArrearsAmount() / 100000) {
							i += 5;
							if (i >= 5) {
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i >= 5 ? true : false;
	}

	/**
	 * 91征信 - 风险名单
	 *
	 * @param loanInfoList
	 * @return
	 */
	public static boolean isRiskList(List<LoanInfo> loanInfoList) {
		boolean isRisk = false;
		try {
			for (LoanInfo loanInfo : loanInfoList) {
				if (loanInfo.getCompanyCode() != null) {
					if (loanInfo.getCompanyCode().contains("人人催") || loanInfo.getCompanyCode().contains("风险名单")) {
						isRisk = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRisk;
	}

}
