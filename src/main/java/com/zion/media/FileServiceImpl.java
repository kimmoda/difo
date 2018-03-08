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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.zion.common.AppConfig;

public class FileServiceImpl implements FileService {
    private Cloudinary cloudinary;
    private static final int MAX_IMAGE_WIDTH = 1500;
    private static final int MAX_IMAGE_HEIGHT = 1500;

    public FileServiceImpl() {
        cloudinary = new Cloudinary(AppConfig.getInstance().getCloudinaryConfig());
    }

    @Override
    public Photo uploadPhoto(InputStream image) throws IOException, CloudinaryFileUploadException {
        return this.uploadPhoto(image, null);
    }

    @Override
    public Photo uploadPhoto(InputStream image, Map optionalParams) throws IOException, CloudinaryFileUploadException {
        Map requiredParams = ObjectUtils.asMap("resource_type", "image", "allowed_formats",
                AppConfig.getInstance().getSupportImageFormats(),
                "transformation", new Transformation<>().quality("auto").width(MAX_IMAGE_WIDTH).height(MAX_IMAGE_HEIGHT).crop("limit"));
        if (optionalParams != null) {
            for (Object object : optionalParams.keySet()) {
                requiredParams.put(object, optionalParams.get(object));
            }
        }
        return this.upload(image, requiredParams);
    }

    @Override
    public Photo cropPhoto(InputStream image, Map optionalParams, int width, int height) throws IOException, CloudinaryFileUploadException {
        Map requiredParams = ObjectUtils.asMap("resource_type", "image", "allowed_formats",
                AppConfig.getInstance().getSupportImageFormats(),
                "transformation", new Transformation<>().quality("auto").width(Integer.toString(width)).height(Integer.toString(height)).crop("lpad"));
        if (optionalParams != null) {
            for (Object object : optionalParams.keySet()) {
                requiredParams.put(object, optionalParams.get(object));
            }
        }
        return this.upload(image, requiredParams);
    }
    
    private Photo upload(InputStream image, Map requiredParams) throws CloudinaryFileUploadException {
        if (image == null) {
            throw new IllegalArgumentException("cannot update empty image.");
        }
        @SuppressWarnings("rawtypes")
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().uploadLarge(image, requiredParams);
            Photo photo = new Photo();
            photo.setFormat((String) uploadResult.get("format"));
            photo.setVersion((Integer) uploadResult.get("version"));
            photo.setPublicId((String) uploadResult.get("public_id"));
            photo.setUrl((String) uploadResult.get("secure_url"));
            return photo;
        } catch (Exception e) {
            throw new CloudinaryFileUploadException("Upload image file fail, please try again later.");
        }
    }

}
