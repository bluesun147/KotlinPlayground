package com.example.kotlin.kakao.repository

import com.example.kotlin.kakao.entity.WordCount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<WordCount, Int>