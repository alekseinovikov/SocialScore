package com.socialscore.provider.service.api;

import com.socialscore.provider.service.api.dto.PersonData;

public interface PersonScoreProviderService {

    void process(final PersonData data);

}
