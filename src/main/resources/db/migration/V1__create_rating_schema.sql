CREATE TABLE IF NOT EXISTS `rating` (

                                          `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `user_id` int NOT NULL,
                                          `recipe_id` int NOT NULL ,
                                          `fingers` int NOT NULL,
                                             FOREIGN KEY (user_id) REFERENCES user(id),
                                             FOREIGN KEY (recipe_id) REFERENCES recipe(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;