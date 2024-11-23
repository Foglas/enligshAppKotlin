package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.data.InputWordDto
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.mapping.toEntity
import cz.foglas.enligsh.wordApp.response.CommonResponseInf
import cz.foglas.enligsh.wordApp.response.CommonSuccessResponse
import cz.foglas.enligsh.wordApp.service.UserService
import cz.foglas.enligsh.wordApp.service.WordCollectionFuzzySchedulerService
import cz.foglas.enligsh.wordApp.service.WordService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("\${englishApp.api.requestPath}")
open class WordController(
    private val wordService: WordService,
    private val userService: UserService,
    private val wordCollectionSchedulerServiceImpl: WordCollectionFuzzySchedulerService
) {

    companion object {
        val log = KotlinLogging.logger {}
    }

    @PostMapping("/private/createWord")
    open fun createWord(@Valid @RequestBody word: InputWordDto): ResponseEntity<CommonResponseInf<InputWordDto>> {
        log.info { "word received" }
        val user = userService.getUserById(word.userId!!)
        val responseWord = wordService.createWord(word.toEntity(user))

        log.info { "Word was created" }
         return ResponseEntity.ok(CommonSuccessResponse(responseWord.toDto()))
    }

    //  @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasPermission('READ_PRIVILEGES')")
    @GetMapping("/private/getSet/{capacity}")
    open suspend fun getWordSet(@PathVariable capacity: Int): List<InputWordDto> {
        log.info { "received request for getting set with number $capacity" }
        return wordCollectionSchedulerServiceImpl.getWordCollection(capacity)
            .map { word -> word.toDto() }.toList()
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