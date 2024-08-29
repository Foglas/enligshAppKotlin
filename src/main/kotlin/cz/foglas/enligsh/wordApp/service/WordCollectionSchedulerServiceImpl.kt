package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.repository.WordRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WordCollectionSchedulerServiceImpl(
    val wordRepo: WordRepo
) : WordCollectionScheduler {


    override fun getWordIdCollection(surface: Int, capacity: Int): List<InputWordDto> {
        val words = wordRepo.getWordSet(surface,capacity).map { word -> word.toDto() }

        return words
    }

}