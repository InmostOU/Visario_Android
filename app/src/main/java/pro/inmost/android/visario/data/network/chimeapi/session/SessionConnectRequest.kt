package pro.inmost.android.visario.data.network.chimeapi.session

import android.text.format.DateFormat
import android.util.Base64
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pro.inmost.android.visario.data.network.chimeapi.services.Endpoints.SECRET_KEY
import pro.inmost.android.visario.data.network.utils.sha256
import pro.inmost.android.visario.data.network.utils.urlEncode
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


data class SessionConnectRequest(
    @Expose
    @SerializedName("X-Amz-Algorithm")
    val algorithm: String = "HMAC-SHA256",
    @Expose
    @SerializedName("X-Amz-Credential")
    var credential: String = "",
    @Expose
    @SerializedName("X-Amz-Date")
    val date: String = DateFormat.format("yyyyMMddTHHmmssZ", System.currentTimeMillis()).toString(),
    @Expose
    @SerializedName("X-Amz-Expires")
    val expires: Int = 3600,
    @Expose
    @SerializedName("X-Amz-SignedHeaders")
    val signedHeaders: String = "host",
    @Expose
    @SerializedName("sessionId")
    val sessionId: String = UUID.randomUUID().toString(),
    @Expose
    @SerializedName("userArn")
    val userArn: String = "arn:aws:chime:us-east-1:277431928707:app-instance/c9f0aa1c-74c7-49af-8b75-b650f128511c",
    @Expose
    @SerializedName("X-Amz-Signature")
    var signature: String = "",
) {
    private val timestamp: String = DateFormat.format("yyyyMMdd", System.currentTimeMillis()).toString()


    fun signAndGetRequestUrl(endpoint: String): String {
        val method = "GET"
        val service = "chime"
        val region = "us-east-1"
        val canonicalUri = "/connect"
        val canonicalHeaders = "host:$endpoint\n"
        val canonicalQuery = getCanonicalQuery()
        val canonicalRequest = method + "\n" +
                canonicalUri + "\n" +
                canonicalQuery + "\n" +
                canonicalHeaders + "\n" +
                signedHeaders + "\n"
        val stringToSign = algorithm + "\n" +
                date + "\n" +
                credential + "\n" +
                canonicalRequest.sha256()
        val signingKey = getSignatureKey(SECRET_KEY, timestamp, region, service)
        signature = sign(signingKey, stringToSign)
        return "wss://$endpoint$canonicalUri?$canonicalQuery"
    }

    private fun getCanonicalQuery(): String {
        var canonicalQuery = ""
        canonicalQuery += "X-Amz-Algorithm=$algorithm"
        canonicalQuery += "&X-Amz-Credential=${credential.urlEncode()}"
        canonicalQuery += "&X-Amz-Date=$date"
        canonicalQuery += "&X-Amz-Expires=$expires"
     //   canonicalQuerystring += "&X-Amz-Security-Token=" + urllib.parse.quote(session_token, safe='')
        canonicalQuery += "&X-Amz-SignedHeaders=$signedHeaders"
        canonicalQuery += "&sessionId=$sessionId"
        canonicalQuery += "&userArn=${userArn.urlEncode()}"
        return canonicalQuery
    }

    private fun generateSignature(
        securityToken: String,
        requestTime: String,
        requestBody: String
    ): String {
        return try {
            val mac = Mac.getInstance(algorithm)
            val key = SecretKeySpec(securityToken.toByteArray(UTF_8), algorithm)
            mac.init(key)
            val data = "$requestTime|$requestBody"
            val rawHmac = mac.doFinal(data.toByteArray(UTF_8))
            Base64.encodeToString(rawHmac, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun getSignatureKey(
        key: String,
        dateStamp: String,
        regionName: String,
        serviceName: String
    ): String {
        return try {
            val kDate = sign("AWS4$key", dateStamp)
            val kRegion = sign(kDate, regionName)
            val kService = sign(kRegion, serviceName)
            sign(kService, "aws4_request")
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun sign(key: String, msg: String): String {
        val mac = Mac.getInstance(algorithm)
        val keySpec = SecretKeySpec(key.toByteArray(UTF_8), algorithm)
        mac.init(keySpec)
        val rawHmac = mac.doFinal(msg.toByteArray(UTF_8))
        return Base64.encodeToString(rawHmac, Base64.DEFAULT)
    }
}