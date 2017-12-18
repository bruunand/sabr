cd src
find . -type f -name '*.class' -delete
../leJOS-Linux/bin/nxjc com/sabr/Main.java
while true
do
    ../leJOS-Linux/bin/nxj -r -b com.sabr.Main
done
