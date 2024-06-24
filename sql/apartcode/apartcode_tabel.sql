use jipzip;

drop table IF EXISTS jipzip.apartment_information;


CREATE TABLE apartment_information (
    sidoName VARCHAR(255),
    sigunguName VARCHAR(255),
    eubmyundongName1 VARCHAR(255),
    eubmyundongName2 VARCHAR(255),
    dongCode VARCHAR(10),
    ApartCode VARCHAR(255),
    apartmentName VARCHAR(255),
    PRIMARY KEY (ApartCode)
);


