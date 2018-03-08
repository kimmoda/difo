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
package com.zion.rate.service;

import com.zion.comment.OverLimitedLengthException;
import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.RateEntity;
import com.zion.rate.RateAlreadyExistException;
import com.zion.rate.RateDto;

public interface RateService {

    RateDto saveOrUpdate(RateDto dto) throws OverLimitedLengthException, RateAlreadyExistException;

    DtoListWrapper<RateDto> listRates(QueryCriteria<RateEntity> criteria);

    RateDto get(String id);
    
    RateDto get(String userId, String destId);

    RateDto delete(String id);
}
