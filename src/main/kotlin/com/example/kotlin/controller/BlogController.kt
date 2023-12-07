package com.example.kotlin.controller

import com.example.kotlin.entity.Post
import com.example.kotlin.service.BlogService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// https://blog.naver.com/pjt3591oo/223224423347

data class CreatePostReqDto (
    val title: String,
    val body: String
    )


@RestController
@RequestMapping("/blog")
// 서비스 주입
class BlogController(val blogService: BlogService) {
    @GetMapping("/{idx}")
    fun readOnePost(@PathVariable("idx") idx:Int, @RequestParam("page", required = false, defaultValue = 0.toString()) page: Int): Post {
        return blogService.readOnePost(idx)
    }

    @GetMapping()
    fun readAllPost(): List<Post> {
        return blogService.readAllPost()
    }

    @PostMapping()
    fun createPost(@RequestBody() createPostReqDto: CreatePostReqDto): Boolean {
        return blogService.createPost(createPostReqDto.title, createPostReqDto.body)
    }

    @PutMapping("/{idx}")
    fun modifyPost(@PathVariable("idx") idx:Int, @RequestBody() createPostReqDto: CreatePostReqDto) : Boolean {
        return blogService.modifyPost(idx, createPostReqDto.title, createPostReqDto.body)
    }

    @DeleteMapping("/{idx}")
    fun deletePost(@PathVariable("idx") idx:Int) : Boolean {
        return blogService.deletePost(idx)
    }
}