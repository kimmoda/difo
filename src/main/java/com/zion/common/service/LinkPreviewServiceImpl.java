/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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
package com.zion.common.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zion.common.AppConfig;
import com.zion.common.LinkPreviewException;
import com.zion.common.YoutubeHelper;
import com.zion.httpclient.WebHttpClientService;
import com.zion.httpclient.model.RequestKeyValue;

public class LinkPreviewServiceImpl implements LinkPreviewService{

    private WebHttpClientService httpClient;
    private static String YOUTUBE_URL_PREFIX_TEMPLATE = "http://res.cloudinary.com/%s/image/youtube/%s";

    @Inject
    public LinkPreviewServiceImpl(WebHttpClientService httpClient) {
        this.httpClient = httpClient;
    }
    
    @Override
    public Object getHybridGraph(String encodedUrl) throws LinkPreviewException {
        if(StringUtils.isBlank(encodedUrl)) {
            return null;
        }
        String apiUrl = "https://opengraph.io/api/1.1/site/" + encodedUrl;
       
        Set<RequestKeyValue> params = new HashSet<>();
        String apiKey = AppConfig.getInstance().getOpenGraphApiKey();
        RequestKeyValue keyValue = new RequestKeyValue("app_id", apiKey);
        params.add(keyValue);
        try {
            String json = this.httpClient.get(apiUrl, params, null).getContent();
            
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> linkMap = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            Object hybridGraph =  linkMap.get("hybridGraph");
            if (hybridGraph != null) {
                return this.hackJobToUpdateYoutubeImageUrl(hybridGraph, encodedUrl);
            }else {
                throw new LinkPreviewException(String.format("Cannot find Open graph of the url %s.", encodedUrl));
            }
        } catch (Exception e) {
            throw new LinkPreviewException(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private Object hackJobToUpdateYoutubeImageUrl(Object hybridGraph, String encodedUrl) throws UnsupportedEncodingException {
        String decodeUrl =  URLDecoder.decode(encodedUrl.trim(), "UTF-8");
        String id = YoutubeHelper.extractVideoIdFromUrl(decodeUrl);
        if (StringUtils.isNotBlank(id)) {
            String cloudinaryHostName =  AppConfig.getInstance().getCloudinaryConfig().get("cloud_name").toString();
            String imageUrl = String.format(YOUTUBE_URL_PREFIX_TEMPLATE, cloudinaryHostName, id.trim());
            ((Map<String, String>) hybridGraph).put("image", imageUrl);
        }
        return hybridGraph;
    }

}

