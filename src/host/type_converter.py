BYTE_ORDER = 'big'

def short_to_bytes(content):
	return (content).to_bytes(2, byteorder=BYTE_ORDER)