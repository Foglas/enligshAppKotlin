package cz.foglas.enligsh.wordApp.exceptions

class DeleteWasNotSuccessful(val inputMessage: String) : Exception() {

    override val message: String
        get() = inputMessage
}