package com.example.kotlin.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

// 인터셉터는 컨트롤러 호출 전/후/완료 시점에 득정 코드 수행하는 역할

@Component
class HttpInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    // 컨트롤러 호출 전
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("[prehandle]")
        return true
    }

    // 컨트롤러 호출 후
    // 익셉션 발생 시 필터 핸들러 동작하면서 컨트롤러 정상 반환 x, postHandle 동작 x
    @Throws(Exception::class)
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        logger.info("[postHandle]")
    }

    // 컨트롤러 호출 완료
    @Throws(Exception::class)
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: java.lang.Exception?) {
        logger.info("[afterCompletion]")
    }
}