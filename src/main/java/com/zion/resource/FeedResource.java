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

import com.google.gson.Gson;
import com.zion.appswitch.AppSwitchProvider;
import com.zion.auth.InvalidJwtToken;
import com.zion.common.*;
import com.zion.feed.*;
import com.zion.media.*;
import com.zion.morphia.entity.FeedEntity;
import com.zion.permission.PermissionService;
import com.zion.socialmedia.SocialSource;
import com.zion.tag.Tag;
import com.zion.user.Author;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

@Path("/feed")
@AppSwitchProvider
public class FeedResource extends BaseResource {
    private static final String PARAM_IMAGE = "image";
    private static final String PARAM_FEED = "feed";
    private static final String PARAM_AUTHOR_ID = "authorId";
    private static final String PARAM_FEED_TAGS = "tags";
    private static final String PARAM_SYS_TAGS = "systags";
    private static final String PARAM_AUTHOR_ROLES = "authorRoles";

    private static final int FEED_CONTENT_REQUIRE = 5;

    private FeedService feedService;
    private UserService userService;
    private PermissionService permissionService;
    private FileService fileService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedResource.class);

    @Inject
    public FeedResource(PermissionService permissionService, UserService userService,
                        FileService fileService, FeedService feedService) {
        this.permissionService = permissionService;
        this.fileService = fileService;
        this.feedService = feedService;
        this.userService = userService;
    }

    @Context
    private HttpServletRequest request;

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> createFeed(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<Feed> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot create feed as logged in user does not have permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create feed as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create feed as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        try {
            Map<String, FileItem> fileItems = FileUploadUtils.getFeedPartsFromRequest(request);
            FileItem image = fileItems.get(PARAM_IMAGE);
            FileItem feedString = fileItems.get(PARAM_FEED);
            Feed feed = new Gson().fromJson(feedString.getString(), Feed.class);
            Author author = feed.getAuthor();
            if (StringUtils.isBlank(feed.getTitle())) {
                payload.setMsg("cannot create feed as title is blank");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            } else if (author == null || StringUtils.isBlank(author.getUserId())) {
                payload.setMsg("cannot create feed as author is null or missing author information.");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            } else if (!this.permissionService.isValidUser(author.getUserId())) {
                payload.setMsg(String.format("cannot create feed for author '%s' or missing author information.", author.getUserId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            } else {
                Photo photo = fileService.uploadPhoto(image.getInputStream());
                feed.setPhoto(photo);
                User authorUser;
                try {
                    authorUser = this.userService.getUserById(author.getUserId());
                } catch (UserNotFoundException e) {
                    payload.setMsg("cannot create feed as author is null or missing author information.");
                    payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                    return payload;
                }
                setAuthorRoles(feed, authorUser);
                this.updateFeedSystemDefinedAttributes(feed);
                Feed savedFeed = feedService.saveOrUpdate(feed);
                Feed statusChanged = feedService.updateStatus(savedFeed.getId(), FeedStatus.FINAL.name());
                payload.setMsg("ok");
                payload.setStatus(Response.Status.OK.getStatusCode());
                payload.setData(statusChanged);
                this.userService.increaseWorkCount(author.getUserId());
                return payload;
            }
        } catch (IOException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to upload image file to clould.");
            return payload;
        } catch (FileUploadException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to retrieve image file.");
            return payload;
        } catch (CloudinaryFileUploadException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to upload image file, please try again.");
            return payload;
        }

    }

    private void setAuthorRoles(Feed feed, User authorUser) {
        if (authorUser.getUserRoles() != null && !authorUser.getUserRoles().isEmpty()) {
            feed.setAuthorRoles(authorUser.getUserRoles());
        } else {
            Set<String> defaultRole = new HashSet<>();
            defaultRole.add(UserRole.DEFAULT.name());
            feed.setAuthorRoles(defaultRole);
        }
    }

    /**
     * Stylist uses this API to upload feed photo.
     *
     * @param token
     * @return
     */
    @POST
    @Path("/v1/photo/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> uploadPhoto(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<Feed> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot upload feed photo as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot upload feed photo as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot upload feed photo as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {
            Map<String, FileItem> fileItems = FileUploadUtils.getFeedPartsFromRequest(request);
            FileItem image = fileItems.get(PARAM_IMAGE);
            FileItem feedString = fileItems.get(PARAM_FEED);
            if (image == null) {
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                payload.setMsg("Fail to create feed as image cannot be found.");
                return payload;
            }
            Feed feed;
            boolean containsFeed = feedString != null;
            if (containsFeed) {
                feed = new Gson().fromJson(feedString.getString(), Feed.class);
                if (!this.feedService.isMyFeed(loggedInUser.getId(), feed.getId())) {
                    payload.setMsg(String.format("Cannot upload feed photo as this feed '%s' does not belong to you", feed.getId()));
                    payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                    return payload;
                }
            } else {
                feed = new Feed();
            }
            Author author = new Author(loggedInUser);
            feed.setAuthor(author);
            setAuthorRoles(feed, loggedInUser);

            Photo photo = fileService.uploadPhoto(image.getInputStream());
            feed.setPhoto(photo);
            this.updateFeedSystemDefinedAttributes(feed);
            Feed savedFeed = feedService.saveOrUpdate(feed);
            if (!containsFeed) {
                savedFeed = feedService.updateStatus(savedFeed.getId(), FeedStatus.DRAFT.name());
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(savedFeed);
            return payload;
        } catch (FeedNotFoundException e) {
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            LOGGER.warn(e.getMessage());
            payload.setMsg("Fail to upload image as feed cannot be found.");
            return payload;
        } catch (IOException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to upload image file to clould.");
            return payload;
        } catch (FileUploadException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to retrieve image file.");
            return payload;
        } catch (CloudinaryFileUploadException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg("Fail to upload image file.");
            return payload;
        }
    }

    @POST
    @Path("/v1/photo/change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> changePhoto(@HeaderParam(PARAM_JWT_TOKEN) String token, ChangePhotoDto dto) {
        Payload<Feed> payload = new Payload<>();
        Photo photo = dto.getPhoto();
        if (StringUtils.isBlank(dto.getFeedId())) {
            payload.setMsg("Cannot change feed photo as feed id is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(photo.getUrl())) {
            payload.setMsg("Cannot change feed photo as image url is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(photo.getPublicId())) {
            payload.setMsg("Cannot change feed photo as image id is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot change feed photo as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }

        if (!this.permissionService.isValidJwtToken(token)) {
            payload.setMsg("Cannot change feed photo as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        Feed feed = this.feedService.getById(dto.getFeedId());
        if (feed != null) {
            feed.setPhoto(photo);
            Feed updated = this.feedService.saveOrUpdate(feed);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(updated);
            return payload;
        } else {
            payload.setMsg("Cannot change feed photo as feed cannot found with id: " + dto.getFeedId());
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/v2/photo/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> uploadPhoto(@HeaderParam(PARAM_JWT_TOKEN) String token, Photo dto) {
        Payload<Feed> payload = new Payload<>();
        if (StringUtils.isBlank(dto.getUrl())) {
            payload.setMsg("Cannot create feed as image url is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getPublicId())) {
            payload.setMsg("Cannot create feed as image id is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot create feed as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create feed as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create feed as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        Feed feed = new Feed();
        Author author = new Author(loggedInUser);
        feed.setAuthor(author);
        setAuthorRoles(feed, loggedInUser);
        feed.setPhoto(dto);
        this.updateFeedSystemDefinedAttributes(feed);
        feed.setStatus(FeedStatus.DRAFT.name());
        Feed savedFeed = feedService.saveOrUpdate(feed);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(savedFeed);
        return payload;
    }

    @POST
    @Path("/admin/update/systags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> updateSysTags(@HeaderParam(PARAM_JWT_TOKEN) String token, FeedTagDto dto) {
        Payload<Feed> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot update feed sys tags  as logged in user does not have permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update feed sys tags  as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot update feed sys tags as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        Feed feed = this.feedService.updateSysTags(dto);
        if (feed != null) {
            payload.setMsg("ok");
            payload.setData(feed);
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } else {
            payload.setMsg("Cannot update feed sys tags  as feed is null after updating");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/admin/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> updateFeed(@HeaderParam(PARAM_JWT_TOKEN) String token, Feed feed) {
        Payload<Feed> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot update feed as logged in user does not have permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update feed as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot update feed as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        Author author = feed.getAuthor();
        if (StringUtils.isBlank(feed.getId())) {
            payload.setMsg("cannot update feed as id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else if (StringUtils.isBlank(feed.getTitle())) {
            payload.setMsg("cannot update feed as title is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else if (author == null || StringUtils.isBlank(author.getUserId())) {
            payload.setMsg("cannot update feed as author is null or missing author information.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (feed.getPhoto() == null) {
            payload.setMsg("cannot update feed as photo is null.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (!this.permissionService.isValidUser(author.getUserId())) {
            payload.setMsg(String.format("cannot update feed for author '%s' or missing author information.", author.getUserId()));
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } else {
            this.updateFeedSystemDefinedAttributes(feed);
            Feed savedFeed = feedService.saveOrUpdate(feed);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(savedFeed);
            return payload;
        }
    }

    @POST
    @Path("/v1/stylist/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> updateStylistFeed(@HeaderParam(PARAM_JWT_TOKEN) String token, Feed feed) {
        Payload<Feed> payload = new Payload<>();
        User loggedInUser;
        if (feed == null || StringUtils.isBlank(feed.getId())) {
            payload.setMsg("cannot update feed as it is null or missing id.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            loggedInUser = this.permissionService.getUser(token);
            if (!this.feedService.isMyFeed(loggedInUser.getId(), feed.getId())) {
                payload.setMsg(String.format("Cannot upload feed photo as this feed '%s' does not belong to you", feed.getId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot update feed  as user not found.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (FeedNotFoundException e) {
            payload.setMsg("Cannot update feed as feed not found.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update feed as invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        if (feed.getPhoto() == null) {
            payload.setMsg("cannot update feed as photo is null.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        Author author = new Author();
        author.setUserId(loggedInUser.getId());
        feed.setAuthor(author);
        this.updateFeedSystemDefinedAttributes(feed);
        Feed existingFeed = feedService.getById(feed.getId());
        if (existingFeed == null || !existingFeed.isEnabled()) {
            payload.setMsg(String.format("cannot update feed '%s' as cannot find it. It may be disabled.", feed.getId()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        Feed savedFeed = feedService.saveOrUpdate(feed);
        Feed updatedStatus = updateFeedStatus(savedFeed);
        if (existingFeed.getStatus().equals(FeedStatus.DRAFT.name()) && updatedStatus.getStatus().equals(FeedStatus.FINAL.name())) {
            // Only increase the user's works count when the feed is change from draft to final status
            if (!loggedInUser.isUploadLooks()) {
                this.userService.setFirstLookStatus(loggedInUser.getId());
            }
            this.userService.increaseWorkCount(loggedInUser.getId());
        }
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(updatedStatus);
        return payload;

    }

    @POST
    @Path("/v2/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> updateFeedV2(@HeaderParam(PARAM_JWT_TOKEN) String token, Feed feed) {
        Payload<Feed> payload = new Payload<>();
        User loggedInUser;
        if (feed == null) {
            payload.setMsg("cannot update feed as it is null");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            loggedInUser = this.permissionService.getUser(token);
            if (StringUtils.isNotBlank(feed.getId()) && !this.feedService.isMyFeed(loggedInUser.getId(), feed.getId())) {
                payload.setMsg(String.format("Cannot upload feed photo as this feed '%s' does not belong to you", feed.getId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot update feed  as user not found.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (FeedNotFoundException e) {
            payload.setMsg("Cannot update feed as feed not found.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update feed as invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        if (StringUtils.isNotBlank(feed.getId())) {
            Feed existingFeed = feedService.getById(feed.getId());
            if (existingFeed == null || !existingFeed.isEnabled()) {
                payload.setMsg(String.format("cannot update feed '%s' as cannot find it. It may be disabled.", feed.getId()));
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
        }

        Author author = new Author();
        author.setUserId(loggedInUser.getId());
        feed.setAuthor(author);
        setAuthorRoles(feed, loggedInUser);
        this.updateFeedSystemDefinedAttributes(feed);
        Feed savedFeed = feedService.saveOrUpdate(feed);

        //update status
        String newStatus = this.getFeedStatus(savedFeed.getContent(), savedFeed.getFeedTags(), savedFeed.getCoverImage() != null, feed.getStatus());
        Feed updatedFeedStatus = this.feedService.updateStatus(savedFeed.getId(), newStatus);

        //update looks count
        if (savedFeed.getStatus().equals(FeedStatus.DRAFT.name()) && updatedFeedStatus.getStatus().equals(FeedStatus.FINAL.name())) {
            // Only increase the user's works count when the feed is change from draft to final status
            if (!loggedInUser.isUploadLooks()) {
                this.userService.setFirstLookStatus(loggedInUser.getId());
            }
            this.userService.increaseWorkCount(loggedInUser.getId());
        }
        if (savedFeed.getStatus().equals(FeedStatus.FINAL.name()) && updatedFeedStatus.getStatus().equals(FeedStatus.DRAFT.name())) {
            this.userService.decreaseWorkCount(loggedInUser.getId());
        }
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(updatedFeedStatus);
        return payload;
    }

    private Feed updateFeedStatus(Feed feed) {
        String content = feed.getContent();
        Set<Tag> tags = feed.getFeedTags();
        String status;
        if (StringUtils.isNotBlank(content) && content.trim().length() >= FEED_CONTENT_REQUIRE
                && tags != null && !tags.isEmpty()) {
            status = FeedStatus.FINAL.name();
        } else {
            status = FeedStatus.DRAFT.name();
        }
        return feedService.updateStatus(feed.getId(), status);
    }

    private String getFeedStatus(String content, Set<Tag> tags, boolean hasCoverImage, String newStatus) {
        if (StringUtils.isNotBlank(newStatus)
                && newStatus.equals(FeedStatus.FINAL.name())
                && StringUtils.isNotBlank(content)
                && content.trim().length() >= FEED_CONTENT_REQUIRE
                && tags != null
                && !tags.isEmpty() && hasCoverImage) {
            return FeedStatus.FINAL.name();
        } else {
            return FeedStatus.DRAFT.name();
        }
    }

    @POST
    @Path("/del")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> deleteFeed(@HeaderParam(PARAM_JWT_TOKEN) String token, String feedId) {
        Payload<Feed> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot delete feed as logged in user does not have permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot delete feed as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot delete feed as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(feedId)) {
            payload.setMsg("cannot update feed as id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            Feed delfeed = this.feedService.disable(feedId);
            if (StringUtils.isNotBlank(delfeed.getStatus()) && delfeed.getStatus().equals(FeedStatus.FINAL.name())) {
                this.userService.decreaseWorkCount(delfeed.getAuthor().getUserId());
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(delfeed);
            return payload;
        }
    }

    @POST
    @Path("/v1/stylist/del")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> deleteStylistFeed(@HeaderParam(PARAM_JWT_TOKEN) String token, String feedId) {
        Payload<Feed> payload = new Payload<>();
        if (StringUtils.isBlank(feedId)) {
            payload.setMsg("cannot delete feed as id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        QueryCriteria<FeedEntity> query = new QueryCriteria<>();
        query.setFeedId(feedId);
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot found login user when delete feed.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {
            if (!this.feedService.isMyFeed(loggedInUser.getId(), feedId)) {
                payload.setMsg(String.format("Cannot delete the feed '%s' as the feed does not belong to user '%s'", feedId,
                        loggedInUser.getId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (FeedNotFoundException e) {
            payload.setMsg(String.format("cannot delete feed '%s' as feed does not exist", feedId));
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            return payload;
        }

        Feed delfeed = this.feedService.disable(feedId);
        if (StringUtils.isNotBlank(delfeed.getStatus()) && delfeed.getStatus().equals(FeedStatus.FINAL.name())) {
            this.userService.decreaseWorkCount(delfeed.getAuthor().getUserId());
        }
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(delfeed);
        return payload;
    }

    @POST
    @Path("/v1/share/stats")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<SharedFeed> updateSharedFeedStats(@HeaderParam(PARAM_JWT_TOKEN) String token, SharedFeedDto sharedFeedDto) {
        Payload<SharedFeed> payload = new Payload<>();
        String loggedInUserId = null;
        if (StringUtils.isNotBlank(token)) {
            try {
                User loggedInUser = this.permissionService.getUser(token);
                loggedInUserId = loggedInUser.getId();
            } catch (UserNotFoundException e) {
                LOGGER.warn(String.format("User cannot be found  with jwt token: '%s'", token));
            } catch (InvalidJwtToken e) {
                LOGGER.warn(String.format("JWT token is invalid when share the feed by id '%s';.", token));
            }
        }

        String feedId = sharedFeedDto.getFeedId();
        SocialSource socialMediaSource = sharedFeedDto.getSource();

        if (StringUtils.isBlank(feedId)) {
            payload.setMsg("Cannot share feed as id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        if (socialMediaSource == null) {
            payload.setMsg("Cannot share feed as social media source is missing");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        Feed feed = this.feedService.getById(feedId);
        if (feed == null) {
            payload.setMsg(String.format("Cannot share feed as feed is null with id '%s'", feedId));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        SharedFeed sharedFeed = new SharedFeed(loggedInUserId, feedId, feed.getAuthor().getUserId(), socialMediaSource.name());
        sharedFeed = this.feedService.shareFeed(sharedFeed);
        payload.setData(sharedFeed);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/v1/view")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<Feed> getFeedById(@HeaderParam(PARAM_JWT_TOKEN) String token, @QueryParam(PARAM_ID) String id) {
        Payload<Feed> payload = new Payload<>();
        QueryCriteria<FeedEntity> query = new QueryCriteria<>();
        query.setFeedId(id);
        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                query.setLoggedInUserId(user.getId());
            } catch (UserNotFoundException e) {
                LOGGER.warn(String.format("User cannot be found when get the feed: '%s' with jwt token: '%s'", id, token));
            } catch (InvalidJwtToken e) {
                LOGGER.warn(String.format("JWT token is invalid when fetch the feed by id '%s';.", id));
            }
        }
        Feed feed = this.feedService.getById(query);
        if (feed == null) {
            payload.setMsg(String.format("cannot find the feed by id '%s';.", id));
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            return payload;
        } else {
            this.feedService.increaseViewCount(id);
            this.userService.increaseFeedViewedCount(feed.getAuthor().getUserId());
            payload.setData(feed);
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setMsg("ok");
            return payload;
        }
    }

    @GET
    @Path("/v1/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<List<Feed>> listFeed(@HeaderParam(PARAM_JWT_TOKEN) String token,
                                        @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
                                        @QueryParam(PARAM_AUTHOR_ID) String authorId,
                                        @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken,
                                        @QueryParam(PARAM_FEED_TAGS) String tags,
                                        @QueryParam(PARAM_AUTHOR_ROLES) @DefaultValue("DESIGNER,STYLIST,INFLUENCER") String authorRoleList,
                                        @QueryParam(PARAM_SYS_TAGS) String systags,
                                        @QueryParam(PARAM_ORDER_BY) FeedOrder orderBy) {
        Payload<List<Feed>> payload = new Payload<>();
        QueryCriteria<FeedEntity> criteria = new QueryCriteria<>();
        criteria.setClazz(FeedEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        criteria.setStatus(FeedStatus.FINAL.name());
        List<String> authorRoles = QueryUtils.parseQueryList(authorRoleList);
        if (!authorRoles.isEmpty()) {
            criteria.setContainAuthorRoles(authorRoles);
        }
        if (StringUtils.isNotBlank(authorId)) {
            criteria.setAuthorId(authorId);
        }
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        List<String> tagList = QueryUtils.parseQueryList(tags);
        if (!tagList.isEmpty()) {
            criteria.setTags(tagList);
        }

        List<String> sysTagList = QueryUtils.parseQueryList(systags);
        if (!sysTagList.isEmpty()) {
            criteria.setSysTags(sysTagList);
        }

        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                criteria.setLoggedInUserId(user.getId());
            } catch (UserNotFoundException e) {
                LOGGER.warn(String.format("User cannot be found when list feeds with jwt token: '%s'", token));
            } catch (InvalidJwtToken e) {
                LOGGER.warn(String.format("invalid jwt token: '%s' when list feed.", token));
            }
        }
        if (orderBy != null) {
            criteria.setAscOrder(false);
            // Do not support next page token when orderBy top data.
            criteria.setNextPageToken(null);
            // Only show first 80 top results. It is hard to do the pagination.
            criteria.setResultSize(80);
            switch (orderBy) {
                case TOP_VIEW:
                    criteria.setSortBy("sumViewedCount");
                    break;
                case TOP_SHARE:
                    criteria.setSortBy("sumSharedCount");
                    break;
                case TOP_LIKE:
                    criteria.setSortBy("sumLikeCount");
                    break;
                default:
                    criteria.setSortBy("creationDate");
                    break;
            }
        } else {
            criteria.setSortBy("creationDate");
        }
        DtoListWrapper<Feed> feedWrapper = feedService.list(criteria);
        payload.setData(feedWrapper.getDtos());
        payload.setNextPageToken(feedWrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/v1/list/mine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<List<Feed>> listMyFeed(@HeaderParam(PARAM_JWT_TOKEN) String token,
                                          @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
                                          @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken,
                                          @QueryParam(PARAM_STATUS) String status) {
        Payload<List<Feed>> payload = new Payload<>();
        QueryCriteria<FeedEntity> criteria = new QueryCriteria<>();
        criteria.setClazz(FeedEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        if (StringUtils.isNotBlank(status)) {
            if (status.trim().equals(FeedStatus.DRAFT.name()) || status.trim().equals(FeedStatus.FINAL.name())) {
                criteria.setStatus(status);
            }
        }
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                criteria.setAuthorId(user.getId());
            } catch (UserNotFoundException e) {
                payload.setMsg(String.format("User cannot be found when list feeds with jwt token: '%s'", token));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            } catch (InvalidJwtToken e) {
                payload.setMsg(String.format("invalid jwt token: '%s' when list feed.", token));
                payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
                return payload;
            }
        } else {
            payload.setMsg(String.format("Missing jwt token when list feeds "));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        criteria.setSortBy("creationDate");
        DtoListWrapper<Feed> feedWrapper = feedService.list(criteria);
        payload.setData(feedWrapper.getDtos());
        payload.setNextPageToken(feedWrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/v1/mine/feedcount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<FeedCount> listMyFeedCount(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<FeedCount> payload = new Payload<>();
        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                FeedCount feedCount = this.feedService.getMyFeedCount(user.getId());
                payload.setData(feedCount);
                payload.setMsg("ok");
                payload.setStatus(Response.Status.OK.getStatusCode());
                return payload;
            } catch (UserNotFoundException e) {
                payload.setMsg(String.format("User cannot be found when get my feed count with jwt token: '%s'", token));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            } catch (InvalidJwtToken e) {
                payload.setMsg(String.format("invalid jwt token: '%s' when get my feed count.", token));
                payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
                return payload;
            }
        } else {
            payload.setMsg(String.format("Missing jwt token when get my feed count "));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

    }

    /**
     * Update feed media and categories attributes
     *
     * @param feed
     */
    private void updateFeedSystemDefinedAttributes(Feed feed) {
        Media coverImage = this.getCoverImage(feed);
        List<Media> mediaContent = this.getUniqueMediaContent(feed);
        feed.setCoverImage(coverImage);
        feed.setMediaContent(mediaContent);
        feed.setType(this.getFeedType(feed));
        feed.setCategory(FeedCategory.STYLE.name());
    }

    private Media getCoverImage(Feed feed) {
        for (Media media : feed.getMediaContent()) {
            if (media.isCoverImage()) {
                return media;
            }
        }
        Media coverImage = feed.getCoverImage();
        // choose first image as cover image when cover image is null.
        if (coverImage == null) {
            for (Media media : feed.getMediaContent()) {
                if (media.getMediaType().equals(com.zion.media.MediaType.IMAGE.name())) {
                    media.setCoverImage(true);
                    return media;
                }
            }
        }
        if (coverImage != null) {
            coverImage.setCoverImage(true);
        }
        return coverImage;
    }

    private List<Media> getUniqueMediaContent(Feed feed) {
        List<Media> mediaContent = this.getMediaContent(feed);
        List<Media> uniqueMediaContent = new ArrayList<>();
        for (Media media : mediaContent) {
            media.setCoverImage(false);
            if (!uniqueMediaContent.contains(media)) {
                uniqueMediaContent.add(media);
            }
        }
        return uniqueMediaContent;
    }

    private List<Media> getMediaContent(Feed feed) {
        List<Media> contentImages = feed.getMediaContent();
        boolean removedConverImage = contentImages.removeIf(image -> image.isCoverImage());
        Media oldCoverImage = feed.getCoverImage();

        if (removedConverImage && oldCoverImage != null && !contentImages.contains(oldCoverImage)) {
            oldCoverImage.setCoverImage(false);
            contentImages.add(0, oldCoverImage);
            return contentImages;
        }

        // choose first image as cover image when there is not cover images.
        if (!removedConverImage && oldCoverImage == null) {
            Media firstImage = null;
            for (Media media : contentImages) {
                if (media.getMediaType().equals(com.zion.media.MediaType.IMAGE.name())) {
                    firstImage = media;
                    break;
                }
            }
            if (firstImage != null) {
                contentImages.remove(firstImage);
            }
            return contentImages;
        }

        return contentImages;
    }

    private String getFeedType(Feed feed) {
        int numberOfImage = 0;
        if (feed.getCoverImage() != null) {
            numberOfImage = numberOfImage + 1;
        }
        boolean hasVideo = false;
        List<Media> content = feed.getMediaContent();
        for (Media media : content) {
            if (media.getMediaType().equals(com.zion.media.MediaType.IMAGE.name())) {
                numberOfImage = numberOfImage + 1;
            }
            if (media.getMediaType().equals(com.zion.media.MediaType.VIDEO.name())) {
                hasVideo = true;
            }

        }
        String type = FeedType.SINGLE_IMAGE.name();
        if (numberOfImage > 1) {
            type = FeedType.IMAGES.name();
        }
        if (hasVideo) {
            type = FeedType.MIXED_VIDEO.name();
        }
        return type;
    }
}
