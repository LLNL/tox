# What is this?

The ivy directory is the destination of Jar library files brought into the project via the Ivy Ant target, *ivy.resolve*.

### If git converted the symbolic links meant to point to the versioned Jar libraries into text files, create a squashed.sh script to convert them...

---
```
#!/bin/bash
for F in `ls *.jar`
do
  export LOC=`cat $F`
  rm $F
  ln -s $LOC $F
  ls -l $F
done
```
---
