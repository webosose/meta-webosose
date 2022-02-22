# Copyright (c) 2014-2022 LG Electronics, Inc.

INC_PR = "r3"

SRC_URI +=" \
https://registry.npmjs.org/fstream/-/fstream-0.1.25.tgz;name=fstream_0.1.25;unpack=yes;downloadfilename=fstream-0.1.25-tarball.tgz;subdir=${BPN}/fstream_0.1.25 \
https://registry.npmjs.org/graceful-fs/-/graceful-fs-2.0.3.tgz;name=graceful-fs_2.0.3;unpack=yes;downloadfilename=graceful-fs-2.0.3-tarball.tgz;subdir=${BPN}/graceful-fs_2.0.3 \
https://registry.npmjs.org/inherits/-/inherits-2.0.1.tgz;name=inherits_2.0.1;unpack=yes;downloadfilename=inherits-2.0.1-tarball.tgz;subdir=${BPN}/inherits_2.0.1 \
https://registry.npmjs.org/mkdirp/-/mkdirp-0.3.5.tgz;name=mkdirp_0.3.5;unpack=yes;downloadfilename=mkdirp-0.3.5-tarball.tgz;subdir=${BPN}/mkdirp_0.3.5 \
https://registry.npmjs.org/glob/-/glob-3.2.9.tgz;name=glob_3.2.9;unpack=yes;downloadfilename=glob-3.2.9-tarball.tgz;subdir=${BPN}/glob_3.2.9 \
https://registry.npmjs.org/graceful-fs/-/graceful-fs-1.2.3.tgz;name=graceful-fs_1.2.3;unpack=yes;downloadfilename=graceful-fs-1.2.3-tarball.tgz;subdir=${BPN}/graceful-fs_1.2.3 \
https://registry.npmjs.org/minimatch/-/minimatch-0.2.14.tgz;name=minimatch_0.2.14;unpack=yes;downloadfilename=minimatch-0.2.14-tarball.tgz;subdir=${BPN}/minimatch_0.2.14 \
https://registry.npmjs.org/lru-cache/-/lru-cache-2.5.0.tgz;name=lru-cache_2.5.0;unpack=yes;downloadfilename=lru-cache-2.5.0-tarball.tgz;subdir=${BPN}/lru-cache_2.5.0 \
https://registry.npmjs.org/sigmund/-/sigmund-1.0.0.tgz;name=sigmund_1.0.0;unpack=yes;downloadfilename=sigmund-1.0.0-tarball.tgz;subdir=${BPN}/sigmund_1.0.0 \
https://registry.npmjs.org/mkdirp/-/mkdirp-0.4.0.tgz;name=mkdirp_0.4.0;unpack=yes;downloadfilename=mkdirp-0.4.0-tarball.tgz;subdir=${BPN}/mkdirp_0.4.0 \
https://registry.npmjs.org/nopt/-/nopt-2.2.1.tgz;name=nopt_2.2.1;unpack=yes;downloadfilename=nopt-2.2.1-tarball.tgz;subdir=${BPN}/nopt_2.2.1 \
https://registry.npmjs.org/abbrev/-/abbrev-1.0.5.tgz;name=abbrev_1.0.5;unpack=yes;downloadfilename=abbrev-1.0.5-tarball.tgz;subdir=${BPN}/abbrev_1.0.5 \
https://registry.npmjs.org/npmlog/-/npmlog-0.0.6.tgz;name=npmlog_0.0.6;unpack=yes;downloadfilename=npmlog-0.0.6-tarball.tgz;subdir=${BPN}/npmlog_0.0.6 \
https://registry.npmjs.org/ansi/-/ansi-0.2.1.tgz;name=ansi_0.2.1;unpack=yes;downloadfilename=ansi-0.2.1-tarball.tgz;subdir=${BPN}/ansi_0.2.1 \
https://registry.npmjs.org/osenv/-/osenv-0.0.3.tgz;name=osenv_0.0.3;unpack=yes;downloadfilename=osenv-0.0.3-tarball.tgz;subdir=${BPN}/osenv_0.0.3 \
https://registry.npmjs.org/request/-/request-2.34.0.tgz;name=request_2.34.0;unpack=yes;downloadfilename=request-2.34.0-tarball.tgz;subdir=${BPN}/request_2.34.0 \
https://registry.npmjs.org/aws-sign2/-/aws-sign2-0.5.0.tgz;name=aws-sign2_0.5.0;unpack=yes;downloadfilename=aws-sign2-0.5.0-tarball.tgz;subdir=${BPN}/aws-sign2_0.5.0 \
https://registry.npmjs.org/forever-agent/-/forever-agent-0.5.2.tgz;name=forever-agent_0.5.2;unpack=yes;downloadfilename=forever-agent-0.5.2-tarball.tgz;subdir=${BPN}/forever-agent_0.5.2 \
https://registry.npmjs.org/form-data/-/form-data-0.1.2.tgz;name=form-data_0.1.2;unpack=yes;downloadfilename=form-data-0.1.2-tarball.tgz;subdir=${BPN}/form-data_0.1.2 \
https://registry.npmjs.org/async/-/async-0.2.10.tgz;name=async_0.2.10;unpack=yes;downloadfilename=async-0.2.10-tarball.tgz;subdir=${BPN}/async_0.2.10 \
https://registry.npmjs.org/combined-stream/-/combined-stream-0.0.4.tgz;name=combined-stream_0.0.4;unpack=yes;downloadfilename=combined-stream-0.0.4-tarball.tgz;subdir=${BPN}/combined-stream_0.0.4 \
https://registry.npmjs.org/delayed-stream/-/delayed-stream-0.0.5.tgz;name=delayed-stream_0.0.5;unpack=yes;downloadfilename=delayed-stream-0.0.5-tarball.tgz;subdir=${BPN}/delayed-stream_0.0.5 \
https://registry.npmjs.org/hawk/-/hawk-1.0.0.tgz;name=hawk_1.0.0;unpack=yes;downloadfilename=hawk-1.0.0-tarball.tgz;subdir=${BPN}/hawk_1.0.0 \
https://registry.npmjs.org/boom/-/boom-0.4.2.tgz;name=boom_0.4.2;unpack=yes;downloadfilename=boom-0.4.2-tarball.tgz;subdir=${BPN}/boom_0.4.2 \
https://registry.npmjs.org/cryptiles/-/cryptiles-0.2.2.tgz;name=cryptiles_0.2.2;unpack=yes;downloadfilename=cryptiles-0.2.2-tarball.tgz;subdir=${BPN}/cryptiles_0.2.2 \
https://registry.npmjs.org/hoek/-/hoek-0.9.1.tgz;name=hoek_0.9.1;unpack=yes;downloadfilename=hoek-0.9.1-tarball.tgz;subdir=${BPN}/hoek_0.9.1 \
https://registry.npmjs.org/sntp/-/sntp-0.2.4.tgz;name=sntp_0.2.4;unpack=yes;downloadfilename=sntp-0.2.4-tarball.tgz;subdir=${BPN}/sntp_0.2.4 \
https://registry.npmjs.org/http-signature/-/http-signature-0.10.0.tgz;name=http-signature_0.10.0;unpack=yes;downloadfilename=http-signature-0.10.0-tarball.tgz;subdir=${BPN}/http-signature_0.10.0 \
https://registry.npmjs.org/asn1/-/asn1-0.1.11.tgz;name=asn1_0.1.11;unpack=yes;downloadfilename=asn1-0.1.11-tarball.tgz;subdir=${BPN}/asn1_0.1.11 \
https://registry.npmjs.org/assert-plus/-/assert-plus-0.1.2.tgz;name=assert-plus_0.1.2;unpack=yes;downloadfilename=assert-plus-0.1.2-tarball.tgz;subdir=${BPN}/assert-plus_0.1.2 \
https://registry.npmjs.org/ctype/-/ctype-0.5.2.tgz;name=ctype_0.5.2;unpack=yes;downloadfilename=ctype-0.5.2-tarball.tgz;subdir=${BPN}/ctype_0.5.2 \
https://registry.npmjs.org/json-stringify-safe/-/json-stringify-safe-5.0.0.tgz;name=json-stringify-safe_5.0.0;unpack=yes;downloadfilename=json-stringify-safe-5.0.0-tarball.tgz;subdir=${BPN}/json-stringify-safe_5.0.0 \
https://registry.npmjs.org/mime/-/mime-1.2.11.tgz;name=mime_1.2.11;unpack=yes;downloadfilename=mime-1.2.11-tarball.tgz;subdir=${BPN}/mime_1.2.11 \
https://registry.npmjs.org/node-uuid/-/node-uuid-1.4.1.tgz;name=node-uuid_1.4.1;unpack=yes;downloadfilename=node-uuid-1.4.1-tarball.tgz;subdir=${BPN}/node-uuid_1.4.1 \
https://registry.npmjs.org/oauth-sign/-/oauth-sign-0.3.0.tgz;name=oauth-sign_0.3.0;unpack=yes;downloadfilename=oauth-sign-0.3.0-tarball.tgz;subdir=${BPN}/oauth-sign_0.3.0 \
https://registry.npmjs.org/qs/-/qs-0.6.6.tgz;name=qs_0.6.6;unpack=yes;downloadfilename=qs-0.6.6-tarball.tgz;subdir=${BPN}/qs_0.6.6 \
https://registry.npmjs.org/tough-cookie/-/tough-cookie-0.12.1.tgz;name=tough-cookie_0.12.1;unpack=yes;downloadfilename=tough-cookie-0.12.1-tarball.tgz;subdir=${BPN}/tough-cookie_0.12.1 \
https://registry.npmjs.org/punycode/-/punycode-1.2.4.tgz;name=punycode_1.2.4;unpack=yes;downloadfilename=punycode-1.2.4-tarball.tgz;subdir=${BPN}/punycode_1.2.4 \
https://registry.npmjs.org/tunnel-agent/-/tunnel-agent-0.3.0.tgz;name=tunnel-agent_0.3.0;unpack=yes;downloadfilename=tunnel-agent-0.3.0-tarball.tgz;subdir=${BPN}/tunnel-agent_0.3.0 \
https://registry.npmjs.org/rimraf/-/rimraf-2.2.6.tgz;name=rimraf_2.2.6;unpack=yes;downloadfilename=rimraf-2.2.6-tarball.tgz;subdir=${BPN}/rimraf_2.2.6 \
https://registry.npmjs.org/semver/-/semver-2.2.1.tgz;name=semver_2.2.1;unpack=yes;downloadfilename=semver-2.2.1-tarball.tgz;subdir=${BPN}/semver_2.2.1 \
https://registry.npmjs.org/tar/-/tar-0.1.19.tgz;name=tar_0.1.19;unpack=yes;downloadfilename=tar-0.1.19-tarball.tgz;subdir=${BPN}/tar_0.1.19 \
https://registry.npmjs.org/block-stream/-/block-stream-0.0.7.tgz;name=block-stream_0.0.7;unpack=yes;downloadfilename=block-stream-0.0.7-tarball.tgz;subdir=${BPN}/block-stream_0.0.7 \
https://registry.npmjs.org/which/-/which-1.0.5.tgz;name=which_1.0.5;unpack=yes;downloadfilename=which-1.0.5-tarball.tgz;subdir=${BPN}/which_1.0.5 \
https://registry.npmjs.org/grunt/-/grunt-0.4.5.tgz;name=grunt_0.4.5;unpack=yes;downloadfilename=grunt-0.4.5-tarball.tgz;subdir=${BPN}/grunt_0.4.5 \
https://registry.npmjs.org/grunt-contrib-concat/-/grunt-contrib-concat-0.5.1.tgz;name=grunt-contrib-concat_0.5.1;unpack=yes;downloadfilename=grunt-contrib-concat-0.5.1-tarball.tgz;subdir=${BPN}/grunt-contrib-concat_0.5.1 \
https://registry.npmjs.org/grunt-contrib-jshint/-/grunt-contrib-jshint-0.10.0.tgz;name=grunt-contrib-jshint_0.10.0;unpack=yes;downloadfilename=grunt-contrib-jshint-0.10.0-tarball.tgz;subdir=${BPN}/grunt-contrib-jshint_0.10.0 \
https://registry.npmjs.org/grunt-contrib-nodeunit/-/grunt-contrib-nodeunit-0.4.1.tgz;name=grunt-contrib-nodeunit_0.4.1;unpack=yes;downloadfilename=grunt-contrib-nodeunit-0.4.1-tarball.tgz;subdir=${BPN}/grunt-contrib-nodeunit_0.4.1 \
https://registry.npmjs.org/grunt-contrib-qunit/-/grunt-contrib-qunit-0.7.0.tgz;name=grunt-contrib-qunit_0.7.0;unpack=yes;downloadfilename=grunt-contrib-qunit-0.7.0-tarball.tgz;subdir=${BPN}/grunt-contrib-qunit_0.7.0 \
https://registry.npmjs.org/grunt-contrib-uglify/-/grunt-contrib-uglify-0.5.0.tgz;name=grunt-contrib-uglify_0.5.0;unpack=yes;downloadfilename=grunt-contrib-uglify-0.5.0-tarball.tgz;subdir=${BPN}/grunt-contrib-uglify_0.5.0 \
https://registry.npmjs.org/grunt-contrib-watch/-/grunt-contrib-watch-0.6.1.tgz;name=grunt-contrib-watch_0.6.1;unpack=yes;downloadfilename=grunt-contrib-watch-0.6.1-tarball.tgz;subdir=${BPN}/grunt-contrib-watch_0.6.1 \
https://registry.npmjs.org/qunitjs/-/qunitjs-1.18.0.tgz;name=qunitjs_1.18.0;unpack=yes;downloadfilename=qunitjs-1.18.0-tarball.tgz;subdir=${BPN}/qunitjs_1.18.0 \
"

