package cz.foglas.enligsh.wordApp.response

import com.fasterxml.jackson.annotation.JsonIgnore

class CommonErrorTextResponse(
    @JsonIgnore
    val message : String
) : CommonResponseInf<String> {


    override fun getResponse(): String {
        return message
    }

}