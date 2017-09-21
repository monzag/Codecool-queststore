CREATE TABLE IF NOT EXISTS `quest` (
	`Name`	TEXT NOT NULL,
	`Value`	INTEGER,
	`Category`	TEXT,
	`Description`	TEXT,
	PRIMARY KEY(`Name`)
);

CREATE TABLE IF NOT EXISTS `done_quest` (
  `id`	TEXT NOT NULL,
  `quest_name`	TEXT,
  `owner_name`	TEXT,
  `date`	TEXT,
  FOREIGN KEY(`quest_name`) REFERENCES `quest`(`name`) ON DELETE SET NULL,
  PRIMARY KEY(`id`),
  FOREIGN KEY(`owner_name`) REFERENCES `student`(`name`) ON DELETE SET NULL
);

