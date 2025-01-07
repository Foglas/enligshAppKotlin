package cz.foglas.enligsh.wordApp.mapping

import cz.foglas.enligsh.wordApp.data.InputWordDto
import cz.foglas.enligsh.wordApp.data.OutputExampleDto
import cz.foglas.enligsh.wordApp.data.OutputWordDto
import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.domains.Word

fun Word.toDto(): OutputWordDto {
    var outputListOfExamples = examples.map { example -> OutputExampleDto(example.id!!, example.text) }.toList()
    var outputWord = OutputWordDto.Builder()
        .text(text.orEmpty())
        .secondaryForm(secondForm.orEmpty())
        .thirdForm(thirdForm.orEmpty())
        .countable(countable.orEmpty())
        .examples(outputListOfExamples)
        .userId(user?.id)
        .originalText(originalText.orEmpty())
        .wordId(id)
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

fun OutputWordDto.toEntity(user: User? = null): Word {
    var listOfExamples = mutableListOf<Example>()
    val word = Word.Builder().text(text.orEmpty()).secondForm(secondForm.orEmpty()).thirdForm(thirdForm.orEmpty())
        .countable(countable.orEmpty()).examples(listOfExamples).user(user).originalText(originalText.orEmpty())
        .id(wordId).build()

    for (example in examples) {
        val outputExample = Example(example.id, example.text, word)
        listOfExamples.add(outputExample)
    }

    return word;
}


