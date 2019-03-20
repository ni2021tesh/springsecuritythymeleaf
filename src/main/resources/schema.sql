drop table if exists user_create;
create table user_create
(
  id            int primary key AUTO_INCREMENT,
  date_created  varchar(50),
  date_modified varchar(50),
  email         varchar(50),
  name          varchar(50)
);

truncate table user_create;
