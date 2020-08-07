package com.socialscore.consumer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialscore.consumer.service.calculator.SocialScoreCalculator;
import com.socialscore.consumer.service.calculator.dto.SocialRatingData;
import com.socialscore.score.consumer.api.ScoreConsumer;
import com.socialscore.consumer.api.SocialScoreConsumerClient;
import com.socialscore.consumer.api.dto.SocialScoreParams;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceImplTest {

    @InjectMocks
    private ConsumerServiceImpl consumer;

    @Mock
    private SocialScoreCalculator socialScoreCalculator;

    @Mock
    private SocialScoreConsumerClient socialScoreConsumerClient;

    @Spy
    private final List<ScoreConsumer> populators = new ArrayList<>();

    @Mock
    private ScoreConsumer firstPopulator;

    @Mock
    private ScoreConsumer secondPopulator;


    @BeforeEach
    public void setUp() {
        populators.add(firstPopulator);
        populators.add(secondPopulator);
    }

    @Test
    void init_hasAllData_callsCalculatorAndEveryPopulator() {
        //arrange
        final List<Consumer<SocialScoreParams>> consumers = new ArrayList<>();
        Mockito.doAnswer(invocation -> {
            final Consumer<SocialScoreParams> consumer = invocation.getArgument(0);
            consumers.add(consumer);

            return null;
        }).when(socialScoreConsumerClient).subscribe(any());

        final SocialScoreParams params = new SocialScoreParams() {
            @Override
            public String getFirstName() {
                return "firstName";
            }

            @Override
            public String getLastName() {
                return "lastName";
            }

            @Override
            public int getAge() {
                return 33;
            }

            @Override
            public double getSeed() {
                return 0.5;
            }
        };


        final SocialRatingData socialRatingData = new SocialRatingData("firstName", "lastName", 12.5);
        Mockito.when(socialScoreCalculator.calculate(params)).thenReturn(12.5);

        consumer.init();

        //act
        consumers.forEach(c -> c.accept(params));

        //assert
        verify(socialScoreCalculator, Mockito.times(1)).calculate(params);
        verify(firstPopulator, times(1)).consume(socialRatingData);
        verify(secondPopulator, times(1)).consume(socialRatingData);
    }

}