package cz.foglas.enligsh.wordApp.exceptions

import org.postgresql.util.PSQLException
import org.postgresql.util.ServerErrorMessage

class NotEnoughWordsException : PSQLException(ServerErrorMessage("Not enough words in db")) {

    override val message: String
        get() = "In the database is not enough words"


}