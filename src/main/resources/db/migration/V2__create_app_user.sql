create table app_user (
    id uuid primary key default gen_random_uuid(),
    workspace_id uuid not null references workspace (id),
    name varchar(120) not null,
    email varchar(160) not null unique,
    password_hash varchar(100) not null,
    role varchar(20) not null default 'MEMBER',
    created_at timestamptz not null default now()
);

create index idx_app_user_workspace on app_user (workspace_id);