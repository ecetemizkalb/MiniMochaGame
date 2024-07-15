package com.example.ecetemizkalb_hw1.cat

import android.os.Parcel
import android.os.Parcelable

class Cat() : Parcelable {

    private var name: String? = null
    private var imgId = 0
    private var imgNum = 0
    private var mood: String?= null
    private var emojiId = 0

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        imgId = parcel.readInt()
        imgNum = parcel.readInt()
        mood = parcel.readString()
        emojiId = parcel.readInt()
    }

    constructor(name: String?, imgId: Int, imgNum: Int, mood: String?, emojiId: Int) : this() {
        this.name = name
        this.imgId = imgId
        this.imgNum = imgNum
        this.mood = mood
        this.emojiId = emojiId
    }


    fun getEmojiId(): Int? {
        return emojiId
    }

    fun getMood(): String? {
        return mood
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImgId(): Int {
        return imgId
    }

    fun setImgId(imgId: Int) {
        this.imgId = imgId
    }

    fun isMoodMatch(mood: String?): Boolean {
        return this.mood.equals(mood)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imgId)
        parcel.writeInt(imgNum)
        parcel.writeString(mood)
        parcel.writeInt(emojiId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cat> {
        override fun createFromParcel(parcel: Parcel): Cat {
            return Cat(parcel)
        }

        override fun newArray(size: Int): Array<Cat?> {
            return arrayOfNulls(size)
        }
    }
}