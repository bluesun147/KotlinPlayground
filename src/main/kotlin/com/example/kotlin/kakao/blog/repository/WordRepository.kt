package com.example.kotlin.kakao.blog.repository

import com.example.kotlin.kakao.blog.entity.WordCount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<WordCount, Int>