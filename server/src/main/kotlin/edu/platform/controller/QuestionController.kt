package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.platform.service.QuestionService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/question")
class QuestionController(
    private val questionService: QuestionService
) {
    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/forLesson/{lessonId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getQuestionsForLesson(@PathVariable lessonId: Long): ResponseEntity<String> {
        val questions = questionService.loadAllQuestionsForLesson(lessonId)
        val jsonQuestions = objectMapper.writeValueAsString(questions)
        return ResponseEntity.ok(jsonQuestions)
    }
}