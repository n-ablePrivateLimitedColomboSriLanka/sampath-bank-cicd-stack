ARG VERSION=3.64.0
ARG NEXUS_VERSION=latest
ARG CASC_PLUGIN_VERSION=${VERSION}
ARG SONATYPE_IMAGE=sonatype/nexus3:${NEXUS_VERSION}
ARG CASC_PLUGIN_GIT_URL=https://github.com/AdaptiveConsulting/nexus-casc-plugin.git

FROM openjdk:8-jdk-alpine as BUILDER
RUN apk add git
ARG CASC_PLUGIN_GIT_URL
RUN git clone $CASC_PLUGIN_GIT_URL /build && cd /build && \
    git tag | grep -m1 ${VERSION} | xargs git checkout
WORKDIR "/build"
RUN ./mvnw package -Dkar.finalName=nexus-casc-plugin

FROM ${SONATYPE_IMAGE}
ENV NEXUS_APP_PORT=80
COPY --from=BUILDER /build/target/nexus-casc-plugin-bundle.kar /opt/sonatype/nexus/deploy/nexus-casc-plugin.kar
USER root
RUN sed -i -e 's/\(^application-port=\).*$/\1'"${NEXUS_APP_PORT}/" /opt/sonatype/nexus/etc/nexus-default.properties
USER nexus
