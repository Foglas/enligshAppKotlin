package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

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

    @OneToMany(mappedBy = "word", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var examples: List<Example> = mutableListOf(),

    @Column(name = "originalText")
    var originalText: String?,

    @ManyToOne
    @JoinColumn(name = "fk_userid", referencedColumnName = "id")
    var user: User? = null,

    @ManyToMany(mappedBy = "words")
    var exercises: List<ExerciseResult> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordGen")
    @SequenceGenerator(name = "wordGen", sequenceName = "wordid", allocationSize = 1, initialValue = 1)
    var id: Long?,

    ){
    constructor() : this(null, null, null, null, 10, mutableListOf(), null, null, id = null)



    class Builder(
        var text: String?=null,
        var secondForm: String?=null,
        var thirdForm: String?=null,
        var countable: String?=null,
        var priority: Int = 10,
        var examples: List<Example> = mutableListOf<Example>(),
        var user: User? = null,
        var originalText: String? = null,
        var exercises: MutableList<ExerciseResult> = mutableListOf(),
        var id: Long? = null
        ){

        fun text(text: String) = apply { this.text = text }
        fun secondForm(secondForm: String) = apply { this.secondForm = secondForm }
        fun thirdForm(thirdForm: String) = apply { this.thirdForm = thirdForm }
        fun countable(countable: String) = apply { this.countable = countable }
        fun priority(priority: Int) = apply { this.priority = priority }
        fun examples(examples: List<Example>) = apply { this.examples = examples }
        fun user(user: User?) = apply { this.user = user }
        fun originalText(text: String) = apply { this.originalText = text }
        fun exercises(exercises: MutableList<ExerciseResult>) = apply { this.exercises = exercises }
        fun id(id: Long?) = apply { this.id = id }
        fun copyWithExamples(word: Word ,examples: MutableList<Example>) = apply {this.text = text
            this.secondForm = word.secondForm
            this.thirdForm = word.thirdForm
            this.countable = word.countable
            this.priority = word.priority
            this.examples = examples
            this.user = word.user
            this.originalText = word.originalText
        }
        fun build() =
            Word(
                this.text,
                this.secondForm,
                this.thirdForm,
                this.countable,
                this.priority,
                this.examples,
                this.originalText,
                this.user,
                this.exercises,
                this.id
            )
    }
}