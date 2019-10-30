// Program To Implement Intermediate Code Generation And Subsequent Evaluation

import java.io.*; // Importing the necessary packages
import java.util.*;
class IC_Generation_Evaluation{ // Declaring the Class
	
	String intermediate_code; // Will store the Intermediate Code
	int value; // Will store the final value
	String[][] var_value; // Will store the value of the variables (identifiers)
	Stack<String> operator_stack=new Stack<String>(); // Used during Intermediate Code generation
	Stack<Integer> evaluation_stack=new Stack<Integer>(); // Used during Intermediate Code evaluation
	
	IC_Generation_Evaluation(int n){ // Constructor
		intermediate_code="";
		var_value=new String[n][2];
	}
	
	private void intermediate_code_generator(Queue<String> tokens, HashMap<Integer, String> symbol_table){ // Will generate the Intermediate Code
		int no_of_tokens=tokens.size();
		for(int i=0;i<no_of_tokens;i++){ // Iterating through all the tokens
			String temp=tokens.remove();
			int token_id=temp.charAt(0);
			int token_attribute=(int)(temp.charAt(2))-48;
			if(token_id=='0'){ // Identifier
				intermediate_code=intermediate_code+symbol_table.get(token_attribute);
			}
			else{ // Operator
				String operator=symbol_table.get(token_attribute);
				
				if(operator_stack.empty()==true){
					operator_stack.push(operator);
				}
				else{
					if(operator.equals("*")==true){
						while((operator_stack.peek()).equals("*")==true){
							intermediate_code=intermediate_code+operator_stack.pop();
						}
						operator_stack.push("*");
					}
					else{
						while(!operator_stack.empty()){
							if(((operator_stack.peek()).equals("*")==true)||((operator_stack.peek()).equals("+")==true)){
								intermediate_code=intermediate_code+operator_stack.pop();
							}
						}
						operator_stack.push("+");
					}
				}
			}
		}
		while(!operator_stack.empty()){ // If stack is not empty
			intermediate_code=intermediate_code+operator_stack.pop();
		}
	}
	
	private void evaluator(){ // Will evaluate the Intermediate Code
		for(int i=0;i<intermediate_code.length();i++){ // Iterating through the intermediate code
			char temp=intermediate_code.charAt(i);
			if((temp>='0')&&(temp<='9')){ // Numeral
				evaluation_stack.push((int)(temp)-48);
			}
			else if(temp=='+'){ // Addition
				int temp1=evaluation_stack.pop();
				int temp2=evaluation_stack.pop();
				value=temp1+temp2;
				evaluation_stack.push(value);
			}
			else{ // Multiplication
				int temp1=evaluation_stack.pop();
				int temp2=evaluation_stack.pop();
				value=temp1*temp2;
				evaluation_stack.push(value);
			}
		}
	}
	
	public static void main(String args[])throws IOException { // Declaring the Main Method
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Lexical_Analyzer scanner=new Lexical_Analyzer(); // Object for Lexical Analyzer Class
		Syntax_Analyzer parser=new Syntax_Analyzer(); // Object for Syntax Analyzer Class
		
		System.out.println("\n ***** Compiler For Arithmetic Expressions ***** \n");
		
		System.out.println(" Enter The Number Of Variables (Identifiers) : ");
		int n=Integer.parseInt(br.readLine());
		IC_Generation_Evaluation ob=new IC_Generation_Evaluation(n); // Object
		
		System.out.println("\n Name And Initialize The Variables (Identifiers) [<Variable_Name>=<Variable_Value>] : ");
		for(int i=0;i<n;i++){
			String temp=br.readLine();
			ob.var_value[i][0]=temp.substring(0,temp.indexOf('='));
			ob.var_value[i][1]=temp.substring(temp.indexOf('=')+1);
		}
		
		System.out.println("\n Enter The Arithmetic Expression : ");
		String expression=br.readLine();
		
		scanner.lexical_analyzer(expression); // Performing lexical analysis
		if(scanner.flag==0){
			System.out.println("\n Lexical Analysis Complete! ");
			
			parser.syntax_analyzer(scanner.simplified_expression); // Performing syntax analysis
			if(parser.flag==1){
				System.out.println("\n Syntax Analysis Complete! ");
			}
			else{
				System.out.println("\n Syntax Analysis Failed! ");
			}
			
			ob.intermediate_code_generator(scanner.tokens, scanner.symbol_table); // Generating the intermediate code
			System.out.println(" \n Intermediate Code : " + ob.intermediate_code + " \n ");
			for(int i=0;i<n;i++){
				ob.intermediate_code=(ob.intermediate_code).replaceAll(ob.var_value[i][0], ob.var_value[i][1]);
			}
			System.out.println(" \n Intermediate Code [After Replacing The Values Of The Variables (Identifiers)] : " + ob.intermediate_code + " \n ");
			
			ob.evaluator();
			System.out.println(" \n Output (Value) Of The Arithmetic Expression : " + ob.value + " \n ");
		}
		else if(scanner.flag==1){
			System.out.println("\n Invalid Operator Encountered!\n Lexical Analysis Failed! ");
		}
		else if(scanner.flag==2){
			System.out.println("\n Expression Not Terminated With Proper Delimiter!\n Lexical Analysis Failed! ");
		}
	}
}