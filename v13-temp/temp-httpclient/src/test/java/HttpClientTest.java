
import com.sun.beans.editors.ByteEditor;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientTest {


    @Test
    public void grepTest(){
        //创建浏览器
        CloseableHttpClient client = HttpClients.createDefault();
        String path = "http://localhost:9093/item/createHTMlById/1";

        HttpGet get = new HttpGet(path);
        try {
            CloseableHttpResponse response = client.execute(get);//生成回应
            //得到回应编码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                //获取内容
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                byte[] bs =new byte[1024];//准备字节数组,读取的内容放在数组中
                int len ;
                while ((len = content.read(bs)) != -1){
                    System.out.println(new String(bs, 0, len));
                }

            }else {
                System.out.println("请求失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求地址不存在");
        }

    }


    //封装一个工具类,共外部使用
    public static String doGet(String path,Map<String,Object> params){
        //String path = "http://localhost:9093/item/createHTMlById/";
        CloseableHttpClient client = HttpClients.createDefault();
        String result = "";
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(path);
            if(params != null){
                Set<Map.Entry<String, Object>> set = params.entrySet();
                for (Map.Entry<String, Object> param : set) {
                    builder.addParameter(param.getKey(),param.getValue().toString());
                    
                }

            }
            URI uri = builder.build();
            HttpGet get = new HttpGet(uri);
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode() ==200){
                 result = EntityUtils.toString(response.getEntity(),"utf-8");
            }else {
                result = EntityUtils.toString(response.getEntity(),"utf-8");
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            if(response !=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }


    public static String doGet(String path){
        return doGet(path,null);
    }


    public static String doPost(String path,Map<String,Object> params){
        //创建服务器
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(path);
        String result ="";
        CloseableHttpResponse response = null;

        try {

           
            List<NameValuePair> list = new ArrayList();
            if(params != null){
                for (String key : params.keySet()) {
                    list.add(new BasicNameValuePair(key,params.get(key).toString()));
                }
                UrlEncodedFormEntity form = new UrlEncodedFormEntity(list);
                post.setEntity(form);
            }
            response = client.execute(post);
            result = EntityUtils.toString(response.getEntity());


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    public static String doPost(String path){
        return doPost(path,null);
    }






}
