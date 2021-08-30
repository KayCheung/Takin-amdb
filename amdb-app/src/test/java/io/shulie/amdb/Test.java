/*
 * Copyright 2021 Shulie Technology, Co.Ltd
 * Email: shulie@shulie.io
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.shulie.amdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.pamirs.pradar.log.parser.LogBased;
import com.pamirs.pradar.log.parser.trace.AttributesBased;
import com.pamirs.pradar.log.parser.trace.FlagBased;
import com.pamirs.pradar.log.parser.trace.LocalAttributesBased;
import io.shulie.amdb.common.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ExecutorType;

@Slf4j
public class Test {

    private static final String TMPSTR = "|||";
    private static final String LEFT_SLASH = "/";
    private static final String SELECT = "select";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String WHERE = "where";
    private static final String COUNT_STAR = "count(*)";
    private static final String STAR = "*";
    //    protected final Properties config;
    //    protected final FileSystem fileSystem;

    private String project;
    private String version;
    private String module;
    private String reportPath;
    private String scanDate;

    //    public Test(FileSystem fileSystem, Properties config) {
    //        this.config = config;
    //        try {
    //            Method getProperties = config.getClass().getMethod("getProperties");
    //            Map<String, String> data = (Map<String, String>) getProperties.invoke(config, null);
    //            for (Map.Entry<String, String> entry : data.entrySet()) {
    //                log.info(" key {} : {}", entry.getKey(), entry.getValue());
    //            }
    //        } catch (Exception e) {
    //            log.error("get config properties error", e);
    //        }
    //        if (config != null) {
    //            reportPath = (String) config.get("sonar.sql.scan.report.path");
    //            scanDate = (String) config.get("sonar.sql.scan.report.date");
    //            project = (String) config.get("sonar.projectName");
    //            module = (String) config.get("sonar.moduleKey");
    //            version = (String) config.get("sonar.projectVersion");
    //        }
    //
    //        if (project != null) {
    //            project = project.replace("org.sonarqube:", "");
    //            module = module.replace("org.sonarqube:", "");
    //        }
    //        if (scanDate != null) {
    //            scanDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    //        }
    //        log.info("start scan::::::::::::::::::::::::::::: {}, {}, {}, {}", project, module, version, reportPath);
    //        this.fileSystem = fileSystem;
    //    }

    private static String getRepositoryKeyForLanguage() {
        return "";
    }

    public static void main(String args[]) {
        Test test = new Test();
        test.processXml();

        //        saveReport();
    }

    private void processXml() {
        Map<String, String> mybatisMapperMap = new HashMap<>(16);
        List<File> reducedFileList = new ArrayList<>();
        List<org.apache.ibatis.session.Configuration> mybatisConfigurations = new ArrayList<>();

        //        Iterable<InputFile> xmlInputFiles = fs.inputFiles(fs.predicates().hasLanguage(SqlLanguage.KEY));
        //        for (InputFile xmlInputFile : xmlInputFiles) {

        org.apache.ibatis.session.Configuration mybatisConfiguration = new org.apache.ibatis.session.Configuration();
        mybatisConfiguration.setDefaultExecutorType(ExecutorType.SIMPLE);
        //            String xmlFilePath = xmlInputFile.uri().getPath();
        //            File xmlFile = new File(xmlFilePath);
        try {
            //                XmlParser xmlParser = new XmlParser();
            //                Document document = xmlParser.parse(xmlFile);
            //                Element rootElement = document.getRootElement();
            //                String publicIdOfDocType = "";
            //                DocumentType documentType = document.getDocType();
            //                if (null != documentType) {
            //                    publicIdOfDocType = documentType.getPublicID();
            //                    if (null == publicIdOfDocType) {
            //                        publicIdOfDocType = "";
            //                    }
            //                }
            // handle mybatis mapper file
            File reducedXmlFile = new File(
                "/Users/cyf/projects/surge/amdb-db-api/src/main/resources/generator/AppMapper.xml");
            String reducedXmlFilePath = reducedXmlFile.getPath();
            log.info("temp file : {}", reducedXmlFilePath);
            reducedFileList.add(reducedXmlFile);
            //MyBatisMapperXmlHandler myBatisMapperXmlHandler = new MyBatisMapperXmlHandler();
            //myBatisMapperXmlHandler.handleMapperFile(xmlFile, reducedXmlFile);
            //mybatisMapperMap.put(reducedXmlFilePath, xmlFilePath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(new FileInputStream(reducedXmlFile),
                mybatisConfiguration, reducedXmlFilePath, mybatisConfiguration.getSqlFragments());

            //如果不同文件有相同的名称 mybatis解析时会解析出来出错
            //@see org.apache.ibatis.session.Configuration.StrictMap.put
            //mybatisConfiguration 需要分开
            xmlMapperBuilder.parse();
            mybatisConfigurations.add(mybatisConfiguration);
        } catch (IOException e) {
            log.info(e.toString());
        }
        //        }

        // parse MappedStatements
        Set<MappedStatement> stmts = mybatisConfigurations.stream().flatMap(s -> s.getMappedStatements().stream())
            .collect(Collectors.toSet());
        //parseStatement(stmts, mybatisMapperMap);
        // clean reduced.xml
        //cleanFiles(reducedFileList);
    }

    //    public void parseStatement(Set<MappedStatement> stmts, Map<String, String> mybatisMapperMap) {
    //
    //        for (Object obj : stmts) {
    //            if (!(obj instanceof MappedStatement)) {
    //                continue;
    //            }
    //            MappedStatement stmt = null;
    //            stmt = (MappedStatement) obj;
    //            if (stmt.getSqlCommandType() == SqlCommandType.SELECT
    //                    || stmt.getSqlCommandType() == SqlCommandType.UPDATE
    //                    || stmt.getSqlCommandType() == SqlCommandType.DELETE) {
    ////                if (stmt.getSqlCommandType() == SqlCommandType.SELECT) {
    //                SqlSource sqlSource = stmt.getSqlSource();
    //                BoundSql boundSql = null;
    //                try {
    //                    boundSql = sqlSource.getBoundSql(null);
    //                } catch (Exception e) {
    //                    log.warn(e.getMessage());
    //                }
    //                if (null != boundSql) {
    //                    String sql = boundSql.getSql();
    //                    String stmtId = stmt.getId();
    //                    if (!StringUtils.endsWith(stmtId, "!selectKey")) {
    //                        sql = sql.replaceAll("\\n", "");
    //                        sql = sql.replaceAll("\\s{2,}", " ");
    //                        final String mapperResource = stmt.getResource();
    //                        String reducedXmlFilePath = mapperResource;
    //                        if (reducedXmlFilePath.contains("[")) {
    //                            reducedXmlFilePath = mapperResource.substring(mapperResource.indexOf('[') + 1,
    //                                    mapperResource.indexOf(']'));
    //                        }
    //
    //                        // windows environment
    //                        if (!reducedXmlFilePath.startsWith(LEFT_SLASH)) {
    //                            reducedXmlFilePath = LEFT_SLASH + reducedXmlFilePath.replace("\\", LEFT_SLASH);
    //                        }
    //                        log.debug("reducedMapperFilePath: " + reducedXmlFilePath);
    //
    //                        final String sourceMapperFilePath = mybatisMapperMap.get(reducedXmlFilePath);
    //
    //                        // get lineNumber by mapper file and keyWord
    //                        final String[] stmtIdSplit = stmtId.split("\\.");
    //                        final String stmtIdTail = stmtIdSplit[stmtIdSplit.length - 1];
    //                        final String sqlCmdType = stmt.getSqlCommandType().toString().toLowerCase();
    //                        log.debug("sourceMapperFilePath: " + sourceMapperFilePath + " stmtIdTail:  "
    //                                + stmtIdTail + " sqlCmdType: " + sqlCmdType);
    //                        final int lineNumber = getLineNumber(sourceMapperFilePath, stmtIdTail, sqlCmdType);
    //                        // match Rule And Save Issue
    //                        matchRuleAndSaveIssue(stmt.getSqlCommandType(), sql, sourceMapperFilePath, stmtId,
    //                        lineNumber);
    //                    }
    //                }
    //            }
    //        }
    //    }
    //
    //    private void matchRuleAndSaveIssue(SqlCommandType sqlCommandType, String sql, String sourceMapperFilePath,
    //    String stmtId, int line) {
    //        sql = sql.toLowerCase();
    //        String errorMessage = "";
    //        String ruleId = "";
    //
    ////        try {
    ////            if (isJoinSql(sqlCommandType, sql)) {
    ////                errorMessage = "sql 包含 join";
    ////                ruleId = "SingleSQLNOTJOIN";
    ////            }else if (isExistsSql(sqlCommandType,sql)){
    ////                errorMessage = "sql 包含 exists";
    ////                ruleId = "SingleSQLNOTEXISTS";
    ////            }
    ////            else if (isInSql(sqlCommandType,sql)){
    ////                errorMessage = "sql 包含 in";
    ////                ruleId = "SingleSQLNOTIN";
    ////            }
    ////            else if (isSelectInUpdate(sqlCommandType,sql)){
    ////                errorMessage = "sql select/update/insert 中包含 select";
    ////                ruleId = "SingleSQLNOTSELECTINUPDATE";
    ////            }
    //
    ////        } catch (SqlScanException e) {
    ////            errorMessage = "sql 匹配异常:" + e.getMessage();
    ////            ruleId = "SingleSQLError";
    ////        }
    //        ruleId = "SQL";
    //
    //        if (!"".equals(ruleId)) {
    //            log.info("ruleId=" + ruleId + " errorMessage=" + errorMessage + " filePath=" + sourceMapperFilePath
    //            + " stmtId="
    //                    + stmtId);
    //            ErrorDataFromLinter mybatisError = new ErrorDataFromLinter(ruleId, errorMessage, sourceMapperFilePath,
    //                    stmtId, line, sql);
    //            errorDataFromLinterList.add(mybatisError);
    //            getResourceAndSaveIssue(mybatisError);
    //        }
    //    }
    //
    //    private boolean isSelectInUpdate(SqlCommandType sqlCommandType, String sql) throws SqlScanException {
    //        try {
    //
    //            if (sqlCommandType != SqlCommandType.SELECT) {
    //                return sql.contains(" select ") || sql.contains("(select ");
    //            }
    //
    //            return false;
    //        } catch (Exception e) {
    //            throw new SqlScanException(e);
    //        }
    //    }
    //
    //    private boolean isInSql(SqlCommandType sqlCommandType, String sql) throws SqlScanException {
    //        try {
    //            return sql.contains(" in ");
    //        } catch (Exception e) {
    //            throw new SqlScanException(e);
    //        }
    //    }
    //
    //    private boolean isExistsSql(SqlCommandType sqlCommandType, String sql) throws SqlScanException {
    //        try {
    //            return sql.contains(" exists ");
    //        } catch (Exception e) {
    //            throw new SqlScanException(e);
    //        }
    //    }
    //
    //    private boolean isJoinSql(SqlCommandType sqlCommandType, String sql) throws SqlScanException {
    //        try {
    //            return sql.contains(" join ");
    ////            if (sql.contains(" join ")) {
    ////                return true;
    ////            }
    ////                if (sql.contains(" from ")) {
    ////                    String fromSql = sql.substring(sql.indexOf(" from ") + 6);
    ////                    if (fromSql.contains(" where ")) {
    ////                        return fromSql.substring(0, fromSql.indexOf(" where ")).contains(",");
    ////                    }
    ////                }
    ////                sql = sql.replaceFirst("select ", "");
    ////                return sql.contains(" select ") || sql.contains("(select ");
    //
    //        } catch (Exception e) {
    //            throw new SqlScanException(e);
    //        }
    //    }
    //
    //
    //    private void getResourceAndSaveIssue(final ErrorDataFromLinter error) {
    //        log.debug(error.toString());
    //
    //
    //        final FileSystem fs = context.fileSystem();
    //        final InputFile inputFile = fs.inputFile(fs.predicates().hasAbsolutePath(error.getFilePath()));
    //        log.debug("inputFile null ? " + (inputFile == null));
    //
    //        if (inputFile != null) {
    //            saveIssue(inputFile, error.getLine(), error.getType(), error.getDescription() + "/" + error
    //            .getStmtId() + "/" + error.getSql());
    //        } else {
    //            log.error("Not able to find a InputFile with " + error.getFilePath());
    //        }
    //    }
    //
    //    private void saveIssue(final InputFile inputFile, int line, final String externalRuleKey, final String
    //    message) {
    //        RuleKey ruleKey = RuleKey.of(getRepositoryKeyForLanguage(), externalRuleKey);
    //
    //        NewIssue newIssue = context.newIssue().forRule(ruleKey).gap(2.0);
    //
    //        NewIssueLocation primaryLocation = newIssue.newLocation().on(inputFile).message(message);
    //        if (line > 0) {
    //            primaryLocation.at(inputFile.selectLine(line));
    //        }
    //        newIssue.at(primaryLocation);
    //        newIssue.save();
    //        log.info(" issue save : {}", newIssue);
    //    }
    //
    //    public void cleanFiles(List<File> files) {
    //        for (File file : files) {
    //            if (file.exists() && file.isFile()) {
    //                try {
    //                    Files.delete(Paths.get(new URI("file:///" + file.getAbsolutePath().replace("\\",
    //                    LEFT_SLASH))));
    //                } catch (IOException | URISyntaxException e) {
    //                    log.warn(e.toString());
    //                }
    //            }
    //        }
    //    }
    //
    //    private int getLineNumber(final String filePath, final String stmtIdTail, final String sqlCmdType) {
    //        return IOUtils.getLineNumber(filePath, stmtIdTail, sqlCmdType);
    //    }

    public void test2() {
        String test2 = "{\"error\":null,\"data\":[{\"log\":null,\"version\":\"16\",\"hostIp\":\"172.17..1\","
            + "\"agentId\":\"172.17.0.1-15857\",\"appName\":\"tsnnnew-redis\","
            + "\"traceId\":\"ee73e90a16213970398664698d0026\",\"entranceNodeId\":null,\"entranceId\":null,"
            + "\"level\":0,\"parentIndex\":0,\"index\":0,\"rpcId\":\"0\",\"rpcIdArray\":[0],\"rpcType\":0,"
            + "\"logType\":1,\"traceAppName\":\"\",\"upAppName\":\"tsnnnew-redis\",\"startTime\":1621397039868,"
            + "\"cost\":13,\"middlewareName\":\"tomcat\",\"serviceName\":\"/agent/redis/get\","
            + "\"methodName\":\"POST\",\"remoteIp\":\"192.168.1.174\",\"port\":\"45210\",\"resultCode\":\"200\","
            + "\"requestSize\":0,\"responseSize\":0,\"request\":\"\",\"response\":\"\",\"clusterTest\":true,"
            + "\"callbackMsg\":\"\",\"samplingInterval\":1,\"localId\":null,\"attributes\":{},\"localAttributes\":{},"
            + "\"async\":false,\"invokeId\":null,\"invokeType\":null,\"flags\":null,\"attributesBased\":null,"
            + "\"taskId\":null,\"localAttributesBased\":null,\"serverAppName\":\"tsnnnew-redis\","
            + "\"logTime\":1621397039868,\"ok\":false,\"clientAppName\":\"tsnnnew-redis\","
            + "\"serviceId\":\"/agent/redis/get.POST\",\"dataType\":1},{\"log\":null,\"version\":\"16\","
            + "\"hostIp\":\"172.17..1\",\"agentId\":\"172.17.0.1-15857\",\"appName\":\"tsnnnew-redis\","
            + "\"traceId\":\"ee73e90a16213970398664698d0026\",\"entranceNodeId\":null,\"entranceId\":null,"
            + "\"level\":0,\"parentIndex\":0,\"index\":0,\"rpcId\":\"0.1\",\"rpcIdArray\":[0,1],\"rpcType\":5,"
            + "\"logType\":2,\"traceAppName\":\"\",\"upAppName\":\"tsnnnew-redis\",\"startTime\":1621397039869,"
            + "\"cost\":0,\"middlewareName\":\"redis\",\"serviceName\":\"get\",\"methodName\":\"1\","
            + "\"remoteIp\":\"unknow\",\"port\":\"0\",\"resultCode\":\"00\",\"requestSize\":0,\"responseSize\":0,"
            + "\"request\":\"{1}\",\"response\":\"\",\"clusterTest\":true,\"callbackMsg\":\"redis-lettuce\","
            + "\"samplingInterval\":1,\"localId\":null,\"attributes\":{},\"localAttributes\":{},\"async\":false,"
            + "\"invokeId\":null,\"invokeType\":null,\"flags\":null,\"attributesBased\":null,\"taskId\":null,"
            + "\"localAttributesBased\":null,\"serverAppName\":\"tsnnnew-redis\",\"logTime\":1621397039869,"
            + "\"ok\":true,\"clientAppName\":\"tsnnnew-redis\",\"serviceId\":\"get.1\",\"dataType\":1}],\"total\":1,"
            + "\"success\":true}";
        //System.out.println(JSON.parseArray(test2, RpcBased.class));

        AmdbResult<List<RpcBased>> amdbResponse = JSON.parseObject(test2,
            new TypeReference<AmdbResult<List<RpcBased>>>() {
            });

        log.info(JSON.toJSONString(amdbResponse));
    }
}


class RpcBased extends LogBased implements Serializable {
    private String appName;
    private String traceId;
    private String entranceNodeId;
    private String entranceId;
    private int level;
    private int parentIndex;
    private int index;
    private String rpcId;
    private int rpcType = 0;
    private int logType = 0;
    private String traceAppName;
    private String upAppName;
    private long startTime;
    private long cost;
    private String middlewareName;
    private String serviceName;
    private String methodName;
    private String remoteIp;
    private String port;
    private String resultCode;
    private long requestSize;
    private long responseSize;
    private String request;
    private String response;
    private boolean clusterTest;
    private String callbackMsg;
    private int samplingInterval = 1;
    private String localId;
    private Map<String, String> attributes;
    private Map<String, String> localAttributes;
    private boolean async;
    private String invokeId;
    private String invokeType;
    private FlagBased flags;
    private AttributesBased attributesBased;
    private LocalAttributesBased localAttributesBased;

    public RpcBased() {
    }

    public void adjust() {
        if (StringUtils.isNotBlank(this.rpcId)) {
            String[] segments = StringUtils.split(this.rpcId, '.');
            this.level = segments.length;

            try {
                this.index = Integer.valueOf(segments[segments.length - 1]);
            } catch (NumberFormatException var3) {
            }
        }

    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInvokeType() {
        return this.invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLogType() {
        if (this.flags != null) {
            if (this.flags.isEntrance()) {
                return 1;
            } else {
                return this.flags.isServer() ? 3 : 2;
            }
        } else {
            return this.logType;
        }
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getAppName() {
        return this.appName;
    }

    public long getLogTime() {
        return this.startTime;
    }

    public int getParentIndex() {
        return this.parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public boolean isOk() {
        return StringUtils.equals(this.getResultCode(), "00");
    }

    public String getClientAppName() {
        if (this.localAttributesBased != null) {
            return this.localAttributesBased.getUpAppName();
        } else {
            return this.logType != 1 && this.logType != 2 ? this.upAppName : this.appName;
        }
    }

    public String getServerAppName() {
        if (this.flags != null) {
            return this.flags.isServer() ? this.appName : this.localAttributesBased.getUpAppName();
        } else {
            return this.logType != 1 && this.logType != 3 ? this.upAppName : this.appName;
        }
    }

    public String getServiceId() {
        return this.serviceName + '.' + this.methodName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRpcId() {
        return this.invokeId != null ? this.invokeId : this.rpcId;
    }

    public void setRpcId(String rpcId) {
        this.rpcId = rpcId;
    }

    public int getRpcType() {
        return this.invokeType != null ? Integer.valueOf(this.invokeType) : this.rpcType;
    }

    public void setRpcType(int rpcType) {
        this.rpcType = rpcType;
    }

    public String getTraceAppName() {
        return this.attributesBased != null ? this.attributesBased.getTraceAppName() : this.traceAppName;
    }

    public void setTraceAppName(String traceAppName) {
        this.traceAppName = traceAppName;
    }

    public String getUpAppName() {
        return this.localAttributesBased != null ? this.localAttributesBased.getUpAppName() : this.upAppName;
    }

    public void setUpAppName(String upAppName) {
        this.upAppName = upAppName;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getCost() {
        return this.cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getMiddlewareName() {
        return this.middlewareName;
    }

    public void setMiddlewareName(String middlewareName) {
        this.middlewareName = middlewareName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRemoteIp() {
        return this.localAttributesBased != null ? this.localAttributesBased.getRemoteIp() : this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getPort() {
        return this.localAttributesBased != null ? this.localAttributesBased.getRemotePort() : this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public long getRequestSize() {
        return this.localAttributesBased != null ? this.localAttributesBased.getRequestSize() : this.requestSize;
    }

    public void setRequestSize(long requestSize) {
        this.requestSize = requestSize;
    }

    public long getResponseSize() {
        return this.localAttributesBased != null ? this.localAttributesBased.getResponseSize() : this.responseSize;
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isClusterTest() {
        return this.flags != null ? this.flags.isPressureTest() : this.clusterTest;
    }

    public void setClusterTest(boolean clusterTest) {
        this.clusterTest = clusterTest;
    }

    public String getCallbackMsg() {
        return this.callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public int getSamplingInterval() {
        return this.samplingInterval;
    }

    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    public String getLocalId() {
        return this.localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public Map<String, String> getAttributes() {
        return this.attributes == null ? Collections.EMPTY_MAP : this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getLocalAttributes() {
        return this.localAttributes == null ? Collections.EMPTY_MAP : this.localAttributes;
    }

    public void setLocalAttributes(Map<String, String> localAttributes) {
        this.localAttributes = localAttributes;
    }

    public boolean isAsync() {
        return this.async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public byte getDataType() {
        return 1;
    }

    public String getEntranceId() {
        return this.entranceId;
    }

    public void setEntranceId(String entranceId) {
        this.entranceId = entranceId;
    }

    public String getEntranceNodeId() {
        return this.entranceNodeId;
    }

    public void setEntranceNodeId(String entranceNodeId) {
        this.entranceNodeId = entranceNodeId;
    }

    public String getInvokeId() {
        return this.invokeId;
    }

    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    public FlagBased getFlags() {
        return this.flags;
    }

    public void setFlags(FlagBased flags) {
        this.flags = flags;
    }

    public AttributesBased getAttributesBased() {
        return this.attributesBased;
    }

    public void setAttributesBased(AttributesBased attributesBased) {
        this.attributesBased = attributesBased;
    }

    public LocalAttributesBased getLocalAttributesBased() {
        return this.localAttributesBased;
    }

    public void setLocalAttributesBased(LocalAttributesBased localAttributesBased) {
        this.localAttributesBased = localAttributesBased;
    }
}

class AmdbResult<T> implements Serializable {
    private static final long serialVersionUID = 45387487319877474L;
    private ErrorInfo error;
    private T data;
    private Long total;
    private Boolean success;
    private Boolean notSuccess;
}

