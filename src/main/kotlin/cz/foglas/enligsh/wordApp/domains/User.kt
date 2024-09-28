package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*
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
    val email: String

) : UserDetails {
    constructor() : this(null, "", "")



    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    };
}
