INSERT INTO Employee(name, email, username, password, roles)
VALUES ('Hannibal Vestergaard', 'Hannibal24@gmail.com', 'Hannimal', 'pass1234','MANAGER');
INSERT INTO project (name, start_date, deadline, budget, description, status)
VALUES ('Project Omega', '2024-01-15', '2024-12-15', 75000.50, 'An innovative project to enhance AI capabilities.', FALSE),
       ('FlyHigh', '1999-01-01', '2099-12-12',5000,'Deport dwarfs to mars', FALSE),
       ('Hell', '1029-12-12', '2000-12-12', 50000, 'find nemo', FALSE );

INSERT INTO sub_project(project_id, name, start_date, deadline, budget, description, status)
VALUES(1, 'codelab', '2000-12-02', '2002-02-02', 4500, 'codelab lets go', FALSE);

INSERT INTO employee(username, password, name, email, roles)
VALUES ('niko123', 'nikoniko', 'Nikolaj Panema', 'niko1234@gmail.com', 'INTERN'),
       ('nielssss', 'niko', 'Niels Svendsen', 'niels1234@gmail.com', 'INTERN'),
       ('LarsOG', 'larsmedhars', 'Lars Adelsborg', 'Lars1234@gmail.com', 'INTERN'),
       ('Mads', 'MadsAttack', 'Mads Olufsen', 'Mads1234@gmail.com', 'INTERN');

INSERT INTO employee (username, password, name, email, roles)
VALUES
    ('jdoe', 'password123', 'John Doe', 'jdoe@example.com', 'JUNIOR'),
    ('asmith', 'passw0rd', 'Alice Smith', 'asmith@example.com', 'SENIOR'),
    ('bwhite', '12345pass', 'Bob White', 'bwhite@example.com', 'INTERN'),
    ('123', '123', 'Kate Martin', 'kmartin@example.com', 'MANAGER'),
    ('ltaylor', 'taylor!123', 'Liam Taylor', 'ltaylor@example.com', 'SENIOR'),
    ('hgrace', 'securePass1', 'Hannah Grace', 'hgrace@example.com', 'JUNIOR');

INSERT INTO project_team (project_id, employee_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2,5),
       (3,6);



INSERT INTO sub_project (name, start_date, deadline, budget, description, status, project_id)
VALUES ('Fix Deployment', '2024-01-02', '2024-12-18', 100.000, 'FIx the deployment to Azure', TRUE, 1);

INSERT INTO task (sub_project_id, name, start_date, deadline, duration, description, status, priority)
VALUES (1, 'Meeting', '2024-01-12', '2024-04-12', 6, 'Get together', false, 1),
(1,'Debugging', '2024-01-12', '2024-02-12', 6, 'Current branch', true, 5),
       (1,'Controller class', '2024-01-12', '2024-01-12', 6, 'Current branch', true, 5);

INSERT INTO task_employee (task_id, employee_id)
VALUES (1,1),
       (3,3),
       (3,4);



