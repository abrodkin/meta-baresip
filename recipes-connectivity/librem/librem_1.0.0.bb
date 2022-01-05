SUMMARY = "librem"
DESCRIPTION = "Audio and video processing media library."
HOMEPAGE = "https://github.com/baresip/rem.git"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://docs/COPYING;md5=a0736345c45291f0f293a69c85460648"
DEPENDS = "libre"
RDEPENDS_${PN} = "libre"

SRC_URI = "git://github.com/baresip/rem.git;tag=v${PV};branch=master;protocol=https"

SRC_URI[md5sum] = "8e2ef31abfe570f99c593e24fae6951d"
SRC_URI[sha256sum] = "bcc91bb521fae183357fb422b00a3981477a22e99d3afe165c4ec50a6bbed9da"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "\
    LIBRE_MK=${STAGING_DATADIR}/re/re.mk \
    LIBRE_INC=${STAGING_INCDIR}/re \
    CC='${CC}' \
    LD='${CC}' \
    EXTRA_CFLAGS='${CFLAGS}' \
    EXTRA_LFLAGS='${LDFLAGS}' \
    OS=linux \
    ARCH=${TARGET_ARCH} \
    DESTDIR=${D} \
"

inherit pkgconfig

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}

# There's no ABI versioning in the 1.0.0 release
# But will be since the next release, as it was added with
# https://github.com/baresip/rem/commit/eea93d400fbf0efa3e7930babd1e6662b2d6aca2
FILES_SOLIBSDEV = ""

# Once ABI versioning is in place, this work-around won't be needed
FILES:${PN} += "${libdir}/*.so"
