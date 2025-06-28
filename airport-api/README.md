# Airport API

## Overview

A Java-based Spring Boot REST API that manages data for cities, airports, aircraft, and passengers:

- Exposes a RESTful API for managing cities, airports, aircraft, and passengers  
- Models all required relationships between entities  
- Supports full CRUD operations for all entities  
- Provides GET endpoints to answer specific queries for the companion CLI  
- Uses a MySQL database for persistent data storage  
- Includes automated tests with H2 for isolated, reliable testing  


## How to Run

Make sure MySQL is running with a database named `airportdb`, then run:

```bash
mvn spring-boot:run
```
The API will be available at:

http://localhost:8080

