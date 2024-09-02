package cz.foglas.enligsh.wordApp.controller

import InputWordDto
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.response.CommonResponseInf
import cz.foglas.enligsh.wordApp.response.CommonSuccessResponse
import cz.foglas.enligsh.wordApp.service.WordCollectionSchedulerServiceImpl
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class WordController(
   val wordService: WordService,
   val wordCollectionSchedulerServiceImpl: WordCollectionSchedulerServiceImpl
) {

    private val log = KotlinLogging.logger {}


    @PostMapping("/createWord")
    fun createWord(@Valid @RequestBody word: InputWordDto): ResponseEntity<CommonResponseInf<InputWordDto>>{
        log.info {  "word received" }
        val responseWord = wordService.createWord(word)

         return ResponseEntity.ok(CommonSuccessResponse(responseWord))
    }

    @GetMapping("/getSet")
    suspend fun getWordSet(@RequestBody() number: Int): List<InputWordDto> {
        log.info { "received request for getting set with number $number" }

     return wordCollectionSchedulerServiceImpl.getWordCollection(8,number)
           .map { word -> word.toDto() }.toList()


    }


}