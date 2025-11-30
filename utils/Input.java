package utils;

import java.time.LocalDate;
import java.util.Scanner;

public class Input {
    private static Scanner scn=new Scanner(System.in);
    private static final String CANCELSTR="*";
   
    public static String getStr(String msg) throws CancelException {
        System.out.print(msg+" ("+CANCELSTR+" para cancelar): ");
        String input=scn.nextLine();
        if (input.equals(CANCELSTR)) throw new CancelException();
        return input;
    }
    
    public static String validString(String msg,Validator validator) throws CancelException {
        boolean ok=false;
        String str;
        
        do {
            str=getStr(msg);
            if (!validator.isValid(str)) System.out.println("\t"+validator.message());
            else                         ok=true;
        } while(!ok);
        return str;
    }
    
    public static String getDni(String msg) throws CancelException {
        return validString(msg,new DniValidator());
    }
    
    public static LocalDate getDate(String msg) throws CancelException {
        DateValidator validator=new DateValidator();
        validString(msg,validator);
        return validator.getDate();
    }
    
    public static int getInt(String msg,int min, int max) throws CancelException {
        boolean ok=false;
        int num=0;
        
        do {
            try {
                String strnum=getStr(msg);
                num=Integer.parseInt(strnum);
                if ((num<min) || (num>max)) {
                    System.out.println("\t--> El valor debe estar entre "+min+" y "+max+" (incluidos)");
                } else {
                    ok=true;
                }
            } catch(NumberFormatException e) {
                System.out.println("\t--> Debes introducir un valor numérico entero");
            }
        } while(!ok);
        return num;
    }
    
    public static boolean question(String msg) throws CancelException {
        String str=validString(msg,new YesNoValidator());
        return str.equals("s") || str.equals("S") || str.equals("y") || str.equals("Y");
    }

    public static void pause(String msg) {
        System.out.print(msg);
        scn.nextLine();
    }

    public static Integer chooser(String msg, Object[] objects) throws CancelException {
        Integer num=null;
        System.out.println(msg);
        for(int idx=0;idx<objects.length;idx++) System.out.println((idx+1)+".- "+objects[idx]);
        num=Input.getInt("Opción (0 para cancelar)",0,objects.length);
        if (num==0) return null;
        return num-1;
    }
}
