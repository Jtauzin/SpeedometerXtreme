package com.bronzeswordstudios.speedometer.query

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

class Query {

    companion object {
        fun makeHTTPRequest(url: URL): String? {

            // set up query variables
            var jsonResponse: String? = null
            var urlConnection: HttpsURLConnection? = null
            var inputStream: InputStream? = null

            // attempt connection
            try {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 10000
                urlConnection.connectTimeout = 15000
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                // if the connection works we get a 200 response
                // then read the stream and convert it to our response
                if (urlConnection.responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                } else {
                    Log.e("Error: ", "makeHTTPRequest: " + urlConnection.responseCode + " code.")
                }

                // check if failed to connect
            } catch (e: Exception) {
                Log.e("Error: ", "makeHTTPRequest: $e")
            }

            // close connection when we are finished
            finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
            return jsonResponse
        }

        // here we translate the response stream into a string
        @Throws(IOException::class)
        fun readFromStream(input: InputStream?): String {
            val stringBuilder = StringBuilder()
            val inputStreamReader = InputStreamReader(input, Charset.forName("UTF-8"))
            val bufferedReader = BufferedReader(inputStreamReader)
            var line = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }
            return stringBuilder.toString()
        }

        fun extractFromJson(inputJson: String?): String? {
            var responseString: String? = null
            try {
                val jsonResponse = JSONObject(inputJson)
                responseString = jsonResponse.getString("value")
            } catch (e: JSONException) {
                Log.e("JSON Error", "extractFromJson: $e")
            }
            return responseString
        }

        fun collectData(urlString: String): String? {
            val url = URL(urlString)
            val jsonResponse: String? = makeHTTPRequest(url)
            return extractFromJson(jsonResponse)
        }

    }


}