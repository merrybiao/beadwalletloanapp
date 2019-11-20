package beadwalletloanapp.mytest;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.utils.OkHttpUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestAsync {
	
	public static void main(String[] args) {
		for(int i=1;i<10000;i++) {
			String path = "http://localhost:8080//TestAsyc";
			JSONObject params = new JSONObject();
			params.put("name", "章大话"+i);
			params.put("id", i);
			OkHttpUtil.asyncPost(path, params.toJSONString(), new Callback() {
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					  String jsonString = response.body().string();
					   JSONObject jsonObject = JSON.parseObject(jsonString);
				        String code = jsonObject.getString("code");
				        System.out.println("----------------"+response);
				        System.out.println("++++++++++++++++++++++++"+jsonString);
				}
				
				@Override
				public void onFailure(Call call, IOException e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
	}
	
	public static void main2(String[] args) throws IOException {
		 
		  long start = System.currentTimeMillis(); 
		  for(int i =0;i<100000;i++) { 
			 String path ="http://localhost:8080//TestAsyc"; 
		    CloseableHttpClient httpClient = HttpClients.createDefault();
		    // 创建一个 GET 请求
		    HttpPost httpGet = new HttpPost(path);
		    // 执行请求
		    CloseableHttpResponse response =httpClient.execute(httpGet);
		    //取响应的结果
		    int statusCode =response.getStatusLine().getStatusCode();
		    System.out.println(statusCode);
		    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
		    System.out.println(content);
		    //关闭httpclient
		    response.close();
		    httpClient.close();
	    }
	}
}
