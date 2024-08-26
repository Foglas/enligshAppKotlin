package cz.foglas.enligsh.wordApp.response

import com.fasterxml.jackson.annotation.JsonIgnore

class CommonSuccessResponse<T>(
    @JsonIgnore
    val clazz : T
) : CommonResponseInf<T> {
    override fun getResponse(): T {
        return clazz
    }


}