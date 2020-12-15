# finance-api

DB Structure 

create database finance_api;

create table quote (
    id int(11) not null auto_increment, 
    quote varchar(10) COLLATE utf8_unicode_ci NOT NULL,
    last_update datetime NOT NULL,
    primary key (id) 
);

create table stock_price (
    id int(11) not null auto_increment, 
    id_quote int(11) not null,
    open decimal(11,3) DEFAULT '0.00',
    current decimal(11,3) DEFAULT '0.00',
    highest decimal(11,3) DEFAULT '0.00',
    lowest decimal(11,3) DEFAULT '0.00',
    previous_close decimal(11,3) DEFAULT '0.00',
    date datetime NOT NULL default now(),
    primary key (id)
);
alter table stock_price add CONSTRAINT fk_quote foreign key (id_quote) references quote(id)

create table stock_following(
    id int(11) not null auto_increment, 
    id_quote int (11) not null,
    quote varchar(10) COLLATE utf8_unicode_ci,
    last_update datetime NOT NULL,
    primary key (id)
);

alter table stock_following add CONSTRAINT fk_quote foreign key (id_quote) references quote(id);




create table stock_split (
);

create table stock_trend (
);

create table stock_earning (
);

create table stock_eps (
);
