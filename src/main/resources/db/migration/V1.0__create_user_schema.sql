CREATE TABLE IF NOT EXISTS `users` (

                                      `id` INT AUTO_INCREMENT PRIMARY KEY,
                                      `name` VARCHAR(255) NOT NULL,
                                      `email` VARCHAR(255) NOT NULL UNIQUE

) DEFAULT CHARSET=UTF8;