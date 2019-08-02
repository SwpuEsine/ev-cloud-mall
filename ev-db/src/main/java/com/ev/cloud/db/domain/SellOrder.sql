-- auto Generated on 2019-07-03 15:54:41 
-- DROP TABLE IF EXISTS `sell_order`; 
CREATE TABLE `sell_order`(
    `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` INT (11) NOT NULL DEFAULT -1 COMMENT 'userId',
    `recycle_book_id` INT (11) NOT NULL DEFAULT -1 COMMENT 'recycleBookId',
    `number` INT (11) NOT NULL DEFAULT -1 COMMENT 'number',
    `phone` INT (11) NOT NULL DEFAULT -1 COMMENT 'phone',
    `region` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'region',
    `address_detail` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'addressDetail',
    `status` SMALLINT (5) NOT NULL DEFAULT -1 COMMENT 'status',
    `appoint_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'appointTime',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'createTime',
    `reserve_price` DECIMAL (13,4) DEFAULT -1 COMMENT 'reservePrice',
    `actual_price` DECIMAL (13,4) DEFAULT -1 COMMENT 'actualPrice',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`sell_order`';
