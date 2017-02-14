create table PERSON (
  ACCIDENT_NO varchar,
  PERSON_ID varchar,
  VEHICLE_ID varchar,
  SEX varchar,
  AGE varchar,
  Age_Group varchar,
  INJ_LEVEL integer,
  Inj_Level_Desc varchar,
  SEATING_POSITION varchar,
  HELMET_BELT_WORN integer,
  ROAD_USER_TYPE varchar,
  Road_User_Type_Desc varchar,
  LICENCE_STATE varchar,
  PEDEST_MOVEMENT varchar,
  POSTCODE varchar,
  TAKEN_HOSPITAL varchar,
  EJECTED_CODE varchar,
  primary key (ACCIDENT_NO, PERSON_ID, VEHICLE_ID)
);