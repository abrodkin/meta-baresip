SUMMARY = "baresip"
DESCRIPTION = "Baresip is a portable and modular SIP User-Agent with audio and video support."
HOMEPAGE = "https://github.com/baresip/baresip.git"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://docs/COPYING;md5=ca21e6b46da99fff423bae72eb2bbaa8"
DEPENDS = "libre librem"
RDEPENDS:${PN} = "libre librem"
RRECOMMENDS:${PN} += "ca-certificates"

SRC_URI = "git://github.com/baresip/baresip.git;tag=v${PV};branch=master;protocol=https"

SRC_URI[md5sum] = "8c45702b44d12c74f1790ae0766fde27"
SRC_URI[sha256sum] = "f9230b27c4a62f31223847bc485c51f3d960f8a09f36998dedb73358e1784b4e"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "\
    LIBRE_MK=${STAGING_DATADIR}/re/re.mk \
    CC='${CC}' \
    LD='${CC}' \
    EXTRA_CFLAGS='${CFLAGS}' \
    EXTRA_LFLAGS='${LDFLAGS}' \
    OS=linux \
    ARCH=${TARGET_ARCH} \
    DESTDIR=${D} \
    V=1 \
"

# Module detection magic in libre up to v2.0.1 is implmented
# by checking of headers supposedly avaialble on target system
# if certain libs or packages are installed.
# In the next release there will be a more robust test via
# compilation of tests, see https://github.com/baresip/re/commit/2697dcd
EXTRA_OEMAKE += " SYSROOT=${STAGING_EXECPREFIXDIR}"

inherit pkgconfig

PACKAGECONFIG ??= "\
    amr fdk-aac ffmpeg glib gstreamer sdl2 \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa directfb', d)} \
"

# Baresip auto-detects supported components during its build process
# thus nothing is done here except we add components which might be
# detected and used in run-time
PACKAGECONFIG[alsa] = ",,alsa-lib"
PACKAGECONFIG[amr] = ",,opencore-amr"
PACKAGECONFIG[directfb] = ",,directfb"
PACKAGECONFIG[fdk-aac] = ",,fdk-aac"
PACKAGECONFIG[ffmpeg] = ",,ffmpeg"
PACKAGECONFIG[glib] = ",,glib-2.0-native"
PACKAGECONFIG[gstreamer] = ",,gstreamer1.0"
PACKAGECONFIG[sdl2] = ",,libsdl2"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}

FILES:${PN}-dbg += "${libdir}/baresip/modules/.debug/*"