SRC_URI[fstream_0.1.25.md5sum]="81805600ac27ed20bbe21e2645021476"
SRC_URI[fstream_0.1.25.sha256sum]="da01b58c91f9657c895f7d5dbe56e957923a2fe216e70d5beac45e5b5d8103d6"
SRC_URI[graceful-fs_2.0.3.md5sum]="ec5604a4c0493dee7c9863a970b6088a"
SRC_URI[graceful-fs_2.0.3.sha256sum]="a2989ace92c5c10859ad7646fcf9c6074b1247c48fbcb986bda32307230627f8"
SRC_URI[inherits_2.0.1.md5sum]="83a19cf64f1dc35573d8752130fd099d"
SRC_URI[inherits_2.0.1.sha256sum]="e0d5493f8142aff09125344665a90a8227b9a3ffa4bb8d086d0fb471c00deb29"
SRC_URI[mkdirp_0.3.5.md5sum]="8e0debb3900aab1713fe50f9b27228d3"
SRC_URI[mkdirp_0.3.5.sha256sum]="8ae9c5107acb41110106224f5e7be2b50b00d15d062ea7941301c111b863fd26"
SRC_URI[glob_3.2.9.md5sum]="cb72303f23a3b6539b1b52019cd8f791"
SRC_URI[glob_3.2.9.sha256sum]="6128906d245d03889bd56caa1fad361d6897a9e1c0df5ddafd485c348cda9e07"
SRC_URI[graceful-fs_1.2.3.md5sum]="7dc1c6c741a99a021b23cd75e4e307c3"
SRC_URI[graceful-fs_1.2.3.sha256sum]="b8d9e0af2261259525a65281573e416b70b91788ea7f7b13f4598aaa7818255a"
SRC_URI[minimatch_0.2.14.md5sum]="e719e5474846b607be4424c55206c8f6"
SRC_URI[minimatch_0.2.14.sha256sum]="682d80dd599fbaedee51cc1dee54fc77f810ab0715e0deec3687e8de537e337e"
SRC_URI[lru-cache_2.5.0.md5sum]="74fa4b149261acd63b1cfda3d567ff37"
SRC_URI[lru-cache_2.5.0.sha256sum]="598f2bc34069f6ebbc277da4671a7cc08a0ef1b4c035e72e7845bab8dd48fe3a"
SRC_URI[sigmund_1.0.0.md5sum]="de607e0df7664744145a2ba95a06f65f"
SRC_URI[sigmund_1.0.0.sha256sum]="ddf823295db284ac4720b9d40f381d7375dc0b05881b98269346b33fe9835e25"
SRC_URI[mkdirp_0.4.0.md5sum]="a9f0705536e61d4b6fe411e6a58e7b50"
SRC_URI[mkdirp_0.4.0.sha256sum]="467a07909f019dd8f232727e9ae24bb44ed3bf8caca45964ff51dfe36257324f"
SRC_URI[nopt_2.2.1.md5sum] = "6df8e09cf90437580e36a299c8a9eb02"
SRC_URI[nopt_2.2.1.sha256sum] = "8fa4705907d8aed1d5133a2022ee15567c06483aa71f8ebe1d18212027a4632e"
SRC_URI[abbrev_1.0.5.md5sum]="73e04446fa70cacc5536e5cb7d83ccb0"
SRC_URI[abbrev_1.0.5.sha256sum]="bb9e56b00c350c03c1233f3d2080564b7f6a89ef6795d9a8f8e53020c5a48f26"
SRC_URI[npmlog_0.0.6.md5sum]="b751e3a650c052119fa66bae456c7cc7"
SRC_URI[npmlog_0.0.6.sha256sum]="6f57dcecdc44ca1b6cbca492728a41fca516a4be45cc84fe1dcfcedc6a02ca2a"
SRC_URI[ansi_0.2.1.md5sum]="b9cc255677a5a48d117142dc04a88f3d"
SRC_URI[ansi_0.2.1.sha256sum]="0620e1cd7ae0132ccbb0b6929cd2eb99b027f823e64f4a932b2441e4a4378cf6"
SRC_URI[osenv_0.0.3.md5sum]="f42ac372960369633871ab3550cb43fb"
SRC_URI[osenv_0.0.3.sha256sum]="aafbb23637b7338c9025f9da336f31f96674d7926c30f209e4d93ce16d5251c4"
SRC_URI[request_2.34.0.md5sum]="03977f2a916c6a1f505f64329e8f502b"
SRC_URI[request_2.34.0.sha256sum]="8f4ec817c1283650d1b349ad0f9821cb2aba96cb548638b89ae59859a14f59ab"
SRC_URI[aws-sign2_0.5.0.md5sum]="feed6da9413e405ca6e8891b77adf534"
SRC_URI[aws-sign2_0.5.0.sha256sum]="bf35697e73e18a9128022cc8c547bad773b807dcca74f17c47dee60b031abf8b"
SRC_URI[forever-agent_0.5.2.md5sum]="04e9c413b8166eec5ed52e39d6e2bc10"
SRC_URI[forever-agent_0.5.2.sha256sum]="e439f83b95be5ca95d640514457cc8ce0ecccaea3bbaccadbb4ff8cd81c069b4"
SRC_URI[form-data_0.1.2.md5sum]="79b0bab52a0c456c013e5d60674f52c0"
SRC_URI[form-data_0.1.2.sha256sum]="f230d8c51cc4f1f028ef574d7be00b18ce612690c6f513de23962e1cfa8cfbe1"
SRC_URI[async_0.2.10.md5sum]="fd313a0e8cc2343569719e80cd7a67ac"
SRC_URI[async_0.2.10.sha256sum]="46869f9efcfc4045217c7730f65a81f85b518a0765d14f931ddb70581afb644c"
SRC_URI[combined-stream_0.0.4.md5sum]="501cfa5817967ef61c9c486514278f14"
SRC_URI[combined-stream_0.0.4.sha256sum]="2502ed7a4406db1a65b09cae3106221051d6af20d3f1d7e8cc38cfc72a36432c"
SRC_URI[delayed-stream_0.0.5.md5sum]="2da6469cf90f8b6a0ff44eac8de7a001"
SRC_URI[delayed-stream_0.0.5.sha256sum]="f40e440dac0f853577d5225d7bd4b2026ea1447a724f4ba1096e29983ee595dd"
SRC_URI[hawk_1.0.0.md5sum]="abdb4a27843dde81d216b9891e8fadd3"
SRC_URI[hawk_1.0.0.sha256sum]="27de6908d6db3de2e50a713b4bd7cb984854c538c7315293bb8e6e1e8a4e7e55"
SRC_URI[boom_0.4.2.md5sum]="36fdfedc7705653d4b9b337a9134dabc"
SRC_URI[boom_0.4.2.sha256sum]="294e022990269e7fa1e3015862d2d24847cc83c3e68d214dad5a2d0d3826a08f"
SRC_URI[cryptiles_0.2.2.md5sum]="65f72287fb0e88a9be6c302e2b9afb47"
SRC_URI[cryptiles_0.2.2.sha256sum]="ed3c2d5d3c1322d5389cceb46778a6c39a62842f737047666d63abda5b39fab5"
SRC_URI[hoek_0.9.1.md5sum]="d051a0a0130ad12b73ea1842030b835d"
SRC_URI[hoek_0.9.1.sha256sum]="34480468d2e85781ecdd7134523b4d6fea978d333edd2bcb828edd33bbbe8a3f"
SRC_URI[sntp_0.2.4.md5sum]="d079edfd79a60468f554ea58ab230306"
SRC_URI[sntp_0.2.4.sha256sum]="1f91a8d60fd48751e4b169885f530012ce2a8223ed29006abcee175f2b10d779"
SRC_URI[http-signature_0.10.0.md5sum]="1cc3a5c258a5f6d8f080c102eaff490b"
SRC_URI[http-signature_0.10.0.sha256sum]="badaa47893d7a6c352ffb0746d68dd225fbb057b7d7149ad87093276445bf95b"
SRC_URI[asn1_0.1.11.md5sum]="a34d188535a702e1c6e0d9f768d6d542"
SRC_URI[asn1_0.1.11.sha256sum]="7206eadc8a9344e484bcce979e22a12c9fa64c1395aa0544b8b767808b268f43"
SRC_URI[assert-plus_0.1.2.md5sum]="48389f623b95fd8db6257c667af2ba0a"
SRC_URI[assert-plus_0.1.2.sha256sum]="bd62e853460024d1e35d3f76065f4c856a271e55ef1ce684f3033314b7377c61"
SRC_URI[ctype_0.5.2.md5sum]="c5efb29957dfdee73c1b469566b9755d"
SRC_URI[ctype_0.5.2.sha256sum]="4a7224a74f19dc6a1206fa1c04ae1a4ab795cd4ba842466e2f511fa714f82c60"
SRC_URI[json-stringify-safe_5.0.0.md5sum]="197b319fb2d2772f4830c8443e57faba"
SRC_URI[json-stringify-safe_5.0.0.sha256sum]="7e2be4a001687b363dbeaf5d19c98fb694a19d417550f74abb3b8e81a9ea1483"
SRC_URI[mime_1.2.11.md5sum]="a96a25013bf4693868ceb6d75983174d"
SRC_URI[mime_1.2.11.sha256sum]="6280009744e6ea56d489de7a10fc47c6c5665dafe44f0174a911c5f2d0e49991"
SRC_URI[node-uuid_1.4.1.md5sum]="f7778f1bb34f4ea250b259388cd7ec13"
SRC_URI[node-uuid_1.4.1.sha256sum]="a2599ef6dd0a163a072fb66cffa3ac051717566879d02c7bdead5a900b34b1d8"
SRC_URI[oauth-sign_0.3.0.md5sum]="ab11e1f8fae176659f4a0915cc6b0b9f"
SRC_URI[oauth-sign_0.3.0.sha256sum]="a202acb9ea84ef53e6a82a6ee5113328cd4bfd68d6ba04d15f1da933660b087e"
SRC_URI[qs_0.6.6.md5sum]="ec114f32f65c4c9ea24f533b8881a2d7"
SRC_URI[qs_0.6.6.sha256sum]="5eb6d13be65571e245bfddcd0025415e78f539f92d72bff1e27fbab4b4ee963a"
SRC_URI[tough-cookie_0.12.1.md5sum]="2488d0c3ced1b40ef87efe78aa66e708"
SRC_URI[tough-cookie_0.12.1.sha256sum]="fc0ba93d692b2541da2ce87ae145a9b74434a5b1e1e6213efd075cbae3c86897"
SRC_URI[punycode_1.2.4.md5sum]="f78e51651686d2adcb641439f635c217"
SRC_URI[punycode_1.2.4.sha256sum]="784bc189fea5a3d82bcb8b30d4a637e5c02733b7fba2ee99af85338f77ad8891"
SRC_URI[tunnel-agent_0.3.0.md5sum]="0eb0b4e46d6a4d72ccd7e5941d0fc11a"
SRC_URI[tunnel-agent_0.3.0.sha256sum]="5b23c1d29e85143e213ba5aca118419ab42d25774da92e816074a73f423bcabd"
SRC_URI[rimraf_2.2.6.md5sum]="12ca912d238e071737d1ffec98556e86"
SRC_URI[rimraf_2.2.6.sha256sum]="5ecd405586cdbd8922d7793759fb4d26d1729759b98ace9f5784fe1d10cd54fd"
SRC_URI[semver_2.2.1.md5sum] = "d62b593a3837b7f8f3056a5c1c8b2759"
SRC_URI[semver_2.2.1.sha256sum] = "aa057b1bfb7bd1ccd0a966ceb2fd549147a21c7b0292c94d6a8dc59d47df49aa"
SRC_URI[tar_0.1.19.md5sum]="66b9f13c53386cc9c5da9b591780862e"
SRC_URI[tar_0.1.19.sha256sum]="1566f80d655338d9770c55538725045ba776565bf4cfe5471929c9d2f66b0f21"
SRC_URI[block-stream_0.0.7.md5sum]="c66aaa2227abec6daa6ff8a155b59294"
SRC_URI[block-stream_0.0.7.sha256sum]="ac1574a921f2bc4213dd90e570ebca3d7826211d50ca341c45a234d232e90d4e"
SRC_URI[which_1.0.5.md5sum]="de8504eba9afa3e8a4e7feb9d3b52b38"
SRC_URI[which_1.0.5.sha256sum]="e26f39d7b152c700636472ab4da57bfb9af17972c49a9e2a06f9ff347d8fad42"
SRC_URI[grunt_0.4.5.md5sum]="e3d801997f70cfc526640941305dda9a"
SRC_URI[grunt_0.4.5.sha256sum]="6d7c28b8e3872cd723f9256013b26cde5f5305af06ed2c1c693995f51a33e5f5"
SRC_URI[grunt-contrib-concat_0.5.1.md5sum]="47377994d05ff77072932987e3bc4a1b"
SRC_URI[grunt-contrib-concat_0.5.1.sha256sum]="c0497e682a1bdcd6bbbd20f532a94603b50c37d3b3992e553a279df084ce5e4f"
SRC_URI[grunt-contrib-jshint_0.10.0.md5sum]="27fdfb36a0195161a76ebf777d280633"
SRC_URI[grunt-contrib-jshint_0.10.0.sha256sum]="4fa00d0f344e6f30d142efbca3dcd7cb2c738c8aab5af8c9c1e9392312b1dfdc"
SRC_URI[grunt-contrib-nodeunit_0.4.1.md5sum]="2a49276df3efef4614057c889bba66c1"
SRC_URI[grunt-contrib-nodeunit_0.4.1.sha256sum]="4cc5180ddf8f1763eb89f7a636ad77371c0764f43713426c48cf6fc53d4d43dc"
SRC_URI[grunt-contrib-qunit_0.7.0.md5sum]="60108c8600397e69f6bbd4850b5230d8"
SRC_URI[grunt-contrib-qunit_0.7.0.sha256sum]="c8df6ee0f724108d24147551c1d6e757471d3d0cf6e9f1777e7bc36c4f26dfb0"
SRC_URI[grunt-contrib-uglify_0.5.0.md5sum]="9d331aec2ee97eb005155f7d0dc693e1"
SRC_URI[grunt-contrib-uglify_0.5.0.sha256sum]="f4236d92bf9888876496782b161de7b80fb581ae10d5cd483667f811d3df77ce"
SRC_URI[grunt-contrib-watch_0.6.1.md5sum]="b70f1adda01c55b7b6f76b48662206fc"
SRC_URI[grunt-contrib-watch_0.6.1.sha256sum]="1d081e96de4f87db8561c31ce89082f2f48c4aca67f6bfd93abeb6ca797ed625"
SRC_URI[qunitjs_1.18.0.md5sum]="e81275e5af008358033c1263af77442a"
SRC_URI[qunitjs_1.18.0.sha256sum]="0aef191b55372129d8961892c51739c0c08d87309d4be3a18db3723481f35614"

