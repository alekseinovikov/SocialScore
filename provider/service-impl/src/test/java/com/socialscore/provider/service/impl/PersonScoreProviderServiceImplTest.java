package com.socialscore.provider.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialscore.client.api.SocialScorePublisherClient;
import com.socialscore.provider.service.api.dto.PersonData;
import com.socialscore.provider.service.impl.params.CalculationParamsProvider;

@ExtendWith(MockitoExtension.class)
class PersonScoreProviderServiceImplTest {

    @Mock
    private CalculationParamsProvider propertiesProvider;

    @Mock
    private SocialScorePublisherClient publisher;

    @InjectMocks
    private PersonScoreProviderServiceImpl service;

    @Test
    void process_hasAllParams_callsPropertyParamsAndCallsPublisher() {
        //arrange
        final PersonData data = new PersonData() {
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
        };

        when(propertiesProvider.getSeed()).thenReturn(42d);

        //act
        service.process(data);

        //assert
        verify(propertiesProvider, times(1)).getSeed();
        verify(publisher, times(1)).publish(new SocialScoreParamsImpl("firstName", "lastName", 33, 42d));
    }

}