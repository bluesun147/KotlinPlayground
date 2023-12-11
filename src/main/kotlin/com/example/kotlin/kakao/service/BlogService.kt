package com.example.kotlin.kakao.service

import com.example.kotlin.kakao.dto.BlogDto
import org.springframework.stereotype.Service

@Service
class BlogService {
    // ?는 null 가능하다는 뜻
    fun searchKakao(blogDto: BlogDto): String? {
        return "serachKakao"
    }
}