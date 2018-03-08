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
package com.zion.mail.service;

import java.util.Locale;

import javax.inject.Inject;

import com.zion.common.AppConfig;
import org.apache.commons.lang3.StringUtils;

import com.google.inject.Provider;
import com.zion.mail.EmailContentLoadException;
import com.zion.mail.RejectTrendsetterEmailContent;

import freemarker.template.Configuration;

public class RejectTrendsetterEmailContentFactory extends EmailContentFactory<RejectTrendsetterEmailContent> {

    private static final String REQUEST_INFO_EMAIL_TEMPLATE = "reject";
    private static final String FULL_REJECT_EMAIL_TEMPLATE = "fullreject";

    private static final String EMAIL_TITLE = "Your ."+ AppConfig.getInstance().getEnabledApp().getBrandName()+" application";

    private final Provider<Configuration> cfgProvider;
    
    private String emailTemplate;

    @Inject
    public RejectTrendsetterEmailContentFactory(Provider<Configuration> cfgProvider) {
        this.cfgProvider = cfgProvider;
    }

    @Override
    public Configuration getConfiguration() {
        cfgProvider.get().setLocale(Locale.US);
        return cfgProvider.get();
    }

    @Override
    public String getEmailTemplateName() {
        return  StringUtils.isNotBlank(this.emailTemplate) ? this.emailTemplate : FULL_REJECT_EMAIL_TEMPLATE;
    }
    
    public void setEmailTemplateName(String templateName) {
        this.emailTemplate = templateName;
    }

    public RejectTrendsetterEmailContent createModel(String receiverName, String faqUrl, String requestedInfo) {
        if(StringUtils.isNotBlank(requestedInfo)) {
            this.emailTemplate = REQUEST_INFO_EMAIL_TEMPLATE;
        }else {
            this.emailTemplate = FULL_REJECT_EMAIL_TEMPLATE;
        }
        return new RejectTrendsetterEmailContent(EMAIL_TITLE, receiverName, faqUrl, StringUtils.trimToNull(requestedInfo));
    }

    @Override
    public void processContent(RejectTrendsetterEmailContent model) throws EmailContentLoadException {
        super.processContent(model);
    }

}
