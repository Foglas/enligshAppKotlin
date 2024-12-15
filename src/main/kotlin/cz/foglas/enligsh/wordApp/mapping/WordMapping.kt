package cz.foglas.enligsh.wordApp.mapping

import cz.foglas.enligsh.wordApp.data.ExampleDto
import cz.foglas.enligsh.wordApp.data.InputWordDto
import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.domains.Word

fun Word.toDto(): InputWordDto {
    var outputListOfExamples = examples.map { example -> ExampleDto(example.text) }.toList()
    var outputWord = InputWordDto.Builder()
        .text(text.orEmpty())
        .secondaryForm(secondForm.orEmpty())
        .thirdForm(thirdForm.orEmpty())
        .countable(countable.orEmpty())
        .examples(outputListOfExamples)
        .userId(user?.id)
        .originalText(originalText.orEmpty())
        .build()
    return outputWord
}

fun InputWordDto.toEntity(user: User? = null): Word {
    var listOfExamples = mutableListOf<Example>()
    val word = Word.Builder().text(text.orEmpty()).secondForm(secondForm.orEmpty()).thirdForm(thirdForm.orEmpty())
        .countable(countable.orEmpty()).examples(listOfExamples).user(user).originalText(originalText.orEmpty()).build()

    for (example in examples) {
        val outputExample = Example(example.text, word)
        listOfExamples.add(outputExample)
    }

    return word;
}


