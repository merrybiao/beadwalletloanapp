package com.waterelephant.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;

/**
 * 上传阿里云服务图片
 * 
 * @author DOY
 *
 */
public class CdnUploadTools {
	/**
	 * @param is上传文件
	 * @param firstKey 文件的路径和文件名
	 * @return
	 */

	public static void uploadPic(InputStream is, String fileName) {

		// 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
		// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
		OSSClient ossClient = new OSSClient(SystemConstant.ENDPOINT, SystemConstant.ACCESSKEY_ID,
				SystemConstant.ACCESS_KEY_SECRET);
		try {
			// 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			if (!(ossClient.doesBucketExist(SystemConstant.BUCKET_NAME))) {
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				ossClient.createBucket(SystemConstant.BUCKET_NAME); // 当为空，创建一个Bucket
			}
			// 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			ossClient.getBucketInfo(SystemConstant.BUCKET_NAME);
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
			ossClient.putObject(SystemConstant.BUCKET_NAME, fileName, is);
			System.out.println("上传成功");
		} catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
	}

	/**
	 * 删除一个Bucket和其中的Object
	 * 
	 * @param
	 * @param bucketName Bucket名
	 */
	public static boolean deleteBucket(String imgPath) {
		OSSClient client = new OSSClient(SystemConstant.ENDPOINT, SystemConstant.ACCESSKEY_ID,
				SystemConstant.ACCESS_KEY_SECRET);
		// 如果不为空，先删除bucket下的指定的文件
		boolean success = false;
		try {
			client.deleteObject(SystemConstant.BUCKET_NAME, imgPath);
			success = true;
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 下载OSS文件
	 * 
	 * @param fileName 上传文件名
	 * @param savePath 保存到本地的路径
	 * @param isRename 是否重命名
	 */
	public static void downloadOSSFile(String fileName, String savePath, boolean isRename) {
		File target = new File(savePath);
		// 父目录不存在时，创建父目录
		File parent = target.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		// 重命名
		if (isRename) {
			int count = parent.list().length;
			savePath = savePath.substring(0, savePath.lastIndexOf(File.separator) + 1) + (count + 1)
					+ savePath.substring(savePath.lastIndexOf(File.separator) + 1, savePath.lastIndexOf("."))
					+ savePath.substring(savePath.lastIndexOf("."));
			System.out.println(savePath);
		}
		OSSClient client = new OSSClient(SystemConstant.ENDPOINT, SystemConstant.ACCESSKEY_ID,
				SystemConstant.ACCESS_KEY_SECRET);
		// 下载object到文件
		client.getObject(new GetObjectRequest(SystemConstant.BUCKET_NAME, fileName), new File(savePath));
		// 关闭client
		client.shutdown();
	}

	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * File f = new File("E:/11.png"); InputStream is = new FileInputStream(f); //InputStream is =
		 * headImage.getInputStream(); CdnUploadTools.uploadPic( is,"image/ss.png");
		 */
		// CdnUploadTools.deleteBucket("http://beadwallet.oss-cn-hangzhou.aliyuncs.com/upload/backend/20160826152028584.png");
		CdnUploadTools.downloadOSSFile("http://img.beadwallet.com/upload/contract/2017-08-29/20170829102140024.pdf",
				"D:\\abc\\c\\test.jpg", true);

	}

}
