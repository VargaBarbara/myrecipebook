CREATE TABLE IF NOT EXISTS `ratings` (



                                        `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        `user_id` int NOT NULL,
                                        `recipe_id` int NOT NULL ,
                                        `fingers` int NOT NULL,
                                        FOREIGN KEY (user_id) REFERENCES users(id),
                                        FOREIGN KEY (recipe_id) REFERENCES recipes(id)

) DEFAULT CHARSET=UTF8;