# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt1"

# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/block-tap contained in package xen-tools-scripts-block requires /bin/bash, but no providers found in RDEPENDS:xen-tools-scripts-block? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /usr/lib/xen/bin/xendomains contained in package xen-tools-xendomains requires /bin/bash, but no providers found in RDEPENDS:xen-tools-xendomains? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/vif2 contained in package xen-tools-scripts-network requires /bin/bash, but no providers found in RDEPENDS:xen-tools-scripts-network? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/launch-xenstore contained in package xen-tools-xencommons requires /bin/bash, but no providers found in RDEPENDS:xen-tools-xencommons? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/remus-netbuf-setup contained in package xen-tools-remus requires /bin/bash, but no providers found in RDEPENDS:xen-tools-remus? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA Issue: /etc/xen/scripts/external-device-migrate contained in package xen-tools-scripts-common requires /bin/bash, but no providers found in RDEPENDS:xen-tools-scripts-common? [file-rdeps]
# ERROR: xen-tools-4.12+gitAUTOINC+a5fcafbfbe-r0 do_package_qa: QA run found fatal errors. Please consider fixing them.
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-scripts-block:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-xendomains:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-scripts-network:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-xencommons:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-remus:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-scripts-common:append:class-target = " ${VIRTUAL-RUNTIME_bash}"

RDEPENDS:${PN}-scripts-block:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-xendomains:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-scripts-network:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-xencommons:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-remus:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-scripts-common:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
