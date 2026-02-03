FROM jenkins/inbound-agent:latest

USER root

RUN apt-get update && \
    apt-get install -y \
    curl \
    ca-certificates \
    maven \
    wget \
    gnupg \
    lsb-release \
    && rm -rf /var/lib/apt/lists/*

RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    chmod +x kubectl && \
    mv kubectl /usr/local/bin/kubectl && \
    kubectl version --client

RUN wget -qO- https://aquasecurity.github.io/trivy-repo/deb/public.key | apt-key add - && \
    echo "deb https://aquasecurity.github.io/trivy-repo/deb $(lsb_release -sc) main" | tee -a /etc/apt/sources.list.d/trivy.list && \
    apt-get update && \
    apt-get install -y trivy && \
    trivy --version

RUN mvn -version && \
    kubectl version --client && \
    trivy --version

USER jenkins

WORKDIR /home/jenkins/agent
