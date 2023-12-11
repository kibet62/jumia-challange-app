package com.example.jumiachallange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jumiachallange.data.local.dao.ConfigurationsDao
import com.example.jumiachallange.model.configarations.Configurations


@Database(entities = [Configurations::class], version = 1)
abstract class JumiaDatabase : RoomDatabase() {

    abstract fun configurationsDao(): ConfigurationsDao

}