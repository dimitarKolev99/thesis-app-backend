docker run --name mysql -p 3306:3306 --rm -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testdb -e MYSQL_USER=user -e MYSQL_PASSWORD=root -d mysql:8.0