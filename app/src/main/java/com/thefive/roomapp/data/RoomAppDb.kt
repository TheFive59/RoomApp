package com.thefive.roomapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 3)
abstract class RoomAppDb : RoomDatabase() {


    abstract fun userDao(): UserDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null


        fun getAppDatabase(context: Context): RoomAppDb? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    "user_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }

    }
}