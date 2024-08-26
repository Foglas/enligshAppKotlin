package cz.foglas.enligsh.wordApp.response

import com.fasterxml.jackson.annotation.JsonCreator

interface CommonResponseInf<T> {

    @JsonCreator
    fun getResponse() : T

}