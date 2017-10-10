import numpy as np
import matplotlib.pyplot as plt

# plot graph
motors = ["A", "B", "C", "D", "E", "F", "G", "H"]
x = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]

plt.xlabel('Motor power')
plt.ylabel('RPM')

f = open("rpm_data.txt")

data = np.loadtxt(f)

print(len(motors))
for i in range(len(motors)):
	plt.plot(x, data[i], label="Motor {0}".format(motors[i]))

plt.legend(loc='upper left')

plt.show()