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
package com.zion.shorturl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;

import com.zion.common.AppConfig;
import com.zion.httpclient.WebHttpClientService;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.httpclient.model.HttpResponse;
import com.zion.httpclient.model.RequestKeyValue;

public class TinyUrlShortUrlServiceImpl implements ShortUrlService {
    
    private WebHttpClientService webHttpClientService;
    
    private static final String SOURCE = "tinyurl";
    
    @Inject
    public TinyUrlShortUrlServiceImpl(WebHttpClientService webHttpClientService) {
        this.webHttpClientService = webHttpClientService;
    }

    @Override
    public String getShortUrl(final String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String urlWithSourceTrack = parseOriginalUrlWithSourceRequestParam(url);
        String tinyUrlApiEndpoint = AppConfig.getInstance().getTinyUrlApiEndpoint();
        Set<RequestKeyValue> params = new HashSet<>();
        params.add(new RequestKeyValue("url", urlWithSourceTrack));
        try {
            HttpResponse response = webHttpClientService.get(tinyUrlApiEndpoint, params, initRequestConfig());
            return convertHttpToHttps(response.getContent());
        } catch (HttpClientConnectionException e) {
            // TODO: need to log
            return null;
        }
    }
    
    private String convertHttpToHttps(final String url) {
        if (StringUtils.isNotBlank(url) && !url.contains("https")) {
            return url.replace("http", "https");
        }
        return url;
    }
    
    private RequestConfig initRequestConfig() {
        int timeout = AppConfig.getInstance().getTinyUrlApiTimeout();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        return config;
    }

    @Override
    public String getSourceRequestParam() {
        return TrackRequestParam.SOURCE.name().toLowerCase() + "=" + SOURCE;
    }

    @Override
    public String parseOriginalUrlWithSourceRequestParam(final String url) {
        if (url.contains("?")) {
            return url + "&" + getSourceRequestParam();
        } else {
            return url + "?" + getSourceRequestParam();
        }
    }
}

