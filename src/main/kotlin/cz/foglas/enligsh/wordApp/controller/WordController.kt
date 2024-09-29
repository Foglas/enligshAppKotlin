package cz.foglas.enligsh.wordApp.controller

import InputWordDto
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.mapping.toEntity
import cz.foglas.enligsh.wordApp.response.CommonResponseInf
import cz.foglas.enligsh.wordApp.response.CommonSuccessResponse
import cz.foglas.enligsh.wordApp.service.WordCollectionFuzzySchedulerService
import cz.foglas.enligsh.wordApp.service.WordCollectionScheduler
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class WordController(
    private val wordService: WordService,
    private val wordCollectionSchedulerServiceImpl: WordCollectionFuzzySchedulerService
) {

    private val log = KotlinLogging.logger {}


    @PostMapping("/private/createWord")
    fun createWord(@Valid @RequestBody word: InputWordDto): ResponseEntity<CommonResponseInf<InputWordDto>>{
        log.info {  "word received" }

         val responseWord = wordService.createWord(word.toEntity())
         return ResponseEntity.ok(CommonSuccessResponse(responseWord.toDto()))
    }

    @GetMapping("/private/getSet/{capacity}")
    suspend fun getWordSet(@PathVariable capacity: Int): Mono<List<InputWordDto>> {
        log.info { "received request for getting set with number $capacity" }
        val response = wordCollectionSchedulerServiceImpl.getWordCollection(capacity)
            .map { word -> word.toDto() }.toList()
        return Mono.just(response)
    }

    @GetMapping("/private/text")
    fun getTest(): String{
        return "hello"
    }

    @PostMapping("/private/priority/plus")
    suspend fun increasePriority(@RequestBody id: Long){
        wordService.increasePriority(1, id)
    }

    @PostMapping("/private/priority/minus")
    suspend fun decreasePriority(@RequestBody id: Long){
        wordService.decreasePriority(1, id)
    }
}