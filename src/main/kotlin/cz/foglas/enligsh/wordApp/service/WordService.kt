package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.ExerciseResult
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.DeleteWasNotSuccessful
import cz.foglas.enligsh.wordApp.exceptions.WordNotFoundException
import cz.foglas.enligsh.wordApp.repository.ExampleRepo
import cz.foglas.enligsh.wordApp.repository.UserRepo
import cz.foglas.enligsh.wordApp.repository.WordRepo
import cz.foglas.enligsh.wordApp.task.priority.Priority
import cz.foglas.enligsh.wordApp.task.priority.PriorityStrategy
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class WordService(
    private val wordRepo: WordRepo,
    private val userRepo: UserRepo,
    private val exampleRepo: ExampleRepo,
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


    override fun deleteWord(id: Long, user: Principal) {
        val userDb = userRepo.findByEmail(user.name)
        val word = wordRepo.findById(id)

        if (userDb != null && userDb.id == word.get().user?.id) {
            wordRepo.deleteById(id)
        } else {
            throw DeleteWasNotSuccessful("Delete word was not successful")
        }
    }

    override fun updateWord(word: Word): Word {
        val wordFromSave = wordRepo.save(word)
        val wordFromRepo = wordRepo.findById(wordFromSave.id) ?: throw WordNotFoundException("Word wasn't saved")
        return wordFromRepo.get()
    }

    override fun getWordById(id: Long): Word {
        return wordRepo.getWordById(id)
    }

    fun getWordsByUserId(id: Long): List<Word> {
        return wordRepo.getAllByUserId(id);
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

