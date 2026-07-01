package com.example.todomate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todomate.databinding.ItemTodoBinding

class TodoAdapter(
    private val todoList: MutableList<String>
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
        holder.binding.textTodo.text = todoList[position]
    }

    // 아이템 개수 변환
    override fun getItemCount(): Int {

        return todoList.size
    }

}
