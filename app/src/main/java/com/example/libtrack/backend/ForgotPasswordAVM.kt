package com.example.libtrack.backend

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


fun sendOTP(email: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP+SEND_OTP_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)

    val request = JsonObjectRequest(
        Request.Method.POST,
        url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "OTP sent to $email", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            onSuccess(false)
        }
    )
    Volley.newRequestQueue(context).add(request)
}


fun verifyOTP(email: String, otp: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP+ VERIFY_OTP_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)
    jsonObject.put("otp", otp)

    val request = JsonObjectRequest(
        Request.Method.POST, url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "OTP verified!", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )

    Volley.newRequestQueue(context).add(request)
}



fun updatePassword(email: String, newPassword: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP + UPDATE_PASSWORD_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)
    jsonObject.put("new_password", newPassword)

    val request = JsonObjectRequest(
        Request.Method.POST, url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )

    Volley.newRequestQueue(context).add(request)
}
