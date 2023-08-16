CREATE TABLE public.users
(
    user_id UUID NOT NULL PRIMARY KEY,
    created TIMESTAMP(6) WITHOUT TIME ZONE,
    email TEXT NOT NULL
);


