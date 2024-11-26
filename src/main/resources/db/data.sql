INSERT INTO Account(username, password)
VALUES ('Hannibal24', 'HansiHinterseer02'),
       ('Wasim13', '123og4'),
       ('WalleMontana', 'AltidAltid');

INSERT INTO Employee(name, email, accountId)
VALUES ('Hannibal Vestergaard', 'Hannibal24@gmail.com',
           SELECT accountId FROM ACCOUNT WHERE Account.username = 'Hannibal24');


