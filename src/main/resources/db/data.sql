INSERT INTO project (name, start_date, deadline, budget, description, status)
VALUES ('Project Omega', '2024-01-15', '2024-12-15', 75000.50, 'An innovative project to enhance AI capabilities.', TRUE);

INSERT INTO employee(username, password, name, email, roles)
VALUES ('niko123', 'nikoniko', 'Nikolaj Panema', 'niko1234@gmail.com', 'INTERN'),
       ('niko1222', 'niko', 'Niels', 'niels1234@gmail.com', 'INTERN');

INSERT INTO project_team (project_id, employee_id)
VALUES (1, 1),
(1, 2);


INSERT INTO sub_project (name, start_date, deadline, budget, description, status, project_id)
VALUES ('Fix Deployment', '2024-01-02', '2024-12-18', 100.000, 'FIx the deployment to Azure', TRUE, 1);

INSERT INTO task (sub_project_id, name, start_date, deadline, duration, description, status, priority)
VALUES (1, 'Meeting', '2024-01-12', '2024-04-12', 6, 'Get together', false, 1),
(1,'Debugging', '2024-01-12', '2024-02-12', 6, 'Current branch', true, 5),
       (1,'Controller class', '2024-01-12', '2024-01-12', 6, 'Current branch', true, 5);

INSERT INTO task_employee (task_id, employee_id)
VALUES (1,1);