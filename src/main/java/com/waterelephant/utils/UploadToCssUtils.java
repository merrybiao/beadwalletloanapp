package com.waterelephant.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传文件到OSS。
 *
 * @author 修改：增加状态码非200的情况，并输出日志，否则上传异常就不清楚了
 */
public class UploadToCssUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadToCssUtils.class);

    /**
     * 上传至oss
     *
     * @param url
     * @return
     */
    public static String urlUpload(String url) {
        String uploadPath = "";
        HttpURLConnection httpURLConnection = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + sdf2.format(new Date()) + ".jpg";
            URL imageUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 从服务器返回一个输入流
                try (InputStream inputStream = httpURLConnection.getInputStream()) {
                    // 上传至oss
                    CdnUploadTools.uploadPic(inputStream, uploadPath);
                }
            } else {
                LOGGER.error("状态码：{}，异常信息：{}", responseCode, httpURLConnection.getResponseMessage());
                return httpURLConnection.getResponseMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath;
    }

    /**
     * 上传至oss（code0091）
     *
     * @param url
     * @param fileName（fileName + ".jpg"）
     * @return
     * @author liuDaodao
     */
    public static String urlUpload(String url, String fileName) {
        String uploadPath = "";
        HttpURLConnection httpURLConnection = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + fileName + ".jpg";
            URL imageUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 从服务器返回一个输入流
                try (InputStream inputStream = httpURLConnection.getInputStream()) {
                    // 上传至oss
                    CdnUploadTools.uploadPic(inputStream, uploadPath);
                }
            } else {
                LOGGER.error("状态码：{}，异常信息：{}", responseCode, httpURLConnection.getResponseMessage());
                return httpURLConnection.getResponseMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath;
    }

    /**
     * 从服务器获得一个输入流（这里指的是获取img输入流）
     *
     * @param imgUrl 图pain路径
     * @return
     */
    public static InputStream getInputStream(String imgUrl) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(imgUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            } else {
                LOGGER.error("状态码：{}，异常信息：{}", responseCode, httpURLConnection.getResponseMessage());
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
