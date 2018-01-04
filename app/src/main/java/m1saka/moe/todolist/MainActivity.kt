package m1saka.moe.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView

import moe.m1saka.todolist.MainAdapter

class MainActivity : AppCompatActivity() {

    val items = listOf(
            "Beauty of Programming",
            "ACM ICPC/CCPC",
            "Super Resolution",
            "Generative Adversavial Network",
            "Android"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val listview = findViewById(R.id.listview) as RecyclerView
        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = MainAdapter(items)

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

