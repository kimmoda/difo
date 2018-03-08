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

import com.zion.common.AppConfig;

public class EmailContentModel {
    private String emailTitle;
    private String receiverName;
    private String homeUrl;
    private String brandName;
    private String supportEmail;

    public EmailContentModel(String emailTitle, String receiverName) {
        this.emailTitle = emailTitle;
        this.receiverName = receiverName;
        this.supportEmail = AppConfig.getInstance().getSesConfiguration().getSupport();
        this.homeUrl = AppConfig.getInstance().getAppHost();
        this.brandName = AppConfig.getInstance().getEnabledApp().getBrandName();
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getBrandName() {
        return brandName;
    }
}
