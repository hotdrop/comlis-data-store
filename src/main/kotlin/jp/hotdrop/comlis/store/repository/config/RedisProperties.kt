package jp.hotdrop.comlis.store.repository.config

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Component
@ConfigurationProperties(prefix = "spring.redis")
data class RedisProperties(
        @NotEmpty
        var host: String? = null,

        @Min(1024)
        @Max(65535)
        var port: Int ?= null
)