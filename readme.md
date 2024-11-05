# Drippy User API

This API handles user authentication and authorization for the Drippy application.

## Prerequisites

To run the Drippy User API, you will need the following software installed:
- [Docker](https://www.docker.com/)
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/)

## Setup

1. Clone the repository:
```bash
git clone https://github.com/drippy-tech/drippy-user-api.git
cd drippy-user-api
```

2. Start PostgreSQL with docker-compose
 ```bash
 docker-compose -p drippy-user-db up --build -d
 ```

## API Documentation

(Documentation will be available soon)

## Testing

This project includes unit and integration testing to ensure code quality. Tests can be run with the command:
```bash
mvn test
```

For integration testing, the Docker must be running. Check status with:
```bash
docker ps
```
