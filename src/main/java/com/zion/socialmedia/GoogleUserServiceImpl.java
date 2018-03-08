/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.zion.socialmedia;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.zion.auth.AuthToken;
import com.zion.auth.InvalidAuthToken;
import com.zion.common.EmptyEmailException;

public class GoogleUserServiceImpl implements GoogleUserService {
    
    public GoogleUserServiceImpl() {
    }

    @Override
    public SocialMediaUserProfile getSocialMediaUserProfile(AuthToken authToken) throws InvalidAuthToken, EmptyEmailException{
        List<String> tokens = new ArrayList<>();
        if(StringUtils.isNotBlank(authToken.getAppClientId())) {
            tokens.add(authToken.getAppClientId().trim());
        }else {
            throw new IllegalArgumentException("Cannot get google user as app client id is missing");
        }
        GoogleIdTokenVerifier tokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).setAudience(tokens).build();
        try {
            GoogleIdToken gToken = tokenVerifier.verify(authToken.getAuthToken().trim());
            GoogleIdToken.Payload payload = gToken.getPayload();
            String externalUserId = payload.getSubject();
            String email = payload.getEmail();
            if(StringUtils.isBlank(email)) {
                throw new EmptyEmailException("User profile's email is missing, please check your google account profile configuration.");
            }
            String name = (String) payload.get("name");
            String avatar = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String lastName = (String) payload.get("family_name");
            String firstName = (String) payload.get("given_name");
            return new SocialMediaUserProfile(firstName, lastName, name, email.trim(), avatar, SocialSource.GOOGLE.name(), externalUserId, locale);
        } catch (GeneralSecurityException | IOException e) {
            throw new InvalidAuthToken("Not allow user access. The access token is invalid");
        }
    }
}

