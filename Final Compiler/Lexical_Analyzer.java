// Program To Implement Lexical Analyzer

import java.io.*; // Importing the necessary packages
import java.util.*;
class Lexical_Analyzer{ // Declaring the Class
	
	int lexemeBegin, forward, flag; // Global Variables
	HashMap<Integer, String> symbol_table=new HashMap<Integer, String>(); // Will store the Symbol Table
	Queue<String> tokens=new LinkedList<String>(); // Will store the tokens
	String simplified_expression; // Simplied Expression for ease of programming
	
	Lexical_Analyzer(){ // Constructor
		lexemeBegin=0;
		forward=0;
		flag=0;
		simplified_expression="";
	}
	
	
	
	protected void lexical_analyzer(String expression){ // Lexical Analyzer
		int counter=0;
		while(true){ // Iterating through the entire arithmetic expression
			
			if(lexemeBegin>=expression.length()){
				flag=2;
				break;
			}
			
			char temp=expression.charAt(lexemeBegin);
			
			if(((temp>='a') && (temp<='z')) || ((temp>='A') && (temp<='Z'))){ // If the character is an alphabet
				forward=lexemeBegin;
				while(forward<expression.length()){
					char temp_char=expression.charAt(forward);
					if(((temp_char>='a') && (temp_char<='z')) || ((temp_char>='A') && (temp_char<='Z')) || ((temp_char>='0') && (temp_char<='9'))){
						forward++;
						continue;
					}
					else{
						break;
					}
				}
				String identifier=expression.substring(lexemeBegin, forward);
				simplified_expression=simplified_expression+"a";
				symbol_table.put(counter, identifier);
				tokens.add("0,"+Integer.valueOf(counter));
				counter++;
				lexemeBegin=forward;
			}
			
			else if((temp=='+') || (temp=='*')){ // If the character is an operator
				simplified_expression=simplified_expression+Character.toString(temp);
				symbol_table.put(counter, String.valueOf(temp));
				tokens.add("1,"+Integer.valueOf(counter));
				counter++;
				lexemeBegin++;
			}
			
			else if(temp==' '){ // If the character is a whitespace
				lexemeBegin++;
				continue;
			}
			
			else if(temp=='$'){
				simplified_expression=simplified_expression+"$";
				break;
			}
			
			else{ // If an invalid character is encountered
				flag=1;
				break;
			}
			
		}
	}
}