create sequence hibernate_sequence start with 1 increment by 1;
create table Author (id bigint not null, createdDate timestamp, updatedDate timestamp, name varchar(255), primary key (id));
create table Book (id bigint not null, createdDate timestamp, updatedDate timestamp, title varchar(255), author_id bigint not null, primary key (id));
alter table Book add constraint FK5gbo4o7yxefxivwuqjichc67t foreign key (author_id) references Author;
