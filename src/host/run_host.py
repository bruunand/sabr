from sabr_host.host import Host

if __name__ == '__main__':
    instance = Host(nxt_name="YAYER") #tcp_host=("74.82.29.43", 9000)
    instance.connect()
    instance.handle_packets()
