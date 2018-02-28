package m1saka.moe.todolist

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todoitem_layout.view.*
import java.util.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.view
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var items = ArrayList<TodoItem>()
    val database: DatabaseManage
        get() = DatabaseManage.getInstance(getApplicationContext())
    private var mRecyclerViewAdapter: TodoListAdapter? = null
    private var IO : TodoListIO? = null
    private fun init(){
        var list = database.use {
            select("TodoList").parseList(classParser<TodoItem>())
        }
        items.clear()
        for (item in list) { items.add(item) }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val listview = findViewById(R.id.listview) as RecyclerView
        mRecyclerViewAdapter = TodoListAdapter(items, this@MainActivity, database)
        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = mRecyclerViewAdapter
        IO = TodoListIO(this@MainActivity, database)
        val addButton = findViewById(R.id.fab) as FloatingActionButton
        addButton.setOnClickListener{
            val et = EditText(this)
            val dialog = AlertDialog.Builder(this@MainActivity)
            dialog.setTitle("Add")
            dialog.setView(et)
            dialog.setCancelable(true)
            dialog.setPositiveButton("Yes", {
                dialogInterface, i ->
                var msg = et.text.toString()
                var check = false
                add(msg, check)
                // Toast.makeText(applicationContext, "You choose Yes.", Toast.LENGTH_SHORT).show()
            })
            dialog.setNegativeButton("Cancel", null)
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.import_item) {
            val importContents = IO?.importFromSDCard() as ArrayList<TodoItem>
            for (i in importContents) {
                add(i.todoitemTitle, i.isdone)
            }
        }
        else if (item.itemId == R.id.export_item) {
            IO?.writeToSDCard()
        }
        else if (item.itemId == R.id.exit_item) {
            this@MainActivity.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun add(item: String, isdone: Boolean) {
        var query = database.use {
            select("TodoList").whereArgs("todoitem = {item}", "item" to item).parseList(classParser<TodoItem>())
        }
        if (query.size > 0) {
            Toast.makeText(applicationContext, "Fail, item exists.", Toast.LENGTH_SHORT).show()
            return
        }
        var check: Int
        var values = ContentValues()
        if (isdone == true) check = 1 else check = 0
        values.put("todoitem", item)
        values.put("isdone", check)
        database.use {
            insert("TodoList",null, values)
        }
        var newTodoItem = TodoItem(item, isdone)
        mRecyclerViewAdapter?.refreshAdd(newTodoItem)
    }
}
