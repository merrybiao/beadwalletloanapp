package  com.waterelephant.cashloan.util;

import org.apache.log4j.Logger;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

  
/** 
 * 3DES加密，解密
 * @author dengyan
 * @version 1.0 
 */  
public class TripleDesUtil{  
    /** 
     * logger 日志 
     */  
    public static Logger logger = Logger.getLogger(TripleDesUtil.class); 
    
    private static final String KEY = CashLoanContext.get("desKey");
  
    /** 
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程 
     *  
     * @param arrB 
     *            需要转换的byte数组 
     * @return 转换后的字符串 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    private static String byteArr2HexStr(byte[] arrB) throws Exception {  
        int iLen = arrB.length;  
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍  
        StringBuffer sb = new StringBuffer(iLen * 2);  
        for (int i = 0; i < iLen; i++) {  
            int intTmp = arrB[i];  
            // 把负数转换为正数  
            while (intTmp < 0) {  
                intTmp = intTmp + 256;  
            }  
            // 小于0F的数需要在前面补0  
            if (intTmp < 16) {  
                sb.append("0");  
            }  
            sb.append(Integer.toString(intTmp, 16));  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 
     * 互为可逆的转换过程 
     *  
     * @param strIn 
     *            需要转换的字符串 
     * @return 转换后的byte数组 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     * @author <a href="mailto:zhangji@aspire-tech.com">ZhangJi</a> 
     */  
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {  
        byte[] arrB = strIn.getBytes();  
        int iLen = arrB.length;  
  
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2  
        byte[] arrOut = new byte[iLen / 2];  
        for (int i = 0; i < iLen; i = i + 2) {  
            String strTmp = new String(arrB, i, 2);  
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
        }  
        return arrOut;  
    }  
  
    /** 
     * 3desAlgorithm 3des加密算法，可用 DES,DESede,Blowfish 
     */  
    public String desAlgorithm;  
  
    /** 
     * keyString 加密密钥 
     */  
    String keyString;  
  
    public TripleDesUtil() {  
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
    }  
  
    /** 
     * decrypt 3DES单参数解密函数 
     *  
     * @param src 
     *            byte[] 
     * @return byte[] 
     * @throws Exception 
     */  
    private byte[] decrypt(byte[] src) throws Exception {  
        byte[] keybyte = keyString.getBytes();  
        // 生成密钥  
        Key deskey = getKey(keybyte);  
        Cipher decryptCipher = Cipher.getInstance(desAlgorithm);  
        decryptCipher.init(Cipher.DECRYPT_MODE, deskey);  
        return decryptCipher.doFinal(src);  
    }  
  
    /** 
     * decrypt 3DES单参数解密函数 
     *  
     * @param src 
     *            String 
     * @return String 
     * @throws Exception 
     */  
    public String decrypt(String src) throws Exception {  
        byte[] srcBytes = TripleDesUtil.hexStr2ByteArr(src);  
        return new String(this.decrypt(srcBytes));  
    }  
  
    /** 
     * encrypt 3DES单参数加密函数 
     *  
     * @param src 
     *            byte[] 输入字符数组 
     * @return byte[] 返回字符数组 
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws Exception 
     */  
    public byte[] encrypt(byte[] src) throws NoSuchPaddingException,  
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,  
            IllegalBlockSizeException, Exception {  
        byte[] keybyte = keyString.getBytes();  
        // 生成密钥  
        Key deskey = getKey(keybyte);  
        // 加密  
        Cipher c1 = Cipher.getInstance(desAlgorithm);  
        c1.init(Cipher.ENCRYPT_MODE, deskey);  
        return c1.doFinal(src);  
    }  
  
    /** 
     * encrypt 3DES单参数加密函数 
     *  
     * @param src 
     *            String 
     * @return String 
     * @throws Exception 
     */  
    public String encrypt(String src) throws Exception {  
        byte[] srcBytes = src.getBytes();  
        return TripleDesUtil.byteArr2HexStr(this.encrypt(srcBytes));  
    }  
  
    public String getDesAlgorithm() {  
        return desAlgorithm;  
    }  
  
