package com.slowAndSteady.slowdy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
 class UserEntity (
    @PrimaryKey(autoGenerate = false) val userID : String = "",
    @ColumnInfo val userName  : String? = null,
    @ColumnInfo val userEmail : String? = null
) : BaseEntity(userID) {
}
