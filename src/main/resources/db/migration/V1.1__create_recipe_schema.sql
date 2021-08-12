CREATE TABLE IF NOT EXISTS `recipe` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `preparation` varchar(4000) NOT NULL,
    `note` varchar(255),
    `creator_id` int NOT NULL,
    `creation_date` DATE NOT NULL,
    `last_edit_date` DATE NOT NULL,
     FOREIGN KEY (creator_id) REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;