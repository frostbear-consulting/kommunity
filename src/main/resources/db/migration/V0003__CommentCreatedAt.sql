-- Adds comment.created_at

alter table comment
    add column created_at timestamp not null;