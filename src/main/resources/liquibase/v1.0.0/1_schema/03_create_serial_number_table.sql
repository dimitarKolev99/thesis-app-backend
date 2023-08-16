CREATE TABLE public.appliance_serial_number
(
    appliance_serial_number_id UUID NOT NULL PRIMARY KEY,
    appliance_serial_number_voucher_code_id UUID REFERENCES voucher_code(voucher_code_id),
    serial_number TEXT NOT NULL
);