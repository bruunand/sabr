# BallsDeep

Embedded system for throwing ping-pong balls into plastic cups containing liquids with viscosity like that of water.
BallsDeep™ uses     N E U R A L   N E T W O R K S     to throw balls.

## Howto
-- Assuming you already have the leJOS and NXT software installed --

When your Main.java file is ready, open a command prompt and execute the following statements: 

### Compile to a .class file
> nxjc Main.java

Results in a Main.class file
### Link the class file to the NXT program
> nxjlink -o Main.nxj Main

### Upload to the NXT
> nxjupload -r Main.nxj

Omit the '-r' argument if you don't want the program to run as soon as it is uploaded.
