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
package com.zion.rate;

import com.zion.common.AuthorUpdatable;
import com.zion.user.Author;

public class RateDto implements AuthorUpdatable{
    private String id;
    private String userId;
    private String destId;
    private String comment;
    private int previousRate;
    private int currentRate;
    private String rateDestination;
    private Author author;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDestId() {
        return destId;
    }
    public void setDestId(String destId) {
        this.destId = destId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getPreviousRate() {
        return previousRate;
    }
    public void setPreviousRate(int previousRate) {
        this.previousRate = previousRate;
    }
    public int getCurrentRate() {
        return currentRate;
    }
    public void setCurrentRate(int currentRate) {
        this.currentRate = currentRate;
    }
    public String getRateDestination() {
        return rateDestination;
    }
    public void setRateDestination(String rateDestination) {
        this.rateDestination = rateDestination;
    }
    
    @Override
    public Author getAuthor() {
        return author;
    }
    @Override
    public void setAuthor(Author author) {
        this.author = author;
    }
}

