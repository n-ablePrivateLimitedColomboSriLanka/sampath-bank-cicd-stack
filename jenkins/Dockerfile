FROM docker:latest as docker-client

FROM jenkins/jenkins:lts-jdk17
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
COPY --from=docker-client /usr/local/bin/docker /usr/local/bin/docker
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --latest true --plugin-file /usr/share/jenkins/ref/plugins.txt