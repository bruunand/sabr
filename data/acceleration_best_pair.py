import numpy as np
import matplotlib.pyplot as plt
import collections

motor_count = 8

def squaredError(a, b):
    sum = 0

    for i in range(len(a)):
        sum += np.math.pow(a[i] - b[i], 2)

    return sum

f = open("acceleration_data.txt")
data = np.loadtxt(f)
od = collections.OrderedDict()

for i in range(motor_count):
	for j in range(motor_count):
		if i == j or (j, i) in od:
			continue

		od.update({(i, j):squaredError(data[j], data[i])})

print(sorted(od.items(), key=lambda x:x[1]))