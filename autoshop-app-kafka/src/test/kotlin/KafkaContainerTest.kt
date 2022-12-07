package ru.drvshare.autoshop.app.kafka

import io.kotest.core.extensions.install
import io.kotest.core.spec.style.FunSpec
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import io.kotest.extensions.testcontainers.TestContainerExtension
import io.kotest.extensions.testcontainers.kafka.createStringStringConsumer
import io.kotest.extensions.testcontainers.kafka.createStringStringProducer
import io.kotest.matchers.collections.shouldHaveSize
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

/** TODO! Начал делать, но пришёл к выводу, что с кафкой тестконтейнеры не принципиально */
class KafkaContainerTest : FunSpec() {


    init {

        val kafka = install(TestContainerExtension(KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1")))) {
            withEmbeddedZookeeper()
        }

        test("should send/receive message") {

            val producer = kafka.createStringStringProducer()
            producer.send(ProducerRecord("foo", null, "bubble bobble"))
            producer.close()

            val consumer = kafka.createStringStringConsumer {
                this[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 1
            }

            consumer.subscribe(listOf("foo"))
            val records = consumer.poll(Duration.ofSeconds(100))
            records.shouldHaveSize(1)
        }
    }


}
