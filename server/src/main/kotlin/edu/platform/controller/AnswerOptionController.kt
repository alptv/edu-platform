package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.platform.service.AnswerOptionService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/answerOption")
class AnswerOptionController(
    private val answerOptionService: AnswerOptionService
) {
    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/forQuestion/{questionId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAnswerOptionsForTest(@PathVariable questionId: Long): ResponseEntity<String> {
        val answerOptions = answerOptionService.loadAllAnswerOptionsForQuestion(questionId)
        val jsonAnswerOptions = objectMapper.writeValueAsString(answerOptions)
        return ResponseEntity.ok(jsonAnswerOptions)
    }
}