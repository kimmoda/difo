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
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "zion_tag", noClassnameStored = true)
@Indexes({
        @Index(fields = @Field("label"), options = @IndexOptions(name = "idx_taglabel")),
        @Index(fields = @Field("code"), options = @IndexOptions(name = "idx_tagcode")),
        @Index(fields = @Field("codeSystem"), options = @IndexOptions(name = "idx_tagcodesystem")),
        @Index(fields = @Field("source"), options = @IndexOptions(name = "idx_tagsource"))
})
public class TagEntity extends BaseEntity {

    private static final long serialVersionUID = -372288812735292331L;

    private String label;

    private String code;

    // e.g. category
    private String codeSystem;

    // e.g. where we defined the tag.
    private String source;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return this.getId();
    }
}
