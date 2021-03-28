import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main method that creates an instance of the Recursive Descent Parsing
 * class and starts the parsing operations
 * @param args
 * @author Gianluca Coletti (3000065278)
 */

public class RecursiveDescentParsing{

    static File file;
    static ArrayList<String> tokens;
    static String token;

    public RecursiveDescentParsing(File f) {
        readFileLines();
    }
        
    public void readFileLines(){
        ArrayList<String> result = new ArrayList<>();
        Scanner s;
        try { //Try reading file
            s = new Scanner(file);

            while (s.hasNextLine()){
                //Add each line to ArrayList
                result.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        //Array list with tokens
        tokens = result;
        
    }
    
    int index = 0;
    public String getNextToken(){
        String t= null;
        
        //Get next token
        t = tokens.get(index);
        index++;

        //DEBUG: print token to terminal
        //System.out.println(t);

        // Return token
        return t;
    }

    public boolean parse() {
        token = getNextToken();
        
        if (!program() || !token.equals("$")){
            return false;
        } else {
            return true;
        }
    }

    // <program>
    public boolean program(){
        if (token.equals("begin")){
            token = getNextToken();
            if (statementList()){ 
                return true;
            } else {
                token = getNextToken();
                if (token.equals("$")){
                    return true;
                }return false;
            }
        } else {
            return false;
        }
    }

    // <statement_list>
    private boolean statementList() {
        if (!statement()){
            return false;
        } else if (token.equals(";")){
            token = getNextToken();
            return statementListPrime();
        } 
        else if (!token.equals("end")){
           return false;
        } 
        else{
            token = getNextToken();
            return true;
        }  
    }

    // <statement_list'>
    private boolean statementListPrime() {
        if (!statementList()){
            return statement();
        }
        else{   
            return false;
        }
    }

    // <statement>
    private boolean statement(){
        if (token.equals("if")){
            token = getNextToken();
            return expression();
        } else if (token.equals("id")){
            token = getNextToken();
            return statementPrime();
        } else if (token.equals(";")){
            token = getNextToken();
            return statement();
        }else{
            return false;
        }  
    }

    // <statement'>
    private boolean statementPrime(){   
        if (token.equals("(")){
            token = getNextToken();
            return parameters();
           
        } else if (token.equals("=")){
            token = getNextToken();
            if(!factor()){
                return false;
            } else {
                return expressionPrime();
            }
        }else {return statement();}  
    }

    private boolean parameters() {
        if (!factor()){
            return false;
            
        } else {
            token = getNextToken();
            return parametersPrime();
            
        }
    }

    private boolean parametersPrime() {
        token = getNextToken();
        if (token.equals(")")){
            return statement();
        } else if (token.equals(",")){
            token = getNextToken();
            return parameters();
        } else {
            return false;
        }
    }

    // <expression>
    private boolean expression() {
        if (factor()){
            token = getNextToken();
            return expressionPrime();        
        } else{
            if (token.equals(";") || token.equals("then")){
                return true;
            } 
            return false;
        }
    }

    // <expression'> 
    private boolean expressionPrime() {
        if (token.equals("+") || token.equals("-")){
            token = getNextToken();
            return factor();
        } else{
            return expression();
        }
    }

    // <factor>
    private boolean factor() {
        if (token.equals("id") || token.equals("num")){
            token = getNextToken();
            return true;
        } 
        else{
            return false;
        }
    }

    public static void main(String[] args){
        if (args.length > 0) {
            String filepath = args[0];

            file = new File(filepath);

            // Check if the specified path belongs to a file
            if (file.isFile() && file.exists()){
                RecursiveDescentParsing parser = new RecursiveDescentParsing(file);

                // Parse file
                boolean success = parser.parse();

                // Display results of parse
                if (success) {
                    System.out.println("SUCCESS: the code has been successfully parsed");
                } else {
                    System.out.println("ERROR: the code contains a syntax mistake");
                }
            } else {

                // Specified arguement does not match valid file path
                System.err.println(args);
                System.err.println("Specified file path is invalid");
            }
        } else {
            // No input file specified
            System.err.println("USAGE: Specify the path of the input file as an arguement");
        }
        
    }
}