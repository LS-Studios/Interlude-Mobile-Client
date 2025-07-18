package de.stubbe.interlude.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

object Constants {

    // Padding
    val PaddingXXSmall: Dp = 2.dp
    val PaddingXSmall: Dp = 4.dp
    val PaddingSmall: Dp = 8.dp
    val PaddingMedium: Dp = 12.dp
    val PaddingLarge: Dp = 16.dp
    val PaddingXLarge: Dp = 24.dp
    val PaddingXXLarge: Dp = 32.dp

    // Font Sizes
    val FontSizeXSmall: TextUnit = 12.sp
    val FontSizeSmall: TextUnit = 14.sp
    val FontSizeMedium: TextUnit = 16.sp
    val FontSizeLarge: TextUnit = 20.sp
    val FontSizeXLarge: TextUnit = 24.sp
    val FontSizeXXLarge: TextUnit = 28.sp
    val FontSizeHuge: TextUnit = 38.sp

    // Corner Radii
    val RadiusSmall: Dp = 8.dp
    val RadiusMedium: Dp = 16.dp
    val RadiusLarge: Dp = 18.dp
    val RadiusXLarge: Dp = 24.dp
    val RadiusXXLarge: Dp = 28.dp

    // Icon Sizes
    val IconSizeSmall: Dp = 18.dp
    val IconSizeMedium: Dp = 22.dp
    val IconSizeLarge: Dp = 28.dp
    val IconSizeXLarge: Dp = 32.dp

    // Card Sizes
    val CardHeightSmall: Dp = 70.dp
    val CardHeightMedium: Dp = 85.dp
    val CardHeightLarge: Dp = 120.dp
    val CardHeightXLarge: Dp = 220.dp

    // Spacing
    val SpacerSmall: Dp = 4.dp
    val SpacerMedium: Dp = 8.dp
    val SpacerLarge: Dp = 16.dp
    val SpacerXLarge: Dp = 24.dp

    // Sizes (for decorative elements, etc.)
    val SizeXXXSmall: Dp = 16.dp
    val SizeXXSmall: Dp = 20.dp
    val SizeXSmall: Dp = 26.dp
    val SizeSmall: Dp = 36.dp
    val SizeSmall2: Dp = 40.dp
    val SizeSmall3: Dp = 44.dp
    val SizeMedium: Dp = 48.dp
    val SizeMedium2: Dp = 56.dp
    val SizeLarge: Dp = 64.dp
    val SizeXLarge: Dp = 80.dp
    val SizeXXLarge: Dp = 96.dp
    val SizeHuge: Dp = 120.dp
    val SizeHuge2: Dp = 100.dp
    val SizeHuge3: Dp = 90.dp

    // Button Heights
    val ButtonHeightSmall: Dp = 48.dp
    val ButtonHeightMedium: Dp = 56.dp
    val ButtonHeightLarge: Dp = 60.dp

    // Special Values
    val ValueLarge: Dp = 180.dp
    val ValueXLarge: Dp = 200.dp
    val ValueXXLarge: Dp = 220.dp
    val ValueHuge: Dp = 260.dp
    val ValueHuge2: Dp = 140.dp
    val ValueHuge3: Dp = 170.dp

    // Offsets (commonly used offset values)
    val OffsetSmall: Dp = 10.dp
    val OffsetMedium: Dp = 16.dp
    val OffsetLarge: Dp = 20.dp
    val OffsetXLarge: Dp = 24.dp
    val OffsetLarge2: Dp = 30.dp
    val OffsetXXLarge: Dp = 32.dp
    val OffsetHuge: Dp = 40.dp
    val OffsetHuge2: Dp = 60.dp

    // Elevation
    val ElevationSmall: Dp = 1.dp
    val ElevationMedium: Dp = 2.dp

    // Border
    val BorderWidthSmall: Dp = 1.dp
    val BorderWidthMedium: Dp = 1.5.dp
    val BorderWidthLarge: Dp = 2.dp

    object Shape {
        val TinyValue: Dp = 4.dp
        val SmallValue: Dp = 12.dp
        val MediumValue: Dp = 24.dp

        object RoundedTop {
            val Small = RoundedCornerShape(SmallValue, SmallValue, 0.dp, 0.dp)
            val Medium = RoundedCornerShape(MediumValue, MediumValue, 0.dp, 0.dp)
        }
        object RoundedBottom {
            val Small = RoundedCornerShape(0.dp, 0.dp, SmallValue, SmallValue)
            val Medium = RoundedCornerShape(0.dp, 0.dp, MediumValue, MediumValue)
        }
        object Rounded {
            val Tiny = RoundedCornerShape(TinyValue)
            val Small = RoundedCornerShape(SmallValue)
            val Medium = RoundedCornerShape(MediumValue)
        }

        val Rectangle = RoundedCornerShape(0.dp)
    }
}