package cz.foglas.enligsh.wordApp.exceptions


class WordDoesntFind(message: String) : Exception() {

    override val message: String?
        get() = message
}