package com.example.appdefilmes.model

import android.os.Parcel
import android.os.Parcelable
import java.sql.Date
import java.text.DateFormat

data class Filme(
    val id: Int? = 0,
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (id != null) {
            parcel.writeInt(id)
        }
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Filme> {
        override fun createFromParcel(parcel: Parcel): Filme {
            return Filme(parcel)
        }

        override fun newArray(size: Int): Array<Filme?> {
            return arrayOfNulls(size)
        }
    }

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
