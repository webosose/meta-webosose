# Copyright (c) 2023 LG Electronics, Inc.

# This script helps to replace the webos path variable in *.service.in or *.sh.in

cmake_minimum_required(VERSION 2.8.7)
project(webos_systemd NONE)

include(webOS/webOS)
webos_modules_init(1 0 0 QUALIFIER RC7)

message("IN_FILES: ${IN_FILES}")

string(REPLACE " " ";" IN_FILES "${IN_FILES}")
foreach(in_file ${IN_FILES})
    string(REGEX REPLACE "\\.in$" "" file "${in_file}")

    if (in_file MATCHES "^.+\\.in$")
        configure_file(${CMAKE_SOURCE_DIR}/${in_file} ${file} @ONLY)
    endif()

    if (file MATCHES "^.+\\.service$")
        install(FILES ${CMAKE_BINARY_DIR}/${file} DESTINATION ${CMAKE_INSTALL_UNITDIR}/ )
    elseif (file MATCHES "^.+\\.path$")
        install(FILES ${CMAKE_BINARY_DIR}/${file} DESTINATION ${CMAKE_INSTALL_UNITDIR}/ )
    elseif (file MATCHES "^.+\\.target$")
        install(FILES ${CMAKE_BINARY_DIR}/${file} DESTINATION ${CMAKE_INSTALL_UNITDIR}/ )
    elseif (file MATCHES "^.+\\.sh$")
        install(FILES ${CMAKE_BINARY_DIR}/${file}
                PERMISSIONS OWNER_WRITE OWNER_READ OWNER_EXECUTE
                            GROUP_READ  GROUP_EXECUTE
                            WORLD_READ  WORLD_EXECUTE
                DESTINATION ${CMAKE_INSTALL_UNITDIR}/scripts )
    endif()
endforeach()

