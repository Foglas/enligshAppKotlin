package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.data.ExerciseDto
import cz.foglas.enligsh.wordApp.data.InputWordDto
import cz.foglas.enligsh.wordApp.data.OutputWordDto
import cz.foglas.enligsh.wordApp.data.PriorityDto
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
    open fun createWord(@Valid @RequestBody word: InputWordDto): ResponseEntity<CommonResponseInf<OutputWordDto>> {
        log.info { "word received" }
        val user = userService.getUserById(word.userId!!)
        val responseWord = wordService.createWord(word.toEntity(user))

        log.info { "Word was created" }
         return ResponseEntity.ok(CommonSuccessResponse(responseWord.toDto()))
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasPermission('READ_PRIVILEGES')")
    @GetMapping("/private/getSet/{capacity}/{userId}")
    open suspend fun getWordSet(@PathVariable capacity: Int, @PathVariable userId: Long): List<OutputWordDto> {
        log.info { "received request for getting set with number $capacity" }
        val exercises = wordCollectionSchedulerServiceImpl.getWordCollection(capacity, userId)
            .map { word -> word.toDto() }.toList()
        return exercises
    }

    @GetMapping("/private/text")
    fun getTest(): String{
        return "hello"
    }

    @PostMapping("/private/priority/plus")
    suspend fun increasePriority(@RequestBody priorityDto: PriorityDto): ResponseEntity<ExerciseDto> {
        val exercise = wordService.increasePriority(1, priorityDto.wordId, priorityDto.exerciseId)
        return ResponseEntity.ok(ExerciseDto(exercise.id!!))
    }

    @PostMapping("/private/priority/minus")
    suspend fun decreasePriority(@RequestBody priorityDto: PriorityDto): ResponseEntity<ExerciseDto> {
        val exercise = wordService.decreasePriority(1, priorityDto.wordId, priorityDto.exerciseId)
        return ResponseEntity.ok(ExerciseDto(exercise.id!!))
    }
}