CREATE TABLE wallet (
    coolcoins INTEGER NOT NULL,
    balance INTEGER NOT NULL,
    FOREIGN KEY('student_login') REFERENCES Student('login') ON DELETE SET NULL;
);
