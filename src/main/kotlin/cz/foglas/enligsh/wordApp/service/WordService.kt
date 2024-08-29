package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.mapping.toEntity
import cz.foglas.enligsh.wordApp.repository.WordRepo
import org.springframework.stereotype.Service

@Service
class WordService(
   val wordRepo: WordRepo
) : WordServiceInf {



    override fun createWord(word: InputWordDto): InputWordDto {
        val wordToSave = word.toEntity()
        val wordFromSave = wordRepo.save(wordToSave)
        val wordFromRepo =  wordRepo.findById(wordFromSave.id).orElse(null)
        val response = wordFromRepo.toDto()
        return response
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