package course.core.module.service

import course.core.module.data.Module
import java.util.*

interface ModuleService {
    fun delete(moduleId: UUID)
    fun find(moduleId: UUID): Module
}