PACKAGES = " \
abbrev-1.0.5 \
ansi-0.2.1 \
asn1-0.1.11 \
assert-plus-0.1.2 \
async-0.2.10 \
aws-sign2-0.5.0 \
block-stream-0.0.7 \
boom-0.4.2 \
combined-stream-0.0.4 \
cryptiles-0.2.2 \
ctype-0.5.2 \
delayed-stream-0.0.5 \
forever-agent-0.5.2 \
form-data-0.1.2 \
fstream-0.1.25 \
glob-3.2.9 \
graceful-fs-1.2.3 \
graceful-fs-2.0.3 \
grunt-0.4.5 \
grunt-contrib-concat-0.5.1 \
grunt-contrib-jshint-0.10.0 \
grunt-contrib-nodeunit-0.4.1 \
grunt-contrib-qunit-0.7.0 \
grunt-contrib-uglify-0.5.0 \
grunt-contrib-watch-0.6.1 \
hawk-1.0.0 \
hoek-0.9.1 \
http-signature-0.10.0 \
inherits-2.0.1 \
json-stringify-safe-5.0.0 \
license-destdir \
lru-cache-2.5.0 \
mime-1.2.11 \
minimatch-0.2.14 \
mkdirp-0.3.5 \
mkdirp-0.4.0 \
node-uuid-1.4.1 \
nopt-2.2.1 \
npmlog-0.0.6 \
oauth-sign-0.3.0 \
osenv-0.0.3 \
package \
punycode-1.2.4 \
qs-0.6.6 \
qunitjs-1.18.0 \
request-2.34.0 \
rimraf-2.2.6 \
semver-2.2.1 \
sigmund-1.0.0 \
sntp-0.2.4 \
tar-0.1.19 \
tough-cookie-0.12.1 \
tunnel-agent-0.3.0 \
which-1.0.5 \
"

