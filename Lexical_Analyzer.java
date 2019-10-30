// Program To Implement Lexical Analyzer

import java.io.*; // Importing the necessary packages
import java.util.*;
class Lexical_Analyzer{ // Declaring the Class
	
	int lexemeBegin, forward, flag; // Global Variables
	
	Lexical_Analyzer(){ // Constructor
		lexemeBegin=0;
		forward=0;
		flag=0;
	}
	
	
	
	public static void main(String args[]) throws IOException { // Declaring the Main Method
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Lexical_Analyzer ob=new Lexical_Analyzer(); // Creating object
		
		HashMap<Integer, String> symbol_table=new HashMap<Integer, String>(); // Will store the Symbol Table
		Queue<String> tokens=new LinkedList<String>(); // Will store the tokens
		
		System.out.println("\n ***** Lexical Analyzer ***** \n");
		System.out.println(" Enter The Arithmetic Expression : ");
		String expression=br.readLine();
		
		int counter=0;
		while(true){ // Iterating through the entire arithmetic expression
			
			if(ob.lexemeBegin>=expression.length()){
				ob.flag=2;
				break;
			}
			
			char temp=expression.charAt(ob.lexemeBegin);
			
			if(((temp>='a') && (temp<='z')) || ((temp>='A') && (temp<='Z'))){ // If the character is an alphabet
				ob.forward=ob.lexemeBegin;
				while(ob.forward<expression.length()){
					char temp_char=expression.charAt(ob.forward);
					if(((temp_char>='a') && (temp_char<='z')) || ((temp_char>='A') && (temp_char<='Z')) || ((temp_char>='0') && (temp_char<='9'))){
						ob.forward++;
						continue;
					}
					else{
						break;
					}
				}
				String identifier=expression.substring(ob.lexemeBegin, ob.forward);
				symbol_table.put(counter, identifier);
				tokens.add("0,"+Integer.valueOf(counter));
				counter++;
				ob.lexemeBegin=ob.forward;
			}
			
			else if((temp=='+') || (temp=='*')){ // If the character is an operator
				symbol_table.put(counter, String.valueOf(temp));
				tokens.add("1,"+Integer.valueOf(counter));
				counter++;
				ob.lexemeBegin++;
			}
			
			else if(temp==' '){ // If the character is a whitespace
				ob.lexemeBegin++;
				continue;
			}
			
			else if(temp=='$'){
				break;
			}
			
			else{ // If an invalid character is encountered
				ob.flag=1;
				break;
			}
			
		}
		
		if(ob.flag==0){
			System.out.println("\n The Tokens Are : ");
			int no_of_tokens=tokens.size();
			for(int i=0;i<no_of_tokens;i++){
				System.out.println(tokens.remove()); // Printing the Tokens
			}
			System.out.println("\n The Corresponding Symbol Table : \n");
			System.out.println(Arrays.asList(symbol_table)); // Printing the Symbol Table
		}
		else if(ob.flag==1){
			System.out.println("\n Invalid Operator Encountered! ");
		}
		else if(ob.flag==2){
			System.out.println("\n Expression Not Terminated With Proper Delimiter! ");
		}
	}
}