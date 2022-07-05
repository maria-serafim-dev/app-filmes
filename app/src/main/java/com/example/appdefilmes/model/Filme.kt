package com.example.appdefilmes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date
import java.text.DateFormat

@Parcelize
data class Filme(
    val id: Int? = 0,
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
) : Parcelable {

    fun formatarDataDeAcordoComALocalidade(): String{

        var dataFinal = ""
        if(release_date?.isNotEmpty() == true) {
            val data = Date.valueOf(release_date)
            val format =  DateFormat.getDateInstance(DateFormat.SHORT)
            dataFinal = format.format(data)
        }
        return dataFinal
    }
}
