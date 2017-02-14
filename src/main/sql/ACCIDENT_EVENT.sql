create table ACCIDENT_EVENT (
  ACCIDENT_NO varchar,
  EVENT_SEQ_NO integer,
  EVENT_TYPE varchar,
  Event_Type_Desc varchar,
  VEHICLE_1_ID varchar,
  VEHICLE_1_COLL_PT varchar,
  Vehicle_1_Coll_Pt_Desc varchar,
  VEHICLE_2_ID varchar,
  VEHICLE_2_COLL_PT varchar,
  Vehicle_2_Coll_Pt_Desc varchar,
  PERSON_ID varchar,
  OBJECT_TYPE integer,
  Object_Type_Desc varchar,
  primary key (ACCIDENT_NO, EVENT_SEQ_NO)
);