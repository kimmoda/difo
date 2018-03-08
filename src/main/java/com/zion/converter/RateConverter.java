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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.zion.morphia.entity.RateEntity;
import com.zion.rate.RateDto;
import com.zion.user.Author;

public class RateConverter {

    public static RateEntity convertTo(RateDto src) {
        RateEntity dest = new RateEntity();
        dest.setComment(src.getComment());
        dest.setRateDestination(src.getRateDestination());
        dest.setDestId(src.getDestId());
        dest.setUserId(src.getUserId());
        if(StringUtils.isNotBlank(src.getId())) {
            dest.setEntityId(new ObjectId(src.getId()));
        }
        dest.setPreviousRate(src.getPreviousRate());
        dest.setCurrentRate(src.getCurrentRate());
        return dest;
    }
    
    public static RateDto convertTo(RateEntity src) {
        RateDto dest = new RateDto();
        dest.setComment(src.getComment());
        dest.setRateDestination(src.getRateDestination());
        dest.setDestId(src.getDestId());
        dest.setUserId(src.getUserId());
        dest.setId(src.getId());
        Author author = new Author();
        author.setUserId(src.getUserId());
        dest.setAuthor(author);
        dest.setPreviousRate(src.getPreviousRate());
        dest.setCurrentRate(src.getCurrentRate());
        return dest;
    }
    
    public static RateEntity update(RateEntity src, RateEntity dest) {
        dest.setComment(src.getComment());
        dest.setPreviousRate(dest.getCurrentRate());
        dest.setCurrentRate(src.getCurrentRate());
        return dest;
    }
}

