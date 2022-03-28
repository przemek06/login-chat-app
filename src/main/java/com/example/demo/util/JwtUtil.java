package com.example.demo.util;

import com.example.demo.entity.UserEntity;
import com.example.demo.model.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "secret";

    public static Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(UserDetailsImpl u) {
        Claims claims = Jwts.claims().setSubject(u.getUserEntity().getUsername());
        claims.put("active", u.getUserEntity().isActive() );
        claims.put("roles", u.getUserEntity().getRoles());


        return Jwts.builder()
                .setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public static UserDetails parseToken(String token) {
        try {
            if(isTokenExpired(token)) return null;

            Claims body = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();


            return new UserDetailsImpl(
                UserEntity.builder().username(body.getSubject())
                    .roles((String) body.get("roles"))
                    .active((Boolean) body.get("active")).build()
            );

        } catch (JwtException | ClassCastException | NullPointerException e) {
            return null;
        }
    }

}
