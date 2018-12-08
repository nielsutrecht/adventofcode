package com.nibado.projects.advent

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URI
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


data class Leaderboard(@JsonProperty("owner_id") val ownerId: Int, val event: Int, val members: Map<String, Member>)

data class Member(
        val id: String,
        val name: String?,
        @JsonProperty("global_score") val globalScore: Int,
        @JsonProperty("local_score") val localScore: Int,
        @JsonProperty("last_star_ts") val lastStar: Int,
        val stars: Int,
        @JsonProperty("completion_day_level") val days: Map<String, Challenge>)

data class Challenge(@JsonProperty("1") val part1: Completion?, @JsonProperty("2") val part2: Completion?)
data class Completion(@JsonProperty("get_star_ts") val time: LocalDateTime)

class TimeStampDeserialiser : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(p0: JsonParser, p1: DeserializationContext): LocalDateTime {
        val instant = p0.valueAsString.toLong() * 1000

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(instant), ZoneId.systemDefault());
    }

}

fun load(id: Int) : String {
    val url = "https://adventofcode.com/2018/leaderboard/private/view/$id.json"

    URI(url).toURL().openStream().use {
        return it.bufferedReader().readText()
    }
}

fun main(args: Array<String>) {
    val id = args.first().toInt()

    val module = SimpleModule()
    module.addDeserializer(LocalDateTime::class.java, TimeStampDeserialiser())

    val mapper = ObjectMapper().registerKotlinModule()
    mapper.registerModule(module)

    val board: Leaderboard = mapper.readValue(Leaderboard::class.java.getResourceAsStream("/leaderboard-$id.json"))
    val members = board.members.values.filter { it.days.isNotEmpty() }.sortedBy { it.localScore }.reversed()

    for (m in members) {
        println("${m.name}:")

        val days = m.days.entries.sortedBy { it.key.toInt() }

        for (d in days) {
            val delta = if (d.value.part1 != null && d.value.part2 != null) {
                ChronoUnit.MINUTES.between(d.value.part1!!.time, d.value.part2!!.time)
            } else {
                0L
            }
            println("  ${d.key} : ${format(d.value.part1?.time)} ${format(d.value.part2?.time)} ($delta)")
        }
    }
}

fun format(dt: LocalDateTime?) =
    dt?.format(DateTimeFormatter.ofPattern("dd HH:mm:ss")) ?: ""
