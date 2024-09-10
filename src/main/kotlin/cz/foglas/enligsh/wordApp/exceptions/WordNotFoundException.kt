package cz.foglas.enligsh.wordApp.exceptions


class WordNotFoundException(message: String) : Exception() {

    override val message: String?
        get() = message
}