package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word

interface WordCollectionScheduler {

    suspend fun getWordCollection(capacity: Int): Collection<Word>

}