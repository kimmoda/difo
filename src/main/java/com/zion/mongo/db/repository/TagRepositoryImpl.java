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
package com.zion.mongo.db.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import com.zion.common.LookOrderTag;
import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.converter.TagConverter;
import com.zion.morphia.entity.TagEntity;
import com.zion.tag.Tag;

public class TagRepositoryImpl extends AbstractRepository implements TagRepository {
    private static final int MAX_USER_DEFINED_TAG = 20;
    private static final int MAX_USER_DEFINED_TAG_LENGTH = 35;
    
    @Override
    public TagEntity createTag(TagEntity entity) {
        Validate.notNull(entity);
        Validate.notEmpty(entity.getCode());
        Validate.notEmpty(entity.getCodeSystem());
        Validate.notEmpty(entity.getSource());
        
        TagCodeSystem codeSystem = TagCodeSystem.findTagCodeSystemByCode(entity.getCodeSystem());
        Validate.notNull(codeSystem);
        TagSourceType source = TagSourceType.findTagSourceTypeByCode(entity.getSource());
        Validate.notNull(source);
        
        TagEntity eixstingTag = findTag(entity.getCode(), codeSystem, source);
        if (eixstingTag == null) {
            entity.setCode(convertTagCode(entity.getCode()));
            
            if (StringUtils.isBlank(entity.getLabel())) {
                entity.setLabel(entity.getCode());
            }
            
            entity.setEnabled(true);
            return super.persist(entity);
        }
        return eixstingTag;
    }
    
    private String convertTagCode(final String code) {
        String convertedCode = code;
        return StringUtils.deleteWhitespace(convertedCode).toLowerCase();
    }
    
    @Override
    public TagEntity findTag(String code, TagCodeSystem codeSystem, TagSourceType source) {
        Query<TagEntity> query = this.getMongoDatastore().find(TagEntity.class);
        query.field("code").equal(code.toLowerCase());
        query.field("codeSystem").equal(codeSystem.getCode());
        query.field("source").equal(source.getCode());
        return query.get();
    }

    @Override
    public List<TagEntity> searchTags(String label, String code, TagCodeSystem codeSystem, TagSourceType source, Integer resultSize) {
        
        Validate.notNull(source);
        
        Query<TagEntity> query = this.getMongoDatastore().find(TagEntity.class);
        
        if (StringUtils.isNotBlank(code)) {
            query.field("code").contains(code.toLowerCase());   
        }
        if (StringUtils.isNotBlank(label)) {
            query.field("label").containsIgnoreCase(label);   
        }
        if (codeSystem != null) {
            query.field("codeSystem").equal(codeSystem.getCode());
        }
        query.field("source").equal(source.getCode());
        query.field("enabled").equal(true);
        return resultSize != null ? query.asList(new FindOptions().limit(resultSize.intValue())): query.asList();
    }
    
    @Override
    public void createUserDefinedTag(Set<Tag> filteredTag) {
        if (filteredTag == null || filteredTag.isEmpty()) {
            return;
        }else {
            for(Tag userDefinedTag: filteredTag) {
                this.createTag(TagConverter.convertTo(userDefinedTag));
            }
        }
        
    }

    @Override
    public Set<Tag> filterDirtyUserDefinedTag(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return tags;
        } else {
            Set<Tag> userDefinedTags = new HashSet<>();
            //We need to limit the number of user input tags.
            Set<Tag> validSizeSet = tags.stream().filter(tag -> {
                if (userDefinedTags.size() == MAX_USER_DEFINED_TAG 
                        && (StringUtils.isBlank(tag.getCode())
                                || tag.getCode().trim().length() > MAX_USER_DEFINED_TAG_LENGTH
                        )&& tag.getSource().equals(TagSourceType.STYLEHUB_USERINPUT.getCode())) {
                    return false;
                } else {
                    userDefinedTags.add(tag);
                    return true;
                }
            }).collect(Collectors.toSet());

            //Remove the duplicated tag which is same as the predefined tag
            return validSizeSet.stream().filter(tag -> isUniqueUserTag(validSizeSet, tag)).collect(Collectors.toSet());
        }
    }

    private boolean isUniqueUserTag(Set<Tag> tags, Tag userTag) {
        for (Tag tag : tags) {
            if (tag.getSource().equals(TagSourceType.STYLEHUB_PREDEFINED.getCode())
                    && userTag.getCode().equals(tag.getCode())
                    && userTag.getCodeSystem().equals(tag.getCodeSystem())
                    && userTag.getSource().equals(TagSourceType.STYLEHUB_USERINPUT.getCode())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public TagEntity getDefaultFeedSysTag() {
        return this.findTag(LookOrderTag.FAIR.name(), TagCodeSystem.LOOK_ORDER, TagSourceType.STYLEHUB_SYSTEM);
    }

}

