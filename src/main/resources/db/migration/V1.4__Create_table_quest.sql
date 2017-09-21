CREATE TABLE IF NOT EXISTS `quest` (
	`Name`	TEXT NOT NULL,
	`Value`	INTEGER,
	`Category`	TEXT,
	`Description`	TEXT,
	PRIMARY KEY(`Name`)
);
INSERT INTO `quest` VALUES ('test_quest',50,'test_category','test_description');
INSERT INTO `quest` VALUES ('test_qiest1',200,'test_category1','test_description1');

