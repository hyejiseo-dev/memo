package com.hyejis.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)  //항상 필요함!
    var id:Long?,
    var memo:String = "")
