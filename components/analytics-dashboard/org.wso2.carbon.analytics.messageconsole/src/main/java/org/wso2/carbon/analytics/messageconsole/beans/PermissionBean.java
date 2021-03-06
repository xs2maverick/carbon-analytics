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
import java.lang.reflect.Field;

/**
 * This class represent user's analytics data service level permissions.
 */
public class PermissionBean implements Serializable {

    private static final long serialVersionUID = -5034605258834260595L;

    private boolean createTable;
    private boolean listTable;
    private boolean dropTable;
    private boolean searchRecord;
    private boolean listRecord;
    private boolean putRecord;
    private boolean deleteRecord;
    private boolean setIndex;
    private boolean getIndex;
    private boolean deleteIndex;

    public PermissionBean() {
    }

    public boolean isDeleteIndex() {
        return deleteIndex;
    }

    public void setDeleteIndex(boolean deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    public boolean isGetIndex() {
        return getIndex;
    }

    public void setGetIndex(boolean getIndex) {
        this.getIndex = getIndex;
    }

    public boolean isSetIndex() {
        return setIndex;
    }

    public void setSetIndex(boolean setIndex) {
        this.setIndex = setIndex;
    }

    public boolean isDeleteRecord() {
        return deleteRecord;
    }

    public void setDeleteRecord(boolean deleteRecord) {
        this.deleteRecord = deleteRecord;
    }

    public boolean isPutRecord() {
        return putRecord;
    }

    public void setPutRecord(boolean putRecord) {
        this.putRecord = putRecord;
    }

    public boolean isListRecord() {
        return listRecord;
    }

    public void setListRecord(boolean listRecord) {
        this.listRecord = listRecord;
    }

    public boolean isSearchRecord() {
        return searchRecord;
    }

    public void setSearchRecord(boolean searchRecord) {
        this.searchRecord = searchRecord;
    }

    public boolean isDropTable() {
        return dropTable;
    }

    public void setDropTable(boolean dropTable) {
        this.dropTable = dropTable;
    }

    public boolean isListTable() {
        return listTable;
    }

    public void setListTable(boolean listTable) {
        this.listTable = listTable;
    }

    public boolean isCreateTable() {
        return createTable;
    }

    public void setCreateTable(boolean createTable) {
        this.createTable = createTable;
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            Class<?> objClass = this.getClass();

            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                Object value = field.get(this);

                sb.append(name).append(":").append(value.toString()).append("||");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
