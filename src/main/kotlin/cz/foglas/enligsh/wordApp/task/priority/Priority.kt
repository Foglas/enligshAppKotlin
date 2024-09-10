package cz.foglas.enligsh.wordApp.task.priority

import cz.foglas.enligsh.wordApp.validationAnnotation.HigherThanZero

/**
 * Class which encapsulate logic of different priority strategies. Strategy is selected
 * on the PriorityStategy enum input
 */
class Priority(private val strategy: PriorityStrategy,
               @HigherThanZero
               val priorityValue : Int = 20) {
   private val strategyClazz : PriorityStrategyInf

    init {
        when(strategy){
            PriorityStrategy.LINEAR -> strategyClazz = LinearPriority(priorityValue)
        }
    }

    fun plus(value: Int) : Priority {
       return Priority(strategy ,strategyClazz.plus(value).priorityValue)
    }

    fun minus(value: Int): Priority{
        return Priority(strategy ,strategyClazz.minus(value).priorityValue)
    }


}