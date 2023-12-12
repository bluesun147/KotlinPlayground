package com.example.kotlin.service

import com.example.kotlin.entity.Post
import com.example.kotlin.exception.BaseException
import com.example.kotlin.exception.BaseResponseCode
import com.example.kotlin.repository.PostRepository
import org.springframework.stereotype.Service

///*
//데이터 크래스
//다양한 메소드 자동 생성해주는 클래스
//임시용 -> 엔티티로 변경
// */
//data class Post(
//        val title: String,
//        val body: String
//)

@Service
//리포지토리를 생성자 주입 받고 있음
class TestBlogService(val postRepository: PostRepository) {
//    // mutableListOf : 수정 가능한 리스트 (동적 할당), 읽기, 쓰기 가능
//    // List인 listOf는 읽기 전용
//    val posts: MutableList<Post> = mutableListOf<Post>()

    fun readOnePost(id: Int): Post {
        // ?:는 falsy값일 경우 우측값 수행
        return postRepository.findById(id).orElse(null) ?: throw BaseException(BaseResponseCode.POST_NOT_FOUND)
    }

    fun readAllPost(): List<Post> {
        return postRepository.findAll()
    }

    fun createPost(title: String, body: String): Boolean {
        val post = Post(title, body)
        postRepository.save(post)
        return true
    }

    fun modifyPost(id: Int, title: String, body: String): Boolean {
        val post = postRepository.findById(id).orElse(null) ?: throw BaseException(BaseResponseCode.POST_NOT_FOUND)

        post.title = title
        post.body = body

        return true
    }

    fun deletePost(id: Int): Boolean {
        postRepository.deleteById(id)
        return true
    }
}