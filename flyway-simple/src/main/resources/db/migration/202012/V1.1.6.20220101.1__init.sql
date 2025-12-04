SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `flyway_test`.`auth_pass_record`
(
    `ID`                  bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `DEVICE_ID`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备 ID',
    `ROOM_CODE`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间号',
    `PERSON_ID`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '人员ID',
    `FULL_NAME`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员姓名',
    `CARD_NUMBER`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
    `PERSON_CODE`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入住人员',
    `VERIFICATION_MODE`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证方式1：二维码，2：刷卡，3：远程',
    `VERIFICATION_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '验证时间',
    `VERIFICATION_RESULT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '验证结果(0：失败/1：成功)',
    `DIRECTION`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '出入方向0：进，1：出',
    `CREATE_TIME`         datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`         datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `PERSON_ATTRIBUTE`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员属性',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `INDEX_PERSON_CODE` (`PERSON_CODE`) USING BTREE COMMENT '人员编码：工作人员ID、留观人员一码通编码'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '门禁出入记录表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`aze_user_behavior`
(
    `ID`          bigint                                                 NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_NAME`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `ITEM_ID`     int                                                    NULL DEFAULT NULL COMMENT '操作项',
    `ITEM_NAME`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `OPT_TIME`    date                                                   NULL DEFAULT NULL COMMENT '发生时间',
    `LOCATION`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '位置',
    `SOURCE`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
    `MODULE`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
    `TARGET`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作目标',
    `PARAMS`      text CHARACTER SET utf8 COLLATE utf8_general_ci        NULL COMMENT '参数',
    `UPDATE_TIME` datetime(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `CREATE_TIME` datetime(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户行为'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_anxiety_questionnaire`
(
    `ID`               bigint                                                       NOT NULL COMMENT '主键ID',
    `PERSON_CODE`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员一码通编码',
    `PERSON_NAME`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员名称',
    `FEEL_ABNORMAL`    tinyint                                                      NULL     DEFAULT NULL COMMENT '感到紧张、焦虑或快要崩溃',
    `WORRYING_CONTROL` tinyint                                                      NULL     DEFAULT NULL COMMENT '不能停止或控制担忧',
    `WORRIED_OVER`     tinyint                                                      NULL     DEFAULT NULL COMMENT '过多的担忧各种各样的事情',
    `DIFFICULT_RELAX`  tinyint                                                      NULL     DEFAULT NULL COMMENT '很难放松下来',
    `HOTCH`            tinyint                                                      NULL     DEFAULT NULL COMMENT '不安得难以静坐',
    `IRRITABLE`        tinyint                                                      NULL     DEFAULT NULL COMMENT '变得容易气恼或易怒',
    `BAD_WORRIED`      tinyint                                                      NULL     DEFAULT NULL COMMENT '感到似乎要发生不好的事情而担心受怕',
    `REGISTER_TIME`    datetime(0)                                                  NULL     DEFAULT NULL COMMENT '问卷填报时间',
    `CREATE_TIME`      datetime(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '状态：S0A有效;S0X无效；',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '数据来源',
    `SCORE`            int                                                          NULL     DEFAULT NULL COMMENT '得分',
    `STAFF_NAME`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员名称：代填时传入',
    `STAFF_PHONE`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员电话：代填时传入',
    `MENTAL_ID`        bigint                                                       NULL     DEFAULT NULL COMMENT '关联心理量表主键',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员焦虑问卷'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_area`
(
    `ID`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AREA_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '场所区域编码',
    `AREA_NAME`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '场所区域名称',
    `FULL_AREA_NAME`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '场所区域全称',
    `PARENT_AREA_CODE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '父节点区域编码',
    `NODE_LEVEL`       int                                                           NULL     DEFAULT NULL COMMENT '节点层级',
    `AREA_TYPE`        tinyint                                                       NULL     DEFAULT 0 COMMENT '场所类型:10,生活区;20,医学观察区;30,后勤保障区;',
    `AREA_LEVEL`       tinyint                                                       NULL     DEFAULT NULL COMMENT '场所等级',
    `TOTAL_AREA`       double                                                        NULL     DEFAULT NULL COMMENT '场所面积',
    `TOTAL_BUILDING`   int                                                           NULL     DEFAULT NULL COMMENT '楼栋数',
    `TOTAL_ROOM`       int                                                           NULL     DEFAULT NULL COMMENT '房间数',
    `HOTEL_ID`         bigint                                                        NULL     DEFAULT NULL COMMENT '酒店ID',
    `GEO_JSON`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '坐标信息',
    `PLAN_JSON`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '结构平面示意图',
    `CREATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `REMARK`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `DUTY_NAME`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '负责人姓名',
    `DUTY_PHONE`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '负责人电话',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `AK_IDX_AREA_CODE` (`AREA_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '场所区域信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_back_route`
(
    `ID`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`           bigint                                                        NULL DEFAULT NULL COMMENT '留观ID',
    `BACK_DATE`             date                                                          NULL DEFAULT NULL COMMENT '返程日期',
    `BACK_TIME`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '返程时间段',
    `LEAVE_TYPE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '离开方式',
    `BACK_SITE`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返程始发站点：车站/机场等',
    `ARRIVAL_SITE`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返程目的站点：车站/机场等',
    `PLAN_ARRIVAL_TIME`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '预计抵达目的地时间',
    `COUNTRY`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '目的地国家',
    `STREET`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '目的地街道：省/市/区县/街道',
    `PROVINCE_OR_CITY`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地省市行政编码',
    `PROVINCE_OR_CITY_NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '目的地省市名称',
    `ADDRESS_DETAIL`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '详细地址',
    `FILE_JSON`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '附件：数组格式；',
    `AUDIT_STATUS`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0:待审核;1:审核通过;2:审核未通过',
    `INFO_UPDATE`           tinyint                                                       NULL DEFAULT 0 COMMENT '信息更新：false,不允许更新；true,允许更新;',
    `AUDIT_USER`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '审核账号',
    `AUDIT_TIME`            datetime(0)                                                   NULL DEFAULT NULL COMMENT '审核时间',
    `AUDIT_OPINION`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '审核意见',
    `CREATE_TIME`           datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否有效',
    `SOURCE`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源',
    `CREATE_PHONE`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建工作人员电话：代填时传入',
    `UPDATE_PHONE`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新工作人员电话：代填时传入',
    `TRAIN_EXTEND`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '航班/车次信息：JSON数组',
    `IS_NONSTOP`            tinyint                                                       NULL DEFAULT NULL COMMENT '是否直达：false,非直达;true,直达',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '返程上报'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_block`
(
    `ID`            bigint                                                        NOT NULL COMMENT '主键',
    `BLOCK_CODE`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '区块编号',
    `BLOCK_NAME`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '区块名称',
    `BLOCK_TYPE`    tinyint                                                       NULL     DEFAULT NULL COMMENT '区块类型(10:走廊，20：厕所，30：电梯)',
    `AREA_CODE`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '场所区域编码',
    `BUILDING_CODE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '楼栋编码',
    `GEO_JSON`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL COMMENT '坐标信息',
    `PLAN_JSON`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '结构平面示意图',
    `TOTAL_AREA`    double                                                        NULL     DEFAULT NULL COMMENT '面积',
    `CREATE_TIME`   datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `REMARK`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '备注',
    `MODEL_CODE`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '三景模型编码',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '区块信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_block_bak`
(
    `ID`            bigint                                                        NOT NULL COMMENT '主键',
    `BLOCK_CODE`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '区块编号',
    `BLOCK_NAME`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '区块名称',
    `BLOCK_TYPE`    tinyint                                                       NULL     DEFAULT NULL COMMENT '区块类型(10:走廊，20：厕所，30：电梯)',
    `BUILDING_CODE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '楼栋编码',
    `GEO_JSON`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL COMMENT '坐标信息',
    `PLAN_JSON`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '结构平面示意图',
    `TOTAL_AREA`    double                                                        NULL     DEFAULT NULL COMMENT '面积',
    `CREATE_TIME`   datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `REMARK`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '备注',
    `MODEL_CODE`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '三景模型编码',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '区块信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_building`
(
    `ID`                  bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `BUILDING_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '楼栋编码',
    `BUILDING_NAME`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '楼栋名称',
    `BUILD_TYPE`          tinyint                                                       NULL DEFAULT NULL COMMENT '楼栋类型',
    `BUILDING_LEVEL`      tinyint                                                       NULL DEFAULT NULL COMMENT '楼栋等级',
    `GEO_JSON`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '坐标信息',
    `PLAN_JSON`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '结构平面示意图',
    `TOTAL_BUILDING_AREA` double                                                        NULL DEFAULT 0 COMMENT '总楼栋面积',
    `TOTAL_FLOOR`         int                                                           NULL DEFAULT 0 COMMENT '楼层数',
    `TOTAL_ROOM`          int                                                           NULL DEFAULT 0 COMMENT '房间数',
    `AREA_CODE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '场所区域编码',
    `CREATE_TIME`         datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`         datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `REMARK`              varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `DUTY_NAME`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '负责人姓名',
    `DUTY_PHONE`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '负责人电话',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `AK_IDX_BUILDING_CODE` (`BUILDING_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '楼栋信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_daily_collection`
(
    `ID`            bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员名称',
    `PERSON_CODE`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '一码通编码',
    `SYMPTOM_TAB`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '症状标记(逗号分隔):0,无；1,干咳；2,乏力；3,鼻塞流涕；4,咽痛；5,嗅觉/味觉减退；6,结膜炎；7,肌痛；8,腹泻；9,其他症状',
    `SYMPTOM`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '症状描述',
    `REGISTER_ID`   bigint                                                        NULL     DEFAULT NULL COMMENT '操作人员ID',
    `REGISTER_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '操作人员姓名',
    `REGISTER_TIME` datetime(0)                                                   NULL     DEFAULT NULL COMMENT '采集时间',
    `CREATE_TIME`   datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                                   NOT NULL COMMENT '更新时间',
    `STATUS`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `SOURCE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源 ',
    `STAFF_PHONE`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '工作人员电话：代填时传入',
    `STAFF_NAME`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '工作人员名称：代填时传入',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_REGISTER_TIME` (`REGISTER_TIME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观日常采集'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_declare_information`
(
    `ID`                         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留观人员一码通',
    `STAFF_ID`                   bigint                                                        NULL DEFAULT NULL COMMENT '审核人id',
    `DESTINATION_AUDIT_STATUS`   tinyint                                                       NULL DEFAULT NULL COMMENT '目的地申报审核状态:0,未通过;1,通过;',
    `DESTINATION_DECLARE_STATUS` tinyint                                                       NULL DEFAULT NULL COMMENT '目的地申报状态:0,未申报;1,已申报;',
    `HOME_MANAGEMENT`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居家管理条件',
    `HOUSING_SITUATION`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住房情况',
    `TRAIN_NUMBER`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '离开的航班号/车次',
    `DESTINATION_CITY`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的城市',
    `DESTINATION`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的地详细地址',
    `CREATE_TIME`                datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `LEAVE_TYPE`                 tinyint                                                       NULL DEFAULT NULL COMMENT '离开方式',
    `SOURCE`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据来源',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '目的地申报信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_device`
(
    `ID`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '设备编码',
    `NAME`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '设备名称',
    `TYPE`        tinyint                                                       NULL DEFAULT NULL COMMENT '设备类型',
    `SITE`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装位置',
    `IP_ADDRESS`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT 'IP地址',
    `GEO_JSON`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '坐标信息',
    `AREA_CODE`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '归属区域',
    `STATUS`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `TENANT_ID`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '租户ID',
    `CREATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '设备信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_disinfect_record`
(
    `ID`               bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ROOM_CODE`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间编码',
    `DISINFECT_STATUS` tinyint                                                      NULL DEFAULT NULL COMMENT '消杀状态：0 未消杀； 1 已消杀',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源',
    `REGISTER_NAME`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `REGISTER_TIME`    datetime(0)                                                  NULL DEFAULT NULL COMMENT '操作时间',
    `CREATE_TIME`      datetime(0)                                                  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '消杀记录表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_dismissal_record`
(
    `ID`                  bigint                                                        NOT NULL,
    `PERSON_CODE`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员编码',
    `PERSON_NAME`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员名称',
    `ARRIVAL_TIME`        datetime(0)                                                   NULL     DEFAULT NULL COMMENT '抵达时间（入境时间、开始留观时间）',
    `PLAN_DISMISSAL_TIME` datetime(0)                                                   NULL     DEFAULT NULL COMMENT '计划解除留观时间',
    `DISMISSAL_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '解除留观时间',
    `CREATE_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '是否有效',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观即将解除留观通知记录'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_health_examination`
(
    `ID`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`              bigint                                                        NOT NULL COMMENT '工作人员id',
    `REGISTER_TIME`         datetime(0)                                                   NULL DEFAULT NULL COMMENT '登记时间',
    `TEMPERATURE_STATUS_AM` tinyint                                                       NULL DEFAULT NULL COMMENT '上午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `TEMPERATURE_STATUS_PM` tinyint                                                       NULL DEFAULT 0 COMMENT '下午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `SYMPTOM_TAB`           tinyint                                                       NULL DEFAULT NULL COMMENT '症状标记:0,无；1,干咳；2,乏力；3,鼻塞流涕；4,咽痛；5,嗅觉/味觉减退；6,结膜炎；7,肌痛；8,腹泻；9,其他症状',
    `SYMPTOM`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '症状',
    `UPDATE_TIME`           datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `CREATE_TIME`           datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `SAMPLING_RESULT`       tinyint                                                       NULL DEFAULT NULL COMMENT '核酸检测结果:1 阳性 0 阴性',
    `STATUS`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT 'S0A' COMMENT '状态:S0X: 无效    S0A: 有效;',
    `REMARK`                varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`STAFF_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '工作人员健康检测记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_insomnia_questionnaire`
(
    `ID`                    bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `PERSON_CODE`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员一码通编码',
    `PERSON_NAME`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员名称',
    `FALL_SLEEP`            tinyint                                                      NULL     DEFAULT NULL COMMENT '入睡困难',
    `KEEP_SLEEP`            tinyint                                                      NULL     DEFAULT NULL COMMENT '睡眠维持困难',
    `WAKE_UP_EARLY`         tinyint                                                      NULL     DEFAULT NULL COMMENT '早醒',
    `SLEEP_DISSATISFACTION` tinyint                                                      NULL     DEFAULT NULL COMMENT '对目前睡眠不满意程度',
    `INSOMNIA_LIVE`         tinyint                                                      NULL     DEFAULT NULL COMMENT '失眠对生活的影响',
    `INSOMNIA_FROM_OTHER`   tinyint                                                      NULL     DEFAULT NULL COMMENT '他人眼中你的失眠情况',
    `WORRY_INSOMNIA`        tinyint                                                      NULL     DEFAULT NULL COMMENT '对失眠的担心/痛苦程度',
    `REGISTER_TIME`         datetime(0)                                                  NULL     DEFAULT NULL COMMENT '登记时间',
    `CREATE_TIME`           datetime(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '状态：S0A有效；S0X无效；',
    `SOURCE`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '数据来源',
    `SCORE`                 int                                                          NULL     DEFAULT NULL COMMENT '得分',
    `STAFF_PHONE`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员电话：代填时传入',
    `STAFF_NAME`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员名称：代填时传入',
    `MENTAL_ID`             bigint                                                       NULL     DEFAULT NULL COMMENT '关联心理量表主键',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员失眠调查'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_investigation_link`
(
    `ID`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `INVESTIGATION_ID` bigint                                                        NULL     DEFAULT NULL COMMENT '关联医学流调表ID',
    `PERSON_CODE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '一码通编码',
    `LINK_MAN`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人姓名',
    `LINK_PHONE`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人电话',
    `LINK_ADDRESS`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人住址',
    `RELATION`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '与留观人员关系',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源',
    `CREATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态：S0A有效；S0X无效',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员联系目录（医学流调）'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_medicine_collection`
(
    `ID`                         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '一码通编码',
    `PERSON_NAME`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员名称',
    `MEDICINE_COLLECTION_CODE`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '医学采集类型编码',
    `MEDICINE_COLLECTION_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '医学采集类型名称',
    `MEDICINE_COLLECTION_STATUS` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '医学采集状态',
    `REGISTER_USER`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '操作人员账号',
    `REGISTER_NAME`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '操作人员姓名',
    `REGISTER_TIME`              datetime(0)                                                   NULL     DEFAULT NULL COMMENT '采集时间',
    `CREATE_TIME`                datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `UPDATE_TIME`                datetime(0)                                                   NOT NULL COMMENT '更新时间',
    `STATUS`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `SOURCE`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源 ',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_REGISTER_TIME` (`REGISTER_TIME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观医学采集'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_model`
(
    `ID`           bigint                                                        NOT NULL COMMENT '主键',
    `CODE`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '模型编码',
    `MAPPING_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '映射类型',
    `MAPPING_CODE` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '映射编码',
    `GEO_JSON`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL COMMENT '坐标信息',
    `CREATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UQ_MODEL_CODE` (`CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '模型信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_nucleic_acid`
(
    `ID`                    bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`           bigint                                                       NULL DEFAULT NULL COMMENT '留观人员ID',
    `PERSON_CODE`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留观人员编码',
    `NUCLEIC_ACID_RESULT`   tinyint                                                      NULL DEFAULT NULL COMMENT '核酸检测结果 0:阴性  1:阳性',
    `SAMPLING_TIME`         datetime(0)                                                  NULL DEFAULT NULL COMMENT '采样时间',
    `SAMPLING_ORGANIZATION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采样机构',
    `SAMPLING_PERSON`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采样人员',
    `SAMPLING_TYPE`         tinyint                                                      NULL DEFAULT NULL COMMENT '采样类型',
    `TESTING_ORGANIZATION`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测机构/实验室',
    `RESULT_TIME`           datetime(0)                                                  NULL DEFAULT NULL COMMENT '检测/检出日期',
    `RESULT_REPORT_TIME`    datetime(0)                                                  NULL DEFAULT NULL COMMENT '检测结果填报时间',
    `IgM_RESULT`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抗体检测结果IgM',
    `IgG_RESULT`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抗体检测结果IgG',
    `TESTING_PERSON`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测人员',
    `CREATE_TIME`           datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UQ_PERSON_SAMPLING_TIME` (`PERSON_CODE`, `SAMPLING_TIME`) USING BTREE,
    INDEX `IDX_RESULT_TIME` (`RESULT_TIME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '核酸检测记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer`
(
    `ID`                         bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '人员ID',
    `UID`                        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '一码通UID',
    `HOTEL_ID`                   bigint                                                         NULL     DEFAULT NULL COMMENT '酒店ID',
    `PERSON_CODE`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '一码通编码',
    `NAME`                       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '姓名',
    `ALIAS_NAME`                 varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '别名',
    `FILE_JSON`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '人员照片',
    `SEX`                        tinyint                                                        NULL     DEFAULT NULL COMMENT '性别',
    `BIRTH_DATE`                 date                                                           NULL     DEFAULT NULL COMMENT '出身年月',
    `AGE`                        tinyint                                                        NULL     DEFAULT NULL COMMENT '年龄',
    `PERSON_FROM`                tinyint                                                        NULL     DEFAULT NULL COMMENT '人源:0普通乘客；1,机组人员；2,船员;3,本地发现隔离人员;',
    `NATIONALITY`                varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '国籍',
    `NATION`                     varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '民族',
    `IDENTITY_TYPE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '证件类型',
    `IDENTITY_NUMBER`            varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '证件号码',
    `IDENTITY_TYPE_COLLECT`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '采集证件类型',
    `IDENTITY_NUMBER_COLLECT`    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '采集证件号码',
    `ID_CARD`                    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '身份证号码',
    `TELPHONE`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '手机号码',
    `CIVIL`                      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '居住行政区划',
    `ADDRESS_DETAIL`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '居住地址详情',
    `NATIVE_PLACE`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '籍贯',
    `BIRTH_PLACE`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '户籍地址',
    `VACCINATION_STATUS`         tinyint                                                        NULL     DEFAULT NULL COMMENT '是否接种疫苗:0,未接种或未完成;1,已接种;',
    `VACCINATION_TIME`           datetime(0)                                                    NULL     DEFAULT NULL COMMENT '接种时间：完成接种节点',
    `OBSERVER_STATUS`            tinyint                                                        NULL     DEFAULT 1 COMMENT '留观状态：0,已留观结束;1,留观中;2,即将到站',
    `CREATE_TIME`                datetime(0)                                                    NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                datetime(0)                                                    NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT 'S0A' COMMENT '状态',
    `REMARK`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '备注',
    `ARRIVAL_TIME`               datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '抵达日期：入境日期（开始隔离时间）',
    `PASSPORT_ID`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '护照号码',
    `ENTRY_STATUS`               bigint                                                         NULL     DEFAULT NULL COMMENT '登车状态',
    `FOLLOWER_NUM`               bigint                                                         NULL     DEFAULT NULL COMMENT '同乘人数量',
    `TRAIN_TYPE`                 bigint                                                         NULL     DEFAULT NULL COMMENT '入境交通方式',
    `TRAIN_NUMBER`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '航班(船班/车次)号',
    `SEAT_NUMBER`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '座位号',
    `SAMPLING_RESULT`            tinyint                                                        NULL     DEFAULT NULL COMMENT '核酸采样结果',
    `RECENTLY_COUNTRY`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '过去14日内居住国家和地区,多个以|分隔',
    `TRANSIT_VEHICLE_NUMBER`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '转运车次号',
    `CAR_NUMBER`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '转运车牌号',
    `PROPOSED_HOTEL`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '拟入驻酒店',
    `IS_HUOMIAN`                 bigint                                                         NULL     DEFAULT NULL COMMENT '是否豁免白名单',
    `ENTRY_PLACE`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '入境口岸',
    `ENTRY_ID`                   int                                                            NULL     DEFAULT NULL COMMENT '入境口岸ID',
    `ENTRY_TYPE`                 bigint                                                         NULL     DEFAULT NULL COMMENT '出入境类型（通过类型）',
    `PROFESSION`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '职业',
    `NATIVE_LINKMAN_INFO`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '其它境内有效联系人及联系方式',
    `UNDERLYING_DISEASE`         tinyint                                                        NULL     DEFAULT NULL COMMENT '是否患有基础疾病',
    `INVESTIGATION_STATUS`       tinyint                                                        NULL     DEFAULT 0 COMMENT '医学流调：0,未上报;1,已上报;',
    `MEDICAL_STATE_STATUS`       tinyint                                                        NULL     DEFAULT 0 COMMENT '健康排查(身体状况):0,未上报;1,已上报；',
    `DESTINATION_DECLARE_STATUS` tinyint                                                        NULL     DEFAULT 0 COMMENT '目的地申报状态:0,未申报;1,待确认;2,符合;3,不符合',
    `DEPARTURE_COUNTRY`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '出发国家(来源国家)',
    `ENTRY_CITY`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '入境城市',
    `ROOM_TYPE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '选房意向：房型',
    `PLAN_DISMISSAL_TIME`        datetime(0)                                                    NULL     DEFAULT NULL COMMENT '计划解除隔离时间',
    `CHECKIN_REQ_SYNC_TIME`      datetime(0)                                                    NULL     DEFAULT NULL COMMENT '留观入住申请时间',
    `CLOSE_CONTACTS`             tinyint                                                        NULL     DEFAULT NULL COMMENT '密切接触者: 0 否;1 次密切接触者;2 密切接触者;3 一般入境人员',
    `RISK_MARK`                  tinyint                                                        NULL     DEFAULT 3 COMMENT '风险等级: 0 低风险;1 中风险;2 高风险;3 未知风险',
    `APPLY_FILE`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '申请文件：101，确认入住申请；102，解除隔离后7天集中健康监测申请文件；103，同住申请文件',
    `COUNTRY_OR_REGION`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '国家或地区：1,中国内地；2,中国港澳台地区；100,境外',
    `PASSPORT_IMAGE`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '护照证件照URL',
    `SOURCE`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '数据来源',
    `CREATE_PHONE`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '创建工作人员电话：工作人员代填的录入',
    `UPDATE_PHONE`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '更新工作人员电话：工作人员代填时传入',
    `RECENTLY_DOMESTIC`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '途经境内地区：多个逗号分隔',
    `SIGN_ALLOGRAPH`             tinyint                                                        NULL     DEFAULT NULL COMMENT '是否代签:0,自主签到;1,代签;',
    `ALLOGRAPH_EXTEND`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '代签扩展字段：JSON',
    `ROOM_CODE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新房间编码',
    `ROOM_NAME`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新房间名称',
    `FLOOR_NUMBER`               int                                                            NULL     DEFAULT NULL COMMENT '楼层',
    `BUILDING_CODE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新楼栋编码',
    `AREA_CODE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新区域编码',
    `NUCLEIC_ACID_RESULT`        tinyint                                                        NULL     DEFAULT NULL COMMENT '最新核酸结果',
    `NUCLEIC_ACID_RESULT_TIME`   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新核酸结果时间',
    `FOCUS_TYPE`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '重点人员类型',
    `PERSON_TYPE`                tinyint                                                        NULL     DEFAULT NULL COMMENT '人员类型',
    `IS_NEED_REFERRAL`           tinyint                                                        NULL     DEFAULT NULL COMMENT '是否需要转诊',
    `CHECK_IN_TIME`              datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新确认入住时间',
    `CHECK_OUT_TIME`             datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新离店时间',
    `BACK_ROUTE_STATUS`          tinyint                                                        NULL     DEFAULT 0 COMMENT '返程上报状态：0,未上报；1已上报',
    `DESTINATION_CITY`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '目的市',
    `DETAILS_DESTINATION`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '详情目的地',
    `DEPARTURE_TIME`             datetime(0)                                                    NULL     DEFAULT NULL COMMENT '本土:发车时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_STATUS_OBSERVER_STATUS` (`STATUS`, `OBSERVER_STATUS`) USING BTREE,
    INDEX `IDX_POPULATION_IDENTITYTYPE_IDENTITYNUMBER` (`IDENTITY_NUMBER`, `IDENTITY_TYPE`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_board`
(
    `ID`                bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `PLATE_NUMBER`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '车牌号',
    `TRAIN_CODE`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '车次号',
    `AUTH_ID`           bigint                                                        NULL     DEFAULT NULL COMMENT '分流员认证ID',
    `AUTH_NAME`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '分流员认证姓名',
    `AUTH_PHONE`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '分流员认证手机号',
    `DRIVER_NAME`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '司机姓名',
    `DRIVER_PHONE`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '司机手机号',
    `FOLLOWER_NAME`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '跟车人姓名',
    `FOLLOWER_PHONE`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '跟车人手机号',
    `FOLLOWER_DISTRICT` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '跟车人归属地',
    `HOTEL_ID`          bigint                                                        NULL     DEFAULT NULL COMMENT '酒店ID',
    `HOTEL_NAME`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '酒店名称',
    `HOTEL_IDS`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '酒店ID(2)',
    `HOTEL_NAMES`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '酒店名称(2)',
    `ENTRY_COUNT`       bigint                                                        NULL     DEFAULT NULL COMMENT '登车人数',
    `DEPART_STATUS`     bigint                                                        NULL     DEFAULT NULL COMMENT '发车状态',
    `DEPART_TIME`       datetime(0)                                                   NULL     DEFAULT NULL COMMENT '发车时间',
    `PASS_STATUS`       bigint                                                        NULL     DEFAULT NULL COMMENT '车次被转运状态',
    `S_GUID`            bigint                                                        NULL     DEFAULT NULL COMMENT 'S_GUID',
    `CREATE_TIME`       datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`       datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态：S0A有效；S0X无效；',
    `TARGET_TYPE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '送往地点类型',
    `TARGET_PORT_ID`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '送往口岸ID',
    `TARGET_PORT_NAME`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '送往口岸名称',
    `TARGET_PLACE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '目标地点',
    `DEPART_PLACE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '出发地点',
    `SOURCE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_TRAIN_CODE` (`TRAIN_CODE`) USING BTREE COMMENT '转运车次编码'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员车次表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_circulation`
(
    `ID`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`    bigint                                                        NULL DEFAULT NULL COMMENT '留观记录ID',
    `HOTEL_ID`       bigint                                                        NULL DEFAULT NULL COMMENT '酒店ID',
    `PERSON_CODE`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '人员编码',
    `LINK_TYPE`      bigint                                                        NULL DEFAULT NULL COMMENT '环节类型1:过海关;2:过边检;3:流调提交;4:转入酒店（开始留观）;5:转出酒店（解除留观）;6:社区目的地变更;7:扫码登车;8:住宿房间变更;9:旅客目的地审核变更;10:七天居家健康管理符合条件;11:七天居家健康管理不符合条件;12:旅客申报目的地;13:临时送医（转出）;14:临时送医（转入）;15:离店送医（解除留观）;16:确诊送医（解除留观）',
    `OPERATION_TIME` datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '操作时间',
    `OPERATOR`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作人',
    `SOURCE_AREA`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源地',
    `PURPOSE_AREA`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的地',
    `CREATE_TIME`    datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `STATUS`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态',
    `REMARK`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `AREA_CODE`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `BUILDING_CODE`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `ROOM_CODE`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员流转状态'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_copy1`
(
    `ID`                         bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '人员ID',
    `UID`                        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '一码通UID',
    `HOTEL_ID`                   bigint                                                         NULL     DEFAULT NULL COMMENT '酒店ID',
    `PERSON_CODE`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '一码通编码',
    `NAME`                       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '姓名',
    `ALIAS_NAME`                 varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '别名',
    `FILE_JSON`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '人员照片',
    `SEX`                        tinyint                                                        NULL     DEFAULT NULL COMMENT '性别',
    `BIRTH_DATE`                 date                                                           NULL     DEFAULT NULL COMMENT '出身年月',
    `AGE`                        tinyint                                                        NULL     DEFAULT NULL COMMENT '年龄',
    `PERSON_FROM`                tinyint                                                        NULL     DEFAULT NULL COMMENT '人源:0普通乘客；1,机组人员；2,船员;3,本地发现隔离人员;',
    `NATIONALITY`                varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '国籍',
    `NATION`                     varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '民族',
    `IDENTITY_TYPE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '证件类型',
    `IDENTITY_NUMBER`            varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '证件号码',
    `IDENTITY_TYPE_COLLECT`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '采集证件类型',
    `IDENTITY_NUMBER_COLLECT`    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '采集证件号码',
    `ID_CARD`                    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '身份证号码',
    `TELPHONE`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '手机号码',
    `CIVIL`                      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '居住行政区划',
    `ADDRESS_DETAIL`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '居住地址详情',
    `NATIVE_PLACE`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '籍贯',
    `BIRTH_PLACE`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '户籍地址',
    `VACCINATION_STATUS`         tinyint                                                        NULL     DEFAULT NULL COMMENT '是否接种疫苗:0,未接种或未完成;1,已接种;',
    `VACCINATION_TIME`           datetime(0)                                                    NULL     DEFAULT NULL COMMENT '接种时间：完成接种节点',
    `OBSERVER_STATUS`            tinyint                                                        NULL     DEFAULT 1 COMMENT '留观状态：0,已留观结束;1,留观中;2,即将到站',
    `CREATE_TIME`                datetime(0)                                                    NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                datetime(0)                                                    NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT 'S0A' COMMENT '状态',
    `REMARK`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '备注',
    `ARRIVAL_TIME`               datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '抵达日期：入境日期（开始隔离时间）',
    `PASSPORT_ID`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '护照号码',
    `ENTRY_STATUS`               bigint                                                         NULL     DEFAULT NULL COMMENT '登车状态',
    `FOLLOWER_NUM`               bigint                                                         NULL     DEFAULT NULL COMMENT '同乘人数量',
    `TRAIN_TYPE`                 bigint                                                         NULL     DEFAULT NULL COMMENT '入境交通方式',
    `TRAIN_NUMBER`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '航班(船班/车次)号',
    `SEAT_NUMBER`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '座位号',
    `SAMPLING_RESULT`            tinyint                                                        NULL     DEFAULT NULL COMMENT '核酸采样结果',
    `RECENTLY_COUNTRY`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '过去14日内居住国家和地区,多个以|分隔',
    `TRANSIT_VEHICLE_NUMBER`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '转运车次号',
    `CAR_NUMBER`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '转运车牌号',
    `PROPOSED_HOTEL`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '拟入驻酒店',
    `IS_HUOMIAN`                 bigint                                                         NULL     DEFAULT NULL COMMENT '是否豁免白名单',
    `ENTRY_PLACE`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '入境口岸',
    `ENTRY_ID`                   int                                                            NULL     DEFAULT NULL COMMENT '入境口岸ID',
    `ENTRY_TYPE`                 bigint                                                         NULL     DEFAULT NULL COMMENT '出入境类型（通过类型）',
    `PROFESSION`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '职业',
    `NATIVE_LINKMAN_INFO`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '其它境内有效联系人及联系方式',
    `UNDERLYING_DISEASE`         tinyint                                                        NULL     DEFAULT NULL COMMENT '是否患有基础疾病',
    `INVESTIGATION_STATUS`       tinyint                                                        NULL     DEFAULT 0 COMMENT '医学流调：0,未上报;1,已上报;',
    `MEDICAL_STATE_STATUS`       tinyint                                                        NULL     DEFAULT 0 COMMENT '健康排查(身体状况):0,未上报;1,已上报；',
    `DESTINATION_DECLARE_STATUS` tinyint                                                        NULL     DEFAULT 0 COMMENT '目的地申报状态:0,未申报;1,待确认;2,符合;3,不符合',
    `DEPARTURE_COUNTRY`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '出发国家(来源国家)',
    `ENTRY_CITY`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '入境城市',
    `ROOM_TYPE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '选房意向：房型',
    `PLAN_DISMISSAL_TIME`        datetime(0)                                                    NULL     DEFAULT NULL COMMENT '计划解除隔离时间',
    `CHECKIN_REQ_SYNC_TIME`      datetime(0)                                                    NULL     DEFAULT NULL COMMENT '留观入住申请时间',
    `CLOSE_CONTACTS`             tinyint                                                        NULL     DEFAULT NULL COMMENT '密切接触者: 0 否;1 次密切接触者;2 密切接触者;3 一般入境人员',
    `RISK_MARK`                  tinyint                                                        NULL     DEFAULT 3 COMMENT '风险等级: 0 低风险;1 中风险;2 高风险;3 未知风险',
    `APPLY_FILE`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '申请文件：101，确认入住申请；102，解除隔离后7天集中健康监测申请文件；103，同住申请文件',
    `COUNTRY_OR_REGION`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '国家或地区：1,中国内地；2,中国港澳台地区；100,境外',
    `PASSPORT_IMAGE`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '护照证件照URL',
    `SOURCE`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '数据来源',
    `CREATE_PHONE`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '创建工作人员电话：工作人员代填的录入',
    `UPDATE_PHONE`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '更新工作人员电话：工作人员代填时传入',
    `RECENTLY_DOMESTIC`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '途经境内地区：多个逗号分隔',
    `SIGN_ALLOGRAPH`             tinyint                                                        NULL     DEFAULT NULL COMMENT '是否代签:0,自主签到;1,代签;',
    `ALLOGRAPH_EXTEND`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '代签扩展字段：JSON',
    `ROOM_CODE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新房间编码',
    `ROOM_NAME`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新房间名称',
    `BUILDING_CODE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新楼栋编码',
    `AREA_CODE`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '最新区域编码',
    `NUCLEIC_ACID_RESULT`        tinyint                                                        NULL     DEFAULT NULL COMMENT '最新核酸结果',
    `NUCLEIC_ACID_RESULT_TIME`   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新核酸结果时间',
    `FOCUS_TYPE`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '重点人员类型',
    `IS_NEED_REFERRAL`           tinyint                                                        NULL     DEFAULT NULL COMMENT '是否需要转诊',
    `CHECK_IN_TIME`              datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新确认入住时间',
    `CHECK_OUT_TIME`             datetime(0)                                                    NULL     DEFAULT NULL COMMENT '最新离店时间',
    `BACK_ROUTE_STATUS`          tinyint                                                        NULL     DEFAULT 0 COMMENT '返程上报状态：0,未上报；1已上报',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_STATUS_OBSERVER_STATUS` (`STATUS`, `OBSERVER_STATUS`) USING BTREE,
    INDEX `IDX_POPULATION_IDENTITYTYPE_IDENTITYNUMBER` (`IDENTITY_NUMBER`, `IDENTITY_TYPE`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_destination`
(
    `ID`                   bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`          bigint                                                         NULL DEFAULT NULL COMMENT '留观ID',
    `DST_PROVINCE_CUSTOMS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地省（海关）',
    `DST_CITY_CUSTOMS`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地市（海关）',
    `DST_AREA_CUSTOMS`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地区县（海关）',
    `DST_STREET_CUSTOMS`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地街道（海关）',
    `DST_ADDRESS_CUSTOMS`  varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的地地址（海关）',
    `DST_PROVINCE_YMT`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地省（一码通）',
    `DST_CITY_YMT`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地市（一码通）',
    `DST_AREA_YMT`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地区县（一码通）',
    `DST_CITY_CODE_YMT`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地城市编码（一码通）',
    `DST_STREET_YMT`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地街道（一码通）',
    `DST_STREET_CODE_YMT`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '目的地街道编码（一码通）',
    `DST_ADDRESS_YMT`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的地地址（一码通）',
    `LEAVE_TYPE`           tinyint                                                        NULL DEFAULT NULL COMMENT '离开方式（一码通）',
    `LEAVE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '离开时间',
    `DST_APPLY_STATUS`     tinyint                                                        NULL DEFAULT NULL COMMENT '目的地申报状态（一码通）',
    `CREATE_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `UQ_OBSERVER_ID` (`OBSERVER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员目的地'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_observer_focus_check_up`
(
    `ID`                 bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '人员编码',
    `OBSERVER_ID`        bigint                                                        NULL DEFAULT NULL COMMENT '留观记录id',
    `CHECK_UP_DATE`      date                                                          NULL DEFAULT NULL COMMENT '诊察日期',
    `CHECK_UP_STATUS`    tinyint                                                       NULL DEFAULT NULL COMMENT '诊察状态',
    `SYMPTOM`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '症状',
    `TEMPERATURE`        double                                                        NULL DEFAULT NULL COMMENT '体温',
    `PULSE`              int                                                           NULL DEFAULT NULL COMMENT '脉搏',
    `HEART_RATE`         int                                                           NULL DEFAULT NULL COMMENT '心跳',
    `BLOOD_PRESSURE`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '血压',
    `DIAGNOSIS`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '诊断',
    `DEAL`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '处理',
    `SECOND`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '二诊情况',
    `THIRD`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '三诊情况',
    `FIRST_DOCTOR_SIGN`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '首诊处理医生签名',
    `SECOND_DOCTOR_SIGN` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '二诊处理医生签名',
    `THIRD_DOCTOR_SIGN`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '三诊处理医生签名',
    `REMARK`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '备注',
    `CREATE_TIME`        datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`        datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UQ_OBSERVER_ID_CHECK_UP_DATE` (`OBSERVER_ID`, `CHECK_UP_DATE`) USING BTREE,
    INDEX `IDX_CHECK_UP_DATE` (`CHECK_UP_DATE`) USING BTREE,
    INDEX `IDX_OBSERVER_ID` (`OBSERVER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '重点留观人员巡查表'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_observer_investigation`
(
    `ID`                                   bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `PERSON_CODE`                          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '留观人员一码通',
    `PERSON_NAME`                          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '留观人员名称',
    `CONTACT_CONDITION`                    tinyint                                                 NULL DEFAULT NULL COMMENT '接触情况',
    `CONTACT_DETAIL`                       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '其他接触类型描述',
    `BASE_SICKNESS`                        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '基础性疾病：逗号分隔',
    `BASE_SICKNESS_DETAIL`                 varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '其他基础性疾病:详情',
    `FIRST_CONTACT_TIME`                   datetime(0)                                             NULL DEFAULT NULL COMMENT '首次接触日期',
    `LAST_CONTACT_TIME`                    datetime(0)                                             NULL DEFAULT NULL COMMENT '末次接触日期',
    `CONTACT_WAY`                          tinyint                                                 NULL DEFAULT NULL COMMENT '接触方式',
    `CONTACT_WAY_DETAIL`                   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '接触方式其他描述',
    `SEPERATE_BEGIN`                       datetime(0)                                             NULL DEFAULT NULL COMMENT '开始隔离时间',
    `SEPERATE_END`                         datetime(0)                                             NULL DEFAULT NULL COMMENT '出院/解除隔离日期',
    `IS_CLINICAL_SYMPTOMS`                 tinyint                                                 NULL DEFAULT NULL COMMENT '是否出现临床症状 1:是 0否',
    `FIRST_SYMPTOMS_TIME`                  datetime(0)                                             NULL DEFAULT NULL COMMENT '首次出现症状日期',
    `MANIPULATION_CLINICAL_MANIFESTATIONS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '首发临床表现:逗号分隔',
    `CLINICAL_MANIFESTATIONS_DETAIL`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '其他临床表现描述',
    `IS_LAST_POSITIVE`                     tinyint                                                 NULL DEFAULT NULL COMMENT '最终检测结果是否阳性:类型',
    `NUCLEATE_POSITIVE_TIME`               datetime(0)                                             NULL DEFAULT NULL COMMENT '核酸检测阳性标本采集日期',
    `FINAL_CLINICAL_RESOLUTION`            tinyint                                                 NULL DEFAULT NULL COMMENT '最终临床结局',
    `CREATOR`                              varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '创建人',
    `UPDATE_USER`                          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '更新人',
    `GRID_CODE`                            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '网格编码',
    `CREATE_TIME`                          datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`                          datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `STATUS`                               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT 'S0A' COMMENT '状态:S0A有效；S0X无效；',
    `SOURCE`                               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '数据来源',
    `INVESTIGATION_STATUS`                 tinyint                                                 NULL DEFAULT NULL COMMENT '医学流调状态',
    `CREATE_PHONE`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '创建工作人员电话：代填时传入',
    `UPDATE_PHONE`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '更新工作人员电话：代填时传入',
    `TEN_SYMPTOMS`                         varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '十大症状：逗号分隔',
    `TEMPERATURE_STATUS`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '体温情况',
    `ACUTE_MEDICAL_HISTORY`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '急症病史：多值逗号间隔',
    `ACUTE_MEDICAL_HISTORY_DETAIL`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '急症病史：其他手填情况',
    `TAKE_MEDICINE_HISTORY`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '服药史：自填',
    `DEPARTURE_COUNTRY`                    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '出发国家/来源地',
    `DEPARTURE_COUNTRY_CODE`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '出发国家/来源地编码',
    `DEPARTURE_ADDRESS`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '出发国家/来源地详址',
    `ARRIVAL_TRAIN_TYPE`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '入境方式',
    `ARRIVAL_CITY`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '入境城市',
    `ARRIVAL_PORT`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '入境口岸',
    `TRAIN_NUMBER`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '航班号:航班/船舶/车次号',
    `SEAT_NUMBER`                          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '座位号',
    `DESTINATION_CITY`                     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '目的地：省市区',
    `DESTINATION_CITY_CODE`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NULL DEFAULT NULL COMMENT '目的地：省市区编码',
    `DST_ADDRESS`                          text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '目的地详细地址',
    `RECENTLY_COUNTRY`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '过去14日内居住国家和地区,多个以|分隔',
    `RECENTLY_DOMESTIC`                    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin      NULL COMMENT '过去14日途经境内地区：多个逗号分隔',
    `RECENT_SITUATIONS`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '近14日情况：多个以逗号分隔',
    `MORE_BASE_SICKNESS`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL COMMENT '更多基础性疾病：逗号分隔',
    `TRANSPORTATION`                       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '交通工具/交通方式',
    `LINK_JSON`                            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin      NULL COMMENT 'JSON数组：联系人目录',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '留观人员医学流调'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_link`
(
    `ID`               bigint(20) UNSIGNED ZEROFILL                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `MEDICAL_STATE_ID` bigint                                                        NULL     DEFAULT NULL COMMENT '关联健康排查表ID',
    `PERSON_CODE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '一码通编码',
    `LINK_MAN`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人姓名',
    `LINK_PHONE`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人电话',
    `LINK_ADDRESS`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '联系人住址',
    `RELATION`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '与留观人员关系',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源',
    `CREATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态：S0A有效；S0X无效',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员联系目录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_medical_state`
(
    `ID`                            bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `PERSON_CODE`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '一码通编码',
    `PERSON_NAME`                   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '留观人员名称',
    `SOURCE`                        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '数据来源',
    `HAS_HYPERTENSION`              tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有高血压：0,否;1,是；',
    `HAS_HYPERGLYCEMIA`             tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有高血糖：0,否;1,是；',
    `HAS_HYPERLIPEMIA`              tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有高血脂：0,否;1,是；',
    `HAS_CARDIOPATHY`               tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有心脏病：0,否;1,是；',
    `HAS_EPILEPSY`                  tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有癫痫病：0,否;1,是；',
    `HAS_HOLDER`                    tinyint                                                        NULL     DEFAULT NULL COMMENT '是否安装支架: 0,否;1,是；',
    `HAS_BRIDGING`                  tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有搭桥: 0,否;1,是；',
    `HAS_CEREBRAL_INFARCTION`       tinyint                                                        NULL     DEFAULT NULL COMMENT '是否有脑梗病史：0,否;1,是；',
    `MENTAL_DISORDER`               tinyint                                                        NULL     DEFAULT NULL COMMENT '精神类疾病',
    `CRD`                           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '慢性呼吸系统疾病:逗号分割',
    `HAS_CONTAGION`                 tinyint                                                        NULL     DEFAULT NULL COMMENT '疟疾等重点传染病患病同史:0,否;1,是；',
    `CONTAGION_TIME`                datetime(0)                                                    NULL     DEFAULT NULL COMMENT '患病时间：疟疾等重点传染病患病',
    `CONTAGION_DETAIL`              longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '疟疾等重点传染病患病同史：详情',
    `HAS_TRAUMA`                    tinyint                                                        NULL     DEFAULT NULL COMMENT '近1月外伤史:0,否;1,是；',
    `TRAUMA_DETAIL`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '近1月外伤史:详情',
    `RELATIVES_DEAD_ILLNESS`        tinyint                                                        NULL     DEFAULT NULL COMMENT '亲属中50岁以前因病死亡情况：0,无,1,有；',
    `RELATIVES_DEAD_ILLNESS_DETAIL` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '亲属中50岁以前因病死亡情况：详情',
    `SMOKING_QUANTITY`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '抽烟及数量：包/天',
    `DRINK_QUANTITY`                int                                                            NULL     DEFAULT NULL COMMENT '饮酒及数量：两/天',
    `HAS_SURGERY`                   tinyint                                                        NULL     DEFAULT NULL COMMENT '有无既往手术史:0,否;1,是；',
    `SURGERY_DETAIL`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '手术史',
    `HAS_IRRITABILITY`              tinyint                                                        NULL     DEFAULT NULL COMMENT '有无食物或药物过敏',
    `IRRITABILITY_DETAIL`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '食物或药物过敏详情',
    `HAS_PREGNANCY`                 tinyint                                                        NULL     DEFAULT NULL COMMENT '有无怀孕:0,否;1,是；',
    `CARRY_MEDICATION`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '携带何种药物',
    `MOOD`                          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '目前心情如何',
    `SLEEP`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '睡眠情况如何',
    `TENSION_IN_HOTEL`              tinyint                                                        NULL     DEFAULT NULL COMMENT '入住隔离酒店是否让您紧张',
    `REGISTER_TIME`                 datetime(0)                                                    NULL     DEFAULT NULL COMMENT '登记时间',
    `REGISTER_NAME`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '填写人员名称',
    `STAFF_PHONE`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '工作人员电话：代填时传入',
    `STAFF_ID`                      bigint                                                         NULL     DEFAULT NULL COMMENT '工作人员ID',
    `STAFF_NAME`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '工作人员名称',
    `INSPECT_TIME`                  datetime(0)                                                    NULL     DEFAULT NULL COMMENT '核查时间',
    `CREATE_TIME`                   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A有效；S0X无效',
    `ATTENTION_ILLNESS`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '其他需重点关注疾病或情况',
    `NASOPHARYNX_HISTORY`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '鼻咽相关病史：多个以逗号隔开',
    `NASOPHARYNX_HISTORY_DETAIL`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '鼻咽相关病史详情：存在其他时文本输入',
    `HAS_ATTENTION_ILLNESS`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '有无其他需要重点关注疾病或情况',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员排查隔离登记(身体状况)'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_record`
(
    `ID`                          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`                 bigint                                                        NULL DEFAULT NULL COMMENT '留观记录id',
    `PERSON_CODE`                 varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留观人员编码',
    `FOCUS_TYPE`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员重点检查类型',
    `FIRST_DEAL_TIME`             datetime(0)                                                   NULL DEFAULT NULL COMMENT '首次处理时间',
    `PAST_HISTORY`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '既往史',
    `CHRONIC_DISEASES_MEDICATION` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '慢性病服药情况',
    `DEAL_PROBLEM_TYPE`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理问题类别',
    `IS_NEED_REFERRAL`            tinyint                                                       NULL DEFAULT NULL COMMENT '是否需要转诊',
    `REFERRAL_TIME`               datetime(0)                                                   NULL DEFAULT NULL COMMENT '转诊时间',
    `CREATE_TIME`                 datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                 datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`PERSON_CODE`) USING BTREE,
    INDEX `IDX_OBSERVER_ID` (`OBSERVER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观记录表'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_observer_task`
(
    `ID`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`          bigint                                                        NULL     DEFAULT NULL,
    `PERSON_CODE`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员编码',
    `PERSON_NAME`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员名称',
    `ROOM_CODE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '房间编号',
    `ROOM_NAME`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '房间名称',
    `FLOOR_NUMBER`         int                                                           NULL     DEFAULT NULL COMMENT '楼层',
    `ARRIVAL_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '开始隔离时间',
    `TASK_TYPE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '任务类型：编码',
    `TASK_FULL_NAME`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '任务类型：全称',
    `BEGIN_TIME`           timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '计划开始时间',
    `END_TIME`             timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '计划结束时间',
    `TASK_STATUS`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0W' COMMENT '任务状态(S0W:待处理, S0P:处理中, S0C:处理完成 )',
    `EXEC_TIME`            timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '执行时间',
    `EXEC_BY`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采集人',
    `SOURCE`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采集来源:数据来源',
    `CREATE_TIME`          timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `RELATION_TABLES`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '关联填报信息主键',
    `REMARK`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '备注',
    `AREA_CODE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '区域编码',
    `BUILDING_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '楼宇编码',
    `RESULT`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '结果：正常；异常；',
    `EXPORT_DATE`          date                                                          NULL     DEFAULT NULL COMMENT '导出日期(批次号)',
    `REMAIN_STATUS`        tinyint                                                       NULL     DEFAULT NULL COMMENT '剩余时间状态：true，正常；false，异常（超时）',
    `TOP_TASK_TYPE`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '顶级任务类型',
    `OBSERVING_DAYS_INDEX` int                                                           NULL     DEFAULT NULL COMMENT '留观第几天',
    `SCHEMA_CODE`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '任务编码',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员日常任务记录表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_task_20211228_xys测试`
(
    `ID`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`          bigint                                                        NULL     DEFAULT NULL,
    `PERSON_CODE`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员编码',
    `PERSON_NAME`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员名称',
    `ROOM_CODE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '房间编号',
    `ROOM_NAME`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '房间名称',
    `FLOOR_NUMBER`         int                                                           NULL     DEFAULT NULL COMMENT '楼层',
    `ARRIVAL_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '开始隔离时间',
    `TASK_TYPE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '任务类型：编码',
    `TASK_FULL_NAME`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '任务类型：全称',
    `BEGIN_TIME`           timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '计划开始时间',
    `END_TIME`             timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '计划结束时间',
    `TASK_STATUS`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0W' COMMENT '任务状态(S0W:待处理, S0P:处理中, S0C:处理完成 )',
    `EXEC_TIME`            timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '执行时间',
    `EXEC_BY`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采集人',
    `SOURCE`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采集来源:数据来源',
    `CREATE_TIME`          timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          timestamp(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `RELATION_TABLES`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '关联填报信息主键',
    `REMARK`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '备注',
    `AREA_CODE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '区域编码',
    `BUILDING_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '楼宇编码',
    `RESULT`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '结果：正常；异常；',
    `EXPORT_DATE`          date                                                          NULL     DEFAULT NULL COMMENT '导出日期(批次号)',
    `REMAIN_STATUS`        tinyint                                                       NULL     DEFAULT NULL COMMENT '剩余时间状态：true，正常；false，异常（超时）',
    `TOP_TASK_TYPE`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '顶级任务类型',
    `OBSERVING_DAYS_INDEX` int                                                           NULL     DEFAULT NULL COMMENT '留观第几天',
    `SCHEMA_CODE`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '任务编码',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员日常任务记录表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_task_schema`
(
    `ID`          bigint                                                 NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID` bigint                                                 NULL DEFAULT NULL COMMENT '留观人员编码',
    `TASK_TYPE`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '101:核酸采集,102:流调,103:健康排查,104:目的地申报',
    `SCHEMA_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板编码',
    `CREATE_TIME` datetime(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '留观人员与任务模板绑定表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_observer_vaccine`
(
    `ID`                     bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员一码通',
    `PERSON_NAME`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员名称',
    `VACCINE_NAME`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '疫苗名称',
    `MANUFACTURER`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '厂家名称',
    `BATCH_CODE`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '批号',
    `INOCULATE_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '接种时间',
    `INOCULATE_ORGANIZATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '接种单位',
    `INOCULATE_NUM`          int                                                           NULL     DEFAULT NULL COMMENT '接种针次',
    `FINISHED`               tinyint                                                       NULL     DEFAULT NULL COMMENT '是否完成接种疫苗:0,否；1,是；',
    `CREATE_TIME`            datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A,有效；S0X无效；',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE,
    INDEX `IDX_FINISHED` (`FINISHED`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员接种疫苗记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_psychological`
(
    `ID`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`          bigint                                                        NULL DEFAULT NULL COMMENT '留观ID',
    `PERSON_CODE`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '留观人员编码',
    `PERSON_NAME`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '留观人员名称',
    `OBSERVE_TYPE`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '医学观察类型',
    `OBSERVE_ATTRIBUTE`    tinyint                                                       NULL DEFAULT NULL COMMENT '对医学观察的态度：0:完全理解 1:部分理解 2:不理解',
    `PSYCHOLOGICAL_STATUS` tinyint                                                       NULL DEFAULT NULL COMMENT '精神心理状态/心理现况：0:未知 1:正常 2:轻度异常 3:中度异常 4:重度异常',
    `PSYCHOLOGICAL_DESC`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '精神心理描述',
    `AUTOTOMY_STATUS`      tinyint                                                       NULL DEFAULT NULL COMMENT '自伤自杀风险：0:无明显自伤自杀风险；1:有自伤自杀观念；2:有...企图 3:有...行为',
    `REGISTER_ID`          bigint                                                        NULL DEFAULT NULL COMMENT '操作人员ID',
    `REGISTER_NAME`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作人员姓名',
    `REGISTER_TIME`        datetime(0)                                                   NULL DEFAULT NULL COMMENT '操作时间',
    `CREATE_TIME`          datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`               varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `SOURCE`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据来源',
    `RESULT`               tinyint                                                       NULL DEFAULT NULL COMMENT '诊断结果:0:未知 1:正常 2:轻度异常 3:中度异常 4:重度异常',
    `REPORT_TYPE`          tinyint                                                       NULL DEFAULT NULL COMMENT '上报渠道：0:驻点医务人员；1:酒店工作人员',
    `HISTORY`              longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '既往史（躯体疾病史、精神疾病史、自杀伤害史、物质成瘾史、重大应激事件）',
    `MENTAL_ID`            bigint                                                        NULL DEFAULT NULL COMMENT '关联心理量表主键',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '心理健康评估'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_psychological_questionnaire`
(
    `ID`                   bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `PERSON_CODE`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员一码通编码',
    `PERSON_NAME`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '留观人员名称',
    `INTEREST_DOING`       tinyint                                                      NULL     DEFAULT NULL COMMENT '做事时有没兴趣或乐趣',
    `MOOD_ABNORMAL`        tinyint                                                      NULL     DEFAULT NULL COMMENT '感到心情低落、沮丧或绝望',
    `SLEEP_ABNORMAL`       tinyint                                                      NULL     DEFAULT NULL COMMENT '入睡困难、易醒或睡眠过多',
    `TIRED`                tinyint                                                      NULL     DEFAULT NULL COMMENT '感到疲倦或没有精力',
    `APPETITE_ABNORMAL`    tinyint                                                      NULL     DEFAULT NULL COMMENT '食欲不振或吃得过多',
    `TERRIBLE_SELF`        tinyint                                                      NULL     DEFAULT NULL COMMENT '觉得自己很糟或自己很失败，或让自己或家人失望',
    `DIFFCULT_CONCENTRATE` tinyint                                                      NULL     DEFAULT NULL COMMENT '做事情很难专注、例如读报纸或看电视',
    `ACTION_ABNORMAL`      tinyint                                                      NULL     DEFAULT NULL COMMENT '行动或说话速度缓慢到别人已经觉察到，或刚好相反—变得比平日更烦躁或坐立不安，以至于走来走去比平常多很多',
    `HURTING_SELF`         tinyint                                                      NULL     DEFAULT NULL COMMENT '有不如死掉的想法，或以某种方式伤害自己的念头',
    `REGISTER_TIME`        datetime(0)                                                  NULL     DEFAULT NULL COMMENT '填问卷时间',
    `CREATE_TIME`          datetime(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          datetime(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A有效;S0X无效',
    `SOURCE`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '数据来源',
    `SCORE`                int                                                          NULL     DEFAULT NULL COMMENT '得分',
    `STAFF_PHONE`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员电话：代填时传入',
    `STAFF_NAME`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员名称：代填时传入',
    `MENTAL_ID`            bigint                                                       NULL     DEFAULT NULL COMMENT '关联心理量表主键',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观人员心理健康问卷'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_room`
(
    `ID`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ROOM_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '房间号, 实际门牌号码',
    `ROOM_NAME`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房间名称',
    `FLOOR_NUMBER`     int                                                           NULL DEFAULT NULL COMMENT '楼层',
    `ROOM_TYPE`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房间类型',
    `ROOM_LEVEL`       tinyint                                                       NULL DEFAULT NULL COMMENT '房间等级',
    `GEO_JSON`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '坐标信息',
    `PLAN_JSON`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '结构平面示意图',
    `TOTAL_AREA`       double                                                        NULL DEFAULT NULL COMMENT '总面积',
    `HOTEL_ID`         bigint                                                        NULL DEFAULT NULL COMMENT '酒店ID',
    `AREA_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '场所区域编码',
    `BUILDING_CODE`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '楼栋编码',
    `CREATE_TIME`      datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态:S0X,无效;S0A,有效;',
    `REMARK`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `DISINFECT_STATUS` tinyint                                                       NULL DEFAULT NULL COMMENT '消杀状态：0 未消杀； 1 已消杀',
    `DISINFECT_DATE`   date                                                          NULL DEFAULT NULL COMMENT '最新消杀时间',
    `DUTY_NAME`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '负责人姓名',
    `DUTY_PHONE`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '负责人电话',
    `MODEL_CODE`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '三景模型编码',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `AK_IDX_ROOM_CODE` (`ROOM_CODE`) USING BTREE,
    INDEX `IDX_AREA_CODE` (`AREA_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '房间信息'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `flyway_test`.`basedata_room_observer`
(
    `ID`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '人员编码',
    `ROOM_CODE`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间编号',
    `OBSERVER_ID`       bigint                                                         NULL DEFAULT NULL COMMENT '留观记录id',
    `HOTEL_ID`          bigint                                                         NULL DEFAULT NULL COMMENT '酒店ID',
    `ROOM_NAME`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间名称',
    `FLOOR_NUMBER`      int                                                            NULL DEFAULT NULL COMMENT '楼层',
    `CHECK_IN`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '入住时间',
    `CHECK_OUT`         datetime(0)                                                    NULL DEFAULT NULL COMMENT '离开时间',
    `INTAKE_INFO`       varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入住信息',
    `INTAKE_STATUS`     tinyint                                                        NULL DEFAULT 1 COMMENT '入住状态:0,未入住;  1,已入住; 2,即将解除留观; 3,已离宿',
    `REGISTER_ID`       bigint                                                         NULL DEFAULT NULL COMMENT '办理人员ID',
    `REGISTER_NAME`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '办理人员姓名',
    `REGISTER_TIME`     datetime(0)                                                    NULL DEFAULT NULL COMMENT '办理时间',
    `CREATE_TIME`       datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`       datetime(0)                                                    NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `REMARK`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '备注',
    `NOTIFICATION_FILE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '入住告知书文件url',
    `PAY_TYPE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT 'OnlinePay：线上、CardPay：银行卡支付、CashPay：现金或转账、OtherPay：其他',
    `MEALS_TYPES`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '餐类：CG :常规, QZ:清真',
    `ORDER_ID`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '预订单号',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_ROOM_ROOM_CODE` (`ROOM_CODE`) USING BTREE,
    INDEX `IDX_INTAKE_STATUS` (`INTAKE_STATUS`, `STATUS`) USING BTREE,
    INDEX `IDX_PERSON_CODE` (`PERSON_CODE`) USING BTREE,
    INDEX `IDX_OBSERVER_ID` (`OBSERVER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '人(留观)房关系'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_room_staff`
(
    `ID`            bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`      bigint                                                         NOT NULL COMMENT '工作人员id',
    `ROOM_CODE`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间编号',
    `ROOM_NAME`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间名称',
    `BUILDING_CODE` bigint                                                         NULL DEFAULT NULL COMMENT '楼栋编码',
    `CHECK_IN`      datetime(0)                                                    NULL DEFAULT NULL COMMENT '入住时间',
    `CHECK_OUT`     datetime(0)                                                    NULL DEFAULT NULL COMMENT '离开时间',
    `INTAKE_INFO`   varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入住信息',
    `INTAKE_STATUS` tinyint                                                        NULL DEFAULT 1 COMMENT '入住状态:0,未入住或已离宿;1，已入住',
    `REGISTER_ID`   bigint                                                         NULL DEFAULT NULL COMMENT '办理人员ID',
    `REGISTER_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '办理人员姓名',
    `REGISTER_TIME` datetime(0)                                                    NULL DEFAULT NULL COMMENT '办理时间',
    `CREATE_TIME`   datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                                    NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `REMARK`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_ROOM_ROOM_CODE` (`ROOM_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '人(工作)房关系'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_routine`
(
    `ID`                        bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '留观人员编码',
    `OBSERVE_TYPE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '医学观察类型',
    `TEMPERATURE`               double                                                        NULL     DEFAULT NULL COMMENT '体温:摄氏度',
    `TEMPERATURE_STATUS`        tinyint                                                       NULL     DEFAULT 0 COMMENT '体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `BLUTDRUCK`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '血压：mmHg',
    `HEART_RATE`                tinyint                                                       NULL     DEFAULT NULL COMMENT '心率：次/分钟',
    `SYMPTOM_TAB`               tinyint                                                       NULL     DEFAULT NULL COMMENT '症状标记:0,无；1,干咳；2,乏力；3,鼻塞流涕；4,咽痛；5,嗅觉/味觉减退；6,结膜炎；7,肌痛；8,腹泻；9,其他症状',
    `SYMPTOM`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '症状描述',
    `CHINESE_MEDICINE_STATUS`   tinyint                                                       NULL     DEFAULT NULL COMMENT '是否服用中药：0,否；1,是;',
    `CHINESE_MEDICINE_REASON`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '不服用中药原因描述',
    `REGISTER_ID`               bigint                                                        NULL     DEFAULT NULL COMMENT '操作人员ID',
    `REGISTER_NAME`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '操作人员姓名',
    `REGISTER_TIME`             datetime(0)                                                   NULL     DEFAULT NULL COMMENT '操作时间',
    `CREATE_TIME`               datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `UPDATE_TIME`               datetime(0)                                                   NOT NULL COMMENT '更新时间',
    `STATUS`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`                    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `DATA_SRC`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '数据来源',
    `BASIC_DISEASES`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '基础病',
    `ACUTE_DISEASES`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '急性病',
    `MEDICINE_RECORD`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '服药记录',
    `REPORT_TIME`               tinyint                                                       NULL     DEFAULT NULL COMMENT '填报时段1：上午 2：下午',
    `TEMPERATURE_SAMPLING_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '体温采样类型：检测类型',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`PERSON_CODE`) USING BTREE,
    INDEX `IDX_REGISTER_TIME` (`REGISTER_TIME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观日常监测'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_routine_day`
(
    `ID`                           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '留观人员编码',
    `PERSON_NAME`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '留观人姓名',
    `CHECK_DATE`                   date                                                          NULL DEFAULT NULL COMMENT '健康检测日期',
    `SYMPTOM_TAB`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状标记:多个以逗号分隔',
    `TEMPERATURE_STATUS_AM`        tinyint                                                       NULL DEFAULT NULL COMMENT '上午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `TEMPERATURE_STATUS_PM`        tinyint                                                       NULL DEFAULT NULL COMMENT '下午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `NUCLEIC_ACID_RESULT`          tinyint                                                       NULL DEFAULT NULL COMMENT '核酸检测结果 0:阴性  1:阳性',
    `NUCLEIC_ACID_RESULT_DATE`     date                                                          NULL DEFAULT NULL COMMENT '核酸结果检出日期',
    `CREATE_TIME`                  datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`                  datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `TEMPERATURE_AM`               double                                                        NULL DEFAULT NULL COMMENT '上午体温：摄氏度',
    `TEMPERATURE_PM`               double                                                        NULL DEFAULT NULL COMMENT '下午体温: 摄氏度',
    `TEMPERATURE_SAMPLING_TYPE_AM` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '上午体温采样方式',
    `TEMPERATURE_SAMPLING_TYPE_PM` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '下午体温采样方式',
    `TEMPERATURE_SAMPLING_TIME_AM` datetime(0)                                                   NULL DEFAULT NULL COMMENT '上午体温采样时间',
    `TEMPERATURE_SAMPLING_TIME_PM` datetime(0)                                                   NULL DEFAULT NULL COMMENT '下午体温采样时间',
    `SYMPTOM`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '症状描述',
    `SOURCE`                       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据来源',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `IDX_PERSON_CODE_CHECK_DATE` (`PERSON_CODE`, `CHECK_DATE`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观日常监测(天维度)'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_shift`
(
    `ID`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `TEAM_CODE`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '工作组别编码',
    `DUTY_ID`     bigint                                                        NULL DEFAULT NULL COMMENT '负责人员ID',
    `DUTY_NAME`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '负责人员名称',
    `PERSON`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '工作人员信息：JSON数组',
    `IN_TIME`     date                                                          NULL DEFAULT NULL COMMENT '值班日期',
    `CREATE_BY`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人员账号',
    `CREATE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员名称',
    `UPDATE_BY`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新人员账号',
    `UPDATE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人员名称',
    `CREATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态：S0A有效；S0X无效',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '职工值班表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff`
(
    `ID`                 bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '人员ID',
    `NAME`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `ALIAS_NAME`         varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '别名',
    `FILE_JSON`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员照片',
    `SEX`                tinyint                                                       NULL DEFAULT NULL COMMENT '性别',
    `BIRTH_DATE`         date                                                          NULL DEFAULT NULL COMMENT '出身年月',
    `AGE`                tinyint                                                       NULL DEFAULT NULL COMMENT '年龄',
    `PERSON_FROM`        tinyint                                                       NULL DEFAULT NULL COMMENT '人源:1,本地；2,来穗;3,境外;',
    `NATIONALITY`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '国籍',
    `NATION`             varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '民族',
    `IDENTITY_TYPE`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '证件类型',
    `IDENTITY_NUMBER`    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '证件号码',
    `ID_CARD`            varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '身份证号码',
    `TELPHONE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '手机号码',
    `CIVIL`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居住行政区划',
    `ADDRESS_DETAIL`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居住地址详情',
    `NATIVE_PLACE`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
    `BIRTH_PLACE`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '户籍地址',
    `VACCINATION_STATUS` tinyint                                                       NULL DEFAULT NULL COMMENT '是否接种疫苗:0,未接种或未完成;1,已接种;',
    `VACCINATION_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '接种时间：完成接种节点',
    `JOB_RISK`           tinyint                                                       NULL DEFAULT NULL,
    `CREATE_TIME`        datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`        datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态',
    `REMARK`             varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UQ_POPULATION_IDENTITYTYPE_IDENTITYNUMBER` (`IDENTITY_NUMBER`, `IDENTITY_TYPE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_handover`
(
    `ID`                  bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AREA_CODE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组团',
    `SUM_OBSERVER`        int                                                          NULL     DEFAULT 0 COMMENT '累计留观',
    `CURRENT_OBSERVER`    int                                                          NULL     DEFAULT 0 COMMENT '当前留观',
    `SUM_POSITIVE`        int                                                          NULL     DEFAULT 0 COMMENT '累计阳性',
    `SUM_CLOSE_CONTACT`   int                                                          NULL     DEFAULT 0 COMMENT '密接人数',
    `IS_LOCAL`            tinyint(1)                                                   NOT NULL COMMENT '性质：0-入境，1-本土',
    `FROM_COUNTRY`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '入境国',
    `OPEN_TIME`           datetime(0)                                                  NULL     DEFAULT NULL COMMENT '开团日期',
    `DISMISSAL_TIME`      datetime(0)                                                  NULL     DEFAULT NULL COMMENT '解除隔离日期',
    `PLUS_SEVEN`          int                                                          NULL     DEFAULT 0 COMMENT '+7人数',
    `MEDICAL_STAFF_COUNT` int                                                          NULL     DEFAULT 0 COMMENT '医护人数',
    `DOCTOR_LEADER`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '医生组长',
    `NURSE_LEADER`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '护理组长',
    `START_DATE`          datetime(0)                                                  NOT NULL COMMENT '始计日期',
    `CREATE_TIME`         datetime(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`         datetime(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A,有效;S0X,无效；',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员交班记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_health_status_day`
(
    `ID`                     bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `AREA_CODE`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场所区域编码',
    `BUILDING_CODE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '楼栋编码',
    `ATTENDANCE`             int UNSIGNED                                                 NOT NULL COMMENT '出勤人数',
    `ATTENDANCE_REQUIRED`    int UNSIGNED                                                 NOT NULL COMMENT '应出勤人数',
    `CODE_SCAN_NUM`          int UNSIGNED                                                 NOT NULL COMMENT '扫码人数',
    `NUCLEIC_ACID_NUM`       int UNSIGNED                                                 NOT NULL COMMENT '核酸检测人数',
    `HEALTH_EXAMINATION_NUM` int UNSIGNED                                                 NOT NULL COMMENT '健康检测人数',
    `CONFIRMED_CASES`        int UNSIGNED                                                 NULL DEFAULT NULL COMMENT '确诊病例',
    `CHECK_DATE`             date                                                         NULL DEFAULT NULL COMMENT '健康检测日期',
    `CREATE_TIME`            datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员健康情况（天维度）'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_isolation`
(
    `ID`                       bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`                 bigint                                                       NULL     DEFAULT NULL COMMENT '工作人员ID',
    `ISOLATION_TYPE`           tinyint                                                      NULL     DEFAULT NULL COMMENT '隔离场所：0，驿站内,1，居家隔离',
    `ISOLATION_BEGIN_TIME`     datetime(0)                                                  NULL     DEFAULT NULL COMMENT '隔离开始时间',
    `ISOLATION_END_TIME`       datetime(0)                                                  NULL     DEFAULT NULL COMMENT '隔离结束时间',
    `ISOLATION_STATUS`         tinyint                                                      NULL     DEFAULT NULL COMMENT '是否按规定隔离：0，否, 1，是',
    `LATEST_LEFT_STATION_TIME` datetime(0)                                                  NULL     DEFAULT NULL COMMENT '最近一次离开驿站时间',
    `CREATE_TIME`              datetime(0)                                                  NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`              datetime(0)                                                  NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A,有效;S0X,无效；',
    `ISOLATION_DAY`            int                                                          NULL     DEFAULT NULL COMMENT '隔离时长（天数）',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_STAFF_ID` (`STAFF_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员隔离信息记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_nat`
(
    `ID`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`              bigint                                                        NULL     DEFAULT NULL COMMENT '工作人员ID',
    `NUCLEIC_ACID_RESULT`   tinyint                                                       NULL     DEFAULT NULL COMMENT '核酸检测结果 0:阴性  1:阳性',
    `SAMPLE_ADDRESS`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '采样地点',
    `SAMPLING_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '采样时间',
    `SAMPLING_ORGANIZATION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采样机构',
    `SAMPLING_PERSON`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '采样人员',
    `SAMPLING_TYPE`         tinyint                                                       NULL     DEFAULT NULL COMMENT '采样类型：1咽拭子,2鼻拭子,3鼻咽拭子,4痰液,5气管分泌物,6气管吸取物,7肺泡灌洗液,8尿液',
    `GATHER_TIME`           datetime(0)                                                   NULL     DEFAULT NULL COMMENT '收样时间',
    `TESTING_ORGANIZATION`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '检测机构/实验室',
    `RESULT_TIME`           datetime(0)                                                   NULL     DEFAULT NULL COMMENT '检测/检出日期',
    `RESULT_REPORT_TIME`    datetime(0)                                                   NULL     DEFAULT NULL COMMENT '检测结果填报时间',
    `IgM_RESULT`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '抗体检测结果IgM',
    `IgG_RESULT`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '抗体检测结果IgG',
    `TESTING_PERSON`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '检测人员',
    `CREATE_TIME`           datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A,有效;S0X,无效；',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_STAFF_ID` (`STAFF_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员核酸检测记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_routine_day`
(
    `ID`                       bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`                 bigint                                                       NULL DEFAULT NULL COMMENT '工作人员ID',
    `STAFF_NAME`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作人姓名',
    `CHECK_DATE`               date                                                         NULL DEFAULT NULL COMMENT '健康检测日期',
    `SYMPTOM_TAB`              tinyint                                                      NULL DEFAULT NULL COMMENT '症状标记:0,无；1,干咳；2,乏力；3,鼻塞流涕；4,咽痛；5,嗅觉/味觉减退；6,结膜炎；7,肌痛；8,腹泻；9,其他症状',
    `TEMPERATURE_STATUS_AM`    tinyint                                                      NULL DEFAULT NULL COMMENT '上午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `TEMPERATURE_STATUS_PM`    tinyint                                                      NULL DEFAULT NULL COMMENT '下午体温情况：1、正常 37.3以下 2、低热~37.9 3、中热~38.9 4、高热39.0以上',
    `NUCLEIC_ACID_RESULT`      tinyint                                                      NULL DEFAULT NULL COMMENT '核酸检测结果 0:阴性  1:阳性',
    `NUCLEIC_ACID_RESULT_DATE` date                                                         NULL DEFAULT NULL COMMENT '核酸结果检出日期',
    `CREATE_TIME`              datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`              datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    `TEMPERATURE_AM`           double                                                       NULL DEFAULT NULL COMMENT '上午体温：摄氏度',
    `TEMPERATURE_PM`           double                                                       NULL DEFAULT NULL COMMENT '下午体温: 摄氏度',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_STAFF_ID` (`STAFF_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员日常监测(天维度)'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_train_situation`
(
    `ID`                     bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`               bigint                                                       NOT NULL COMMENT '工作人员ID',
    `WHETHER_TO_PARTICIPATE` tinyint                                                      NULL DEFAULT NULL COMMENT '是否正常到场参加：1：已参加，0：未参加',
    `TRAIN_COURSE_NAME`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训课程名称',
    `LECTURER_NAME`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训老师',
    `HOST_ORGANIZATION`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主办单位',
    `TRAIN_ORGANIZATION`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训单位',
    `TRAIN_BEGIN_TIME`       datetime(0)                                                  NULL DEFAULT NULL COMMENT '培训开始时间',
    `TRAIN_END_TIME`         datetime(0)                                                  NULL DEFAULT NULL COMMENT '培训结束时间',
    `TRAIN_TIME_LENGTH`      double                                                       NULL DEFAULT NULL COMMENT '培训时长',
    `TRAIN_CONTENT`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL COMMENT '培训内容',
    `TRAIN_WAY`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训方式',
    `STATUS`                 varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态（SOA,SOD）',
    `CREATE_TIME`            datetime(0)                                                  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `REMARK`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员培训情况'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_vaccine`
(
    `ID`                     bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`               bigint                                                        NULL     DEFAULT NULL COMMENT '工作人员ID',
    `STAFF_NAME`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作人员名称',
    `VACCINE_NAME`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '疫苗名称',
    `MANUFACTURER`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '厂家名称',
    `BATCH_CODE`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '批号',
    `INOCULATE_TIME`         datetime(0)                                                   NULL     DEFAULT NULL COMMENT '接种时间',
    `INOCULATE_ORGANIZATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '接种单位',
    `INOCULATE_NUM`          int                                                           NULL     DEFAULT NULL COMMENT '接种针次',
    `FINISHED`               tinyint                                                       NULL     DEFAULT NULL COMMENT '是否完成接种疫苗:0,否；1,是；',
    `CREATE_TIME`            datetime(0)                                                   NULL     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                   NULL     DEFAULT NULL COMMENT '更新时间',
    `STATUS`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'S0A' COMMENT '状态:S0A,有效；S0X无效；',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_STAFF_ID` (`STAFF_ID`) USING BTREE,
    INDEX `IDX_FINISHED` (`FINISHED`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作人员接种疫苗记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_staff_workinfo`
(
    `ID`                       bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_ID`                 bigint                                                        NOT NULL COMMENT '人员ID',
    `AREA_CODE`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场所区域编码',
    `BUILDING_CODE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '楼栋编码',
    `ROOM_CODE`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房间编码',
    `TEAM_TYPE`                tinyint                                                       NULL DEFAULT NULL COMMENT '团队类型：1，防控消毒组,2，健康观察组,3，信息联络组,4，安全保卫组,5，后勤保障组,6，病例转运组,7，人文关怀组',
    `DUTY`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '职务',
    `IS_DUTY`                  tinyint                                                       NULL DEFAULT NULL COMMENT '是否是负责人:0,否；1,是；',
    `START_TIME`               datetime(0)                                                   NULL DEFAULT NULL COMMENT '入岗时间',
    `END_TIME`                 datetime(0)                                                   NULL DEFAULT NULL COMMENT '离岗时间',
    `WORKING_LIVE`             varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '在岗期间居住情况',
    `ORGANIZATION_NAME`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '所属单位',
    `STAFF_STATUS`             tinyint                                                       NULL DEFAULT 1 COMMENT '职员状态：1,在职；2,休假；3,离职',
    `REGISTER_TIME`            datetime(0)                                                   NULL DEFAULT NULL COMMENT '填报时间',
    `CREATE_TIME`              datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`              datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A有效',
    `REMARK`                   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `JOB_RISK`                 tinyint                                                       NULL DEFAULT NULL COMMENT '岗位风险等级：0,高风险；1,低风险；',
    `ISOLATION_TYPE`           tinyint                                                       NULL DEFAULT NULL COMMENT '隔离场所：0，驿站内,1，居家隔离',
    `ISOLATION_BEGIN_TIME`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '隔离开始时间',
    `ISOLATION_END_TIME`       datetime(0)                                                   NULL DEFAULT NULL COMMENT '隔离结束时间',
    `ISOLATION_STATUS`         tinyint                                                       NULL DEFAULT NULL COMMENT '是否按规定隔离：0，否, 1，是',
    `LATEST_LEFT_STATION_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '最近一次离开驿站时间',
    `CHECK_IN_AREA`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '入住区域',
    `DORMITORY_CODE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '宿舍编码',
    `DORMITORY_NAME`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '宿舍名称',
    `WORK_CARD_COLOR`          tinyint                                                       NULL DEFAULT 0 COMMENT '工牌颜色：0 未知；1,红色；2,蓝色；3,绿色',
    `CHECK_IN`                 datetime(0)                                                   NULL DEFAULT NULL COMMENT '入住时间',
    `CHECK_OUT`                datetime(0)                                                   NULL DEFAULT NULL COMMENT '离开时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UQ_STAFF_ID` (`STAFF_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_task_schema`
(
    `ID`             bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `SCHEMA_CODE`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模板编码',
    `SCHEMA_NAME`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模板名称',
    `SCHEMA_DESC`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板描述',
    `TASK_TYPE`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '101:核酸采集,102:流调,103:健康排查,104:目的地申报',
    `OBSERVER_TYPE`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '适用人员类型:0,入境;1,本土;*,通配符',
    `CLOSE_CONTACTS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '密切接触者: 0 非密接者;1 次密接者;2 密接者;3 一般入境人员;*,通配符',
    `OBSERVER_DAYS`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '适用留观天数：*,通配符',
    `PRIORITY`       int                                                     NULL DEFAULT NULL COMMENT '优先级, 值越大优先级越高',
    `BEGIN_TIME`     datetime(0)                                             NULL DEFAULT NULL COMMENT '生效时间',
    `END_TIME`       datetime(0)                                             NULL DEFAULT NULL COMMENT '失效时间',
    `STATUS`         varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '状态, S0W:待发布, S0A, 已生效',
    `CREATE_TIME`    datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_SCHEMA_CODE` (`SCHEMA_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '留观任务模板'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_task_schema_20211228_1`
(
    `ID`             bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `SCHEMA_CODE`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模板编码',
    `SCHEMA_NAME`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模板名称',
    `SCHEMA_DESC`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板描述',
    `TASK_TYPE`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '101:核酸采集,102:流调,103:健康排查,104:目的地申报',
    `OBSERVER_TYPE`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '适用人员类型',
    `CLOSE_CONTACTS` tinyint                                                 NULL DEFAULT NULL COMMENT '密切接触者: 0 非密接者;1 次密接者;2 密接者;3 一般入境人员',
    `OBSERVER_DAYS`  int                                                     NULL DEFAULT NULL COMMENT '适用留观天数',
    `PRIORITY`       int                                                     NULL DEFAULT NULL COMMENT '优先级, 值越小优先级越高',
    `BEGIN_TIME`     datetime(0)                                             NULL DEFAULT NULL COMMENT '生效时间',
    `END_TIME`       datetime(0)                                             NULL DEFAULT NULL COMMENT '失效时间',
    `STATUS`         varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '状态, S0W:待发布, S0A, 已生效',
    `CREATE_TIME`    datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_SCHEMA_CODE` (`SCHEMA_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '留观任务模板'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_task_schema_detail`
(
    `ID`          bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `SCHEMA_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模板编码',
    `ITEM_CODE`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '任务项编码',
    `ITEM_NAME`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '任务项名称',
    `ON_DAY`      int                                                     NULL DEFAULT NULL COMMENT '留观第N天,0表示每天执行',
    `START_TIME`  time(0)                                                 NULL DEFAULT NULL COMMENT '执行开始时间',
    `END_TIME`    time(0)                                                 NULL DEFAULT NULL COMMENT '执行结束时间',
    `CREATE_TIME` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    `TASK_STATUS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态:S0W,未完成;S0P,处理中;S0C,已完成;',
    `REMARK`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_SCHEMA_CODE` (`SCHEMA_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '留观任务项目'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_train_situation`
(
    `ID`                 bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `TRAIN_COURSE_NAME`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训课程名称',
    `TEAM_TYPE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '受训组别，多个以英文逗号隔开',
    `TRAINEES_TOTAL`     int                                                           NULL DEFAULT NULL COMMENT '受训人数-应到',
    `TRAINEES_REAL`      int                                                           NULL DEFAULT NULL COMMENT '受训人数-实到',
    `LECTURER_NAME`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训老师',
    `HOST_ORGANIZATION`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主办单位',
    `TRAIN_ORGANIZATION` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训单位',
    `TRAIN_BEGIN_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '培训开始时间',
    `TRAIN_END_TIME`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '培训结束时间',
    `TRAIN_TIME_LENGTH`  double                                                        NULL DEFAULT NULL COMMENT '培训时长',
    `TRAIN_CONTENT`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '培训内容',
    `TRAIN_WAY`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '培训方式',
    `REMARK`             longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '备注',
    `CREATE_TIME`        datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`        datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '驿站工作培训情况'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_treatmentinfo`
(
    `ID`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PERSON_CODE`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '人员编码',
    `TREATMENT_TYPE` tinyint                                                       NULL DEFAULT NULL COMMENT '服务类型：10,医疗;20,服务;',
    `TREATMENT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '服务(服药)记录',
    `TREATMENT_DESC` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '工作内容',
    `REGISTER_ID`    bigint                                                        NULL DEFAULT NULL COMMENT '操作人员ID',
    `REGISTER_NAME`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作人员姓名',
    `REGISTER_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '操作时间',
    `CREATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `REMARK`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_PERSON_ID` (`PERSON_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '医疗服务记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_visit`
(
    `ID`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `VISIT_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '拜访人员姓名',
    `COMPANY_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属单位',
    `VISIT_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '到访时间',
    `LEAVE_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '离开时间',
    `USER_NAME`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '登记人员账号',
    `REAL_NAME`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记人员姓名',
    `UPDATE_BY`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '修改人员账号',
    `UPDATE_NAME`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员名称',
    `REMARK`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态：S0A，有效；S0X，无效；',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '来访人员记录'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`basedata_work_dynamic`
(
    `ID`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `TITLE`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `AUTHOR_NAME`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发布人姓名(用户)',
    `AUTHOR_ID`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发布人ID(用户)',
    `RELEASE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '发布日期',
    `RELEASE_DEPT` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布部门',
    `CONTENT`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '正文',
    `CREATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '工作动态'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`bil_bill`
(
    `ID`          bigint                                                 NOT NULL COMMENT 'ID',
    `TENANT_ID`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '租户ID',
    `BILL_CODE`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '账单号',
    `BILL_NAME`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '账单名称, A1-2231房费',
    `OWNER`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '账单归属, 人员编码',
    `REFER`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '账单出处, 房间号',
    `NEED_PAY`    double                                                 NULL     DEFAULT NULL COMMENT '待支付账金额',
    `CREDIT`      double                                                 NULL     DEFAULT NULL COMMENT '入账金额,支付金额',
    `DEBIT`       double                                                 NULL     DEFAULT NULL COMMENT '出账金额,消费金额',
    `START_TIME`  datetime(0)                                            NULL     DEFAULT NULL COMMENT '出账开始时间',
    `END_TIME`    datetime(0)                                            NULL     DEFAULT NULL COMMENT '出账截止时间',
    `LAST_TIME`   datetime(0)                                            NULL     DEFAULT NULL COMMENT '最新出账时间',
    `STATUS`      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT 'S0P: 处理中, S0T: 停止处理, S0A:已完成归档, S0X:失效',
    `UPDATE_TIME` datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME` datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BILLCODE` (`BILL_CODE`) USING BTREE,
    INDEX `IDX_ORGAN` (`REFER`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '账单信息'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`bil_bill_credit`
(
    `ID`          bigint                                                  NOT NULL COMMENT 'ID',
    `BILL_CODE`   varbinary(20)                                           NULL     DEFAULT NULL COMMENT '账单号',
    `ITEM_ID`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '项目号',
    `AMOUNT`      double                                                  NULL     DEFAULT NULL COMMENT '金额',
    `PAY_TYPE`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付方式',
    `PAY_CODE`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付订单号',
    `CREDIT_TIME` datetime(0)                                             NULL     DEFAULT NULL COMMENT '入账时间',
    `TRANS_DATE`  date                                                    NULL     DEFAULT NULL COMMENT '交易日期',
    `STATUS`      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'S0A: 有效的, S0X:失效',
    `MEMO`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `UPDATE_TIME` datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME` datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BILL_CODE` (`BILL_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '支付入账'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`bil_bill_debit`
(
    `ID`              bigint                                                  NOT NULL COMMENT 'ID',
    `BILL_CODE`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '账单号',
    `REFER`           varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL,
    `ITEM_ID`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '项目号',
    `AMOUNT`          double                                                  NULL     DEFAULT NULL COMMENT '金额',
    `DEBIT_TYPE`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付方式',
    `DEBIT_TYPE_NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付订单号',
    `DEBIT_TIME`      datetime(0)                                             NULL     DEFAULT NULL COMMENT '入账时间',
    `TRANS_DATE`      date                                                    NULL     DEFAULT NULL COMMENT '交易日期',
    `STATUS`          varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'S0A: 有效的, S0X:失效',
    `MEMO`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `UPDATE_TIME`     datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`     datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BILL_CODE` (`BILL_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '消费出账明细'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`custom_msg_record`
(
    `ID`            bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `OBSERVER_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留观人员编码',
    `OBSERVER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留观人员姓名',
    `ROOM_CODE`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '房间编码',
    `TOPIC`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '消息队列topic',
    `MESSAGE`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
    `MSG_TYPE`      int                                                     NULL DEFAULT NULL COMMENT '0:文字,1:语音',
    `OPERATE_TIME`  datetime(0)                                             NULL DEFAULT NULL COMMENT '操作时间',
    `OPERATOR`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '操作人',
    `CREATE_TIME`   datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    `STATUS`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '状态',
    `RECEIVER`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_hotel_guest`
(
    `ID`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '自增字段 ，不用填',
    `SOURCE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
    `GUESTID`     bigint                                                        NULL DEFAULT NULL COMMENT '客人id',
    `GROUPID`     int                                                           NULL DEFAULT NULL COMMENT '同团号',
    `GUESTTYPE`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客人类型',
    `NAME`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客人姓名',
    `SEX`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `BIRTHDAY`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生日',
    `IDTYPE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型',
    `IDCODE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
    `DISTRICTID`  int                                                           NULL DEFAULT NULL COMMENT '行政区划',
    `ADDRESS`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `ROOMID`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房号',
    `LTIME`       varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '入住时间',
    `LWAITER`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入住站点',
    `PHOTO`       longblob                                                      NULL COMMENT '客人头像',
    `CURPHOTO`    longblob                                                      NULL COMMENT '客人现场照片(新加的必填字段)',
    `STAYPHONE`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国内邀请人或本人联系电话',
    `PETIME`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '预离店时间',
    `COUNTRY`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家/地区',
    `NATION`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族	',
    `SCANTIME`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '读证件时间',
    `OK`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否处理过',
    `RESULT`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果',
    `NOTE`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败的原因',
    `CREATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '旅客入住资料'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_hotel_guest_out`
(
    `ID`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '自增字段',
    `SOURCE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
    `GUEST_ID`    bigint                                                        NULL DEFAULT NULL COMMENT '客人id',
    `IDCODE`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客人证件号',
    `ROOMID`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房间号码',
    `OUTORCHG`    varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '退房还是转房',
    `ETIME`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '离店时间或转房时间',
    `EWAITER`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '退房操作员',
    `OK`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否处理过',
    `RESULT`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果',
    `NOTE`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败的原因',
    `CREATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '旅客退房资料'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_hotel_inout`
(
    `ID`                    bigint                                                         NOT NULL AUTO_INCREMENT,
    `SOURCE`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`           int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `RID`                   bigint                                                         NULL DEFAULT NULL COMMENT '业务记录ID',
    `UID`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `ROOM_NUM`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '转入酒店房间号',
    `IS_SINGLE_ROOM`        int                                                            NULL DEFAULT NULL COMMENT '是否单人单间：0、否 1、是',
    `NOT_SINGLE_REASON`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '非单人单间原因(非单人单间时必填）',
    `HOTEL_CHECKOUT_REASON` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '转出备注',
    `PASS_TIME`             datetime(0)                                                    NULL DEFAULT NULL COMMENT '转运时间',
    `PASS_TYPE`             int                                                            NOT NULL COMMENT '转运类型 6 转入酒店 7 转出酒店 18 转送外市	',
    `HOTEL_ID`              int                                                            NULL DEFAULT NULL COMMENT '操作酒店ID',
    `PERSON_CATEGORY`       int                                                            NULL DEFAULT NULL COMMENT '人员类型 0: 普通旅客 1: 机组人员 2: 船员 3: 本地发现隔离人员 4 外省来粤人员',
    `DST_HOTEL_ID`          int                                                            NULL DEFAULT NULL COMMENT '酒店转酒店 目的酒店ID	',
    `DST_CITY`              varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '转送外市，目的地市',
    `HANDLER_NAME`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '提交用户姓名',
    `HANDLER_PHONE`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '提交用户手机号',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '酒店转入转出'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_medicine_report`
(
    `ID`                     bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`            int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`            varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`            datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `MEDICINE_TYPE`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '医学上报类型 2 流调, 3 核酸采样 4 日常监测, 5 健康管理措施',
    `GENDER`                 int                                                            NULL DEFAULT NULL COMMENT '性别 1 男 2 女',
    `BIRTHDATE`              date                                                           NULL DEFAULT NULL COMMENT '出生日期',
    `TEMPERATURE`            int                                                            NULL DEFAULT NULL COMMENT '体温情况(1:正常 37.3C以下 2:低热~37.9 3:中热~38.9 4:高热39.0~)	',
    `RECENTLY_HEALTH_STATUS` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日情况 JSON串 [\"0\"]--曾接触新冠肺炎病例/疑似病例/无症状感染者\', VALUE: \'1\' ; \'曾接触有发热或呼吸道症状的患者\', VALUE: \'2\' ; \'居住的社区有曾报告有新冠肺炎病例\', VALUE: \'3\' ; \'所在办公室/家庭等出现2人及以上有发热或呼吸道症状\', VALUE: \'4\' ; \'曾服用过退烧药、感冒药、止咳药\', VALUE: \'5\' ; \'曾接受新冠肺炎病毒检测，检测结果为阳性\', VALUE: \'6\' ; \'有发热、咳嗽、腹泻等不适\', VALUE: \'7\' ; \'以上均无\', VALUE:\'0\' ;',
    `SICKNESS`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '基础病情况, 多个逗号间隔; 高血压、心脏病、精神科疾病、肺结核、（其他手填）、以上均无',
    `SDBZ`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '十大病症 JSON串 [\"0\"]-- ; 多选 1发热/2干咳/3乏力/4嗅觉味觉减退/5鼻塞/6流涕/7咽痛/8结膜炎/9肌痛/10腹泻/ 0 以上均无	',
    `JZBS`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '急症病史, 多个逗号间隔;心梗、脑梗、中风、冠心病、（其他手填）、以上均无	',
    `FYS`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '服药史',
    `PORT_ID`                int                                                            NULL DEFAULT NULL COMMENT '入境口岸ID',
    `FROM_COUNTRY`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '出发国家',
    `TRAIN_TYPE`             int                                                            NULL DEFAULT NULL COMMENT '入境交通方式: 1=飞机/船舶/火车,2=其它',
    `TRAIN_NUMBER`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '航班(船班/车次)号',
    `SEAT_NUMBER`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '座位号',
    `DST_PROVINCE_CN`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的省(中文)',
    `DST_CITY_CN`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的市(中文)',
    `ADDRESS`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '常住地址',
    `RECENTLY_COUNTRY`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日旅行或居住国家',
    `GELI_START_TIME`        datetime(0)                                                    NULL DEFAULT NULL COMMENT '隔离开始时间 格式2006-01-02 15:04:05	',
    `GELI_END_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '隔离结束时间 格式2006-01-02 15:04:05',
    `CN_CID`                 varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '内地居民身份证号码',
    `IS_CN_CID`              int                                                            NULL DEFAULT NULL COMMENT '是否内地居民身份证 0 否 1 是',
    `OTHER_CID_TYPE`         int                                                            NULL DEFAULT NULL COMMENT '非内地居民身份证原因 1 其他 2 华人华侨 3 港澳台居民 4 外籍人数 5 未达到办理条件,6 身份被限制无法办理',
    `HAS_TAKEN_VACCINE`      int                                                            NULL DEFAULT NULL COMMENT '是否接种疫苗 0 否 1 是',
    `IS_LOCAL_DISCOVERY`     tinyint(1)                                                     NULL DEFAULT NULL COMMENT '是否本地隔离人员',
    `HOTEL_ID`               int                                                            NULL DEFAULT NULL COMMENT '操作酒店ID',
    `MEDICINE_NAME`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '操作人姓名',
    `MEDICINE_PHONE`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '操作人手机号',
    `HESUAN_STATUS`          int                                                            NULL DEFAULT NULL COMMENT '核酸采样情况 1:有',
    `HESUAN_TIME`            datetime(0)                                                    NULL DEFAULT NULL COMMENT '核酸采样时间 格式2006-01-02 15:04:05',
    `HESUAN_ORGANIZATION`    int                                                            NULL DEFAULT NULL COMMENT '核酸检测机构 1 海关 2 酒店',
    `BLOOD_PRESSURE_STR`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '血压',
    `HEART_RATE`             int                                                            NULL DEFAULT NULL COMMENT '心率，单位次/分',
    `HAS_TAKE_MEDICINE`      int                                                            NULL DEFAULT NULL COMMENT '是否服用中药 1 是 2 否',
    `SETUP_TYPE`             varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL DEFAULT NULL COMMENT '安置类型,0:集中隔离 1:居家隔离 2:送医 3:送密接隔离酒店 4:解除隔离 5:无需隔离 7:特殊情况 8:七天居家健康管理\r\n            其中安置类型 1，2，3，4，7，8 提交上报同时，会自动生成酒店转出的记录',
    `REMARK`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '医学上报'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_person_arrivein`
(
    `ID`                    bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`           int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `IS_LOCAL`              tinyint                                                        NULL DEFAULT NULL COMMENT '是否本土人员, 1: 是, 0:否',
    `NAME`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '姓名',
    `GENDER`                int                                                            NULL DEFAULT NULL COMMENT '性别： 1 男 2 女',
    `AGE`                   int                                                            NULL DEFAULT NULL COMMENT '年龄',
    `BIRTHDATE`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '生日',
    `PHONE`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '电话',
    `NATION`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '国籍',
    `NATION_CODE`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '一码通码面 国籍码-证件号',
    `NATIVE_LINKMAN_INFO`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '紧急联系人',
    `LATER_ADDRESS`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '详细地址',
    `CARD_TYPE`             int                                                            NULL DEFAULT NULL COMMENT '证件类型：1=护照,2=前往港澳通行证,3=往来台湾通行 证,4=往来港澳通行证,5=港澳居民来往内地通行证,6= 台湾居民来往大陆通行证,7=中华人民共和国出入境通行证,8=其它证件 ; 1000=内地居民身份证;1001=普通 护照;1002=港澳居民来往内地通行证;1003=台湾居民 来往内地通行证;1004=出生医学证明;2001=境外人员 护照(外国人入境服务外国护照);2002=中国护照 (外国人入境服务中国护照);2003=内地居民前往港 澳通行证;2004=内地居民往来台湾通行证;2005=内地 居民往来港澳通行证;2006=中华人民共和国出入境通 行证;2007=其它证件;2008=军官证;2009=港澳居民居 住证;2010=台湾居民居住证;2011=澳门居民身份 证;10001=港澳台居民居住证',
    `CARD_NUMBER`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '证件号码',
    `CHECK_IN_HOTEL`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '拟入住酒店名称',
    `SCANNING`              int                                                            NULL DEFAULT NULL COMMENT '是否超时没转入酒店 0 否 1 是',
    `RECENTLY_COUNTRY`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日途径国家',
    `RECENTLY_COUNTRY_CITY` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日途径城市',
    `ARRIVAL_DATE`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '入境日期',
    `ARRIVAL_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '入境时间',
    `TRAIN_NUMBER`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '航班(车次)号',
    `SEAT_NUMBER`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '座位号',
    `PORT_ID`               int                                                            NULL DEFAULT NULL COMMENT '口岸ID',
    `PORT_NAME`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '入境口岸',
    `TRAIN_TYPE`            int                                                            NULL DEFAULT NULL COMMENT '入境方式 1 飞机/火车/船舶 2 其他',
    `DST_CITY`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的城市',
    `PLATE_NUMBER`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '车牌号码',
    `TRANSIT_CODE`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '车次扩展信息',
    `DEPART_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '发车时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '即将到达人员'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_person_arrivein_detail`
(
    `ID`                    bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`           int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`                   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `NATION_CODE`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '一码通面值',
    `NAME`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '姓名',
    `PHONE_ENCODE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '电话',
    `CARD_TYPE`             int                                                            NULL DEFAULT NULL COMMENT '证件类型：1=护照,2=前往港澳通行证,3=往来台湾通行 证,4=往来港澳通行证,5=港澳居民来往内地通行证,6= 台湾居民来往大陆通行证,7=中华人民共和国出入境通行证,8=其它证件 ; 1000=内地居民身份证;1001=普通 护照;1002=港澳居民来往内地通行证;1003=台湾居民 来往内地通行证;1004=出生医学证明;2001=境外人员 护照(外国人入境服务外国护照);2002=中国护照 (外国人入境服务中国护照);2003=内地居民前往港 澳通行证;2004=内地居民往来台湾通行证;2005=内地 居民往来港澳通行证;2006=中华人民共和国出入境通 行证;2007=其它证件;2008=军官证;2009=港澳居民居 住证;2010=台湾居民居住证;2011=澳门居民身份 证;10001=港澳台居民居住证',
    `CARD_NUMBER`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '证件号码',
    `CID_ENCODE`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '身份证',
    `GENDER`                int                                                            NULL DEFAULT NULL COMMENT '性别： 1 男 2 女',
    `BIRTHDATE`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '生日',
    `NATIONALITY_CN`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '国籍',
    `DST_PROVINCE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '海关填写目的地省',
    `DST_CITY`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '海关填写目的地市',
    `DST_AREA`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '海关填写目的地区',
    `DST_ADDRESS`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '海关填写目的地详细地址',
    `RECENTLY_COUNTRY`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日内居住国家和地区',
    `RECENTLY_COUNTRY_CITY` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '过去14日内居住国家的城市',
    `INBOUND_TYPE`          int                                                            NULL DEFAULT NULL COMMENT '入境方式1 飞机/火车/船舶 2 其他 0 无',
    `INBOUND_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '入境时间',
    `INBOUND_PORT_NAME`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '入境口岸',
    `INBOUND_PORT_ID`       int                                                            NULL DEFAULT NULL COMMENT '入境口岸ID',
    `TRAIN_NUMBER`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '航班（车次）号',
    `SEAT_NUMBER`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '座位号',
    `JCGL`                  int                                                            NULL DEFAULT NULL COMMENT '当前能否操作解除隔离 0 不符合 1 符合',
    `QTJJGL`                int                                                            NULL DEFAULT NULL COMMENT '当前能否操作七天居家健康管理0 不符合 1 符合',
    `APPLY_INFO`            varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旅客自主申报内容',
    `TRANSPORT_INFO`        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旅客转运信息',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_UID` (`UID`) USING BTREE,
    INDEX `IDX_NATION_CODE` (`NATION_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '即将到达人员明细'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_person_dstinfo`
(
    `ID`                   bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`          int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `NATION_CODE`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '一码通面值',
    `DST_PROVINCE`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '自主申报目的地省',
    `DST_CITY`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '自主申报目的地市',
    `DST_CITY_CODE`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的地城市区划',
    `DST_AREA`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '自主申报目的地区',
    `DST_STREET`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的地街道',
    `DST_STREET_CODE`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '目的地街道码12位',
    `DST_ADDRESS`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '自主申报目的地详细地址',
    `NATIVE_LINKMAN_NAME`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '亲友联系人姓名 亲友联系人手机号加密串',
    `NATIVE_LINKMAN_PHONE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '亲友联系人电话',
    `STATUS`               int                                                            NULL DEFAULT NULL COMMENT '申报状态 0 未申报 1 待确认 2 符合 3 不符合',
    `LEAVE_TYPE`           int                                                            NULL DEFAULT NULL COMMENT '离开方式0=未知 1=飞机 2=船舶 3=火车4=其他',
    `LEAVE_TIME`           datetime(0)                                                    NULL DEFAULT NULL COMMENT '离开时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_UID` (`UID`) USING BTREE,
    INDEX `IDX_NATION_CODE` (`NATION_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '人员目的地申报信息(一码通申报)'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_person_supplement`
(
    `ID`                     bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`            int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`            varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`            datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`            datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `ROOM_NUMBER`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间号',
    `ID_NUMBER`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '身份证号码',
    `NATIVE_LINKMAN_NAME`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '紧急联系人',
    `NATIVE_LINKMAN_PHONE`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '紧急联系人电话',
    `NATIVE_LINKMAN_INFO`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '紧急联系人信息',
    `IN_COUNTRY_PHONE`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '境内联系手机',
    `IS_ABROAD_STUDENT`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '是否为留学生 0:是 1:否',
    `ACCEPTABLE_PRICE`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '意向集中隔离酒店可接受价格范围',
    `FOLLOWER_NUMBER`        int                                                            NULL DEFAULT NULL COMMENT '同行人数',
    `HOUSE_SITUATION`        int                                                            NULL DEFAULT NULL COMMENT '家庭住房情况 1: 常驻广州  2:临时来穗  3:其他',
    `RESIDENCE_ADDRESS`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '户籍地址',
    `LIVE_NUMBER`            int                                                            NULL DEFAULT NULL COMMENT '居住人数',
    `HOUSE_SIZE`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '房间大小(1,1,1: 1房1厅1卫)',
    `IS_SINGLE_ROOM`         tinyint                                                        NULL DEFAULT NULL COMMENT '是否单人单间：0、否 1、是',
    `NOT_SINGLE_ROOM_REASON` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '非单人单间原因',
    `COMMIT_USER_NAME`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '提交用户姓名',
    `COMMIT_USER_PHONE`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '提交用户手机号',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '人员补登记'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`ds_xinli_assessment`
(
    `ID`           bigint                                                         NOT NULL COMMENT '主键',
    `SOURCE`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '数据来源',
    `SYNC_STATUS`  int                                                            NULL DEFAULT NULL COMMENT '同步状态，0：未同步，1：已同步',
    `ERR_MESSAGE`  varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
    `CREATE_TIME`  datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`  datetime(0)                                                    NULL DEFAULT NULL COMMENT '修改时间',
    `UID`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '用户唯一标识',
    `RESULT`       int                                                            NULL DEFAULT NULL COMMENT '心理评估结果 [1 正常 2 轻度异常 3 中度异常 4 重度异常]',
    `HURT_RISK`    int                                                            NULL DEFAULT NULL COMMENT '自伤自杀风险[0:无明显自伤自杀风险 1:有自伤自杀观念 2:有...企图 3:有...行为]',
    `COMMIT_NAME`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '操作人姓名',
    `COMMIT_PHONE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '操作人手机号',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '健康评估'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`geo_area`
(
    `ID`        int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
    `AREA_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域编号',
    `AREA_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域名称',
    `GEOMETRY`  polygon                                                NULL COMMENT '地理描述（面）',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '区域的地理表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`geo_building`
(
    `ID`            int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
    `BUILDING_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '楼栋编号',
    `BUILDING_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '楼栋名称',
    `GEOMETRY`      polygon                                                NULL COMMENT '地理描述（点）',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '楼栋的地理表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`gray_test_task`
(
    `id`            bigint                                                  NOT NULL COMMENT '主键',
    `batch_code`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '批次，21122402',
    `room_code`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '房间编码',
    `building_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '楼宇编码',
    `area_code`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '归属区域',
    `tenant_id`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '租户',
    `person_code`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '人员编码，如 jdt-21121677tza998',
    `person_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '人员名称，如：测试211216a8j',
    `device_ip`     varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备IP, 多个用逗号分隔',
    `begin_time`    datetime(0)                                             NULL DEFAULT NULL COMMENT '开始时间',
    `end_time`      datetime(0)                                             NULL DEFAULT NULL COMMENT '结束时间',
    `status`        varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'S0WW：待处理，S0P：任务执行中， S0F：任务执行结束',
    `user_name`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    `remark`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '留观测试任务'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`help_id`
(
    `id` int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '辅助id表,不要清数据及删除'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`hh_mental_scale`
(
    `ID`                   bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `OBSERVER_ID`          bigint                                                       NOT NULL COMMENT '留观人员ID',
    `AUTO_RISK`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '心理风险等级：问卷填报',
    `TAB_RISK`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '心理风险等级：标记',
    `ANXIETY_SCORE`        int                                                          NULL DEFAULT NULL COMMENT '焦虑问卷得分',
    `ANXIETY_RESULT`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '焦虑问卷填报结果',
    `INSOMNIA_SCORE`       int                                                          NULL DEFAULT NULL COMMENT '失眠问卷得分',
    `INSOMNIA_RESULT`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失眠问卷填报结果',
    `PSYCHOLOGICAL_SCORE`  int                                                          NULL DEFAULT NULL COMMENT '心理问卷得分',
    `PSYCHOLOGICAL_RESULT` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '心理问卷填报结果',
    `HURTING_SELF`         tinyint                                                      NULL DEFAULT NULL COMMENT '心理问卷：有不如死掉的想法',
    `QUESTIONNAIRE_TIME`   datetime(0)                                                  NULL DEFAULT NULL COMMENT '问卷填报时间',
    `HAS_ASSESS`           tinyint                                                      NULL DEFAULT 0 COMMENT '心理评估状态:0,未评估;1,已评估;',
    `HAS_TAB_RISK`         tinyint                                                      NULL DEFAULT 0 COMMENT '心理风险标记状态:0,未标记;1,已标记;',
    `TASK_TYPE`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联心理健康调查问卷任务类型',
    `TASK_FULL_NAME`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联心理健康调查问卷任务全称',
    `TASK_ID`              bigint                                                       NULL DEFAULT NULL COMMENT '关联心理健康调查问卷任务ID',
    `CREATE_TIME`          datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`          datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
    `EXEC_TIME`            datetime(0)                                                  NULL DEFAULT NULL COMMENT '操作时间',
    `EXEC_BY`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人员',
    `SOURCE`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `AK_IDX_OBSERVER_ID` (`OBSERVER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '心理量表结果'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`opt_sale_plan`
(
    `ID`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AREA_CODE`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '区域',
    `ROOM_TYPE`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房型',
    `PLAN_SALE_DATE` date                                                          NULL DEFAULT NULL COMMENT '计划销售日期',
    `PLAN_NUM`       int                                                           NULL DEFAULT NULL COMMENT '计划销售量',
    `REMAIN_NUM`     int                                                           NULL DEFAULT NULL COMMENT '剩余库存',
    `STATUS`         varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `CREATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `IS_DEFAULT`     tinyint(1)                                                    NULL DEFAULT 0 COMMENT '是否默认(最低房型):0,否;1,是;',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_SALEPLAN_AREACODE_ROOMTYPE_SAELDATE` (`AREA_CODE`, `ROOM_TYPE`, `PLAN_SALE_DATE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '销售计划'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`opt_transit_area`
(
    `ID`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AREA_CODE`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '开往区域',
    `TRANSIT_CODE`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车次',
    `TRANSIT_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '发车时间',
    `TRANSIT_DATE`   date                                                          NULL DEFAULT NULL COMMENT '发车日期',
    `PLATE_NUMBER`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '车牌号',
    `STATUS`         varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `CREATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_BY`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人',
    `CREATE_NAME`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人姓名',
    `TRANSIT_STATUS` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '使用状态',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_TRANSITAREA_TRANSITCODE` (`TRANSIT_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '车次与区域关系'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`opt_transit_schedule`
(
    `ID`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AREA_CODE`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '开往区域',
    `START_TIME`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '起始时间',
    `END_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '截止时间',
    `SORT`        int                                                           NULL DEFAULT NULL COMMENT '顺序号',
    `STATUS`      varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'S0A' COMMENT '状态：S0X，无效；S0A，有效；',
    `CREATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '车次排班表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_check`
(
    `ID`           bigint      NOT NULL COMMENT 'ID',
    `CHECK_DATE`   date        NOT NULL COMMENT '支付单',
    `CHECK_RESULT` tinyint     NULL     DEFAULT NULL COMMENT '对账结果, 0:账单, 1:账不平',
    `BASE_AMOUNT`  double      NULL     DEFAULT NULL COMMENT '支付平台支付总金额',
    `BASE_TOTAL`   int         NULL     DEFAULT NULL COMMENT '支付平台支付笔数',
    `BASE_REFUND`  double      NULL     DEFAULT NULL COMMENT '支付平台退款总金额',
    `SYS_AMOUNT`   double      NULL     DEFAULT NULL COMMENT '平台支付总金额',
    `SYS_REFUND`   double      NULL     DEFAULT NULL COMMENT '平台退款总金额',
    `BIZ_AMOUNT`   double      NULL     DEFAULT NULL COMMENT '业务系统入账总金额(支付)',
    `BIZ_REFUND`   double      NULL     DEFAULT NULL COMMENT '业务系统出账总金额(退款)',
    `UPDATE_TIME`  datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`  datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_CHECK_DATE` (`CHECK_DATE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '支付对账表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_check_detail`
(
    `ID`           bigint                                                  NOT NULL COMMENT 'ID',
    `CHECK_ID`     bigint                                                  NULL     DEFAULT NULL COMMENT '对账ID',
    `CHECK_RESULT` tinyint                                                 NULL     DEFAULT NULL COMMENT '对账结果,0:对平, 1: 不平',
    `CHECK_DATE`   date                                                    NULL     DEFAULT NULL COMMENT '对账日期',
    `ORDER_ID`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '支付单',
    `TOTAL_FEE`    double                                                  NULL     DEFAULT NULL COMMENT '支付金额',
    `ORDER_TITLE`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付主题',
    `TARGET`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '支付目标,如账单号',
    `TRD_ORDER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '如：支付宝/微信等系统的订单id',
    `PAY_TIME`     datetime(0)                                             NULL     DEFAULT NULL COMMENT '支付时间',
    `PAY_CHANNEL`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'alipay:支付宝,wechat:微信,unionpay:银联',
    `UPDATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_CHECK_ID` (`CHECK_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '平台账单明细'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_check_detail_base`
(
    `ID`           bigint                                                  NOT NULL COMMENT 'ID',
    `CHECK_ID`     bigint                                                  NULL     DEFAULT NULL COMMENT '对账ID',
    `CHECK_DATE`   date                                                    NULL     DEFAULT NULL COMMENT '对账日期',
    `ORDER_ID`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '支付单',
    `TOTAL_FEE`    double                                                  NULL     DEFAULT NULL COMMENT '支付金额',
    `ORDER_TITLE`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付主题',
    `TARGET`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '支付目标,如账单号',
    `TRD_ORDER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '如：支付宝/微信等系统的订单id',
    `PAY_TIME`     datetime(0)                                             NULL     DEFAULT NULL COMMENT '支付时间',
    `PAY_CHANNEL`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'alipay:支付宝,wechat:微信,unionpay:银联',
    `UPDATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_CHECK_ID` (`CHECK_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '支付平台账单明细'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_check_detail_biz`
(
    `ID`           bigint                                                  NOT NULL COMMENT 'ID',
    `CHECK_ID`     bigint                                                  NULL     DEFAULT NULL COMMENT '对账ID',
    `CHECK_RESULT` tinyint                                                 NULL     DEFAULT NULL COMMENT '对账结果,0:对平, 1: 不平',
    `CHECK_DATE`   date                                                    NULL     DEFAULT NULL COMMENT '对账日期',
    `ORDER_ID`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '支付单',
    `TOTAL_FEE`    double                                                  NULL     DEFAULT NULL COMMENT '支付金额',
    `ORDER_TITLE`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '支付主题',
    `TARGET`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '支付目标,如账单号',
    `TRD_ORDER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '如：支付宝/微信等系统的订单id',
    `PAY_TIME`     datetime(0)                                             NULL     DEFAULT NULL COMMENT '支付时间',
    `PAY_CHANNEL`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'alipay:支付宝,wechat:微信,unionpay:银联',
    `UPDATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_CHECK_ID` (`CHECK_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '业务平台账单明细'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_payment`
(
    `ID`                bigint                                                         NOT NULL COMMENT 'ID',
    `ORDER_ID`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '支付单',
    `TOTAL_FEE`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '支付金额',
    `ORDER_TITLE`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '支付主题',
    `ORDER_STATE`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '00:支付成功；01:支付失败,但撤单退款；10:处理中，可能需要用户输入密码或指纹等确认支付；11:挂起；20:支付失败；30:订单作废；',
    `TARGET`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '支付目标,如账单号',
    `TRD_ORDER_ID`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '如：支付宝/微信等系统的订单id',
    `PAY_TIME`          datetime(0)                                                    NULL     DEFAULT NULL COMMENT '支付时间',
    `PAY_MODE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT 'passive：被扫支付',
    `PAY_CHANNEL`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT 'alipay:支付宝,wechat:微信,unionpay:银联',
    `PAY_CODE`          varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '付款码',
    `IDEMPOTENT_NO`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '支付流水',
    `ORDER_EXPIRE_MINS` int                                                            NULL     DEFAULT NULL COMMENT '以分钟为单位',
    `TRD_USER_ID`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '第三方支付系统用户,如支付宝uid、微信openid',
    `NOTIFY_RESULT`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '通知结果',
    `NOTIFY_TIME`       datetime(0)                                                    NULL     DEFAULT NULL COMMENT '通知时间',
    `UPDATE_TIME`       datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`       datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `IDX_ORDER_ID` (`ORDER_ID`) USING BTREE,
    INDEX `IDX_TARGET` (`TARGET`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付单'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pay_refund`
(
    `ID`                 bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `REFUND_ORDER_ID`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '退款订单ID',
    `REFUND_FEE`         int                                                           NULL     DEFAULT NULL COMMENT '退款金额,分',
    `REFUND_STATE`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '00:退款成功 10:退款中 20:退款失败',
    `TRD_REFUND_ID`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '第三方支付系统退款ID',
    `ORDER_ID`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '支付单',
    `TARGET`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '原支付单支付目标',
    `ACTUAL_REFUND_TIME` datetime(0)                                                   NULL     DEFAULT NULL COMMENT '退款时间',
    `REFUND_REASON`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '退款原因',
    `PAY_CHANNEL`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'alipay:支付宝,wechat:微信,unionpay:银联',
    `NOTIFY_RESULT`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '通知结果',
    `NOTIFY_TIME`        datetime(0)                                                   NULL     DEFAULT NULL COMMENT '通知时间',
    `UPDATE_TIME`        datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME`        datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `IDX_ORDER_ID` (`ORDER_ID`) USING BTREE,
    INDEX `IDX_REFUND_ORDER_ID` (`REFUND_ORDER_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '退款单'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`person_room_relation`
(
    `ID`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '唯一主键',
    `HotelID`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '酒店ID',
    `RoomNo`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '房间号',
    `OrderID`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '订单号',
    `OrderType`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '订单类型(Rsvn,Rcpn)',
    `CusAccID`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'XPMS客历ID',
    `PersonCode`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '佳都一码通编号',
    `IDType`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '证件类型(佳都证件类型代码)',
    `IDNO`         varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '证件号码',
    `CheckInDate`  datetime(0)                                                   NULL DEFAULT NULL COMMENT '入住时间',
    `CheckOutDate` datetime(0)                                                   NULL DEFAULT NULL COMMENT '离店时间',
    `SceneCode`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '场景代码(AssignRm,CheckOut),换房会先写入CheckOut,在写入AssignRoom',
    `CreateDate`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `Remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `SYNC_STATUS`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '0' COMMENT '同步状态，0未同步，1已同步，2同步失败',
    `SYNC_RESULT`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同步结果',
    `ReceiveID`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '接待单ID',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '酒店人房关系表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`pm_hotel_info`
(
    `ID`             bigint                                                        NOT NULL COMMENT '酒店ID',
    `PARENT_ID`      bigint                                                        NULL DEFAULT NULL COMMENT '父ID',
    `OUTER_CODE`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '外部唯一标识, 一码通加密ID',
    `HOTEL_NAME`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '场所区域名称',
    `HOTEL_ADDR`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `HOTEL_DESC`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '酒店描述',
    `TOTAL_AREA`     double                                                        NULL DEFAULT NULL COMMENT '场所面积',
    `TOTAL_BUILDING` int                                                           NULL DEFAULT NULL COMMENT '楼栋数',
    `TOTAL_ROOM`     int                                                           NULL DEFAULT NULL COMMENT '房间数',
    `GEO_JSON`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '坐标信息',
    `PLAN_JSON`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构平面示意图',
    `CREATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `STATUS`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'S0A' COMMENT '状态:S0R,就绪;S0A,有效',
    `REMARK`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '酒店信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`room_import`
(
    `id`             int                                                           NOT NULL AUTO_INCREMENT,
    `room_code`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `room_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `room_type`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`sbss_yq_ldpop`
(
    `ID`                                   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NOT NULL,
    `USERNAME`                             varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `PHONE`                                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL,
    `IDENTITY_TYPE`                        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL COMMENT '必填 1：身份证3：护照4：军官证6：港澳居民居住证7：台湾居民居住证 8：港澳居民来往内地通行证9：台湾居民来往内地通行证11：出入境通行证\r\n            ',
    `IDENTITY_NUM`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `AGE`                                  int                                                           NULL DEFAULT 0 COMMENT '年龄',
    `CONTACT_CONDITION`                    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '接触情况',
    `BASE_SICKNESS`                        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '基础性疾病',
    `FIRST_CONTACT_TIME`                   datetime(0)                                                   NULL DEFAULT NULL COMMENT '末次接触日期',
    `LAST_CONTACT_TIME`                    datetime(0)                                                   NULL DEFAULT NULL COMMENT '末次接触日期',
    `CONTACT_WAY`                          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '接触方式',
    `SEPERATE_BEGIN`                       datetime(0)                                                   NULL DEFAULT NULL COMMENT '开始隔离时间',
    `IS_CLINICAL_SYMPTOMS`                 varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL DEFAULT NULL COMMENT '是否出现临床症状 1:是 0否',
    `FIRST_SYMPTOMS_TIME`                  datetime(0)                                                   NULL DEFAULT NULL COMMENT '首次出现症状日期',
    `MANIPULATION_CLINICAL_MANIFESTATIONS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '手法临床表现',
    `IS_LAST_POSITIVE`                     varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          NULL DEFAULT NULL COMMENT '最终检测结果是否阳性 ',
    `NUCLEATE_POSITIVE_TIME`               datetime(0)                                                   NULL DEFAULT NULL COMMENT '核酸检测阳性标本采集日期',
    `FINAL_CLINICAL_RESOLUTION`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '最终临床解决',
    `SEPERATE_END`                         datetime(0)                                                   NULL DEFAULT NULL COMMENT '出院/解除隔离日期',
    `STATUS`                               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL COMMENT '状态',
    `CREATOR`                              varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL COMMENT '创建人',
    `CREATE_TIME`                          datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_USER`                          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL COMMENT '更新人',
    `UPDATE_TIME`                          datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `GRID_CODE`                            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NULL DEFAULT NULL COMMENT '网格编码',
    `BASE_SICKNESS_OTHER`                  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '其他基础性疾病',
    `CLINICAL_MANIFESTATIONS_OTHER`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin        NULL DEFAULT NULL COMMENT '其他临床表现描述',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `IDENTITY_NUM_UNIQUE_IDX` (`IDENTITY_NUM`) USING BTREE,
    INDEX `USERNAME_IDX` (`USERNAME`) USING BTREE,
    INDEX `PHONE_IDX` (`PHONE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '流调人员信息表 SBSS_YQ_LDPOP'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`sync_update_time_record`
(
    `table_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
    `max_update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '最大更新时间',
    `REMARK`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`table_name`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '同步时间记录表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`temp1202`
(
    `id`    bigint NOT NULL COMMENT '主键',
    `msgid` bigint NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_msgid` (`msgid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`un_sync_observer`
(
    `ID`              bigint                                                  NOT NULL,
    `PERSON_CODE`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `NAME`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `OBSERVER_STATUS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `ROOM_CODE`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_disinfect_task`
(
    `ID`              bigint                                                        NOT NULL COMMENT '主键',
    `TITLE`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `TYPE_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '任务类型',
    `AREA_TYPE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '区域类型',
    `PROCESS_MODE`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理方法',
    `PROCESS_COUNT`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理次数',
    `PRIORITY`        int                                                           NULL DEFAULT NULL COMMENT '紧急程度，1特急，2急，3一般',
    `REQUIREMENT`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务要求',
    `FILE_LIST`       json                                                          NULL COMMENT '附件',
    `PROC_TYPE`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程类型。T03:消杀',
    `APPLY_USER_CODE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行区域名称',
    `PLAN_END_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '规定完成时间',
    `FEEDBACK_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '反馈时间',
    `STATUS`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `REMARK`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `ROOM_CODE`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '房间编码',
    `ROOM_NAME`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '房间名称',
    `PROCESS_TYPE`    tinyint                                                       NULL DEFAULT NULL COMMENT '消杀类型,101:阴性消杀，102阳性消杀',
    `SOURCE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '来源，hsom，system',
    `CURR_USER`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '当前处理人',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PROCTYPE_STATUS` (`PROC_TYPE`, `STATUS`) USING BTREE,
    INDEX `IDX_AREA_STATUS` (`AREA_CODE`, `STATUS`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '消杀调度任务表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_emergency_task`
(
    `ID`               bigint                                                        NOT NULL COMMENT '主键',
    `TITLE`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '告警名称',
    `TYPE_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '告警类型',
    `ALARM_TIME`       datetime(0)                                                   NULL DEFAULT NULL COMMENT '告警时间',
    `DEVICE_TYPE`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型',
    `DEVICE_CODE`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '设备编码',
    `DEVICE_NAME`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
    `ADDRESS`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事发地',
    `ADDRESS_CODE`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址编码（房号）',
    `IMG_LIST`         json                                                          NULL COMMENT '图片地址',
    `VIDEO_LIST`       json                                                          NULL COMMENT '视频地址',
    `ALARM_PLAN`       json                                                          NULL COMMENT '告警预案',
    `PROC_TYPE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '流程类型。T05:应急',
    `APPLY_USER_CODE`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域名称',
    `FEEDBACK_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '反馈时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `EXECUTOR`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
    `EXECUTE_TYPE`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处置方式',
    `REMARK`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`      datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '来源，hsom，system',
    `URGENCY`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '紧急程度, 0：一般，1：紧急',
    `HEART_RATE`       int                                                           NULL DEFAULT NULL COMMENT '心率',
    `RESPIRATORY_RATE` int                                                           NULL DEFAULT NULL COMMENT '呼吸',
    `TURN`             int                                                           NULL DEFAULT NULL COMMENT '体动，翻身次数',
    `ISBED`            int                                                           NULL DEFAULT NULL COMMENT '在床状态（0：离床，1：在床，11：分析中，21：设备异常）',
    `SIGNAL_NUM`       int                                                           NULL DEFAULT NULL COMMENT '信号值',
    `ALARM_TYPE`       int                                                           NULL DEFAULT NULL COMMENT '预警类型（默认为0，BR07卫生间场景特有属性；0：正常，6：驻留时间超过n分钟，7：体征数据异常）',
    `LEAVE_TIME`       datetime(0)                                                   NULL DEFAULT NULL COMMENT '离场时间',
    `RECORD_TIME`      datetime(0)                                                   NULL DEFAULT NULL COMMENT '记录时间',
    `PERSON_CODE`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '一码通编码',
    `PERSON_NAME`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员姓名',
    `OBSERVER_ID`      bigint                                                        NULL DEFAULT NULL COMMENT '留观记录id',
    `PLAN_END_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '规定完成时间',
    `EXTRA`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '附件信息',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PROCTYPE_STATUS` (`PROC_TYPE`, `STATUS`) USING BTREE,
    INDEX `IDX_AREA_STATUS` (`AREA_CODE`, `STATUS`) USING BTREE,
    INDEX `IDX_SOURCE` (`SOURCE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '应急处置任务表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_emergency_task_his`
(
    `ID`               bigint                                                        NOT NULL COMMENT '主键',
    `TITLE`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '告警名称',
    `TYPE_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '告警类型',
    `ALARM_TIME`       datetime(0)                                                   NULL DEFAULT NULL COMMENT '告警时间',
    `DEVICE_TYPE`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型',
    `DEVICE_CODE`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '设备编码',
    `DEVICE_NAME`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
    `ADDRESS`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事发地',
    `ADDRESS_CODE`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址编码（房号）',
    `IMG_LIST`         json                                                          NULL COMMENT '图片地址',
    `VIDEO_LIST`       json                                                          NULL COMMENT '视频地址',
    `ALARM_PLAN`       json                                                          NULL COMMENT '告警预案',
    `PROC_TYPE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '流程类型。T05:应急',
    `APPLY_USER_CODE`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域名称',
    `FEEDBACK_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '反馈时间',
    `STATUS`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `EXECUTOR`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
    `EXECUTE_TYPE`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处置方式',
    `REMARK`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`      datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`      datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `SOURCE`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '来源，hsom，system',
    `URGENCY`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '紧急程度, 0：一般，1：紧急',
    `HEART_RATE`       int                                                           NULL DEFAULT NULL COMMENT '心率',
    `RESPIRATORY_RATE` int                                                           NULL DEFAULT NULL COMMENT '呼吸',
    `TURN`             int                                                           NULL DEFAULT NULL COMMENT '体动，翻身次数',
    `ISBED`            int                                                           NULL DEFAULT NULL COMMENT '在床状态（0：离床，1：在床，11：分析中，21：设备异常）',
    `SIGNAL_NUM`       int                                                           NULL DEFAULT NULL COMMENT '信号值',
    `ALARM_TYPE`       int                                                           NULL DEFAULT NULL COMMENT '预警类型（默认为0，BR07卫生间场景特有属性；0：正常，6：驻留时间超过n分钟，7：体征数据异常）',
    `LEAVE_TIME`       datetime(0)                                                   NULL DEFAULT NULL COMMENT '离场时间',
    `RECORD_TIME`      datetime(0)                                                   NULL DEFAULT NULL COMMENT '记录时间',
    `PERSON_CODE`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '一码通编码',
    `PERSON_NAME`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员姓名',
    `OBSERVER_ID`      bigint                                                        NULL DEFAULT NULL COMMENT '留观记录id',
    `PLAN_END_TIME`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '规定完成时间',
    `EXTRA`            text CHARACTER SET utf8 COLLATE utf8_general_ci               NULL COMMENT '附件信息'
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_observer_task`
(
    `ID`              bigint                                                        NOT NULL COMMENT '主键',
    `TITLE`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `TYPE_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '任务类型编码',
    `TYPE_NAME`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '任务类型名称',
    `CONTENT`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务内容',
    `REQUIREMENT`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务要求',
    `PRIORITY`        int                                                           NULL DEFAULT NULL COMMENT '紧急程度，1特急，2急，3一般',
    `FILE_LIST`       json                                                          NULL COMMENT '附件',
    `PROC_TYPE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '流程类型。T02:留观',
    `APPLY_USER_CODE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域名称',
    `PLAN_END_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '规定完成时间',
    `FEEDBACK_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '反馈时间',
    `STATUS`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `EXECUTOR`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
    `FEEDBACK`        tinyint                                                       NULL DEFAULT NULL COMMENT '执行反馈，0正常，1异常',
    `REMARK`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `SOURCE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '来源，hsom，system',
    `CURR_USER`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '当前处理人',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PROCTYPE_STATUS` (`PROC_TYPE`, `STATUS`) USING BTREE,
    INDEX `IDX_AREA_STATUS` (`AREA_CODE`, `STATUS`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '留观调度任务表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_proc_conf`
(
    `ID`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `PROC_TYPE`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '流程分类',
    `ITEM_CODE`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '表单项',
    `GROUP_CODE`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '分组',
    `SOURCE`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '来源',
    `PROC_KEY`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义标识',
    `SKIP_BPM`    tinyint                                                       NULL     DEFAULT NULL COMMENT '是否跳过流程引擎',
    `PRIORITY`    int                                                           NULL     DEFAULT 999 COMMENT '优先级, 数字越大优先级越低',
    `UPDATE_TIME` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `CREATE_TIME` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_ITEM_BPM_CONFIG_ITEMCODE` (`ITEM_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '流程配置表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_safety_task`
(
    `ID`              bigint                                                        NOT NULL COMMENT '主键',
    `TITLE`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `TYPE_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '任务类型',
    `CONTENT`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务内容',
    `PRIORITY`        int                                                           NULL DEFAULT NULL COMMENT '紧急程度，1特急，2急，3一般',
    `FILE_LIST`       json                                                          NULL COMMENT '附件',
    `PROC_TYPE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '流程类型。T04:安全值守',
    `APPLY_USER_CODE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行区域名称',
    `PLAN_END_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '规定完成时间',
    `FEEDBACK_TIME`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '反馈时间',
    `FEEDBACK`        tinyint                                                       NULL DEFAULT NULL COMMENT '反馈状态',
    `EXECUTOR`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行负责人',
    `STATUS`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `REMARK`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`     datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `SOURCE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '来源，hsom，system',
    `CURR_USER`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '当前处理人',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PROCTYPE_STATUS` (`PROC_TYPE`, `STATUS`) USING BTREE,
    INDEX `IDX_AREA_STATUS` (`AREA_CODE`, `STATUS`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '安全值守调度任务表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_supplier_task`
(
    `ID`              bigint                                                         NOT NULL COMMENT '主键',
    `TITLE`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '任务名称',
    `CONTENT_CODE`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '任务内容编码',
    `CONTENT`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '任务内容',
    `REQUIREMENT`     varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '任务要求',
    `PRIORITY`        int                                                            NULL     DEFAULT NULL COMMENT '紧急程度，1特急，2急，3一般',
    `FILE_LIST`       json                                                           NULL COMMENT '附件',
    `PROC_TYPE`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '流程类型。T01:物资',
    `APPLY_USER_CODE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '发起人',
    `AREA_CODE`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '执行区域编码',
    `AREA_NAME`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '执行区域名称',
    `PLAN_END_TIME`   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '规定完成时间',
    `ADDRESS_INFO`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '领用地',
    `ADDRESS_CODE`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '领用地编码',
    `STATUS`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '状态，S0W执行中，S0C已执行，S0D删除',
    `EXECUTOR`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '执行人',
    `FEEDBACK`        tinyint                                                        NULL     DEFAULT NULL COMMENT '执行反馈，0正常，1异常',
    `FEEDBACK_TIME`   datetime(0)                                                    NULL     DEFAULT NULL COMMENT '反馈时间',
    `REMARK`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `EXTEND_FORM`     json                                                           NULL COMMENT '扩展表单',
    `CREATE_TIME`     datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`     datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `SOURCE`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '来源，hsom，system',
    `CURR_USER`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '当前处理人',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_PROCTYPE_STATUS` (`PROC_TYPE`, `STATUS`) USING BTREE,
    INDEX `IDX_AREA_STATUS` (`AREA_CODE`, `STATUS`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '物资调度任务表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_task_disinfect_rel`
(
    `ID`            bigint                                                         NOT NULL COMMENT '主键',
    `BUSI_ID`       bigint                                                         NULL DEFAULT NULL COMMENT '调度任务id',
    `BUILDING_CODE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '楼栋编码',
    `BUILDING_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '楼栋名称',
    `BLOCK_CODE`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '房间/区域编码',
    `BLOCK_NAME`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '房间/区域名称',
    `BLOCK_COUNT`   int                                                            NULL DEFAULT NULL COMMENT '房间/区域数',
    `PROCESS_TYPE`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '消杀方式',
    `CLEAR_BY`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '检查人',
    `REMARK`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `EXECUTE_TIME`  datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '执行时间',
    `CREATE_TIME`   datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`   datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BUSIID` (`BUSI_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '消杀反馈清单'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_task_safety_rel`
(
    `ID`                    bigint                                                         NOT NULL COMMENT '主键',
    `BUSI_ID`               bigint                                                         NULL DEFAULT NULL COMMENT '调度任务id',
    `BUILDING_CODE`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '楼栋编码',
    `BUILDING_NAME`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '楼栋名称',
    `BLOCK_CODE`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '房间/区域编码，逗号分隔',
    `BLOCK_NAME`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '房间/区域名称，逗号分隔',
    `BLOCK_COUNT`           int                                                            NULL DEFAULT NULL COMMENT '房间/区域数',
    `DUTY`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '安保负责人',
    `SECURITY_PERSON`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL COMMENT '安保人员',
    `SECURITY_PERSON_COUNT` int                                                            NULL DEFAULT NULL COMMENT '安保人员数',
    `FEEDBACK`              tinyint                                                        NULL DEFAULT NULL COMMENT '执行反馈，0正常，1异常',
    `OVERTIME`              int                                                            NULL DEFAULT NULL COMMENT '是否超期，0否，1是',
    `REMARK`                varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `EXECUTE_TIME`          datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '执行时间',
    `CREATE_TIME`           datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `UPDATE_TIME`           datetime(0)                                                    NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BUSIID` (`BUSI_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '安全值守反馈清单'
  ROW_FORMAT = Dynamic;

CREATE TABLE `flyway_test`.`work_task_supplies_rel`
(
    `ID`            bigint                                                         NOT NULL COMMENT '主键',
    `BUSI_ID`       bigint                                                         NULL DEFAULT NULL COMMENT '调度任务id',
    `SUPPLIES_CODE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '物资类型编码',
    `SUPPLIES_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '物资类型',
    `FULL_NAME`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '物资类型全称',
    `SUPPLIES_NUM`  int                                                            NULL DEFAULT NULL COMMENT '数量',
    `REMARK`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `IDX_BUSIID_SUPPLUESCODE` (`BUSI_ID`, `SUPPLIES_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '调度任务物资关联表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;