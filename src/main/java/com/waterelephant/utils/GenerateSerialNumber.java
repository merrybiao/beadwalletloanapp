package com.waterelephant.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateSerialNumber {

	private static String ser = "123456789abcdefghijklmnpqrstuvwxzy";
	private static String user = "abcdefghijklmnpqrstuvwxzy987654321";
	private static String kser = "cghjmqtw";
	private static int cbase = 148453711;
	private static Random random = new Random();

	public static void main(String[] args) {
		try {
			Set<String> strSet = new HashSet<String>();
			for (int i = 0; i < 10000000; i++) {
				String key = getSerialNumber();
				//System.out.println(key);
				strSet.add(key);
			}
			System.out.println(strSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getSerialNumber() {
		String serialNumber = generate(random.nextInt(10000000) + 1) + System.currentTimeMillis();
		return serialNumber;
	}

	public static String generate(int offset) {
		int p1 = random.nextInt(34);
		int p2 = random.nextInt(34);
		int p3 = random.nextInt(5) + 1;
		int p411 = cbase + offset;
		int p1315 = (int) Math.sqrt((p3 + 1) * p1 * p1 + (p3 + (p411 % 10)) * p2 * p2);

		String strp411 = String.valueOf(p411);

		int tmptotal = 0, tmpline = 0;
		for (int i = 0; i < strp411.length(); i++) {
			int tmpi = Integer.parseInt(strp411.charAt(i) + "");
			tmpline += (strp411.length() - i) * tmpi;
			tmptotal += (i + 1) * tmpi * tmpi;
		}
		p1315 += (int) Math.sqrt(tmptotal);
		p1315 += tmpline;

		StringBuffer key = new StringBuffer();
		key.append(ser.charAt(p1));
		key.append(user.charAt(p2));
		key.append(kser.charAt(p3));

		char cp30 = user.charAt(Integer.parseInt(strp411.charAt(p3) + ""));
		char cp31 = user.charAt(Integer.parseInt(strp411.charAt(p3 + 1) + ""));
		char cp32 = user.charAt(Integer.parseInt(strp411.charAt(p3 + 2) + ""));
		char cp33 = user.charAt(Integer.parseInt(strp411.charAt(p3 + 3) + ""));

		StringBuilder sbp411 = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			if (i == p3) {
				sbp411.append(cp30);
				continue;
			}

			if ((i == (p3 + 1))) {
				sbp411.append(cp31);
				continue;
			}

			if ((i == (p3 + 2))) {
				sbp411.append(cp32);
				continue;
			}

			if ((i == (p3 + 3))) {
				sbp411.append(cp33);
				continue;
			}

			sbp411.append(strp411.charAt(i));
		}

		key.append(sbp411.reverse().toString());

		String strp1315 = String.valueOf(p1315);
		if (strp1315.length() == 1) {
			key.append(user.charAt(p1315)); // 13
			int rnd = random.nextInt(24) + 10;
			key.append(ser.charAt(rnd)); // 14
			key.append(user.charAt(rnd)); // 15
		} else if (strp1315.length() == 2) {
			key.append(user.charAt(p1315 % 10)); // 13
			int rnd = (int) (p1315 / 10);
			key.append(ser.charAt(rnd)); // 14
			key.append(user.charAt(rnd + 10)); // 15
		} else {
			key.append(user.charAt(p1315 % 10)); // 13
			key.append(ser.charAt((int) ((p1315 / 10) % 10))); // 14
			key.append(user.charAt((int) (p1315 / 100))); // 15
		}

		return key.toString().toUpperCase();
	}

	public static boolean validate(String key) {
		if (key == null || key.length() != 15 || key.indexOf("o") != -1 || key.indexOf("O") != -1) {
			return false;
		}

		key = key.toLowerCase();

		int p1 = ser.indexOf(key.charAt(0));
		int p2 = user.indexOf(key.charAt(1));
		int p3 = kser.indexOf(key.charAt(2));

		String strp411 = new StringBuffer(key.substring(3, 12)).reverse().toString();
		StringBuilder sbp411 = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			if (i == p3) {
				sbp411.append(user.indexOf(strp411.charAt(i)));
				continue;
			}

			if ((i == (p3 + 1))) {
				sbp411.append(user.indexOf(strp411.charAt(i)));
				continue;
			}

			if ((i == (p3 + 2))) {
				sbp411.append(user.indexOf(strp411.charAt(i)));
				continue;
			}

			if ((i == (p3 + 3))) {
				sbp411.append(user.indexOf(strp411.charAt(i)));
				continue;
			}

			sbp411.append(strp411.charAt(i));
		}

		int tmptotal = 0, tmpline = 0;
		for (int i = 0; i < sbp411.length(); i++) {
			int tmpi = Integer.parseInt(sbp411.charAt(i) + "");
			tmpline += (sbp411.length() - i) * tmpi;
			tmptotal += (i + 1) * tmpi * tmpi;
		}
		tmptotal = (int) Math.sqrt(tmptotal);
		tmptotal += tmpline;

		int p411 = Integer.parseInt(sbp411.toString());
		int p13 = user.indexOf(key.charAt(12));
		int p14 = ser.indexOf(key.charAt(13));
		int p15 = user.indexOf(key.charAt(14));
		if (p14 > 9 && p15 != p14) {
			return false;
		}

		if (p15 > 9 && (p15 != (p14 + 10))) {
			return false;
		}

		if (p14 > 9) {
			p14 = 0;
		}

		if (p15 > 9) {
			p15 = 0;
		}

		StringBuffer sbp1315 = new StringBuffer();
		sbp1315.append(p15);
		sbp1315.append(p14);
		sbp1315.append(p13);
		int p1315 = Integer.parseInt(sbp1315.toString());

		return p1315 == ((int) Math.sqrt((p3 + 1) * p1 * p1 + (p3 + (p411 % 10)) * p2 * p2) + tmptotal);
	}
}
