package com.example.kotlin.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 해당 클래스가 전역적으로 예외 처리하도록
@RestControllerAdvice
class BaseFilter {
    // 특정 예외 타입에 대한 처리 메소드 정의
    @ExceptionHandler(*[java.lang.NullPointerException::class, ClassCastException::class, BaseException::class, java.lang.Exception::class])
    // ExceptionHandler 어노테이션에 적용되 타입 중 하나 발생 시 호출되어 처리 담당
    // 파라미터로 처리할 예외 객체
    fun handle(ex: BaseException): ResponseEntity<BaseResponseError> {
        val errorResponse = BaseResponseError(ex.baseResponseCode, ex.baseResponseCode.message)
        return ResponseEntity.status(ex.baseResponseCode.status).body(errorResponse);
    }
}