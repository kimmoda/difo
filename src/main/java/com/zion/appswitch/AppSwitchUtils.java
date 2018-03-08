/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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
package com.zion.appswitch;

import java.util.ArrayList;
import java.util.List;

import com.zion.common.AppConfig;
import com.zion.resource.FeedResource;
import com.zion.resource.TaskResource;
import com.zion.resource.TaskTxResource;
import com.zion.resource.UserFeedResource;

public class AppSwitchUtils {

    private static final AppSwitch CURRENT_APP = AppConfig.getInstance().getEnabledApp();

    private static final List<Class<?>> FEED_APP_BLACK_LIST_RESOURCES = new ArrayList<Class<?>>() {
        private static final long serialVersionUID = 4097281681631359351L;

        {
            add(TaskResource.class);
            add(TaskTxResource.class);
        }

    };

    private static final List<Class<?>> CAMPAIGN_APP_BLACK_LIST_RESOURCES = new ArrayList<Class<?>>() {
        private static final long serialVersionUID = -5017128097164791665L;

        {
            add(FeedResource.class);
            add(UserFeedResource.class);
        }

    };

    /**
     * if this resource is blacklisted by current app return true. Otherwise, return false.
     * @param resourceClass
     * @return
     */
    public static boolean isResourceDisabled(Class<?> resourceClass) {
        switch (CURRENT_APP) {
            case CAMPAIGN:
                return CAMPAIGN_APP_BLACK_LIST_RESOURCES.contains(resourceClass);
            case FEED:
                return FEED_APP_BLACK_LIST_RESOURCES.contains(resourceClass);
            default:
                throw new UnsupportedOperationException("cannot support app switch enum: " + CURRENT_APP);
        }
    }
}
