# Copyright (c) 2023 LG Electronics, Inc.

inherit webos_qmake5

# we don't provide cmake tests
EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"

PACKAGECONFIG[criu] = "CONFIG+=criu,,criu-webos"
PACKAGECONFIG[lttng] = "CONFIG+=lttng,,"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"
