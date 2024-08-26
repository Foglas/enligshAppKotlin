package cz.foglas.enligsh.wordApp.domains

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "example")
class Example(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exampleGen")
    @SequenceGenerator(name = "exampleGen", sequenceName = "exampleid", allocationSize = 1, initialValue = 1)
    val id: Long?=null,

    @Column(name = "text")
    val text: String,

    @ManyToOne
    @JoinColumn(name = "fk_wordid", referencedColumnName = "id")
    val word: Word? = null

){
    constructor(text: String, word: Word?) : this(null,text, word )
}