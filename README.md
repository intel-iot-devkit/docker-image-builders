# DISCONTINUATION OF PROJECT #
This project will no longer be maintained by Intel.
Intel has ceased development and contributions including, but not limited to, maintenance, bug fixes, new releases, or updates, to this project.
Intel no longer accepts patches to this project.
# Docker Image Builders for Intel IoT Devkit

[![Build Status](https://travis-ci.org/intel-iot-devkit/docker-image-builders.svg?branch=master)](https://intel-iot-devkit/docker-image-builders)

## Docker Images Hierarchy for Mraa

To improve build times and images sizes, mraa use a build hierarchy to incrementally
create a build environment. Find below a brief description of them:

**mraa-base**

Provides the basic infrastructure and tools to compile C/C++ code and documentation.

**mraa-python**

Provides the python2/python3 build tools. Depends on `mraa-base`.

**mraa-java**

Provides the Java build tools. Depends on `mraa-base`.

**mraa-android**

 Provides the Android Things build tools.  Depends on `mraa-base`.

**mraa-node4**

Provides the Node.js v4.4.7 build tools. Depends on `mraa-base`.

**mraa-node5**

Same as `mraa-node4`, but using Node.js v5.12.0.

**mraa-node6**

Same as `mraa-node4`, but using Node.js v6.11.0.

**mraa-all**

Provides python, java, and node build tools and additional tools . Depends on `mraa-base`.

## Docker Images Hierarchy for Upm

To improve build times and images sizes, upm use a build hierarchy to incrementally
create a build environment. Find below a brief description of them:

**upm-base**

Provides the basic infrastructure and tools to compile C/C++ code and documentation.

**upm-python**

Provides the python2/python3 build tools. Depends on `upm-base`.

**upm-java**

Provides the Java build tools. Depends on `upm-base`.

**upm-android**

Provides the Android Things build tools. Depends on `upm-java`.

**upm-node4**

Provides the Node.js v4.4.7 build tools. Depends on `upm-base`.

**upm-node5**

Same as `upm-node4`, but using Node.js v5.12.0.

**upm-node6**

Same as `upm-node4`, but using Node.js v6.11.0.

**upm-all**

Provides python, java, and node build tools and additional tools . Depends on `upm-base`.

# Proxy Consideration

**docker build fails to fetch packages from internet**

docker-compose will automatically take `http_proxy`, `https_proxy`, and `no_proxy`
environment variables and use it as build arguments. Be sure to properly configure
this variables before building.

docker, unlinke docker-compose, do not take the proxy settings from the environment
automatically. You need to send them as build arguments:

```sh
# From the repository root folder
$ docker build \
    -d docker/Dockerfile.base \
    --build-arg http_proxy=$http_proxy \
    --build-arg https_proxy=$https_proxy \
    --build-arg no_proxy=$no_proxy \
    -t mraa-base .
```
