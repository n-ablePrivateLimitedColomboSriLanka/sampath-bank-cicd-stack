FROM mcr.microsoft.com/devcontainers/base:bullseye
RUN sudo apt update
RUN sudo apt install libsecret-1-0 -y iputils-ping vim openjdk-17-jre-headless
COPY jenkins-jack-1.2.1.vsix /
RUN mkdir -p /home/vscode/.ssh && chown -R vscode:vscode /home/vscode/
RUN git config --global credential.helper store
RUN git clone --depth 1 https://github.com/git/git.git /tmp/git
RUN cd /tmp/git/contrib/subtree && sudo make install
RUN curl -L http://10.240.64.4:8080/jnlpJars/jenkins-cli.jar -o /usr/local/bin/jenkins-cli.jar
RUN echo "alias jenkins-cli='java -jar /usr/local/bin/jenkins-cli.jar -s http://52.118.165.240:8080'" >> /home/vscode/.bashrc
RUN wget https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64 -O /usr/bin/yq && \
    chmod +x /usr/bin/yq