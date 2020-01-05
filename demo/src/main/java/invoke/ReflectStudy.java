package invoke;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectStudy {
    public Map<String,Object> query(Map<String,Object> paramMap) throws Exception {
        String queryType = (String) paramMap.get("queryType");
        String methodName = TranferToMethod.getMethodName(queryType);
        Map<String,Object> result = new HashMap<>();
        try {
            Method declaredMethod = ReflectStudy.class.getDeclaredMethod(methodName, Map.class);
            result = (Map<String, Object>) declaredMethod.invoke(this,paramMap);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new Exception("没有此方法");
        }
        return result;

    }

    public Map<String,Object> queryNameList(Map<String,Object> map){
        //编写业务代码返回
        Map<String,Object> result = new HashMap<>();
        result.put("flag","1");
        result.put("data","操作成功");
        return result;
    }

    public Map<String,Object> queryAppointNoList(Map<String,Object> map){
        //编写业务代码返回
        return null;
    }
}
