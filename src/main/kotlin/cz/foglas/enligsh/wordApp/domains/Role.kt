package cz.foglas.enligsh.wordApp.domains

import org.springframework.security.core.GrantedAuthority

class Role(
    val authorityName: String
) : GrantedAuthority {


    override fun getAuthority(): String {
        return authorityName
    }
}