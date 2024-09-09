package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.MismatchPriorityValue
import cz.foglas.enligsh.wordApp.exceptions.WordDoesntFind
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.mapping.toEntity
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
        val wordFromRepo =  wordRepo.findById(wordFromSave.id)?: throw WordDoesntFind("Word didn't saved")
        return wordFromRepo.get()
    }


    override fun deleteWord(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateWord(word: Word): Word {
        TODO("Not yet implemented")
    }

    override fun getWordById(id: Long): Word {
        val context = Dispatchers.IO + CoroutineName("FindWordById")
        return wordRepo.getWordById(20)
    }

    /**
     * Increase the priority of the word.
     * @param id is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun increasePriority(value: Int, id: Long): Word {
           var priority = Priority(PriorityStrategy.LINEAR)
           val word = basicPriorityOperation(id) { priority.plus(value) };
           return updateWord(word)
    }

    /**
     * Decrease the priority of the word.
     * @param id is id of the word
     * @param value is for the computation of new priority
     */
    override suspend fun decreasePriority(value: Int, id: Long): Word {
        var priority = Priority(PriorityStrategy.LINEAR)
        val word = basicPriorityOperation(id) {  priority.minus(value) };
        return updateWord(word)
    }


    private fun basicPriorityOperation(id: Long, operation: () -> Priority): Word{
        var priority = operation()
        val word =  getWordById(id)
        word.apply {
            this.priority = priority.priorityValue
        }
        return updateWord(word)
    }

}