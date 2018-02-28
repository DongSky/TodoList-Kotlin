package m1saka.moe.todolist

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.todoitem_layout.view.*;
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.view

class TodoListAdapter(var _items: ArrayList<TodoItem>, var _context: Context, database: DatabaseManage) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    val database = database
    var items = _items
    var context = _context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todoitem_layout, parent, false)
        return ViewHolder(view, context, database)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun refresh(pos: Int) {
        var cnt = items.size
        items.removeAt(pos)
        notifyItemRemoved(pos)
//        notifyItemRangeChanged(0, cnt - 1)
    }
    fun refreshAdd(item: TodoItem) {
        var cnt = items.size
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    inner class ViewHolder(val view: View, context: Context, val database: DatabaseManage) : RecyclerView.ViewHolder(view){
        fun bind(item: TodoItem){
            view.title.text = item.todoitemTitle
            view.check.isChecked =item.isdone
            if (view.check.isChecked == false)view.title.setTextColor(Color.BLACK)
            else view.title.setTextColor(Color.GRAY)
            view.check.setOnClickListener{
                val check : Long
                if (view.check.isChecked == false){
                    view.title.setTextColor(Color.BLACK)
                    check = 0
                }
                else {
                    view.title.setTextColor(Color.GRAY)
                    check = 1
                }
                database.use {
                    update("TodoList", "isdone" to check).whereArgs("todoitem = {item}", "item" to view.title.text.toString()).exec()
                }
            }
            view.setOnClickListener {
                var dialog = AlertDialog.Builder(context)
                dialog.setTitle("Delete this Todo item? ")
                dialog.setCancelable(true)
                dialog.setPositiveButton("Yes", {
                    dialogInterface, i ->
                    try {
                        var pos = 0
                        var msg = view.title.text.toString()
                        for (item in items) {
                            if (item.todoitemTitle.equals(msg)) break
                            pos++
                        }
                        database.use { delete("TodoList","todoitem = {item}", "item" to msg) }
                        refresh(pos)
                        Toast.makeText(context, "Delete complete.", Toast.LENGTH_SHORT).show()
                    }
                    catch (e: Exception) { Toast.makeText(context, "Delete fail.", Toast.LENGTH_SHORT).show() }
                })
                dialog.setNegativeButton("No", null)
                dialog.show()
            }
        }
    }
}