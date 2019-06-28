import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class TestJWT {

    @Test
    public void testCreateToken(){
        JwtBuilder jwtBuilder = Jwts.builder().setId("111")
                .setSubject("jwt测试")
                .setIssuedAt(new Date())
                .claim("role", "admin")
                .setExpiration(new Date(new Date().getTime() + 1000 * 120))
                .signWith(SignatureAlgorithm.HS256,"zhangsan123");//设置算法和私钥

        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);


    }

    @Test
    public void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTEiLCJzdWIiOiJqd3TmtYvor5UiLCJpYXQiOjE1NjE2OTM4MTQsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU2MTY5MzkzNH0.Nrxnu5YCj9s4gXF0oob76_Imk0rHvJxtP3khDfNaEjQ";
        Claims claims = Jwts.parser().setSigningKey("zhangsan123")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
        System.out.println(claims.get("role").toString());
        claims.setExpiration(new Date(new Date().getTime()+120*1000));


    }
}
