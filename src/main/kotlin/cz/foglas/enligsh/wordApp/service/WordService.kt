package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.repository.WordRepo
import org.springframework.stereotype.Service

@Service
class WordService(
   val wordRepo: WordRepo
) : WordServiceInf {

    override fun createWord(word: Word): Word {
        return wordRepo.save(word)
    }

    override fun deleteWord(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateWord(word: Word): Word {
        TODO("Not yet implemented")
    }

    override fun getWordById(id: Long): Word {
        TODO("Not yet implemented")
    }


}