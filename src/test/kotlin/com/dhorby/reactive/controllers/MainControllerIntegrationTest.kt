package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@WebMvcTest(MainController::class)
class MainControllerIntegrationTest {

    @MockBean
    lateinit var repository:ArticleRepository;

    @Autowired
    lateinit var wac: WebApplicationContext;

    lateinit var mvc: MockMvc;

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun testHello() {
        mvc.perform(get("/hello").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk)
                .andExpect(view().name("hello"))
                .andExpect(model().attribute("user", "World"))
    }

    @Test
    @Ignore
    fun `should save an article`() {

        val article = Article("1", "Title 1", "Desc 1", "link", "01/01/2015")


        val mapper = ObjectMapper()
        val articleJson = mapper.writeValueAsString(article)
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(articleJson))
                .andExpect(status().isCreated)

    }

}