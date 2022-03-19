package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.platform.service.LessonService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lesson")
class LessonController(
    private val lessonService: LessonService
) {
    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/forCourse/{courseId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllLessonsForCourse(@PathVariable courseId: Long): ResponseEntity<String> {
        val lessons = lessonService.loadAllLessonsForCourse(courseId)
        val jsonLessons = objectMapper.writeValueAsString(lessons)
        return ResponseEntity.ok(jsonLessons)
    }

    @PostMapping("/{lessonId}/verifyAnswers", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun verifyUserAnswers(@PathVariable lessonId: Long, @RequestBody jsonAnswers: String): ResponseEntity<String> {
        val answers = objectMapper.readValue<List<Long>>(jsonAnswers)
        val correctAnswers = lessonService.verifyAnswers(lessonId, answers)
        val correctAnswersJson = objectMapper.writeValueAsString(correctAnswers)
        return ResponseEntity.ok(correctAnswersJson)

    }
}