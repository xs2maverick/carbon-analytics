/**
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.analytics.dataservice.restapi.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.analytics.dataservice.commons.exception.AnalyticsIndexException;
import org.wso2.carbon.analytics.dataservice.restapi.Constants;
import org.wso2.carbon.analytics.dataservice.restapi.beans.ResponseBean;

/**
 * The Class AnalyticsIndexExceptionMapper triggers when AnalyticsIndexException occurred.
 */
@Provider
public class AnalyticsIndexExceptionMapper implements ExceptionMapper<AnalyticsIndexException> {
	
	/** The logger. */
	private static final Log logger = LogFactory.getLog(AnalyticsIndexExceptionMapper.class);

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
    public Response toResponse(AnalyticsIndexException exception) {
		ResponseBean errorResponse = new ResponseBean();
		errorResponse.setStatus(Constants.Status.FAILED);
		errorResponse.setMessage(exception.getMessage());
		logger.error("Error accessing Analytics Indexing REST APIs", exception);
		return Response.serverError().entity(errorResponse).build();

    }
	
}
