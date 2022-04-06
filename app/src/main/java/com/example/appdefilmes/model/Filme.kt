package com.example.appdefilmes.model

import android.os.Parcel
import android.os.Parcelable
import java.sql.Date
import java.text.DateFormat

class Filme(
    val id: Int? = 0,
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
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
        if (vote_average != null) {
            parcel.writeDouble(vote_average)
        }
        if (vote_count != null) {
            parcel.writeInt(vote_count)
        }
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
