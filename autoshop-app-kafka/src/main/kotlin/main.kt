package ru.drvshare.autoshop.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()/*, ConsumerStrategyV2()*/))
    consumer.run()
}
