package com.socialscore.provider.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.provider.application.configuration.SocialScoreProviderApplicationIntegrationTestConfiguration;

@SpringBootTest(classes = SocialScoreProviderApplicationIntegrationTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SocialScoreProviderApplicationIntegrationTest {

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private KafkaConsumer<String, SocialScoreParamsProto> kafkaConsumer;


    @Test
    void test() throws InterruptedException {
        //Subscribing on kafka topic
        kafkaConsumer.subscribe(List.of(topic));

        //Getting all possible records (if ryuk failed to delete a container before we re-run the test)
        kafkaConsumer.poll(Duration.ofSeconds(1));

        //Commit current offset
        kafkaConsumer.commitSync();

        //Making request to we server
        final String jsonString = "{\n" +
                "   \"first_name\": \"firstName\"," +
                "   \"last_name\": \"lastName\"," +
                "   \"age\": 33" +
                "}";


        final SocialScoreParamsProto expectedProto = SocialScoreParamsProto.newBuilder()
                                                                           .setFirstName("firstName")
                                                                           .setLastName("lastName")
                                                                           .setAge(33)
                                                                           .setSeed(0.5)
                                                                           .build();

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

        final HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        final String result = testRestTemplate.postForObject("/person/register", request, String.class);
        assertThat(result).isNull();

        //Waiting for 5 sec to receive the record
        final ConsumerRecords<String, SocialScoreParamsProto> record = kafkaConsumer.poll(Duration.ofSeconds(5));
        assertThat(record.isEmpty()).isFalse();

        //Checks if the record is the one we expect
        final Iterable<ConsumerRecord<String, SocialScoreParamsProto>> records = record.records(topic);
        records.forEach(r -> {
            final SocialScoreParamsProto proto = r.value();
            assertThat(proto).isEqualTo(expectedProto);
        });

        //Commit offset
        kafkaConsumer.commitSync();
    }

}