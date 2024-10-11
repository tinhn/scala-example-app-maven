package org.tinhn

import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import java.util.Properties
import scala.collection.JavaConverters.iterableAsScalaIterableConverter

object KafkaConsumerExample {
  def main(args : Array[String]) {
    consumeFromKafka("getting-started")
  }

  def consumeFromKafka(TOPIC: String): Unit = {
    val props: Properties = new Properties()
    props.put("group.id", "anything")
    // auto.offset.reset in [earliest, latest]
    props.put("auto.offset.reset","earliest")
    props.put("bootstrap.servers", "localhost:19092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")

    val consumer = new KafkaConsumer[String, String](props)
    val topics = util.Arrays.asList(TOPIC)

    consumer.subscribe(topics)
    try {
      while (true) {
        val records = consumer.poll(100).asScala
        for (data <- records.iterator) {
          println("Topic: " + data.topic() + ", Key: " + data.key() + ", Value: " + data.value() +
            ", Offset: " + data.offset() + ", Partition: " + data.partition())
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    finally {
      consumer.close()
    }
  }
}
