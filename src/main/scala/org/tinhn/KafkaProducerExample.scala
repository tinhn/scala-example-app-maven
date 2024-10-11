package org.tinhn

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties

object KafkaProducerExample {
  def main(args : Array[String]) {
    writeToKafka("getting-started")
  }

  def writeToKafka(TOPIC: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:19092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    try {
      var i = 0
      for (i <- 1 to 50) {
        val record = new ProducerRecord(TOPIC, i.toString, s"Simple message $i")
        val metadata = producer.send(record)

        printf(s"sent record(key=%s value=%s) meta(partition=%d, offset=%d)\n",
          record.key(), record.value(), metadata.get().partition(), metadata.get().offset()
        )
      }

      val record = new ProducerRecord(TOPIC, "key", "the end " + new java.util.Date)
      producer.send(record)

    } catch {
      case e: Exception => e.printStackTrace()
    }
    finally {
      producer.close()
    }
  }
}
