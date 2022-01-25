CREATE TABLE IF NOT EXISTS hsom_flyway.auth_pass_record_bak5
SELECT *
FROM flyway_test.auth_pass_record;

INSERT INTO `flyway_test`.`auth_pass_record`(`ID`, `DEVICE_ID`, `ROOM_CODE`, `PERSON_ID`, `FULL_NAME`, `CARD_NUMBER`,
                                             `PERSON_CODE`, `VERIFICATION_MODE`,
                                             `VERIFICATION_TIME`, `VERIFICATION_RESULT`, `DIRECTION`, `CREATE_TIME`,
                                             `UPDATE_TIME`, `REMARK`,
                                             `PERSON_ATTRIBUTE`)
VALUES (14563062349045662, '4810081e28a34becb652a219f47c8cad', 'B7-2121AAAAAAA', '', NULL, NULL, '1463076768405934081', '3',
        '2021-11-05 01:08:13', '0', '0',
        '2021-11-05 01:08:13', '2021-11-05 01:06:54', NULL, NULL);
INSERT INTO `flyway_test`.`auth_pass_record`(`ID`, `DEVICE_ID`, `ROOM_CODE`, `PERSON_ID`, `FULL_NAME`, `CARD_NUMBER`,
                                             `PERSON_CODE`, `VERIFICATION_MODE`,
                                             `VERIFICATION_TIME`, `VERIFICATION_RESULT`, `DIRECTION`, `CREATE_TIME`,
                                             `UPDATE_TIME`, `REMARK`,
                                             `PERSON_ATTRIBUTE`)
VALUES (145644234443465, '4810081e28a34becb652a219f47c8cad', 'B7-2121', '', NULL, NULL, '1463076768405934081', '3',
        '2021-11-05 01:08:13', '0', '0',
        '2021-11-05 01:08:13', '2021-11-05 01:06:54', NULL, NULL);




