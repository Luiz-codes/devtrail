create table workspace (
    id uuid primary key default gen_random_uuid(),
    name varchar(120) not null,
    created_at timestamptz not null default now()
);