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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import com.zion.auth.InvalidJwtToken;
import com.zion.common.DtoListWrapper;
import com.zion.common.FollowerDto;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.common.QueryUtils;
import com.zion.common.RegistrationStatus;
import com.zion.common.TermCondition;
import com.zion.common.UserRole;
import com.zion.follower.Follower;
import com.zion.follower.service.FollowerService;
import com.zion.media.CloudinaryFileUploadException;
import com.zion.media.FileService;
import com.zion.media.FileUploadUtils;
import com.zion.media.Photo;
import com.zion.morphia.entity.UserEntity;
import com.zion.permission.PermissionService;
import com.zion.rate.RateDto;
import com.zion.rate.service.RateService;
import com.zion.user.Contact;
import com.zion.user.Person;
import com.zion.user.User;
import com.zion.user.UserRoleDto;
import com.zion.user.UserStatusDto;
import com.zion.user.UserSummary;
import com.zion.user.UserTagDto;
import com.zion.user.service.DuplicatedUserException;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;
import com.zion.validation.utils.UserValidationUtils;

@Path("/user")
public class UserResource extends BaseResource {
    private static final String PARAM_INCLUDE_DISABLED = "includeDisabled";
    private static final String PARAM_Q = "q";
    private static final String PARM_USER_ROLES = "roles";
    private static final String PARAM_IMAGE = "image";
    private static final String PARAM_COUNTRY_CODE = "countryCode";
    private static final String PARAM_MIN_WORK_COUNT = "minWorkCount";
    private UserService userService;
    private PermissionService permissionService;
    private RateService rateService;
    private FollowerService followerService;
    private FileService fileService;

    @Context
    private HttpServletRequest request;

    @Inject
    public UserResource(UserService userService, PermissionService permissionService, RateService rateService,
            FollowerService followerService, FileService fileService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.rateService = rateService;
        this.followerService = followerService;
        this.fileService = fileService;
    }

    @GET
    @Path("/v1/me")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<UserSummary> getMyProfile(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<UserSummary> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(user);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot found the user");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
    }

