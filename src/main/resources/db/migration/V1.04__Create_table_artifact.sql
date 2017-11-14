CREATE TABLE IF NOT EXISTS `artifact` (
	`name`	TEXT NOT NULL UNIQUE,
	`price`	INTEGER NOT NULL,
	`description`	TEXT NOT NULL,
	PRIMARY KEY(`name`)
);

CREATE TABLE IF NOT EXISTS `owned_artifact` (
  `artifact_name`	TEXT NOT NULL,
  `owner_name`	TEXT NOT NULL,
	FOREIGN KEY(`artifact_name`) REFERENCES `artifact`(`name`) ON DELETE SET NULL,
  FOREIGN KEY(`owner_name`) REFERENCES `student`(`login`) ON DELETE SET NULL
);
