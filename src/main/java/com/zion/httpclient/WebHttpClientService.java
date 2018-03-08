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

import java.util.Set;

import org.apache.http.client.config.RequestConfig;

import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.httpclient.model.HttpResponse;
import com.zion.httpclient.model.RequestKeyValue;

public interface WebHttpClientService {
	
	HttpResponse get(String endpoint, Set<RequestKeyValue> params, RequestConfig config) throws HttpClientConnectionException;

}

