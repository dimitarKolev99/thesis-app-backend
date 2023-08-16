
# first run ./mvnw clean install -U

#docker build --build-arg JAR_FILE=target/*.jar -t localapiportal .

#docker run --name localapiportal --rm -p 8081:8081 --network demo localapiportal

cp target/localapibackend-0.0.1-SNAPSHOT.jar .deploy/development/app.jar

docker build -t localapibackend .

docker run --name localapibackend --rm -p 8081:8081 localapibackend