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
import com.zion.mail.EmailContentLoadException;
import com.zion.mail.StylistApprovedEmailContent;

import freemarker.template.Configuration;

public class ApproveStylistEmailContentFactory extends EmailContentFactory<StylistApprovedEmailContent> {

    private static final String EMAIL_TEMPLATE = "approvestylist";
    
    private static final String EMAIL_TITLE = "Stylist Application Approved";
    
    private final Provider<Configuration> cfgProvider;
    
    @Inject
    public ApproveStylistEmailContentFactory(Provider<Configuration> cfgProvider) {
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
    
    public StylistApprovedEmailContent createModel(String receiverName, String loginUrl) {
        return new StylistApprovedEmailContent(EMAIL_TITLE, receiverName, loginUrl);
    }

    @Override
    public void processContent(StylistApprovedEmailContent model) throws EmailContentLoadException {
        super.processContent(model);
    }

}

