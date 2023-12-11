package com.example.jumiachallange.model.configarations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jumiachallange.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = Constants.CONFIGURATIONS_TABLE)
@Serializable
data class Configurations(

    @PrimaryKey(autoGenerate = true)
    var iid: Int? = null,
    @SerialName("success")
    var success: Boolean,
    @Embedded
    var session: Session,
    @Embedded
    var metadata: Metadata
)