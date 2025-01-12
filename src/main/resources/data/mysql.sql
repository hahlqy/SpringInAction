CREATE TABLE ingredient (
  id varchar(4) NOT NULL,
  name varchar(25) NOT NULL,
  type varchar(10) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE taco (
   id int NOT NULL AUTO_INCREMENT,
   name varchar(50) NOT NULL,
   createAt timestamp NOT NULL,
   PRIMARY KEY (id)
) ;


CREATE TABLE taco_ingredients (
   taco int NOT NULL,
   ingredient varchar(4) NOT NULL
) ;

alter table Taco_Ingredients
    add foreign key (taco) references Taco(id);
alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient(id);

CREATE TABLE taco_order (
   id int NOT NULL AUTO_INCREMENT,
   deliveryName varchar(50) NOT NULL,
   deliveryStreet varchar(50) NOT NULL,
   deliveryCity varchar(50) NOT NULL,
   deliveryState varchar(2) NOT NULL,
   deliveryZip varchar(10) NOT NULL,
   ccNumber varchar(16) NOT NULL,
   ccExpiration varchar(5) NOT NULL,
   ccCVV varchar(3) NOT NULL,
   placeAt timestamp NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE taco_order_tacos (
   tacoOrder int NOT NULL,
   taco int NOT NULL
) ;

alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order(id);

alter table Taco_Order_Tacos
    add foreign key (taco) references Taco(id);



insert into Ingredient (id,name,type)
values ('FLTO','Flour Tortilla', 'WRAP');

insert into Ingredient (id,name,type)
values ('COTO','Corn Tortilla', 'WRAP');

insert into Ingredient (id,name,type)
values ('GRBF','Ground Beef', 'PROTEIN');

insert into Ingredient (id,name,type)
values ('CARN','Carnitas', 'PROTEIN');
insert into Ingredient (id,name,type)
values ('TMTO','Dices Tomatoes', 'VEGGIES');
insert into Ingredient (id,name,type)
values ('LETC','Lettuce', 'VEGGIES');
insert into Ingredient (id,name,type)
values ('CHED','Cheddar', 'CHEESE');
insert into Ingredient (id,name,type)
values ('JACK','Monterrey Jack', 'CHEESE');
insert into Ingredient (id,name,type)
values ('SLSA','Salsa', 'SAUCE');
insert into Ingredient (id,name,type)
values ('SRCR','Sour Cream', 'SAUCE');
