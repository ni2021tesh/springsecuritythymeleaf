--  command to run springsecurity application using docker

    1)  run below command to create network

            docker network create spring-sec-net

    2) run below command to create volume
    
            docker volume create mysql_db

    3) run the below command so that spring application can use mysql database container at runtime

            docker container run --rm -d --name mysql_db --net spring-sec-net -v mysql_db:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=springsecurity mysql:5

    4) after the container is successfully created run below command to create springsecurity container
    
            docker container run --rm -d --name spring_security --net spring-sec-net -p 80:8080 niteshjha2021/springsecurity-production:latest
            
    5) type localhost in web browser url bar to connect to application