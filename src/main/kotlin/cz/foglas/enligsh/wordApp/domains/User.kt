package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*
import org.antlr.v4.runtime.atn.SemanticContext.Empty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_detail")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @SequenceGenerator(name = "userGen", sequenceName = "userid", initialValue = 1, allocationSize = 1)
    val id: Long?,

    @Column
    val nickname: String,

    @Column
    val email: String,

    @Column
    private var password: String


) : UserDetails {
    constructor() : this(null, "", "", "")



    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return emptyList<GrantedAuthority>().toMutableList()
    }

    override fun getPassword(): String {
       return password
    }

    fun setPassword(password: String){
        this.password = password
    }

    override fun getUsername(): String {
      return email;
    };
}
