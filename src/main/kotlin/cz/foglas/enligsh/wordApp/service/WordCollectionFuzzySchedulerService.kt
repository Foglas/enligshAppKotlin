package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.config.WordFuzzy
import cz.foglas.enligsh.wordApp.config.WordFuzzyConfig
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.NotEnoughWordsException
import cz.foglas.enligsh.wordApp.repository.WordRepo
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class WordCollectionFuzzySchedulerService(
    @Value("\${englishApp.word.set.lower_surface}") val lowerSurface: Int,
    @Value("\${englishApp.word.set.surface}") val surface: Int,
    @Value("\${englishApp.word.set.higher_surface}") val higherSurface: Int,
    val fuzzyWord: WordFuzzyConfig,
    val wordRepo: WordRepo
) : WordCollectionScheduler {

    val log = KotlinLogging.logger("FuzzyScheduler")

    override suspend fun getWordCollection(capacity: Int): Collection<Word>{
        val context = Dispatchers.IO
        val scope = CoroutineScope(context)

        val fuzzyWord = fuzzyWord.getValues()

       var list : MutableList<Deferred<Any>> = fuzzyWord.map{
                element ->
            scope.async(context + CoroutineName(element.key)){
              val innerCapacity = (element.value * capacity).roundToInt()
                log.info { "${element.key}: $innerCapacity" }
              when(element.key){
                  WordFuzzy.RATIO_UNKNOWN.name -> {

                      wordRepo.getRandomUnknownWords(7, innerCapacity)
                  }
                  WordFuzzy.RATIO_KNOW.name -> {
                      wordRepo.getRandomWordsWithRange(6,8, innerCapacity)
                  }
                  WordFuzzy.RATIO_MEDIUM_KNOW.name -> {
                      wordRepo.getRandomUnknownWords(7, innerCapacity)
                  }
                  WordFuzzy.RATIO_WELL_KNOWN.name -> {
                      wordRepo.getRandomWordsWithRange(6,8, innerCapacity)
                  } else -> log.info { "Ratio doesn´t recognize" }
              }
          }
       }.toMutableList()

      val finalListOfWords = list.map {
          try{
          it.await()
          } catch (ex: JpaSystemException){
              throw NotEnoughWordsException()
          }
      }.flatMap{ it as List<Word> }.toMutableList()


      if (finalListOfWords.size < capacity){
          val additionalCapacity = capacity - finalListOfWords.size
          val words = wordRepo.getWords(additionalCapacity)
          finalListOfWords.addAll(words);
      }

      finalListOfWords.apply {
          shuffle()
      }

        log.info { "Actual size of words = ${finalListOfWords.size}" }

        return finalListOfWords
    }
}