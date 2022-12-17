package course.core.lesson.repository

import course.core.lesson.data.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LessonRepository : JpaRepository<Lesson, UUID> {
}