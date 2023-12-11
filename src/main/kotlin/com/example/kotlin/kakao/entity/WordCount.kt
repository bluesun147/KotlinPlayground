package com.example.kotlin.kakao.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class WordCount (
    @Id
    // val은 불변 변수
    val word: String,
    // var는 가변 변수
    var count: Int = 0
)