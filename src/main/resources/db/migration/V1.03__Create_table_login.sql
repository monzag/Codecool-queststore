CREATE TABLE IF NOT EXISTS `login` (
  `login`	TEXT UNIQUE NOT NULL,
  `password`	TEXT NOT NULL,
  FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL
);
INSERT INTO `login` VALUES ('admin','admin');
INSERT INTO `login` VALUES ('jan','KRK.2017.2','A2',0,0);