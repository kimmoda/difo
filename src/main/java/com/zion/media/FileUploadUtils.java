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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadUtils {

	private static final String REQUEST_CONTENT_TYPE = "Content-Type";
	private static final String MULTIPART_FORM_DATA = "multipart/form-data";
	
	public static Map<String, FileItem> getFeedPartsFromRequest(final HttpServletRequest request) throws FileUploadException {
		if (request.getHeader(REQUEST_CONTENT_TYPE) == null || !request.getHeader(REQUEST_CONTENT_TYPE).startsWith(MULTIPART_FORM_DATA)) {
			throw new FileUploadException("Require " + MULTIPART_FORM_DATA + " data submit");
		}
		ServletFileUpload servletFileUpload = new ServletFileUpload();
		servletFileUpload.setFileItemFactory(new DiskFileItemFactory());
		List<FileItem> parts = servletFileUpload.parseRequest(request);
		Map<String, FileItem> feedMap = new HashMap<>();
		for(FileItem item: parts) {
			feedMap.put(item.getFieldName(), item);
		}
		return feedMap;
	}
}

