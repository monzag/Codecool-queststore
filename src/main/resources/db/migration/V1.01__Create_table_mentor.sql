CREATE TABLE IF NOT EXISTS `city` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name` TEXT NOT NULL UNIQUE,
	`short` TEXT NOT NULL UNIQUE
);
INSERT INTO `city` VALUES (null, 'Kraków', 'krk');
INSERT INTO `city` VALUES (null, 'Miszkolc', 'misz');


CREATE TABLE IF NOT EXISTS `group` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`city_name` TEXT,
	`year` INTEGER,
	`number` INTEGER,
	FOREIGN KEY (`city_name`) REFERENCES `city`(`name`) ON DELETE SET NULL
);
INSERT INTO `group` VALUES (null, 'Kraków', 2017, 1);

CREATE TABLE IF NOT EXISTS `mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_id` INTEGER,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL,
	FOREIGN KEY (`group_id`) REFERENCES `group`(`id`) ON DELETE SET NULL
);
INSERT INTO `mentor` VALUES ('mentor', 1);
