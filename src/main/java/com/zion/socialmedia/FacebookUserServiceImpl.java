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
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zion.auth.InvalidAuthToken;

public class FacebookUserServiceImpl implements FacebookUserService{

    private static final String FACEBOOK_API_PROTOCOL = "https";
    private static final String FACEBOOK_API_HOST = "graph.facebook.com";
    private static final String FACEBOOK_API_PATH = "/v2.10/me";
    
    private static final String FACEBOOK_PARAM_FIELDS = "fields";
    private static final String FACEBOOK_PARAM_ACCESSTOKEN = "access_token";
    
    private static final String FACEBOOK_ATTRIBUTE_EMAIL = "email";
    private static final String FACEBOOK_ATTRIBUTE_LASTNAME = "last_name";
    private static final String FACEBOOK_ATTRIBUTE_FIRSTNAME = "first_name";
    private static final String FACEBOOK_ATTRIBUTE_NAME = "name";
    private static final String FACEBOOK_ATTRIBUTE_ID = "id";
    private static final String FACEBOOK_ATTRIBUTE_LOCALE = "locale";
    private static final String FACEBOOK_ATTRIBUTE_PICTURE = "picture";
    

    public FacebookUserServiceImpl() {
    }
    
    @Override
    public SocialMediaUserProfile getSocialMediaUserProfile(String accessToken) throws InvalidAuthToken, SocialMediaAuthException {
        
        JsonObject facebookUserResponse;
        try {
            facebookUserResponse = getFaceBookUserDetails(accessToken);
            if (facebookUserResponse.get("error") != null) {
                throw new InvalidAuthToken("Cannot fetch facebook user with token: " + accessToken);
            }
            
            return this.generateUserProfile(facebookUserResponse);
        } catch (URISyntaxException |IOException e) {
            throw new SocialMediaAuthException("Fail with the facebook user validation.");
        }
        
    }
    
    private SocialMediaUserProfile generateUserProfile(final JsonObject facebookUserResponse) {
        String email = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_EMAIL, false);
        SocialMediaUserProfile userProfile = new SocialMediaUserProfile();
        String userId = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_ID, true);
        String firstName = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_FIRSTNAME, false);
        String lastName = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_LASTNAME, false);
        String fbAvatarPattern = "https://res.cloudinary.com/dozzyprzt/image/facebook/%s.jpg";
        String picture = String.format(fbAvatarPattern, userId);
        String name = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_NAME, false);
        String locale = getValue(facebookUserResponse, FACEBOOK_ATTRIBUTE_LOCALE, false);
        if(StringUtils.isNotBlank(email)) {
            userProfile.setEmail(email);
        }
        userProfile.setAvatar(picture);
        userProfile.setExternalUserId(userId);
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setName(name);
        userProfile.setSource(SocialSource.FACEBOOK.name());
        userProfile.setLocale(locale);
        return userProfile;
    }
    
    private String getValue(final JsonObject facebookUserResponse, final String attribute, final boolean required) {
        if (facebookUserResponse.get(attribute) != null) {
            return facebookUserResponse.get(attribute).getAsString();
        }
        if (!required) {
            return null;
        }
        throw new IllegalArgumentException("Facebook login [" + attribute + "] is required.");
    }

    private JsonObject getFaceBookUserDetails(final String accessToken) throws URISyntaxException, ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        URI uri = generateGraphAPIConnection(accessToken);
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject) jsonParser.parse(content);
        return object;
        
    }

    private URI generateGraphAPIConnection(String accessToken) throws URISyntaxException {
        URI uri= new URIBuilder().setScheme(FACEBOOK_API_PROTOCOL)
                .setHost(FACEBOOK_API_HOST)
                .setPath(FACEBOOK_API_PATH)
                .setParameter(FACEBOOK_PARAM_ACCESSTOKEN, accessToken)
                .setParameter(FACEBOOK_PARAM_FIELDS, generateFields())
                .build();
        return uri;
    }
    
    private String generateFields() {
        StringBuilder builder = new StringBuilder();
        builder.append(FACEBOOK_ATTRIBUTE_ID).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_EMAIL).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_FIRSTNAME).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_LASTNAME).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_PICTURE).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_LOCALE).append(",");
        builder.append(FACEBOOK_ATTRIBUTE_NAME);
        return builder.toString();
    }

}

