CREATE TABLE CHEF (
    ID SERIAL NOT NULL,
    USERNAME VARCHAR(30) NOT NULL,
	EMAIL VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	PASSWORD_RESET BOOLEAN,
	EMAIL_NOTIFICATIONS BOOLEAN,
	LAST_SELECTED_COOKBOOK_ID INTEGER,
	PRIMARY KEY (ID)
);

CREATE TABLE COOKBOOK (
    ID SERIAL NOT NULL ,
	NAME VARCHAR(30) NOT NULL,
	CREATOR_ID INT NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE RECIPE (
	ID SERIAL NOT NULL,
	TITLE VARCHAR(30) NOT NULL,
	DESCRIPTION VARCHAR(100),
	CALORIES INTEGER,
	INGREDIENT_LIST VARCHAR(2000),
	DIRECTIONS VARCHAR(2000),
	SERVING_SIZE INT,
	URL VARCHAR(2000),
	COOKBOOK_ID INT REFERENCES COOKBOOK(ID),
	PRIMARY KEY (ID)
);

CREATE TABLE INGREDIENT (
	ID SERIAL NOT NULL,
	NAME VARCHAR(30) NOT NULL,
	AMOUNT VARCHAR(30),
	RECIPE_ID INT REFERENCES RECIPE(ID),
	PRIMARY KEY (ID)
);

CREATE TABLE RECIPE_CATEGORIES (
	RECIPE_ID SERIAL NOT NULL,
	CATEGORIES VARCHAR(50)
);

CREATE TABLE ESTIMATED_TIME (
    ID INT REFERENCES RECIPE(ID),
	MINUTES INT,
	HOURS INT,
	PRIMARY KEY (ID)
);

CREATE TABLE CHEF_COOKBOOK (
  CHEF_ID INT REFERENCES CHEF(ID) NOT NULL,
  COOKBOOK_ID INT REFERENCES COOKBOOK(ID) NOT NULL,
  PRIMARY KEY (CHEF_ID, COOKBOOK_ID)
);

CREATE TABLE JOIN_COOKBOOK_REQUEST (
    ID SERIAL NOT NULL,
	COOKBOOK_ID INT NOT NULL,
	CHEF_ID INT NOT NULL,
	STATUS VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE CHEF_ROLES (
	CHEF_ID SERIAL NOT NULL,
	ROLES VARCHAR(50)
);

INSERT INTO CHEF (NAME, EMAIL, PASSWORD) VALUES ('The Chef', 'lisannewoudt@hotmail.com', '1');
INSERT INTO COOKBOOK (NAME, CHEF_ID) VALUES ('My first cookbook', 1);
INSERT INTO RECIPE (TITLE, DESCRIPTION, CALORIES, COOKBOOK_ID) VALUES ('My First Recipe', 'You should try this', 565, 1);
INSERT INTO INGREDIENT (NAME, AMOUNT, RECIPE_ID) VALUES ('Rice', '100 grams', 1);
INSERT INTO INGREDIENT (NAME, AMOUNT, RECIPE_ID) VALUES ('Paprika', '1 piece', 1);