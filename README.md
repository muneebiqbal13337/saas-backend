# SaaS Backend — Multi-Tenant Architecture

A production-grade multi-tenant SaaS backend built with Java Spring Boot and PostgreSQL. 
Demonstrates complete tenant data isolation — the core pattern behind every SaaS product.

## What is Multi-Tenancy?

Multi-tenancy allows multiple organizations (tenants) to share the same application while 
keeping their data completely isolated from each other. This is how Slack, Salesforce, 
and every major SaaS product works at the backend level.

## Features

- Full tenant isolation — one tenant can never access another tenant's data
- Tenant organization management with subscription plans
- Tenant-scoped user management with role-based structure
- Duplicate prevention at tenant level
- Global exception handling with structured error responses
- Spring Security integration with stateless REST configuration
- Automatic timestamp management

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.11**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL 16**
- **Maven**
- **Lombok**

## API Endpoints

### Tenant Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tenants` | Create a new tenant organization |
| GET | `/api/tenants` | Retrieve all tenants |
| GET | `/api/tenants/{slug}` | Retrieve tenant by slug |
| PATCH | `/api/tenants/{id}/deactivate` | Deactivate a tenant |

### User Management (Tenant-Scoped)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tenants/{slug}/users` | Create user within a tenant |
| GET | `/api/tenants/{slug}/users` | Get all users in a tenant |
| GET | `/api/tenants/{slug}/users/{id}` | Get specific user in a tenant |
| PATCH | `/api/tenants/{slug}/users/{id}/deactivate` | Deactivate a user |

## Getting Started

### Prerequisites
- Java 21+
- PostgreSQL 16+
- Maven 3.9+

### Setup

1. Clone the repository
```
git clone https://github.com/muneebiqbal13337/saas-backend.git
```

2. Create PostgreSQL database
```sql
CREATE DATABASE saas_db;
```

3. Configure credentials in `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/saas_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

4. Run the application
```
mvn spring-boot:run
```

API starts on `http://localhost:8081`

## Tenant Isolation Example

### Create Two Tenants
```json
POST /api/tenants
{ "slug": "acme-corp", "companyName": "Acme Corporation", 
  "email": "admin@acme.com", "plan": "ENTERPRISE" }

POST /api/tenants  
{ "slug": "globex-inc", "companyName": "Globex Corporation", 
  "email": "admin@globex.com", "plan": "STARTER" }
```

### Create User in Acme
```json
POST /api/tenants/acme-corp/users
{ "firstName": "John", "lastName": "Doe", 
  "email": "john@acme.com", "role": "ADMIN" }
```

### Try to Access Acme's User from Globex — Returns 404
```json
GET /api/tenants/globex-inc/users/1

{
  "error": "Resource Not Found",
  "message": "User not found: id: 1 in tenant: globex-inc",
  "status": 404
}
```

Globex can never see Acme's data. That's tenant isolation.

## Error Handling

| Scenario | HTTP Status |
|----------|-------------|
| Resource not found | 404 Not Found |
| Duplicate resource | 409 Conflict |
| Validation failure | 400 Bad Request |
| Unexpected error | 500 Internal Server Error |
