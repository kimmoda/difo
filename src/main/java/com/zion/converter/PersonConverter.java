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

import com.zion.common.CodeSet;
import com.zion.common.Gender;
import com.zion.morphia.entity.embed.PersonEntity;
import com.zion.user.Person;
import org.apache.commons.lang3.StringUtils;

public class PersonConverter {
    private static final String DEFAULT_AVATAR_URL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/avatar.png";


    public Person convertToModel(PersonEntity personEntity) {
        if (personEntity != null) {
            CodeSet codeSet = Gender.getCodeSet(personEntity.getGender());
            String avatarUrl = StringUtils.isNotBlank(personEntity.getAvatar()) ? personEntity.getAvatar() : DEFAULT_AVATAR_URL;
            return new Person(personEntity.getTitle(), personEntity.getIntroduce(), personEntity.getPreferName(),personEntity.getFirstName(), personEntity.getMiddleName(),
                    personEntity.getLastName(), codeSet, avatarUrl, personEntity.getDob(),
                    personEntity.getExperience(), personEntity.getCertification(), personEntity.getAward(),
                    personEntity.getPartnership(), personEntity.getClient(), personEntity.getStory());
        }
        return new Person();
    }

    public PersonEntity convertToEntity(Person person) {
        if (person != null) {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setPreferName(StringUtils.trimToNull(person.getPreferName()));
            personEntity.setFirstName(StringUtils.trimToNull(person.getFirstName()));
            personEntity.setMiddleName(StringUtils.trimToNull(person.getMiddleName()));
            personEntity.setLastName(StringUtils.trimToNull(person.getLastName()));
            personEntity.setTitle(StringUtils.trimToNull(person.getTitle()));
            personEntity.setIntroduce(StringUtils.trimToNull(person.getIntroduce()));
            personEntity.setExperience(person.getExperience());
            personEntity.setCertification(person.getCertification());
            personEntity.setAward(person.getAward());
            personEntity.setPartnership(person.getPartnership());
            personEntity.setClient(person.getClient());
            personEntity.setStory(StringUtils.trimToNull(person.getStory()));
            if (person.getGender() != null && StringUtils.isNotBlank(person.getGender().getCode())) {
                personEntity.setGender(Gender.sanitize(person.getGender()).name());
            }
            personEntity.setDob(person.getDob());
            if (StringUtils.isNotBlank(person.getAvatar())) {
                personEntity.setAvatar(person.getAvatar());
            }
            return personEntity;
        }
        return null;
    }
}

