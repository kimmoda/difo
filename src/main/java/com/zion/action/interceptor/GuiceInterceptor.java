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
package com.zion.action.interceptor;

import javax.servlet.ServletContext;

import com.google.inject.Injector;
import com.zion.web.servlet.listener.GuiceServletListener;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts(LifecycleStage.ActionBeanResolution)
public class GuiceInterceptor implements Interceptor {

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        ServletContext ctx = context.getActionBeanContext().getServletContext();
        Injector injector = (Injector) ctx.getAttribute(GuiceServletListener.INJETOR_KEY);
        // Need to think about the inject constructor. currently only support inject members.
        injector.injectMembers(context.getActionBean());
        return resolution;
    }

}

