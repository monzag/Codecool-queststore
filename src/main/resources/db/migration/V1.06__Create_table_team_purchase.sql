CREATE TABLE IF NOT EXISTS `team_purchase` (
    `id` INTEGER NOT NULL,
    `artifact_name` TEXT NOT NULL,
    `student_login` TEXT NOT NULL,
    `price` TEXT NOT NULL,
    `is_marked` INTEGER,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`artifact_name`) REFERENCES `artifact`(`name`),
    FOREIGN KEY(`student_login`) REFERENCES `student`(`login`)
);
