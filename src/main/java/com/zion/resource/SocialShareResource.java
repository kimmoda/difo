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

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zion.common.Payload;
import com.zion.socialmedia.shareurl.ShareUrlGeneraterService;
import com.zion.socialmedia.shareurl.model.SocialShare;

@Path("/socialshare")
public class SocialShareResource extends BaseResource {
    
    private ShareUrlGeneraterService shareUrlGeneraterService;
    
    @Inject
    public SocialShareResource(ShareUrlGeneraterService shareUrlGeneraterService) {
        this.shareUrlGeneraterService = shareUrlGeneraterService;
    }

    @GET
    @Path("/v1/pub/feed/{feedId}/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<SocialShare>> generateFeedSocialUrl(@PathParam("feedId") String feedId) {
        Payload<List<SocialShare>> payload = new Payload<>();
        payload.setData(shareUrlGeneraterService.generateFeedShareUrls(feedId));
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }
    
    @GET
    @Path("/v1/pub/user/{userId}/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<SocialShare>> generateUserSocialUrl(@PathParam("userId") String userId) {
        Payload<List<SocialShare>> payload = new Payload<>();
        payload.setData(shareUrlGeneraterService.generateUserShareUrls(userId));
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }

    @GET
    @Path("/v1/pub/campaign/{campaignId}/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<SocialShare>> generateCampaignSocialUrl(@PathParam("campaignId") String campaignId) {
        Payload<List<SocialShare>> payload = new Payload<>();
        payload.setData(shareUrlGeneraterService.generateCampaignShareUrls(campaignId));
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }
}

