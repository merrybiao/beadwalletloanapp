package com.waterelephant.drainage.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.waterelephant.drainage.util.youyu.DesService;
import com.waterelephant.drainage.util.youyu.DesServiceImpl;
import com.waterelephant.drainage.util.youyu.RsaService;
import com.waterelephant.drainage.util.youyu.RsaServiceImpl;

public class TestYouyu {
	
	
	
	
	
	
	
	
	
	/*private static String sxfqPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfsW2GTUSEi1mM6uYkTWwQV0M6hPuPufSHGKCNVivVImM2huq7AAK/z+Kj+F9MzhcLlfSg80hEyJdPZz56CdNJRvfialrEif8A8Kvoc4cpUUENUIkwA977lM1Tjh3JHEYOLSFgpW7ciVn57pADqx5hXfwndrj2F1H23tonZt8qCwIDAQAB";
	    private static String sxfqPriKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN+xbYZNRISLWYzq5iRNbBBXQzqE+4+59IcYoI1WK9UiYzaG6rsAAr/P4qP4X0zOFwuV9KDzSETIl09nPnoJ00lG9+JqWsSJ/wDwq+hzhylRQQ1QiTAD3vuUzVOOHckcRg4tIWClbtyJWfnukAOrHmFd/Cd2uPYXUfbe2idm3yoLAgMBAAECgYBKoV6CJTtL4VYCLsiTqgT4urRiTMXFIOxVOkEuhmZfWHazVXXw58MDDa92t0HMVSHZKrGQFTqyQB5DTpXIj4ie1Uqx/IRYsXWqBJtiu/tfeBP3TeLvKC9Ok4DaW6gWkclJY94L3REpYAemEfWGpGXcyA+92q3/S1MCggpzq2gvQQJBAPPZ+LwAIkDRKty5nCbXDRX9HHA8yr44ECPEXH9Bo/lCCXiAnrKKmuywy7jvD5+cClQIjeI7P95HuncO89ic0KsCQQDq1lxWSu5GP3CWhf48k4B4z3OTnlpA1aNkNaIt5Kd/WSuNNog82iKeOr9cWj2JzLhbwC2vGAa8hQWcRTpNIMwhAkEAoZG4A+DW0TmF+8+jjfyJOBuH3zn4X4bH8trlKpA0dC/0FHqsK8I4mkEeCn+jS27ZTQqJ7+6wB7hg7QfkO47TlwJBALDI3oo3rlhDucYW6OVBRR2tThx6wQa9zVrK5WQXFeDQh/CudLEapaToJNapOFpcOw0XB00gkTGqEicqFlGptwECQAs6BfTxgRryA/liP1yGtnpcVlPe9w+R6kRVkGwAE1pS9kSQSTNl0NikVsv6Q4a9ESGHnerw7byNqALjR7l+ytk=";
	    private static final String TEST_URL ="http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";

	String YOUYU_APP_ID_PRIVATE_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALm6oSiEFjzLcDcN775liqYUsp8M+opQZ4bAF08bT65skj+CFJjMijCPQEMYEvk7wcp+lOLaG8xmfEl7e+SWyXbhvPJuoOhwwLkOLIQ+FzA1l2LUUQ50amTE7po/fCFjc9ILDC4cNWQ4QsmkD1heL2iw9yGcTPq4SThnL8l6YwGlAgMBAAECgYEAoHnDGbDXTfwxn0ApcAdofwvPMmIScEP8z49WFIHynTEuRDVu+n9wv3hm/BHnAvZBAWfR2Ri/gCLlLNDHQcGPm/EXVrHw8WOZ0/Vm+7c8eLI7eONwrQx21D/IPANxr+2Ro+0SY7xj2qr6kZ2jeC/RpTE6KM/PqTq9FQqxsbMUSkECQQDmgTIRsJa3P1q0Q+DehzkvmcAAXd8IOWFloWM8BEYbIrGugTTFtBG4BnOdx+ecWdTUm3B2yJNy6TRWEnU4qDwZAkEAzkWZUJktdKDq1jzwTw9tYI1wxwyeGRImfOAG+duuQf1Wp3h/enBmesAy3n/VuI7ktV0oFRFsYwfcB+XNQBojbQJBAMsnuRpQOjWdt/LDFJDsO2GRr+Bi26yi7g2ltRFzbtqBtHpp1DofX7KCtSmtw5tQrT0JFpEKbLJNiVwG/puF4xkCQQCclWz7KCy/emCK3Zu3CIkJYJWaJNPnSEX7Sb1B8yfnZD9hxjFPawAAcuKq0PQWtDKeEueYhq92xcKcJDCZVr5JAkEAoOFoOMKmoyezEYgeStGCyuw3gyqJEyUaK/S9u5PsEypVyxjKJhY4dnuhpQILYturFQQoQ9us5+bm7teNhgvNvw==";
	
	String YOUYU_APP_ID_PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5uqEohBY8y3A3De++ZYqmFLKfDPqKUGeGwBdPG0+ubJI/ghSYzIowj0BDGBL5O8HKfpTi2hvMZnxJe3vklsl24bzybqDocMC5DiyEPhcwNZdi1FEOdGpkxO6aP3whY3PSCwwuHDVkOELJpA9YXi9osPchnEz6uEk4Zy/JemMBpQIDAQAB";


	    @Test
	    public void testRsa1(){
        RsaService rsaService = new RsaServiceImpl(YOUYU_APP_ID_PRIVATE_KEY,sxfqPubKey,RsaService.PKCS8);
        DesService desService = new DesServiceImpl();
        String key = desService.getRandomDesKey(16);
        String hello = "hello world";
        String des_encrypt_data  = desService.desEncrypt(hello,key);
        String rsa_encrypt_deskey = rsaService.encrypt(key);
        String sign = rsaService.generateSign(des_encrypt_data);
        System.out.println("des_key===>"+rsa_encrypt_deskey);
        System.out.println("sign===>"+sign);
        System.out.println("data===>"+des_encrypt_data);
	        
	        
	        
	        

	        DesService desService1 = new DesServiceImpl();
	        RsaService sxfqRsaservice = new RsaServiceImpl(sxfqPriKey,YOUYU_APP_ID_PUBLICKEY,RsaService.PKCS8);
	        boolean f = sxfqRsaservice.verifySign(sign,des_encrypt_data);
	        String keys = sxfqRsaservice.decrypt(rsa_encrypt_deskey);
	        String sxdata = desService1.desDecrypt(des_encrypt_data,keys);
	        System.out.println("keys ==>"+keys);
	        System.out.println("sxdata ==>"+sxdata);
	        System.out.println("sign ==>"+f);
	    }*/
	
	
	
	
	
	
	
	
	
	
	
/*	@Test
	public void testRsa() {
		
		RsaService rsaService = new RsaServiceImpl(YOUYU_APP_ID_PRIVATE_KEY, YouyuConstant.SHUIXIANG_PUBLICKEY,
				RsaService.PKCS8);
		DesService desService = new DesServiceImpl();
		String key = desService.getRandomDesKey(16);
		System.out.println(key);
		String hello = "hello world";
		String des_encrypt_data = desService.desEncrypt(hello, key);
		String rsa_encrypt_deskey = rsaService.encrypt(key);
		String sign = rsaService.generateSign(des_encrypt_data);
		System.out.println("des_key===>" + rsa_encrypt_deskey);
		System.out.println("sign===>" + sign);
		System.out.println("data===>" + des_encrypt_data);
		
		
		RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY, YouyuConstant.SHUIXIANG_PUBLICKEY,
				RsaService.PKCS8);
		boolean f = sxfqRsaservice.verifySign(sign,des_encrypt_data);
        String keys = sxfqRsaservice.decrypt(rsa_encrypt_deskey);
        String sxdata = desService.desDecrypt(des_encrypt_data,keys);
        System.out.println("keys ==>"+keys);
        System.out.println("sxdata ==>"+sxdata);
        System.out.println("sign ==>"+f);
		
		DesService desService1 = new DesServiceImpl();
		String decrypt = rsaService1.decrypt(rsa_encrypt_deskey);
		
		System.out.println("key===>"+decrypt);
		desService1.desDecrypt(des_encrypt_data, decrypt);
		
		
	}*/

}
