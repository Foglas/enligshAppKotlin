package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.service.ResultService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("api/result")
class ResultController(
    private val resultService: ResultService
) {

    @GetMapping("/globalResults/{id}/all")
    fun getAllGlobalById(@PathVariable("id") id: Int) {

    }

    @GetMapping("/exerciseResults/{id}/all")
    fun getAllExerciseById(@PathVariable("id") id: Int) {

    }

}