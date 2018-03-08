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
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding(AuthCampaignActionBean.URL)
public class AuthCampaignActionBean extends PubBaseActionBean{
    public static final String ACTION = "auth/campaign";
    public static final String URL = "/action/" + ACTION;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public String getAction() {
        return ACTION;
    }
}
