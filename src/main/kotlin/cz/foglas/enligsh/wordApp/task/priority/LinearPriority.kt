package cz.foglas.enligsh.wordApp.task.priority

import cz.foglas.enligsh.wordApp.exceptions.MismatchPriorityValueException
import mu.KotlinLogging

class LinearPriority(override val priorityValue: Int) : PriorityStrategyInf {

    val log = KotlinLogging.logger("LinearPriority")

    override fun plus(value: Int): PriorityStrategyInf {
       return if (value > 0){
            LinearPriority( priorityValue + value)
        } else {
            throw MismatchPriorityValueException(value)
       }
    }

    override fun minus(value: Int): PriorityStrategyInf {
        return if (value > 0){
            var newPriority = priorityValue - value
            if(newPriority > 0) {
                LinearPriority(newPriority)
            } else {
                log.info { "Priority is already on the lowest value" }
                this
            }
        } else {
            throw MismatchPriorityValueException(value)
        }
    }
}