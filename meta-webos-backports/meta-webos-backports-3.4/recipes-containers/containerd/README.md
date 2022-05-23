# containerd: sample image fetch and exec commands

```shell
root@qemux86-64:~# ctr images list
REF                             TYPE                                                      DIGEST                                                                  SIZE     PLATFORMS
docker.io/calico/node:v3.11.2   application/vnd.docker.distribution.manifest.list.v2+json sha256:887bcd551668cccae1fbfd6d2eb0f635ec37bb4cf599e1169989aa49dfac5b57 84.8 MiB linux/amd64,linux/arm64,linux/ppc64le
docker.io/library/alpine:latest application/vnd.docker.distribution.manifest.list.v2+json sha256:c0e9560cda118f9ec63ddefb4a173a2b2a0347082d7dff7dc14272e7841a5b5a 2.7 MiB  linux/386,linux/amd64,linux/arm/v6,linux/arm/v7,linux/arm64/v8,l

root@qemux86-64:~# ctr image pull docker.io/library/alpine:latest
docker.io/library/alpine:latest:                                                  resolved       |++++++++++++++++++++++++++++++++++++++|
index-sha256:c0e9560cda118f9ec63ddefb4a173a2b2a0347082d7dff7dc14272e7841a5b5a:    exists         |++++++++++++++++++++++++++++++++++++++|
manifest-sha256:d7342993700f8cd7aba8496c2d0e57be0666e80b4c441925fc6f9361fa81d10e: exists         |++++++++++++++++++++++++++++++++++++++|
layer-sha256:188c0c94c7c576fff0792aca7ec73d67a2f7f4cb3a6e53a84559337260b36964:    exists         |++++++++++++++++++++++++++++++++++++++|
config-sha256:d6e46aa2470df1d32034c6707c8041158b652f38d2a9ae3d7ad7e7532d22ebe0:   exists         |++++++++++++++++++++++++++++++++++++++|
elapsed: 6.5 s                                                                    total:   0.0 B (0.0 B/s)
unpacking linux/amd64 sha256:c0e9560cda118f9ec63ddefb4a173a2b2a0347082d7dff7dc14272e7841a5b5a...


root@qemux86-64:~# ctr run -t docker.io/library/alpine:latest dtest /bin/sh
/ # uname -a
Linux qemux86-64 5.8.13-yocto-standard #1 SMP PREEMPT Tue Oct 6 12:23:29 UTC 2020 x86_64 Linux
/ #


 # root@qemux86-64:~# ctr c list
CONTAINER    IMAGE                              RUNTIME
dtest        docker.io/library/alpine:latest    io.containerd.runc.v2

root@qemux86-64:~# ctr c delete dtest
```