package cz.foglas.enligsh.wordApp.task.priority

import cz.foglas.enligsh.wordApp.exceptions.MismatchPriorityValue

class LinearPriority(override val priorityValue: Int) : PriorityStrategyInf {

    override fun plus(value: Int): PriorityStrategyInf {
       return if (value > 0){
            LinearPriority( priorityValue + value)
        } else {
            throw MismatchPriorityValue(value)
       }
    }

    override fun minus(value: Int): PriorityStrategyInf {
        return if (value > 0){
            LinearPriority( priorityValue - value)
        } else {
            throw MismatchPriorityValue(value)
        }
    }
}