    @GET
    @Path("/v1/view")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<UserSummary> getUserProfile(@HeaderParam(PARAM_JWT_TOKEN) String token, @QueryParam(PARAM_ID) String id) {
        Payload<UserSummary> payload = new Payload<>();
        try {
            User user = this.userService.getUserById(id);
            if (StringUtils.isNotBlank(token)) {
                User loggedInUser = this.permissionService.getUser(token);
                RateDto rateDto = this.rateService.get(loggedInUser.getId(), user.getId());
                user.setLoggedInUserRate(rateDto);
                Follower follower = this.followerService.get(new FollowerDto(loggedInUser.getId(), user.getId()));
                if (follower != null) {
                    user.setLoggedInUserFollowed(true);
                }
                if (loggedInUser.getId().equals(user.getId())) {
                    user.setMine(true);
                }
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(user);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg(e.getMessage());
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg(e.getMessage());
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
    }

    @GET
    @Path("/list")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<User>> listUsers(@HeaderParam(PARAM_JWT_TOKEN) String token,
            @QueryParam(PARAM_INCLUDE_DISABLED) @DefaultValue("true") Boolean includeDisabled, @QueryParam(PARAM_Q) String q) {
        Payload<List<User>> payload = new Payload<>();
        List<User> users;
        if (StringUtils.isBlank(token)) {
            payload.setMsg("jwt token is missing");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        try {
            User loggedInUser = this.permissionService.getUser(token);
            Set<String> roles = loggedInUser.getUserRoles();
            if (roles != null && roles.contains(UserRole.SYSADMIN.name())) {
                users = this.userService.listAllUsers();
            } else if (roles != null && roles.contains(UserRole.ADMIN.name())) {
                users = this.userService.listNoAdminUsers(includeDisabled);
            } else {
                payload.setMsg("cannot list user as no permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (UserNotFoundException e) {
            payload.setMsg("cannot find user with token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }

        if (StringUtils.isNotBlank(q)) {
            users = users.stream().filter(new Predicate<User>() {

                @Override
                public boolean test(User t) {
                    String qs = q.trim().toLowerCase();
                    Person person = t.getPerson();
                    Contact contact = t.getContact();
                    if (t.getId().equals(qs)) {
                        return true;
                    }
                    if (person != null && StringUtils.isNotBlank(person.getFirstName())) {
                        if (person.getFirstName().toLowerCase().trim().contains(qs)) {
                            return true;
                        }
                    }

                    if (person != null && StringUtils.isNotBlank(person.getLastName())) {
                        if (person.getLastName().toLowerCase().trim().contains(qs)) {
                            return true;
                        }
                    }

                    if (contact != null && StringUtils.isNotBlank(contact.getEmail())) {
                        if (contact.getEmail().toLowerCase().contains(qs)) {
                            return true;
                        }
                    }
                    if (StringUtils.isNotBlank(t.getDisplayName())) {
                        if (t.getDisplayName().toLowerCase().contains(qs)) {
                            return true;
                        }
                    }
                    if (qs.equals(UserRole.ADMIN.name().toLowerCase()) || qs.equals(UserRole.STYLIST.name().toLowerCase())
                            || qs.equals(UserRole.DESIGNER.name().toLowerCase())
                            || qs.equals(UserRole.INFLUENCER.name().toLowerCase())
                            || qs.equals(UserRole.PUPPET.name().toLowerCase())) {
                        Set<String> userRoles = t.getUserRoles();
                        if (userRoles != null && userRoles.contains(qs.toUpperCase())) {
                            return true;
                        }
                    }
                    if (qs.equals("trendsetter")) {
                        Set<String> userRoles = t.getUserRoles();
                        if (userRoles != null && (userRoles.contains(UserRole.STYLIST.name())
                                || userRoles.contains(UserRole.DESIGNER.name())
                                || userRoles.contains(UserRole.INFLUENCER.name()))) {
                            return true;
                        }
                    }
                    return false;
                }
            }).collect(Collectors.toList());
        }
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(users);
        return payload;
    }

    @GET
    @Path("/registration/list")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<User>> listPendingUsers(@HeaderParam(PARAM_JWT_TOKEN) String token,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken, @QueryParam(PARAM_STATUS) String status) {
        Payload<List<User>> payload = new Payload<>();
        QueryCriteria<UserEntity> criteria = new QueryCriteria<>();
        try {
            if (!this.permissionService.hasAnyRole(token, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot list users as logged in user is not admin");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot list users as logged in user cannot be found.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isNotBlank(status)) {
            try {
                RegistrationStatus regStatus = RegistrationStatus.valueOf(status);
                criteria.setRegistrationStatus(regStatus.name());
            } catch (Exception e) {
                payload.setMsg(String.format("Cannot list users as registration status '%s' is not suport", status));
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            }
        } else {
            payload.setMsg("Cannot list users as missing registratin status");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        criteria.setClazz(UserEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        criteria.setContainUserRoles(Arrays.asList(UserRole.DESIGNER.name(), UserRole.INFLUENCER.name(), UserRole.STYLIST.name()));
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        criteria.setAscOrder(true);
        criteria.setSortBy("creationDate");
        DtoListWrapper<User> users = this.userService.list(criteria);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(users.getDtos());
        payload.setNextPageToken(users.getNextPageToken());
        return payload;
    }

    @GET
    @Path("/v1/list")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<User>> fetchUsers(@QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken, @QueryParam(PARM_USER_ROLES) String userRoles, 
            @QueryParam(PARAM_COUNTRY_CODE)String countryCode, @QueryParam(PARAM_MIN_WORK_COUNT)@DefaultValue("1") int minWorkCount,
            @QueryParam(PARAM_TAGS) String tags) {
        Payload<List<User>> payload = new Payload<>();
        QueryCriteria<UserEntity> criteria = new QueryCriteria<>();
        criteria.setClazz(UserEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        criteria.setWorkCountBaseLine(Long.valueOf(minWorkCount));
        if (StringUtils.isNotBlank(countryCode)) {
            criteria.setUserCountryCode(countryCode.trim());
        }
        List<String> roles = getUserRoles(userRoles);
        if (roles.isEmpty()) {
            criteria.setContainUserRoles(Arrays.asList(UserRole.DESIGNER.name(), UserRole.INFLUENCER.name(), UserRole.STYLIST.name()));
        } else {
            criteria.setContainUserRoles(roles);
        }
        List<String> tagList = QueryUtils.parseQueryList(tags);
        if (!tagList.isEmpty()) {
            criteria.setSysTags(tagList);
        }
        criteria.setRegistrationStatus(RegistrationStatus.APPROVED.name());
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        criteria.setAscOrder(false);
        criteria.setSortBy("creationDate");
        DtoListWrapper<User> users = this.userService.list(criteria);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(users.getDtos());
        payload.setNextPageToken(users.getNextPageToken());
        return payload;
    }

    private List<String> getUserRoles(String userRoles) {
        List<String> roleList = new ArrayList<>();
        if (StringUtils.isNotBlank(userRoles)) {
            String[] roles = StringUtils.split(userRoles.trim(), QueryUtils.LIST_PARAM_SPERATOR);
            for (String role : roles) {
                String trimedRole = role.trim();
                try {
                    UserRole userRole = UserRole.valueOf(trimedRole);
                    if (userRole.equals(UserRole.DESIGNER) || userRole.equals(UserRole.INFLUENCER) || userRole.equals(UserRole.STYLIST)) {
                        roleList.add(trimedRole);
                    }
                } catch (Exception e) {
                    // Do nothing.
                }
            }
        }
        return roleList;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> createUser(@HeaderParam(PARAM_JWT_TOKEN) String token, User user) {
        Payload<User> payload = new Payload<>();
        try {
            if (!this.permissionService.canCreateUser(token)) {
                payload.setMsg("Cannot create a new user as only admin is allowed this action.");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (StringUtils.isBlank(user.getUsername())) {
            payload.setMsg("Cannot create a new user as username is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        try {
            User newUser;
            if (StringUtils.isBlank(user.getPassword())) {
                newUser = this.userService.createUserWithoutPassword(user);
            } else {
                newUser = this.userService.createUser(user);
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(newUser);
            return payload;
        } catch (DuplicatedUserException e) {
            payload.setMsg("Cannot create a new user as the user already exists, please check username.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/v1/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> updateUser(@HeaderParam(PARAM_JWT_TOKEN) String token, User user) {
        Payload<User> payload = new Payload<>();
        if (user == null) {
            payload.setMsg("Cannot update the user as user is null");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(user.getId())) {
            payload.setMsg("Cannot update the user as user id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        if (StringUtils.isBlank(user.getUsername())) {
            payload.setMsg("Cannot update the user as username is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        if (!UserValidationUtils.isValidUser(user)) {
            payload.setMsg("Cannot update the user as some user attributes are invalid.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        try {
            if (!this.permissionService.canUpdateUser(token, user.getId())) {
                payload.setMsg("Cannot create a new user as only admin is allowed this action.");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        try {
            User newUser = this.userService.updateUser(user);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(newUser);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg(String.format("Cannot update the user as the user '%s' cannot be found.", user.getUsername()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/v1/change/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> changeAvatar(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<User> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot upload avatar photo as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot upload avatar photo as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot upload avatar photo as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {
            Map<String, FileItem> fileItems = FileUploadUtils.getFeedPartsFromRequest(request);
            FileItem image = fileItems.get(PARAM_IMAGE);
            Map<String, String> optionalParams = new HashMap<>();
            optionalParams.put("folder", "avatar");
            Photo photo = fileService.cropPhoto(image.getInputStream(), optionalParams, 150, 150);
            loggedInUser.getPerson().setAvatar(photo.getUrl());
            User updatedUser = this.userService.updateUser(loggedInUser);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(updatedUser);
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
        } catch (UserNotFoundException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg(String.format("Fail to update the logged in user '%s' when change avatar", loggedInUser.getId()));
            return payload;
        }
    }

    @POST
    @Path("/v2/change/avatar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> changeAvatarV2(@HeaderParam(PARAM_JWT_TOKEN) String token, String avatarUrl) {
        Payload<User> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot upload avatar photo as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(avatarUrl)) {
            payload.setMsg("Cannot upload avatar photo as missing avatarUrl");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot upload avatar photo as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot upload avatar photo as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {

            loggedInUser.getPerson().setAvatar(avatarUrl.trim());
            User updatedUser = this.userService.updateUser(loggedInUser);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(updatedUser);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            payload.setMsg(String.format("Fail to update the logged in user '%s' when change avatar", loggedInUser.getId()));
            return payload;
        }
    }

    @POST
    @Path("/update/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> updateUser(@HeaderParam(PARAM_JWT_TOKEN) String token, UserStatusDto user) {
        Payload<User> payload = new Payload<>();
        if (user == null) {
            payload.setMsg("Cannot update the user status as user is null");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(user.getId())) {
            payload.setMsg("Cannot update the user status as user id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        if (StringUtils.isBlank(user.getUsername())) {
            payload.setMsg("Cannot update the user status as username is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        try {
            if (!this.permissionService.canUpdateUserStatus(token, user.getId())) {
                payload.setMsg("Cannot create a new user as only admin is allowed this action.");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        try {
            User newUser;
            if (user.isEnabled()) {
                newUser = this.userService.enableUser(user.getUsername());
            } else {
                newUser = this.userService.disableUser(user.getUsername());
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(newUser);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg(String.format("Cannot update the user as the user '%s' cannot be found.", user.getUsername()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/update/tags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> updateUserTags(@HeaderParam(PARAM_JWT_TOKEN) String token, UserTagDto userTagDto) {
        Payload<User> payload = new Payload<>();
        if (userTagDto == null) {
            payload.setMsg("Cannot update user tags as userTagDto is empty.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (userTagDto.getTags() == null) {
            payload.setMsg("Cannot update user tags as tags is null.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(userTagDto.getUserId())) {
            payload.setMsg("Cannot update user tags as user id is blank.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            if (!this.permissionService.canUpdateUser(token, userTagDto.getUserId())) {
                payload.setMsg("Cannot update user tags as only admin is allowed this action.");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        User newUser = this.userService.updateUserTags(userTagDto);
        if (newUser != null) {
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(newUser);
            return payload;
        } else {
            payload.setMsg("fail to update user tags as user is null after updating");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return payload;
        }

    }

    @POST
    @Path("/update/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> updateUserRole(@HeaderParam(PARAM_JWT_TOKEN) String token, UserRoleDto userRoleDto) {
        Payload<User> payload = new Payload<>();
        try {
            if (!this.permissionService.canUpdateUserRole(token)) {
                payload.setMsg("Cannot update user roles as only admin is allowed this action.");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (userRoleDto == null) {
            payload.setMsg("Cannot update user roles as user is empty.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (userRoleDto.getUserRoles() == null) {
            payload.setMsg("Cannot update user roles as role is empty.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(userRoleDto.getUsername())) {
            payload.setMsg("Cannot update user roles as username is blank.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        for (String role : userRoleDto.getUserRoles()) {
            try {
                UserRole.valueOf(role);
            } catch (Exception e) {
                payload.setMsg(String.format("Cannot update user roles as role '%s' is not supported.", role));
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
        }

        try {
            User newUser = this.userService.updateUserRoles(userRoleDto.getUsername(), userRoleDto.getUserRoles());
            if (userRoleDto.getUserRoles() == null || userRoleDto.getUserRoles().isEmpty()) {
                newUser = this.userService.updateRegistrationStatus(newUser.getId(), RegistrationStatus.DEFAULT);
            } else if (newUser.getRegistrationStatus()!= null && newUser.getRegistrationStatus().equals(RegistrationStatus.PENDING.name())){
                //Do not do any thing.
            }else {
                newUser = this.userService.updateRegistrationStatus(newUser.getId(), RegistrationStatus.APPROVED);
            }
            if (userRoleDto.getUserRoles() != null && userRoleDto.getUserRoles().contains(UserRole.ADMIN.name())) {
                userService.setDefaultPassword(newUser.getUsername());
            }
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(newUser);
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg(String.format("Cannot update user role as the user '%s' is not found", userRoleDto.getUsername()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/v1/agree/termcondition")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> agreeTermCondition(@HeaderParam(PARAM_JWT_TOKEN) String token, TermCondition termCondition) {
        Payload<User> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (termCondition.equals(TermCondition.UPLOAD_PHOTO)) {
                user = this.userService.agreeUploadPhotoCondition(user.getId());
                if (user == null) {
                    throw new UserNotFoundException("cannot find the user");
                }
                payload.setMsg("ok");
                payload.setStatus(Response.Status.OK.getStatusCode());
                payload.setData(user);
                return payload;
            } else {
                payload.setMsg("Cannot support the term condition " + termCondition);
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot find the user");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
    }

}
