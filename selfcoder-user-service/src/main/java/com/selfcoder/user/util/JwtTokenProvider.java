package com.selfcoder.user.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.selfcoder.user.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}") // 1 hour by default
	private long validityInMilliseconds;

	private Key key;

	@PostConstruct
	public void init() {
	    try {
	        String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\r\n"
	        		+ "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCb0w30SY+s95wz\r\n"
	        		+ "/dc/dqV79W6GXCt7utoJJpMZw5qLzlovsMlCDyPEd3BCRuYfz8ccoZam1LIkqJnh\r\n"
	        		+ "jKBDGwjeH2tW/oPERur6/ghnXVzstai/2V6nAiCCW8BYnp0ig6Dx9yDNf9FJv7nW\r\n"
	        		+ "DEj1EkGSNc+Qs3zITboKh68W5NuKrCLk4HO1j/bhirUhUAsr16IvBQfkCCA/e4VI\r\n"
	        		+ "APNIfyJ3sp4S6263pdxbWQDjJ6U7O7jdTcVDynaSpWYId8mDLxLIoi8HT3HyzSN6\r\n"
	        		+ "oMDpv6UGq+7Bf4MQrtItT261OEpkfvbmdYqKYm/hiszBynpA3wlySqwfNDiteM6X\r\n"
	        		+ "KcniIeYlAgMBAAECggEBAJZyOHfSiih6zNKZ07ePawj6luKcnNMSPa82EjJ+23j+\r\n"
	        		+ "G68N5dKk4Wiv1K/42fh+2IQ5M0YR94lTS3csGhOQR4iGF1Hf3AKmYftBR4Xc63oT\r\n"
	        		+ "jkDksvFWKWMTCOvHAHmxUrQrYP1n2f51YQxbwhjzrmFnGOdhlCmkeenuxMq63YjN\r\n"
	        		+ "VgcNX/zdCTwQh+4prKHwY9RQgweGsqcTxu5hRlCSXUclu80cHh1Zt9WqMVS0l7vp\r\n"
	        		+ "+oQhPSzhSP7JsgFSus2pWAhPGxp539Zj3IPnAvmYHN1e14tbe3RZT5KltMAVfsff\r\n"
	        		+ "dopvmQ9qjd8fHK5WYhZZmxwoGo71Yl2uOUKfATr3geUCgYEAy8XAJ9FVSDoXMyq3\r\n"
	        		+ "K5M61B7gy+kNdgW3RpWznqqAp1X8zNAiD/vp7CyIdsYnSnUOzifHHcMPQH3DR5n1\r\n"
	        		+ "fTOcI8FYExX+L73OA2yXXHVh7L0ewHgwutGLRaUOhoOD1u+UG4YCGL2nnzPnsWIU\r\n"
	        		+ "BZa+Qnk2GFqFi7Qep1rSZozthrMCgYEAw8NChy+vE419c8zQO73UfRhTKyll6T+c\r\n"
	        		+ "bxNu/jsvG8LiTCloTTJiA8qdjMaegBUt+JcDaQv/xLR22kJj58EQIAdpCbGPKqrU\r\n"
	        		+ "H1kHMSSS546N6M51wrFAo4hwQwk7wqX43bD+q/ZpVcFMyS5VROJmEX59pgwPIpUB\r\n"
	        		+ "S369j+K5i8cCgYAdO793duafOAZABu7ER5j2+X8Sgdq2hddnPu7H4noWh5V+5JUe\r\n"
	        		+ "aeuDQFhLgd39JpH7LWW5dVRo0iID0aiQT6kk0AYMpukSfE5j215Tm1tzFSamQhfr\r\n"
	        		+ "lUg/s5BD5/YwDarXz//ZAMxdYJrqrcFlk9yzuKP0Fnh2NF75b2Qj5i/lVQKBgHyN\r\n"
	        		+ "5exhmIFWSvW84NjCBUq6iWAy7NalVIEP2lUDjwuYyacvyV8kqVxENE33qa1QzHkY\r\n"
	        		+ "jGDv84JSWDSyfSJdokbesCQsxyycmG6BlKPd42JVP1Fa225OnRPvoY3leBJ2y4sy\r\n"
	        		+ "LDd5hre7T4HJaxNzAQIsewGFNrQIHlTMhQvhc7DNAoGBALzni90rucBGR7X/SwJI\r\n"
	        		+ "TS9opmKVtEtATidNYtepJjATh6eUgM8y+7cba96FZs4cGUE9j+NQNmPtAZuyXpTJ\r\n"
	        		+ "bYRjzpKXCRL97K1JVpHMCuBkRQWFUDAutfjq5rnrx1rjdTI+U+gj/vsLgf0AozPY\r\n"
	        		+ "gyIXWFjR8XyR8ReDe2XpCjCv\r\n"
	        		+ "-----END PRIVATE KEY-----\r\n"
	        		+ "";

	        byte[] keyBytes = Base64.getMimeDecoder().decode(privateKeyPEM.replaceAll("\\n", "")
	                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
	                .replaceAll("-----END PRIVATE KEY-----", ""));

	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        this.key = kf.generatePrivate(spec);
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	        // Handle exceptions
	    }
	}
//	byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
//	var keyFactory = KeyFactory.getInstance("RSA");

	public String createToken(String username, List<Role> roles) {
	    try {
	        Claims claims = Jwts.claims().setSubject(username);
	        claims.put("roles", roles.stream().map(Role::getRoleName).collect(Collectors.toList()));

	        Date now = new Date();
	        Date validity = new Date(now.getTime() + validityInMilliseconds);

	        return Jwts.builder()
	                   .setClaims(claims)
	                   .setIssuedAt(now)
	                   .setExpiration(validity)
	                   .signWith(this.key) // or RS384, RS512 if using a different algorithm
	                   .compact();
	    } catch (Exception e) {
	        // Handle the exception appropriately
	        throw new RuntimeException("Error generating JWT token", e);
	    }
	}
}
