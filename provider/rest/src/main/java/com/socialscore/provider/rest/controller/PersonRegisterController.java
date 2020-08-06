package com.socialscore.provider.rest.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialscore.provider.rest.request.PersonRegisterRequest;
import com.socialscore.provider.service.api.PersonScoreProviderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@RestController
@RequestMapping("/person")
public class PersonRegisterController {

    private final PersonScoreProviderService processor;


    @PostMapping("/register")
    public void register(@Valid @RequestBody final PersonRegisterRequest request) {
        processor.process(request);
    }

}
