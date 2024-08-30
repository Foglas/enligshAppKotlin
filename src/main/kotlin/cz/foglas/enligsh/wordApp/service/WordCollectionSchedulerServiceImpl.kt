package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.NotEnoughWordsException
import cz.foglas.enligsh.wordApp.mapping.toDto
import cz.foglas.enligsh.wordApp.repository.WordRepo
import kotlinx.coroutines.*
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cglib.proxy.Dispatcher
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class WordCollectionSchedulerServiceImpl(
    val wordRepo: WordRepo
) : WordCollectionScheduler {


   override suspend fun getWordIdCollection(surface: Int, capacity: Int): List<Word> {
        val context = Dispatchers.IO
        val scope = CoroutineScope(context)
        var knownWords = emptyList<Word>()
        var unKnownWords = emptyList<Word>()


       val knowWordsJob = scope.async(context + CoroutineName("IOWordKnowWords")){
              knownWords  = wordRepo.getWordSet(surface,capacity)

        }

       val unknownWordsJob = scope.async(context + CoroutineName("IOWordUnknownWords")) {
           unKnownWords =  wordRepo.getWordSet(surface,capacity)
        }

       try {
           unknownWordsJob.await()
           knowWordsJob.await()
       }catch (ex: JpaSystemException){
           throw NotEnoughWordsException()
       }

        val responseWords = mutableListOf<Word>().apply {
            addAll(knownWords)
            addAll(unKnownWords)
            shuffle()
        }

        return responseWords
    }

}