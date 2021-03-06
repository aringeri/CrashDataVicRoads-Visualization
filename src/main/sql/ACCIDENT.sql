create table ACCIDENT (
  ACCIDENT_NO varchar primary key,
  ACCIDENTDATE date,
  ACCIDENTTIME varchar,
  ACCIDENT_TYPE integer,
  Accident_Type_Desc varchar,
  DAY_OF_WEEK integer,
  Day_Week_Description varchar,
  DCA_CODE integer,
  DCA_Description varchar,
  DIRECTORY varchar,
  EDITION varchar,
  PAGE varchar,
  GRID_REFERENCE_X varchar,
  GRID_REFERENCE_Y varchar,
  LIGHT_CONDITION integer,
  Light_Condition_Desc varchar,
  NODE_ID integer,
  NO_OF_VEHICLES integer,
  NO_PERSONS integer,
  NO_PERSONS_INJ_2 integer,
  NO_PERSONS_INJ_3 integer,
  NO_PERSONS_KILLED integer,
  NO_PERSONS_NOT_INJ integer,
  POLICE_ATTEND integer,
  ROAD_GEOMETRY integer,
  Road_Geometry_Desc varchar,
  SEVERITY integer,
  SPEED_ZONE integer
);