package cz.foglas.enligsh.wordApp.response

import org.springframework.validation.FieldError

class CommonErrorValidationResponse(
   private val fieldErrors : List<FieldError>
) : CommonResponseInf<MutableMap<String, String>> {

   private val validationViolationMap : MutableMap<String, String>

    init {
        validationViolationMap = transformErrorListIntoMap(fieldErrors);
    }


    private fun transformErrorListIntoMap(errors : List<FieldError>) : MutableMap<String,String>{
        val responseMap : MutableMap<String, String> = mutableMapOf()
        errors.forEach { innerError -> responseMap[innerError.field] = innerError.defaultMessage }
        return responseMap
    }

    override fun getResponse(): MutableMap<String, String> {
        return validationViolationMap;
    }

}