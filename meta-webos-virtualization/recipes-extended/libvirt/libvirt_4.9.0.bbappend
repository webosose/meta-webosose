# Backport fix from meta-virtualization/fc129dccd304bf5bf2f7e7241750354126714e0a
# It's included in Yocto 3.0 Zeus
# numactl is NOT compatible with arm
PACKAGECONFIG_remove_arm = "numactl"
PACKAGECONFIG_remove_armeb = "numactl"
