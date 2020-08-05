package com.socialscore.provider.service.api;

import com.socialscore.provider.service.api.dto.PersonData;

public interface PersonScoreProcessor {

    void process(final PersonData data);

}
