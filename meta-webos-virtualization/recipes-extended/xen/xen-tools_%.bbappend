# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt1"

# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/block-tap contained in package xen-tools-scripts-block requires /bin/bash, but no providers found in RDEPENDS_xen-tools-scripts-block? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /usr/lib/xen/bin/xendomains contained in package xen-tools-xendomains requires /bin/bash, but no providers found in RDEPENDS_xen-tools-xendomains? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/vif2 contained in package xen-tools-scripts-network requires /bin/bash, but no providers found in RDEPENDS_xen-tools-scripts-network? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/launch-xenstore contained in package xen-tools-xencommons requires /bin/bash, but no providers found in RDEPENDS_xen-tools-xencommons? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/remus-netbuf-setup contained in package xen-tools-remus requires /bin/bash, but no providers found in RDEPENDS_xen-tools-remus? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/external-device-migrate contained in package xen-tools-scripts-common requires /bin/bash, but no providers found in RDEPENDS_xen-tools-scripts-common? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA run found fatal errors. Please consider fixing them.
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-scripts-block_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-xendomains_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-scripts-network_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-xencommons_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-remus_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-scripts-common_append_class-target = " ${VIRTUAL-RUNTIME_bash}"

RDEPENDS_${PN}-scripts-block_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-xendomains_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-scripts-network_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-xencommons_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-remus_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-scripts-common_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
