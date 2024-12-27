package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.ExerciseResult
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.WordNotFoundException
import cz.foglas.enligsh.wordApp.repository.WordRepo
import cz.foglas.enligsh.wordApp.task.priority.Priority
import cz.foglas.enligsh.wordApp.task.priority.PriorityStrategy
import org.springframework.stereotype.Service

@Service
class WordService(
    private val wordRepo: WordRepo,
    private val resultService: ResultService
) : WordServiceInf {

    /**
     * Save the word into storage
     * @param word is the word which have to be saved
     */
    override fun createWord(word: Word): Word {
        val wordFromSave = wordRepo.save(word)
        val wordFromRepo = wordRepo.findById(wordFromSave.id) ?: throw WordNotFoundException("Word wasn't saved")
        return wordFromRepo.get()
    }


    override fun deleteWord(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateWord(word: Word): Word {
        return wordRepo.save(word)
    }

    override fun getWordById(id: Long): Word {
        return wordRepo.getWordById(id)
    }

    /**
     * Increase the priority of the word.
     * @param wordId is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun increasePriority(value: Int, wordId: Long, exerciseId: Long?): ExerciseResult {
        val word = basicPriorityOperation(wordId) { actualPriority ->
            var priority = Priority(PriorityStrategy.LINEAR, actualPriority)
            priority.plus(value)
        }
        updateWord(word)
        return resultService.recomputeExerciseResults(ExerciseResultType.FAILURE, exerciseId, word)
    }

    /**
     * Decrease the priority of the word.
     * @param wordId is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun decreasePriority(value: Int, wordId: Long, exerciseId: Long?): ExerciseResult {
        val word = basicPriorityOperation(wordId) { actualPriority ->
            var priority = Priority(PriorityStrategy.LINEAR, actualPriority)
            priority.minus(value)
        }
        updateWord(word)
        return resultService.recomputeExerciseResults(ExerciseResultType.SUCCESS, exerciseId, word)
    }


    private fun basicPriorityOperation(id: Long, operation: (Int) -> Priority): Word {
        val word = getWordById(id)
        var priority = operation(word.priority)
        word.apply {
            this.priority = priority.priorityValue
        }
        return updateWord(word)
    }

}