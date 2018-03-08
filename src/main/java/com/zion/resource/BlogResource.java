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
package com.zion.resource;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.zion.httpclient.WebHttpClientService;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.httpclient.model.RequestKeyValue;

@Path("/blog")
public class BlogResource extends BaseResource {

    private WebHttpClientService webHttpClientService;
    
    private static final String LIST_POSTS_URL = "http://trending.stylehub.online/wp-json/wp/v2/posts";
    private static final String MEDIA_URL = "http://trending.stylehub.online/wp-json/wp/v2/media/%s";
    private static final String POST_URL = "http://trending.stylehub.online/wp-json/wp/v2/posts/%s";
    private static final String AUTHOR_URL = "http://trending.stylehub.online/wp-json/wp/v2/users/%s";
    private static final String PAGE= "page";
    private static final String PER_PAGE = "per_page";
    private static final String ORDER = "order";
    private static final String ORDER_BY = "orderby";
    private static final String STATUS = "status";
    
    
    
    @Inject
    public BlogResource(WebHttpClientService webHttpClientService) {
        this.webHttpClientService = webHttpClientService;
    }
    
    @GET
    @Path("/v1/posts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getPosts(@QueryParam(PAGE) String page, @QueryParam(PER_PAGE)String perPage, 
            @QueryParam(ORDER) String order, @QueryParam(ORDER_BY) String orderBy, @QueryParam(STATUS) String status) throws HttpClientConnectionException {
        Set<RequestKeyValue> params = new HashSet<>();
        if (StringUtils.isNotBlank(page)) {
            RequestKeyValue pageParam = new RequestKeyValue(PAGE, page);
            params.add(pageParam);
        }
        if (StringUtils.isNotBlank(perPage)) {
            RequestKeyValue perPageParam = new RequestKeyValue(PER_PAGE, perPage);
            params.add(perPageParam);
        }
        if (StringUtils.isNotBlank(order)) {
            RequestKeyValue orderParam = new RequestKeyValue(ORDER, order);
            params.add(orderParam);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            RequestKeyValue orderByParam = new RequestKeyValue(ORDER_BY, orderBy);
            params.add(orderByParam);
        }
        if (StringUtils.isNotBlank(status)) {
            RequestKeyValue statusParam = new RequestKeyValue(STATUS, status);
            params.add(statusParam);
        }
        return this.webHttpClientService.get(LIST_POSTS_URL, params, null).getContent();
    }
    
    @GET
    @Path("/v1/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getPost(@QueryParam(PARAM_ID) String id) throws HttpClientConnectionException {
        if (StringUtils.isBlank(id)) {
            return "{status: 401, msg: 'id is missing'}";
        }
        return this.webHttpClientService.get(String.format(POST_URL, id.trim()), null, null).getContent();
    }
    
    @GET
    @Path("/v1/media")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getMedia(@QueryParam(PARAM_ID) String id) throws HttpClientConnectionException {
        if (StringUtils.isBlank(id)) {
            return "{status: 401, msg: 'id is missing'}";
        }
        return this.webHttpClientService.get(String.format(MEDIA_URL, id.trim()), null, null).getContent();
    }
    
    @GET
    @Path("/v1/author")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getAuthor(@QueryParam(PARAM_ID) String id) throws HttpClientConnectionException {
        if (StringUtils.isBlank(id)) {
            return "{status: 401, msg: 'id is missing'}";
        }
        return this.webHttpClientService.get(String.format(AUTHOR_URL, id.trim()), null, null).getContent();
    }
}

