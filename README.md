## Bottom up LR Parser Features

- The purpose of this project is to construct a bottom-up LR parser. This project used the following grammar and LR parse table.

##### Grammar

    E -> E + T
	E -> T
	T -> T * F
	T -> F
	F -> (E)
	F -> id
    
##### LR Parsing Table

| Stack | Input | Action |
| ------------- | ------------- | ------------- |

| | Action | |  |  |  |  |  | Goto |  |  |
|------------- | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------|------------- |
| State  | id  | + | * | ( | ) | $ | | E | T | F
| 0  | S5  |     | S4  |    | | | | 1 | 2 | 3 |
| 1   |      | S6 |      |    | | accept | | | | |
| 2  |      | R2 | S7 |     | R2 | R2 | | | | |
| 3  |      | R4 | R4 |     | R4 | R4 | | | | |
| 4  | S5 |      |      | S4 |     | | | 8 | 2 | 3 |
| 5  |      | R6 | R6 |      | R6 | R6| | | | |
| 6  | S5 |      |      | S4 | | | | | 9 | 3 |
| 7  | S5 |      |      | S4 | | | | | | 10 |
| 8  |      | S6 |      |      | S11 | | | | | |
| 9  |      | R1  | S7 |      | R1 | R1| | | | |
| 10 |      | R3 | R3 |      | R3| R3| | | | |
| 11 || R5 | R5 || R5| R5| | | | | |


### Sample Run

The code will take the input from the console and will output the contents of Stack, Input, and Action to an output file as in the following example where the source code, input string and output file are named BottomUpLRParser.java, id+id*id$, and output.txt, respectively:

	java BottomUpLRParser.java	 id+id*id$ 	output.txt
	The input has been parsed successfully.

The output file “output.txt” consists of the following content:

| Stack | Input | Action |
| ------------- | ------------- | ------------- |
| 0 | id + id * id $ | Shift 5 |
| 0id5 |  + id * id $ | Reduce 6
| 0F3 | + id * id $ | Reduce 4
| 0T2 | + id * id $ | Reduce 2
| 0E1 | + id * id $ | Shift 6
| 0E1+6 | id * id $ | Shift 5
| 0E1+6id5 | * id $ | Reduce 6
| 0E1+6F3 | * id $ | Reduce 4
| 0E1+6T9 | * id $ | Shift 7
| 0E1+6T9*7 | id $ | Shift 5
| 0E1+6T9*7id5 | $ | Reduce 6
| 0E1+6T9*7F10 | $ | Reduce 3
| 0E1+6T9 | $ | Reduce 1
| 0E1 | $ | Accept

### Another Run
	java hw2_firstname_lastname.java id++*id$ output.txt
	Error occurred.

The content of output.txt:

| Stack | Input | Action |
| ------------- | ------------- | ------------- |
| 0 | id + + * id $ | Shift 5
| 0id5 | + + * id $ | Reduce 6
| 0F3 | + + * id $ | Reduce 4
| 0T2 | + + * id $ | Reduce 2
| 0E1 | + + * id $ | Shift 6
| 0E1+6 | + * id $ | ERROR

