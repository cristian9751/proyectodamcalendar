package com.cristian.calendarapp.data

import android.annotation.SuppressLint
import android.icu.text.DateFormatSymbols
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Suppress("EXTERNAL_SERIALIZER_USELESS")
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
class DateSerializer : KSerializer<Date> {
    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).apply {
        this.timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(format.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        return format.parse(decoder.decodeString()) ?: Date()
    }
}