cd src
../leJOS-Linux/bin/nxjc com/tools/CompareTest.java
../leJOS-Linux/bin/nxjlink -o Exception.nxj -od Exception.nxd com.tools.CompareTest
while true
do
    ../leJOS-Linux/bin/nxj -r -b com.tools.CompareTest
done
