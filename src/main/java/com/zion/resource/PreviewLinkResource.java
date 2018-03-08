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
package com.zion.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.zion.common.LinkPreviewException;
import com.zion.common.Payload;
import com.zion.common.service.LinkPreviewService;
import com.zion.permission.PermissionService;

@Path("/previewlink")
public class PreviewLinkResource extends BaseResource{
    
    private LinkPreviewService previewLinkService;
    private PermissionService permissionService;
    private static final String PARAM_URL = "url";
    
    @Inject
    public PreviewLinkResource(LinkPreviewService previewLinkService, PermissionService permissionService) {
        this.previewLinkService = previewLinkService;
        this.permissionService = permissionService;
    }

    /**
     * Return 404 when it not found.
     * @param token
     * @param encodedUrl this must be encoded url from client side eg: var urlEncoded = encodeURIComponent(url);
     * @return
     */
    @GET
    @Path("/v1/hybridgraph")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<Object> getHybridGraph(@HeaderParam(PARAM_JWT_TOKEN) String token, @QueryParam(PARAM_URL) String encodedUrl) {
        Payload<Object> payload = new Payload<>();
        if(StringUtils.isBlank(encodedUrl)) {
            payload.setMsg("Cannot get hybrid graph as url is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (!this.permissionService.isValidJwtToken(token)) {
            payload.setMsg("Cannot get hybrid graph as jwt token is invalid");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        Object data;
        try {
            data = this.previewLinkService.getHybridGraph(encodedUrl);
            payload.setMsg("ok");
            payload.setData(data);
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (LinkPreviewException e) {
            payload.setMsg("Cannot find the url preview link.");
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            return payload;
        }
    }
}

