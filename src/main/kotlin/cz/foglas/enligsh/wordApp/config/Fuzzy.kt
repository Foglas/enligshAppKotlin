package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.data.Range

interface Fuzzy<T> {

    fun getValues() : T
    fun getRange(fuzzyState: WordFuzzy) : Range
}