package m1saka.moe.todolist

import android.content.Context
import android.os.Environment.*
import android.widget.Toast
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.json.JSONObject
import java.io.*

/**
 * Created by DongSky on 2018/2/28.
 */
class TodoListIO(var _context: Context, database: DatabaseManage) {
    val database = database
    var context = _context
    val sdcardDir = getExternalStorageDirectory()
    val todoListDir = File(sdcardDir.absolutePath + "/todolist")
    var todoListFile : File? = null
    fun writeToSDCard(): Int {
        Toast.makeText(context, sdcardDir.absolutePath, Toast.LENGTH_LONG).show()
        if (todoListDir.exists() == false) {
            if (todoListDir.mkdirs() == false) {
                Toast.makeText(context, "Directory create failed.", Toast.LENGTH_LONG).show()
                return -1
            }
            else {
                Toast.makeText(context, "Directory create succeed.", Toast.LENGTH_LONG).show()
            }
        }
        var list = database.use {
            select("TodoList").parseList(classParser<TodoItem>())
        }
        var jsonObj = JSONObject()
        for (item in list) {
            jsonObj.put(item.todoitemTitle, item.isdone)
        }
        todoListFile = File(todoListDir.absolutePath + "/backup.json")
        if (todoListFile?.exists() == false) {
            if (todoListFile?.createNewFile() == false) {
                Toast.makeText(context, "File create failed.", Toast.LENGTH_LONG).show()
                return -2
            }
            else {
                Toast.makeText(context, "File create succeed.", Toast.LENGTH_LONG).show()
            }
        }
        var out = BufferedWriter(FileWriter(todoListFile))
        out.write(jsonObj.toString())
        out.flush()
        out.close()
        Toast.makeText(context, "Write complete.", Toast.LENGTH_LONG).show()
        return 0
    }

    fun importFromSDCard(): ArrayList<TodoItem> {
        val result = ArrayList<TodoItem>()
        todoListFile = File(todoListDir.absolutePath + "/backup.json")
        if (todoListFile?.exists() == false) {
            Toast.makeText(context, "File does not exist.", Toast.LENGTH_LONG).show()
            return result
        }
        var strin = BufferedReader(FileReader(todoListFile))
        var jsonStr = strin.readLine()
        print(jsonStr)
        var jsonObj = JSONObject(jsonStr)
        var keys = jsonObj.keys()
        for (key in keys) {
            var item = key
            var isdone = jsonObj.get(key)
            result.add(TodoItem(item, isdone as Boolean))
        }
        return result
    }
}