package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.Word

interface WordServiceInf {
    fun createWord(word: Word) : Word
    fun deleteWord(id: Long) : Boolean
    fun updateWord(word: Word): Word
    fun getWordById(id: Long): Word
}