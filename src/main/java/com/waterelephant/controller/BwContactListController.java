package com.waterelephant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.entity.BwContactList;
import com.waterelephant.service.IBwContactListService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 借款人通讯录
 * 
 * @author lujilong
 *
 */
@Controller
@RequestMapping("/app/contact")
public class BwContactListController {
	private Logger logger = Logger.getLogger(BwContactListController.class);
	@Autowired
	private IBwContactListService bwContactListService;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/appCheckLogin/insertOrUpdateContactByBwId.do")
	public AppResponseResult insertOrUpdateContactByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		String bcStr = request.getParameter("bcStr");
		logger.info("根据借款人id:" + bwId + "添加或修改通讯录参数:" + bcStr);
		if (CommUtils.isNull(bwId)) {
			result.setCode("1041");
			result.setMsg("借款人id为空");
			return result;
		}
		if (CommUtils.isNull(bcStr)) {
			result.setCode("1042");
			result.setMsg("借款人通讯录获取为空");
			return result;
		}
		bcStr = bcStr.replaceAll(",=", ",缺省名称=");
		if (bcStr.startsWith("{=[],")) {
			bcStr = "{" + bcStr.substring(5, bcStr.length());
		}
		Map<String, Object> reqMap = (Map<String, Object>) JSONObject.fromObject(bcStr);

		Map<String, Object> map = str2Map(reqMap, Long.parseLong(bwId));

		List<BwContactList> list = (List<BwContactList>) map.get("bcList");

		List<String> nameList = (List<String>) map.get("nameList");

		bwContactListService.addOrUpdateBwContactListByAttr(list, Long.parseLong(bwId));
		result.setCode("000");
		result.setMsg("添加借款人通讯录成功");
		return result;
	}

	/**
	 * 讲通讯录的字符串转换为对应的map
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, Object> str2Map(Map<String, Object> reqMap, Long bwId) {
		List<BwContactList> bcList = new ArrayList<BwContactList>();
		List<String> nameList = new ArrayList<String>();
		for (String s : reqMap.keySet()) {
			String tempPhone = CommUtils.isNull(reqMap.get(s)) ? "" : reqMap.get(s).toString();
			String[] phones = getJsonToStringArray(tempPhone);
			if (CommUtils.isNull(phones)) {
				continue;
			}
			for (int i = 0; i < phones.length; i++) {
				BwContactList bc = new BwContactList();
				bc.setBorrowerId(bwId);
				String str = phones[i].replace("[", "").replace("]", "");
				if (str.contains(",")) {
					String[] temp = str.split(",");
					for (int k = 0; k < temp.length; k++) {
						bc = new BwContactList();
						bc.setBorrowerId(bwId);
						bc.setPhone(temp[k]);
						bcList.add(bc);
						nameList.add(s);
					}
				} else {
					bc.setPhone(str);
					bcList.add(bc);
					nameList.add(CommUtils.filterEmoji(s));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bcList", bcList);
		map.put("nameList", nameList);
		return map;
	}

	public static String[] getJsonToStringArray(String str) {
		if (CommUtils.isNull(str)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(str);
		String[] arr = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			arr[i] = jsonArray.getString(i);
		}
		return arr;
	}

	@ResponseBody
	@RequestMapping("/appCheckLogin/insertOrUpdateContactByBwIdNew.do")
	public AppResponseResult insertOrUpdateContactByBwIdNew(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		String bcStr = request.getParameter("bcStr");
		logger.info("根据借款人id:" + bwId + "添加或修改通讯录参数"); // 修改日志1001
		if (CommUtils.isNull(bwId)) {
			result.setCode("1041");
			result.setMsg("借款人id为空");
			return result;
		}
		if (CommUtils.isNull(bcStr)) {
			result.setCode("1042");
			result.setMsg("借款人通讯录获取为空");
			return result;
		}

		List<BwContactList> list = str2List(bcStr, Long.parseLong(bwId));

		bwContactListService.addOrUpdateBwContactLists(list,Long.parseLong(bwId));

		result.setCode("000");
		result.setMsg("添加借款人通讯录成功");
		return result;
	}

	/**
	 * 将"张三=1867777777,李四:121311111"格式字符串转换为map
	 * 
	 * @param str
	 * @return
	 */
	public static List<BwContactList> str2List(String str, Long bwId) {
		List<BwContactList> list = new ArrayList<BwContactList>();
		if (str.contains(",")) {
			String[] tStr = str.split(",");
			for (int i = 0; i < tStr.length; i++) {
				if (tStr[i].contains("=")) {
					String[] tempStr = tStr[i].split("=");
					String name = tempStr[0];
					String phone = tempStr[1];
					if (!CommUtils.isNull(name) && !CommUtils.isNull(phone)) {
						BwContactList bc = new BwContactList();
						bc.setBorrowerId(bwId);
						bc.setName(CommUtils.filterEmoji(name));
						bc.setPhone(phone);
						list.add(bc);
					}
				}

			}
		}
		return list;
	}

	public static void main(String[] args) {
		String str ="" ;
		str = str.replaceAll(",=", ",缺省名称=");
		if (str.startsWith("{=[],")) {
			str = "{" + str.substring(5, str.length());
		}
		Map<String, Object> reqMap = (Map<String, Object>) JSONObject.fromObject(str);

		Map<String, Object> map = str2Map(reqMap, 15347L);
		List<BwContactList> list = (List<BwContactList>) map.get("bcList");

		List<String> nameList = (List<String>) map.get("nameList");

		for (int i = 0; i < list.size(); i++) {

			String sql = " insert into " + " bw_contact_list(borrower_id,name,phone,create_time) values("
					+ list.get(i).getBorrowerId() + ",'" + nameList.get(i) + "','" + list.get(i).getPhone()
					+ "',now()); ";
			System.out.println(sql);

		}
	}
}
