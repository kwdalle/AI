README for Naive Bayes Classification Program
=============================================
The files necessary to run this program
are the Main.java and the BayesSystem.java.
You will also need meta data files and trai-
ning data to train the system. After the sys-
tem has been trained you will be able to
either classify files based on that training
or read in data to compute its accuracy.
This program is a Naive Bayes classification
program. It will take in meta data and train-
ing data and then subsequently be able to
either classify other files based on the given
meta data, or be able to compute the accuracy 
of other files that follow the meta data's 
shceme. You are able to Train as many times
as you like. However, you must train the system
before you can classify or read in files. You
can quit any time that you are at the main menu.

To compile the program you must simply type

javac Main.java

This will compile the entire program for you. To run
Program all that is needed is to type

java Main

This will execute the program and start you at
the user interface. Note that when you select
Train that if you misstype the file names, or
enter names of files that do not exist you will
continually be asked for file names to you enter
one that exists. This was done because all of the
other complonents of the program require that the
system be trained. This is also done because you
can move the files to the correct directory without
interfering with the program execution, and thus
no reason was seen to have the choice to exit
the program at that point in time. Also, note 
that you cannot classify or read data without
first having trained the system.
