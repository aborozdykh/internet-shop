CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

  CREATE TABLE `internet_shop`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(11) NOT NULL,
  PRIMARY KEY (`product_id`));