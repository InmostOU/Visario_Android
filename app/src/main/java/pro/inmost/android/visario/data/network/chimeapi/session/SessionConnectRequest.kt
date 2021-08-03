package pro.inmost.android.visario.data.network.chimeapi.session

import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import java.util.*

data class SessionConnectRequest(
    @SerializedName("X-Amz-Credential")
    val credential: String,
    @SerializedName("userArn")
    val userArn: String,
    @SerializedName("X-Amz-Signature")
    val signature: String,

    @SerializedName("X-Amz-Algorithm")
    val algorithm: String = "AWS4-HMAC-SHA256",
    @SerializedName("X-Amz-Date")
    val date: String = DateFormat.format("yyyyMMddTHHmmssZ", System.currentTimeMillis()).toString(),
    @SerializedName("X-Amz-Expires")
    val expires: Int = 10,
    @SerializedName("X-Amz-SignedHeaders")
    val signedHeaders: String = "host",
    @SerializedName("sessionId")
    val sessionId: String = UUID.randomUUID().toString()
)