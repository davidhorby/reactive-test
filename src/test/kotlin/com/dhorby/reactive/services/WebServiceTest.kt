package com.dhorby.reactive.services

import org.junit.Test


class WebServiceTest {

    @Test
    fun makeRequestTest() {

        val rrsUrl: String = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml?edition=uk#"

        val request: String? = WebService().makeRequest(rrsUrl)?.block()
        println(request)

    }
}