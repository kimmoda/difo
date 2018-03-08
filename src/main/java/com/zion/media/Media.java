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
package com.zion.media;

public class Media {

    private String publicId;
    private String mediaType;
    private Integer version;
    private String format;
    private String url;
    private String source;
    private String description;
    private boolean coverImage;

    public Media() {
    }

    public String getPublicId() {
        return publicId;
    }

    public Integer getVersion() {
        return version;
    }

    public String getFormat() {
        return format;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCoverImage() {
        return coverImage;
    }

    public void setCoverImage(boolean coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mediaType == null) ? 0 : mediaType.hashCode());
        result = prime * result + ((publicId == null) ? 0 : publicId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Media other = (Media) obj;
        if (mediaType == null) {
            if (other.mediaType != null)
                return false;
        } else if (!mediaType.equals(other.mediaType))
            return false;
        if (publicId == null) {
            if (other.publicId != null)
                return false;
        } else if (!publicId.equals(other.publicId))
            return false;
        return true;
    }
}
