package cz.foglas.enligsh.wordApp.validationAnnotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [HigherThanZeroValidator::class])
annotation class HigherThanZero(val message: String = "Priority have to be higher than zero",
                                val groups: Array<KClass<*>> = [],
                                val payload: Array<KClass<out Payload>> = [])

