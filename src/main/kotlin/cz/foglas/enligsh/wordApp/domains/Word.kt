package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "word")
@Immutable
class Word private constructor(

    @Column(name = "text")
    @NotBlank(message = "Word have to be with text form")
    val text: String,

    @Column(name = "second_form")
    val secondForm: String,

    @Column(name = "third_form")
    val thirdForm: String,

    @Column(name = "countable")
    val countable: String,

    @Column(name = "priority")
    val priority: Int = 10,

    @OneToMany(mappedBy = "word", cascade = [CascadeType.ALL])
    val examples : List<Example> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordGen")
    @SequenceGenerator(name = "wordGen", sequenceName = "wordid", allocationSize = 1, initialValue = 1)
    var id: Long? = null,

)
    class Builder(
        var text: String,
        var secondForm: String,
        var thirdForm: String,
        var countable: String,
        var priority: Int = 10,
        var examples : List<Example> = listOf()
        ){

        fun text(text: String) = apply { this.text = text }
        fun secondForm(secondForm: String) = apply { this.secondForm = secondForm }
        fun thirdForm(thirdForm: String) = apply { this.thirdForm = thirdForm }
        fun countable(countable: String) = apply { this.countable = countable }
        fun priority(priority: Int) = apply { this.priority = priority }
        fun examples(examples: List<Example>) = apply { this.examples = examples }

    }

