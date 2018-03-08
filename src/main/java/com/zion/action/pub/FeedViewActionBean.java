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
import com.zion.common.QueryCriteria;
import com.zion.common.TrackRequestParamValue;
import com.zion.feed.Feed;
import com.zion.feed.FeedService;
import com.zion.morphia.entity.FeedEntity;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Be careful, this url must not be changed. It was used by short url.
 */
@UrlBinding(FeedViewActionBean.URL)
public class FeedViewActionBean extends PubBaseActionBean {

    public static final String URL = "/action/feed";
    
    @Inject
    private FeedService feedService;

    /**
     * This feedId name must not be changed.
     * It was used by short url.
     */
    private String feedId;
    
    /**
     * This is optional field and used to track where is this url clicked from.
     * eg: short url is clicked. 
     */
    private String source;
    
    @DefaultHandler
    public Resolution initView() {
        initFeedShareMeta();
        this.updateTrackingCount();
        return new ForwardResolution(getPubView(this.getClass()));
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }
    
    private void initFeedShareMeta() {
        QueryCriteria<FeedEntity> query = new QueryCriteria<>();
        query.setFeedId(getFeedId());
        Feed feed = feedService.getById(query);
        if (feed != null && feed.isEnabled()) {
            String feedImage = feed.getCoverImage() != null ? feed.getCoverImage().getUrl() : "";
            String url = StringUtils.isNotBlank(feed.getShortUrl()) ? feed.getShortUrl() : parseFeedViewPageUrl();
            String title = getSharedMetaTitle(feed);
            String description = feed.getContent();
            ShareMeta shareMeta = new ShareMeta(title, description, url, "article", feedImage);
            this.setShareMeta(shareMeta);
        }
    }
    
    private void updateTrackingCount() {
        if(StringUtils.isNotBlank(this.getFeedId()) 
                && StringUtils.isNotBlank(this.getSource()) 
                && this.getSource().trim().equals(TrackRequestParamValue.TINY_URL.getKey())) {
            this.feedService.increaseTrackingCount(this.getFeedId());
        }
    }
    
    private String parseFeedViewPageUrl() {
        return this.getWebConfig().getActionUrl() + "/feed?feedId=" + getFeedId();
    }
    
    private String getSharedMetaTitle(Feed feed) {
        if (StringUtils.isBlank(feed.getTitle())) {
            return feed.getAuthor().getDisplayName();
        } 
        return feed.getTitle();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
