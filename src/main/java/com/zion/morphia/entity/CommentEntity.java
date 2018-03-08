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
package com.zion.morphia.entity;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "zion_comment", noClassnameStored = true)
public class CommentEntity extends BaseEntity {
    private static final long serialVersionUID = -7586201338005402370L;
    private String userId;
    private String destId;
    private String comment;
    private String commentDest;

    public CommentEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getCommentDest() {
        return commentDest;
    }

    public void setCommentDest(String commentDest) {
        this.commentDest = commentDest;
    }

    @Override
    public String toString() {
        return String.format("Comment: ['id': %s, 'userId': %s, 'destId': %s]", this.getId(), this.getUserId(), this.getDestId());
    }

}
