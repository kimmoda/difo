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
package com.zion.mail;

public class SesConfiguration {
    private String fromAdmin;
    private String from;
    private String support;
    private String port;
    private String server;
    private String username;
    private String password;
    
    public SesConfiguration(String fromAdmin, String from, String support, String port, String server,
            String username, String password) {
        this.fromAdmin = fromAdmin;
        this.from = from;
        this.support = support;
        this.port = port;
        this.server = server;
        this.username = username;
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getPort() {
        return port;
    }

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFromAdmin() {
        return fromAdmin;
    }

    public void setFromAdmin(String fromAdmin) {
        this.fromAdmin = fromAdmin;
    }
}
