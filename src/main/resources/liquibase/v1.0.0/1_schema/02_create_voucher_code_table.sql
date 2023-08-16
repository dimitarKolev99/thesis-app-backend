CREATE TABLE public.voucher_code
(
    voucher_code_id UUID NOT NULL PRIMARY KEY,
    voucher_code_user_id UUID REFERENCES users(user_id),
    code TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    obtained_from_company varchar(255),
    voucher_code_status varchar(255),
    timed BOOLEAN,
    expires_at TIMESTAMP NOT NULL
);