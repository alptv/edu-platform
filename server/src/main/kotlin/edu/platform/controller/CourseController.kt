package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.platform.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/course")
class CourseController(
    private val courseService: CourseService
) {
    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllCourses(): ResponseEntity<String> {
        val courses = courseService.loadAllCourses()
        val jsonCourses = objectMapper.writeValueAsString(courses)
        return ResponseEntity.ok(jsonCourses)
    }

    @GetMapping("/{courseId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCourseById(@PathVariable courseId: Long): ResponseEntity<String> {
        return if (courseService.hasCourseWithId(courseId)) {
            val course = courseService.loadCourseById(courseId)
            val jsonCourse = objectMapper.writeValueAsString(course)
            ResponseEntity.ok(jsonCourse)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}