//package com.waterelephant.sxyDrainage.utils.rongYiTuiUtil;
//
//import java.security.Key;
//import java.security.Security;
//
//import javax.crypto.Cipher;
//
//public class DecryptUtil {
//
//    private static String strDefaultKey = "ryt-tec";
//    private Cipher decryptCipher = null;
//    public DecryptUtil() throws Exception {
//        this(strDefaultKey);
//    }
//
//    public DecryptUtil(String strKey) throws Exception {
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
//        Key key = getKey(strKey.getBytes());
//        decryptCipher = Cipher.getInstance("DES");
//        decryptCipher.init(Cipher.DECRYPT_MODE, key);
//    }
//
//    public static byte[] hexStr2ByteArr(String strIn) throws Exception
//    {
//        byte[] arrB = strIn.getBytes();
//        int iLen = arrB.length;
//        byte[] arrOut = new byte[iLen / 2];
//        for (int i = 0; i < iLen; i = i + 2) {
//            String strTmp = new String(arrB, i, 2);
//            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
//        }
//        return arrOut;
//    }
//
//
//    private static Key getKey(byte[] arrBTmp) throws Exception {
//        byte[] arrB = new byte[8];
//        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
//            arrB[i] = arrBTmp[i];
//        }
//        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
//        return key;
//    }
//
//    public byte[] decrypt(byte[] arrB) throws Exception {
//        return decryptCipher.doFinal(arrB);
//    }
//
//    public String decrypt(String strIn) throws Exception {
//        return new String(decrypt(hexStr2ByteArr(strIn)));
//    }
//
//}
