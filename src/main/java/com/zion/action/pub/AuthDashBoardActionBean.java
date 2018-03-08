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
package com.zion.action.pub;

import com.zion.action.base.PubBaseActionBean;
import com.zion.appswitch.AppSwitch;
import com.zion.common.AppConfig;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding(AuthDashBoardActionBean.URL)
public class AuthDashBoardActionBean extends PubBaseActionBean {

    public static final String ACTION = "action/auth/dashboard";
    public static final String URL = "/" + ACTION;

    @Override
    public Resolution view() {
        AppSwitch appSwitch = AppConfig.getInstance().getEnabledApp();
        switch (appSwitch) {
            case CAMPAIGN: {
                return new ForwardResolution(getPubView("authCampaign"));
            }
            case FEED: {
                return new ForwardResolution(getPubView("authDashBoard"));
            }
            default: {
                return new ForwardResolution(getPubView("error"));
            }
        }
    }

    @Override
    public String getAction() {
        return ACTION;
    }
}