// Program To Implement A Token Recognizer
// Token Is Represented By The Regular Expression (a)+b(a|b)*

import java.io.*; // Importing the necessary package
class Token_Recognizer{ // Declaring the Class
	
	int dfa; // Global Variable
	
	Token_Recognizer(){ // Constructor
		dfa=1;
	}
	
	private void state_1(char temp){ // Represents State 1
		if(temp=='a'){
			dfa=2;
		}
		else if(temp=='b'){
			dfa=4;
		}
		else{ // On encountering an invalid character
			dfa=-1;
		}
	}
	
	private void state_2(char temp){ // Represents State 2
		if(temp=='a'){
			dfa=2;
		}
		else if(temp=='b'){
			dfa=3;
		}
		else{ // On encountering an invalid character
			dfa=-1;
		}
	}
	
	private void state_3(char temp){ // Represents State 3
		if(temp=='a'){
			dfa=3;
		}
		else if(temp=='b'){
			dfa=3;
		}
		else{ // On encountering an invalid character
			dfa=-1;
		}
	}
	
	private void state_4(char temp){ // Represents State 4
		if(temp=='a'){
			dfa=4;
		}
		else if(temp=='b'){
			dfa=4;
		}
		else{ // On encountering an invalid character
			dfa=-1;
		}
	}
	
	private static void check_token(String temp){ // Checks whether the token is valid or not
		Token_Recognizer ob=new Token_Recognizer(); // Creating object
		int flag=0;
		for(int i=0;i<temp.length();i++){ // Iterating through all the symbols of the token
			if(ob.dfa==1){
				ob.state_1(temp.charAt(i));
			}
			else if(ob.dfa==2){
				ob.state_2(temp.charAt(i));
			}
			else if(ob.dfa==3){
				ob.state_3(temp.charAt(i));
			}
			else if(ob.dfa==4){
				ob.state_4(temp.charAt(i));
			}
			else{ // If invalid symbol is encountered
				flag=1;
				break;
			}
		}
		if(flag==0){
			if(ob.dfa==3){
				System.out.println("Valid Token!");
			}
			else{
				System.out.println("Invalid Token!");
			}
		}
		else{
			System.out.println("Invalid Symbol Encountered! Execution Terminated!");
		}
	}
	public static void main(String args[]) throws IOException { // Declaring the Main Method
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n ***** Token Recognizer ***** \n");
		System.out.println(" Enter The Token (String) : ");
		String token=br.readLine();
		System.out.println();
		check_token(token);
	}
}

