package com.example.baseproyect.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.OVAL
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.baseproyect.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng


object ViewUtils {
    fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int
    ): BitmapDescriptor {
        val vectorDrawable =
            ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = vectorDrawable?.intrinsicHeight?.let {
            Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                it,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        canvas?.let { vectorDrawable.draw(it) }
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getBusIcon(line: String): Int =
        when (line) {
            "500" -> {
                R.drawable.ic_autobus_yellow
            }
            "501" -> {
                R.drawable.ic_autobus
            }
            "502" -> {
                R.drawable.ic_autobus_502
            }
            "503" -> {
                R.drawable.ic_autobus_blue
            }
            "504" -> {
                R.drawable.ic_autobus_green
            }
            "505" -> {
                R.drawable.ic_autobus_marron
            }
            else -> {
                R.drawable.ic_autobus_502
            }
        }

    fun getBusColorRoute(line: String): Int =
        when (line) {
            "500" -> R.color.colorYell
            "501" -> R.color.colorRed
            "502" -> R.color.color502
            "503" -> R.color.colorGreenPressed
            "504" -> R.color.colorBlue
            "505" -> R.color.color505
            else -> R.color.colorNegro
        }

    fun getBusCard(line: String): Int =
        when (line) {
            "500" -> R.color.colorYellCard
            "501" -> R.color.colorRedCard
            "502" -> R.color.color502Card
            "503" -> R.color.colorGreenCard
            "504" -> R.color.colorBlueCard
            "505" -> R.color.color505Card
            else -> R.color.colorNegro
        }
}

