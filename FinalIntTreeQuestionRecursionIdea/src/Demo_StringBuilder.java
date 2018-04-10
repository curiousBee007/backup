
public class Demo_StringBuilder {
	public static void main(String args[]){  
		StringBuilder sb=new StringBuilder("Hello ");  
		sb.append("Java");//now original string is changed  
		System.out.println(sb);//prints Hello Java  
		
		StringBuilder sb1=new StringBuilder("Hello ");  
		sb1.insert(1,"Java");//now original string is changed  
		System.out.println(sb1);//prints HJavaello  
	
		StringBuilder sb2=new StringBuilder("Hello");  
		sb2.replace(1,3,"Java");  
		System.out.println(sb2);//prints HJavalo
		
		StringBuilder sb3=new StringBuilder("Hello");  
		sb3.delete(3,4);  
		System.out.println(sb3);//prints Hlo  
		
		StringBuilder sb4=new StringBuilder("Hello");  
		sb4.reverse();  
		System.out.println(sb4);//prints olleH  
		
		StringBuilder sb5=new StringBuilder("Hello");  
		//String sb6 = sb5.substring(0,4);  
		 sb5 = new StringBuilder(sb5.substring(0,4));
		System.out.println(sb5);//prints Hlo  
	}  
}
