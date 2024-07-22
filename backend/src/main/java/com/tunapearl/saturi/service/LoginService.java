package com.tunapearl.saturi.service;

import com.tunapearl.saturi.dto.user.social.SocialAuthResponse;
import com.tunapearl.saturi.dto.user.social.SocialUserResponse;
import com.tunapearl.saturi.dto.UserType;
import com.tunapearl.saturi.exception.InvalidTokenException;

public interface LoginService {
    UserType getServiceName();
    SocialAuthResponse getAccessToken(String code);
    void checkTokenValidity(String accessToken) throws InvalidTokenException, RuntimeException;
    SocialUserResponse getUserInfo(String accessToken);
}