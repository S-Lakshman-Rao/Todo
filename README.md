# Todo

#### Installation

1. Clone this repo

```sh
$ https://github.com/S-Lakshman-Rao/Todo.git
```

2. Open SQL Database
```sh
$ connect to local instance 3306
```

3. Run RabbidMq Server in terminal 
```sh
$ rabbitmq-server 
```

4. Run Demo2Application.java 

5. Run get,post,put,delete requests using Postman

5. i) http://localhost:8080/todo under GET -> gets list of all todo's for all users <br/>
   ii) http://localhost:8080/todo under POST with necessary details in body -> Updates list of todoitems and also sends message to consumer using RabbidMq server<br/>
   iii) http://localhost:8080/todo/{id} under DELETE -> Deletes Todoitem with this ID <br/>
   iv) http://localhost:8080/todo under PUT with appropriate JSON body -> Updates TodoItem (ID field Must) <br/>
   v) http://localhost:8080/todo/{id}/{userid} under PUT -> assigns user with is 'userid' to todoitem ID with 'id' <br/>
   vi) http://localhost:8080/todo/{userid} under GET -> gets list of TodoItems under each user <br/>
   vii) http://localhost:8080/todo/batch under POST -> reads todoitems from csv file and Updates list of todoitems and also sends message to consumer using RabbidMq server <br/>
   
