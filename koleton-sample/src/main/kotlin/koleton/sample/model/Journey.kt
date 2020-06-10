package koleton.sample.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Journey(
    val date: String,
    val pickUpPoint: String,
    val dropOffPoint: String,
    val pickUpTime: String,
    val dropOffTime: String,
    val driverName: String,
    val carName: String,
    val basePrice: String,
    val servicePrice: String,
    val total: String,
    val paymentMethod: String,
    @DrawableRes val driverImage: Int,
    @DrawableRes val carIcon: Int,
    @DrawableRes val mapImage: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(pickUpPoint)
        parcel.writeString(dropOffPoint)
        parcel.writeString(pickUpTime)
        parcel.writeString(dropOffTime)
        parcel.writeString(driverName)
        parcel.writeString(carName)
        parcel.writeString(basePrice)
        parcel.writeString(servicePrice)
        parcel.writeString(total)
        parcel.writeString(paymentMethod)
        parcel.writeInt(driverImage)
        parcel.writeInt(carIcon)
        parcel.writeInt(mapImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Journey> {
        override fun createFromParcel(parcel: Parcel): Journey {
            return Journey(parcel)
        }

        override fun newArray(size: Int): Array<Journey?> {
            return arrayOfNulls(size)
        }
    }
}