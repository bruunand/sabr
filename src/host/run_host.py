from ballthrower.ballthrower import BallThrower

if __name__ == '__main__':
    instance = BallThrower(host_name="YAYER", tcp_host=("74.82.29.43", 9000))
    instance.connect()
    instance.handle_packets()
