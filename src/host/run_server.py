from sabr_host.tcp.server import Server

if __name__ == '__main__':
    server = Server(9000)
    server.listen()
