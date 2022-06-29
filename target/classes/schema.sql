CREATE TABLE if not exists assignments (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    receiver NVARCHAR(100) NOT NULL,
    deadline DATE NOT NULL,
    taskId INTEGER NOT NULL,
    token VARCHAR(500) NOT NULL
);
