package com.example.todomate

import android.os.Bundle
import android.view.inputmethod.EditorInfo //1. Enter 감지용 추가
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todomate.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    // 뷰 바인딩 선언
    private lateinit var binding: ActivityMainBinding

    // RecyclerView 와 연결한 Adapter
    private lateinit var todoAdapter: TodoAdapter

    //3. 입력된 To do 텍스트들을 차곡차곡 저장할 동적 배열(리스트) 추가
    private val todoList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //뷰 바인딩 추가
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Adapter 생성
        todoAdapter = TodoAdapter(todoList) { position ->

            //해당 위치 Todo 데이터 삭제
            todoList.removeAt(position)

            //RecyclerView 화면 갱신
            todoAdapter.notifyItemRemoved(position)
        }

        //RecyclerView에 Adapter 연결
        binding.recyclerView.adapter = todoAdapter

        //RecyclerView를 세로 리스트로 표시
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 5. 공통으로 쓸 입력 처리 함수 추가
        fun handleAddTodo() {
            val taskText = binding.editTodoInput.text.toString().trim()

            // 6. 사용자가 아무것도 안 적고 버튼을 눌렀을 때 예외 처리 기능 추가
            if (taskText.isEmpty()) {
                Toast.makeText(this, "Please enter a task!", Toast.LENGTH_SHORT).show()
                return
                // 그대로 함수 종료
            }

            // 7. Memory List(투두리스트)에 입력된 텍스트를 넣는 진짜 데이터 저장 기능 추가
            todoList.add(taskText)

            //RecyclerView에 새 아이템 추가되었다고 호출하는 알림
            todoAdapter.notifyItemInserted(todoList.size - 1)

            //8. 성공 메시지 띄우기(입력한 내용이 토스트에 동적으로 표시)
            Toast.makeText(this, "\"$taskText\" added!", Toast.LENGTH_SHORT).show()

            //9. 다음 할일 입력할 수 있도록 입력창을 깨끗하게 비워주는 기능 추가, 바인딩으로 수정
            binding.editTodoInput.text.clear()
        }

        //이 부분도 바인딩으로 수정
        binding.addButton.setOnClickListener {
            handleAddTodo()

        }

        // 10. Keyboard Enter완료 시 위에서 만든 함수 실행, 바인딩으로 바로 연결
        binding.editTodoInput.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleAddTodo()
                true
            } else {
                false
            }
            }
    }
}