package com.waterelephant.third.utils;

import java.util.ResourceBundle;

/**
 * FaceID常量
 * @author dengyan
 *
 */
public class ThirdInterfaceConstant {

	
		public static String YUNOSSURL = "";
		public static String ACCESS_KEY_ID = "";
		public static String ACCESS_KEY_SECRET = "";
		public static String BACKETNAME = "";
		public static String FILEURL = "";
		public static String IMAGERUL = "";
		
		static {
			ResourceBundle config_faceid = ResourceBundle.getBundle("faceid");
			ThirdInterfaceConstant.YUNOSSURL = config_faceid.getString("yunossurl");
			ThirdInterfaceConstant.ACCESS_KEY_ID = config_faceid.getString("access_key_id");
			ThirdInterfaceConstant.ACCESS_KEY_SECRET = config_faceid.getString("access_key_secret");
			ThirdInterfaceConstant.BACKETNAME = config_faceid.getString("backetname");
			ThirdInterfaceConstant.FILEURL = config_faceid.getString("fileurl");
			ThirdInterfaceConstant.IMAGERUL = config_faceid.getString("imageurl");
		}
}
