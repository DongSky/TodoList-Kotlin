package m1saka.moe.todolist

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.todoitem_layout.view.*;
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView

/**
 * Created by DongSky on 2018/1/17.
 */
class TodoListAdapter(val items: List<TodoItem>, val itemClickListener: (TodoItem) -> Unit) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todoitem_layout, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(val view: View, val itemClickListener: (TodoItem) -> Unit) : RecyclerView.ViewHolder(view){
        fun bind(item: TodoItem){
            view.title.text = item.todoitemTitle
            view.check.isChecked =item.isdone
            if (view.check.isChecked == false)view.title.setTextColor(Color.BLACK)
            else view.title.setTextColor(Color.GRAY)
            view.check.setOnClickListener {
                if (view.check.isChecked == false)view.title.setTextColor(Color.BLACK)
                else view.title.setTextColor(Color.GRAY)
            }
            view.setOnClickListener { itemClickListener(item) }
        }
    }
}