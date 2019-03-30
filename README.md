Spring Security application can be run by executing all the docker command manually or it can be run through docker compose
                        
*******************************************************************************************************
                        command to run spring security application manually
*******************************************************************************************************

   1) run below command to create network

     docker network create spring-sec-net

   2) run below command to create volume
    
     docker volume create mysql_db

   3) run the below command so that spring application can use mysql database container at runtime

     docker container run --rm -d --name mysql_db --net spring-sec-net -v mysql_db:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=springsecurity mysql:5

   4) after the container is successfully created run below command to create springsecurity container
    
     docker container run --rm -d --name spring_security --net spring-sec-net -p 80:8080 niteshjha2021/springsecurity-production:latest
            
   5) type localhost in web browser url bar to connect to application
      
     localhost
     
*******************************************************************************************************
                        command to run spring security application using docker-compose
*******************************************************************************************************
    
   1) change the directory where "docker-compose.yaml" file is present
   2) run the below command in terminal
       
    docker-compose up -d


*******************************************************************************************************
                        command to run spring security application using docker-swarm and docker-stack
*******************************************************************************************************

   1) open docker playground 
   2) choose a template with 3 manager and 2 node( as it is recommended to use odd number of manager node )
   3) drag and drop docker-compose.yaml file in any one of manager terminal
   4) run the below command in docker swarm manager node.
    
    docker stack deploy -c docker-compose.yaml spring-security-stack     