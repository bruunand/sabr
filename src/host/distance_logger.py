from ballthrower.target_info import TargetInfo
from ballthrower.tcp.client import Client
import statistics
import cv2

pixelHeight = 133
physicalDistance = 120.0
physicalHeight = 12.0
focalLength = pixelHeight * physicalDistance / physicalHeight


def calculate_distance(target):
    return focalLength * physicalHeight / target.height


target_info = TargetInfo(capture_device=1, passthrough_client=Client("74.82.29.43", 9000))


def capture():
    heights = []
    widths = []
    distances = []

    for i in range(20):
        frame = target_info.get_frame()
        targets, width = target_info.get_targets(frame)
        if len(targets) != 1:
            print(f'Length: {len(targets)}')
            i = i - 1
            continue

        for target in targets:
            target.draw_rectangle(frame)

            deviance = (width / 2) - (target.x_min + target.width / 2)
            distance = calculate_distance(target)

            heights.append(target.height)
            widths.append(target.width)
            distances.append(distance)

            print(f"Height: {target.height}, Width: {target.width}, Distance: {distance}, Deviance: {deviance}")

        print(f"[Medians] Height: {statistics.median(heights)}, Width: {statistics.median(widths)}")
        print(f"[Variance] Height: {statistics.pvariance(heights)}, Width: {statistics.pvariance(widths)}")

        cv2.imwrite('test.jpg', frame)

    with open('distances_at_height.txt', 'a') as file:
        file.writelines(f'{distance}\t{statistics.mean(distances)}\n')

    return statistics.pvariance(heights)

while True:
    capture(input("Distance: "))
