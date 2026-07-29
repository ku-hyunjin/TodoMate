package com.example.todomate

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TodoDao {
    // 할 일을 DB에 저장하는 명령어
    // suspend는 화면이 멈추지 않게 백그라운드에서 안전하게 실행하는 타입
    // -> suspend insertTodo 에서 suspend 지정 때문에 에러 발생, 그냥 함수인 fun으로 변경, 나중에 앱 개발할 때는 꼭 suspend 함수 쓰기
    @Insert
    fun insertTodo(todo: Todo)
}