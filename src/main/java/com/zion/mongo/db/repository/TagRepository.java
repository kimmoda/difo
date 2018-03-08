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

import java.util.List;
import java.util.Set;

import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.morphia.entity.TagEntity;
import com.zion.tag.Tag;

public interface TagRepository {
    
    TagEntity createTag(TagEntity entity);
    
    TagEntity findTag(String code, TagCodeSystem codeSystem, TagSourceType source);
    
    TagEntity getDefaultFeedSysTag();
    
    List<TagEntity> searchTags(String label, String code, TagCodeSystem codeSystem, TagSourceType source, Integer resultSize);

    void createUserDefinedTag(Set<Tag> filteredTag);

    Set<Tag> filterDirtyUserDefinedTag(Set<Tag> tags);

}

