package m1saka.moe.todolist

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*

class DatabaseManage(ctx: Context): ManagedSQLiteOpenHelper(ctx, "TodoList") {

    companion object {
        private var instance: DatabaseManage? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseManage {
            if (instance == null) {
                instance = DatabaseManage(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("TodoList", true,
                "todoitem" to TEXT,
                "isdone" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable("TodoList",true)
        onCreate(db)
    }
}


