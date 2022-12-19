package course.core.course.service.impl

import course.core.course.data.Course
import course.core.course.exception.CourseException
import course.core.course.repository.CourseRepository
import course.core.course.service.CourseService
import course.core.lesson.repository.LessonRepository
import course.core.module.repository.ModuleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository
) : CourseService {
    @Transactional // only transaction if something goes wrong, return
    override fun delete(courseId: UUID) {

        val course = find(courseId)
        val modules = moduleRepository.findAllModulesIntoCourse(courseId)

        if (modules.isNotEmpty()) {

            for (module in modules) {

                val lessons = lessonRepository.findAllLessonsIntoModule(module.moduleId!!)
                if (lessons.isNotEmpty())
                    lessonRepository.deleteAll(lessons)
            }
            moduleRepository.deleteAll(modules)
        }
        courseRepository.delete(course)
    }

    override fun find(courseId: UUID): Course {

        return courseRepository.findByIdOrNull(courseId) ?: throw CourseException(
            "Course not found", HttpStatus.NOT_FOUND
        )
    }

}