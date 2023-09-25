FROM azul/zulu-openjdk:17-latest

VOLUME ["/tmp"]

ADD /maven/quartz-cluster-demo.jar /

ENV SPRING_PROFILES_ACTIVE="default"

ENTRYPOINT ["sh", "-c", "exec java -Djava.security.egd=file:/dev/./urandom -jar /quartz-cluster-demo.jar"]
