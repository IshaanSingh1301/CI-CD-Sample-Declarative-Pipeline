package meet;
import java.util.*;
public class Factorial {
public static void main(String[] args) {
int num;
//try{   
Scanner scan =new Scanner(System.in);
System.out.println("Enter a value");
num=scan.nextInt();
System.out.println(calculate(num));
//}
//catch(StackOverflowError e){
//System.out.println("invalid entry");
//}
}
public static int calculate(int num){
do{
return  calculate(num-1)*num;
}while(num>0);
}
}