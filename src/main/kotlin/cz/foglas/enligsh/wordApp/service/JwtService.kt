package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService {
    val secretKey: String = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b"
    val expiration : Long = 3600000

    fun generateToken(user: User): String{
        return generateToken(emptyMap(), user)
    }

    fun generateToken(claims: Map<String, String>, user: UserDetails):String{
        return buildToken(claims, user)
    }

    private fun buildToken(claims : Map<String, String> ,user: UserDetails): String{
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSignKey(): Key {
       val keyBytes = Decoders.BASE64.decode(secretKey)
       return Keys.hmacShaKeyFor(keyBytes)
    }

    fun isValid(token: String, user: UserDetails): Boolean{
       val username = extractUsername(token)
       return (username.equals(user.username)&& !isExpired(token))
    }

    fun isExpired(token: String): Boolean{
        return extractExpiration(token).before(Date(System.currentTimeMillis()))
    }

    fun extractExpiration(token: String): Date{
     var extractAll = extractAllClaims(token)
        return extractAll.expiration
    }

    fun extractUsername(token: String): String{
        var extractAll = extractAllClaims(token)
        return extractAll.subject
    }

    fun extractAllClaims(token: String): Claims{
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}