-- Adds a foreign key from comments to threads to identify owning threads

alter table comment
    add column thread_id uuid not null;

alter table comment
    add constraint comment_thread_id_fk foreign key (thread_id)
        references thread (idthread);