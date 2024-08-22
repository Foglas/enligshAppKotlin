package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.Example
import org.springframework.data.repository.CrudRepository

interface ExampleRepo : CrudRepository<Example, Long> {
}