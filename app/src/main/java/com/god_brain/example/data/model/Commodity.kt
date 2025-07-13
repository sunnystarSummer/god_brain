package com.god_brain.example.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.math.BigInteger

class CommodityInfo(
    private var data: String,
) {

    init {
        data = data.replace("\'", "\"")
    }

    fun toCommodityList(): List<Commodity> {

        val formatted = data

        //ToDo: Parse the formatted data into a list of Commodity objects
        val gson = Gson()
        val type = object : TypeToken<List<Commodity>>() {}.type

        return gson.fromJson(formatted, type)
            ?: emptyList()
    }
}


/**
 * 商品
 */
@Parcelize
data class Commodity(
    val finalPrice: Int,
    val martName: String,
    val stockAvailable: Int,
    val price: Int,
    val martShortName: String,
    val imageUrl: String,
    val martId: Int,
): Parcelable