package cz.foglas.enligsh.wordApp.validationAnnotation

import cz.foglas.enligsh.wordApp.exceptions.MismatchPriorityValueException
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class HigherThanZeroValidator: ConstraintValidator<HigherThanZero, Int> {
    override fun isValid(p0: Int?, p1: ConstraintValidatorContext?): Boolean {
        return if (p0 != null && p0 >0){
            true
        } else{
            throw MismatchPriorityValueException(p0?:-1)
        }
    }

}