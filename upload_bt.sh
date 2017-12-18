cd src
find . -type f -name '*.class' -delete
../leJOS-Linux/bin/nxjc com/sabr/Main.java
../leJOS-Linux/bin/nxjlink -o Main.nxj -od Main.nxd com.sabr.Main
while true
do
    ../leJOS-Linux/bin/nxj -r -b com.sabr.Main
done
