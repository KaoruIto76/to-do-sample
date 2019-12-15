-- Category schema

-- !Ups

CREATE TABLE category (
  id         BIGINT(20)   NOT NULL AUTO_INCREMENT,
  name       VARCHAR(255) NOT NULL,
  slug       VARCHAR(255) NOT NULL,
  update_at  TIMESTAMP    NOT NULL,
  created_at TIMESTAMP    NOT NULL,
  PRIMARY KEY (id)
);

-- !Downs

DROP TABLE category;
