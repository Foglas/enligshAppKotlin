package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.data.ResultDto
import cz.foglas.enligsh.wordApp.domains.ExerciseResult
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.repository.ExerciseResultRepo
import cz.foglas.enligsh.wordApp.repository.UserRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ResultService(
    private val exerciseRepo: ExerciseResultRepo,
    private val userRepo: UserRepo
) {

    @Transactional
    open fun getResultsByUserId(id: Long): List<ResultDto> {
        return userRepo.findById(id).get().words.flatMap { it.exercises }
            .map { ResultDto(it.successCount, it.failedCount) }
    }

    open fun getResultsById(id: Long): ExerciseResult {
        return exerciseRepo.getById(id)
    }

    private fun recomputeSuccessExerciseResults(exercise: ExerciseResult, word: Word): ExerciseResult {
        exercise.apply {
            successCount += 1
            words.add(word)
        }
        return exercise
    }

    private fun recomputeFailureExerciseResults(exercise: ExerciseResult, word: Word): ExerciseResult {
        exercise.apply {
            failedCount += 1
            words.add(word)
        }
        return exercise
    }

    open fun recomputeExerciseResults(type: ExerciseResultType, exerciseId: Long?, word: Word): ExerciseResult {
        return if (exerciseId == null) {
            when (type) {
                ExerciseResultType.SUCCESS -> exerciseRepo.save(ExerciseResult(1, 0, mutableListOf(word), null))
                ExerciseResultType.FAILURE -> exerciseRepo.save(ExerciseResult(0, 1, mutableListOf(word), null))

            }
        } else {
            val exercise = exerciseRepo.getById(exerciseId)
            val updatedExercise = when (type) {
                ExerciseResultType.SUCCESS -> recomputeSuccessExerciseResults(exercise, word)
                ExerciseResultType.FAILURE -> recomputeFailureExerciseResults(exercise, word)
            }
            exerciseRepo.save(updatedExercise)
        }
    }
}

enum class ExerciseResultType {
    FAILURE, SUCCESS
}