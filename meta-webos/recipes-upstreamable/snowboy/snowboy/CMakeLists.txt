# @@@LICENSE
#
# Copyright (c) 2019-2023 LG Electronics, Inc.
#
# LICENSE@@@

cmake_minimum_required(VERSION 2.8.7)
project(snowboy-detect)

configure_file(${CMAKE_SOURCE_DIR}/snowboy.pc.in snowboy.pc @ONLY)

# install
install(FILES lib/${SNOWBOY_ARCH}/libsnowboy-detect.a DESTINATION ${CMAKE_INSTALL_LIBDIR}/snowboy)
install(FILES include/snowboy-detect.h DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
install(FILES ${CMAKE_BINARY_DIR}/snowboy.pc DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/pkgconfig)

option (BUILD_SHARED_LIB "Build the shared library as an wrapper" OFF)
if(BUILD_SHARED_LIB)
    # force build as 'C' for .cc file & set flags
    SET_SOURCE_FILES_PROPERTIES(examples/C/snowboy-detect-c-wrapper.cc PROPERTIES LANGUAGE C)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fPIC -Wall ${SNOWBOY_EXTRA_FLAGS}")

    # add include files
    include_directories(
        ${CMAKE_SOURCE_DIR}/
        ${CMAKE_SOURCE_DIR}/examples/C
    )

    # wrapper
    add_library(snowboy-detect-c SHARED examples/C/snowboy-detect-c-wrapper.cc)
    set_target_properties(snowboy-detect-c PROPERTIES SOVERSION 1.3.0)

    # add static library to shared object
    target_link_libraries(snowboy-detect-c PUBLIC
        -Wl,--whole-archive
        ${CMAKE_SOURCE_DIR}/lib/${SNOWBOY_ARCH}/libsnowboy-detect.a
        -Wl,--no-whole-archive
        cblas
    )

    install(TARGETS snowboy-detect-c DESTINATION ${CMAKE_INSTALL_LIBDIR}/snowboy)
    install(FILES examples/C/snowboy-detect-c-wrapper.h DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
endif()

option (INSTALL_KEYWORD_MODELS "Install the keyword activation models. snowboy, jarvis, alexa" OFF)
if(INSTALL_KEYWORD_MODELS)
    install(
        FILES
        resources/common.res
        resources/models/snowboy.umdl
        resources/models/jarvis.umdl
        resources/alexa/alexa-avs-sample-app/alexa.umdl
        DESTINATION /etc/snowboy/models
    )
endif()
