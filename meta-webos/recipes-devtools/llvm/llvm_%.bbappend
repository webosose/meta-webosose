# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

PACKAGECONFIG[libedit] = "-DLLVM_ENABLE_LIBEDIT=ON,-DLLVM_ENABLE_LIBEDIT=OFF,libedit"

# These are used by gallium-llvm in mesa
WEBOS_NO_STATIC_LIBRARIES_WHITELIST = "libLLVMAnalysis.a libLLVMAsmPrinter.a libLLVMBitWriter.a libLLVMCodeGen.a libLLVMCore.a libLLVMExecutionEngine.a libLLVMInstCombine.a libLLVMMC.a libLLVMMCJIT.a libLLVMMCParser.a libLLVMObjCARCOpts.a libLLVMObject.a libLLVMRuntimeDyld.a libLLVMScalarOpts.a libLLVMSelectionDAG.a libLLVMSupport.a libLLVMTarget.a libLLVMTransformUtils.a libLLVMX86AsmParser.a libLLVMX86AsmPrinter.a libLLVMX86CodeGen.a libLLVMX86Desc.a libLLVMX86Disassembler.a libLLVMX86Info.a libLLVMX86Utils.a"

# Skipped libraries just because we don't need them now:
# libLLVMAMDGPUAsmParser.a libLLVMAMDGPUAsmPrinter.a libLLVMAMDGPUCodeGen.a libLLVMAMDGPUDesc.a libLLVMAMDGPUDisassembler.a libLLVMAMDGPUInfo.a libLLVMAMDGPUUtils.a libLLVMAsmParser.a libLLVMBinaryFormat.a libLLVMBitReader.a libLLVMCoroutines.a libLLVMCoverage.a libLLVMDebugInfoCodeView.a libLLVMDebugInfoDWARF.a libLLVMDebugInfoMSF.a libLLVMDebugInfoPDB.a libLLVMDemangle.a libLLVMDlltoolDriver.a libLLVMGlobalISel.a libLLVMInstrumentation.a libLLVMInterpreter.a libLLVMipo.a libLLVMIRReader.a libLLVMLibDriver.a libLLVMLineEditor.a libLLVMLinker.a libLLVMLTO.a libLLVMMCDisassembler.a libLLVMMIRParser.a libLLVMObjectYAML.a libLLVMOption.a libLLVMOrcJIT.a libLLVMPasses.a libLLVMProfileData.a libLLVMSymbolize.a libLLVMTableGen.a libLLVMVectorize.a libLLVMXRay.a
