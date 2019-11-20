//
//package com.waterelephant.sxyDrainage.utils.kabao;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.entity.BwKabaoPhoneTagInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.PhoneTagInfo;  
//
///**
// * ClassName:CopyBeanPluginsUtil <br/>
// * Function: TODO  <br/>
// * @author   liwanliang
// * @version   1.0  
// */
//public class CopyBeanPluginsUtil {
//
//	 /** 
//     * 父类DTO对象转换为实体对象。如命名不规范或其他原因导致失败 
//     * @param t 源转换的对象 
//     * @param e 目标转换的对象 
//     *  
//     */  
//    public static <T,E> void transalte(T t,E e){  
//        Method[] tms=t.getClass().getSuperclass().getDeclaredMethods();  
//        Method[] tes=e.getClass().getSuperclass().getDeclaredMethods();  
//        for(Method m1:tms){  
//            if(m1.getName().startsWith("get")){  
//                String mNameSubfix=m1.getName().substring(3);  
//                String forName="set"+mNameSubfix;  
//                for(Method m2:tes){  
//                    if(m2.getName().equals(forName)){  
//                        // 如果类型一致，或者m1的返回类型是m2的参数类型的父类或接口  
//                        boolean canContinue = m1.getReturnType().isAssignableFrom(m2.getParameterTypes()[0]);  
//                        if (canContinue) {  
//                            try {  
//                                m2.invoke(e, m1.invoke(t));  
//                                break;  
//                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {  
//                                // TODO Auto-generated catch block  
//                                e1.printStackTrace();  
//                            }  
//                        }  
//                    }  
//                }  
//            }  
//        }  
//    }  
//      
//      
//    /** 
//     * DTO对象转换为实体对象。如命名不规范或其他原因导致失败 
//     * @param t 源转换的对象 
//     * @param e 目标转换的对象 
//     *  
//     */  
//    public static <T,E> void transalte2(T t,E e){  
//        Method[] tms=t.getClass().getDeclaredMethods();  
//        Method[] tes=e.getClass().getDeclaredMethods();  
//        for(Method m1:tms){  
//            if(m1.getName().startsWith("get")){  
//                String mNameSubfix=m1.getName().substring(3);  
//                String forName="set"+mNameSubfix;  
//                for(Method m2:tes){  
//                    if(m2.getName().equals(forName)){  
//                        // 如果类型一致，或者m1的返回类型是m2的参数类型的父类或接口  
//                        boolean canContinue = m1.getReturnType().isAssignableFrom(m2.getParameterTypes()[0]);  
//                        if (canContinue) {  
//                            try {  
//                                m2.invoke(e, m1.invoke(t));  
//                                break;  
//                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {  
//                                // TODO Auto-generated catch block  
//                                e1.printStackTrace();  
//                            }  
//                        }  
//                    }  
//                }  
//            }  
//        }  
//    }  
//      
//    /** 
//     * DTO对象转换为实体对象。如命名不规范或其他原因导致失败 
//     * 源目标数据为空不转换 
//     * @param t 源转换的对象 
//     * @param e 目标转换的对象 
//     *  
//     */  
//    public static <T,E> void transalte3(T t,E e){  
//        Method[] tms=t.getClass().getDeclaredMethods();  
//        Method[] tes=e.getClass().getDeclaredMethods();  
//        for(Method m1:tms){  
//            if(m1.getName().startsWith("get")){  
//                String mNameSubfix=m1.getName().substring(3);  
//                String forName="set"+mNameSubfix;  
//                for(Method m2:tes){  
//                    if(m2.getName().equals(forName)){  
//                        // 如果类型一致，或者m1的返回类型是m2的参数类型的父类或接口  
//                        boolean canContinue = m1.getReturnType().isAssignableFrom(m2.getParameterTypes()[0]);  
//                        if (canContinue) {  
//                            try {  
//                                if(m1.invoke(t) != null)  
//                                {  
//                                    m2.invoke(e, m1.invoke(t));  
//                                }  
//                                break;  
//                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {  
//                                // TODO Auto-generated catch block  
//                                e1.printStackTrace();  
//                            }  
//                        }  
//                    }  
//                }  
//            }  
//        }  
//    }  
//      
//    /** 
//     * JSONObject对象转换为实体对象。如命名不规范或其他原因导致失败 
//     * @param jsonObject 源json对象 
//     * @param jsonKey json对象key 
//     * @param dtoKey 目标对象属性 
//     * @param clazz 目标转换的class 
//     * @return 
//     */  
//    public static <T> T jsonObj2Dto(JSONObject jsonObject, String[] jsonKey, String[] dtoKey, Class<T> clazz){  
//        for(int i = 0; i < jsonKey.length; i++)  
//        {  
//            if(jsonObject.containsKey(jsonKey[i]) && jsonObject.get(jsonKey[i]) != null)  
//            {  
//                jsonObject.put(dtoKey[i], jsonObject.get(jsonKey[i]));  
//            }  
//        }  
//        return JSONObject.toJavaObject(jsonObject, clazz);  
//    } 
//    
//    
//    
//    public static void main(String[] args) {
//    	
//    	PhoneTagInfo t = new PhoneTagInfo();
//    	t.setCallInCount(1);
//    	t.setCallInTime(2);
//    	t.setCallOutCount(5);
//    	t.setLastCallTime("2018-01-02");
//    	t.setPhone("12345678911");
//    	t.setTag("success");
//    	t.setTotalCount(120);
//    	t.setTotalTime(500);
//    	
//    	BwKabaoPhoneTagInfo e = new BwKabaoPhoneTagInfo();
//    	
//    	transalte3(t, e);
//    	
//    	System.out.println(t);
//    	System.out.println(e);
//	}
//    
//    
//}
//
