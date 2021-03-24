package io.kraftsman.dtos

import tw.ktrssreader.annotation.RssTag

@RssTag(name = "channel")
data class CustomChannel(
    val title: String?,
    val description: String?,
    @RssTag(name = "item")
    val items: List<CustomItem>,
)
