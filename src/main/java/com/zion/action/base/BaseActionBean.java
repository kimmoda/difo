/*******************************************************************************
 *  Forward Thinking CONFIDENTIAL
 *   __________________
 *
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of Forward Thinking Ltd and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to Forward Thinking Ltd
 *  and its suppliers and may be covered by New Zealand and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Forward Thinking Ltd.
 */
package com.zion.action.base;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.zion.action.model.ShareMeta;
import com.zion.action.model.WebConfig;
import com.zion.action.util.WebConfigBuilder;
import com.zion.common.AppConfig;

public abstract class BaseActionBean implements ActionBean {

    private static final String VIEW_TEMPLATE = "/WEB-INF/jsp/%s.jsp";

    private WebConfigBuilder webConfigBuilder = new WebConfigBuilder();
    private AppConfig appConfig = AppConfig.getInstance();

    private ActionBeanContext context;

    private ShareMeta shareMeta;

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return this.context;
    }

    protected HttpServletRequest getRequest() {
        return this.context.getRequest();
    }

    protected String getAdminView(Class<? extends BaseActionBean> clazz) {
        return getView(clazz, "admin", null);
    }

    protected String getPubView(Class<? extends BaseActionBean> clazz) {
        return getView(clazz, "pub", null);
    }

    protected String getAdminView(String jspName) {
        return getView(jspName, "admin", null);
    }

    protected String getPubView(String jspName) {
        return getView(jspName, "pub", null);
    }

    private String getView(Class<? extends BaseActionBean> clazz, String dir, String param) {
        if (StringUtils.isNotBlank(param)) {
            return String.format(VIEW_TEMPLATE, dir + "/" + templeteName(clazz.getSimpleName())) + param;
        } else {
            return String.format(VIEW_TEMPLATE, dir + "/" + templeteName(clazz.getSimpleName()));
        }
    }

    private String getView(String jspName, String dir, String param) {
        if (StringUtils.isNotBlank(param)) {
            return String.format(VIEW_TEMPLATE, dir + "/" + jspName) + param;
        } else {
            return String.format(VIEW_TEMPLATE, dir + "/" + jspName);
        }
    }

    private String templeteName(final String className) {
        String name = className.substring(0, className.indexOf("ActionBean"));
        return toLowerCaseFirstOne(name);
    }

    // TODO: change to WordUtils lib
    private String toLowerCaseFirstOne(final String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public WebConfig getWebConfig() {
        return webConfigBuilder.build(this.getRequest());
    }

    public ShareMeta getShareMeta() {
        return shareMeta;
    }

    public void setShareMeta(ShareMeta shareMeta) {
        this.shareMeta = shareMeta;
    }

    public String getAction() {
        return "";
    }

    public String getAppBrandName() {
        return appConfig.getEnabledApp().getBrandName();

    }

    public String getAppLogoUrl() {
        return appConfig.getEnabledApp().getLogoUrl();
    }

    public String getAppSupportEmail() {
        return appConfig.getSesConfiguration().getSupport();
    }

}
