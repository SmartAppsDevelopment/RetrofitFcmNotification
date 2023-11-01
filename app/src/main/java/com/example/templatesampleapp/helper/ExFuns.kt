package com.example.templatesampleapp.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import java.io.File

/**
 * @author Umer Bilal
 * Created 10/26/2023 at 12:37 PM
 */
fun getChuckerInterceptor(context:Context):ChuckerInterceptor{
    // Create the Collector
    val chuckerCollector = ChuckerCollector(
        context = context,
        // Toggles visibility of the notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

// Create the Interceptor
   return ChuckerInterceptor.Builder(context)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // List of headers to replace with ** in the Chucker UI
        .redactHeaders("Auth-Token", "Bearer")
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        // Use decoder when processing request and response bodies. When multiple decoders are installed they
        // are applied in an order they were added.
//        .add(decoder)
        // Controls Android shortcut creation.
//        .createShortcut(true)
        .build()
//    return chuckerCollector
}


fun Context.showToast(msg:String){
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}


// Request code for selecting a PDF document.
const val PICK_PDF_FILE = 2

fun AppCompatActivity.openFile() = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
//        putExtra(DocumentsContract.EXTRA_INITIAL_URI,null)
    }




