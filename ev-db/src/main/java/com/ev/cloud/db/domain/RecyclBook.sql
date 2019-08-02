-- auto Generated on 2019-07-03 05:56:02 
-- DROP TABLE IF EXISTS `recycl_book`; 
CREATE TABLE `recycl_book`(
    `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `book_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'bookName',
    `isbn` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'isbn',
    `author` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'author',
    `desc` VARCHAR (50) DEFAULT '' COMMENT 'desc',
    `max_price` DECIMAL (13,4) NOT NULL DEFAULT -1 COMMENT 'maxPrice',
    `common_price` DECIMAL (13,4) DEFAULT -1 COMMENT 'commonPrice',
    `pic_url` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'picUrl',
    `freight_id` INT (11) DEFAULT -1 COMMENT 'freightId',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`recycl_book`';
