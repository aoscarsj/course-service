package course.core.course.service.impl

import course.core.course.data.Course
import course.core.course.data.request.CourseRegistrationRequest
import course.core.course.data.request.CourseUpdateRequest
import course.core.course.exception.CourseException
import course.core.course.exception.CourseRegistrationException
import course.core.course.repository.CourseRepository
import course.core.course.service.CourseService
import course.core.lesson.repository.LessonRepository
import course.core.module.repository.ModuleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
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

    override fun find(courseId: UUID): Course =
        courseRepository.findByIdOrNull(courseId) ?: throw CourseException(
            "Course not found", NOT_FOUND
        )

    override fun create(registrationRequest: CourseRegistrationRequest): Course {

        validateCreateRequest(registrationRequest)
        val course = Course.from(registrationRequest)

        if (courseRepository.existsCourseByName(course.name))
            throw CourseRegistrationException("Error: name is Already taken!", CONFLICT)

        return courseRepository.save(course)
    }

    override fun update(courseId: UUID, updateRequest: CourseUpdateRequest): Course {

        validateUpdateRequest(updateRequest)
        val course = find(courseId)

        updateRequest.apply {

            if (name != null) course.name = name
            if (description != null) course.description = description
            if (imageUrl != null) course.imageUrl = imageUrl
            if (status != null) course.status = status
            if (instructorId != null) course.instructorId = instructorId
            if (level != null) course.level = level
        }

        return courseRepository.save(course)
    }

    override fun findAll(): List<Course> = courseRepository.findAll()

    private fun validateUpdateRequest(updateRequest: CourseUpdateRequest) {

        updateRequest.apply {

            if (name != null) validateCourseName(name)
            if (description != null) validateCourseDescription(description)
        }
    }

    private fun validateCreateRequest(courseCreateRequest: CourseRegistrationRequest) {

        courseCreateRequest.apply {
            validateCourseName(name)
            validateCourseDescription(description)
        }
    }

    private fun validateCourseDescription(description: String) {
        if (description.isBlank())
            throw CourseRegistrationException("The description is not valid", CONFLICT)
    }

    private fun validateCourseName(name: String) {

        val (minSizeName, maxSizeName) = Pair(2, 30)
        if (name.isSizeExceedLimit(minSizeName, maxSizeName))
            throw CourseException(
                "Name must be between $minSizeName and $maxSizeName characters",
                CONFLICT
            )
    }

    fun String.isSizeExceedLimit(lowerLimit: Int, upperLimit: Int): Boolean {
        if (lowerLimit > upperLimit) throw NumberFormatException("The lower limit cannot be greater than the upper limit.")
        return this.length < lowerLimit || this.length > upperLimit
    }

}