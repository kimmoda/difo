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
package com.zion.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.*;

public class CountryConfig {

    private static final String CONFIG_FILE = "country.properties";

    private static List<CodeSet> countryList;

    private static final Configuration config;

    static {
        try {
            config = new PropertiesConfiguration(CONFIG_FILE);
            Iterator<String> it = config.getKeys();
            List<CodeSet> countries = new ArrayList<>();
            while (it.hasNext()) {
                String countryCode = it.next();
                CodeSet country = new CodeSet(countryCode, config.getString(countryCode));
                countries.add(country);
            }
            countryList = countries;
        } catch (ConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<CodeSet> getCountries() {
        return countryList;
    }

    public static String getCountry(String code) {
        return config.getString(code);
    }
}

