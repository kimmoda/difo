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
package com.zion.validation.utils;

import org.apache.commons.lang3.StringUtils;

import com.zion.user.Contact;
import com.zion.user.Person;
import com.zion.user.User;

public class UserValidationUtils {
    private static final int ATTR_MAX_LEN = 256;
    private static final int CONTENT_MAX_LEN = 1000;
    private static final int CONTENT_MIN_LEN = 5;

    public static boolean isValidUser(User user) {
        if (user == null ) {
            return false;
        }
        Person person = user.getPerson();
        if (person != null) {
            if(!isValidMaxLength(person.getTitle(), ATTR_MAX_LEN)) {
               return false; 
            }
            if (!isValidMaxLength(person.getFirstName(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(person.getLastName(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(person.getPreferName(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(person.getIntroduce(), CONTENT_MAX_LEN)) {
                return false;
            }
            if (!isValidMinLength(person.getIntroduce(), CONTENT_MIN_LEN)) {
                return false;
            }
        }
        
        Contact contact = user.getContact();
        if (contact != null) {
            if (!isValidMaxLength(contact.getEmail(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getAddress1(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getAddress2(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getCity(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getPhone1(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getPhone2(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getMobile(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getState(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getSuburb(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getPlaceId(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getPostcode(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getBlog(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getFacebook(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getInstagram(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getWebsite(), ATTR_MAX_LEN)) {
                return false;
            }
            if (!isValidMaxLength(contact.getYoutube(), ATTR_MAX_LEN)) {
                return false;
            }
        }
        
        return true;
    }
    
    private static boolean isValidMaxLength(String src, int max) {
        if(StringUtils.isBlank(src)) {
            return true;
        }else {
            return src.trim().length() < max;
        }
    }
    
    private static boolean isValidMinLength(String src, int min) {
        if(StringUtils.isBlank(src)) {
            return true;
        }else {
            return src.trim().length() > min;
        }
    }
}

