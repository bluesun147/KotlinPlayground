package com.example.kotlin.kakao.blog.controller

import com.example.kotlin.kakao.blog.dto.BlogDto
import com.example.kotlin.kakao.blog.entity.WordCount
import com.example.kotlin.kakao.blog.service.BlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/blog")
@RestController
class BlogController (
    // 서비스 주입
    val blogService: BlogService
) {
    @GetMapping("")
    fun search(@RequestBody @Valid blogDto: BlogDto): String? {
        val result: String? = blogService.searchKakao(blogDto)
        return result
    }

    // 검색어 카운트 순 정렬
    @GetMapping("/rank")
    fun searchWordRank(): List<WordCount> = blogService.searchWordRank()
}