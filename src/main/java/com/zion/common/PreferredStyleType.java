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

public enum PreferredStyleType {
    ARTISTIC("artistic", "Artistic"),
    Bohemian("bohemian", "Bohemian"),
    BOHO("boho", "Boho"),
    CASUAL("casual","Casual"),
    CHIC("chic", "Chic"),
    CLASSIC("classic","Classic"),
    DAPPER("dapper", "Dapper"),
    EDGY("edgy", "Edgy"),
    ELEGANT("elegant", "Elegant"),
    GLAMOROUS("glamorous", "Glamorous"),
    FORMAL("formal", "Formal"),
    GOTHIC("gothic", "Gothic"),
    GRUNGE("grunge", "Grunge"),
    MINIMAL("minimal", "Minimal"),
    PREPPY("preppy", "Preppy"),
    PUNK("punk", "Punk"),
    RETRO("retro", "Retro"),
    ROMANTIC("romantic", "Romantic"),
    SPORTY("sporty", "Sporty"),
    STREET("street", "Street"),
    TOMBOY("tomboy", "Tomboy"),
    TRENDY("trendy", "Trendy"),
    VINTAGE("vintage", "Vintage");

    private String displayName;
    private String key;

    private PreferredStyleType(String key, String displayName) {
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
