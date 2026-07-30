package com.example.todomate

import androidx.room.Database
import androidx.room.RoomDatabase

// 우리가 방금 만든 Todo 표(Entity)를 사용하겠다고 선언, 버전은 1로 시작
@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // 데이터베이스 안에서 TodoDao(명령어 모음집)를 꺼내 쓸 수 있게 연결
    abstract fun todoDao(): TodoDao

}