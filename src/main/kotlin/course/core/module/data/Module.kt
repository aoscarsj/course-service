package course.core.module.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_MODULES")
class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var moduleId: UUID? = null,
    @Column(nullable = false, length = 150)
    val title: String,
    @Column(nullable = false, length = 250)
    val description: String,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),

) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}