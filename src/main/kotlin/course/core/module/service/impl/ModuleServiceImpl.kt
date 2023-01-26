package course.core.module.service.impl

import course.core.course.service.CourseService
import course.core.lesson.service.LessonService
import course.core.module.data.Module
import course.core.module.data.request.ModuleRegistrationRequest
import course.core.module.data.request.ModuleUpdateRequest
import course.core.module.exception.ModuleException
import course.core.module.repository.ModuleRepository
import course.core.module.service.ModuleService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository,
    private val lessonService: LessonService,
    private val courseService: CourseService
) : ModuleService {
    @Transactional
    override fun delete(courseId: UUID, moduleId: UUID) {

        val module = findIntoCourse(courseId, moduleId)

        lessonService.removeAllIntoModule(moduleId)
        moduleRepository.delete(module)
    }


    override fun update(
        courseId: UUID,
        moduleId: UUID,
        updateRequest: ModuleUpdateRequest
    ): Module {

        val module = findIntoCourse(courseId, moduleId)
        updateRequest.apply {
            description?.let { module.description = it }
            title?.let { module.title = it }
        }

        return moduleRepository.save(module)
    }

    override fun find(moduleId: UUID): Module =
        moduleRepository.findByIdOrNull(moduleId) ?: throw ModuleException(
            "Module not found", HttpStatus.NOT_FOUND
        )

    override fun findIntoCourse(courseId: UUID, moduleId: UUID): Module =
        moduleRepository.findIntoCourse(courseId, moduleId)
            ?: throw ModuleException("Module not found for this course", HttpStatus.NOT_FOUND)

    override fun findAllIntoCourse(courseId: UUID): List<Module> =
        moduleRepository.findAllModulesIntoCourse(courseId)

    override fun create(
        courseId: UUID,
        moduleRegistrationRequest: ModuleRegistrationRequest
    ): Module {

        val course = courseService.find(courseId)

        return moduleRepository.save(Module.from(moduleRegistrationRequest, course))
    }
}