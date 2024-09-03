package cz.foglas.enligsh.wordApp.exceptions

import kotlin.math.floor

class RatioIsHigherThanIsAllowed(
    private val totalRatioValue : Float
) : RuntimeException() {

    override val message: String?
        get() = "Ratio of words is higher than 1, that means more than 100% -> ${floor(totalRatioValue*100)}%"

}