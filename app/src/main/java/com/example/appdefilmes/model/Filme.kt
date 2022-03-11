package com.example.appdefilmes.model

import android.os.Parcel
import android.os.Parcelable

class Filme(
    val backdrop_path: String?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdrop_path)
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

    companion object CREATOR : Parcelable.Creator<UmFilme> {
        override fun createFromParcel(parcel: Parcel): UmFilme {
            return UmFilme(parcel)
        }

        override fun newArray(size: Int): Array<UmFilme?> {
            return arrayOfNulls(size)
        }
    }
}
