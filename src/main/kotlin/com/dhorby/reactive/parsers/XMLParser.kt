package com.dhorby.reactive.parsers

import com.dhorby.reactive.entities.Article
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory


class XMLParser {

    fun parseRss(rss:String): MutableList<Article> {

        var articleList:MutableList<Article> = mutableListOf<Article>()
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()

        val stream = ByteArrayInputStream(rss.toByteArray())
        val doc = dBuilder.parse(stream)

        doc.documentElement.normalize()

        val nList: NodeList = doc.getElementsByTagName("item")

        for (temp in 0 until nList.length) {

            val nNode = nList.item(temp)

            when (nNode.nodeType) {
                Node.ELEMENT_NODE -> {
                    val eElement = nNode as Element

                    val article = Article(eElement.getElementsByTagName("guid").item(0).getTextContent(),
                            eElement.getElementsByTagName("title").item(0).getTextContent(),
                            eElement.getElementsByTagName("description").item(0).getTextContent(),
                            eElement.getElementsByTagName("link").item(0).getTextContent(),
                            eElement.getElementsByTagName("pubDate").item(0).getTextContent())
                    articleList.add(article)
                }
            }
        }
        return articleList
    }
}