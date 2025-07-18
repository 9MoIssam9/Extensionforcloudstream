package com.flowind

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.Jsoup

class Flowind : MainAPI() {
    override var mainUrl = "https://flowind.net"
    override var name = "Flowind"
    override val hasMainPage = false
    override val lang = "ar"
    override val supportedTypes = setOf(TvType.Anime, TvType.Cartoon, TvType.Movie, TvType.TvSeries)

    override suspend fun load(url: String): LoadResponse? {
        val doc = app.get(url).document
        val title = doc.select("h1").text()
        val poster = doc.select("img.poster").attr("src")

        val episode = Episode(
            url = url,
            name = "Watch",
        )
        return newTvSeriesLoadResponse(title, url, TvType.Cartoon) {
            posterUrl = poster
            episodes = listOf(episode)
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        val iframe = doc.select("iframe").attr("src")
        loadExtractor(iframe, data, subtitleCallback, callback)
        return true
    }
}