package com.socialscore.provider.rest.controller;

import com.socialscore.provider.rest.configuration.SocialScoreProviderRestConfiguration;
import com.socialscore.provider.service.api.PersonScoreProcessor;
import com.socialscore.provider.service.api.dto.PersonData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")

@ContextConfiguration(classes = SocialScoreProviderRestConfiguration.class)
@WebMvcTest(controllers = PersonRegisterController.class)
class PersonRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonScoreProcessor personScoreProcessor;


    @Test
    void register_correctRequest_OK() throws Exception {
        //arrange
        final String request = "{\n" +
                "   \"first_name\": \"firstName\"," +
                "   \"last_name\": \"lastName\"," +
                "   \"age\": 33" +
                "}";


        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(personScoreProcessor, Mockito.times(1)).process(new PersonData("firstName", "lastName", 33));
    }

    @Test
    void register_notJson_BadRequest() throws Exception {
        //arrange
        final String request = "wrong";

        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(personScoreProcessor);
    }

    @Test
    void register_notSupportedContentType_UnSupportedMedia() throws Exception {
        //arrange
        final String request = "{\n" +
                "   \"first_name\": \"firstName\"," +
                "   \"last_name\": \"lastName\"," +
                "   \"age\": 33" +
                "}";

        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_PDF_VALUE)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().is(415));

        verifyNoInteractions(personScoreProcessor);
    }

    @Test
    void register_emptyFirstName_BadRequest() throws Exception {
        //arrange
        final String request = "{\n" +
                "   \"first_name\": \"\"," +
                "   \"last_name\": \"lastName\"," +
                "   \"age\": 33" +
                "}";


        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(personScoreProcessor);
    }

    @Test
    void register_emptyLastName_BadRequest() throws Exception {
        //arrange
        final String request = "{\n" +
                "   \"first_name\": \"firstName\"," +
                "   \"last_name\": \"\"," +
                "   \"age\": 33" +
                "}";


        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(personScoreProcessor);
    }

    @Test
    void register_negativeAge_BadRequest() throws Exception {
        //arrange
        final String request = "{\n" +
                "   \"first_name\": \"firstName\"," +
                "   \"last_name\": \"lastName\"," +
                "   \"age\": -1" +
                "}";


        //act
        mockMvc.perform(post("/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

                //assert
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(personScoreProcessor);
    }

}