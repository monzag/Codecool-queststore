CREATE TABLE IF NOT EXISTS `login` (
  `login`	TEXT UNIQUE NOT NULL,
  `password`	TEXT NOT NULL
);
INSERT INTO `login` VALUES ('admin','admin');
INSERT INTO `login` VALUES ('mentor','mentor');
INSERT INTO `login` VALUES ('student','student');