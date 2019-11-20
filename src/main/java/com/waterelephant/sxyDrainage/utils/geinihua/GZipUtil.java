//package com.waterelephant.sxyDrainage.utils.geinihua;
//
//import com.alibaba.fastjson.JSONObject;
//
//import java.io.*;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
///**
// * Created by yinzhimin@u51.com
// */
//public class GZipUtil {
//
//    public static final int BUFFER = 1024;
//    public static final String EXT = ".gz";
//
//    /**
//     * 数据压缩
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static byte[] compress(byte[] data) {
//        ByteArrayInputStream bais = new ByteArrayInputStream(data);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            // 压缩
//            compress(bais, baos);
//            byte[] output = baos.toByteArray();
//            baos.flush();
//            return output;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                baos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                bais.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 数据压缩
//     *
//     * @param is
//     * @param os
//     * @throws Exception
//     */
//    public static void compress(InputStream is, OutputStream os) {
//        try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
//            int count;
//            byte[] data = new byte[BUFFER];
//            while ((count = is.read(data, 0, BUFFER)) != -1) {
//                gos.write(data, 0, count);
//            }
//            gos.finish();
//            gos.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 数据解压缩
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static byte[] decompress(byte[] data) {
//        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
//            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//                // 解压缩
//                decompress(bais, baos);
//                data = baos.toByteArray();
//                baos.flush();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return data;
//    }
//
//    /**
//     * 数据解压缩
//     *
//     * @param is
//     * @param os
//     * @throws Exception
//     */
//    public static void decompress(InputStream is, OutputStream os) throws Exception {
//        GZIPInputStream gis = new GZIPInputStream(is);
//        try {
//            int count;
//            byte data[] = new byte[BUFFER];
//            while ((count = gis.read(data, 0, BUFFER)) != -1) {
//                os.write(data, 0, count);
//            }
//        } finally {
//            try {
//                gis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("orderNo", "xxxxxx");
//        jsonObject.put("userId", "xxxxxx");
//        jsonObject.put("appId", "xxxxxx");
//        String encryptBASE64 = Coder.encryptBASE64(compress(jsonObject.toJSONString().getBytes()));
//        System.out.println(encryptBASE64);
//        String decryptBASE64 = new String(GZipUtil.decompress(Coder.decryptBASE64(encryptBASE64)), "UTF-8");
//        System.out.println(decryptBASE64);
//    }
//}
