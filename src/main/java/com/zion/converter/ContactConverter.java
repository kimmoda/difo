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
import com.zion.common.UrlUtils;
import com.zion.morphia.entity.embed.ContactEntity;
import com.zion.user.Contact;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ContactConverter {
    private static Map<String, Locale> localeMap;

    static {
        String[] countries = Locale.getISOCountries();
        localeMap = new HashMap<>(countries.length);
        for (String country : countries) {
            Locale locale = new Locale("", country);
            localeMap.put(locale.getISO3Country().toUpperCase(), locale);
        }
    }
    
    public CodeSet converterToContryCodeSet(String countryCode) {
        CodeSet codeSet = null;
        if (StringUtils.isNotBlank(countryCode)) {

            String value = localeMap.get(countryCode).getCountry();
            if (StringUtils.isNotBlank(value)) {
                codeSet = new CodeSet(countryCode, localeMap.get(countryCode).getDisplayName());
            }
        }
        return codeSet;
    }

    public Contact convertToModel(ContactEntity contactEntity) {
        if (contactEntity == null) {
            return new Contact();
        }
        String countryCode = contactEntity.getCountry();
        CodeSet codeSet = this.converterToContryCodeSet(countryCode);
        return new Contact(contactEntity.getEmail(), contactEntity.getAddress1(), contactEntity.getAddress2(),
                contactEntity.getSuburb(), contactEntity.getCity(), codeSet, contactEntity.getState(),
                contactEntity.getPostcode(), contactEntity.getPlaceId(), contactEntity.getPhone1(), contactEntity.getPhone2(),
                contactEntity.getMobile(), contactEntity.getWebsite(), contactEntity.getFacebook(), contactEntity.getInstagram(),
                contactEntity.getYoutube(), contactEntity.getBlog());
    }

    public ContactEntity convertToEntity(Contact contact) {

        if (contact != null) {

            ContactEntity entity = new ContactEntity();
            entity.setEmail(StringUtils.trimToNull(contact.getEmail()));
            entity.setAddress1(StringUtils.trimToNull(contact.getAddress1()));
            entity.setAddress2(StringUtils.trimToNull(contact.getAddress2()));
            entity.setCity(StringUtils.trimToNull(contact.getCity()));
            if (contact.getCountry() != null && StringUtils.isNotBlank(contact.getCountry().getCode())) {
                if (contact.getCountry().getCode().length() == 2) {
                    Locale locale = new Locale("", contact.getCountry().getCode());
                    entity.setCountry(locale.getISO3Country());
                } else {
                    entity.setCountry(contact.getCountry().getCode());
                }
            }
            entity.setState(StringUtils.trimToNull(contact.getState()));
            entity.setMobile(StringUtils.trimToNull(contact.getMobile()));
            entity.setPhone1(StringUtils.trimToNull(contact.getPhone1()));
            entity.setPhone2(StringUtils.trimToNull(contact.getPhone2()));
            entity.setPostcode(StringUtils.trimToNull(contact.getPostcode()));
            entity.setSuburb(StringUtils.trimToNull(contact.getSuburb()));
            entity.setWebsite(UrlUtils.normalizeUrl(contact.getWebsite()));
            entity.setFacebook(UrlUtils.normalizeUrl(contact.getFacebook()));
            entity.setInstagram(UrlUtils.normalizeUrl(contact.getInstagram()));
            entity.setYoutube(UrlUtils.normalizeUrl(contact.getYoutube()));
            entity.setPlaceId(StringUtils.trimToNull(contact.getPlaceId()));
            entity.setBlog(UrlUtils.normalizeUrl(contact.getBlog()));
            return entity;
        }

        return null;
    }
}

