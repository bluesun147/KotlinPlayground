package com.example.kotlin.service

import com.example.kotlin.entity.Post
import com.example.kotlin.exception.BaseException
import com.example.kotlin.exception.BaseResponseCode
import com.example.kotlin.repository.PostRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.math.log

// https://blog.naver.com/pjt3591oo/223225740605

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class BlogServiceTest {
    final val postRepository = mock(PostRepository::class.java)
    // 서비스에 리포지토리 주입
    val blogService = BlogService(postRepository)

    @Test
    @DisplayName("없는 아이디 조회 시 익셉션 발생")
    fun readOnePostToFailure() {
        val id: Int = 4
        Mockito.`when`(postRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<BaseException> {
            blogService.readOnePost(id)
        }

        print(exception)

        assertEquals(BaseResponseCode.POST_NOT_FOUND, exception.baseResponseCode)
    }

    // 실제 db에 들어있는 것을 테스트 하는 것이 아닌 가짜 객체를 만들어서 테스트 하는 것
    @Test
    @DisplayName("존재하는 id 조회")
    fun readOnePost() {
        val id: Int = 12
        Mockito.`when`(postRepository.findById(id)).thenReturn(Optional.of(Post(title="11", body="22")))
        val result = blogService.readOnePost(id)
        println(result.id)
        println(result.title)

        Assertions.assertEquals("11", result.title)
        Assertions.assertEquals("22", result.body)
    }

    @Test
    @DisplayName("목록 조회")
    fun readAllPost() {
        Mockito.`when`(postRepository.findAll()).thenReturn(listOf(Post(title="11", body="22"), Post(title="title2", body="body2")))
        val result = blogService.readAllPost()
        // result의 모든 객체 값 출력
        result.forEach { post ->
            println("Title: ${post.title}, Body: ${post.body}")
        }

        // postRepository에서 findAll이 몇번 호출되었는지 검사
        // 만약 서비스에서 findAll() 호출 시 인자 전달한다면 전달되는 인자 명시
        verify(postRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("추가")
    fun createPost() {
        Mockito.`when`(postRepository.save(Mockito.any<Post>())).thenReturn(Post(title="11", body = "22"))
        val result = blogService.createPost("aa", "bb")
        assertEquals(result, true)
    }

    @Test
    @DisplayName("존재하지 않는 아이디 업데이트 시도 시 익셉션 발생")
    fun modifyPostToFailure() {
        val id: Int = 1

        // findById 메소드가 주어진 id로 호출될 때 empty 반환하도록 설정 (포스트가 존재하지 않는 경우 시뮬레이션)
        Mockito.`when`(postRepository.findById(id)).thenReturn(Optional.empty())

        // 특정 예외(BaseException)가 발생하는지 확인
        val exception = assertThrows<BaseException> {
            blogService.modifyPost(id, "title0", "body0")
        }

        assertEquals(BaseResponseCode.POST_NOT_FOUND, exception.baseResponseCode)
    }

    @Test
    @DisplayName("존재하지 않는 아이디 업데이트")
    fun modifyPostToSuccess() {
        val id: Int = 1

        Mockito.`when`(postRepository.findById(id)).thenReturn(Optional.of(Post(title="title1", body="body1")))

        val result = blogService.modifyPost(id, "title1", "body1")

        assertEquals(result, true)
    }
}