package com.qf.v13payweb.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.alipay.api.AlipayConstants.APP_ID;

@Controller
@RequestMapping("pay")
public class PayController {


    @RequestMapping("test")

    public void doPost(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,String orderNo) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016101100662645",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfI93GK9RrDavXxtb5/aNsucwvRc8oe094z/SSWgGCxhuB0Yz0nNKpi/QVdwnOE6LwUYq4mWB1SSYhlMMLolT9V6DHO/7zEV3zKXeO0YjMMCvLPHqHfm/rubegMzB3/VJJqN/kOBJ5D493axsqpntMXbit934DqSCrVsGxQLmxQFTEHkWEtIwA+RdaiTTLSGb+pCZx5fKT0F5G52kjMPOWISNdjCxgoNnMDi7M2UQG0CvBsVSIVgZtnl/aiq+bgucQlEICu+B1jEfs3kytEwcksGhjTnJJnNJE2NXPf2/Yv4/QyNfo5MG5FLzgYvrL2xOLqlKsGu3PnveWOJytcIHfAgMBAAECggEADlOgR5LykKCuc8KG4AKhj8w6QZc7Sx2uzeWK0xIfSLKlD07aLbtgi7r5wmTxEQhbht8QagYE1Qo80JEf7hAzLouRAd642REbMCTvin/6nMdJagaL0V7knIgfq+Bv4J2Bpvf5EAasQxHPjKSr0W8jcoCNe7voMFQtiL9KuLZnATnxDrtVISiZSM602hiVex2klZnF4fIMsEIkRwa1+e37z/odAOG0T8vTkgEF/UjEaeG7kfC4dWn12uGV9psO7atSjsD4H1tm6CwZxU8xTab3b6d0Bx0nQp0zYv66aY1sp3wOE9rIL83a+LDKShi0MZi9p3uX9qCiCATpwii1BNCd4QKBgQDNP0ZnFqlwN8L63HaHMXrYbgCTeBhc9kH8YCeqs1kzAI3UAcO6Uca6jTzgPMNGOoJNP/aqlKbdeVEGYpeECgMMbwwf7rYeaPSxjaQEfHb2NHLCUcpxzM6kCcj7HgkpLLioUX+7wejsHThKHi8m7olez2pZVQAz63a9BviaYZqq1QKBgQDGfeJJ134Shr0Rhf6QSEShsHi6NmDSb0Q/1UNukmIA48RifmCa8/HA7Fnl+uD7bUYraGB7hNkxbBUUYjXsiOW5N65da904b2P0uiGF8LpkTogelKzb4GiPwzbYE+CSelu0/tB8BLQ1ISo/QoGoBxs9qfjWtKCDBKiV5FxVtlRr4wKBgHGjvRlK5NmrJhu7gSztanT2wv7jmamu924C1zOeRZ8FiYF+0J4StROJK9GEEyKJNqgK6EoeMB2kFAaTBA0pdfKmcXIku1UvC3xbThrlxwAengOVQZ+spCUS/eJ8CNpxVnRrW2vbu1kv/ICdIcJOhGSoNtvrQkd88atLds4TiI1ZAoGBAMM97+hSX2A5yKAD/XJOo7Onshj4hCR57dgcHkgVFPFrGTEIvl2sto/d7E+bhtHJmsv3z2+wLTO/7LwwHnqb7dtSOeEKyH9KkjeXHoJxV+ZymNH7zEw7YJ6IltnUM4HheXN/cfOLbPcVuEihUofE9baiCnKnGXvGiU1nYqusvT2hAoGAGwbOfnecfTSA/86pgtH18eGNY871IqNJ1sHe4Gj3JT+RRkffXsMjN1rdvQIiKPvicYuoSxUa64iTE7O2G2jo43BvaPWmAZN2tpM+68Wmb/GvBo1buHuABmRwFs0B8rIlA9UXnXkEoprBiJC5SXEIlMbdgFAen97wV5CQ3kBgm/0=",
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjgN9eaEC3wxqiBNhNclv9NuF61avxoi9iRimRyD3F/cu68R+x9ODn+bLAiwJhiLf2fVUW+SSTC6GayFJgmMCAZS+avR+8rNjxd13pi7/NOSl5XaAv6hmZUAxIo3m4sqc9U7URERCUOvbU3Qp0hhv4aiEVDI4vcQ3c0Tvsbbnuwzc1jhyM2l0F6eJB70i2qax0qKX9JF/qU26mLphfSK5FfVZcoWcrQBAb4+uL+RrDCiJIcYMu7mnXXJNwVVXdr6AKgg5uFt03aJkxJCQnIgD6OEXPQygqacji7ScsoDBUNdfXMDf+opp8nap+FCN6dOvWuXD+AszGbD/bdu9PwElhQIDAQAB+f2jbLnML0XPKHtPeM/0kloBgsYbgdGM9JzSqYv0FXcJzhOi8FGKuJlgdUkmIZTDC6JU/Vegxzv+8xFd8yl3jtGIzDAryzx6h35v67m3oDMwd/1SSajf5DgSeQ+Pd2sbKqZ7TF24rfd+A6kgq1bBsUC5sUBUxB5FhLSMAPkXWok0y0hm/qQmceXyk9BeRudpIzDzliEjXYwsYKDZzA4uzNlEBtArwbFUiFYGbZ5f2oqvm4LnEJRCArvgdYxH7N5MrRMHJLBoY05ySZzSRNjVz39v2L+P0MjX6OTBuRS84GL6y9sTi6pSrBrtz573ljicrXCB3wIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orderNo+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + "utf-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }
}
