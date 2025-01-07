package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.Word
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordRepo : CrudRepository<Word, Long> {

    fun getWordById(id: Long) : Word

    @Query("SELECT * FROM getrandomwordwellknown(:surface, :capacity, :userId)", nativeQuery = true)
    fun getKnownWords(surface: Int, capacity: Int, userId: Long): MutableList<Word>

    @Query(
        "SELECT * FROM getrandomwordswithrange(:lowerSurface, :higherSurface, :capacity, :userId)",
        nativeQuery = true
    )
    fun getRandomWordsWithRange(lowerSurface: Int, higherSurface: Int, capacity: Int, userId: Long): MutableList<Word>

    @Query("SELECT * FROM getrandomunknownwords(:surface, :capacity, :userId)", nativeQuery = true)
    fun getRandomUnknownWords(surface: Int, capacity: Int, userId: Long): MutableList<Word>

    @Query("SELECT * FROM getRandomWords(:capacity, :userId)", nativeQuery = true)
    fun getWords(capacity: Int, userId: Long): MutableList<Word>

    fun getAllByUserId(userId: Long): List<Word>

}