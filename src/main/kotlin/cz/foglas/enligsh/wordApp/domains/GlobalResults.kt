package cz.foglas.enligsh.wordApp.domains

import jakarta.persistence.*


@Entity
@Table(name = "global_results")
class GlobalResults(

    @Column(name = "totalSuccessRate")
    val totalSuccessRate: Double,

    @Column(name = "totalExercisesCount")
    val totalExerciseCount: Int,

    @Column(name = "totalExercisedWords")
    val totalExercisedWords: Long,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "fk_userid", referencedColumnName = "id")
    val user: User? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalResultGen")
    @SequenceGenerator(
        name = "globalResultGen",
        sequenceName = "global_result_seq",
        initialValue = 1,
        allocationSize = 1
    )
    val id: Long? = null,
) {

    constructor() : this(0.0, 0, 0)
}