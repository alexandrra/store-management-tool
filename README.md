The Store Management Tool is a web-based application that allows users to manage products stocks.
It also supports roles and users management.

Features:
- Username/password authentication
- Role based access
- Roles and users management:
  - add roles and users (no authentication needed)
  - see all available users or search for a specific one by username (authentication and admin/user role required)
  - remove a user by his id (authentication and admin rights required)
- Products management (authentication required for all the actions):
  - see all available products (pagination implemented)
  - get all products from a specific category
  - get specific product by its id 
  - update product 
  - add new products - or update quantity if product already present (admin/user role required)
  - delete product (admin rights required)

Technology stack:
- Java with SpringBoot for creating RESTful services
- SpringSecurity for username/password authentication and role based access control 
- SQL Server database for development 
- Hibernate with JPA for easy data manipulation
- Lombok for clean and readable code

Database setup:
- Before running the project, you must run a SQL Server container in Docker using the following commands:
  - docker pull mcr.microsoft.com/mssql/server
  - docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Str0ngPa$$w0rd" -p 1433:1433 -d mcr.microsoft.com/mssql/ser
    ver
