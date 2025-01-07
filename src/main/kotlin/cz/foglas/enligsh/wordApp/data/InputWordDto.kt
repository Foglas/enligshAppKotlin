package cz.foglas.enligsh.wordApp.data

import jakarta.validation.constraints.NotBlank

data class InputWordDto(
    @field:NotBlank(message = "Word have to be with text form")
    val text: String?,
    val secondForm: String?,
    val thirdForm: String?,
    val countable: String?,
    val examples: List<InputExampleDto> = emptyList(),
    val userId: Long?,
    val originalText: String?,
) {
    class Builder(
        var text: String? = null,
        var secondForm: String? = null,
        var thirdForm: String? = null,
        var countable: String? = null,
        var examples: List<InputExampleDto> = emptyList(),
        var userId: Long? = null,
        var originalText: String? = null
    ) {
        fun text(text: String) = apply { this.text = text }
        fun secondaryForm(secondForm: String) = apply { this.secondForm = secondForm }
        fun thirdForm(thirdForm: String) = apply { this.thirdForm = thirdForm }
        fun countable(countable: String) = apply { this.countable = countable }
        fun examples(examples: List<InputExampleDto>) = apply { this.examples = examples }
        fun userId(userId: Long?) = apply { this.userId = userId }
        fun originalText(originalText: String) = apply { this.originalText = originalText }
        fun build() = InputWordDto(text, secondForm, thirdForm, countable, examples, userId, originalText)

    }
}