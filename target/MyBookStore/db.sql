CREATE datebase bookstore;

use bookstore;

--分类表
--例：
--id-I
--name-中文小说
--这个分类中的书籍全部都是中国小说。
CREATE  TABLE categorys
(
  id    VARCHAR (100) PRIMARY KEY ,      --和books表中categoryId相同
  name  VARCHAR (100) not NULL UNIQUE ,  --unique是唯一约束，不能重名
  des   VARCHAR (200)
);

--图书表
CREATE TABLE books
(
  id         VARCHAR (100) PRIMARY KEY ,
  name       VARCHAR (100),
  author     VARCHAR (100),
  price      FLOAT (8, 2),
  path       VARCHAR (100),
  filename   VARCHAR (100),
  des        VARCHAR (200),
  categoryId VARCHAR (100),
  CONSTRAINT category_id_fk FOREIGN KEY (categoryId) REFERENCES categorys(id) --定义引用约束
);

--用户表
CREATE TABLE customers
(
  id        VARCHAR (100) PRIMARY KEY ,
  username  VARCHAR (100) not NULL UNIQUE ,
  password  VARCHAR (100) not NULL ,
  phone     VARCHAR (20)  not NULL UNIQUE ,
  address   VARCHAR (200) not NULL ,
  email     VARCHAR (20)  not NULL UNIQUE ,
  code      VARCHAR (200) UNIQUE ,
  isActived BIT (1)
);

--订单表
CREATE TABLE orders
(
  ordernum      VARCHAR (100) PRIMARY KEY ,
  price         FLOAT (8, 2),
  num           INT ,
  status        INT ,
  customerId    VARCHAR (100),
  CONSTRAINT   customerId_fk FOREIGN KEY (customerId) REFERENCES customers(id)
);

--订单详情表
CREATE TABLE orderitems
(
  id          VARCHAR (100) PRIMARY KEY ,
  num         int,
  price       FLOAT (8, 2),
  ordernum    VARCHAR (100),
  bookid      VARCHAR (100),
  CONSTRAINT ordernum_fk FOREIGN (ordernum) REFERENCES orders(ordernum),
  CONSTRAINT bookid_fk   FOREIGN (bookid)   REFERENCES books(id)
);

--订单编号表
CREATE TABLE ordernum
(
  prefix date,
  num    int
);

--权限控制
CREATE TABLE users
(
	id       varchar(100) primary key,
	username varchar(100) not null unique,
	password varchar(100) not null
);

CREATE TABLE roles
(
  id        VARCHAR (100) PRIMARY KEY ,
  name      VARCHAR (100) NOT NULL UNIQUE ,
  des       VARCHAR (200)
);

CREATE create table functions
(
  id        VARCHAR (100) PRIMARY KEY ,
  name      VARCHAR (100) not NULL UNIQUE ,
  uri       VARCHAR (200)
);

CREATE TABLE role_function
(
  r_id      VARCHAR (100),
  f_id      VARCHAR (100),
  PRIMARY KEY (r_id, f_id),
  CONSTRAINT role_id_fk FOREIGN KEY (r_id) REFERENCES roles(id),
  CONSTRAINT function_id_fk FOREIGN KEY (f_id) REFERENCES functions(id)
);

CREATE TABLE user_role
(
  u_id      VARCHAR (100),
  r_id      VARCHAR (100),
  PRIMARY KEY (u_id, r_id),
  CONSTRAINT user_id_fk FOREIGN (u_id) REFERENCES users(id),
  CONSTRAINT role_id_fk FOREIGN (r_id) REFERENCES roles(id)
);

insert into 'functions' ('id', 'name', 'uri') value('1', '主页', '');
insert into 'functions' ('id', 'name', 'uri') value('2', '消息', '');
insert into 'functions' ('id', 'name', 'uri') value('3', '添加分类', '');
insert into 'functions' ('id', 'name', 'uri') value('4', '查询分类', '');
insert into 'functions' ('id', 'name', 'uri') value('5', '添加书籍', '');
insert into 'functions' ('id', 'name', 'uri') value('6', '查询书籍','');

insert into 'roles' ('id', 'name', 'des') values ('1', '超级管理员', '可以访问任何页面');
insert into 'roles' ('id', 'name', 'des') values ('2', '普通管理员', '只能访问书籍部分');

insert into 'role_function' ('r_id', 'f_id') values ('1', '1');
insert into 'role_function' ('r_id', 'f_id') values ('1', '2');
insert into 'role_function' ('r_id', 'f_id') values ('1', '3');
insert into 'role_function' ('r_id', 'f_id') values ('1', '4');
insert into 'role_function' ('r_id', 'f_id') values ('1', '5');
insert into 'role_function' ('r_id', 'f_id') values ('1', '6');
insert into 'role_function' ('r_id', 'f_id') values ('2', '1');
insert into 'role_function' ('r_id', 'f_id') values ('1', '2');
insert into 'role_function' ('r_id', 'f_id') values ('1', '5');
insert into 'role_function' ('r_id', 'f_id') values ('1', '6');

insert into 'users' ('id', 'username', 'password') values ('1', 'admin', '123456');
insert into 'users' ('id', 'username', 'password') values ('2', 'aa', '123456');

insert into 'user_role'('u_id', 'r_id') values ('1', '1');
insert into 'user_role'('u_id', 'r_id') values ('2', '2');

