from ballthrower.target_info import TargetInfo
import statistics

pixelHeight = 94
physicalDistance = 160.0
physicalHeight = 12.0
focalLength = pixelHeight * physicalDistance / physicalHeight

def calculate_distance(target):
    return focalLength * physicalHeight / target.height

target_info = TargetInfo(capture_device=1)
while True:
    distance = input("Distance: ")

    heights = []

    for i in range(0, 3):
        print(f"Image {i}")

        targets = target_info.get_targets()[0]
        if len(targets) != 1:
            print(f'Length: {len(targets)}')
            continue

        for target in targets:
            print(f"Height: {target.height}, Distance: {calculate_distance(target)}")
            heights.append(target.height)

    with open('data.txt', 'a') as file:
        file.writelines(f'{distance}\t{statistics.median(heights)}\n')
