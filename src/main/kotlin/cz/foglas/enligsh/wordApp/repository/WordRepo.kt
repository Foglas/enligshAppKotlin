package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.Word
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface WordRepo : CrudRepository<Word, Long> {

    @Query("SELECT * FROM getrandomwordwellknown(:surface, :capacity)", nativeQuery = true)
    fun getWellKnownWords(surface: Int, capacity: Int) : MutableList<Word>

    @Query("SELECT * FROM getrandomwordswithrange(:lowerSurface, :higherSurface, :capacity)", nativeQuery = true)
    fun getRandomWordsWithRange(lowerSurface: Int, higherSurface: Int, capacity: Int) : MutableList<Word>

    @Query("SELECT * FROM getrandomunknownwords(:surface, :capacity)", nativeQuery = true)
    fun getRandomUnknownWords(surface: Int, capacity: Int) : MutableList<Word>

    @Query("SELECT * FROM getRandomWords(:capacity)", nativeQuery = true)
    fun getWords(capacity: Int) : MutableList<Word>
}