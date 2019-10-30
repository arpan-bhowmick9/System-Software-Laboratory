// Program To Implement Predictive Parser

import java.io.*; // Importing the necessary packages
import java.util.*;
class Predictive_Parser_A{ // Declaring the Class
	
	int flag;
	String[][] parsing_table; // Will store the Parsing Table
	Stack<Character> st=new Stack<Character>(); // Will store the symbols
	HashMap<Character, Integer> grammar_code=new HashMap<Character, Integer>(); // Will store the Symbol Table
	Queue<String> pdr=new LinkedList<String>(); // Will store the sequence of Production Rules
	
	Predictive_Parser_A(){ // Constructor
		flag=0;
		parsing_table=new String[5][4];
		for(int i=0;i<5;i++){ // Filling up the Parsing Table
			for(int j=0;j<4;j++){
				if((i==0)&&(j==0)){
					parsing_table[i][j]="E:=TX";
				}
				else if((i==1)&&(j==1)){
					parsing_table[i][j]="X:=+TX";
				}
				else if((i==1)&&(j==3)){
					parsing_table[i][j]="X:=e";
				}
				else if((i==2)&&(j==0)){
					parsing_table[i][j]="T:=FY";
				}
				else if((i==3)&&(j==1)){
					parsing_table[i][j]="Y:=e";
				}
				else if((i==3)&&(j==2)){
					parsing_table[i][j]="Y:=*FY";
				}
				else if((i==3)&&(j==3)){
					parsing_table[i][j]="Y:=e";
				}
				else if((i==4)&&(j==0)){
					parsing_table[i][j]="F:=a";
				}
				else{
					parsing_table[i][j]="-";
				}
			}
		}
		
		st.push('$'); // Initializing the stack
		st.push('E');
		
		grammar_code.put('E', 0); // Initializing the Hash Map
		grammar_code.put('X', 1);
		grammar_code.put('T', 2);
		grammar_code.put('Y', 3);
		grammar_code.put('F', 4);
		grammar_code.put('a', 0);
		grammar_code.put('+', 1);
		grammar_code.put('*', 2);
		grammar_code.put('$', 3);
	}
	
	private void parser(String expression){ // Predictive Parser
		int counter=0; // Pointer to the first character of the input
		
		while(true){
			char temp_s=st.peek(); // Top of the stack
			char temp_e=expression.charAt(counter); // First character of the expression
			if(temp_s==temp_e){
				if(temp_s=='$'){
					System.out.println("\n Parsing Complete! Expression Accepted! ");
					flag=1;
					break;
				}
				else{
					st.pop();
					counter++;
					if(counter>=expression.length()){
						System.out.println("\n Error In Parsing! ");
						break;
					}
				}
			}
			else{
				int row=grammar_code.get(temp_s);
				if(grammar_code.containsKey(temp_e)==true){
					int column=grammar_code.get(temp_e);
					if(parsing_table[row][column].equals("-")==true){
						System.out.println("\n Error In Parsing! ");
						break;
					}
					else{
						String temp_grammar=parsing_table[row][column];
						pdr.add(temp_grammar);
						temp_grammar=temp_grammar.substring(3);
						st.pop();
						if(temp_grammar.equals("e")==false){
							for(int i=temp_grammar.length()-1;i>=0;i--){
								st.push(temp_grammar.charAt(i));
							}
						}
					}
				}
				else{
					System.out.println("\n Error In Parsing! ");
					break;
				}
			}
		}
	}
	
	public static void main(String args[]) throws IOException { // Declaring the Main Method
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Predictive_Parser_A ob=new Predictive_Parser_A(); // Creating object
		
		System.out.println("\n ***** Predictive Parser ***** \n");
		System.out.println(" Enter The Expression : ");
		String expression=br.readLine();
		ob.parser(expression);
		
		if(ob.flag==1){
			System.out.println("\n Parsing Table : \n ");
			System.out.println("\t a\t+\t*\t$");
			for(int i=0;i<5;i++){ // Printing the Parsing Table
				if(i==0){
					System.out.print("E\t");
				}
				else if(i==1){
					System.out.print("X\t");
				}
				else if(i==2){
					System.out.print("T\t");
				}
				else if(i==3){
					System.out.print("Y\t");
				}
				else{
					System.out.print("F\t");
				}
				for(int j=0;j<4;j++){
					System.out.print(ob.parsing_table[i][j]+"\t");
				}
				System.out.println();
			}
			System.out.println("\n Sequence Of Production Rules : \n ");
			while(!(ob.pdr).isEmpty()){ // Printing the Sequence Of Production Rules
				String temp=(ob.pdr).remove();
				System.out.println(temp);
			}
		}
	}
}