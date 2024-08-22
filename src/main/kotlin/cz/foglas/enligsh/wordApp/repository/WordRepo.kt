package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.Word
import org.springframework.data.repository.CrudRepository

interface WordRepo : CrudRepository<Word, Long> {
}