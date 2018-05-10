package com.example.demo.actors.db

import java.sql.DriverManager

import com.example.demo.actors.db.DbConnection.{driver, password, url, username}


object DbConnection {
  val url: String = "jdbc:mysql://teamlul.cqtdfz66koiq.eu-west-2.rds.amazonaws.com:3306/testS?zeroDateTimeBehavior=convertToNull"
  val driver = "com.mysql.jdbc.Driver"
  val username: String = "root"
  val password: String = "starbucks"

  def query(bookId: Long, page: Int): String = {
    println("start query" + page)
    Class.forName(driver)
    val connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement
    val rs = statement.executeQuery("SELECT content FROM page WHERE number=" + page)
    var res: String = ""
    while (rs.next) {
      val host = rs.getString("content")
      res = host
    }
    connection.close()
    res
  }
}
