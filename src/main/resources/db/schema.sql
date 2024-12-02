CREATE SCHEMA IF NOT EXISTS ProjectCalculationDB;

DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS project_team CASCADE;
DROP TABLE IF EXISTS task CASCADE;
DROP TABLE IF EXISTS task_employee CASCADE;
DROP TABLE IF EXISTS sub_project CASCADE;


CREATE TABLE employee
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    username VARCHAR(30),
    password VARCHAR(40),
    name VARCHAR(30),
    email VARCHAR(30) UNIQUE,
    roles VARCHAR(20),
    CONSTRAINT chk_roles CHECK (roles IN ('INTERN', 'JUNIOR', 'SENIOR', 'MANAGER')),
    PRIMARY KEY (id)
);

CREATE TABLE project
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    employee_id INTEGER,
    name VARCHAR(30),
    start_date DATE,
    deadline DATE,
    budget DOUBLE,
    description VARCHAR(100),
    status BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);

CREATE TABLE sub_project
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    project_id INTEGER,
    name VARCHAR(30),
    start_date DATE,
    deadline DATE,
    budget DOUBLE,
    description VARCHAR(100),
    status BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE project_team
(
    project_id INTEGER,
    employee_id INTEGER,
    PRIMARY KEY (project_id, employee_id),
    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);

CREATE TABLE task
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    sub_project_id INTEGER,
    name VARCHAR(30),
    start_date DATE,
    deadline DATE,
    duration DOUBLE,
    description VARCHAR(100),
    status BOOLEAN,
    priority INTEGER DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (sub_project_id) REFERENCES sub_project ON DELETE CASCADE
);

CREATE TABLE task_employee
(
    task_id INTEGER,
    employee_id INTEGER,
    PRIMARY KEY (task_id, employee_id),
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);