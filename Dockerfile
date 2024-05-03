FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/ecommerce-api.jar ecommerce-api.jar
ENTRYPOINT ["java", "-jar", "ecommerce-api.jar"]
