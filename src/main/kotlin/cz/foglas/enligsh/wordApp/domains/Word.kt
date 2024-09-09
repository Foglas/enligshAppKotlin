package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "word")
class Word(

    @Column(name = "text")
    @field:NotBlank(message = "Word have to be with text form")
    var text: String?,

    @Column(name = "second_form")
    var secondForm: String?,

    @Column(name = "third_form")
    var thirdForm: String?,

    @Column(name = "countable")
    var countable: String?,

    @Column(name = "priority")
    var priority: Int = 10,

    @OneToMany(mappedBy = "word", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var examples : List<Example> = mutableListOf<Example>(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordGen")
    @SequenceGenerator(name = "wordGen", sequenceName = "wordid", allocationSize = 1, initialValue = 1)
    var id: Long? = null,

    ){
    constructor() : this(null, null, null, null, 10, mutableListOf(), null)



    class Builder(
        var text: String?=null,
        var secondForm: String?=null,
        var thirdForm: String?=null,
        var countable: String?=null,
        var priority: Int = 10,
        var examples : List<Example> = mutableListOf<Example>()
        ){

        fun text(text: String) = apply { this.text = text }
        fun secondForm(secondForm: String) = apply { this.secondForm = secondForm }
        fun thirdForm(thirdForm: String) = apply { this.thirdForm = thirdForm }
        fun countable(countable: String) = apply { this.countable = countable }
        fun priority(priority: Int) = apply { this.priority = priority }
        fun examples(examples: List<Example>) = apply { this.examples = examples }

        fun copyWithExamples(word: Word ,examples: MutableList<Example>) = apply {this.text = text
            this.secondForm = word.secondForm
            this.thirdForm = word.thirdForm
            this.countable = word.countable
            this.priority = word.priority
            this.examples = examples
        }
        fun build() = Word(this.text, this.secondForm, this.thirdForm, this.countable, this.priority, this.examples)
    }
}