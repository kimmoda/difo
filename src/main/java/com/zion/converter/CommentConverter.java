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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.zion.comment.CommentDto;
import com.zion.morphia.entity.CommentEntity;
import com.zion.user.Author;

public class CommentConverter {

    public static CommentEntity convertTo(CommentDto src) {
        CommentEntity dest = new CommentEntity();
        dest.setComment(src.getComment());
        dest.setCommentDest(src.getCommentDestination());
        dest.setDestId(src.getDestId());
        dest.setUserId(src.getUserId());
        if(StringUtils.isNotBlank(src.getId())) {
            dest.setEntityId(new ObjectId(src.getId()));
        }
        return dest;
    }
    
    public static CommentDto convertTo(CommentEntity src) {
        CommentDto dest = new CommentDto();
        dest.setComment(src.getComment());
        dest.setCommentDestination(src.getCommentDest());
        dest.setDestId(src.getDestId());
        dest.setUserId(src.getUserId());
        dest.setId(src.getId());
        Author author = new Author();
        author.setUserId(src.getUserId());
        dest.setAuthor(author);
        return dest;
    }
    
    public static CommentEntity update(CommentEntity src, CommentEntity dest) {
        dest.setComment(src.getComment());
        return dest;
    }
}

