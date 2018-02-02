package com.dhorby.reactive.parsers

import com.dhorby.reactive.entities.Article
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.should.shouldMatch
import org.junit.Test


class XMLParserTest {

    @Test
    fun `should parse list of rss articles`() {

        val testArticle = Article("2", "Whale people ", "A glimpse into the life the Inupiat, an indigenous community in Alaska.", "link", "01/01/2015")
        val rss: String = this.javaClass.classLoader.getResource("test-rss.xml").readText()
        val articles = XMLParser().parseRss(rss)
        articles shouldMatch hasSize(equalTo(48))
        articles shouldMatch hasElement(testArticle)
    }

}