package com.example.kotlin.kakao.blog.service

import com.example.kotlin.kakao.core.exception.InvalidInputException
import com.example.kotlin.kakao.blog.dto.BlogDto
import com.example.kotlin.kakao.blog.entity.WordCount
import com.example.kotlin.kakao.blog.repository.WordRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BlogService (
    val wordRepository: WordRepository
) {

    @Value("\${REST_API_KEY}")
    lateinit var restApiKey: String

    private enum class ExceptionMsg(val msg: String) {
        EMPTY_QUERY("query parameter required"),
        NOT_IN_SORT("sort parameter one of accuracy and recency"),
        LESS_THAN_MIN("page is less than min"),
        MORE_THAN_MAX("page is more than max"),
    }


    // ?는 null 가능하다는 뜻
    fun searchKakao(blogDto: BlogDto): String? {
        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri {
                it.path("/v2/search/blog")
                    .queryParam("query", blogDto.query)
                    .queryParam("sort", blogDto.sort)
                    .queryParam("page", blogDto.page)
                    .queryParam("size", blogDto.size)
                    .build()
            }
            .header("Authorization", "KakaoAK $restApiKey")
            .retrieve()
            .bodyToMono<String>()

        // 값 체크
        val msgList = mutableListOf<ExceptionMsg>()

        // 쿼리가 비어있다면
        // trim() : 공백 제거
        if (blogDto.query.trim().isEmpty()) {
            msgList.add(ExceptionMsg.EMPTY_QUERY)
        }

        if (blogDto.sort.trim() !in arrayOf("ACCURACY", "RECENCY")) {
            msgList.add(ExceptionMsg.NOT_IN_SORT)
        }

        when {
            // 해당 조건 만족 시 -> 실행 후 when 밖으로 이동
            blogDto.page < 1 -> msgList.add(ExceptionMsg.LESS_THAN_MIN)
            blogDto.page > 50 -> msgList.add(ExceptionMsg.MORE_THAN_MAX)
        }

        if (msgList.isNotEmpty()) {
            // msgList에 있는 각 메시지 객체의 msg 속성을 추출해 문자열로 합침
            // it은 람다식에서 사용되는 반복 요소에 대한 참조
            val message = msgList.joinToString { it.msg }
            throw InvalidInputException(message)
        }


        val lowQuery: String = blogDto.query.lowercase()
        val word: WordCount = wordRepository.findById(lowQuery).orElse(WordCount(lowQuery))
        word.count++

        wordRepository.save(word)

        val result = response.block()

        return result
    }

    // 검색어 카운트 순 정렬
    fun searchWordRank(): List<WordCount> = wordRepository.findTop10ByOrderByCountDesc()
}