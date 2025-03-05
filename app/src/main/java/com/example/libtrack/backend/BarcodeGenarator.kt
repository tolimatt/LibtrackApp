package com.example.libtrack.backend

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

fun generateBarcode(text: String): Bitmap {
    val writer = MultiFormatWriter()
    val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.CODE_128, 1200, 900)

    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }

    return bmp
}

@Composable
fun BarcodeDisplay(text: String) {
    val barcodeBitmap = generateBarcode(text)
    Image(
        bitmap = barcodeBitmap.asImageBitmap(),
        contentDescription = "Generated Barcode",
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Fit
    )
}

