package jp.hotdrop.comlis.store.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ErrorAttributes
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class ComlisErrorController @Autowired constructor(
        val errorAttributes: ErrorAttributes
): ErrorController {

    @RequestMapping("/error")
    fun error(request: HttpServletRequest, response: HttpServletResponse): MutableMap<String, Any>? {
        val servletRequestAttributes = ServletRequestAttributes(request)
        val attributes = errorAttributes.getErrorAttributes(servletRequestAttributes, false)
        (errorAttributes.getError(servletRequestAttributes) as? ComlisException)?.run {
            response.status = status
            attributes.put("status", status)
            attributes.put("error", HttpStatus.valueOf(status).reasonPhrase)
            attributes.put("message", message)
        }
        return attributes
    }

    override fun getErrorPath() = "/error"
}