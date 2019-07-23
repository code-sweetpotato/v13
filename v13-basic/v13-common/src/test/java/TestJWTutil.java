import com.qf.v13.pojo.ResultBean;
import com.qf.v13.utils.JWTUtil;
import org.junit.Test;

import java.util.UUID;

public class TestJWTutil {

    @Test
    public void testJwt(){
        String uuid = UUID.randomUUID().toString();
        String subject = "工具类封装";
        String password = "zhangsan123";
        String token = JWTUtil.produceToken(uuid, subject);
        System.out.println("得到的token是"+token);

    }
    //String = eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2OWQ1NjU0Yi1iYzdhLTQzODQtYjQxYy1mNzk0NGIzMTlkNDYiLCJzdWIiOiLlt6XlhbfnsbvlsIHoo4UiLCJpYXQiOjE1NjE3MTI4MzUsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU2MTcxNDYzNX0.tO7iy5ROWAYclXnUVgUAq7-JH4d43ZhV4oHAj9DmKzw
    //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4YTcwN2Q4NC03NzEyLTQ1MGEtYjczOS1jYmU2OWY3NTBhYjYiLCJzdWIiOiLlt6XlhbfnsbvlsIHoo4UiLCJpYXQiOjE1NjE3MTM4NTIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU2MTcxNTY1Mn0.MnToUd1_Owzfz7ZYdVaYulI62BDCICDTHLbHs7LvjZ4
    @Test
    public void testJwt2(){
        /*String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4YTcwN2Q4NC03NzEyLTQ1MGEtYjczOS1jYmU2OWY3NTBhYjYiLCJzdWIiOiLlt6XlhbfnsbvlsIHoo4UiLCJpYXQiOjE1NjE3MTM4NTIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU2MTcxNTY1Mn0.MnToUd1_Owzfz7ZYdVaYulI62BDCICDTHLbHs7LvjZ4";
        ResultBean resultBean = JWTUtil.parseToken(token);
        System.out.println(resultBean.getStatusCode());
        System.out.println(resultBean.getData().toString());*/

    }

}
