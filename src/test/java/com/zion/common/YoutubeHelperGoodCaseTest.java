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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class YoutubeHelperGoodCaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "youtube.com/v/60OoGRSydho" },
                { "youtube.com/vi/60OoGRSydho" },
                { "youtube.com/?v=60OoGRSydho" },
                { "youtube.com/?vi=60OoGRSydho" },
                { "youtube.com/watch?v=60OoGRSydho" },
                { "youtube.com/watch?vi=60OoGRSydho" },
                { "youtu.be/60OoGRSydho" },
                { "youtube.com/embed/60OoGRSydho" },
                { "youtube.com/embed/60OoGRSydho" },
                { "www.youtube.com/v/60OoGRSydho" },
                { "http://www.youtube.com/v/60OoGRSydho" },
                { "https://www.youtube.com/v/60OoGRSydho" },
                { "youtube.com/watch?v=60OoGRSydho&wtv=wtv" },
                { "http://www.youtube.com/watch?dev=inprogress&v=60OoGRSydho&feature=related" },
                { "https://m.youtube.com/watch?v=60OoGRSydho" }
        });
    }

    private String url;

    public YoutubeHelperGoodCaseTest(String url) {
        this.url= url;
    }


    @Test
    public void extractingVideoIdFromUrlShouldReturnVideoId() {
        assertEquals("Unable to extract correct video id from url " + url, "60OoGRSydho", YoutubeHelper.extractVideoIdFromUrl(url));
    }
    
}