CREATE TABLE IF NOT EXISTS `login` (
  `login`	TEXT UNIQUE NOT NULL,
  `password`	TEXT NOT NULL,
  FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL
);
INSERT INTO `login` VALUES ('admin','admin');