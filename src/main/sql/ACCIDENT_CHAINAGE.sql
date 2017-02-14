create table ACCIDENT_CHAINAGE (
  Node_Id varchar,
  Route_No integer,
  Chainage_Seq integer,
  Route_Link_No integer,
  Chainage integer,
  primary key (Node_Id, Chainage_Seq)
);