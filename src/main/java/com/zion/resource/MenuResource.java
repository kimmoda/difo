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

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zion.common.Payload;
import com.zion.menu.Menu;
import com.zion.menu.MenuService;

@Path("/menu")
public class MenuResource extends BaseResource {

    private MenuService menuService;
    
    @Inject
    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }
    
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<Menu> getMenu(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<Menu> payload = new Payload<>();
        payload.setData(menuService.generateMenu(token));
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }
}

