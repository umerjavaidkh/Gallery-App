package com.android.galleryapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.android.galleryapp.R

val Roboto = FontFamily(
    Font(R.font.roboto_thin, FontWeight.W100),
    Font(R.font.roboto_light, FontWeight.W300),
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_black, FontWeight.W900)
)

object GalleryTypography {
    val defaultFontFamily = Roboto
    private val defaultTextColor = Charcoal.v100

    object Digits {
        val XL = GalleryTextStyle(
            fontWeight = FontWeight.W300,
            fontSize = 88.sp,
            lineHeight = 88.sp
        )
        val L = GalleryTextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 44.sp,
            lineHeight = 44.sp
        )
        val M = GalleryTextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 28.sp,
            lineHeight = 28.sp
        )
        val S = GalleryTextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 24.sp,
            lineHeight = 28.sp
        )

        object Strong {
            val XL = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 88.sp,
                lineHeight = 88.sp
            )
        }
    }

    object Heading {
        val h1 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 32.sp,
            lineHeight = 38.sp
        )
        val h2 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 24.sp,
            lineHeight = 30.sp
        )
        val h3 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )
        val h4 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
        val h5 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        val h6 = GalleryTextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }

    object Body {

        val L = GalleryTextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )

        val M = GalleryTextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 22.sp
        )

        val S = GalleryTextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 20.sp
        )

        object Strong {
            val L = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            val M = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            val S = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 12.sp,
                lineHeight = 20.sp
            )
        }
    }

    object Label {
        object Caps {
            val M = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )

            val S = GalleryTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }

        val M = GalleryTextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )

        val S = GalleryTextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }

    @Suppress("FunctionNaming")
    private fun GalleryTextStyle(
        fontWeight: FontWeight,
        fontSize: TextUnit,
        lineHeight: TextUnit
    ) = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = lineHeight,
        color = defaultTextColor
    )
}