package cz.foglas.enligsh.wordApp.task.priority

import cz.foglas.enligsh.wordApp.data.Range
import cz.foglas.enligsh.wordApp.exceptions.MismatchPriorityValueException
import mu.KotlinLogging

class LinearPriority(override val priorityValue: Int) : PriorityStrategyInf {

    private val log = KotlinLogging.logger("LinearPriority")
    private val range : Range = Range(1,20)

    override fun plus(value: Int): PriorityStrategyInf {
       return if (value > 0){
           var newPriority = priorityValue + value
           if(newPriority <= range.b) {
               LinearPriority(newPriority)
           } else {
               log.info { "Priority is already on the highest value" }
               this
           }
        } else {
            throw MismatchPriorityValueException(value)
       }
    }

    override fun minus(value: Int): PriorityStrategyInf {
        return if (value > 0){
            var newPriority = priorityValue - value
            if(newPriority >= range.a) {
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