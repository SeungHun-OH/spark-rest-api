package com.spark.dating.member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

  private String strkey = "com.mycompany.backendapi.secret.key";
  private SecretKey secretKey;
  private long jwtDuration = 24 * 60 * 60 * 1000;

  public JwtService() throws Exception {
    byte[] bytes = strkey.getBytes("UTF-8");
    secretKey = Keys.hmacShaKeyFor(bytes);
  }

  public String createJWT(String mid, String memail) {
    JwtBuilder jwtBuilder = Jwts.builder();

    jwtBuilder.subject(mid);
    jwtBuilder.claim("memail", memail);

    jwtBuilder.expiration(new Date(new Date().getTime() + jwtDuration));
    jwtBuilder.signWith(secretKey);

    String jwt = jwtBuilder.compact();
    return jwt;
  }

  public boolean validateJwt(String jwt){
    boolean result = false;
    try{
      JwtParserBuilder jwtParserBuilder = Jwts.parser();
      jwtParserBuilder.verifyWith(secretKey);
      JwtParser jwtParser = jwtParserBuilder.build();
      
      Jws<Claims>jws = jwtParser.parseSignedClaims(jwt);
      result = true;
      } catch(ExpiredJwtException e){
        log.info("기간이 만료된 토큰입니다");
      } catch(io.jsonwebtoken.security.SecurityException e){
        log.info("잘못된 서명입니다");
      } catch(Exception e){
        log.info("토큰을 해석할 수 없습니다");
    } 
    return result;
  }

  public Map<String, String> getClaims(String jwt){
    // jwt를 해석하는 jwtParser 얻기
    JwtParserBuilder jwtParserBuilder = Jwts.parser();
    jwtParserBuilder.verifyWith(secretKey);
    JwtParser jwtParser = jwtParserBuilder.build();

    // Jwt를 해석
    Jws<Claims> jws = jwtParser.parseSignedClaims(jwt);
    Claims claims = jws.getPayload();

    Map<String, String>map = new HashMap<>();
    map.put("mid", claims.getSubject());
    map.put("memail", claims.get("memail").toString()); 

    return map;
  }

}
