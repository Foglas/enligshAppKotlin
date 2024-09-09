package cz.foglas.enligsh.wordApp.exceptions

import java.lang.Exception

class MismatchPriorityValue(val value: Int) : RuntimeException() {

    override val message: String?
        get() = "Value of total priority/adding priority is mismatch. Value have to be higher than zero, but actual value is $value"
}