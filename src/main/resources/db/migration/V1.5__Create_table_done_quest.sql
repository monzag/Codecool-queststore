CREATE TABLE IF NOT EXISTS `Done_quest` (
	`Id`	TEXT NOT NULL,
	`Quest_name`	TEXT,
	`Owner_name`	TEXT,
	`Date`	TEXT,
	FOREIGN KEY(`Quest_name`) REFERENCES `quest`(`name`) ON DELETE SET NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`Owner_name`) REFERENCES `student`(`name`) ON DELETE SET NULL
);

