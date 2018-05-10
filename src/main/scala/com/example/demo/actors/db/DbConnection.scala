package com.example.demo.actors.db

import java.sql.DriverManager

import com.example.demo.actors.db.DbConnection.{driver, password, url, username}


class DbConnection {

  def query() {
    try {
      println("start query")
      Class.forName(driver)
      val connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM page")

      while (rs.next) {
        val host = rs.getString("content")
        println(host)
      }
      connection.close()
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
}

object DbConnection {
  val url:String = "jdbc:mysql://teamlul.cqtdfz66koiq.eu-west-2.rds.amazonaws.com:3306/testS?zeroDateTimeBehavior=convertToNull"
  val driver = "com.mysql.jdbc.Driver"
  val username:String = "root"
  val password:String = "starbucks"

}
