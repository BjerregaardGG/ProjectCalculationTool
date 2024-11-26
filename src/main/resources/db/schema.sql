CREATE SCHEMA IF NOT EXISTS ProjectCalculationDB;

DROP TABLE IF EXISTS Account CASCADE;
DROP TABLE IF EXISTS Employee CASCADE;
DROP TABLE IF EXISTS Project CASCADE;
DROP TABLE IF EXISTS ProjectTeam CASCADE;
DROP TABLE IF EXISTS Task CASCADE;
DROP TABLE IF EXISTS TaskEmployee CASCADE;
DROP TABLE IF EXISTS SubProject CASCADE;
DROP TABLE IF EXISTS DoneTask CASCADE;

CREATE TABLE Account
(
    accountId INTEGER NOT NULL AUTO_INCREMENT,
    userName  VARCHAR(30),
    password  VARCHAR(40),
    PRIMARY KEY (accountId)
);

CREATE TABLE Employee
(
    employeeId INTEGER NOT NULL AUTO_INCREMENT,
    accountId  INTEGER,
    name       VARCHAR(30),
    email      VARCHAR(30) UNIQUE,
    PRIMARY KEY (employeeId),
    FOREIGN KEY (accountId) REFERENCES Account (accountId) ON DELETE CASCADE
);

CREATE TABLE Project
(
    projectId INTEGER NOT NULL AUTO_INCREMENT,
    employeeId INTEGER,
    name VARCHAR(30),
    startDate DATE,
    deadline DATE,
    budget DOUBLE,
    description VARCHAR(100),
    PRIMARY KEY (projectId),
    FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE
);

CREATE TABLE SubProject
(
    subProjectId INTEGER NOT NULL AUTO_INCREMENT,
    projectId INTEGER,
    name VARCHAR(30),
    dateDate DATE,
    deadline DATE,
    subProjectBudget DOUBLE,
    description VARCHAR(100),
    PRIMARY KEY (subProjectId),
    FOREIGN KEY (projectId) REFERENCES Project(projectId) ON DELETE CASCADE
);

CREATE TABLE ProjectTeam
(
    projectId INTEGER,
    employeeId INTEGER,
    PRIMARY KEY (projectId, employeeId),
    FOREIGN KEY (projectId) REFERENCES Project (projectId) ON DELETE CASCADE,
    FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE
);

CREATE TABLE Task
(
    taskId INTEGER NOT NULL AUTO_INCREMENT,
    subProjectId INTEGER,
    name VARCHAR(30),
    startDate DATE,
    deadline DATE,
    duration DOUBLE,
    description VARCHAR(100),
    PRIMARY KEY (taskId),
    FOREIGN KEY (subProjectId) REFERENCES SubProject ON DELETE CASCADE
);



CREATE TABLE DoneTask
(
    doneTaskId INTEGER NOT NULL AUTO_INCREMENT,
    taskId INTEGER,
    name VARCHAR(30),
    startDate DATE,
    deadline DATE,
    Duration double,
    Description VARCHAR (100),
    PRIMARY KEY (doneTaskId),
    FOREIGN KEY (taskId) REFERENCES Task(taskId) ON DELETE CASCADE
);

CREATE TABLE TaskEmployee
(
    taskId INTEGER,
    employeeId INTEGER,
    PRIMARY KEY (taskId, employeeId),
    FOREIGN KEY (taskId) REFERENCES Task (taskId) ON DELETE CASCADE,
    FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE
);

