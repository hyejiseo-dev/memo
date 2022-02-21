package com.hyejis.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//데이터베이스를 바꾸게 되면 버전을 올려야함
@Database(entities = arrayOf(MemoEntity::class),version = 1)
abstract class MemoDatabase : RoomDatabase(){
    abstract fun memoDAO():MemoDAO

    companion object{
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context:Context) : MemoDatabase?{
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db").fallbackToDestructiveMigration().build() // 버전이 업그레이드 되면 이전 데이터는 모두 없애고 다시 만든다
                }
            }

            return INSTANCE
        }
    }
}