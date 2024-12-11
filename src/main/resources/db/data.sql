INSERT INTO project (name, start_date, deadline, budget, description, status,work_hours_per_project)
VALUES ('Project Omega', '2024-01-15', '2024-12-15', 75000.50, 'An innovative project to enhance AI capabilities.', FALSE, 4),
       ('FlyHigh', '1999-01-01', '2099-12-12',5000,'Deport dwarfs to mars', FALSE, 8),
       ('Hell', '1029-12-12', '2000-12-12', 50000, 'find nemo', FALSE, 8 ),
       ('Project Titan', '2024-02-01', '2025-02-01', 100000.75, 'A project to develop a quantum computing platform.', FALSE, 10),
       ('AI for Good', '2024-03-15', '2025-03-15', 120000.50, 'Building AI tools to address social issues and enhance education.', FALSE, 6),
       ('Space Mining', '2024-04-01', '2026-04-01', 200000.00, 'Exploring and developing technologies for asteroid mining.', FALSE, 8),
       ('SmartCityX', '2024-05-01', '2025-05-01', 50000.00, 'Developing IoT infrastructure for smart cities.', FALSE, 7);

INSERT INTO sub_project(project_id, name, start_date, deadline, budget, description, status)
VALUES(1, 'Code lab', '2000-12-02', '2002-02-02', 4500, 'code lab lets go', FALSE),
      (2, 'Rocket Prototype', '2024-01-10', '2024-07-10', 20000, 'Build a prototype for the rocket to Mars.', FALSE),
      (3, 'Data Collection', '2024-04-01', '2025-02-01', 15000, 'Gather and analyze data on space objects for mining.', TRUE),
      (4, 'IoT Development', '2024-06-01', '2025-06-01', 30000, 'Develop the IoT devices and sensors for the smart city project.', FALSE),
      (5, 'AI Development', '2024-05-10', '2025-05-10', 25000, 'Develop AI models for urban planning and infrastructure.', TRUE),
      (5, 'Fix Deployment', '2024-01-02', '2024-12-18', 100.000, 'FIx the deployment to Azure', TRUE);

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
    ('hgrace', 'securePass1', 'Hannah Grace', 'hgrace@example.com', 'JUNIOR'),
    ('Hannimal', 'pass1234', 'Hannibal Vestergaard', 'Hannibal24@gmail.com','MANAGER'),
       ('Mads', 'MadsAttack', 'Mads Olufsen', 'Mads1234@gmail.com', 'INTERN'),
       ('jdoe', 'password123', 'John Doe', 'jdoe@example.com', 'JUNIOR'),
       ('asmith', 'passw0rd', 'Alice Smith', 'asmith@example.com', 'SENIOR'),
       ('bwhite', '12345pass', 'Bob White', 'bwhite@example.com', 'INTERN'),
       ('123', '123', 'Kate Martin', 'kmartin@example.com', 'MANAGER'),
       ('ltaylor', 'taylor!123', 'Liam Taylor', 'ltaylor@example.com', 'SENIOR'),
       ('hgrace', 'securePass1', 'Hannah Grace', 'hgrace@example.com', 'JUNIOR'),
       ('Hannibal Vestergaard', 'Hannibal24@gmail.com', 'Hannibal', 'pass1234','MANAGER'),
       ('Lone','lone123', 'Lone Jensen', 'lone123@example.com','SENIOR'),
       ('Henrik','henny','Henrik Madsen','henny@example.com','INTERN'),
       ('Michael123','mic123','Michael Laudrup','laudrip@example.com','SENIOR'),
       ('juju','jul2024','Julie Juju','juju321@example.com','INTERN');

INSERT INTO project_team (project_id, employee_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2,5),
       (3,6);

INSERT INTO task (sub_project_id, name, start_date, deadline, duration, description, status, priority)
VALUES (1, 'Meeting', '2024-01-12', '2024-04-12', 6, 'Get together', FALSE, 1),
       (1,'Debugging', '2024-01-12', '2024-02-12', 6, 'Current branch', TRUE, 5),
       (1,'Controller class', '2024-01-12', '2024-01-12', 6, 'Current branch', TRUE, 5),
       (4, 'Unit-test', '2024-01-12','2024-02-12',2, 'successful unit-tests', TRUE, 5),
       (4, 'Integration-test','2024-04-12','2024-07-12',3, 'successful integration-tests', TRUE, 4);

INSERT INTO task_employee (task_id, employee_id)
VALUES (1,1),
       (3,3),
       (3,4);



