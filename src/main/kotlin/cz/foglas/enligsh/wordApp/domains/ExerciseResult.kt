package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*


@Entity
@Table(name = "exercise_results")
class ExerciseResult(

    @Column(name = "successCount")
    var successCount: Int,

    @Column(name = "failedCount")
    var failedCount: Int,


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "exercise_results_words",
        joinColumns = [JoinColumn(name = "fk_exerciseid", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_wordid", referencedColumnName = "id")]
    )
    val words: MutableList<Word> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exerciseGen")
    @SequenceGenerator(name = "exerciseGen", sequenceName = "exercise_result_seq", initialValue = 1, allocationSize = 1)
    val id: Long?,

    ) {

    constructor() : this(0, 0, id = null)
}