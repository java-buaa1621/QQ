create table user_info
(
	id int unsigned not null primary key,
	name varchar(100) not null,
	sex char(1) not null default "男",
	age tinyint unsigned not null default "0",
	motto varchar(100) not null,
	head_icon int unsigned not null default 1
);