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
package com.zion.converter;

import com.zion.morphia.entity.embed.UserServiceEntity;
import com.zion.user.UserService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceConverter {

    public ArrayList<UserService> convertToModel(List<UserServiceEntity> serviceEntityList) {
        if (serviceEntityList == null) {
            return new ArrayList<>();
        }

        ArrayList<UserService> userServiceList = new ArrayList<>();
        for (UserServiceEntity entity : serviceEntityList) {
            UserService service = new UserService();
            service.setServiceName(entity.getServiceName());
            service.setDescribe(entity.getDescribe());
            if (entity.getPrice() != null && entity.getPrice().getPrice() != null) {
                service.setPrice(entity.getPrice());
            }
            Double duration = entity.getDuration();
            if(duration != null) {
            	service.setDuration(duration);
            	String displayValue = getDurationDisplayValue(duration);
            	service.setDurationDisplayValue(displayValue);
            }
            userServiceList.add(service);
        }

        return userServiceList;
    }

	private String getDurationDisplayValue(Double duration) {
		DecimalFormat df = new DecimalFormat("###.#");
		if(duration <= 0) {
			return "";
		}else if(duration < 1) {
			return df.format(duration * 60) + " minutes";
		}else if(duration == 1) {
			return df.format(duration) + " hour";
		}else {
			return df.format(duration) + " hours";
		}
	}

    public ArrayList<UserServiceEntity> convertToEntity(List<UserService> serviceList) {

        if (serviceList != null) {
            ArrayList<UserServiceEntity> userServiceEntityList = new ArrayList<>();
            for (UserService entity : serviceList) {
                UserServiceEntity serviceEntity = new UserServiceEntity();
                serviceEntity.setServiceName(entity.getServiceName());
                serviceEntity.setDescribe(entity.getDescribe());
                if (entity.getPrice() != null && entity.getPrice().getPrice() != null) {
                    serviceEntity.setPrice(entity.getPrice());
                }
                serviceEntity.setDuration(entity.getDuration());
                userServiceEntityList.add(serviceEntity);
            }
            return userServiceEntityList;
        }
        return new ArrayList<>();
    }
}

