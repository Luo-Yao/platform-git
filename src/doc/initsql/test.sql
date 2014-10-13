-- DROP TABLE users;

CREATE TABLE users
(
  id serial NOT NULL,
  name character varying, 
  address character varying, 
  create_time timestamp with time zone DEFAULT now(), 
  CONSTRAINT pkey_id PRIMARY KEY (id)
)
WITHOUT OIDS;
ALTER TABLE users OWNER TO postgres;
COMMENT ON TABLE users IS '测试表';
COMMENT ON COLUMN users.id IS '姓名';
COMMENT ON COLUMN users.address IS '地址';
COMMENT ON COLUMN users.create_time IS '记录创建时间';


--INSERT INTO user VALUES ('1', 'admin', '北京','2014-07-14 23:35:11');



-- DROP TABLE logs;

CREATE TABLE logs
(
  id serial NOT NULL,
  log_id character varying,
  admin_id character varying,
  login_time timestamp with time zone DEFAULT now(),
  CONSTRAINT logs_pkey PRIMARY KEY (id)
)
WITHOUT OIDS;
ALTER TABLE users OWNER TO postgres;
COMMENT ON TABLE logs IS '测试表';
COMMENT ON COLUMN logs.log_id IS '姓名';
COMMENT ON COLUMN logs.admin_id IS '地址';
COMMENT ON COLUMN logs.login_time IS '记录创建时间';


--INSERT INTO logs VALUES ('1', 'fdafafdsa', 'admin121212121','2014-07-14 23:35:11');