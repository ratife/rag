FROM openjdk:23-slim AS build

# Installer Maven 3.9.9
ENV MAVEN_VERSION=3.9.11
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

RUN apt-get update && apt-get install -y curl tar && \
    curl -fsSL https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    | tar -xz -C /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} $MAVEN_HOME && \
    ln -s $MAVEN_HOME/bin/mvn /usr/bin/mvn && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Vérification
RUN mvn -v
WORKDIR /app

COPY pom.xml .
COPY domain/pom.xml domain/
COPY infrastructure/pom.xml infrastructure/
COPY interface/pom.xml interface/

RUN mvn dependency:go-offline -B

COPY .. .


RUN mvn clean -DskipTests
RUN mvn  install -DskipTests
#RUN mvn install -pl interface -DskipTests

FROM openjdk:23-slim
WORKDIR /app
COPY --from=build /app/interface/target/*.jar app.jar

# Copier les credentials
#COPY application_default_credentials.json /app/adc.json

# Définir la variable d'environnement pour Google
#ENV GOOGLE_APPLICATION_CREDENTIALS=/app/adc.json

EXPOSE 8080
#ENTRYPOINT ["java", "-cp", "app.jar", "mg.tife.App"]
ENTRYPOINT ["java", "-jar", "app.jar"]