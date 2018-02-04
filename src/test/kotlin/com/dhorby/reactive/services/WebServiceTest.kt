package com.dhorby.reactive.services

import com.dhorby.reactive.dao.ArticleRepository
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@SuppressWarnings("Duplicates")
@RunWith(SpringRunner::class)
@SpringBootTest
class WebServiceTest {

    @Autowired
    private lateinit var repository: ArticleRepository

    @Test
    @Ignore
    fun `should make web request to rss feed and return a list of articles`() {

        TODO()
    }

    @Test
    fun `should consume mono`(){

        val rrsUrl: String = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml?edition=uk#"
        WebService(repository).makeRequest(rrsUrl)
        Thread.sleep(1000);

    }
}