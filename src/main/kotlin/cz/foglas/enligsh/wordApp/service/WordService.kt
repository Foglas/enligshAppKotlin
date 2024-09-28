package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.WordNotFoundException
import cz.foglas.enligsh.wordApp.repository.WordRepo
import cz.foglas.enligsh.wordApp.task.priority.Priority
import cz.foglas.enligsh.wordApp.task.priority.PriorityStrategy
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Service

@Service
class WordService(
   val wordRepo: WordRepo
) : WordServiceInf {

    /**
     * Save the word into storage
     * @param word is the word which have to be saved
     */
    override fun createWord(word: Word): Word {
        val wordFromSave = wordRepo.save(word)
        val wordFromRepo =  wordRepo.findById(wordFromSave.id)?: throw WordNotFoundException("Word wasn't saved")
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
     * @param id is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun increasePriority(value: Int, id: Long): Word {
           val word = basicPriorityOperation(id) {actualPriority ->
               var priority = Priority(PriorityStrategy.LINEAR, actualPriority)
               priority.plus(value) };
           return updateWord(word)
    }

    /**
     * Decrease the priority of the word.
     * @param id is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun decreasePriority(value: Int, id: Long): Word {
        val word = basicPriorityOperation(id) { actualPriority ->
            var priority = Priority(PriorityStrategy.LINEAR, actualPriority)
            priority.minus(value)
        }
        return updateWord(word)
    }


    private fun basicPriorityOperation(id: Long, operation: (Int) -> Priority): Word{
        val word =  getWordById(id)
        var priority = operation(word.priority)
        word.apply {
            this.priority = priority.priorityValue
        }
        return updateWord(word)
    }

}