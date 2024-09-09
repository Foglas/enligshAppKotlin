package cz.foglas.enligsh.wordApp.task.priority

interface PriorityStrategyInf {
    val priorityValue: Int
    fun plus(value: Int): PriorityStrategyInf
    fun minus(value: Int): PriorityStrategyInf

}