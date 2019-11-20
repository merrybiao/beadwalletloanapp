package com.waterelephant.faceID.utils;

import java.util.ResourceBundle;

/**
 * FaceID常量
 * @author dengyan
 *
 */
public class FaceIDConstant {

	
		public static String YUNOSSURL = "";
		public static String ACCESS_KEY_ID = "";
		public static String ACCESS_KEY_SECRET = "";
		public static String BACKETNAME = "";
		public static String FILEURL = "";
		public static String IMAGERUL = "";
		
		static {
			ResourceBundle config_faceid = ResourceBundle.getBundle("faceid");
			FaceIDConstant.YUNOSSURL = config_faceid.getString("yunossurl");
			FaceIDConstant.ACCESS_KEY_ID = config_faceid.getString("access_key_id");
			FaceIDConstant.ACCESS_KEY_SECRET = config_faceid.getString("access_key_secret");
			FaceIDConstant.BACKETNAME = config_faceid.getString("backetname");
			FaceIDConstant.FILEURL = config_faceid.getString("fileurl");
			FaceIDConstant.IMAGERUL = config_faceid.getString("imageurl");
		}
}
