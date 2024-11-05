CREATE TABLE `order` (
	`id` CHAR(10) PRIMARY KEY,
	`customer_id` CHAR(10) NOT NULL
);

CREATE TABLE `address` (
	`id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`order_id` CHAR(10) NOT NULL,
	`first_name` VARCHAR(32) NOT NULL,
	`last_name` VARCHAR(32) NOT NULL,
	`street` VARCHAR(64) NOT NULL,
	`zip_code` CHAR(5) NOT NULL,
	`city` VARCHAR(32) NOT NULL,
	`country` VARCHAR(32) NOT NULL,
	CONSTRAINT `fk_address_order` FOREIGN KEY (`order_id`)
	REFERENCES `order` (`id`) ON DELETE CASCADE
);
