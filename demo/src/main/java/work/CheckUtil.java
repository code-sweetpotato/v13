package work;

import java.util.Map;

public class CheckUtil {
    public static CheckResult checkParam(Map paramMap){
        CheckResult checkResult = new CheckResult();
        checkResult.setFlag("T");
        //参数map
        Map<String,Object> param = (Map<String, Object>) paramMap.get("param");
        /*入参名称  如：入参为name:zhangsan   age:18  则param为"name",zhangsan
        attribute为"name,age"*/
        String attribute = (String) paramMap.get("attribute");
        String[] split = attribute.split(",");
        for (String key : split) {
            if(null == param.get("key") || param.get("key").toString().length() <0){
                checkResult.setReason("必填参数"+key+"缺失");
                checkResult.setFlag("F");
                return checkResult;
            }
        }
        return checkResult;
    }
}
