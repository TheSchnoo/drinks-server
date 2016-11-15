CREATE TABLE drink (
  drinkId int NOT NULL AUTO_INCREMENT,
  drinkName varchar(255),
  breweryId int,
  ibu int,
  abv float,
  tastingId int,
  PRIMARY KEY(drinkId),
  UNIQUE KEY(drinkName, breweryId),
  FOREIGN KEY(breweryId) REFERENCES brewery(breweryId),
);

CREATE TABLE brewery (
  breweryId int NOT NULL AUTO_INCREMENT,
  breweryName varchar(255),
  city varchar(255),
  PRIMARY KEY(breweryId)
);

CREATE TABLE tasting (
  tastingId int NOT NULL AUTO_INCREMENT,
  drinkId int,
  tag varchar(50),
  PRIMARY KEY(tastingId),
  FOREIGN KEY(drinkId) REFERENCES drink(drinkId)
);

INSERT INTO courses VALUES ("CPSC", 110, "Computation, Programs, and Programming", "Computation, Programs, and Programming", 4);
INSERT INTO courses VALUES ("CPSC", 210, "Software Construction", "Software Construction", 4);
INSERT INTO courses VALUES ("CPSC", 310, "Introduction to Software Engineering", "Introduction to Software Engineering", 4);

INSERT INTO prerequisites VALUES("CPSC", 210, "CPSC", 110);
INSERT INTO prerequisites VALUES("CPSC", 310, "CPSC", 210);


INSERT INTO requirements VALUES ("All CPSC *10", "CPSC", 110);
INSERT INTO requirements VALUES ("All CPSC *10", "CPSC", 210);
INSERT INTO requirements VALUES ("All CPSC *10", "CPSC", 310);
INSERT INTO requirements VALUES ("All CPSC *10", "CPSC", 410);
