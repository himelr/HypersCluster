package com.example.demo.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.client.ClusterClient.Publish
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import com.example.demo.actors.db.DbConnection

import scala.util.Random

object SubscribeActor {
  def props(): Props = Props(new SubscribeActor)
}

class SubscribeActor extends Actor with ActorLogging {
  val r: Random.type = scala.util.Random
  var pages: Array[String] = _
  var book_id: Long = _
  val mediator: ActorRef = DistributedPubSub(context.system).mediator
  var frontEnd: ActorRef = _
  // subscribe to the topic named "content"
  mediator ! Subscribe("content", self)
  mediator ! Subscribe("communicate", self)

  def receive = {
    case s: String ⇒ println("helllo" + s)

    case list: Array[String] => compare(list)

    case start: (Int, Long) =>
      frontEnd = sender()
      book_id = start _2;
      pages = new Array[String](start _1)
      getPage()
  }

  def checkIfFull = {
    var check = true
    pages.foreach(content => if(content == null) check = false)

    if(!check) getPage()
    else frontEnd ! pages

    //mediator ! Publish("communicate", pages)
  }
  def getPage(): Unit = {
    val pageNumber = r.nextInt(pages.length) + 1
    pages(pageNumber - 1) = DbConnection.query(book_id, pageNumber)
    mediator ! Publish("communicate", pages)
    checkIfFull
  }

  def compare(receivedPages: Array[String]): Unit = {
    val newPages = new Array[String](receivedPages.length)

    for(a <- receivedPages.indices) {
      if(receivedPages(a) == null && pages(a) != null) {
        newPages(a) = pages(a)
      }
      else if(receivedPages(a) != null && pages(a) == null) {
        newPages(a) = receivedPages(a)
      }
      else newPages(a) = pages(a)
    }
    pages = newPages
    mediator ! Publish("communicate", pages)

  }

}
