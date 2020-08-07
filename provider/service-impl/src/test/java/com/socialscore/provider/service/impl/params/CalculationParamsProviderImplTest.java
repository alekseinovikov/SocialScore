package com.socialscore.provider.service.impl.params;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialscore.provider.service.impl.properties.CalculationProperties;

@ExtendWith(MockitoExtension.class)
class CalculationParamsProviderImplTest {

    @InjectMocks
    private CalculationParamsProviderImpl provider;

    @Mock
    private CalculationProperties properties;


    @Test
    void getSeed_correctFileCorrectSeed_returnsIt() {
        //arrange
        when(properties.getFileName()).thenReturn("calculation.properties");
        when(properties.refreshRateSeconds()).thenReturn(30);

        provider.init();

        //act
        final double seed = provider.getSeed();

        //assert
        assertThat(seed).isEqualTo(0.5);
    }

    @Test
    void getSeed_wrongNegativeSeed_IllegalStateException() {
        //arrange
        when(properties.getFileName()).thenReturn("calculation-wrong-negative.properties");
        when(properties.refreshRateSeconds()).thenReturn(30);

        provider.init();

        //act
        //assert
        assertThatThrownBy(() -> provider.getSeed())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void getSeed_wrongPositiveSeed_IllegalStateException() {
        //arrange
        when(properties.getFileName()).thenReturn("calculation-wrong-positive.properties");
        when(properties.refreshRateSeconds()).thenReturn(30);

        provider.init();

        //act
        //assert
        assertThatThrownBy(() -> provider.getSeed())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void getSeed_onChangeFileEvictsCache_throwsIllegalState() {
        //arrange
        when(properties.getFileName()).thenReturn("calculation.properties");
        when(properties.refreshRateSeconds()).thenReturn(1);

        provider.init();

        //act
        //assert
        final double seed = provider.getSeed();
        assertThat(seed).isEqualTo(0.5);

        //changing properties
        when(properties.getFileName()).thenReturn("calculation-wrong-positive.properties");

        //Reloaded wrong value
        assertThatThrownBy(() -> provider.getSeed())
                .isInstanceOf(IllegalStateException.class);
    }

}