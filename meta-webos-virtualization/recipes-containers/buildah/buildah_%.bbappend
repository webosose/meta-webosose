# Backport from:
# https://lists.yoctoproject.org/g/meta-virtualization/message/7976

# Rdepends on podman which needs seccomp and ipv6
inherit features_check
REQUIRED_DISTRO_FEATURES = "seccomp ipv6"
