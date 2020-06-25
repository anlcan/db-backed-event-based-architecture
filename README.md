```sql
CREATE TABLE COMPANY(
   ID INT PRIMARY KEY     NOT NULL,
   NAME           TEXT    NOT NULL,
   ADDRESS        CHAR(50)
);


CREATE TABLE "events" (
                          "table" text NOT NULL,
                          "trigger" text NOT NULL,
                          "event" text NOT NULL,
                          "old" jsonb NOT NULL,
                          "new" jsonb NOT NULL,
                          "inserted_at" timestamp(6) NOT NULL DEFAULT statement_timestamp()
);

create or replace function store_event ()
    returns trigger
    language plpgsql
as $$
declare
    blob_new json := json_agg(new);
    blob_old json := json_agg(old);

    payload json := json_build_object('table', tg_table_name,
                            'event', tg_op,
                            'new',to_json(new));
begin

    insert into events ("trigger", "table", event, new, old)
    values (tg_name, tg_table_name, tg_op, blob_new, blob_old);
    perform pg_notify('events', payload #>> '{}' ) ;
    RETURN NULL;
end;

$$;

create trigger storage 
    after insert OR update OR delete on company
    for each row
    execute procedure store_event();

```

(https://www.postgresql.org/docs/9.1/trigger-definition.html)[triggers]
https://www.postgresql.org/docs/9.1/plpgsql-trigger.html
