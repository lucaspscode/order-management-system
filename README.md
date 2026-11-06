# Order Management System

Sistema de gestão de pedidos com arquitetura de microsserviços, feito como projeto de estudo.

## Stack
- Java 21 / Spring Boot 4
- PostgreSQL, MongoDB
- Apache Kafka, RabbitMQ
- Docker, GitHub Actions, AWS

## Estrutura

```
order-management-system/
├── order-service/          # serviço de pedidos (em andamento)
├── inventory-service/
├── catalog-service/
├── payment-service/
├── notification-service/
├── api-gateway/
└── docker-compose.yml      # infraestrutura local
```

## Como rodar localmente

Pré-requisitos: Docker Desktop aberto e Java 21 (`java -version`).

```bash
# na raiz do repositório (onde está o docker-compose.yml)
docker compose up -d postgresql

# dentro de order-service/
cd order-service
./mvnw spring-boot:run
```

Sucesso = `Started OrderServiceApplication` no console. Ao terminar, `Ctrl + C` na aplicação e `docker compose stop postgresql` (use `stop`, não `down`, que apaga os dados do banco).