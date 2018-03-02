package com.dhorby.reactive.controllers

import org.junit.Before
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(ViewController::class)
internal class ViewControllerTest {

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
    fun `should say Hello World` () {
        mvc.perform(MockMvcRequestBuilders.get("/hello")
                .param("name", "World")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.view().name("hello"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", "World"))
    }


}