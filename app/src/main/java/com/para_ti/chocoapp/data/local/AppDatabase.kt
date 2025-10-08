package com.para_ti.chocoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.para_ti.chocoapp.data.local.dao.ProductDao
import com.para_ti.chocoapp.data.local.dao.UserDao
import com.para_ti.chocoapp.data.local.entity.UserEntity
import com.para_ti.chocoapp.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Product::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // si ya existe la instancia, la devolvemos
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "choco_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // ✅ usamos INSTANCE una vez creada (no “instance” local)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.userDao()?.insertUser(
                                    UserEntity(
                                        username = "gerardo",
                                        password = "123",
                                        email = "gerardo@example.com"
                                    )
                                )
                            }
                        }
                    })
                    .build()

                INSTANCE = dbInstance
                return dbInstance
            }
        }
    }
}
