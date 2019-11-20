//package com.waterelephant.sxyDrainage.utils.geinihua;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.*;
//
///**
// * 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
// *
// * @author xanthuim
// */
//public class Utils {
//
//    /**
//     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
//     * 实现步骤: <br>
//     *
//     * @param paraMap 要排序的Map对象
//     * @return
//     */
//    public static String formatUrlMap(Map<String, Object> paraMap) {
//        String buff;
//        Map<String, Object> tmpMap = paraMap;
//        try {
//            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
//            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
//            Collections.sort(infoIds, Comparator.comparing(o -> (o.getKey())));
//            // 构造URL 键值对的格式
//            StringBuilder buf = new StringBuilder();
//            for (Map.Entry<String, Object> item : infoIds) {
//                if (StringUtils.isNotBlank(item.getKey())) {
//                    String key = item.getKey();
//                    Object val = item.getValue();
//                    buf.append(key + "=" + val);
//                    buf.append("&");
//                }
//
//            }
//            buff = buf.toString();
//            if (buff.isEmpty() == false) {
//                buff = buff.substring(0, buff.length() - 1);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return buff;
//    }
//}
