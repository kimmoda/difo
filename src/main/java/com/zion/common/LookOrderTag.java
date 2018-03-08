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

public enum LookOrderTag {

    TOP("top", "TOP"),
    GOOD("good", "Good"),
    FAIR("fair", "Fair"),
    POOR("poor", "Poor");

    private String displayName;
    private String key;

    private LookOrderTag(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public String getKey() {
        return key;
    }
}

