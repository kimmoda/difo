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
package com.zion.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zion.common.Price;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserService {
    private String serviceName;
    private String describe;
    private Price price;
    private Double duration;
    private String durationDisplayValue;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getDurationDisplayValue() {
		return durationDisplayValue;
	}

	public void setDurationDisplayValue(String durationDisplayValue) {
		this.durationDisplayValue = durationDisplayValue;
	}

	public UserService(){}

    public UserService(String serviceName, String describe, Price price, Double duration) {
        this.serviceName = serviceName;
        this.describe = describe;
        this.price = price;
        this.duration = duration;
    }
}
