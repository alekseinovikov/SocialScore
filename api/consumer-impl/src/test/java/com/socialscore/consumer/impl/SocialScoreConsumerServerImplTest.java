package com.socialscore.consumer.impl;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.consumer.api.dto.SocialScoreParams;
import com.socialscore.consumer.impl.dto.SocialScoreParamsImpl;

@ExtendWith(MockitoExtension.class)
class SocialScoreConsumerServerImplTest {

    @InjectMocks
    private SocialScoreConsumerServerImpl consumerServer;


    @Test
    void receiveMessage_hasNoConsumers_noActions() {
        //act
        consumerServer.receiveMessage(SocialScoreParamsProto.newBuilder().build());
    }

    @SuppressWarnings("unchecked")
    @Test
    void receiveMessage_hasOneConsumer_callsIt() {
        //arrange
        final Consumer<SocialScoreParams> consumer = Mockito.mock(Consumer.class);

        consumerServer.subscribe(consumer);

        final SocialScoreParamsProto protoMessage = SocialScoreParamsProto.newBuilder()
                                                                          .setFirstName("firstName")
                                                                          .setLastName("lastName")
                                                                          .setAge(33)
                                                                          .setSeed(0.5)
                                                                          .build();

        final SocialScoreParamsImpl expectedMessage = new SocialScoreParamsImpl("firstName", "lastName", 33, 0.5);

        //act
        consumerServer.receiveMessage(protoMessage);

        //assert
        Mockito.verify(consumer, Mockito.times(1)).accept(expectedMessage);
    }

    @SuppressWarnings("unchecked")
    @Test
    void receiveMessage_hasThreeConsumers_callsThem() {
        //arrange
        final Consumer<SocialScoreParams> consumer1 = Mockito.mock(Consumer.class);
        final Consumer<SocialScoreParams> consumer2 = Mockito.mock(Consumer.class);
        final Consumer<SocialScoreParams> consumer3 = Mockito.mock(Consumer.class);

        consumerServer.subscribe(consumer1);
        consumerServer.subscribe(consumer2);
        consumerServer.subscribe(consumer3);

        final SocialScoreParamsProto protoMessage = SocialScoreParamsProto.newBuilder()
                                                                          .setFirstName("firstName")
                                                                          .setLastName("lastName")
                                                                          .setAge(33)
                                                                          .setSeed(0.5)
                                                                          .build();

        final SocialScoreParamsImpl expectedMessage = new SocialScoreParamsImpl("firstName", "lastName", 33, 0.5);

        //act
        consumerServer.receiveMessage(protoMessage);

        //assert
        Mockito.verify(consumer1, Mockito.times(1)).accept(expectedMessage);
        Mockito.verify(consumer2, Mockito.times(1)).accept(expectedMessage);
        Mockito.verify(consumer3, Mockito.times(1)).accept(expectedMessage);
    }

}