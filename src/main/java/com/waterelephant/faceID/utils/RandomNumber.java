package com.waterelephant.faceID.utils;

import java.util.Random;

/**
 * 随机字符串
 * @author dengyan
 *
 */
public class RandomNumber {

	public static String numberChar(){
		String number = "0123456789";
		StringBuilder randomNumber = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<4;i++){
			randomNumber.append(number.charAt(random.nextInt(4)));
		}
		return randomNumber.toString();
	}
	
}
