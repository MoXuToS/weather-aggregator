create table if not exists weather_city
(
    city_id         bigserial primary key,
    city_name       varchar(128)  not null,
    latitude        numeric(9, 6) not null,
    longitude       numeric(9, 6) not null,
    collect_weather boolean default true
);

comment on table weather_city is 'Список городов для которых осуществляем сбор статистики';
comment on column weather_city.city_id is 'Идентификатор города';
comment on column weather_city.city_name is 'Наименование города';
comment on column weather_city.latitude is 'Ширина';
comment on column weather_city.longitude is 'Долгота';
comment on column weather_city.collect_weather is 'Осуществляем ли мы сейчас сбор';

alter table weather_city
    add constraint chk_weather_city_latitude
        check (latitude between -90 and 90);

alter table weather_city
    add constraint chk_weather_city_longitude
        check (longitude between -180 and 180);

create table if not exists weather_source
(
    source_id bigserial primary key,
    name      varchar(64)  not null,
    base_url  varchar(256) not null,
    enabled   boolean default true,
    api_key   varchar(256)
);

comment on table weather_source is 'Список источников которые поставляют погоду';
comment on column weather_source.source_id is 'Идентификатор источника погоды';
comment on column weather_source.name is 'Наименование источника погоды';
comment on column weather_source.base_url is 'Url адрес источника погоды';
comment on column weather_source.enabled is 'Включен ли сбор погоды с этого адреса';
comment on column weather_source.api_key is 'API ключ для сбора погоды';

create table if not exists weather_record
(
    record_id      bigserial primary key,

    city_id        bigint      not null,
    source_id      bigint      not null,

    measured_at    timestamptz not null,

    temperature    numeric(5, 2),
    feels_like     numeric(5, 2),
    humidity       numeric(5, 2),
    pressure       numeric(7, 2),
    wind_speed     numeric(6, 2),
    wind_direction numeric(6, 2),

    weather_code   integer     not null,

    constraint fk_weather_record_city
        foreign key (city_id)
            references weather_city (city_id),

    constraint fk_weather_record_source
        foreign key (source_id)
            references weather_source (source_id)
);

comment on table weather_record is 'История погодных наблюдений';

comment on column weather_record.record_id is 'Идентификатор погодного наблюдения';
comment on column weather_record.city_id is 'Идентификатор города';
comment on column weather_record.source_id is 'Идентификатор источника погоды';
comment on column weather_record.measured_at is 'Время измерения погоды';

comment on column weather_record.temperature is 'Температура воздуха, °C';
comment on column weather_record.feels_like is 'Ощущаемая температура, °C';
comment on column weather_record.humidity is 'Относительная влажность, %';
comment on column weather_record.pressure is 'Атмосферное давление, hPa';
comment on column weather_record.wind_speed is 'Скорость ветра, м/с';
comment on column weather_record.wind_direction is 'Направление ветра, градусы';

comment on column weather_record.weather_code is 'Код погодного состояния согласно источнику';

create index if not exists idx_weather_record_city_measured_at
    on weather_record (city_id, measured_at);

create index if not exists idx_weather_record_source_measured_at
    on weather_record (source_id, measured_at);

create unique index if not exists uk_weather_record_city_source_measured_at
    on weather_record (city_id, source_id, measured_at);