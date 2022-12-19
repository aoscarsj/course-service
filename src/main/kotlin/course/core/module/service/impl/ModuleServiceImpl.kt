package course.core.module.service.impl

import course.core.lesson.repository.LessonRepository
import course.core.module.data.Module
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
    private val lessonRepository: LessonRepository
) : ModuleService {
    @Transactional
    override fun delete(moduleId: UUID) {

        val module = find(moduleId)
        val lessons = lessonRepository.findAllLessonsIntoModule(moduleId)

        if (lessons.isNotEmpty())
            lessonRepository.deleteAll(lessons)

        moduleRepository.delete(module)
    }

    override fun find(moduleId: UUID): Module =
        moduleRepository.findByIdOrNull(moduleId) ?: throw ModuleException(
            "Module not found", HttpStatus.NOT_FOUND
        )
}