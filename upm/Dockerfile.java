FROM inteliotdevkit/upm-base

# Update apt-get
RUN apt-get -y update && \
    # Java Build Dependencies
    apt-get -y --no-install-recommends install \
    default-jre default-jdk libmraa-java

# Configure Java Home
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

# Install Mraa with Java Support
RUN cd /opt/mraa && \
  cmake \
    -DCMAKE_INSTALL_PREFIX=install \
    -DBUILDSWIG=ON \
    -DBUILDSWIGPYTHON=OFF \
    -DBUILDSWIGNODE=OFF \
    -DBUILDSWIGJAVA=ON \
    -DIMRAA=ON \
    -DFIRMATA=ON \
    -Bbuild \
    -H. && \
  make -Cbuild install && \
  rm -fr build

# Set Workdir
WORKDIR $UPM_SRC_DIR

CMD bash
