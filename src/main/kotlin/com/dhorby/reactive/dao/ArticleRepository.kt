package com.dhorby.reactive.dao

import com.dhorby.reactive.entities.Article
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ArticleRepository : ReactiveCrudRepository<Article, Integer> {
}