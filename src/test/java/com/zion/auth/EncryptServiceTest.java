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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EncryptServiceTest {

    private EncryptService classUnderTest = new EncryptServiceImpl();
    
    /**
     * https://cloudinary.com/documentation/upload_images#creating_api_authentication_signatures
     * For example, if your API secret is 'abcd', your API key is '1234', the Unix time now is 1315060510 and you are posting a request to
     * upload a file from 'http://www.example.com/sample.jpg' and set its Public ID as 'sample_image':
     * 
     * Parameters to sign: timestamp: 1315060510 
     * public_id: "sample_image" Serialized sorted parameters in a single string:
     * "public_id=sample_image&timestamp=1315060510" String including the API secret that is used to create the SHA-1 signature:
     * "public_id=sample_image&timestamp=1315060510abcd" SHA-1 hexadecimal result: "b4ad47fb4e25c7bf5f92a20089f9db59bc302313"
     */

    @Test
    public void test() {
        // Given
        Map<String, String> paramsToSign = new HashMap<>();
        paramsToSign.put("public_id", "sample_image");
        paramsToSign.put("timestamp", "1315060510");
        
        //When
        String actualSignature = classUnderTest.generateFileUploadSignature(paramsToSign, "abcd");
        
        //Then
        Assert.assertEquals(actualSignature, "b4ad47fb4e25c7bf5f92a20089f9db59bc302313");
    }
}
