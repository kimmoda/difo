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
package com.zion.feed;

public class FeedCount {
    private long draftCount;
    private long finalCount;

    public FeedCount() {
    }

    public FeedCount(long draftCount, long finalCount) {
        this.draftCount = draftCount;
        this.finalCount = finalCount;
    }

    public long getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(int draftCount) {
        this.draftCount = draftCount;
    }

    public long getFinalCount() {
        return finalCount;
    }

    public void setFinalCount(int finalCount) {
        this.finalCount = finalCount;
    }

    public long getTotalCount() {
        return this.finalCount + this.draftCount;
    }

}
