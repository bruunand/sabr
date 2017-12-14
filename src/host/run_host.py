from ballthrower.ballthrower import BallThrower

if __name__ == '__main__':
    instance = BallThrower("YAYER")
    instance.connect()
    instance.handle_packets()
