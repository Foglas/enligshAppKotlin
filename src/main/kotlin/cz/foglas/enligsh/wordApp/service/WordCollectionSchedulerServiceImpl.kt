package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.NotEnoughWordsException
import cz.foglas.enligsh.wordApp.repository.WordRepo
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.stereotype.Component
import kotlin.math.floor

@Component
class WordCollectionSchedulerServiceImpl(
    @Value("\${englishApp.word.set.ratio_know}") val ratioKnow : Float,
    val wordRepo: WordRepo
) : WordCollectionScheduler {

    private val log = KotlinLogging.logger("WordCollectionScheduler")
   override suspend fun getWordCollection(surface: Int, capacity: Int): List<Word> {
        val context = Dispatchers.IO
        val scope = CoroutineScope(context)
        var knownWords = mutableListOf<Word>()
        var unKnownWords = mutableListOf<Word>()

        val knowCapacity = floor(ratioKnow*capacity).toInt()
        val unknownCapacity = capacity-knowCapacity

       log.info { "Set: total capacity: $capacity knowCapacity: $knowCapacity unknownCapacity: $unknownCapacity" }

       val knowWordsJob = scope.async(context + CoroutineName("IOWordKnowWords")){
          //TODO need to do additional function which return know words
           knownWords  = wordRepo.getWellKnownWords(surface,knowCapacity)
        }

       val unknownWordsJob = scope.async(context + CoroutineName("IOWordUnknownWords")) {
           unKnownWords =  wordRepo.getRandomUnknownWords(surface,unknownCapacity)
        }

       try {
           unknownWordsJob.await()
           knowWordsJob.await()
       }catch (ex: JpaSystemException){
           throw NotEnoughWordsException()
       }

       val gettedListSize = knownWords.size + unKnownWords.size

       if (gettedListSize < capacity) {
           val additionalWordCapacity = capacity - gettedListSize
           knownWords.addAll(wordRepo.getWords(additionalWordCapacity))
           log.info { "Set after getting: total capacity: $capacity knowCapacity: ${knownWords.size + additionalWordCapacity} unknownCapacity: ${unKnownWords.size}" }
       }

        val responseWords = mutableListOf<Word>().apply {
            addAll(knownWords)
            addAll(unKnownWords)
            shuffle()
        }



        return responseWords
    }

}