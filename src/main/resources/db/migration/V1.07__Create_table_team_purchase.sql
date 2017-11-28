CREATE TABLE IF NOT EXISTS `team_purchase` (
    `artifact_name` TEXT NOT NULL,
    `student_login` TEXT NOT NULL,
    `price` TEXT NOT NULL,
    `is_marked` INTEGER,
    FOREIGN KEY(`artifact_name`) REFERENCES `artifact`(`name`),
    FOREIGN KEY(`student_login`) REFERENCES `student`(`login`)
);
