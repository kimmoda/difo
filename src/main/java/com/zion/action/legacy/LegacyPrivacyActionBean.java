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
package com.zion.action.legacy;

import com.zion.action.base.BaseActionBean;
import com.zion.action.pub.PrivacyActionBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import static com.zion.action.legacy.LegacyPrivacyActionBean.URL;

@UrlBinding(URL)
public class LegacyPrivacyActionBean extends BaseActionBean {
    public static final String URL = "/action/privacy";

    public Resolution view() {
        return new ForwardResolution(PrivacyActionBean.class);
    }
}
