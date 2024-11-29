INSERT INTO project (name, start_date, deadline, budget, description, status)
VALUES ('Project Omega', '2024-01-15', '2024-12-15', 75000.50, 'An innovative project to enhance AI capabilities.', TRUE);

INSERT INTO employee(username, password, name, email, roles)
VALUES ('niko123', 'nikoniko', 'Nikolaj Panema', 'niko1234@gmail.com', 'INTERN');

INSERT INTO project_team (project_id, employee_id)
VALUES (1, 1);

INSERT INTO sub_project (name, start_date, deadline, budget, description, status)
VALUES ('Fix Deployment', '2024-01-02', '2024-12-18', 100.000, 'FIx the deployment to Azure', TRUE);

