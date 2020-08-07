package com.socialscore.populator.api.dto;

import java.io.Serializable;

public interface SocialScore extends Serializable {
    String getFirstName();
    String getLastName();
    double getScore();
}
