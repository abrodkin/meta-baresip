SUMMARY = "libre"
DESCRIPTION = "Generic library for real-time communications with async IO support."
HOMEPAGE = "https://github.com/baresip/re.git"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://docs/COPYING;md5=a0736345c45291f0f293a69c85460648"
DEPENDS = "openssl zlib"

SRC_URI = "git://github.com/baresip/re.git;tag=v${PV};branch=master;protocol=https \
    file://0001-build-infrastructure-silent-and-verbose-modes.patch"

SRC_URI[md5sum] = "0d6f8f9814895c337d032cddb5076efd"
SRC_URI[sha256sum] = "43aa439b96aff75fe5768b9f9d49dea97042e42e7647df47b345465763e2f7ed"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "\
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

PACKAGECONFIG ??= "openssl zlib"

PACKAGECONFIG[openssl] = ",,openssl"
PACKAGECONFIG[zlib] = ",,zlib"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}

FILES:${PN}-dev += "${datadir}/re/re.mk"
