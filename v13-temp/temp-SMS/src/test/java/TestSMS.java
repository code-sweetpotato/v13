import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qf.v13.utils.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestSMS {



    @Test
    public void testSMS() {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIGw2iajg4GSoO", "p86C32Q4K23MFvINPG333Fk7H53zX3");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "15773261573");
        request.putQueryParameter("TemplateCode", "SMS_168827509");

        request.putQueryParameter("TemplateParam", 	"{'code':'6666'}");
        request.putQueryParameter("SignName", "巨萌");


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
