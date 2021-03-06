/*
 *  Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.wso2.carbon.analytics.datasource.rdbms.mysql;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.wso2.carbon.analytics.datasource.commons.exception.AnalyticsException;
import org.wso2.carbon.analytics.datasource.core.AnalyticsFileSystemTest;
import org.wso2.carbon.analytics.datasource.core.fs.AnalyticsFileSystem;
import org.wso2.carbon.analytics.datasource.rdbms.RDBMSAnalyticsFileSystem;
import org.wso2.carbon.analytics.datasource.rdbms.RDBMSQueryConfigurationEntry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MySQL implementation of analytics file system tests.
 */
public class MySQLInnoDBAnalyticsFileSystemTest extends AnalyticsFileSystemTest {

    private DataSource dataSource;

    private AnalyticsFileSystem afs;

    public AnalyticsFileSystem getAFS() {
        return this.afs;
    }

    @BeforeSuite
    @Parameters({"mysql.url", "mysql.username", "mysql.password"})
    public void setup(String url, String username, String password)
            throws NamingException, AnalyticsException, SQLException, IOException {

        this.dataSource = createDataSource(url, username, password);
        this.dropSystemTables();
        new InitialContext().bind("DSFS", this.dataSource);
        this.afs = new RDBMSAnalyticsFileSystem(this.generateQueryConfiguration());
        Map<String, String> props = new HashMap<String, String>();
        props.put("datasource", "DSFS");
        this.afs.init(props);
        this.init("MySQLInnoDBAnalyticsDataSource", afs);
    }

    @AfterClass
    public void destroy() throws AnalyticsException, SQLException {
        try {
            new InitialContext().unbind("DSFS");
        } catch (NamingException ignore) { }
        if (this.dataSource != null) {
            this.dataSource.close(true);
        }
        this.dropSystemTables();
    }

    private DataSource createDataSource(String url, String username, String password) {
        PoolProperties pps = new PoolProperties();
        pps.setDriverClassName("com.mysql.jdbc.Driver");
        pps.setUrl(url);
        pps.setUsername(username);
        pps.setPassword(password);
        pps.setDefaultAutoCommit(false);
        return new DataSource(pps);
    }
    
    private void dropSystemTables() throws SQLException {
        Connection conn = null;
        try {
            conn = this.dataSource.getConnection();
            conn.prepareStatement("DROP TABLE IF EXISTS AN_FS_DATA").executeUpdate();
            conn.prepareStatement("DROP TABLE IF EXISTS AN_FS_PATH").executeUpdate();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) { } 
            }
        }
    }
    
    private RDBMSQueryConfigurationEntry generateQueryConfiguration() {
        RDBMSQueryConfigurationEntry conf = new RDBMSQueryConfigurationEntry();
        String[] fsTableInitQueries = new String[3];
        fsTableInitQueries[0] = "CREATE TABLE AN_FS_PATH (path VARCHAR(256), is_directory BOOLEAN, length BIGINT, " +
                                "parent_path VARCHAR(256), PRIMARY KEY(path), FOREIGN KEY (parent_path) REFERENCES " +
                                "AN_FS_PATH(path) ON DELETE CASCADE) ENGINE='InnoDB'";
        fsTableInitQueries[1] = "CREATE TABLE AN_FS_DATA (path VARCHAR(256), sequence BIGINT, data BLOB, " +
                                "PRIMARY KEY (path,sequence), FOREIGN KEY (path) REFERENCES AN_FS_PATH(path) ON " +
                                "DELETE CASCADE) ENGINE='InnoDB'";
        fsTableInitQueries[2] = "CREATE INDEX index_parent_id ON AN_FS_PATH(parent_path)";
        conf.setFsTableInitQueries(fsTableInitQueries);
        conf.setFsTablesCheckQuery("SELECT record_id FROM AN_FS_PATH WHERE path = '/'");
        conf.setFsPathRetrievalQuery("SELECT * FROM AN_FS_PATH WHERE path = ?");
        conf.setFsListFilesQuery("SELECT path FROM AN_FS_PATH WHERE parent_path = ?");
        conf.setFsInsertPathQuery("INSERT INTO AN_FS_PATH (path,is_directory,length,parent_path) VALUES (?,?,?,?)");
        conf.setFsFileLengthRetrievalQuery("SELECT length FROM AN_FS_PATH WHERE path = ?");
        conf.setFsFileLengthRetrievalQuery("SELECT length FROM AN_FS_PATH WHERE path = ?");
        conf.setFsSetFileLengthQuery("UPDATE AN_FS_PATH SET length = ? WHERE path = ?");
        conf.setFsReadDataChunkQuery("SELECT data FROM AN_FS_DATA WHERE path = ? AND sequence = ?");
        conf.setFsWriteDataChunkQuery("INSERT INTO AN_FS_DATA (path,sequence,data) VALUES (?,?,?)");
        conf.setFsUpdateDataChunkQuery("UPDATE AN_FS_DATA SET data = ? WHERE path = ? AND sequence = ?");
        conf.setFsDeletePathQuery("DELETE FROM AN_FS_PATH WHERE path = ?");
        conf.setFsDataChunkSize(10240);
        return conf;
    }
    
}
