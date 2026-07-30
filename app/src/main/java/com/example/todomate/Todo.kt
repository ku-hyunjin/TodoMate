package com.example.todomate

import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. 이 데이터 클래스가 Room DB의 '표(Table)' 역할을 한다고 선언
@Entity(tableName = "todo_table")
data class Todo (

    // 2. DB에서 각각의 할 일을 구분하기 위한 고유 번호(주민번호)를 맨 위에 추가
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    var isChecked: Boolean = false
)