package cz.foglas.enligsh.wordApp.response

import org.springframework.validation.FieldError

class CommonErrorResponse(
   private val fieldErrors : List<FieldError>
) : CommonResponseInf<MutableMap<String, String>> {

   private val validationValoationMap : MutableMap<String, String>

    init {
        validationValoationMap = transformErrorListIntoMap(fieldErrors);
    }


    private fun transformErrorListIntoMap(errors : List<FieldError>) : MutableMap<String,String>{
        val responseMap = HashMap<String, String>()
        errors.forEach { innerError -> validationValoationMap[innerError.field] = innerError.defaultMessage }
        return responseMap
    }

    override fun getResponse(): MutableMap<String, String> {
        return validationValoationMap;
    }

}