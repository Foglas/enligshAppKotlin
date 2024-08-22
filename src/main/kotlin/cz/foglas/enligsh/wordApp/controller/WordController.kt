package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import lombok.extern.log4j.Log4j2
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Log4j2
@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class WordController(
   val wordService: WordService) {



    @PostMapping("/createWord")
    fun createWord(@Valid @RequestBody word: Word): Word{

        val example = Example(2,"he;", Word.Builder().examples(listOf()).text("naydar").countable("COUNT").build())
       return wordService.createWord(word)
    }

}