package com.doan.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.doan.config.Constants;
import com.doan.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(AuthUserDetails userDetails) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.JWT_EXPIRATION * 1000);

        return JWT.create()
                .withIssuer("self")
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withSubject(userDetails.getUser().getUsername())
                .withClaim("user", toMap(userDetails.getUser()))
                .sign(Algorithm.HMAC512(Constants.JWT_SECRET));
    }

    private Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Arrays.stream(Constants.ATTRIBUTIES_TO_TOKEN).anyMatch(field.getName()::equals)) {
                try {
                    if (field.getName().equals("role")) {
                        // Truy cập trực tiếp thuộc tính "role" từ đối tượng User
                        Role role = (Role) field.get(obj);
                        if (role != null) {
                            // Bạn có thể chọn "role_id" hoặc "role_name" tùy ý
                            map.put("roleName", role.getRoleName());
                            // Hoặc map.put("role_name", role.getRoleName());
                        }
                    } else {
                        map.put(field.getName(), field.get(obj));
                    }
                } catch (Exception e) {
                    // Xử lý ngoại lệ (nếu cần)
                }
            }
        }
        return map;
    }


    /**
     * get user from token
     *
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token) {
        return JWT.decode(token).getSubject();
    }

    /**
     * verify token
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            DecodedJWT token = JWT.require(Algorithm.HMAC512(Constants.JWT_SECRET)).build().verify(authToken);

            // check token Expire
            Date expireAt = token.getExpiresAt();
            if (expireAt.compareTo(new Date()) > 0) {
                return true;
            }
        } catch (JWTVerificationException ex) {
            log.error("Invalid JWT token");
        }
        return false;
    }


}
