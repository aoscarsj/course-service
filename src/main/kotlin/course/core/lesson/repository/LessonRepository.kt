package course.core.lesson.repository

import course.core.lesson.data.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface LessonRepository : JpaRepository<Lesson, UUID> {

    @Query(
        value = "SELECT * from tb_lessons WHERE module_module_id = :moduleId", nativeQuery =
        true
    )
    fun findAllLessonsIntoModule(@Param("moduleId") moduleId: UUID): List<Lesson>
}