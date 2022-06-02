import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class BottomUpLRParser {


    String fileName = "";
    String [][] parsingTable = new String[12][9];
    //String input = "id+id*id$";
    //String input = "id++*id$";
    String input = "";


    ArrayList<String> parsedInput = new ArrayList<>();
    ArrayList<String> stack = new ArrayList<>();


    public static void main(String[]args){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(args[1]), true));
            bw.write("Stack        Input       Action");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }

        BottomUpLRParser obj = new BottomUpLRParser();
        obj.input = args[0];
        obj.fileName = args[1];

        obj.createLRPrasingTable(obj.parsingTable);

        String action = "";
        obj.parseInput(obj.input);
        obj.stack.add("0");

        if(obj.parsedInput.size() == 0){
            System.out.println("Error occurred.");
            System.exit(0);
        }
        action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
        obj.writeToFile(obj.stack , obj.parsedInput , action );


        while(!action.equals("Accept") && !action.equals("ERROR")){
            String[] actionArr = action.split(" ");
            if (obj.parsedInput.size() == 0){
                System.out.println("Error occurred.");
                break;
            }
            else if(actionArr[0].equals("Shift")){
                obj.stack.add(obj.parsedInput.get(0));
                obj.stack.add(actionArr[1]);
                obj.parsedInput.remove(0);
                if(obj.parsedInput.size() <= 0 ){
                    System.out.println("Error occurred.");
                    break;
                }
                action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                obj.writeToFile(obj.stack , obj.parsedInput , action );
            }
            else if(actionArr[0].equals("Reduce")) {
                // 1. E -> E + T
                // 2. E -> T
                // 3. T -> T * F
                // 4. T -> F
                // 5. F -> (E)
                // 6. F -> id

                switch (actionArr[1]) {
                    case "1":
                        boolean findCase1 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals("T")) {
                                i = i - 2;
                                if(i >= 0 && obj.stack.get(i).equals("+")){
                                    i = i - 2;
                                    if(i >= 0 && obj.stack.get(i).equals("E")){
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);

                                        obj.stack.add("E");

                                        String numb = obj.findAction(obj.stack.get(i - 1), "E");
                                        obj.stack.add(numb);
                                        if(obj.parsedInput.size() <= 0 ){
                                            System.out.println("Error occurred.");
                                            break;
                                        }
                                        action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                        obj.writeToFile(obj.stack , obj.parsedInput , action);
                                        findCase1 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(findCase1 = false){
                            System.out.println("Error occurred.");
                        }
                        break;
                    case "2":
                        boolean findCase2 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals("T")) {
                                obj.stack.remove(i);
                                obj.stack.remove(i);
                                obj.stack.add("E");

                                String numb = obj.findAction(obj.stack.get(i - 1), "E");
                                obj.stack.add(numb);
                                if(obj.parsedInput.size() <= 0 ){
                                    System.out.println("Error occurred.");
                                    break;
                                }
                                action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                obj.writeToFile(obj.stack , obj.parsedInput , action);
                                findCase2 = true;
                                break;
                            }
                        }

                        if(findCase2 == false){
                            System.out.println("Error occurred.");
                        }
                        break;
                    case "3":
                        boolean findCase3 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals("F")) { //i
                                i = i - 2;
                                if(i >= 0 && obj.stack.get(i).equals("*")){ //i-2
                                    i = i - 2;
                                    if(i >= 0 && obj.stack.get(i).equals("T")){ //i-4
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);

                                        obj.stack.add("T");

                                        String numb = obj.findAction(obj.stack.get(i - 1), "T");
                                        obj.stack.add(numb);
                                        if(obj.parsedInput.size() <= 0 ){
                                            System.out.println("Error occurred.");
                                            break;
                                        }
                                        action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                        obj.writeToFile(obj.stack , obj.parsedInput , action);
                                        findCase3 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(findCase3 == false){
                            System.out.println("Error occurred.");
                        }
                        break;
                    case "4":
                        boolean findCase4 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals("F")) {

                                obj.stack.remove(i);
                                obj.stack.remove(i);
                                obj.stack.add("T");

                                String numb = obj.findAction(obj.stack.get(i - 1), "T");
                                obj.stack.add(numb);
                                if(obj.parsedInput.size() <= 0 ){
                                    System.out.println("Error occurred.");
                                    break;
                                }
                                action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                obj.writeToFile(obj.stack , obj.parsedInput , action);
                                findCase4 = true;
                                break;
                            }
                        }

                        if(findCase4 == false){
                            System.out.println("Error occurred.");
                        }
                        break;
                    case "5":
                        boolean findCase5 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals(")")) {
                                i = i - 2;
                                if(i>= 0 && obj.stack.get(i).equals("E")){
                                    i = i - 2;
                                    if(i>= 0 && obj.stack.get(i).equals("(")){
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);
                                        obj.stack.remove(i);

                                        obj.stack.add("F");

                                        String numb = obj.findAction(obj.stack.get(i - 1), "F");
                                        obj.stack.add(numb);
                                        if(obj.parsedInput.size() <= 0 ){
                                            System.out.println("Error occurred.");
                                            break;
                                        }
                                        action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                        obj.writeToFile(obj.stack, obj.parsedInput , action);
                                        findCase5 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(findCase5 == false){
                            System.out.println("Error occurred.");
                        }
                        break;
                    case "6":
                        boolean findCase6 = false;
                        for (int i = obj.stack.size() - 1; i >= 0; i--) {
                            if (obj.stack.get(i).equals("id")) {

                                obj.stack.remove(i);
                                obj.stack.remove(i);
                                obj.stack.add("F");

                                String numb = obj.findAction(obj.stack.get(i - 1), "F");
                                obj.stack.add(numb);
                                if(obj.parsedInput.size() <= 0 ){
                                    System.out.println("Error occurred.");
                                    break;
                                }
                                action = obj.findAction(obj.stack.get(obj.stack.size()-1) , obj.parsedInput.get(0));
                                obj.writeToFile(obj.stack , obj.parsedInput , action);
                                findCase6 = true;
                                break;
                            }
                        }
                        if(findCase6 == false){
                            System.out.println("Error occurred.");
                        }
                        break;
                }
            }

        }

        if(action.equals("Accept")){
            System.out.println("The input has been parsed successfully.");
        }
        else if(action.equals("ERROR")){
            System.out.println("Error occurred.");
        }
    }

    public void writeToFile(ArrayList<String> stack, ArrayList<String> input, String action) {
        String stackString = "";
        String inputString = "";
        for(int i = 0 ; i < stack.size(); i++){
            stackString= stackString + stack.get(i);
        }
        for(int i = 0 ; i < input.size(); i++){
            inputString= inputString + input.get(i);
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.fileName), true));
            bw.write(stackString + "         " + inputString  + "         " + action);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }
    }

    public void parseInput(String input){
        String parsed = "";
        int letterStart = 0;
        if(input.length() != 0){
            for(int i = 0 ; i < input.length() ; i++){
                if((input.charAt(i)>='a' &&  input.charAt(i)<='z') || (input.charAt(i)>='A' &&  input.charAt(i)<='Z')){
                    letterStart = 1;
                    parsed = parsed + input.charAt(i);
                    if(i == input.length()-1){
                        parsedInput.add(parsed);
                    }
                }
                else{
                    if(letterStart == 1)
                        parsedInput.add(parsed);
                    parsedInput.add(String.valueOf(input.charAt(i)));
                    letterStart = 0;
                    parsed = "";
                }
            }
        }
    }

    public void createLRPrasingTable(String[][] table){

        for(int i = 0; i < table.length ; i++){
            for(int j = 0 ; j < table[0].length ; j++){
                table[i][j] = "ERROR";
            }
        }

        table[0][0] = "Shift 5";
        table[0][3] = "Shift 4";
        table[0][6] = "1";
        table[0][7] = "2";
        table[0][8] = "3";

        table[1][1] = "Shift 6";
        table[1][5] = "Accept";

        table[2][1] = "Reduce 2";
        table[2][2] = "Shift 7";
        table[2][4] = "Reduce 2";
        table[2][5] = "Reduce 2";

        table[3][1] = "Reduce 4";
        table[3][2] = "Reduce 4";
        table[3][4] = "Reduce 4";
        table[3][5] = "Reduce 4";

        table[4][0] = "Shift 5";
        table[4][3] = "Shift 4";
        table[4][6] = "8";
        table[4][7] = "2";
        table[4][8] = "3";

        table[5][1] = "Reduce 6";
        table[5][2] = "Reduce 6";
        table[5][4] = "Reduce 6";
        table[5][5] = "Reduce 6";

        table[6][0] = "Shift 5";
        table[6][3] = "Shift 4";
        table[6][7] = "9";
        table[6][8] = "3";

        table[7][0] = "Shift 5";
        table[7][3] = "Shift 4";
        table[7][8] = "10";

        table[8][1] = "Shift 6";
        table[8][4] = "Shift 11";

        table[9][1] = "Reduce 1";
        table[9][2] = "Shift 7";
        table[9][4] = "Reduce 1";
        table[9][5] = "Reduce 1";

        table[10][1] = "Reduce 3";
        table[10][2] = "Reduce 3";
        table[10][4] = "Reduce 3";
        table[10][5] = "Reduce 3";

        table[11][1] = "Reduce 5";
        table[11][2] = "Reduce 5";
        table[11][4] = "Reduce 5";
        table[11][5] = "Reduce 5";
    }

    public String findAction(String first , String second){

        int y = 0;
        switch(second){
            case "id":
                y = 0;
                break;
            case "+":
                y = 1;
                break;
            case "*":
                y = 2;
                break;
            case "(":
                y = 3;
                break;
            case ")":
                y = 4;
                break;
            case "$":
                y = 5;
                break;
            case "E":
                y = 6;
                break;
            case "T":
                y = 7;
                break;
            case "F":
                y = 8;
                break;
        }

        return this.parsingTable[Integer.parseInt(first)][y];
    }
}
