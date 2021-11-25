import java.io.*;
import java.util.*;

class Date {
    ArrayList<Integer> available=new ArrayList<Integer>();
    ArrayList<Integer> occupied=new ArrayList<Integer>();
     Date(int[] a,int s){
        for(int i=0;i<s;i++){
            available.add(a[i]);
        }

    }
}

class Property{
    String ManagerName;
    String name;
    String address;
    float price;
    Date date;
     
}

class Users{
    int typeOfUser;
    String name;
    String password;
    float balance;
    Users(String n,String p,int t,float b){
        name=n;
        password=p;
        balance=b;
        typeOfUser=t;
    }
    public String getUsername(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public int userType(){
        return typeOfUser;
    }
    public float getBalance(){
        return balance;
    }
    public void showBalance(){
        try{
        File f=new File("UserDetails/users.txt");
        Scanner s=new Scanner(f);
        while(s.hasNextLine()){
            String Line=s .nextLine();
            String[] items=Line.split("\\|");
            if(items[0].equals(getUsername())){
                System.out.println("Current balance is \n");
                System.out.println(items[3]);
                System.out.println("\n");
            }
               }
            }
            catch(Exception e){

            }
        System.out.println("Redirecting back to main menu");
        chooseMethod();
      }
    public void propertyBook(){
        System.out.println("Booking a property");
        System.out.println("Enter inital date");
        Scanner s=new Scanner(System.in);
        int initdate=s.nextInt();
        System.out.println("Enter no of Nights");
        int noOfNights=s.nextInt();
        System.out.println("FInding properties that match your description");
        try{
            File file=new File("PropertyDetails/prop.txt");
            Scanner sc1=new Scanner(file);
            int flag2=0;
            int count=1;
            ArrayList<String> lop = new ArrayList<String>();
            ArrayList<String> lom = new ArrayList<String>();
            ArrayList<String> lor = new ArrayList<String>();
            while(sc1.hasNextLine()){
                
                String line=sc1.nextLine();
                String[] items=line.split("\\|");
                if(!items[4].equals("empty")){
                String[] dates=items[4].split(",");
                int[] lod= new int[dates.length];
                for(int i=0;i<dates.length;i++){
                    lod[i]=Integer.parseInt(dates[i]);
                }
                int flag=0;
                    // System.out.println(lod[1]);
                    for(int i=0;i<noOfNights;i++){
                     for(int j=0;j<lod.length;j++){
                         if(lod[j]==(initdate+i))flag+=1;
                     }
                    }
               
                    if(flag==noOfNights){
                        System.out.println("Property no " + count);
                        System.out.println("Name: "+items[1]+ ". Address: " + items [2]+". Price/night: " +items[3]);
                        System.out.println("\n");
                        flag2=1;
                        count++;
                        lop.add(items[1]);
                        lom.add(items[0]);
                        lor.add(items[3]);
                    }
                }
            }
            if(flag2==0){
                System.out.println("No properties matching your dates found , Try again");
                chooseMethod();
            }
            else{

                System.out.println("Enter 0 to search again or enter property no to select the property");
                int chosen=s.nextInt();
                if(chosen==0){
                  propertyBook();
                }
                else{
                // System.out.println("Enter the property no that you want");
            
               
                System.out.println("CHecking if you have enough balance");
                handleTransaction(lop.get(chosen-1),noOfNights,initdate,lom.get(chosen-1),lor.get(chosen-1));
                }
            }
        }
        catch(Exception e){

        }
        
    }
    public void propertyCancel(){
        try{
            ArrayList<String> lop = new ArrayList<String>();
            ArrayList<String> lom = new ArrayList<String>();
            ArrayList<String> lor = new ArrayList<String>();
            ArrayList<String> loNon = new ArrayList<String>();
            ArrayList<String> los = new ArrayList<String>();
        System.out.println("Cancel Property Page \n");
        File file= new File("BookedProps/booked.txt");
        Scanner s = new Scanner(file);
        int flag=0,count=1;
        while(s.hasNextLine()){
            String line=s.nextLine();
            String[] items=line.split("\\|");
            if(items[0].equals(getUsername())){
                flag=1;
                System.out.println("Property no: "+count);
                System.out.println("Property Name: "+items[1]+". No of Nights: "+items[3]+". Starting date: "+items[4]+" Rate/night:" + items[5]);
                count++;
                lop.add(items[1]);
                lom.add(items[2]);
                lor.add(items[5]);
                los.add(items[4]);
                loNon.add(items[3]);
            }
        }
        if(flag==0){
            System.out.println("You have booked No properties , Redirecting to main menu");
            chooseMethod();
        }
        else{
            System.out.println("Enter the property no you wanna cancel");
            Scanner sc= new Scanner(System.in);
            int chosen=sc.nextInt();
            handleCancel(lop.get(chosen-1),Integer.parseInt(loNon.get(chosen-1)),Integer.parseInt(los.get(chosen-1)),lom.get(chosen-1),lor.get(chosen-1));
        }
    }
    catch(Exception e){
        System.out.println("No properties have been booked");
    }
    }
    public void handleCancel(String propName,int non,int initD,String manName,String rate){
                      ////Change the balance of manager and customer
                      try{
                        File file=new File("UserDetails/users.txt");
                        Scanner sc=new Scanner(file);    
                        ArrayList<String> AllLines = new ArrayList<String>(); 
                        // System.out.println("Rate "+rate);
                        Float cost= Float.parseFloat(rate)*non;
                        Float cancellationFee=200.0f * non;
                        // System.out.println("COst "+cost);
                        while(sc.hasNextLine()){
                            String line=sc.nextLine();
                            String[] items=line.split("\\|");
                            
                            //  int diff=cost-cancellationFee;
                            // System.out.println(manName);
                            if(manName.equals(items[0])){
                                items[3]=Float.toString(Float.parseFloat(items[3])-cost+cancellationFee);

                                // System.out.println(items[3]);
                            }
                            // System.out.println(items[3]);
                            if(items[0].equals(getUsername())){
                                items[3]=Float.toString(Float.parseFloat(items[3])+cost-cancellationFee);
                                // System.out.println(items[3]);
                            }
                           
                            line=items[0]+"|"+items[1]+"|"+items[2]+"|"+items[3]+"\n";
                            // System.out.println(line);
                            AllLines.add(line);
                        }
        
                        sc.close();
        
                        FileWriter fos = new FileWriter("UserDetails/users.txt");
                        for(int i=0;i<AllLines.size();i++){
                         fos.write(AllLines.get(i));
                        }
                            fos.close();
                      System.out.println("Property Successfully Cancelled");
                      System.out.println("Cancellation fee of "+cancellationFee+" Charged");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    //////
                    

                    ////Delete it from Booked.txt file
                    try{
                        ArrayList<String> AllLines = new ArrayList<String>();
                        File file=new File("BookedProps/booked.txt");
                        Scanner sc=new Scanner(file); 
                        while(sc.hasNextLine()){
                            String line=sc.nextLine();
                            String[] items=line.split("\\|");
                            if(!(items[0].equals(getUsername())&&items[1].equals(propName)&&(Integer.parseInt(items[4])==initD))){
                                AllLines.add(line);
                            }
                        }
                        sc.close();
                        FileWriter fos = new FileWriter("BookedProps/booked.txt");
                        for(int i=0;i<AllLines.size();i++){
                         fos.write(AllLines.get(i)+"\n");
                        }
                            fos.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


                   
                  
                    ////
                    

                    ///Change the dates in prop.txt
 

                    try{
             File file=new File("PropertyDetails/prop.txt");
             Scanner sc=new Scanner(file);
             ArrayList<String> AllLines = new ArrayList<String>(); 
             while(sc.hasNextLine()){
                String line=sc.nextLine();
                String[] items=line.split("\\|");
                
                if(items[1].equals(propName)){
                    
                    if(items[4].equals("empty")){
                        items[4]="";
                    }
                     for(int i=0;i<non;i++){
                         items[4]+=initD+i+",";
                     }
                    //  System.out.println(items[4]);
                     String[] dates=items[5].split(",");
                     int[] lod= new int[dates.length];
                     for(int i=0;i<dates.length;i++){
                        lod[i]=Integer.parseInt(dates[i]);
                    }
                     int[] nlod= new int[dates.length-non];
                     int count=0;
                     for(int i=0;i<lod.length;i++){
                        int flag=0;
                        for(int j=0;j<non;j++){
                            if(lod[i]==initD+j){
                                flag=1;
                            }
                        }
                        if(flag==0){
                            nlod[count]=lod[i];
                            count++;
                        }
                     }
                     items[5]="";
                     for(int i=0;i<nlod.length;i++){
                         // System.out.println(nlod[i]);
                         items[5]+=nlod[i]+",";
                     }
                     if(items[5].equals("")){
                         items[5]="empty";
                 }
                }
                line=items[0]+"|"+items[1]+"|"+items[2]+"|"+items[3]+"|"+items[4]+"|"+items[5]+"\n";
                AllLines.add(line);
             }
             sc.close();
             FileWriter fos = new FileWriter("PropertyDetails/prop.txt");
             for(int i=0;i<AllLines.size();i++){
              fos.write(AllLines.get(i));
            }
            fos.close();
        }
               catch (Exception e) {
                        e.printStackTrace();
                    }
  

                    System.out.println("\n \n     Redirecting to main menu");
                    chooseMethod();
                    /////
    }
    public void handleTransaction(String propName,int non,int initD,String manName,String rate){
                
        System.out.println("Chosen Property Name " + propName);
        System.out.println("Current balance is " + getBalance());
        System.out.println("Rate per night and no of nights is " + rate + " & " + non + ". Total cost is " + Float.parseFloat(rate)*non);
         Float cost = Float.parseFloat(rate)*non;
        if(Float.parseFloat(rate)*non<=getBalance()){
            System.out.println("Balance Sufficient , Proceeding with Transaction");


            ////Keep a list of all properties customer has booked
    
            try{
                FileWriter fos = new FileWriter("BookedProps/booked.txt",true);
                 fos.write(getUsername()+"|"+propName+"|"+manName+"|"+non+"|"+initD+"|"+rate+"\n");
                 fos.close();
                }
                catch (Exception e) {
                   e.printStackTrace();
               }

            ////

            ////Change the balance of manager and customer
            try{
                File file=new File("UserDetails/users.txt");
                Scanner sc=new Scanner(file);    
                ArrayList<String> AllLines = new ArrayList<String>(); 

                while(sc.hasNextLine()){
                    String line=sc.nextLine();
                    String[] items=line.split("\\|");


                    if(manName.equals(items[0])){
                        items[3]=Float.toString(Float.parseFloat(items[3])+cost);
                        // System.out.println(items[3]);
                    }
                    if(items[0].equals(getUsername())){
                        items[3]=Float.toString(Float.parseFloat(items[3])-cost);

                    }
                    line=items[0]+"|"+items[1]+"|"+items[2]+"|"+items[3]+"\n";
                    AllLines.add(line);
                }

                sc.close();

                FileWriter fos = new FileWriter("UserDetails/users.txt");
                for(int i=0;i<AllLines.size();i++){
                 fos.write(AllLines.get(i));
                }
                    fos.close();
              
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //////

            ////Change the available dates in properties class
            try{
                File file=new File("PropertyDetails/prop.txt");
                Scanner sc1=new Scanner(file);
                ArrayList<String> PropertyList=new ArrayList<String>();
                while(sc1.hasNextLine()){
                    String line=sc1.nextLine();
                    String[] items=line.split("\\|");
                    String p=new String();

                    if(items[1].equals(propName)){
                        if(items[5].equals("empty")){
                            items[5]="";
                        }
                      String[] dates=items[4].split(",");
                        int[] lod= new int[dates.length];  
                        for(int i=0;i<dates.length;i++){
                            lod[i]=Integer.parseInt(dates[i]);
                        }
                        for(int i=0;i<non;i++){
                           
                            items[5]+=initD+i+",";
                       
                            
                        }
                        int[] nlod= new int[dates.length-non];
                        int count=0;
                        for(int i=0;i<lod.length;i++){
                           int flag=0;
                           for(int j=0;j<non;j++){
                               if(lod[i]==initD+j){
                                   flag=1;
                               }
                           }
                           if(flag==0){
                               nlod[count]=lod[i];
                               count++;
                           }
                        }
                        items[4]="";
                        for(int i=0;i<nlod.length;i++){
                            // System.out.println(nlod[i]);
                            items[4]+=nlod[i]+",";
                        }
                        if(items[4].equals("")){
                            items[4]="empty";
                    }
                      }
                
                    line=items[0]+"|"+items[1]+"|"+items[2]+"|"+items[3]+"|"+items[4]+"|"+items[5]+"\n";
            
                   
                    PropertyList.add(line);
                }
                sc1.close();
                FileWriter fos = new FileWriter("PropertyDetails/prop.txt");
                for(int i=0;i<PropertyList.size();i++){
                 fos.write(PropertyList.get(i));
                }
                    fos.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            /////


            System.out.println("\n\n Transaction Succesful , Returning to main menu");
            chooseMethod();
        }
        else{
            System.out.println("BALANCE INSUFFICIENT , RETURNING TO MAIN MENU ");
            chooseMethod();
        }

        //
    }
    public void chooseMethod(){
        System.out.println("Type 1 to book property, 2 to cancel a older booking , 3 to show your current balance , 4 to logout");
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        if(n==1){
            propertyBook();
        }
        else if(n==2){
            propertyCancel();
        }
        else if(n==3){
            showBalance();
        }
        else if(n==4){
            System.out.println("Thank you for using our app. Program ending now....");
            System.exit(0);
        }
    }
}
class Manager extends Users{
    //declare array how?
    Property listOfProperties = new Property();

  Manager(String n,String p,int t,float b){
      super(n,p,t,b);

  }




    public void propertyAdd(){
        System.out.println("Adding a property");
       
        ArrayList<String> propNames=new ArrayList<String>();
        try{
        File f=new File("PropertyDetails/prop.txt");
        Scanner scan=new Scanner(f);
        while(scan.hasNextLine()){
            String line=scan.nextLine();
            String[] items=line.split("\\|");
            propNames.add(items[1]);

        }
    }
    catch(Exception e){

    }
        int flag=0;
        
        System.out.println("Enter name of property");
        String pname=new String();
        Scanner s = new Scanner(System.in);
        while(flag==0){
        
        pname=s.nextLine();
        if(propNames.contains(pname)){
            flag=0;System.out.println("Name already Exists enter a new one");
        }
        else{
            flag=1;
        }
        }
        System.out.println("Enter address of property");
        String paddress=s.nextLine();
        System.out.println("Enter price per night");
        Float pnight=s.nextFloat();
        String BookedDates="empty";
        System.out.println("Enter total no of nights that the property is available for this month");
        int psize=s.nextInt();
        System.out.println("Enter the dates one by one");
        int[] arr= new int[psize];
        String ListOfDates=new String();
        for(int i=0;i<psize;i++){
            arr[i]=s.nextInt();
            ListOfDates+=arr[i]+",";
        }
        Date d = new Date(arr,psize);

        try{
            FileWriter fos = new FileWriter("PropertyDetails/prop.txt",true);
             fos.write(getUsername()+"|"+pname+"|"+paddress+"|"+pnight+"|"+ListOfDates+"|"+BookedDates+"\n");
             fos.close();
             System.out.println("Property Succesfully added");
             System.out.println("Redirecting to main menu");
             chooseMethod();
            }
            catch (Exception e) {
               e.printStackTrace();
           }
        
    }
    public void chooseMethod(){
        System.out.println("Type 1 to book property, 2 to cancel an older property ,3 to add property, 4 to see your current balance, 5 to logout");
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        if(n==1){
            propertyBook();
        }
        else if(n==2){
            propertyCancel();
        }
        else if(n==3){
            propertyAdd();
        }
        else if(n==4){
            showBalance();
        }
        else if(n==5){
            System.out.println("Thank you for using our app. Program ending now....");
            System.exit(0);
        }
    }

}


public class Login {
    String username = new String();
    String password = new String();
    int typeOfSignUp;
    int typeOfUser;
    HashSet<Users> listOfUsers = new HashSet<Users>();
    int LoginCount=0;
    public void loginHandler(){
       
        System.out.println("Login Page");
        try{
        File file=new File("UserDetails/users.txt");
        Scanner sc=new Scanner(file);     
        Scanner sc2=new Scanner(System.in);
        System.out.println("Enter username");
        String name=sc2.nextLine();
        int flag=0;
        while(sc.hasNextLine()){
            String line=sc.nextLine();
            String[] items=line.split("\\|");
            Users u= new Users(items[0],items[1],Integer.parseInt(items[2]),Float.parseFloat(items[3]));

            if(name.equals(u.getUsername())){
                flag=1;
             
                System.out.println("enter password");
                String password=sc2.nextLine();
                if(password.equals(u.getPassword())){
                    System.out.println("Succesfully logged in , redirecting to respective main menu");
                    // System.out.println(u.userType());
                    
                if(u.userType()==0){
                        //initalize a customer class call the main menyu function
                        System.out.println("\n Welcome Customer \n");
                        u.chooseMethod();
                }
                else if(u.userType()==1){
                     //initialize a manager class and call the main menu function
                     System.out.println("\n Welcome Manager \n");
                     Manager m= new Manager(u.getUsername(),u.getPassword(),1,u.getBalance());
                     m.chooseMethod();
                }
                }
                else{
                    LoginCount++;
                    System.out.println("Username/Password incorrect redirecting back to main page");
                   
                    if(LoginCount==3){
                        System.out.println("Three unsucessful attempts , program terminating");
                        System.exit(0);
                    }
                    MainMenu();
                }
            }

        }
        if(flag==0){
            System.out.println("enter password");
            String password=sc2.nextLine();
            System.out.println("Username/password does not exist");
            System.out.println("Try again");
            LoginCount++;
            if(LoginCount==3){
                System.out.println("Three unsucessful attempts , program terminating");
                System.exit(0);
            }
            MainMenu();
        }
        }
        catch(Exception e){

        }
    }
    public void signUpHandler(){
        System.out.println("Sign up procedure ");
        System.out.println("Enter username");
        
        Scanner s = new Scanner(System.in);
       
        ArrayList<String> Usernames=new ArrayList<String>();
        try{
            File f = new File("UserDetails/users.txt");
            Scanner scan=new Scanner(f);
            while(scan.hasNextLine()){
                String line=scan.nextLine();
                String[] items=line.split("\\|");
                Usernames.add(items[0]);
            }
        }
        catch(Exception e){

        }
        int flag=0;
        String name=new String();
        while(flag==0){
      
        name=s.nextLine();
         if(Usernames.contains(name)){
             System.out.println("Username already exists , Enter another one");
         }
         else{
             flag=1;
         }
        }
        System.out.println("Enter password");
        String  password=s.nextLine();
        System.out.println("Enter type of user , 1 for manager and 0 for customer");
        int toc=s.nextInt();
        
        float bal=2000.0f;
         Users u1 = new Users(name,password,toc,bal);

 //WRITE TO A FILE HERE
 try{
 FileWriter fos = new FileWriter("UserDetails/users.txt",true);
  fos.write(u1.getUsername()+"|"+u1.getPassword()+"|"+u1.userType()+"|"+u1.getBalance()+"\n");
  fos.close();
 }
 catch (Exception e) {
    e.printStackTrace();
}


 System.out.println("User Succesfully signed Up , Redirecting to login page");
 loginHandler();
    }

    public void MainMenu(){
        System.out.println("Enter 0 for login, 1 for signup");
        Scanner in = new Scanner(System.in);
        int typeOfSignUp = in.nextInt();
        if(typeOfSignUp == 0){
            System.out.println("Login");
            loginHandler();
        }
        if(typeOfSignUp == 1){
            System.out.println("Signup");
            signUpHandler();
        }
    }
    public static void main(String[] args){
        Login l=new Login();
        l.MainMenu();
        
    }
}

