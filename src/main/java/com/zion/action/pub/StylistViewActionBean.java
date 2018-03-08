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
package com.zion.action.pub;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.action.base.PubBaseActionBean;
import com.zion.action.model.ShareMeta;
import com.zion.common.TrackRequestParamValue;
import com.zion.user.User;
import com.zion.user.service.UserService;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding(StylistViewActionBean.URL)
public class StylistViewActionBean extends PubBaseActionBean {

    public static final String URL = "/action/stylist/view";
    
    private static final String SHOP_HOME_JSP_NAME = "trendsetterHome";

    private String userId;

    /**
     * This is optional field and used to track where is this url clicked from. eg: short url is clicked.
     */
    private String source;

    @Inject
    private UserService userService;

    // Below does not work, currently only support inject member not methods
    // take a look GuiceInterceptor
    // @Inject
    // public StylistViewActionBean(UserService userService) {
    // this.userService = userService;
    // }

    @DefaultHandler
    public Resolution initView() {
        initUserShareMeta();
        this.updateTrackingCount();
        if (this.isUserFromSharedLink()) {
            return new ForwardResolution(this.getPubView(SHOP_HOME_JSP_NAME));
        }else {
            return new ForwardResolution(getPubView(this.getClass()));
            
        }
    }

    private void updateTrackingCount() {
        if(this.isUserFromSharedLink()) {
            this.userService.increaseTrackingCount(this.getUserId());
        }

    }
    
    private boolean isUserFromSharedLink() {
        return StringUtils.isNotBlank(this.getUserId()) 
                && StringUtils.isNotBlank(this.getSource()) 
                && this.getSource().trim().equals(TrackRequestParamValue.TINY_URL.getKey());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private void initUserShareMeta() {
        try {
            User user = userService.getUserById(this.userId);
            if (user.isEnabled()) {
                String avatar = user.getPerson() != null ? user.getPerson().getAvatar() : "";
                String getUserViewPage = parseUserViewPageUrl();
                ShareMeta shareMeta = new ShareMeta(user.getDisplayName(), user.getIntroSummary(), getUserViewPage, "article", avatar);
                this.setShareMeta(shareMeta);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private String parseUserViewPageUrl() {
        return this.getWebConfig().getActionUrl() + "/stylist/view?userId=" + getUserId();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
