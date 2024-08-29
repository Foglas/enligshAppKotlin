package cz.foglas.enligsh.wordApp.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


class DefaultPriority(
    @Autowired(required = false)
    override val priorityValue : Int
) : Priority {

    override fun plus(value: Int): Priority {
        return DefaultPriority(priorityValue + value)
    }

    override fun minus(value: Int): Priority {
       return DefaultPriority(priorityValue-value)
    }

}