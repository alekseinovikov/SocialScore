package com.socialscore.provider.rest.controller;

import com.socialscore.provider.rest.request.PersonRegisterRequest;
import com.socialscore.provider.service.api.PersonScoreProcessor;
import com.socialscore.provider.service.api.dto.PersonData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor

@RestController
@RequestMapping("/person")
public class PersonRegisterController {

    private final PersonScoreProcessor processor;


    @PostMapping("/register")
    public void register(@Valid @RequestBody final PersonRegisterRequest request) {
        processor.process(convert(request));
    }


    private PersonData convert(final PersonRegisterRequest request) {
        return new PersonData(
                request.getFirstName(),
                request.getLastName(),
                request.getAge());
    }

}
