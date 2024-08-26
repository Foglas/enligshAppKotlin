package cz.foglas.enligsh.wordApp.controller

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class WordController(
   val wordService: WordService) {



    @PostMapping("/createWord")
    fun createWord(@Valid @RequestBody word: InputWordDto): InputWordDto?{
        InputWordDto
        val example = Example(2,"he;", Word.Builder().examples(listOf()).text("naydar").countable("COUNT").build())
        return wordService.createWord(word)
    }

}