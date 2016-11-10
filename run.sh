# code to compile then execute the program using java
# input files: paymo_input/batch_payment.csv paymo_input/stream_payment.csv
# output files: paymo_output/output1.txt paymo_output/output2.txt paymo_output/output3.txt

javac src/*.java
java -classpath src paymoFraudCheck paymo_input/batch_payment.csv paymo_input/stream_payment.csv paymo_output/output1.txt paymo_output/output2.txt paymo_output/output3.txt
