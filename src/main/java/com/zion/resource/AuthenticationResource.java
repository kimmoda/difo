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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zion.common.*;

import org.apache.commons.lang3.StringUtils;

import com.zion.auth.AuthToken;
import com.zion.auth.AuthenticationService;
import com.zion.auth.EncryptService;
import com.zion.auth.InvalidAuthToken;
import com.zion.auth.InvalidJwtToken;
import com.zion.auth.JwtClaim;
import com.zion.auth.JwtService;
import com.zion.permission.PermissionService;
import com.zion.socialmedia.SocialMediaAuthException;
import com.zion.user.User;
import com.zion.user.UserAuthDto;
import com.zion.user.service.DuplicatedUserException;
import com.zion.user.service.InvalidPasswordException;
import com.zion.user.service.UserDisabledException;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;
import org.cloudinary.json.JSONObject;
import org.cloudinary.json.JSONTokener;

@Path("/auth")
public class AuthenticationResource extends BaseResource {
	
	private UserService userService;
	private JwtService jwtService;
	private PermissionService permissionService;
	private EncryptService encryptService;
	private AuthenticationService authService;
	
	@Inject
	public AuthenticationResource(UserService userService, JwtService jwtService, PermissionService permissionService, EncryptService encryptService, AuthenticationService authService) {
		this.userService = userService;
		this.encryptService = encryptService;
		this.jwtService= jwtService;
		this.permissionService = permissionService;
		this.authService = authService;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Payload<User> login(UserAuthDto userAuthDto) throws DuplicatedUserException, UserNotFoundException{
		Payload<User> payload = new Payload<>();
		User validUser = userService.getValidUser(userAuthDto);
		if(validUser != null && validUser.getUserRoles() != null && this.permissionService.containAnyRole(validUser, UserRole.ADMIN.name())) {
			JwtClaim newClaim = jwtService.constructDefaultJwtClaim(userAuthDto.getUsername().trim());
			String jwtToken = jwtService.issueToken(newClaim);
			payload.setData(validUser);
			payload.setJwtToken(jwtToken);
			payload.setMsg("ok");
			payload.setStatus(Response.Status.OK.getStatusCode());
		}else {
			String defaultUsername = "admin";
			String password = "password";
			try {
				userService.getUserByUsername(defaultUsername);
				payload.setMsg("Fail to login, Please check username and passwrod.");
				payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			} catch (UserNotFoundException e) {
				User defaultUser = new User();
				defaultUser.setUsername(defaultUsername);
				defaultUser.setPassword(password);
				userService.createUser(defaultUser);
				Set<String> roles = new HashSet<>();
				roles.add(UserRole.ADMIN.name());
				roles.add(UserRole.SYSADMIN.name());
				User user =  userService.updateUserRoles(defaultUsername, roles);
				userService.setDefaultPassword(defaultUsername);
				payload.setData(user);
				JwtClaim newClaim = jwtService.constructDefaultJwtClaim(defaultUsername);
				String jwtToken = jwtService.issueToken(newClaim);
				payload.setJwtToken(jwtToken);
				payload.setMsg("ok");
				payload.setStatus(Response.Status.OK.getStatusCode());
			}
		}
		return payload;
	}
	
	@POST
    @Path("/v1/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<User> socialMediaLogin(AuthToken authToken) {
	    Payload<User> payload = new Payload<>();
        try {
            User user = authService.socialMediaLogin(authToken);
            if(!user.isEnabled()) {
                throw new UserDisabledException("User is disabled, please contact admin user.");
            }
            payload.setData(user);
            payload.setJwtToken(user.getJwt());
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
        } catch(IllegalArgumentException e) {
            payload.setMsg("Bad request. Please check the request fields.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        } catch (InvalidAuthToken e) {
            payload.setMsg("Authentication token is invalid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        } catch (SocialMediaAuthException e) {
            payload.setMsg("Social media authentication verification fails");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        } catch (DuplicatedUserException e) {
            payload.setMsg("Fail to save the user.");
            payload.setStatus(Response.Status.CONFLICT.getStatusCode());
        } catch (EmptyEmailException e) {
            payload.setMsg("Fail to login as user profile's email is missing, please check your social media profile and ensure it contains a valid email.");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        } catch (UserDisabledException e) {
            payload.setMsg("User is disabled, Please contact with admin user.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        }
        return payload;
	}
	
	@POST
	@Path("/update/username")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
	public Payload<User> updateUsername(@HeaderParam(PARAM_JWT_TOKEN)String token, UsernameChangeDto userNameChangeDto) throws UserNotFoundException {
	    Payload<User> payload = new Payload<>();
	    User user;
	    try {
            user = this.permissionService.getUser(token);
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update password as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
	    if(!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
            payload.setMsg("Cannot update password as the user is not admin");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
	    try{
	        User updatedUser = this.userService.updateUserName(userNameChangeDto);
	        payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(updatedUser);
            return payload;
	    }catch(IllegalArgumentException e) {
	        payload.setMsg(e.getMessage());
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
	    }
	}
	
	@POST
    @Path("/generate/fileupload/signature")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<String> generateFileUploadSignature(@HeaderParam(PARAM_JWT_TOKEN)String token, Map<String, String> paramsToSign) {
        Payload<String> payload = new Payload<>();
        if (!this.permissionService.isValidJwtToken(token)) {
            payload.setMsg("Cannot generate file upload signature as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        
        String secretKey = (String) AppConfig.getInstance().getCloudinaryConfig().get("api_secret");
        String signature = this.encryptService.generateFileUploadSignature(paramsToSign, secretKey);
        payload.setMsg("ok");
        payload.setData(signature);
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
	}

	@POST
	@Path("/update/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Payload<User> changePassword(@HeaderParam(PARAM_JWT_TOKEN)String token, PasswordChangeDto psdto) throws UserNotFoundException {
		Payload<User> payload = new Payload<>();
		User user;
        try {
            user = this.permissionService.getUser(token);
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update password as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
		try {
			if(!this.permissionService.hasAnyRole(token, UserRole.ADMIN.name())) {
				payload.setMsg("Cannot update password as the user is not admin");
				payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
				return payload;
			}
		} catch (UserNotFoundException e) {
			payload.setMsg("Cannot update password as user cannot be found");
			payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			return payload;
		}
		if(psdto == null) {
			payload.setMsg("Cannot update password as it is null.");
			payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
			return payload;
		}
		if(StringUtils.isBlank(psdto.getOldPs())) {
			payload.setMsg("Cannot update password as old password is blank.");
			payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
			return payload;
		}
		if(StringUtils.isBlank(psdto.getNewPs())) {
			payload.setMsg("Cannot update password as new password is blank.");
			payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
			return payload;
		}
		
		UserAuthDto userAuthDto = new UserAuthDto();
		userAuthDto.setUsername(user.getUsername());
		userAuthDto.setPassword(psdto.getOldPs());

		try {
			User updatedUser = this.userService.updateUserPassword(userAuthDto, psdto.getNewPs());
			payload.setData(updatedUser);
			payload.setMsg("ok");
			payload.setStatus(Response.Status.OK.getStatusCode());
			return payload;
		} catch (InvalidPasswordException e) {
			payload.setMsg("Cannot update password as old password is invalid");
			payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return payload;
		}
	}

	@POST
	@Path("/verify/recaptcha")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Payload<Boolean> validationReCAPTCHA(String responseCode) throws Exception {
		Payload<Boolean> payload = new Payload<>();
		if(StringUtils.isBlank(responseCode)) {
			payload.setMsg("Cannot verify the response code as it is null.");
			payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
			return payload;
		}
		JSONObject jsonObject = performRecaptchaSiteVerify(responseCode);
		boolean success = jsonObject.getBoolean("success");
		payload.setData(success);
		if (success){
			payload.setMsg("success");
			payload.setStatus(Response.Status.OK.getStatusCode());
		}else {
			payload.setMsg("failed");
			payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return payload;
	}

	private JSONObject performRecaptchaSiteVerify(String recaptchaResponseToken)
			throws IOException {
		ReCaptcha reCaptcha = AppConfig.getInstance().getReCaptchaConfig();
		URL url = new URL(reCaptcha.getSiteVerifyUrl());
		StringBuilder postData = new StringBuilder();
		addParam(postData, reCaptcha.getSecretParam(), reCaptcha.getSiteSecret());
		addParam(postData, reCaptcha.getResponseParam(), recaptchaResponseToken);

		return postAndParseJSON(url, postData.toString());
	}

	private JSONObject postAndParseJSON(URL url, String postData) throws IOException {
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty(
				"Content-Type", "application/x-www-form-urlencoded");
		urlConnection.setRequestProperty(
				"charset", StandardCharsets.UTF_8.displayName());
		urlConnection.setRequestProperty(
				"Content-Length", Integer.toString(postData.length()));
		urlConnection.setUseCaches(false);
		urlConnection.getOutputStream()
				.write(postData.getBytes(StandardCharsets.UTF_8));
		JSONTokener jsonTokener = new JSONTokener(urlConnection.getInputStream());
		return new JSONObject(jsonTokener);
	}

	private StringBuilder addParam(
			StringBuilder postData, String param, String value)
			throws UnsupportedEncodingException {
		if (postData.length() != 0) {
			postData.append("&");
		}
		return postData.append(
				String.format("%s=%s",
						URLEncoder.encode(param, StandardCharsets.UTF_8.displayName()),
						URLEncoder.encode(value, StandardCharsets.UTF_8.displayName())));
	}


}

