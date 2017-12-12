cd src
find . -type f -name '*.class' -delete
../leJOS-Linux/bin/nxjc com/ballthrower/Main.java
while true
do
    ../leJOS-Linux/bin/nxj -r -b com.ballthrower.Main
done
