package com.qf.v13.utils;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {
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


    public static String doPost(String path,Map<String,String> params){
        //创建服务器
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(path);
        String result ="";
        CloseableHttpResponse response = null;

        try {


            List<NameValuePair> list = new ArrayList();
            if(params != null){
                for (String key : params.keySet()) {
                    System.out.println("得到的参数为"+params.get(key));
                    list.add(new BasicNameValuePair(key,params.get(key)));
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
