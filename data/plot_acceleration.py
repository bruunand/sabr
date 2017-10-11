import numpy as np
import matplotlib.pyplot as plt

# plot graph
motors = ["A", "B", "C", "D", "E", "F", "G", "H"]
x = [50, 60, 70, 80, 90, 100]

plt.xlabel('Motor power')
plt.ylabel('Time to rotate (milliseconds)')

f = open("acceleration_data.txt")

data = np.loadtxt(f)

print(len(motors))
for i in range(len(motors)):
	plt.plot(x, data[i], label="Motor {0}".format(motors[i]))

plt.legend(loc='lower left')

plt.show()
