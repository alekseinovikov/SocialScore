package com.socialscore.consumer.service.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialscore.server.api.dto.SocialScoreParams;

@ExtendWith(MockitoExtension.class)
class SocialScoreCalculatorImplTest {

    @InjectMocks
    private SocialScoreCalculatorImpl calculator;


    @Test
    void calculate_randomParams_calculatesWithFormula() {
        //arrange
        final Random random = new Random();

        //act
        //assert
        IntStream.range(0, 1000).forEach(i -> {
            final String firstName = UUID.randomUUID().toString();
            final String lastName = UUID.randomUUID().toString();
            final int age = random.nextInt(10000);
            final double seed = random.nextDouble();

            final SocialScoreParams params = new SocialScoreParams() {
                @Override
                public String getFirstName() {
                    return firstName;
                }

                @Override
                public String getLastName() {
                    return lastName;
                }

                @Override
                public int getAge() {
                    return age;
                }

                @Override
                public double getSeed() {
                    return seed;
                }
            };

            final double score = calculator.calculate(params);
            assertThat(score).isEqualTo(params.getSeed() * params.getAge());
        });
    }
}