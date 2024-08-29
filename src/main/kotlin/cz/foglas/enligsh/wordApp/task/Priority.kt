package cz.foglas.enligsh.wordApp.task

interface Priority {
    val priorityValue: Int
    fun plus(value: Int): Priority
    fun minus(value: Int): Priority

}