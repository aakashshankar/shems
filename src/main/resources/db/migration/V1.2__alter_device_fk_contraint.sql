alter table devices
    drop constraint devices_location_id_fkey,
    add constraint devices_location_id_fkey foreign key (location_id) references locations (id) on delete cascade;