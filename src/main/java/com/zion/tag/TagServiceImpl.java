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
package com.zion.tag;

import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.converter.TagConverter;
import com.zion.mongo.db.repository.TagRepository;
import com.zion.morphia.entity.TagEntity;

public class TagServiceImpl implements TagService {
    
    private TagRepository tagRepository;
    
    @Inject
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    
    @Override
    public List<Tag> searchTag(String label, String code, TagCodeSystem codeSystem, TagSourceType source, Integer resultSize) {
        List<TagEntity> searchTags = this.tagRepository.searchTags(label, code, codeSystem, source, resultSize);
        searchTags.sort(new Comparator<TagEntity>() {
            @Override
            public int compare(TagEntity o1, TagEntity o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
        });
        return TagConverter.convertTo(searchTags);
    }

}

