import akka.actor.Actor

class NodeActor extends Actor {
  override def receive: Receive = {
    case MyMsg(i) => {println(s"$self: $i"); Thread.sleep(700)}
    case _ => println("unknown")
  }
}
