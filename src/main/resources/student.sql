CREATE TABLE student (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  name       VARCHAR(32),
  age        INT,
  birthday   DATETIME,
  createTime DATETIME,
  updateTime DATETIME,

  PRIMARY KEY (id)
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;