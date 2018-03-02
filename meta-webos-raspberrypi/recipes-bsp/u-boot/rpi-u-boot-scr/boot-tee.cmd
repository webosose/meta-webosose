
#Original boot.cmd
fdt addr ${fdt_addr} && fdt get value bootargs /chosen bootargs
fatload mmc 0:1 ${kernel_addr_r} uImage
bootm ${kernel_addr_r} - ${fdt_addr}

# Working stuff below that will probably be tossed

#fdt addr ${fdt_addr} && fdt get value bootargs /chosen bootargs
#fatload mmc 0:1 ${kernel_addr_r} uImage
#bootm ${kernel_addr_r} - ${fdt_addr}


#setenv fdt_addr_r "0x1700000"
#setenv fdt_file "bcm2710-rpi-3-b.dtb"
#setenv atf_load_addr "0x08400000"
#setenv atf_file "tee.bin"
#setenv kernel_addr_r "0x10000000"
#setenv kernel_file "uImage"

#fatload mmc 0:1 "${fdt_addr_r}" "${fdt_file}"
#fatload mmc 0:1 "${kernel_addr_r}" "${kernel_file}"
#fatload mmc 0:1 "${atf_load_addr}" "${atf_file}"
#fdt addr "${fdt_addr_r}" && fdt get value bootargs /chosen bootargs
#echo "bootargs = ${bootargs}"
#bootm "${kernel_addr_r}" - "${fdt_addr_r}"
