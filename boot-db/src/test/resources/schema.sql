CREATE TABLE `t_users` (
                           `user_id` bigint NOT NULL AUTO_INCREMENT,
                           `user_name` varchar(50) DEFAULT NULL,
                           `birthday` date DEFAULT NULL,
                           `email` varchar(100) DEFAULT NULL,
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `t_addresses` (
                               `address_id` bigint NOT NULL AUTO_INCREMENT,
                               `addr` varchar(255) NOT NULL,
                               `postcode` varchar(10) DEFAULT NULL,
                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`address_id`)
) ENGINE=InnoDB;

CREATE TABLE `t_users_addresses` (
                                     `user_address_id` bigint NOT NULL AUTO_INCREMENT,
                                     `user_id` bigint NOT NULL,
                                     `address_id` bigint NOT NULL,
                                     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`user_address_id`)
) ENGINE=InnoDB;