# app-customer-management
Customer Management Application 


docker build -t customermangament .
docker run customermangament -p 8080:8080 --name customermangament

Exemplos de request:

# Inclusão de cliente

curl --location 'localhost:8080/v1/customers' \
--header 'Content-Type: application/json' \
--data '{
"name" : "Alvaro",
"document" : "12345678916",
"phone" : "19-987654321",
"born_date" : "1992-02-16",
"address" : {
"zip_code" : "03105000",
"number" : "123"
}
}'

# Pesquisa de cliente por documento
curl --location 'localhost:8080/v1/customers?document=12345678901'

# Métricas do Prometheus
curl --location 'localhost:8080/actuator/prometheus'
