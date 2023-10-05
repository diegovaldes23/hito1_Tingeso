FROM openjdk:17
ARG  JAR_FILE=target/*.jar
COPY ${JAR_FILE} MyWebApp1.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","/MyWebApp1.jar"]