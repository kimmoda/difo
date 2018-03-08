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
package com.zion.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class QueryUtils {
    public static final String LIST_PARAM_SPERATOR = ",";

    public static List<String> parseQueryList(String queryList) {
        List<String> tagList = new ArrayList<>();
        if(StringUtils.isBlank(queryList)) {
            return tagList;
        }else {
            String[] rowTags = StringUtils.split(queryList.trim(), LIST_PARAM_SPERATOR);
            for(String tag: rowTags) {
                tagList.add(tag.trim());
            }
            return tagList;
        }
    }
}

