package com.example.demo

import akka.actor.{ActorSystem, Props}
import akka.cluster.routing.{ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing.RoundRobinPool
import com.example.demo.actors.{NodeActor, SubscribeActor}
import com.typesafe.config.ConfigFactory

case class MyMsg(n: Int)

object ClusterMain extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("hyperscluster")
  // run jar with command java -DPORT=2555 -DHOSTNAME=127.0.0.1 -jar hyperscluster-assembly-1.0.jar
  // (replace ip address with your address)
  // this will create seed node and router towards the cluster (see below)
  // (to create jar, go to view -> tool windows -> sbt shell, and issue command 'assembly')
  // start other cluster node by using port number other than 2551 and 2555
  // to start traffic generation, run the jar with port number 2555 (see code below)
  // Note: need to change the ip address below and in application.conf (sorry for that, cumbersome)
    println(s"now creating a router towards node actors")
    val router = system.actorOf(ClusterRouterPool(
      local = RoundRobinPool(8),
      settings = ClusterRouterPoolSettings(
        totalInstances = 15,
        maxInstancesPerNode = 4,
        allowLocalRoutees = false
      )
    ).props(Props[NodeActor]),
      name = "routeractor")

    println(s"router: ${router.path}")

  val pubAc = system.actorOf(SubscribeActor.props())

}

