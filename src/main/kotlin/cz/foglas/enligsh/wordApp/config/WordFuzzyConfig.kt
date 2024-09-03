package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.domains.Word
import cz.foglas.enligsh.wordApp.exceptions.RatioIsHigherThanIsAllowed
import org.springframework.context.annotation.Configuration

@Configuration
open class WordFuzzyConfig: Fuzzy<Map<String, Float>> {
    private val RATIO_WELLKNOWN: Float = 0.2f
    private val RATIO_KNOW: Float = 0.3f
    private val RATIO_MEDIUM_KNOW: Float = 0.3f
    private val RATIO_UNKNOWN: Float = 0.2f

    init {
        val total_ratio = RATIO_WELLKNOWN + RATIO_KNOW + RATIO_MEDIUM_KNOW + RATIO_UNKNOWN
        if (total_ratio > 1){
            throw RatioIsHigherThanIsAllowed(total_ratio)
        }
    }

    override fun getValues(): Map<String, Float> {
       return mapOf(
            Pair(WordFuzzy.RATIO_WELL_KNOWN.name, RATIO_WELLKNOWN),
            Pair(WordFuzzy.RATIO_KNOW.name, RATIO_KNOW),
            Pair(WordFuzzy.RATIO_MEDIUM_KNOW.name, RATIO_MEDIUM_KNOW),
            Pair(WordFuzzy.RATIO_UNKNOWN.name, RATIO_UNKNOWN))
    }

}