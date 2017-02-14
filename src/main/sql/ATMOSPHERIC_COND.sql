create table ATMOSPHERIC_COND (
  ACCIDENT_NO varchar,
  ATMOSPH_COND integer,
  ATMOSPH_COND_SEQ integer,
  Atmosph_Cond_Desc varchar,
  primary key (ACCIDENT_NO, ATMOSPH_COND_SEQ)
);