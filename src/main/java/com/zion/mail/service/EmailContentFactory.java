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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.zion.appswitch.AppSwitch;
import com.zion.common.AppConfig;
import com.zion.mail.EmailContentLoadException;
import com.zion.mail.EmailContentModel;
import com.zion.mail.EmailContentType;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public abstract class EmailContentFactory<T extends EmailContentModel> implements EmailContentService<T> {

    private static final String EMAIL_CONTENT_MODEL = "data";

    private Map<EmailContentType, String> emailContents = new HashMap<>();

    public Map<EmailContentType, String> getEmailContents() {
        return emailContents;
    }

    public void processContent(T contentModel) throws EmailContentLoadException {
        emailContents.put(EmailContentType.SUBJECT, this.processContentByEmailContentType(contentModel, EmailContentType.SUBJECT));
        emailContents.put(EmailContentType.HTML, processContentByEmailContentType(contentModel, EmailContentType.HTML));
    }

    private String processContentByEmailContentType(T contentModel, EmailContentType type) throws EmailContentLoadException {
        try {
            Template template = this.loadTemplate(type);
            return this.processContent(template, contentModel);
        } catch (TemplateException | IOException e) {
            throw new EmailContentLoadException("Failed load email html content [" + getEmailTemplateName() + "]");
        }
    }

    private Template loadTemplate(EmailContentType type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        AppSwitch appSwitch = AppConfig.getInstance().getEnabledApp();
        String folderName = "";
        switch (appSwitch) {
            case FEED: {
                folderName = "feed";
                break;
            }
            case CAMPAIGN: {
                folderName = "campaign";
                break;
            }
        }
        return getConfiguration().getTemplate(folderName + "/" + getEmailTemplateName() + "/" + type.name().toLowerCase() + ".ftl");
    }

    private String processContent(Template template, T contentModel) throws TemplateException, IOException {
        Map<String, T> modelMaps = new HashMap<>();
        modelMaps.put(EMAIL_CONTENT_MODEL, contentModel);
        Writer stringWriter = new StringWriter();
        template.process(modelMaps, stringWriter);
        return stringWriter.toString();
    }
}

