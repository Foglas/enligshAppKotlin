package cz.foglas.enligsh.wordApp.mapping

import ExampleDto
import InputWordDto
import cz.foglas.enligsh.wordApp.domains.Example
import cz.foglas.enligsh.wordApp.domains.Word

    fun Word.toDto(): InputWordDto {
       var outputListOfExamples =  examples.map { example ->  ExampleDto(example.text) }.toList()
       var outputWord = InputWordDto.Builder().text(text.orEmpty()).secondaryForm(secondForm.orEmpty()).thirdForm(thirdForm.orEmpty()).countable(countable.orEmpty()).examples(outputListOfExamples).build()
        return outputWord
    }

    fun InputWordDto.toEntity(): Word {
        var listOfExamples = mutableListOf<Example>()
        val word =  Word.Builder().text(text.orEmpty()).secondForm(secondForm.orEmpty()).thirdForm(thirdForm.orEmpty()).countable(countable.orEmpty()).examples(listOfExamples).build()

        for (example in examples){
            val outputExample = Example(example.text, word)
            listOfExamples.add(outputExample)
        }

       return word;
    }


