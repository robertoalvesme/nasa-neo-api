./gradlew build;
docker build -t nasa-api .;
docker run -p 8080:8080 -t nasa-api;
