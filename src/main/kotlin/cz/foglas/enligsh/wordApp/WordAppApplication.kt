package cz.foglas.enligsh.wordApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["cz.foglas.enligsh"])
open class WordAppApplication

fun main(args: Array<String>) {
    runApplication<WordAppApplication>(*args)
}