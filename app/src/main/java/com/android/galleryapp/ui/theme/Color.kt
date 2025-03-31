@file:Suppress("MagicNumber")

package com.android.galleryapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

/* New colors based on https://www.figma.com/file/lCXqYN2aGLpHiFAA6CjAq6/Colors?node-id=7%3A99&t=kQgUgnaaHFXg7180-0 */
object Base {
    val Black = Color(0xFF000000)
    val DarkGrey = Color(0xFF333333)
    val White = Color(0xFFFFFFFF)
}

object BrandingOrange {
    val v120 = Color(0XFFCC3600)
    val v100 = Color(0XFFFF4300)
    val v80 = Color(0XFFFF6933)
    val v60 = Color(0XFFFF8E66)
    val v40 = Color(0XFFFFB499)
    val v20 = Color(0XFFFFD9CC)
}

object BrandingRed {
    val v120 = Color(0xFFCC0101)
    val v100 = Color(0XFFFF0101)
    val v80 = Color(0XFFFF3434)
    val v60 = Color(0XFFFF6767)
    val v40 = Color(0XFFFF9999)
    val v20 = Color(0XFFFFCCCC)
}

object Charcoal {
    val v120 = Color(0XFF1A1A1A)
    val v100 = Color(0xFF202020)
    val v80 = Color(0XFF4D4D4D)
    val v60 = Color(0XFF797979)
    val v40 = Color(0XFFA6A6A6)
    val v20 = Color(0XFFD2D2D2)
}

object LightGrey {
    val v120 = Color(0XFFC4BFBB)
    val v100 = Color(0XFFDEDBD7)
    val v80 = Color(0XFFF8F4F2)
    val v60 = Color(0XFFF9F6F5)
    val v40 = Color(0XFFFBF8F7)
    val v20 = Color(0XFFFCFBFA)
}

object Grey {
    val v120 = Color(0XFFC4BFBB)
    val v100 = Color(0XFFE4E4E4)
    val v80 = Color(0XFFE9E9E9)
    val v60 = Color(0XFFEFEFEF)
    val v40 = Color(0XFFF4F4F4)
    val v20 = Color(0XFFFAFAFA)
}

object Green {
    val v120 = Color(0XFF138942)
    val v100 = Color(0XFF18AB53)
    val v80 = Color(0XFF46BC75)
    val v60 = Color(0XFF74CD98)
    val v40 = Color(0XFFA3DDBA)
    val v20 = Color(0XFFD1EEDD)
}

object Yellow {
    val v120 = Color(0XFFCC8900)
    val v100 = Color(0XFFFFAB00)
    val v80 = Color(0XFFFFBC33)
    val v60 = Color(0XFFFFCD66)
    val v40 = Color(0XFFFFDD99)
    val v20 = Color(0XFFFFEECC)
}

object Red {
    val v120 = Color(0XFFBB1533)
    val v100 = Color(0XFFEA1A40)
    val v80 = Color(0XFFEE4866)
    val v60 = Color(0XFFF27685)
    val v40 = Color(0XFFF7A3AD)
    val v20 = Color(0XFFFBD1D6)
}

object Purple {
    val v120 = Color(0XFF624ECC)
    val v100 = Color(0XFF7B61FF)
    val v80 = Color(0XFF9581FF)
    val v60 = Color(0XFFB0A0FF)
    val v40 = Color(0XFFCAC0FF)
    val v20 = Color(0XFFE5DFFF)
}

object Transparent {
    val BlackAlpha80 = Color(0XCC000000)
    val WhiteAlpha10 = Color(0X1AFFFFFF)
}

fun Color.isDark() = luminance() < BRIGHT_LEVEL

private const val BRIGHT_LEVEL = 0.5
