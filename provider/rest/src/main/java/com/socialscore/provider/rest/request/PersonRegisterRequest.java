package com.socialscore.provider.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialscore.provider.service.api.dto.PersonData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonRegisterRequest implements PersonData {

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Min(0)
    @JsonProperty("age")
    private int age;

}
