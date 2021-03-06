/*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.analytics.datasource.hbase;

import com.hazelcast.core.Hazelcast;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.wso2.carbon.analytics.dataservice.AnalyticsDataServiceImpl;
import org.wso2.carbon.analytics.dataservice.AnalyticsServiceHolder;
import org.wso2.carbon.analytics.dataservice.clustering.AnalyticsClusterManagerImpl;
import org.wso2.carbon.analytics.datasource.commons.exception.AnalyticsException;
import org.wso2.carbon.analytics.datasource.core.fs.AnalyticsFileSystem;
import org.wso2.carbon.analytics.datasource.core.rs.AnalyticsRecordStore;
import org.wso2.carbon.analytics.datasource.rdbms.AnalyticsDataServiceTest;

import javax.naming.NamingException;
import java.io.IOException;

public class HBaseADServiceClusteredTest extends AnalyticsDataServiceTest {

    private HBaseAnalyticsRecordStoreTest hbasearstest;

    private HDFSAnalyticsFileSystemTest hdfsafstest;

    @BeforeClass
    public void setup() throws NamingException, AnalyticsException, IOException {
        Hazelcast.shutdownAll();
        this.hbasearstest = new HBaseAnalyticsRecordStoreTest();
        this.hdfsafstest = new HDFSAnalyticsFileSystemTest();
        this.hbasearstest.setup();
        this.hdfsafstest.setup();
        AnalyticsRecordStore ars = this.hbasearstest.getStore();
        AnalyticsFileSystem afs = this.hdfsafstest.getAFS();
        AnalyticsServiceHolder.setHazelcastInstance(Hazelcast.newHazelcastInstance());
        AnalyticsServiceHolder.setAnalyticsClusterManager(new AnalyticsClusterManagerImpl());
        this.init(new AnalyticsDataServiceImpl(ars, afs, 6));
    }

    @AfterClass
    public void done() throws NamingException, AnalyticsException, IOException {
        this.service.destroy();
        Hazelcast.shutdownAll();
        AnalyticsServiceHolder.setHazelcastInstance(null);
        this.hbasearstest.destroy();
        this.hdfsafstest.destroy();

    }
}
