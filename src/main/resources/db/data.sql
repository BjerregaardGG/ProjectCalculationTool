INSERT INTO project (name, start_date, deadline, budget, description, status)
VALUES ('Project Omega', '2024-01-15', '2024-12-15', 75000.50, 'An innovative project to enhance AI capabilities.', TRUE),
       ('FlyHigh', '1999-01-01', '2099-12-12',5000,'Deport dwarfs to mars', FALSE);

INSERT INTO sub_project(project_id, name, start_date, deadline, budget, description, status) VALUES
                                                                                                    (
                                                                                                    1, 'codelab', '2000-12-02', '2002-02-02', 4500, 'codelab lets go', FALSE
                                                                                                   );
