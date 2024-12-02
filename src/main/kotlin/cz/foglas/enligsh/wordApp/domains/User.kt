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
    val email: String,

    @Column(name = "password")
    var securityPassword: String,

    @OneToMany(mappedBy = "user")
    var words: List<Word> = mutableListOf(),


    ) : UserDetails {
    constructor() : this(null, "", "", "")



    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return emptyList<GrantedAuthority>().toMutableList()
        // return mutableListOf(SimpleGrantedAuthority("ADMIN"))
    }

    override fun getPassword(): String {
        return securityPassword
    }

    fun setPassword(password: String){
        this.securityPassword = password
    }

    override fun getUsername(): String {
      return email;
    };
}
