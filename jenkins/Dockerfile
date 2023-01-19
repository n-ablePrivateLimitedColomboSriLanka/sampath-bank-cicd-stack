FROM docker:latest as docker-client

FROM jenkins/jenkins:lts-jdk11
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /usr/share/jenkins/ref/casc_configs/casc.yaml
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --latest --plugin-file /usr/share/jenkins/ref/plugins.txt
COPY --from=docker-client /usr/local/bin/docker /usr/local/bin/docker
COPY casc.yaml /usr/share/jenkins/ref/casc_configs/casc.yaml
COPY initial-jobs.groovy /usr/share/jenkins/ref/initial-jobs/initial-jobs.groovy 
