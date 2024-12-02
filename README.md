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
  - see all available products
  - get specific product by its id 
  - add new products - or update quantity if product already present (admin/user role required)
  - delete product (admin rights required)

Technology stack:
- Java with SpringBoot for creating RESTful services
- SpringSecurity for username/password authentication and role based access control 
- H2 in-memory database for rapid testing and development
- Hibernate with JPA for easy data manipulation
- Lombok for clean and readable code
