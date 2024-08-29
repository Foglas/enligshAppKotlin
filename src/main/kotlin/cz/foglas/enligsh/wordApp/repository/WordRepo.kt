package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.Word
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface WordRepo : CrudRepository<Word, Long> {

    @Query("SELECT * FROM getRandomWords(:surface, :capacity)", nativeQuery = true)
    fun getWordSet(surface: Int, capacity: Int) : List<Word>
}