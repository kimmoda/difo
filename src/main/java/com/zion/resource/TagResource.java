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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zion.common.Payload;
import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.tag.Tag;
import com.zion.tag.TagListWrapper;
import com.zion.tag.TagService;

@Path("/tag")
public class TagResource extends BaseResource {

    private TagService tagService;

    @Inject
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @GET
    @Path("/v1/{codeSystem}/{source}/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<Tag>> findTags(
            @PathParam("codeSystem") String codeSystem,
            @PathParam("source") String source,
            @QueryParam(PARAM_RESULT_SIZE) Integer resultSize,
            @QueryParam("code") String code,
            @QueryParam("label") String label) {

        List<Tag> tags = new ArrayList<>();
        Payload<List<Tag>> payload = new Payload<>();
        TagCodeSystem tagCodeSystem = TagCodeSystem.findTagCodeSystemByCode(codeSystem);
        TagSourceType tagSourceType = TagSourceType.findTagSourceTypeByCode(source);

        if (tagCodeSystem != null && tagSourceType != null) {
            tags = this.tagService.searchTag(label, code, tagCodeSystem, tagSourceType, resultSize);
        }

        payload.setData(tags);
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }

    @GET
    @Path("/v1/{source}/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<Tag>> findTagsBySource(
            @PathParam("source") String source,
            @QueryParam("code") String code,
            @QueryParam("label") String label) {

        List<Tag> tags = new ArrayList<>();
        Payload<List<Tag>> payload = new Payload<>();
        TagSourceType tagSourceType = TagSourceType.findTagSourceTypeByCode(source);
        tags = this.tagService.searchTag(label, code, null, tagSourceType, null);
        payload.setData(tags);
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }

    @GET
    @Path("/v2/{source}/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<TagListWrapper>> findTagsBySourceV2(
            @PathParam("source") String source,
            @QueryParam("code") String code,
            @QueryParam("label") String label) {

        List<Tag> tags = new ArrayList<>();
        Payload<List<TagListWrapper>> payload = new Payload<>();
        TagSourceType tagSourceType = TagSourceType.findTagSourceTypeByCode(source);
        tags = this.tagService.searchTag(label, code, null, tagSourceType, null);
        Map<String, List<Tag>> tagMap = new HashMap<>();
        for (Tag tag : tags) {
            List<Tag> tagList = tagMap.get(tag.getCodeSystem());
            if (tagList != null) {
                tagList.add(tag);
            } else {
                tagList = new ArrayList<>();
                tagList.add(tag);
                tagMap.put(tag.getCodeSystem(), tagList);
            }
        }
        List<TagListWrapper> tagListWrappers = new ArrayList<>();

        for (String key : tagMap.keySet()) {
            TagCodeSystem tagCodeSys = TagCodeSystem.findTagCodeSystemByCode(key);
            TagListWrapper tagListWrapper = new TagListWrapper(tagCodeSys.getDisplayName(), tagCodeSys.getCode(), tagMap.get(key), tagCodeSys.getOrder());
            tagListWrappers.add(tagListWrapper);
        }
        
        tagListWrappers.sort(new Comparator<TagListWrapper>() {

            @Override
            public int compare(TagListWrapper o1, TagListWrapper o2) {
                if (o1.getOrder() > o2.getOrder()) {
                    return 1;
                }else if (o1.getOrder() == o2.getOrder()) {
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        payload.setData(tagListWrappers);
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }

}
