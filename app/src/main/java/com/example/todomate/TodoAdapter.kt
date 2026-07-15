package com.example.todomate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todomate.databinding.ItemTodoBinding

class TodoAdapter(
    private val todoList: MutableList<Todo>,
    //삭제 이벤트 전달 함수 추가
    private val onDeleteClick: (Int) -> Unit
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    // ViewHolder : item_todo.xml 한 개를 관리
    class TodoViewHolder(
        val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root)


    // item_todo.xml 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TodoViewHolder(binding)

    }

    // 데이터와 화면 연결
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // to do 내용 표시
        holder.binding.textTodo.text = todoList[position].text

        // 기존 리스너 제거(RecyclerView 재사용 문제 방지)
        holder.binding.checkTodo.setOnCheckedChangeListener(null)

        // 현재 체크 상태 화면에 표시
        holder.binding.checkTodo.isChecked = todoList[position].isChecked

        // 체크박스 변경 이벤트 처리 추가
        holder.binding.checkTodo.setOnCheckedChangeListener { _, isChecked ->
            todoList[position].isChecked = isChecked

        }

        // 삭제 버튼 클릭 시 이벤트 처리
        holder.binding.deleteButton.setOnClickListener {

            onDeleteClick(position)
        }
    }

    // 아이템 개수 변환
    override fun getItemCount(): Int {

        return todoList.size
    }

}
