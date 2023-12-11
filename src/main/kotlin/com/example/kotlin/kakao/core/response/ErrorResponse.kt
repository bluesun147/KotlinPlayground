package com.example.kotlin.kakao.core.response

const val INVALID_ARGUMENT = "Invalid Argument"

// data class는 여러 메소드 자동 생성
// lombok의 @Data와 비슷
data class ErrorResponse(
    val message: String,
    val errorType: String = INVALID_ARGUMENT
)