package ru.drvshare.autoshop.app.kafka

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInV1: String = KAFKA_TOPIC_IN_V1,
    val kafkaTopicOutV1: String = KAFKA_TOPIC_OUT_V1,
    val kafkaTopicInV2: String = KAFKA_TOPIC_IN_V2,
    val kafkaTopicOutV2: String = KAFKA_TOPIC_OUT_V2
) {
    companion object {
        private const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        private const val KAFKA_TOPIC_IN_V1_VAR = "KAFKA_TOPIC_IN_V1"
        private const val KAFKA_TOPIC_OUT_V1_VAR = "KAFKA_TOPIC_OUT_V1"
        private const val KAFKA_TOPIC_IN_V2_VAR = "KAFKA_TOPIC_IN_V2"
        private const val KAFKA_TOPIC_OUT_V2_VAR = "KAFKA_TOPIC_OUT_V2"
        private const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "autoshop" }
        val KAFKA_TOPIC_IN_V1 by lazy { System.getenv(KAFKA_TOPIC_IN_V1_VAR) ?: "autoshop-in-v1" }
        val KAFKA_TOPIC_OUT_V1 by lazy { System.getenv(KAFKA_TOPIC_OUT_V1_VAR) ?: "autoshop-out-v1" }
        val KAFKA_TOPIC_IN_V2 by lazy { System.getenv(KAFKA_TOPIC_IN_V2_VAR) ?: "autoshop-in-v2" }
        val KAFKA_TOPIC_OUT_V2 by lazy { System.getenv(KAFKA_TOPIC_OUT_V2_VAR) ?: "autoshop-out-v2" }
    }
}
