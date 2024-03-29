# For runqemu
IMAGE_CLASSES += "qemuboot"
QB_SYSTEM_NAME:x86 = "qemu-system-i386"
QB_CPU:x86 = "-cpu qemu32"
QB_CPU_KVM:x86 = "-cpu kvm32"

QB_SYSTEM_NAME:x86-64 = "qemu-system-x86_64"
QB_CPU:x86-64 = "-cpu core2duo"
QB_CPU_KVM:x86-64 = "-cpu kvm64"

QB_AUDIO_DRV = "alsa"
QB_AUDIO_OPT = "-soundhw hda,ac97,es1370"
QB_KERNEL_CMDLINE_APPEND = "oprofile.timer=1"
# Add the 'virtio-rng-pci' device otherwise the guest may run out of entropy
QB_OPT_APPEND = "-enable-kvm -device virtio-vga,virgl -display sdl,gl=on -show-cursor -usb -usbdevice tablet -device virtio-rng-pci"
