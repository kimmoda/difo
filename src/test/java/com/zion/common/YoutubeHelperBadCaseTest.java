/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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

import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class YoutubeHelperBadCaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "youtube.com/c/vidid" },
                { "youtube.com/channel/vidid" },
                { "youtube.com/user/vidid" },
                { "stylehub.online"},
                { "http://youtube.com/c/vidid" },
                { "http://youtube.com/channel/vidid" },
                { "http://youtube.com/user/vidid" },
                { "https://youtube.com/c/vidid" },
                { "https://youtube.com/channel/vidid" },
                { "https://youtube.com/user/vidid" }
                
        });
    }

    private String url;

    public YoutubeHelperBadCaseTest(String url) {
        this.url= url;
    }


    @Test
    public void extractingVideoIdFromUrlShouldReturnVideoId() {
        assertNull("Expected video id is null from url " + url,  YoutubeHelper.extractVideoIdFromUrl(url));
    }
    
}