LICENSE_abbrev-1.0.5 = "MIT"
LICENSE_ansi-0.2.1 = "MIT"
LICENSE_asn1-0.1.11 = "MIT"
LICENSE_assert-plus-0.1.2 = "MIT"
LICENSE_async-0.2.10 = "MIT"
LICENSE_aws-sign2-0.5.0 = "Apache-2.0"
LICENSE_block-stream-0.0.7 = "BSD-2-Clause"
LICENSE_boom-0.4.2 = "BSD-3-Clause"
LICENSE_combined-stream-0.0.4 = "MIT"
LICENSE_cryptiles-0.2.2 = "MIT"
LICENSE_ctype-0.5.2 = "MIT"
LICENSE_delayed-stream-0.0.5 = "MIT"
LICENSE_forever-agent-0.5.2 = "Apache-2.0"
LICENSE_form-data-0.1.2 = "MIT"
LICENSE_fstream-0.1.25 = "MIT"
LICENSE_glob-3.2.9 = "ISC"
LICENSE_graceful-fs-1.2.3 = "ISC"
LICENSE_graceful-fs-2.0.3 = "ISC"
LICENSE_grunt-0.4.5 = "MIT"
LICENSE_grunt-contrib-concat-0.5.1 = "MIT"
LICENSE_grunt-contrib-jshint-0.10.0 = "MIT"
LICENSE_grunt-contrib-nodeunit-0.4.1 = "MIT"
LICENSE_grunt-contrib-qunit-0.7.0 = "MIT"
LICENSE_grunt-contrib-uglify-0.5.0 = "MIT"
LICENSE_grunt-contrib-watch-0.6.1 = "MIT"
LICENSE_hawk-1.0.0 = "Apache-2.0"
LICENSE_hoek-0.9.1 = "MIT"
LICENSE_http-signature-0.10.0 = "MIT"
LICENSE_inherits-2.0.1 = "ISC"
LICENSE_json-stringify-safe-5.0.0 = "ISC"
LICENSE_lru-cache-2.5.0 = "MIT"
LICENSE_mime-1.2.11 = "MIT"
LICENSE_minimatch-0.2.14 = "MIT"
LICENSE_mkdirp-0.3.5 = "MIT"
LICENSE_mkdirp-0.4.0 = "MIT"
LICENSE_node-uuid-1.4.1 = "MIT"
LICENSE_nopt-2.2.1 = "MIT"
LICENSE_npmlog-0.0.6 = "ISC"
LICENSE_oauth-sign-0.3.0 = "Apache-2.0"
LICENSE_osenv-0.0.3 = "ISC"
LICENSE_punycode-1.2.4 = "MIT"
LICENSE_qs-0.6.6 = "MIT"
LICENSE_qunitjs-1.18.0 = "MIT"
LICENSE_request-2.34.0 = "Apache-2.0"
LICENSE_rimraf-2.2.6 = "MIT"
LICENSE_semver-2.2.1 = "ISC"
LICENSE_sigmund-1.0.0 = "ISC"
LICENSE_sntp-0.2.4 = "BSD-3-Clause"
LICENSE_tar-0.1.19 = "ISC & MIT"
LICENSE_tough-cookie-0.12.1 = "CC0-1.0 & (MPL-1.1 | GPL-2.0-only | LGPL-2.1-only) & MIT"
LICENSE_tunnel-agent-0.3.0 = "Apache-2.0"
LICENSE_which-1.0.5 = "MIT"

