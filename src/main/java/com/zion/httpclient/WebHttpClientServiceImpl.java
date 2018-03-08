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
package com.zion.httpclient;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.httpclient.model.HttpResponse;
import com.zion.httpclient.model.RequestKeyValue;

public class WebHttpClientServiceImpl implements WebHttpClientService {
    
	@Override
	public HttpResponse get(final String endpoint, final Set<RequestKeyValue> params, RequestConfig config) throws HttpClientConnectionException {
	    CloseableHttpClient httpclient;
	    if (config != null) {
	        httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	    } else {
	        httpclient = HttpClients.createDefault();
	    }
		String url = parseEndpoint(endpoint, params);
		HttpGet httpGet = new HttpGet(url);
		try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity);
                    long length = entity.getContentLength();
                    String contentType = entity.getContentType().toString();
                    return new HttpResponse(content, contentType, length);
                }
                throw new HttpClientConnectionException(status, "The response entity is null");
            }
            throw new HttpClientConnectionException(status, "Server response error");
        } catch (ClientProtocolException e) {
            throw new HttpClientConnectionException("Http protocoal error", e);
        } catch (IOException e) {
            throw new HttpClientConnectionException("Http reponse parse error", e);
        } catch (Exception e) {
            throw new HttpClientConnectionException("Unknown error", e);
        }
	}
	
	private String parseEndpoint(final String endpoint, final Set<RequestKeyValue> params) {
		Validate.notBlank(endpoint);
		
		StringBuilder requestStringBuilder = new StringBuilder();
		if (params != null) {
			int i = 0;
			for (RequestKeyValue param : params) {
				requestStringBuilder.append(param.toString());
                if (i != params.size() - 1) {
                	requestStringBuilder.append("&");
				}
                i++;
			}
		}
		
		if (StringUtils.isNotBlank(requestStringBuilder.toString())) {
			if (endpoint.contains("?")) {
				return endpoint + "&" + requestStringBuilder.toString();
			} else {
				return endpoint + "?" + requestStringBuilder.toString();
			}
		}
		
		return endpoint;
	}

}

