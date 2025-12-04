CREATE TABLE USER
(
    ID   bigint NOT NULL AUTO_INCREMENT,
    NAME varchar(200)  DEFAULT NULL,
    AGE  double(10, 2) DEFAULT NULL,
    PRIMARY KEY (ID)
);



CREATE TABLE PERSON_STATISTICS_INFO
(
    ID               int NOT NULL,
    CODE             varchar(50) DEFAULT NULL COMMENT '编码',
    TOTAL_NUM        int         DEFAULT NULL COMMENT '总数',
    REPORT_DATE      datetime    DEFAULT NULL COMMENT '统计日期时间',
    REPORT_PURE_DATE int         DEFAULT NULL COMMENT '统计日期 yyyyMMdd',
    REPORT_PURE_TIME int         DEFAULT NULL COMMENT '统计时间 HHmmss',
    PRIMARY KEY (ID)
);