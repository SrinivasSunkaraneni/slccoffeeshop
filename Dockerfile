FROM openjdk:8
ADD target/app.jar slccoffeeshop.jar
EXPOSE 6001
ENTRYPOINT ["java","-jar","slccoffeeshop.jar"]
