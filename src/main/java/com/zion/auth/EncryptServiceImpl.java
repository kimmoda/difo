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
package com.zion.auth;

import com.zion.common.AppConfig;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class EncryptServiceImpl implements EncryptService {

    @Override
    public String encrypt(String key, String rawPassword) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("encrypt key cannot be empty.");
        }
        if (StringUtils.isEmpty(rawPassword)) {
            throw new IllegalArgumentException("raw password cannot be empty.");
        }
        String key_salt = key + AppConfig.getInstance().getAuthEncryptKey();
        return DigestUtils.sha256Hex(rawPassword + key_salt);
    }

    @Override
    public String generateFileUploadSignature(Map<String, String> paramsToSign, String apiSecret) {
        if (paramsToSign == null || paramsToSign.isEmpty()) {
            throw new IllegalArgumentException("cannot generate signatute for uploading as params to sign is null or empty");
        }
        // https://cloudinary.com/documentation/upload_images#creating_api_authentication_signatures
        /*
         * Create a string with the parameters used in the POST request to Cloudinary: 
         * All parameters should be included except the file,
         * type, and resource_typeparameters, and the api_key. 
         * 
         * Make sure to include the timestamp. 
         * 
         * Sort the parameters by their names in
         * alphabetical order. 
         * 
         * Separate the parameter names from their values with an = and join the parameter/value pairs together with an
         * &. 
         * Append your API secret to the end of the string. Create a hexadecimal message digest (hash value) of the string using the
         * SHA-1 function.
         */
        if (StringUtils.isBlank(paramsToSign.get("timestamp"))) {
            long timestamp = DateTime.now().getMillis()/1000;
            paramsToSign.put("timestamp", String.valueOf(timestamp));
        }
        Set<String> keys = paramsToSign.keySet();
        List<String> paramList = Arrays.asList(keys.toArray(new String[]{}));
        paramList.sort(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for(String param: paramList) {
            String prefix = i == 0 ? "" : "&";
            builder.append(prefix + param + "="  + paramsToSign.get(param));
            i++;
        }
        builder.append(apiSecret);
        String digestString = builder.toString();
        return DigestUtils.sha1Hex(digestString);
    }

}
