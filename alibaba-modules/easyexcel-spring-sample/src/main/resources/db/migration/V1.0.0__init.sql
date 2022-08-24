create table book
(
    id          bigint not null auto_increment,
    name        varchar(50),
    book_type   varchar(50),
    author      varchar(50),
    description text,
    create_time datetime,
    primary key (id)
);


INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '水文随机分析', '自然科学', 'XXX', NULL, '2022-08-10 14:53:51');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '地球概论', '自然科学', 'QQQ', NULL, '2022-08-10 14:54:17');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '火星概论', '自然科学', 'QQQ', NULL, '2022-08-10 14:54:17');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '太阳概论', '自然科学', 'QQQ', NULL, '2022-08-10 14:54:17');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '流星概论', '自然科学', 'QQQ', NULL, '2022-08-10 14:54:17');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( '水分子概论', '自然科学', 'QQQ', NULL, '2022-08-10 14:54:17');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( 'Java语言', '计算机', 'WWW', NULL, '2022-08-10 14:54:36');
INSERT INTO `book`( `name`, `book_type`, `author`, `description`, `create_time`) VALUES ( 'JavaScript图解', '计算机', 'RRR', 'JavaScript图解JavaScript图解JavaScript图解', '2022-08-10 14:54:58');


