CREATE TABLE IF NOT EXISTS `quest` (
	`name`	TEXT NOT NULL,
  `description`	TEXT,
	`reward`	INTEGER,
	PRIMARY KEY(`name`)
);

CREATE TABLE IF NOT EXISTS `done_quest` (
  `quest_name`	TEXT,
  `owner_name`	TEXT,
  FOREIGN KEY(`quest_name`) REFERENCES `quest`(`name`) ON DELETE SET NULL,
  FOREIGN KEY(`owner_name`) REFERENCES `student`(`login`) ON DELETE SET NULL
);

