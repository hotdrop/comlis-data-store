package jp.hotdrop.comlis.store

import org.apache.catalina.connector.Connector
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TomcatConfig {

    @Value("\${server.http.port}")
    private var httpPort: Int = 0

    @Bean
    fun containerCustomizer(): EmbeddedServletContainerCustomizer {
        return EmbeddedServletContainerCustomizer { container ->
            (container as? TomcatEmbeddedServletContainerFactory)?.run {
                val connector = Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL).apply {
                    port = httpPort
                }
                addAdditionalTomcatConnectors(connector)
            }
        }
    }

}