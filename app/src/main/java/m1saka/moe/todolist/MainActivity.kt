package m1saka.moe.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import java.util.*

//import moe.m1saka.todolist.MainAdapter
import moe.m1saka.todolist.*

class MainActivity : AppCompatActivity() {

    /*
    val items = listOf(
            "Beauty of Programming",
            "ACM ICPC/CCPC",
            "Super Resolution",
            "Generative Adversavial Network",
            "Android",
            "6","7","8","9","10","11","12","13","14","15"
    )
    */
    var items = ArrayList<TodoItem>()

    private fun init(){
        items.add(TodoItem("Beauty of Programming", false))
        items.add(TodoItem("ACM ICPC/CCPC", true))
        items.add(TodoItem("Super Resolution", false))
        items.add(TodoItem("Generative Adversarial Network", true))
        items.add(TodoItem("Android", true))
        items.add(TodoItem("Left FFF", false))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val listview = findViewById(R.id.listview) as RecyclerView
        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = TodoListAdapter(items){
            var dialog = AlertDialog.Builder(this@MainActivity)
            dialog.setTitle("Delete this Todo item? ")
            dialog.setCancelable(true)
            dialog.setPositiveButton("Yes", {
                dialogInterface, i ->
                Toast.makeText(applicationContext, "You choose Yes.", Toast.LENGTH_SHORT).show()
            })
            dialog.setNegativeButton("No", {
                dialogInterface, i ->
                Toast.makeText(applicationContext, "You choose No.", Toast.LENGTH_SHORT).show()
            })
            dialog.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
////        return when (item.itemId) {
////            //R.id.action_settings -> true
////            //else -> super.onOptionsItemSelected(item)
////        }
//    }
}

