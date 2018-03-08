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

public class RejectTrendsetterEmailContent extends EmailContentModel {
    private String faqUrl;
    private String requestedInfo;
    
    public RejectTrendsetterEmailContent(String emailTitle, String receiverName, String faqUrl, String requestedInfo) {
        super(emailTitle, receiverName);
        this.faqUrl = faqUrl;
        this.requestedInfo = requestedInfo;
    }

    public String getFaqUrl() {
        return faqUrl;
    }

    public void setFaqUrl(String faqUrl) {
        this.faqUrl = faqUrl;
    }

    public String getRequestedInfo() {
        return requestedInfo;
    }

    public void setRequestedInfo(String requestedInfo) {
        this.requestedInfo = requestedInfo;
    }


}
