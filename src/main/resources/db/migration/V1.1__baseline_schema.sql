create sequence if not exists customers_seq start with 1 increment by 50;

create table if not exists customers (
    id bigint not null default nextval('customers_seq'),
    sign_up_date timestamp(6) not null,
    billing_address TEXT,
    email varchar(255) not null unique,
    name varchar(255) not null,
    primary key (id)
);

create sequence if not exists energy_prices_seq start with 1 increment by 50;

create table if not exists energy_prices (
    hour integer not null default nextval('energy_prices_seq'),
    price float(53) not null,
    id bigint not null default nextval('energy_prices_seq'),
    zip_code varchar(255) not null,
    primary key (id)
);

create sequence if not exists locations_seq start with 1 increment by 50;

create table if not exists locations (
    number_of_bedrooms integer not null,
    number_of_occupants integer not null,
    square_footage float(53) not null,
    id bigint not null default nextval('locations_seq'),
    start_date timestamp(6) not null,
    user_id bigint references customers(id),
    address varchar(255) not null,
    zip_code varchar(255) not null,
    primary key (id)
);

create sequence if not exists device_seq start with 1 increment by 50;

create table if not exists devices (
    model_number integer not null,
    enrollment_date timestamp(6) not null,
    id bigint not null default nextval('device_seq'),
    location_id bigint references locations(id) on delete set null,
    type varchar(255) not null,
    primary key (id)
);

create sequence if not exists events_seq start with 1 increment by 50;

create table if not exists events (
    device_id bigint references devices(id) on delete no action,
    id bigint not null default nextval('events_seq'),
    timestamp timestamp(6) not null,
    type varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
);













