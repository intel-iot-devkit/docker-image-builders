FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive

# Update apt-get
RUN apt-get update && \
    # Install apt-utils
    apt-get -y --no-install-recommends install apt-utils && \
    # Install software-properties-common
    apt-get -y --no-install-recommends install software-properties-common && \
    # Main Build Dependencies
    apt-get -y --no-install-recommends install \
    build-essential cmake swig clang-3.8 gcc-4.8 g++-4.8 gcc-5 g++-5 \
    # Additional Tools
    git libpthread-stubs0-dev pkg-config wget unzip \
    # Docs Build Dependencies
    doxygen graphviz \
    # Sensor Specific Build Dependencies
    libjpeg-dev

# Add Mraa Repository
RUN add-apt-repository ppa:mraa/mraa && apt-get -y update

# Test Build Dependencies
RUN apt-get -y --no-install-recommends install \
    python python-dev python-pip && \
    pip install chardet

# Install gcc-6 g++-6
RUN add-apt-repository ppa:ubuntu-toolchain-r/test && \
    apt-get update && \
    apt-get -y --no-install-recommends install gcc-6 g++-6

WORKDIR /opt

# Install libbacnet 0.8.3
ARG BACNET_VERSION=0.8.3
RUN wget https://downloads.sourceforge.net/project/bacnet/bacnet-stack/bacnet-stack-$BACNET_VERSION/bacnet-stack-$BACNET_VERSION.zip && \
    unzip -qq bacnet-stack-$BACNET_VERSION.zip && cd bacnet-stack-$BACNET_VERSION && \
    BACDL_DEFINE=-DBACDL_MSTP=1 MAKE_DEFINE=-fPIC make clean all && \
    cp /opt/bacnet-stack-$BACNET_VERSION/lib/libbacnet.a /usr/lib/libbacnet.a && \
    echo "prefix=/usr" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "exec_prefix=\${prefix}" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "libdir=\${exec_prefix}/lib" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "includedir=\${exec_prefix}/include" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "Name: bacnet" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "Description: BACnet library" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "Version: $BACNET_VERSION" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "Libs: -L\${libdir} -lbacnet" >> /usr/lib/pkgconfig/libbacnet.pc && \
    echo "Cflags: -I\${includedir}/bacnet" >> /usr/lib/pkgconfig/libbacnet.pc && \
    mkdir /usr/include/bacnet && \
    cp /opt/bacnet-stack-$BACNET_VERSION/include/* /usr/include/bacnet/ && \
    rm -fr /opt/bacnet-stack-$BACNET_VERSION.zip

# Install libmodbus 3.1.4
ARG MODBUS_VERSION=3.1.4
RUN wget http://libmodbus.org/releases/libmodbus-$MODBUS_VERSION.tar.gz && \
    tar xf libmodbus-$MODBUS_VERSION.tar.gz && cd libmodbus-$MODBUS_VERSION && \
    ./configure && make -j8 && make install && \
    rm -fr /opt/libmodbus-$MODBUS_VERSION.tar.gz

# Install openzwave
ARG OPENZWAVE_VERSION=V1.5
RUN apt-get update && apt-get -y --no-install-recommends install libudev-dev && \
    git clone --branch $OPENZWAVE_VERSION --depth 1 https://github.com/OpenZWave/open-zwave.git && \
    cd open-zwave && make -j8 install && \
    echo "/usr/local/lib64" >> /etc/ld.so.conf.d/openzwave.conf

# Clone Mraa Repository and install it
RUN apt-get update && apt-get -y --no-install-recommends install libjson-c-dev
RUN git clone https://github.com/intel-iot-devkit/mraa.git && \
  cd mraa && \
  cmake \
    -DCMAKE_INSTALL_PREFIX=install \
    -DBUILDSWIG=OFF \
    -Bbuild \
    -H. && \
  make -Cbuild install && \
  rm -fr build
ENV MRAA_INSTALL_DIR /opt/mraa/install
ENV PKG_CONFIG_PATH $MRAA_INSTALL_DIR/lib/pkgconfig/:$PKG_CONFIG_PATH
ENV LD_LIBRARY_PATH $MRAA_INSTALL_DIR/lib/:$LD_LIBRARY_PATH

# Set Workdir
ARG UPM_SRC_DIR=/usr/src/app
ENV UPM_SRC_DIR $UPM_SRC_DIR
WORKDIR $UPM_SRC_DIR

CMD bash
