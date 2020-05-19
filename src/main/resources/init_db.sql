CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`orders` (
  `order_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_1_idx` (`user_id`),
  CONSTRAINT `fk_orders_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`orders_products` (
  `order_id` bigint(11) NOT NULL,
  `product_id` bigint(11) NOT NULL,
  KEY `fk_orders_products_2_idx` (`order_id`),
  KEY `fk_orders_products_1_idx` (`product_id`),
  CONSTRAINT `fk_orders_products_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_products_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`products` (
  `product_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) NOT NULL,
  `price` decimal(11,0) NOT NULL,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `internet_shop`.`roles` (
  `role_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(225) NOT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `internet_shop`.`shopping_carts` (
  `shopping_cart_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`shopping_cart_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_shopping_carts_1_idx` (`user_id`),
  CONSTRAINT `fk_shopping_carts_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `internet_shop`.`shopping_carts_products` (
  `shopping_cart_id` bigint(11) NOT NULL,
  `product_id` bigint(11) NOT NULL,
  KEY `fk_shopping_carts_products_1_idx` (`shopping_cart_id`),
  KEY `fk_shopping_carts_products_2_idx` (`product_id`),
  CONSTRAINT `fk_shopping_carts_products_1` FOREIGN KEY (`shopping_cart_id`) REFERENCES `shopping_carts` (`shopping_cart_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shopping_carts_products_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `users` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(225) NOT NULL,
  `salt` varbinary(225) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
);

CREATE TABLE `internet_shop`.`users_roles` (
  `user_id` bigint(11) NOT NULL,
  `role_id` bigint(11) NOT NULL,
  KEY `fk_users_roles_2_idx` (`user_id`),
  KEY `fk_users_roles_1_idx` (`role_id`),
  CONSTRAINT `fk_users_roles_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_roles_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
