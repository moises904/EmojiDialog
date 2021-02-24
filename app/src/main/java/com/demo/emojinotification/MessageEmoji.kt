package com.demo.emojinotification

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MessageEmoji(@SerializedName("visible") val visible:Boolean,
                        @SerializedName("type") val type:String,
                        @SerializedName("title") val title:String,
                        @SerializedName("description") val description:String) : Serializable