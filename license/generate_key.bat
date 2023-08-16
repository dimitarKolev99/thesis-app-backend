openssl ecparam -name prime256v1 -genkey -noout -out ec-key.pem
openssl pkcs8 -topk8 -inform pem -in ec-key.pem -outform pem -nocrypt -out ec-key-1.pem