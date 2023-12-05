package com.example.kotlin.service

import org.springframework.stereotype.Service

/*
데이터 크래스
다양한 메소드 자동 생성해주는 클래스
 */
data class Post(
        val title: String,
        val body: String
)

@Service
class blogService {
    // mutableListOf : 수정 가능한 리스트 (동적 할당), 읽기, 쓰기 가능
    // List인 listOf는 읽기 전용
    val posts: MutableList<Post> = mutableListOf<Post>()

    fun readOnePost(id: Int): Post {
        return posts[id]
    }

    fun readAllPost(): List<Post> {
        return posts
    }

    fun createPost(title: String, body: String): Boolean {
        val post = Post(title, body)
        posts.add(post)
        return true
    }

    fun modifyPost(id: Int, title: String, body: String): Boolean {
        val post = Post(title, body)
        posts[id] = post
        return true
    }

    fun deletePost(id: Int): Boolean {
        posts.removeAt(id)
        return true
    }
}