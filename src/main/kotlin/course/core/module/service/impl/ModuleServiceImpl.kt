package course.core.module.service.impl

import course.core.module.repository.ModuleRepository
import course.core.module.service.ModuleService
import org.springframework.stereotype.Service

@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository
) : ModuleService {

}