package com.example.kotlin.kakao.core.exception

class InvalidInputException (
    message: String = "Invalid Input"
) : RuntimeException(message)