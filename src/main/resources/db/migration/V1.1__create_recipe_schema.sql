CREATE TABLE IF NOT EXISTS `recipes` (

                                      `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      `preparation` varchar(4000) NOT NULL,
                                      `note` varchar(255),
                                      `creator_id` int,
                                      `creation_date` DATE NOT NULL,
                                      `last_edit_date` DATE NOT NULL,
                                      FOREIGN KEY (creator_id) REFERENCES users(id)

) DEFAULT CHARSET=UTF8;