CREATE TABLE bill (
  idbill INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_idcustomer INTEGER UNSIGNED NOT NULL,
  funspark_recommendation_idfunspark_recommendation INTEGER UNSIGNED NOT NULL,
  emtour_recommendation_idemtour_recommendation INTEGER NOT NULL,
  paymentStatus BOOL NULL,
  PRIMARY KEY(idbill),
  INDEX bill_FKIndex1(emtour_recommendation_idemtour_recommendation),
  INDEX bill_FKIndex2(funspark_recommendation_idfunspark_recommendation),
  INDEX bill_FKIndex3(customer_idcustomer)
);

CREATE TABLE customer (
  idcustomer INTEGER UNSIGNED NOT NULL,
  name VARCHAR(255) NULL,
  birthDate DATE NULL,
  budget FLOAT NULL,
  desideredCity ENUM('PARIS','ROME','BUDAPEST') NULL,
  travelStartDate DATE NULL,
  travelEndDate DATE NULL,
  email VARCHAR(255) NULL,
  children INTEGER UNSIGNED NULL,
  adult INTEGER UNSIGNED NULL,
  PRIMARY KEY(idcustomer)
);

CREATE TABLE emtour_recommendation (
  idemtour_recommendation INTEGER NOT NULL AUTO_INCREMENT,
  customer_idcustomer INTEGER UNSIGNED NOT NULL,
  price FLOAT NULL,
  description VARCHAR(255) NULL,
  recomendedDestination ENUM('PARIS','ROME','BUDAPEST') NULL,
  PRIMARY KEY(idemtour_recommendation),
  INDEX emtour_recommendation_FKIndex1(customer_idcustomer)
);

CREATE TABLE funspark_recommendation (
  idfunspark_recommendation INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  emtour_recommendation_idemtour_recommendation INTEGER NOT NULL,
  price FLOAT NULL,
  description VARCHAR(255) NULL,
  PRIMARY KEY(idfunspark_recommendation),
  INDEX funspark_recommendation_FKIndex1(emtour_recommendation_idemtour_recommendation)
);


