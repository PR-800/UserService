# UserService

 
---------- QUEUE ( EXCHANGE name: Direct ) ----------
- AddUserQueue | ROUTING KEY: addUser
- UpdateUserQueue | ROUTING KEY: updateUser
- DeleteUserQueue | ROUTING KEY: deleteUser


----------------------- PATH ------------------------
- getUser | get -> http://localhost:8082/user-service/
- addUser | post -> http://localhost:8082/user-service/users
- updateUser | put -> http://localhost:8082/user-service/users
- deleteUser | delete -> http://localhost:8082/user-service/users/{id}


----------------- addUser JSON body -----------------
   {
       "email": "User1@gmail.com",
       "username": "User",
       "password": "1234",
       "role": ["reader", "writer"],
       "createdDate": "2023-11-10T15:33:42.583+00:00",
       "birthDate": "2023-11-10T15:33:42.583+00:00"
   }

--------------- updateUser JSON body ----------------
   {
       "userId": "{id}",
       "email": "User@gmail.com",
       "username": "User edited",
       "password": "1234",
       "role": ["reader", "writer"],
       "createdDate": "2023-11-10T15:33:42.583+00:00",
       "birthDate": "2023-11-10T15:33:42.583+00:00"
   }
