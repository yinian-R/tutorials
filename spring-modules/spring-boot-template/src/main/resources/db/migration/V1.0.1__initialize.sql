CREATE TABLE `user`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) DEFAULT NULL,
    `password` varchar(50) DEFAULT NULL,
    `birthday` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息';