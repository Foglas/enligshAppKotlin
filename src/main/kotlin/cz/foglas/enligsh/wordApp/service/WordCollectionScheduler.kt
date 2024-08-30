package cz.foglas.enligsh.wordApp.service

import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Word

/**
 * @variable ratioKnowWords - used for defining the ratio between words. This variable define how many
 * words will be unknown and the other part will be unknown.
 */
interface WordCollectionScheduler {

    suspend fun getWordIdCollection(surface: Int, capacity: Int): Collection<Word>

}