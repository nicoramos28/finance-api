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
    close decimal(11,3) DEFAULT '0.00',
    current decimal(11,3) DEFAULT '0.00',
    highest decimal(11,3) DEFAULT '0.00',
    lowest decimal(11,3) DEFAULT '0.00',
    previous_close decimal(11,3) DEFAULT '0.00',
    date datetime NOT NULL default now(),
    volumen bigint(15),
    primary key (id),
    foreign key (id_quote) references quote(id)
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

create table stock_earning (
    id int(11) not null auto_increment, 
    id_quote int (11) not null,
    date datetime not null, 
    eps_current decimal (18,5) DEFAULT '0.00',
    eps_estimate decimal (18,5) DEFAULT '0.00',
    quarter tinyint(1) not null default 0,
    revenue_actual bigint(15),
    revenue_estimate bigint(15),
    enabled tinyint(1) default 1,
    primary key (id),
    foreign key (id_quote) references quote(id)
);

create table stock_technical_ema (
    id int(11) not null auto_increment,
    period int(11) not null, 
    id_quote int (11) not null,
    date datetime not null,
    value decimal (18,5) not null,
    primary key (id),
    status int(6),
    foreign key (id_quote) references quote(id) 
);

create table stock_technical_sma (
    id int(11) not null auto_increment, 
    period int(11) not null, 
    id_quote int (11) not null,
    date datetime not null,
    value decimal (18,5) not null,
    status int(6),
    primary key (id),
    foreign key (id_quote) references quote(id) 
);

create table stock_exchange_holiday(
    date datetime not null,
    holiday varchar(70) not null,
    primary key(date)
);

insert into stock_exchange_holiday values 
    ("2020-12-25 00:00:00", "Christmas Day"),
    ("2021-01-01 00:00:00", "New Years Day"),
    ("2021-01-18 00:00:00", "Martin Luther King, Jr. Day"),
    ("2021-02-15 00:00:00", "Washington's Birthday"),
    ("2021-04-02 00:00:00", "Good Friday"),
    ("2021-05-31 00:00:00", "Memorial Day"),
    ("2021-07-05 00:00:00", "Independence Day"),
    ("2021-09-06 00:00:00", "Labor Day"),
    ("2021-11-25 00:00:00", "Thanksgiving Day"),
    ("2021-12-24 00:00:00", "Christmas Day"),
    ("2021-12-25 00:00:00", "Christmas Day");
    
    
        