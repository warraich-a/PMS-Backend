DROP ALL OBJECTS;
create table if not exists user
(
    id          int auto_increment
        primary key,
    firstName       varchar(150) not null,
    lastName        varchar(150) not null,
    dateOfBirth     varchar(150) not null,
    email           text,
    password        varchar(150) not null,
    streetName      varchar(150) not null,
    houseNr         int not null,
    city            varchar(150) not null,
    zipcode         varchar(150) not null,
    userType        varchar(150) not null
);

create table if not exists medicine
(
    id int auto_increment
        primary key,
    medName varchar(150) not null,
    price double not null,
    sellingPrice double not null
);
create table if not exists connectionTable
(
    id           int auto_increment
        primary key,
    patientId    int                             not null,
    medicineId   int                              not null,
    isActive     tinyint                               not null,
    startDate        varchar(150)                               not null,
    endDate     text                                       not null,
    constraint patientIdFK
        foreign key (patientId) references user (id)
            on update cascade on delete cascade,
    constraint medicinId_fk
        foreign key (medicineId) references medicine (id)
            on update cascade on delete cascade
);

create table if not exists notification
(
    id int auto_increment
        primary key,
    content varchar(150) not null,
    patientId int not null,
    Date varchar(150) not null,
    constraint patient_IdFK
        foreign key (patientId) references user (id)
            on update cascade on delete cascade
);

INSERT INTO user (firstName, lastName, dateOfBirth, email, password, streetName, houseNr, city, zipcode, userType) values ( 'Anas', 'Ahmad','1996-09-03','Anas@gmail.com' , '1234', 'straat', 5, 'Mierlo', '4545DF', 'Patient' );
INSERT INTO user (firstName, lastName, dateOfBirth, email, password, streetName, houseNr, city, zipcode, userType) values ( 'naqsh', 'Ahmad','1999-09-03','naqsh@gmail.com' , '1234', 'straat', 5, 'Mierlo', '4545DF', 'Patient' );
INSERT INTO medicine (medName, price, sellingPrice) values ( 'kdd', 12, 250 );
INSERT INTO medicine (medName, price, sellingPrice) values ( 'new', 12, 250 );
INSERT INTO connectionTable (patientId, medicineId, isActive, startDate, endDate) values (1, 1, 1 ,'2020-12-12', '2020-12-20'  );
INSERT INTO connectionTable (patientId, medicineId, isActive, startDate, endDate) values (2, 1, 1 ,'2020-12-12', '2020-12-20'  );
INSERT INTO notification (content, patientId, Date) values ( 'New Notification', 1, '2020-12-12' );