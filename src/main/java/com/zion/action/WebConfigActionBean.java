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
package com.zion.action;

import java.io.StringReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zion.action.base.BaseActionBean;
import com.zion.action.model.WebConfig;
import com.zion.action.util.WebConfigBuilder;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding(WebConfigActionBean.URL)
public class WebConfigActionBean extends BaseActionBean {
    
    public static final String URL = "/action/webconfig";

    private WebConfigBuilder webConfigBuilder = new WebConfigBuilder();
    
    @DefaultHandler
    public Resolution webGlobalConfig() {
        
        WebConfig webConfig = webConfigBuilder.build(this.getRequest());
        
        ObjectWriter ow = new ObjectMapper().writer();
        String config = "{}";
        try {
            config = ow.writeValueAsString(webConfig);
        } catch (JsonProcessingException e) {
            // log error
        }
        return new StreamingResolution("application/json", new StringReader(config));
    }
}
