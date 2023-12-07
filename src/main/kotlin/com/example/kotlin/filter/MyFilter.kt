package com.example.kotlin.filter

import jakarta.servlet.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException

// 인터셉터와 유사한 기능
//

@Component
class MyFilter: Filter {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(ServletException::class)
    // FilterConfig? : nullable한 FilterConfig 객체
    override fun init(filterConfig: FilterConfig?) {
        logger.info("init filter")
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("before filter")
        if (chain != null) {
            chain.doFilter(request, response)
        }
        logger.info("after filter")
    }

    @Throws(IOException::class, ServletException::class)
    override fun destroy() {
        logger.info("destroy filter")
    }
}