# Optional Back-End Assignment

Mhd Ghaith Al Abtah (ghaith.dev.0@gmail.com)
Maids.cc Software Developer - Back-End
## Steps to run:
- Create a database named 'library'.

    - note: the tables will be generated automatically after the first run.
- Add your database credentials and url in 'src/main/resources/application.properties'
- After the first run an admin will be created with credentials as: (you will these credentials to generate an admin JWT to access the endpoints).

    - email: admin@admin.com
    - password: password
- Authenticate your user at /api/auth
  - you can find the documentation for all endpoints at 'postman_apis_collection'.
- Use the generated JWT as Bearer Authorization Header.
## Patron:
- After creating any patron a new user will be added to the users table with password as "password".
- The patron user can get a User JWT at /api/auth, this JWT can be only used to view all books.