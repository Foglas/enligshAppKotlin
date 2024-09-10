package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.data.Range
import cz.foglas.enligsh.wordApp.exceptions.RatioIsHigherThanIsAllowedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class WordFuzzyConfig: Fuzzy<Map<String, Float>> {
    private val ratioKnown: Float = 0.2f
    private val ratioMediumKnown: Float = 0.3f
    private val ratioLessKnown: Float = 0.3f
    private val ratioUnknown: Float = 0.2f

    @Value("\${englishApp.word.priority.max}")
    private val maxPriorityValue: Int = 20
    private val minPriorityValue: Int = 1

    init {
        val total_ratio = ratioKnown + ratioMediumKnown + ratioLessKnown + ratioUnknown
        if (total_ratio > 1){
            throw RatioIsHigherThanIsAllowedException(total_ratio)
        }
    }

    override fun getValues(): Map<String, Float> {
       return mapOf(
            Pair(WordFuzzy.RATIO_LESS_KNOW.name, ratioLessKnown),
            Pair(WordFuzzy.RATIO_KNOW.name, ratioKnown),
            Pair(WordFuzzy.RATIO_MEDIUM_KNOW.name, ratioMediumKnown),
            Pair(WordFuzzy.RATIO_UNKNOWN.name, ratioUnknown))
    }

    override fun getRange(fuzzyState: WordFuzzy): Range {
        return when(fuzzyState){
            WordFuzzy.RATIO_KNOW -> {
                Range(1,3)
            }
            WordFuzzy.RATIO_MEDIUM_KNOW -> {
                Range(4, 6)
            }
            WordFuzzy.RATIO_LESS_KNOW -> {
                Range(7, 9)
            }
            WordFuzzy.RATIO_UNKNOWN -> {
                Range(10, 10)
            }
        }

    }


}