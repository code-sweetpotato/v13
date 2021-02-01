package MyTest;

import invoke.ReflectStudy;
import invoke.TranferToMethod;
import work.EnumStudy;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MyTest {
    public static void main (String[] args ) throws Exception {
      /*  String s = "Hello World";
        //将其以空格分开
       // String[] result = s.split(" ");
        int index = 0;
        char[] result = s.toCharArray();
        System.out.println(result[5]);
        for (int i = 0;i<result.length;i++){
            if(result[i] == ' '){
                System.out.println(i);
                index = i;
                break;
            }
        }
        char[] c1 = s.toCharArray();

        char[] r1 = new char[result.length-index-1];
        for (int j=0;j<result.length-index;j++){
            r1[j] = c1[result.length-index-j-1];
        }
        System.out.println(new String(r1));*/

      //枚举类测试
       // EnumStudy s = new EnumStudy("nihao");
    /*    String s = "";//入参
        EnumStudy enumStudy = EnumStudy.valueOf("NIHAO");
        enumStudy.op();*/
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("queryType","nameMap");
        ReflectStudy reflectStudy = new ReflectStudy();
        Map<String, Object> query = reflectStudy.query(paramMap);
        System.out.println(query.toString());


    }

    public void runCXDAta(String reportDate){
        Map<String,Object> param = new HashMap<>();
        param.put("P_DATESTR",reportDate);
        param.put("P_PROCESS_CODE","");
        param.put("P_PROCESS_MSG","");
        param.put("result","");
        getSqlMaopClientTemplate().update("runSignDate",param);
        log.info("储存过程返回值P_PROCESS_CODE为"+param.get("P_PROCESS_CODE"));
        log.info("储存过程返回值P_PROCESS_MSG为"+param.get("P_PROCESS_MSG"));

        // 正在使用的类加载器
        ClassLoader c = MyTest.class.getClassLoader();
        // AppClassLoader的父加载器是平台类加载器
        ClassLoader c1 = c.getParent();
        // 使用的是Bootstrap classloader
        ClassLoader c2= c1.getParent();
    }
}
