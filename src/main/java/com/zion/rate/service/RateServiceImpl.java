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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.comment.OverLimitedLengthException;
import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.common.RateDestinationType;
import com.zion.converter.RateConverter;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.RateRepository;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.morphia.entity.RateEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.rate.RateAlreadyExistException;
import com.zion.rate.RateDto;
import com.zion.user.service.UserService;

public class RateServiceImpl implements RateService {
    private UserService userService;
    private RateRepository rateRepo;
    private UserRepository userRepo;
    private static int MAX_LENGTH = 1000;

    @Inject
    public RateServiceImpl(UserService userService, RateRepository rateRepo, UserRepository userRepo) {
        this.userService = userService;
        this.rateRepo = rateRepo;
        this.userRepo = userRepo;
    }

    @Override
    public RateDto saveOrUpdate(RateDto dto) throws OverLimitedLengthException, RateAlreadyExistException {
        if (StringUtils.isNotBlank(dto.getComment()) && dto.getComment().length() > MAX_LENGTH) {
            throw new OverLimitedLengthException("Cannot save or update comment as its length is over limited.");
        }
        if (dto.getCurrentRate() == 0) {
            throw new IllegalArgumentException("Cannot rate with 0 score");
        }
        RateEntity entity = RateConverter.convertTo(dto);
        RateEntity saved = this.rateRepo.saveOrUpdate(entity);
        int previous = saved.getPreviousRate();
        int current = saved.getCurrentRate();
        int delta = current - previous;
        String rateDest = saved.getRateDestination();
        int count = StringUtils.isNotBlank(dto.getId()) ? 0 : 1;
        updateRate(saved.getDestId(), rateDest, delta, count);
        RateDto rateDto = RateConverter.convertTo(saved);
        this.userService.dynaUpdateAuthor(Arrays.asList(rateDto));
        return rateDto;
    }

    @Override
    public DtoListWrapper<RateDto> listRates(QueryCriteria<RateEntity> criteria) {
        EntityListWrapper<RateEntity> entityWrapper = rateRepo.listRates(criteria);
        DtoListWrapper<RateDto> dtoWrapper = new DtoListWrapper<>();
        List<RateDto> dtos = dtoWrapper.getDtos();
        for (RateEntity entity : entityWrapper.getEntities()) {
            dtos.add(RateConverter.convertTo(entity));
        }
        if (!dtos.isEmpty()) {
            this.userService.dynaUpdateAuthor(dtos);
        }
        dtoWrapper.setNextPageToken(entityWrapper.getNextPageToken());
        return dtoWrapper;
    }

    @Override
    public RateDto get(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Cannot get rate as rate id is blank");
        }
        RateEntity saved = this.rateRepo.get(id);
        RateDto rateDto = RateConverter.convertTo(saved);
        this.userService.dynaUpdateAuthor(Arrays.asList(rateDto));
        return rateDto;
    }

    @Override
    public RateDto delete(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Cannot delete the rate as rate id is blank");
        }
        RateEntity saved = this.rateRepo.delete(id);
        int delta = 0 - saved.getCurrentRate();
        this.updateRate(saved.getDestId(), saved.getRateDestination(), delta, -1);
        RateDto rateDto = RateConverter.convertTo(saved);
        this.userService.dynaUpdateAuthor(Arrays.asList(rateDto));
        return rateDto;
    }

    private void updateRate(String destId, String rateDest, int delta, int count) {
        try {
            RateDestinationType rateType = RateDestinationType.valueOf(rateDest);
            switch (rateType) {
                case FEED:
                    throw new UnsupportedOperationException("Cannot support updating the Feed rate entity");
                case USER:
                    UserEntity ratedUser = this.userRepo.getUser(destId);
                    long rateCount = ratedUser.getRateCount() + count;
                    long totalRate = ratedUser.getTotalRate() + delta;
                    ratedUser.setRateCount(rateCount);
                    ratedUser.setTotalRate(totalRate);
                    this.userRepo.updateUser(ratedUser);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("Cannot support updating the '%s' rate entity", rateType));
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException(String.format("Cannot support rate the '%s' rate entity", rateDest));
        }
    }

    @Override
    public RateDto get(String userId, String destId) {
        RateEntity entity = this.rateRepo.get(userId, destId);
        return (entity != null) ? RateConverter.convertTo(entity) : null;
    }

}
