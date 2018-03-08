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
package com.zion.converter;

import com.zion.follower.Follower;
import com.zion.morphia.entity.FollowerEntity;

public class FollowerConverter {

    private static UserConverter USER_CONVERTER = new UserConverter();
    
    public static Follower convertTo(FollowerEntity src) {
        if(src == null) {
            return null;
        }
        Follower dest = new Follower();
        dest.setFanId(src.getFanId());
        dest.setId(src.getId());
        dest.setFollowedUser(USER_CONVERTER.convertToModel(src.getFollowedUser()));
        dest.setFollowed(src.isFollowed());
        return dest;
    }
}

