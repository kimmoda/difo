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

public interface FileService {

    Photo uploadPhoto(InputStream image) throws IOException, CloudinaryFileUploadException;

    /**
     * 
     * @param image
     * @param optionalParams https://cloudinary.com/documentation/image_upload_api_reference#upload
     * @return
     * @throws IOException
     * @throws CloudinaryFileUploadException
     */
    Photo uploadPhoto(InputStream image, Map optionalParams) throws IOException, CloudinaryFileUploadException;

    Photo cropPhoto(InputStream image, Map requireParams, int width, int height) throws IOException, CloudinaryFileUploadException;
}
