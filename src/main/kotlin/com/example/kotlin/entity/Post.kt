package com.example.kotlin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Post(title: String, body: String) {
    @Id
    @GeneratedValue
    // ?는 nullable이고 초기값으로 null을 할당함.
    var id: Int? = null

    @Column
    var title: String = title

    @Column
    var body: String = body
}