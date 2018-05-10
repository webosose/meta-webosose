# Summary

This layer is a collection of recipes and bbclasses we needed to backport
from newer oe-core to our build which is using older release.

The aim of this layer is to be able to just remove it from BBLAYERS when we
upgrade oe-core instead of removing individual files from our other layers.

If you're looking for backports from dylan to danny, check danny branch.
If you're looking for backports from dora to dylan, check dylan branch.
If you're looking for backports from daisy to dora, check dora branch.
If you're looking for backports from dizzy to daisy, check daisy branch.
If you're looking for backports from fido to dizzy, check dizzy branch.
If you're looking for backports from jethro to fido, check fido branch.
If you're looking for backports from krogoth to jethro, check jethro branch.
If you're looking for backports from morty to krogoth, check krogoth branch.
Backports from 2.3 (Pyro) to 2.2 (Morty) are in "meta-webos-backports-2.3" subdirectory.
Backports from 2.4 (Rocko) to 2.2 (Morty) are in "meta-webos-backports-2.4" subdirectory.
Backports from 2.5 (Sumo) to 2.2 (Morty) are in "meta-webos-backports-2.5" subdirectory.
Backports from 2.6 (Thud) to 2.2 (Morty) are in "meta-webos-backports-2.6" subdirectory.

See https://wiki.yoctoproject.org/wiki/Releases for complete list of releases.

# Copyright and License Information

Unless otherwise specified, all content, including all source code files and
documentation files in this repository is imported from oe-core without any
modification.

This means all metadata is MIT licensed unless otherwise stated. Source code
included in tree for individual recipes is under the LICENSE stated in the
.bb file for those software projects unless otherwise stated.

License information for any other files is either explicitly stated
or defaults to GPL version 2.

Modified files are marked with LG Electronics Copyright
