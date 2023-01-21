ARG SONATYPE_IMAGE=sonatype/nexus3:latest
ARG CASC_PLUGIN_GIT_URL=https://github.com/AdaptiveConsulting/nexus-casc-plugin.git

FROM bitnami/git:latest as GIT_CLONER
ARG CASC_PLUGIN_GIT_URL
WORKDIR /build
RUN git clone $CASC_PLUGIN_GIT_URL .

FROM openjdk:8-jdk-alpine as BUILDER
COPY --from=GIT_CLONER "/build" "/build"
WORKDIR "/build"
RUN ./mvnw package -Dkar.finalName=nexus-casc-plugin

# hadolint ignore=DL3006
FROM ${SONATYPE_IMAGE}
ENV NEXUS_APP_PORT=80
ENV NEXUS_CASC_CONFIG=/opt/nexus.yml
COPY --from=BUILDER /build/target/nexus-casc-plugin-bundle.kar /opt/sonatype/nexus/deploy/nexus-casc-plugin.kar
COPY casc.yaml /opt/nexus.yml
USER root
RUN sed -i -e 's/\(^application-port=\).*$/\1'"${NEXUS_APP_PORT}/" /opt/sonatype/nexus/etc/nexus-default.properties
USER nexus
