package com.sai.incubation.IotConnector.utility;

import static com.sai.incubation.IotConnector.constants.SecurityConstant.AUTHORITIES;
import static com.sai.incubation.IotConnector.constants.SecurityConstant.EXPIRATION_TIME;
import static com.sai.incubation.IotConnector.constants.SecurityConstant.IOT_BLENDS_LLC;
import static com.sai.incubation.IotConnector.constants.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sai.incubation.IotConnector.domain.Common.UserPrincipal;

/*import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;*/

@Service
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

    /*public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }*/
	
	public String generateJwtToken(UserPrincipal userPrincipal) {
		String token = "";
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			String[] claims = getClaimsFromUser(userPrincipal);
			token = JWT.create().withIssuer(IOT_BLENDS_LLC).withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
			.withArrayClaim(AUTHORITIES, claims)
			.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(algorithm);
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
			System.out.println("Invalid Signing configuration / Couldn't convert Claims.");
		}
		
		return token;
	}

	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority grantedAuthority: userPrincipal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
	
	public List<GrantedAuthority> getAuthorities(String token){
		String[] claims = getClaimsFromToken(token);
		return Stream.of(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private String[] getClaimsFromToken(String token) {
		// TODO Auto-generated method stub
		JWTVerifier verifier = getJwtVerifier(token);
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJwtVerifier(String token) {
		// TODO Auto-generated method stub
		JWTVerifier verifier;
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(IOT_BLENDS_LLC).build();
		} catch(JWTVerificationException ex) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return userPasswordAuthToken;
	}
	
	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJwtVerifier(token);
		return !StringUtils.isEmpty(username) && !isTokenExpired(verifier, token);
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		// TODO Auto-generated method stub
		Date expirationDate =  verifier.verify(token).getExpiresAt();
		return expirationDate.before(new Date());
	}

	public String getSubject(String token) {
		// TODO Auto-generated method stub
		JWTVerifier verifier = getJwtVerifier(token);
		return verifier.verify(token).getSubject();
	}
}
