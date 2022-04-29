drop sequence zz_fqq_user;  
drop sequence zz_fqq_group;  
drop sequence zz_fqq_group_user;  
drop sequence zz_fqq_category;
drop sequence zz_fqq_category_member;
drop sequence zz_fqq_user_info;

drop table fqq_user;  
drop table fqq_group;  
drop table fqq_group_user;  
drop table fqq_category;
drop table fqq_category_member;
---------------------------------------------
-- Table: fqq_user                         --
---------------------------------------------
create sequence zz_fqq_user
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 10;

create table fqq_user
(
  id number(10) primary key,
  nick_name varchar2(20) not null,
  user_name number(10) not null,
  user_password varchar2(20) not null,
  user_signature varchar2(100) not null
);
comment on table fqq_user
  is '用户';
comment on column fqq_user.nick_name
  is '用户昵称';
comment on column fqq_user.user_name
  is '用户账号';
comment on column fqq_user.user_password
  is '用户密码';
comment on column fqq_user.user_signature
  is '用户签名';

  
---------------------------------------------
-- Table: fqq_group                        --
--------------------------------------------- 
create sequence zz_fqq_group
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 10;

create table fqq_group
(
  id number(10) primary key,
  name varchar2(20) not null
);
comment on table fqq_group
  is 'QQ群';
comment on column fqq_group.name
  is 'QQ群名称';


---------------------------------------------
-- Table: fqq_group_user                   --
---------------------------------------------  
create sequence zz_fqq_group_user
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 10;

create table fqq_group_user
(
  id number(10) primary key,
  group_id number(10) not null,
  user_id number(10) not null
);
comment on table fqq_group_user
  is 'QQ群、用户关系';
comment on column fqq_group_user.group_id
  is 'QQ群ID';
comment on column fqq_group_user.user_id
  is '用户ID';
  
  
---------------------------------------------
-- Table: fqq_category                     --
---------------------------------------------
create sequence zz_fqq_category
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 10;

create table fqq_category
(
  id number(10) primary key,
  name varchar2(20) not null,
  owner_id number(10) not null,
  category_type varchar2(10) not null
);
comment on table fqq_category
  is '分组';
comment on column fqq_category.name
  is '分组名称';
comment on column fqq_category.owner_id
  is '所属者ID';
comment on column fqq_category.category_type
  is '分组类型（用户、QQ群）';
  
  
---------------------------------------------
-- Table: fqq_category_member              --
---------------------------------------------  
create sequence zz_fqq_category_member
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 10;

create table fqq_category_member
(
  id number(10) primary key,
  category_id number(10) not null,
  owner_id number(10) not null,
  member_id number(10) not null
);
comment on table fqq_category_member
  is '分组、用户关系';
comment on column fqq_category_member.category_id
  is '分组ID';
comment on column fqq_category_member.owner_id
  is '所属者ID';
comment on column fqq_category_member.member_id
  is '成员ID（QQ群、用户）';