package cz.foglas.enligsh.wordApp.controller

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.response.CommonResponseInf
import cz.foglas.enligsh.wordApp.response.CommonSuccessResponse
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class WordController(
   val wordService: WordService) {



    @PostMapping("/createWord")
    fun createWord(@Valid @RequestBody word: InputWordDto): ResponseEntity<CommonResponseInf<Any>>{
        val responseWord = wordService.createWord(word)

         return ResponseEntity.ok(CommonSuccessResponse(word))
    }

}