ALL_LICENSES = "\
${LICENSE_abbrev-1.0.5} & \
${LICENSE_ansi-0.2.1} & \
${LICENSE_asn1-0.1.11} & \
${LICENSE_assert-plus-0.1.2} & \
${LICENSE_async-0.2.10} & \
${LICENSE_aws-sign2-0.5.0} & \
${LICENSE_block-stream-0.0.7} & \
${LICENSE_boom-0.4.2} & \
${LICENSE_combined-stream-0.0.4} & \
${LICENSE_cryptiles-0.2.2} & \
${LICENSE_ctype-0.5.2} & \
${LICENSE_delayed-stream-0.0.5} & \
${LICENSE_forever-agent-0.5.2} & \
${LICENSE_form-data-0.1.2} & \
${LICENSE_fstream-0.1.25} & \
${LICENSE_glob-3.2.9} & \
${LICENSE_graceful-fs-1.2.3} & \
${LICENSE_graceful-fs-2.0.3} & \
${LICENSE_grunt-0.4.5} & \
${LICENSE_grunt-contrib-concat-0.5.1} & \
${LICENSE_grunt-contrib-jshint-0.10.0} & \
${LICENSE_grunt-contrib-nodeunit-0.4.1} & \
${LICENSE_grunt-contrib-qunit-0.7.0} & \
${LICENSE_grunt-contrib-uglify-0.5.0} & \
${LICENSE_grunt-contrib-watch-0.6.1} & \
${LICENSE_hawk-1.0.0} & \
${LICENSE_hoek-0.9.1} & \
${LICENSE_http-signature-0.10.0} & \
${LICENSE_inherits-2.0.1} & \
${LICENSE_json-stringify-safe-5.0.0} & \
${LICENSE_lru-cache-2.5.0} & \
${LICENSE_mime-1.2.11} & \
${LICENSE_minimatch-0.2.14} & \
${LICENSE_mkdirp-0.3.5} & \
${LICENSE_mkdirp-0.4.0} & \
${LICENSE_node-uuid-1.4.1} & \
${LICENSE_nopt-2.2.1} & \
${LICENSE_npmlog-0.0.6} & \
${LICENSE_oauth-sign-0.3.0} & \
${LICENSE_osenv-0.0.3} & \
${LICENSE_punycode-1.2.4} & \
${LICENSE_qs-0.6.6} & \
${LICENSE_qunitjs-1.18.0} & \
${LICENSE_request-2.34.0} & \
${LICENSE_rimraf-2.2.6} & \
${LICENSE_semver-2.2.1} & \
${LICENSE_sigmund-1.0.0} & \
${LICENSE_sntp-0.2.4} & \
${LICENSE_tar-0.1.19} & \
${LICENSE_tough-cookie-0.12.1} & \
${LICENSE_tunnel-agent-0.3.0} & \
${LICENSE_which-1.0.5} \
"

