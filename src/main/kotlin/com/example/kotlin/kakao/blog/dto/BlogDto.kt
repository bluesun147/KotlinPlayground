package com.example.kotlin.kakao.blog.dto

import com.example.kotlin.kakao.core.anotation.ValidEnum
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BlogDto(
    // 속성 선언
    @field:NotBlank(message = "query parameter required")
    // json 직렬화 시 이름 사용 (json 데이터와 엔티티 이름 매핑 위해, snake, camel case)
    @JsonProperty("query")
    private val _query: String?,

    @field:NotBlank(message = "sort parameter required")
    @field:ValidEnum(enumClass = EnumSort::class, message = "sort parameter one of ACCURACY and RECENCY")
    @JsonProperty("sort")
    private val _sort: String?,

    @field:NotNull(message = "page parameter required")
    @field:Max(50, message = "page is more than max")
    @field:Min(1, message = "page is less than min")
    @JsonProperty("page")

    private val _page: Int?,
    @field:NotNull(message = "size parameter required")
    @JsonProperty("size")
    private val _size: Int?
) {
    // 게터 메소드
    // !!는 non null 단언 연산자 (non null assertion)
    // 자바에선 null 체크 직접 해야 하지만 코틀린에선 해당 패턴 간결 표현 가능
    val query: String
        // _query가 절대 null이 아님을 개발자가 단언. null이 아님을 가정
        get() = _query!!
    val sort: String
        get() = _sort!!
    val page: Int
        get() = _page!!
    val size: Int
        get() = _size!!

    private enum class EnumSort
    {
        ACCURACY,
        RECENCY
    }
}