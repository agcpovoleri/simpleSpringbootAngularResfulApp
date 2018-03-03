--liquibase formatted sql

--changeset clubeviajante:v1 logicalFilePath:20180215_2016.sql
-- Table: users
CREATE TABLE users
(
  id serial NOT NULL,
  name character varying(100),
  email character varying(50),

  create_timestamp timestamp without time zone DEFAULT now(),
  update_timestamp timestamp without time zone,
  is_active boolean NOT NULL DEFAULT true,
  CONSTRAINT users_pk PRIMARY KEY (id )
);

-- Table: user_login
CREATE TABLE user_login
(
  id serial NOT NULL,
  username character varying(50) NOT NULL,
  password character varying(50) NOT NULL,
  user_id integer NOT NULL,
  active boolean NOT NULL DEFAULT true,
  CONSTRAINT user_login_pk PRIMARY KEY (id ),
  CONSTRAINT user_login_users_fk FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
