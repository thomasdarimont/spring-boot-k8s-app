FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic as builder

RUN   cd /opt/java && \
      jlink \
      --no-header-files \
      --no-man-pages \
      --compress=2 \
      --strip-debug \
      --vm=server \
      --exclude-files="**/bin/rmiregistry,**/bin/jrunscript,**/bin/rmid" \
      --module-path "$JAVA_HOME/jmods" \
      --add-modules java.base,java.instrument,java.logging,java.management,java.se,java.naming,java.security.jgss,java.security.sasl,java.sql,java.transaction.xa,java.xml,java.xml.crypto,jdk.security.auth,jdk.xml.dom,jdk.unsupported \
      --output java-runtime

FROM debian:buster-20190708-slim
COPY --from=builder /opt/java/java-runtime /opt/java

ENV PATH=$PATH:/opt/java/bin

EXPOSE 8080
CMD ["java", "-jar", "/opt/app/app.jar"]

RUN mkdir -p /opt/app \
    && chown -R daemon /opt/app

USER daemon
WORKDIR /opt/app

COPY --chown=daemon:daemon maven/*.jar /opt/app/app.jar
