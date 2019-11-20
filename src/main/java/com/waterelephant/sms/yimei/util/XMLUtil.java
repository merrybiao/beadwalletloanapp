package com.waterelephant.sms.yimei.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

import com.waterelephant.sms.entity.ReportEntity;

public class XMLUtil {
	
	/**
	 * 解析从亿美获取到的通话数据XML
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static List<ReportEntity> getXMLEntity(String text) throws DocumentException {
		// 第一步：创建SAXReader的reader对象
		SAXReader reader = new SAXReader();
		InputSource ins = new InputSource(new StringReader(text));
		ins.setEncoding("UTF-8");
		Document document = reader.read(ins);
		Element root = document.getRootElement(); // 获取根节点
		Iterator it2 = root.elementIterator("message"); // 获取根节点下的子节点
		List<ReportEntity> listData = new ArrayList<ReportEntity>();
		while(it2.hasNext()) {
			Element item = (Element)it2.next();
			String srctermid = item.elementTextTrim("srctermid");
			String submitDate = item.elementTextTrim("submitDate");
			String receiveDate = item.elementTextTrim("receiveDate");
			String addSerial = item.elementTextTrim("addSerial");
			String addSerialRev = item.elementTextTrim("addSerialRev");
			String state = item.elementTextTrim("state");
			String seqid = item.elementTextTrim("seqid");
			ReportEntity reportEntity = new ReportEntity();
			reportEntity.setSrctermid(srctermid);
			reportEntity.setSubmitDate(submitDate);
			reportEntity.setReceiveDate(receiveDate);
			reportEntity.setAddSerial(addSerial);
			reportEntity.setAddSerialRev(addSerialRev);
			reportEntity.setState(state);
			reportEntity.setSeqid(seqid);
			listData.add(reportEntity);
		}
		
		return listData;
	}
	

	/**
	 * xml转换成json字符串
	 * @param xml
	 * @return
	 */
	public static String xmlToJson(String xml) {
		JSONObject xmlJSONObject = XML.toJSONObject(xml);
		return xmlJSONObject.toString();
	}
}
