package cz.foglas.enligsh.wordApp.data

import jakarta.validation.constraints.NotBlank

data class OutputWordDto(
    @field:NotBlank(message = "Word have to be with text form")
    val text: String?,
    val secondForm: String?,
    val thirdForm: String?,
    val countable: String?,
    val examples: List<OutputExampleDto> = emptyList(),
    val userId: Long?,
    val originalText: String?,
    val wordId: Long?
) {
    class Builder(
        var text: String? = null,
        var secondForm: String? = null,
        var thirdForm: String? = null,
        var countable: String? = null,
        var examples: List<OutputExampleDto> = emptyList(),
        var userId: Long? = null,
        var originalText: String? = null,
        var wordId: Long? = null
    ) {
        fun text(text: String) = apply { this.text = text }
        fun secondaryForm(secondForm: String) = apply { this.secondForm = secondForm }
        fun thirdForm(thirdForm: String) = apply { this.thirdForm = thirdForm }
        fun countable(countable: String) = apply { this.countable = countable }
        fun examples(examples: List<OutputExampleDto>) = apply { this.examples = examples }
        fun userId(userId: Long?) = apply { this.userId = userId }
        fun wordId(wordId: Long?) = apply { this.wordId = wordId }
        fun originalText(originalText: String) = apply { this.originalText = originalText }
        fun build() = OutputWordDto(text, secondForm, thirdForm, countable, examples, userId, originalText, wordId)

    }
}