    /** 
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位 
     *  
     * @param arrBTmp 
     *            构成该字符串的字节数组 
     * @return 生成的密钥 
     * @throws Exception 
     */  
    private Key getKey(byte[] arrBTmp) throws Exception {  
        // 创建一个空的8位字节数组（默认值为0）  
        byte[] arrB = new byte[8];  
        // 将原始字节数组转换为8位  
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {  
            arrB[i] = arrBTmp[i];  
        }  
        // 生成密钥  
        Key key = new SecretKeySpec(arrB, desAlgorithm);  
        return key;  
    }  
  
    public String getKeyString() {  
        return keyString;  
    }  
  
    public void setDesAlgorithm(String desAlgorithm) {  
        this.desAlgorithm = desAlgorithm;  
    }  
  
    public void setKeyString(String keyString) {  
        this.keyString = keyString;  
    } 

    // 解密函数 
    public static String tripleDesDecode(String dataEncode) throws Exception{  
    	TripleDesUtil des = new TripleDesUtil();  
        // 设置算法  
        des.setDesAlgorithm("DES");  
        // 设置密钥  
        des.setKeyString(KEY);
        //byte[] bytes = des.decrypt(Base64.decode(URLDecoder.decode(dataEncode,"UTF-8")));
          
        return null; //new String(bytes,"UTF-8");
    }
    
    // 加密函数
    public static String tripleDesEncode(String dataDecode) throws Exception {
    	TripleDesUtil des = new TripleDesUtil();  
        // 设置算法  
        des.setDesAlgorithm("DES");  
        // 设置密钥  
        des.setKeyString(KEY);
        // 去除base64加密后的空格，换行和回车符
        //String base64Encode = Base64.encode(des.encrypt(dataDecode.getBytes("UTF-8"))).replaceAll("\\s*|\r|\n|\t", "");
        //String dataEncode = URLEncoder.encode(base64Encode, "UTF-8");
        
        return null;// dataEncode;
    }
    
    // 测试加密解密
    public static void main(String[] args) throws Exception {
//    	String dataEncode = "hki%2FWTh6HeiK%2FPH%2B5dzUOfV4UOqADhCNLJBy31ezTAy2wBlbmB2oV%2BN07yI0C5laB2sM4MhKkCuFx6%2BTmCkz4YYINF%2F%2B29OHT836vnBRjfkMT5a%2Bb51LbXzMdbJ5Wc4cQL4YIMBg9jdqKcNz380%2Bkttp%2FHezKLCm";
		String dataDecode = "amount=50&mobile=15811522567&period=3&realname=张三&scureid=1327918431238481364781&send_time=1498549469&sid=asdcas";
    	System.out.println("dataDecode="+tripleDesEncode(dataDecode));
    	
//    	String data = "mobile=13918144444&realname=张三&scureid=4211154545211112545&amount=1000&period=1&send_time=54554222222&sid=2469854256&source_device=1";
    	
//    	System.out.println(tripleDesEncode(data));
//    	System.out.println(CheckSign.checkSignKey(data));
//    	String string = "hYzKxSBl8SnH/Xs5BuAOJhTBK35fcl560f3pQTtdQYeC1hJKDGdQTna62dJwe50+b/w5d1a0vhwH9qMAdqGzkT0hE/EVK0DJY9kBU9wgjXYqAZjv33DirjvKRc9AyTdMZBurWGTXS5cOMfEsojTxt0c6PdXR4PaPw4GhbUh/b9eJIrxV+yPG8DbJPvWCKpK9";
//    	System.out.println(URLEncoder.encode(string));
    	System.out.println(tripleDesDecode("yMuYB5rYJSVk3R82iwxm0ecVevns42muXcFutpSU6aVcUC4LRukEgYah8eKZmZPRE8toUTXf4tQjryJT%2FZBwTeiXcUZJIzWm3leasWGgwZwgd2RhD7MYcD0Ca1pBGRioJyTp0GwY%2FKbM1vw9fas7NYG4WGdgmutv"));
    	
//    	String data = "sid=249957445117417&send_time=1221545643";
//    	System.out.println(tripleDesEncode(data));
//    	System.out.println(CheckSign.checkSignKey(data));
//    	System.out.println("http://106.14.238.126:8092/beadwalletloanapp/app/cashLoan/pullOrderInfo.do?appid=123456&signkey="+CheckSign.checkSignKey(data)+"&data="+tripleDesEncode(data));
	}
}  