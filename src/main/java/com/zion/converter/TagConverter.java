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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zion.morphia.entity.TagEntity;
import com.zion.tag.Tag;

public class TagConverter {

    public static List<Tag> convertTo(List<TagEntity> tagEntities) {
        List<Tag> tags = new ArrayList<>();
        if (tagEntities != null && !tagEntities.isEmpty()) {
            tagEntities.forEach(tagEntity -> {
                String label = StringUtils.isBlank(tagEntity.getLabel()) ? tagEntity.getCode() : tagEntity.getLabel();
                Tag tag = new Tag(label, tagEntity.getCode(), tagEntity.getCodeSystem(), tagEntity.getSource());
                tags.add(tag);
            });
        }
        return tags;
    }
    
    public static TagEntity convertTo(Tag tag) {
        TagEntity entity = new TagEntity();
        if (StringUtils.isNotBlank(tag.getName())) {
            entity.setLabel(tag.getName().trim());
        }else {
            throw new IllegalArgumentException("cannot convert to tag entity as name is blank");
        }
        
        if (StringUtils.isNotBlank(tag.getCode())) {
            entity.setCode(tag.getCode().trim());
        }else {
            throw new IllegalArgumentException("cannot convert to tag entity as code is blank");
        }
        
        if (StringUtils.isNotBlank(tag.getCodeSystem())) {
            entity.setCodeSystem(tag.getCodeSystem().trim());
        }else {
            throw new IllegalArgumentException("cannot convert to tag entity as code system is blank");
        }
        
        if (StringUtils.isNotBlank(tag.getSource())) {
            entity.setSource(tag.getSource().trim());
        }else {
            throw new IllegalArgumentException("cannot convert to tag entity as source is blank");
        }
        return entity;
    }
}

