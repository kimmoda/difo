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

import com.google.inject.Provider;
import com.zion.common.AppConfig;
import com.zion.mail.EmailContentLoadException;
import com.zion.mail.WelcomeUserEmailContent;

import freemarker.template.Configuration;

public class WelcomeUserEmailContentFactory extends EmailContentFactory<WelcomeUserEmailContent> {

    private static final String EMAIL_TEMPLATE = "welcome";

    private static final String EMAIL_TITLE = "Welcome to " + AppConfig.getInstance().getEnabledApp().getBrandName();

    private final Provider<Configuration> cfgProvider;

    @Inject
    public WelcomeUserEmailContentFactory(Provider<Configuration> cfgProvider) {
        this.cfgProvider = cfgProvider;
    }

    @Override
    public Configuration getConfiguration() {
        cfgProvider.get().setLocale(Locale.US);
        return cfgProvider.get();
    }

    @Override
    public String getEmailTemplateName() {
        return EMAIL_TEMPLATE;
    }

    public WelcomeUserEmailContent createModel(String receiverName, String campaignUrl, String applyUrl) {
        return new WelcomeUserEmailContent(EMAIL_TITLE, receiverName, campaignUrl, applyUrl);
    }

    @Override
    public void processContent(WelcomeUserEmailContent model) throws EmailContentLoadException {
        super.processContent(model);
    }
}
