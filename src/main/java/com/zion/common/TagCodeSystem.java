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

public enum TagCodeSystem {
    
    PREFERRED_STYLE("pstyle", "Styles", 0),
    PRODUCT_CATEGORY("prodcategory", "Product Categories", 1),
    LOOK_ORDER("lookorder", "Looks Order", 2),
    TRENDSETTER_ORDER("trendsetterorder", "Trendsetter Order", 3),
    LOOK_RECOMMEND("lookrecommend", "Look Recommend", 4);
    
    private String code;
    private String displayName;
    private int order;

    private TagCodeSystem(String code, String displayName, int order) {
        this.code = code;
        this.displayName = displayName;
        this.order = order;
    }

    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getOrder() {
        return order;
    }
    
    public static TagCodeSystem findTagCodeSystemByCode(final String codeInput) {
        for (TagCodeSystem tagCodeSystem : TagCodeSystem.values()) {
            if (tagCodeSystem.getCode().equalsIgnoreCase(codeInput)) {
                return tagCodeSystem;
            }
        }
        return null;
    }
    
}

