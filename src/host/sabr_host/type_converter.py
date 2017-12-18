from struct import pack, unpack

BYTE_ORDER = '>'  # > for big endian, < for little endian


def short_to_bytes(content):
    return pack(BYTE_ORDER + 'h', content)


def bytes_to_short(content):
    return unpack(BYTE_ORDER + 'h', content)[0]


def float_to_bytes(content):
    return pack(BYTE_ORDER + 'f', content)


def bytes_to_float(content):
    return unpack(BYTE_ORDER + 'f', content)[0]


def uint_to_bytes(content):
    return pack(BYTE_ORDER + 'I', content)


def bytes_to_uint(content):
    return unpack(BYTE_ORDER + 'I', content)[0]

