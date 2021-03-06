/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.analytics.messageconsole.beans;

import java.io.Serializable;

/**
 * This class represent the data that hold the single column cell.
 */
public class EntityBean implements Serializable {

    private static final long serialVersionUID = 1928077277570009600L;

    private String columnName;
    private String value;
    private String type;

    public EntityBean(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public EntityBean(String columnName, String value, String type) {
        this.columnName = columnName;
        this.value = value;
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
