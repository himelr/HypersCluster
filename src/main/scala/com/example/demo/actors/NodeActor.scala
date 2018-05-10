package com.example.demo.actors

import akka.actor.Actor
import com.example.demo.MyMsg

class NodeActor extends Actor {
  override def receive: Receive = {
    case MyMsg(i) => {println(s"$self: $i"); Thread.sleep(700)}
    case _ => println("unknown")
  }
}

