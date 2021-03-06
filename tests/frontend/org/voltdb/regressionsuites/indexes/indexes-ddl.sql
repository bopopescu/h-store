CREATE TABLE P1 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE P1IX ( 
  ID INTEGER DEFAULT '0' NOT NULL, 
  DESC VARCHAR(300), 
  NUM INTEGER NOT NULL, 
  RATIO FLOAT NOT NULL, 
  CONSTRAINT P1IX_PK_TREE PRIMARY KEY (ID) 
); 
CREATE INDEX P1IX_IDX_NUM_TREE ON P1IX (NUM); 
CREATE INDEX P1IX_IDX_RATIO_TREE ON P1IX (RATIO); 
CREATE INDEX P1IX_IDX_DESC_TREE ON P1IX (DESC);

CREATE TABLE R1 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE P2 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  CONSTRAINT P2_PK_TREE PRIMARY KEY (ID)
);
CREATE INDEX P2_IDX_NUM_TREE ON P2 (NUM);

CREATE TABLE R2 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  CONSTRAINT R2_PK_TREE PRIMARY KEY (ID)
);
CREATE INDEX R2_IDX_NUM_TREE ON R2 (NUM);

CREATE TABLE P3 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  PRIMARY KEY (ID)
);
CREATE INDEX P3_IDX_COMBO ON P3 (NUM, NUM2);

CREATE TABLE R3 (
  ID INTEGER DEFAULT '0' NOT NULL,
  DESC VARCHAR(300),
  NUM INTEGER NOT NULL,
  NUM2 INTEGER NOT NULL,
  RATIO FLOAT NOT NULL,
  PRIMARY KEY (ID)
);
CREATE INDEX R3_IDX_COMBO ON R3 (NUM, NUM2);

CREATE TABLE BINGO_BOARD (
 T_ID INTEGER NOT NULL,
 B_ID INTEGER NOT NULL,
 LAST_VALUE VARCHAR(128),
 CONSTRAINT B_PK_TREE PRIMARY KEY (T_ID, B_ID)
);

CREATE TABLE MANY_INTS (
  ID1 BIGINT NOT NULL,
  ID2 BIGINT NOT NULL,
  ID3 BIGINT NOT NULL,
  ID4 BIGINT NOT NULL,
  ID5 BIGINT NOT NULL,
  PRIMARY KEY (ID1, ID2, ID3, ID4)
);
CREATE INDEX IDX ON MANY_INTS (ID1, ID2, ID3, ID4, ID5);
