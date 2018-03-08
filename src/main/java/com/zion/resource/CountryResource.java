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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zion.common.CodeSet;
import com.zion.common.CountryConfig;
import com.zion.common.Payload;
import com.zion.user.service.UserService;

@Path("/country")
public class CountryResource {
    
    private UserService userService;
    
    @Inject
    public CountryResource(UserService userService) {
        this.userService = userService;
    }
	
	@GET
	@Path("/v1/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Payload<List<CodeSet>> list() {
		Payload<List<CodeSet>> payload = new Payload<>();
		payload.setData(CountryConfig.getCountries());
		payload.setStatus(Response.Status.OK.getStatusCode());
		return payload;
	}
	
	@GET
    @Path("/v2/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<CodeSet>> listUserCountries() {
        Payload<List<CodeSet>> payload = new Payload<>();
        payload.setData(userService.getUserCountries());
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

}
