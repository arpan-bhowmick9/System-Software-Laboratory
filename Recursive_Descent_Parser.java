// Program To Implement Recursive Descent Parser

import java.io.*; // Importing the necessary packages
import java.util.*;
class Recursive_Descent_Parser{ // Declaring the Class
	
	int lexemeBegin, forward, flag, parser_counter; // Global Variables
	HashMap<Integer, String> symbol_table=new HashMap<Integer, String>(); // Will store the Symbol Table
	Queue<String> tokens=new LinkedList<String>(); // Will store the tokens
	
	Recursive_Descent_Parser(){ // Constructor
		lexemeBegin=0;
		forward=0;
		flag=0;
		parser_counter=0;
	}
	
	
	
	private void lexical_analyzer(String expression){ // Lexical Analyzer
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
				symbol_table.put(counter, identifier);
				tokens.add("0,"+Integer.valueOf(counter));
				counter++;
				lexemeBegin=forward;
			}
			
			else if((temp=='+') || (temp=='*')){ // If the character is an operator
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
				break;
			}
			
			else{ // If an invalid character is encountered
				flag=1;
				break;
			}
			
		}
		
		if(flag==0){
			System.out.println("\n Lexical Analysis Complete! ");
		}
		else if(flag==1){
			System.out.println("\n Invalid Operator Encountered! ");
		}
		else if(flag==2){
			System.out.println("\n Expression Not Terminated With Proper Delimiter! ");
		}
	}
	
	private void E(){ // For Nonterminal E
		T();
		Eds();
	}
	
	private void Eds(){ // For Nonterminal E'
		String temp=tokens.peek(); // Dequeuing the token
		if(temp!=null){ // If token exists
			if(temp.charAt(0)=='1'){ // If the token is an operator
				int key=(int)(temp.charAt(2))-48; // The location in the Symbol Table
				String temp1=symbol_table.get(key); // Token attribute
				if(temp1.equals("+")==true){
					tokens.remove();
					parser_counter++;
					T();
					Eds();
				}
			}
			else{
				System.out.println("\n Error In Parsing! ");
			}
		}
	}
	
	private void T(){ // For Nonterminal T
		F();
		Tds();
	}
	
	private void Tds(){ // For Nonterminal T'
		String temp=tokens.peek(); // Dequeuing the token
		if(temp!=null){ // If token exists
			if(temp.charAt(0)=='1'){ // If the token is an operator
				int key=(int)(temp.charAt(2))-48; // The location in the Symbol Table
				String temp1=symbol_table.get(key); // Token attribute
				if(temp1.equals("*")==true){
					tokens.remove();
					parser_counter++;
					F();
					Tds();
				}
			}
			else{
				System.out.println("\n Error In Parsing! ");
			}
		}
	}
	
	private void F(){ // For Nonterminal F
		String temp=tokens.peek(); // Dequeuing the token
		if(temp!=null){ // If token exists
			if(temp.charAt(0)=='0'){ // If the token is an operator
				tokens.remove();
				parser_counter++;
			}
			else{
				E();
			}
		}
	}
	
	public static void main(String args[]) throws IOException{ // Declaring the Main Method
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Recursive_Descent_Parser ob=new Recursive_Descent_Parser(); // Creating object
		
		System.out.println("\n ***** Recursive Descent Parser ***** \n");
		System.out.println(" Enter The Arithmetic Expression : ");
		String expression=br.readLine();
		ob.lexical_analyzer(expression);
		int no_of_tokens=(ob.tokens).size();
		ob.E();
		if((ob.parser_counter==no_of_tokens) && (ob.flag==0)){
			System.out.println("\n Parsing Successful! ");
		}
		else{
			System.out.println("\n Error In Parsing! ");
		}
	}
}