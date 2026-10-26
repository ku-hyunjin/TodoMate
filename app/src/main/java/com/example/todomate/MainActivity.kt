package com.example.todomate

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomate.databinding.ActivityMainBinding
// 👇 Room DB와 코루틴(백그라운드 처리)을 위한 추가 Import
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<Todo>()

    // 추가 1. 데이터베이스를 담을 변수 선언
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 추가 2. Room 데이터베이스 본체 생성하기 (앱 실행 시 최초 1회)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todo-db"
        ).build()

        todoAdapter = TodoAdapter(todoList) { position ->
            todoList.removeAt(position)
            todoAdapter.notifyItemRemoved(position)

        }

        binding.recyclerView.adapter = todoAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun handleAddTodo() {
            val taskText = binding.editTodoInput.text?.toString()?.trim().orEmpty()

            if (taskText.isEmpty()) {
                Toast.makeText(this, "Please enter a task!", Toast.LENGTH_SHORT).show()
                return
            }

            // 새로운 할 일 객체 생성 (Room DB에 넣을 형태)
            val newTodo = Todo(text = taskText)

            // 🌟 [추가 3] 코루틴을 사용해 백그라운드에서 DB에 안전하게 저장
            lifecycleScope.launch(Dispatchers.IO) {
                db.todoDao().insertTodo(newTodo)
            }


            todoList.add(newTodo)
            todoAdapter.notifyItemInserted(todoList.size - 1)
            Toast.makeText(this, "\"$taskText\" added!", Toast.LENGTH_SHORT).show()
            binding.editTodoInput.text?.clear()
        }

        binding.addButton.setOnClickListener {
            handleAddTodo()
        }

        binding.editTodoInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleAddTodo()
                true
            } else {
                false
            }
        }
    }
}