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

package org.wso2.carbon.analytics.dataservice.restapi;

/**
 * The Class AnalyticsRESTException.
 */
public class AnalyticsRESTException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new analytics rest exception.
	 * @param message
	 *            the message
	 */
	public AnalyticsRESTException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new analytics rest exception.
	 * @param e
	 *            Throwable
	 */
	public AnalyticsRESTException(Throwable e) {
		super(e);
	}

	/**
	 * Instantiates a new analytics rest exception.
	 * @param message
	 *            the message
	 * @param e
	 *            the throwable
	 */
	public AnalyticsRESTException(String message, Throwable e) {
		super(message, e);
	}
}
