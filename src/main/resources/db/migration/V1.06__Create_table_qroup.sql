CREATE TABLE IF NOT EXISTS `group` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `city_name` TEXT,
  `year` INTEGER,
  `number` INTEGER,
  FOREIGN KEY (`city_name`) REFERENCES `city`(`name`) ON DELETE SET NULL
);
INSERT INTO `group` VALUES (null, 'Kraków', 2017, 1);