package com.socialscore.consumer.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.consumer.application.configuration.SocialScoreConsumerApplicationIntegrationTestConfiguration;
import com.socialscore.score.consumer.api.dto.SocialScore;

@SpringBootTest(classes = SocialScoreConsumerApplicationIntegrationTestConfiguration.class)
public class SocialScoreConsumerApplicationIntegrationTest {

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.group-id}")
    private String kafkaGroupId;

    @Autowired
    private KafkaTemplate<String, SocialScoreParamsProto> kafkaTemplate;

    @Autowired
    private RedisTemplate<String, SocialScore> redisTemplate;


    @Test
    public void test() throws InterruptedException {
        //Waiting some time to let consumer re-balance partitions
        Thread.sleep(2000L);

        //sending data to kafka
        final SocialScoreParamsProto protoMessage = SocialScoreParamsProto.newBuilder()
                                                                          .setFirstName("firstName")
                                                                          .setLastName("lastName")
                                                                          .setAge(33)
                                                                          .setSeed(0.5)
                                                                          .build();

        kafkaTemplate.send(topic, protoMessage);

        //Waiting 2 secs to make sure our async consumer processed the message
        Thread.sleep(2000L);

        //Check if value exists in redis
        final String key = "firstName-lastName";
        final SocialScore socialScore = redisTemplate.opsForList().rightPop(key);

        assertThat(socialScore).isNotNull();
        assertThat(socialScore.getFirstName()).isEqualTo(protoMessage.getFirstName());
        assertThat(socialScore.getLastName()).isEqualTo(protoMessage.getLastName());
        assertThat(socialScore.getScore()).isEqualTo(16.5);
    }

}
