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
package com.zion.mail;

public class WelcomeUserEmailContent extends EmailContentModel {
    private String campaignUrl;
    private String applyUrl;

    public WelcomeUserEmailContent(String emailTitle, String receiverName, String campaignUrl, String applyUrl) {
        super(emailTitle, receiverName);
        this.campaignUrl = campaignUrl;
        this.applyUrl = applyUrl;
    }


    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }


    public String getCampaignUrl() {
        return campaignUrl;
    }


    public void setCampaignUrl(String campaignUrl) {
        this.campaignUrl = campaignUrl;
    }

}

