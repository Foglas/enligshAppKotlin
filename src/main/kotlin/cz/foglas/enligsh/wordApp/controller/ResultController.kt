package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.data.ResultDto
import cz.foglas.enligsh.wordApp.service.ResultService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${englishApp.api.requestPath}")
class ResultController(
    private val resultService: ResultService
) {

    @GetMapping("/private/globalResults/{id}/all")
    fun getAllGlobalById(@PathVariable("id") id: Int) {

    }

    @GetMapping("/private/exerciseResult/{id}")
    fun getResultById(@PathVariable("id") id: Long): ResponseEntity<ResultDto> {
        val exercises = resultService.getResultsById(id)
        return ResponseEntity.ok(ResultDto(exercises.successCount, exercises.failedCount))
    }

    @GetMapping("/private/exerciseResults/user/{id}")
    fun getResultByUserId(@PathVariable("id") id: Long): ResponseEntity<List<ResultDto>> {
        val result = resultService.getResultsByUserId(id)
        return ResponseEntity.ok(result)
    }


}