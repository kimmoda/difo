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
package com.zion.httpclient.exception;

public class HttpClientConnectionException extends Exception {

    private static final long serialVersionUID = -8472058006457836008L;
    
    private static final String FORMAT = "%s HTTP STATUS: [%d]";
    
    public HttpClientConnectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public HttpClientConnectionException(int status, String msg) {
        super(String.format(FORMAT, status, msg));
    }

}

