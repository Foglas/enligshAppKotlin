package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.ExerciseResult
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExerciseResultRepo : CrudRepository<ExerciseResult, Long> {

    fun save(exerciseResult: ExerciseResult): ExerciseResult
    fun getById(id: Long): ExerciseResult
}