# Remove the duplicates
LICENSE = "${@ ' & '.join(sorted(set('${ALL_LICENSES}'.strip().split(' & ')))) }"

LIC_FILES_CHKSUM = "\
file://${WORKDIR}/${BPN}/abbrev_1.0.5/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
file://${WORKDIR}/${BPN}/ansi_0.2.1/package/README.md;beginline=67;md5=c9dbec5380fde616b6393b2b1d91cd18 \
file://${WORKDIR}/${BPN}/asn1_0.1.11/package/LICENSE;md5=5ce2f42143c298495637e28e40cd5462 \
file://${WORKDIR}/${BPN}/assert-plus_0.1.2/package/README.md;beginline=101;md5=01b58088f134c724978d16324441a473 \
file://${WORKDIR}/${BPN}/async_0.2.10/package/LICENSE;md5=64a378b2b01424fe22d54bc626175994 \
file://${WORKDIR}/${BPN}/aws-sign2_0.5.0/package/LICENSE;md5=f3f8ead5440d1c311b45be065d135d90 \
file://${WORKDIR}/${BPN}/block-stream_0.0.7/package/LICENCE;md5=c695aba12d799c74e770d90e1c5d59aa \
file://${WORKDIR}/${BPN}/boom_0.4.2/package/LICENSE;md5=8595d57fda9e9f801719f48fb5bff49d \
file://${WORKDIR}/${BPN}/combined-stream_0.0.4/package/License;md5=5270b4ddb6700d0a820e6d066744589c \
file://${WORKDIR}/${BPN}/cryptiles_0.2.2/package/LICENSE;md5=a674df8d258d4437373838e36bc960d2 \
file://${WORKDIR}/${BPN}/ctype_0.5.2/package/LICENSE;md5=0d330d2474abae149a0126325ec70247 \
file://${WORKDIR}/${BPN}/delayed-stream_0.0.5/package/License;md5=5270b4ddb6700d0a820e6d066744589c \
file://${WORKDIR}/${BPN}/forever-agent_0.5.2/package/LICENSE;md5=f3f8ead5440d1c311b45be065d135d90 \
file://${WORKDIR}/${BPN}/form-data_0.1.2/package/License;md5=7aa505292e2636a9e59d4d3f258f4819 \
file://${WORKDIR}/${BPN}/fstream_0.1.25/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/glob_3.2.9/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/graceful-fs_1.2.3/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/graceful-fs_2.0.3/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/grunt_0.4.5/package/LICENSE-MIT;md5=71ec026c2f855627041a4d9a1f5d2c3e \
file://${WORKDIR}/${BPN}/grunt-contrib-jshint_0.10.0/package/LICENSE-MIT;md5=d4c0dac367d17453778bfc70faf69145 \
file://${WORKDIR}/${BPN}/grunt-contrib-nodeunit_0.4.1/package/LICENSE-MIT;md5=d4c0dac367d17453778bfc70faf69145 \
file://${WORKDIR}/${BPN}/grunt-contrib-uglify_0.5.0/package/LICENSE-MIT;md5=d4c0dac367d17453778bfc70faf69145 \
file://${WORKDIR}/${BPN}/grunt-contrib-watch_0.6.1/package/LICENSE-MIT;md5=7aa789b63a114f3e399acfb94bb570a5 \
file://${WORKDIR}/${BPN}/hawk_1.0.0/package/LICENSE;md5=a674df8d258d4437373838e36bc960d2 \
file://${WORKDIR}/${BPN}/hoek_0.9.1/package/LICENSE;md5=1dd27b734d6fd3763ba8d9d1d4d2a44f \
file://${WORKDIR}/${BPN}/http-signature_0.10.0/package/LICENSE;md5=38ecf0a3a3894f404ca99c2a08924afc \
file://${WORKDIR}/${BPN}/inherits_2.0.1/package/LICENSE;md5=5b2ef2247af6d355ae9d9f988092d470 \
file://${WORKDIR}/${BPN}/json-stringify-safe_5.0.0/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/lru-cache_2.5.0/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
file://${WORKDIR}/${BPN}/mime_1.2.11/package/LICENSE;md5=8eab89930e0b484cd991ed4a155f74f7 \
file://${WORKDIR}/${BPN}/minimatch_0.2.14/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
file://${WORKDIR}/${BPN}/mkdirp_0.3.5/package/LICENSE;md5=b2d989bc186e7f6b418a5fdd5cc0b56b \
file://${WORKDIR}/${BPN}/mkdirp_0.4.0/package/LICENSE;md5=b2d989bc186e7f6b418a5fdd5cc0b56b \
file://${WORKDIR}/${BPN}/node-uuid_1.4.1/package/LICENSE.md;md5=c1a615ca498934d9a312c2d5294cf2cf \
file://${WORKDIR}/${BPN}/nopt_2.2.1/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
file://${WORKDIR}/${BPN}/npmlog_0.0.6/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/oauth-sign_0.3.0/package/LICENSE;md5=f3f8ead5440d1c311b45be065d135d90 \
file://${WORKDIR}/${BPN}/osenv_0.0.3/package/LICENSE;md5=c695aba12d799c74e770d90e1c5d59aa \
file://${WORKDIR}/${BPN}/punycode_1.2.4/package/LICENSE-MIT.txt;md5=f6e63cacc921f39d701abb9091a7d73d \
file://${WORKDIR}/${BPN}/punycode_1.2.4/package/LICENSE-GPL.txt;md5=2c1778696d3ba68569a0352e709ae6b7 \
file://${WORKDIR}/${BPN}/qs_0.6.6/package/Readme.md;beginline=35;md5=90d7f26fbfa70f23a025e3e3502e5a5b \
file://${WORKDIR}/${BPN}/qunitjs_1.18.0/package/LICENSE.txt;md5=88e6b088e1bdb95427eca76a90663e3b \
file://${WORKDIR}/${BPN}/request_2.34.0/package/LICENSE;md5=f3f8ead5440d1c311b45be065d135d90 \
file://${WORKDIR}/${BPN}/rimraf_2.2.6/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
file://${WORKDIR}/${BPN}/semver_2.2.1/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/sigmund_1.0.0/package/LICENSE;md5=8ce909f9cccb74a7c6f2720930d072c1 \
file://${WORKDIR}/${BPN}/sntp_0.2.4/package/LICENSE;md5=a674df8d258d4437373838e36bc960d2 \
file://${WORKDIR}/${BPN}/tar_0.1.19/package/LICENCE;md5=c695aba12d799c74e770d90e1c5d59aa \
file://${WORKDIR}/${BPN}/tough-cookie_0.12.1/package/LICENSE;md5=069d7e6f46a4e30df19bc7cddf36674b \
file://${WORKDIR}/${BPN}/tunnel-agent_0.3.0/package/LICENSE;md5=f3f8ead5440d1c311b45be065d135d90 \
file://${WORKDIR}/${BPN}/which_1.0.5/package/LICENSE;md5=b3245a33f2d41818f14c489bd33bc4a8 \
"
