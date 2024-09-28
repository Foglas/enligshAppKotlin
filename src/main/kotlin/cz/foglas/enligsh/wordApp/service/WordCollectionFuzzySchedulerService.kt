package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.config.WordFuzzy
import cz.foglas.enligsh.wordApp.config.WordFuzzyConfig
import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.NotEnoughWordsException
import cz.foglas.enligsh.wordApp.repository.WordRepo
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class WordCollectionFuzzySchedulerService(
    val fuzzyWordConf: WordFuzzyConfig,
    val wordRepo: WordRepo
) : WordCollectionScheduler {

    val log = KotlinLogging.logger("FuzzyScheduler")
    val pattern = Regex(".*_[^_]+_.*")


    override fun getWordCollection(capacity: Int): Collection<Word>{
     /*   val context = Dispatchers.IO
        val scope = CoroutineScope(context)

        val fuzzyWord = fuzzyWordConf.getValues()

        log.info { "authenticated in service? ${SecurityContextHolder.getContext().authentication.isAuthenticated}" }

       var list : MutableList<Deferred<Any>> = fuzzyWord.map{
                element ->
            scope.async(context + CoroutineName(element.key)){
              val innerCapacity = (element.value * capacity).roundToInt()
                log.info { "${element.key}: $innerCapacity" }
                val range = fuzzyWordConf.getRange(WordFuzzy.valueOf(element.key))
              when {
                  element.key.matches(pattern) -> wordRepo.getRandomWordsWithRange( range.a, range.b, innerCapacity)
                  element.key == WordFuzzy.RATIO_KNOW.name -> wordRepo.getKnownWords(range.b, innerCapacity)
                  element.key == WordFuzzy.RATIO_UNKNOWN.name -> wordRepo.getRandomUnknownWords(range.a, innerCapacity)

                  else -> log.info { "Ratio doesn´t recognize" }
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
        log.info { "authenticated in service? ${SecurityContextHolder.getContext().authentication.isAuthenticated}" }



        return finalListOfWords

      */

        return emptyList()
    }
}