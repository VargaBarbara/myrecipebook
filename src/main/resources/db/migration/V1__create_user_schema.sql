CREATE TABLE IF NOT EXISTS `user` (

                                          `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `name` varchar(255) NOT NULL,
                                          `email` varchar(255) NOT NULL UNIQUE

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;