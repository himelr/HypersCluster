import ClusterMain.system
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.Cluster
import akka.cluster.routing.{ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing.{BroadcastPool, RoundRobinPool}
import com.typesafe.config.ConfigFactory

case class MyMsg(n: Int)

object ClusterMain extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("hyperscluster")

  // run jar with command java -DPORT=2551 -DHOSTNAME=127.0.0.1 -jar cluster-experiment-assembly-1.0.jar
  // (replace ip address with your address)
  // this will create seed node and router towards the cluster (see below)
  // (to create jar, go to view -> tool windows -> sbt shell, and issue command 'assembly')
  // start other cluster node by using port number other than 2551 and 2555
  // to start traffic generation, run the jar with port number 2555 (see code below)
  // Note: need to change the ip address below and in application.conf (sorry for that, cumbersome)
  if (config.getString("akka.remote.netty.tcp.port") == "2551") {
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
  }

  if (config.getString("akka.remote.netty.tcp.port") == "2555") {
    Thread.sleep(5000)

    // your ip address here (sorry)
    // note you need to change ip address in application.conf, too
    val router = system.actorSelection("akka.tcp://hyperscluster@127.0.0.1:2551/user/routeractor")

    (1 to 200).foreach(f = (i) => {
      router ! MyMsg(i)
      Thread.sleep(500)
    })

    Thread.sleep(5000)
  }
}

