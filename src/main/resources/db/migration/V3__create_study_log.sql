create table study_log (
    id uuid primary key default gen_random_uuid(),
    workspace_id uuid not null references workspace (id),
    user_id uuid not null references app_user (id),
    topic varchar(120) not null,
    description text,
    duration_minutes integer not null check (duration_minutes > 0),
    study_date date not null,
    created_at timestamptz not null default now()
);

create index idx_study_log_workspace on study_log (workspace_id);
create index idx_study_log_user on study_log (user_id);
create index idx_study_log_date on study_log (study_date);