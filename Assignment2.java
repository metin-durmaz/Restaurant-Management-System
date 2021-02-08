import java.util.*;
import java.io.*;
public class Assignment2{
    public static void main(String[] args) {
        int tableId=0;
        ArrayList<Item> item=new ArrayList<Item>();
        ArrayList<Employer> employer=new ArrayList<Employer>();
        ArrayList<Waiter> waiter=new ArrayList<Waiter>();
        ArrayList<Table> table=new ArrayList<Table>();
        ArrayList<Order> order =new ArrayList<Order>();
        try(Scanner setupData=new Scanner(new FileReader("setup.dat"))){
            while(setupData.hasNextLine()){
                String setupLine=setupData.nextLine();
                String setupCommand=setupLine.substring(0,setupLine.indexOf(" "));
                String setupInfos=setupLine.substring(setupLine.indexOf(" ")+1);
                String[] infos=setupInfos.split(";");
                if(setupCommand.equals("add_item")){
                    Item addItem=new Item(infos[0],Double.parseDouble(infos[1]),Integer.parseInt(infos[2]));
                    item.add(addItem);
                }
                else if(setupCommand.equals("add_employer")){
                    if(employer.size()<5){
                        Employer addEmployer=new Employer(infos[0],Integer.parseInt(infos[1]));
                        employer.add(addEmployer);
                    }
                    else
                        System.out.println("Not allowed to exceed max.number of employers,MAX_EMPLOYERS");
                }
                else if(setupCommand.equals("add_waiter")){
                    if(waiter.size()<5){
                        Waiter addWaiter=new Waiter(infos[0],Integer.parseInt(infos[1]));
                        waiter.add(addWaiter);
                    }
                    else
                        System.out.println("Not allowed to exceed max.number of waiters,MAX_WAITERS");   
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found.");
        }
        try(Scanner commandsData=new Scanner(new FileReader("commands.dat"))){
            while(commandsData.hasNextLine()){
                String commandsLine=commandsData.nextLine();
                String firstCommand;
                if(commandsLine.contains(" ")){
                    firstCommand=commandsLine.substring(0, commandsLine.indexOf(" "));
                    String commandsInfos=commandsLine.substring(commandsLine.indexOf(" ")+1);
                    String[] infos=commandsInfos.split(";");
                    if(firstCommand.equals("create_table")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: create_table");
                        int flag=0;
                        int indexEmployer=0;
                        for(Employer i:employer){
                            if(i.getName().equals(infos[0])){
                                flag=1;
                                indexEmployer=employer.indexOf(i);
                                break;
                            }
                        }
                        if(flag==1){
                            if(employer.get(indexEmployer).getAllowedMaxTables()<2){
                                if(table.size()<5){
                                    Table createTable=new Table(tableId,infos[0],Integer.parseInt(infos[1]));
                                    table.add(createTable);
                                    employer.get(indexEmployer).setAllowedMaxTables();
                                    tableId++;
                                    System.out.println("A new table has successfully been added");
                                }
                                else
                                    System.out.println("Not allowed to exceed max. number of tables, MAX_TABLES");
                            }
                            else
                                System.out.println(infos[0]+" has already created ALLOWED_MAX_TABLES tables!");
                        }
                        else
                            System.out.println("There is no employer named "+infos[0]);
                    }
                    else if(firstCommand.equals("new_order")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: new_order");
                        int flag=0;
                        int indexWaiter=0;
                        int flag1=0;
                        int indexTable=0;
                        for(Waiter i:waiter){
                            if(i.getName().equals(infos[0])){
                                flag=1;
                                indexWaiter=waiter.indexOf(i);
                                break;
                            }
                        }
                        for(Table i:table){
                            if(i.getState()!=1 && i.getCapacity()>=Integer.parseInt(infos[1])){
                                flag1=1;
                                indexTable=table.indexOf(i);
                                break;
                            }
                        }
                        if(flag==1 && flag1==1){
                            if(waiter.get(indexWaiter).getMaxTableServices()<3){
                                table.get(indexTable).setState(1);
                                table.get(indexTable).setMaxOrders();
                                table.get(indexTable).setWaiterName(infos[0]);
                                waiter.get(indexWaiter).setMaxTableServices();
                                waiter.get(indexWaiter).setSettedTables();
                                Order newOrder=new Order();
                                System.out.printf("Table (= ID %d) has been taken into service\n",table.get(indexTable).getID());
                                newOrder.setID(table.get(indexTable).getID());
                                if(infos[2].contains(":")){
                                    String[] itemList=infos[2].split(":");
                                    for(int i=0;i<itemList.length;i++){
                                        String itemName=itemList[i].substring(0,itemList[i].indexOf("-"));
                                        int itemAmount=Integer.parseInt(itemList[i].substring(itemList[i].indexOf("-")+1));
                                        int indexItem=0;
                                        int flag3=0;
                                        for(Item k:item){
                                            if(k.getName().equals(itemName)){
                                                flag3=1;  
                                                indexItem=item.indexOf(k);
                                            }        
                                        }
                                        for(int j=0;j<itemAmount;j++){
                                            if(flag3==1 && item.get(indexItem).getAmount()>0 && newOrder.getItems().size()<10){
                                                item.get(indexItem).setAmount();
                                                System.out.println("Item "+itemName+" added into order");
                                                Item orderedItem=new Item(itemName,item.get(indexItem).getCost(),1);
                                                orderedItem.setID(table.get(indexTable).getID());
                                                newOrder.getItems().add(orderedItem);
                                                newOrder.setMaxItems();
                                            }
                                            else if(flag3==1 && item.get(indexItem).getAmount()==0 && newOrder.getItems().size()<10){
                                                System.out.printf("Sorry! No %s in the stock!\n",itemName);
                                            }
                                            else if(flag3==1 && item.get(indexItem).getAmount()>0 && newOrder.getItems().size()==10){
                                                System.out.println("Not allowed to exceed max. number of items, MAX_ITEMS");
                                            }
                                            else if(flag3==0){
                                                System.out.println("Unknown item "+itemName);
                                            }
                                        }
                                    }
                                    order.add(newOrder);
                                }
                                else{
                                    String itemName=infos[2].substring(0,infos[2].indexOf("-"));
                                    int itemAmount=Integer.parseInt(infos[2].substring(infos[2].indexOf("-")+1));
                                    int indexItem=0;
                                    int flag3=0;
                                    for(Item k:item){
                                        if(k.getName().equals(itemName)){
                                            flag3=1;  
                                            indexItem=item.indexOf(k);
                                        }        
                                    }
                                    for(int j=0;j<itemAmount;j++){
                                        if(flag3==1 && item.get(indexItem).getAmount()>0 && newOrder.getItems().size()<10){
                                            item.get(indexItem).setAmount();
                                            System.out.println("Item "+itemName+" added into order");
                                            Item orderedItem=new Item(itemName,item.get(indexItem).getCost(),1);
                                            orderedItem.setID(table.get(indexTable).getID());
                                            newOrder.getItems().add(orderedItem);
                                            newOrder.setMaxItems();
                                        }
                                        else if(flag3==1 && item.get(indexItem).getAmount()==0 && newOrder.getItems().size()<10){
                                            System.out.printf("Sorry! No %s in the stock!\n",itemName);
                                        }
                                        else if(flag3==1 && item.get(indexItem).getAmount()>0 && newOrder.getItems().size()==10){
                                            System.out.println("Not allowed to exceed max. number of items, MAX_ITEMS");
                                        }
                                        else if(flag3==0){
                                            System.out.println("Unknown item "+itemName);
                                        } 
                                    }
                                    order.add(newOrder);
                                }
                            }
                            else
                                System.out.println("Not allowed to service max. number of tables, MAX_TABLE_SERVICES");
                        }
                        else if(flag==0){
                            System.out.println("There is no waiter named "+infos[0]);
                        }
                        else if(flag1==0){
                            System.out.println("There is no appropriate table for this order!");
                        }
                    }
                    else if(firstCommand.equals("add_order")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: add_order");
                        int flag=0;
                        int indexWaiter=0;
                        int flag1=0;
                        int indexTable=0;
                        for(Waiter i:waiter){
                            if(i.getName().equals(infos[0])){
                                flag=1;
                                indexWaiter=waiter.indexOf(i);
                                break;
                            }
                        }
                        for(Table i:table){
                            if(i.getID()==Integer.parseInt(infos[1]) && i.getState()==1 && i.getWaiterName().equals(infos[0])){
                                flag1=1;
                                indexTable=table.indexOf(i);
                                break;
                            }
                        }
                        if(flag==1 && flag1==1){
                            waiter.get(indexWaiter).setSettedTables();
                            if(table.get(indexTable).getMaxOrders()<5){
                                table.get(indexTable).setMaxOrders();
                                Order addOrder=new Order();
                                addOrder.setID(table.get(indexTable).getID());
                                if(infos[2].contains(":")){
                                    String[] itemList=infos[2].split(":");
                                    for(int i=0;i<itemList.length;i++){
                                        String itemName=itemList[i].substring(0,itemList[i].indexOf("-"));
                                        int itemAmount=Integer.parseInt(itemList[i].substring(itemList[i].indexOf("-")+1));
                                        int indexItem=0;
                                        int flag3=0;
                                        for(Item k:item){
                                            if(k.getName().equals(itemName)){
                                                flag3=1;  
                                                indexItem=item.indexOf(k);
                                            }        
                                        }
                                        for(int j=0;j<itemAmount;j++){
                                            if(flag3==1 && item.get(indexItem).getAmount()>0 && addOrder.getItems().size()<10){
                                                item.get(indexItem).setAmount();
                                                System.out.println("Item "+itemName+" added into order");
                                                Item orderedItem=new Item(itemName,item.get(indexItem).getCost(),1);
                                                orderedItem.setID(table.get(indexTable).getID());
                                                addOrder.getItems().add(orderedItem);
                                                addOrder.setMaxItems();
                                            }
                                            else if(flag3==1 && item.get(indexItem).getAmount()==0 && addOrder.getItems().size()<10){
                                                System.out.printf("Sorry! No %s in the stock!\n",itemName);
                                            }
                                            else if(flag3==1 && item.get(indexItem).getAmount()>0 && addOrder.getItems().size()==10){
                                                System.out.println("Not allowed to exceed max. number of items, MAX_ITEMS");
                                            }
                                            else if(flag3==0){
                                                System.out.println("Unknown item "+itemName);
                                            } 
                                        }
                                    }
                                    order.add(addOrder);
                                }
                                else{
                                    String itemName=infos[2].substring(0,infos[2].indexOf("-"));
                                    int itemAmount=Integer.parseInt(infos[2].substring(infos[2].indexOf("-")+1));
                                    int indexItem=0;
                                    int flag3=0;
                                    for(Item k:item){
                                        if(k.getName().equals(itemName)){
                                            flag3=1;  
                                            indexItem=item.indexOf(k);
                                        }        
                                    }
                                    for(int j=0;j<itemAmount;j++){
                                        if(flag3==1 && item.get(indexItem).getAmount()>0 && addOrder.getItems().size()<10){
                                            item.get(indexItem).setAmount();
                                            System.out.println("Item "+itemName+" added into order");
                                            Item orderedItem=new Item(itemName,item.get(indexItem).getCost(),1);
                                            orderedItem.setID(table.get(indexTable).getID());
                                            addOrder.getItems().add(orderedItem);
                                            addOrder.setMaxItems();
                                        }
                                        else if(flag3==1 && item.get(indexItem).getAmount()==0 && addOrder.getItems().size()<10){
                                            System.out.printf("Sorry! No %s in the stock!\n",itemName);
                                        }
                                        else if(flag3==1 && item.get(indexItem).getAmount()>0 && addOrder.getItems().size()==10){
                                            System.out.println("Not allowed to exceed max. number of items, MAX_ITEMS");
                                        }
                                        else if(flag3==0){
                                            System.out.println("Unknown item "+itemName);
                                        } 
                                    }
                                    order.add(addOrder);
                                }
                            }
                            else
                                System.out.println("Not allowed to exceed max. number of orders, MAX_ORDERS");    
                            
                        }
                        else if(flag==0){
                            System.out.println("There is no waiter named "+infos[0]);
                        }
                        else if(flag1==0){
                            System.out.printf("This table is either not in service now or %s cannot be assigned this table!\n",infos[0]);
                        }
                        
                    }
                    else if(firstCommand.equals("check_out")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: check_out");
                        int flag=0;
                        int indexWaiter=0;
                        int flag1=0;
                        int indexTable=0;
                        for(Waiter i:waiter){
                            if(i.getName().equals(infos[0])){
                                flag=1;
                                indexWaiter=waiter.indexOf(i);
                                break;
                            }
                        }
                        for(Table i:table){
                            if(i.getID()==Integer.parseInt(infos[1]) && i.getState()==1 && i.getWaiterName().equals(infos[0])){
                                flag1=1;
                                indexTable=table.indexOf(i);
                                break;
                            }
                        }
                        if(flag==1 && flag1==1){
                            ArrayList<Order> checked = new ArrayList<Order>();
                            for(Order i:order){
                                if(i.getID()==Integer.parseInt(infos[1])){
                                    checked.add(i);
                                }
                            }
                            ArrayList<Item> checkedItem=new ArrayList<Item>();
                            for(Order i:checked){
                                for(int j=0;j<i.getItems().size();j++){
                                    checkedItem.add(i.getItems().get(j));
                                }
                            }
                            ArrayList<String> namee=new ArrayList<String>();
                            ArrayList<Double> costt=new ArrayList<Double>();
                            ArrayList<Integer> adett=new ArrayList<Integer>();
                            namee.add(checkedItem.get(0).getName());
                            costt.add(checkedItem.get(0).getCost());
                            adett.add(checkedItem.get(0).getAdet());
                            for(Item i:checkedItem){
                                String name1=i.getName();
                                if(namee.contains(name1)){
                                    int indexx=namee.indexOf(name1);
                                    int newAdet=adett.get(indexx)+1;
                                    adett.remove(indexx);
                                    adett.add(indexx, newAdet);

                                }
                                else{
                                    namee.add(i.getName());
                                    costt.add(i.getCost());
                                    adett.add(i.getAdet());
                                }
                                    
                            }
                            int tempAdet=adett.get(0)-1;
                            adett.remove(0);
                            adett.add(0,tempAdet);
                            double total=0;
                            for(int i=0;i<namee.size();i++){
                                System.out.printf("%s:\t%.3f (x %d) %.3f $\n",namee.get(i),costt.get(i),adett.get(i),(costt.get(i)*adett.get(i)));
                                total+=costt.get(i)*adett.get(i);
                            }
                            System.out.printf("Total:\t%.3f $\n",total);
                            waiter.get(indexWaiter).setMaxTableServices2();
                            table.get(indexTable).setState(0);
                            table.get(indexTable).setMaxOrders(0);
                            for(Order i:order){
                                if(table.get(indexTable).getID()==i.getID()){
                                    i.setID(-1);
                                }
                            }
                        }
                        else if(flag==0){
                            System.out.println("There is no waiter named "+infos[0]);
                        }
                        else if(flag1==0){
                            System.out.printf("This table is either not in service now or %s cannot be assigned this table!\n",infos[0]);
                        }
                    }
                }
                else{
                    firstCommand=commandsLine;
                    if(firstCommand.equals("stock_status")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: stock_status");
                        for(Item i:item){
                            System.out.println(i.getName()+":\t"+i.getAmount());
                        }
                    }
                    else if(firstCommand.equals("get_table_status")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: get_table_status");
                        for(Table i:table){
                            if(i.getState()==0)
                                System.out.println("Table "+i.getID()+": Free");
                            else if(i.getState()==1)
                            System.out.println("Table "+i.getID()+": Reserved ("+i.getWaiterName()+")");
                        }
                    }
                    else if(firstCommand.equals("get_order_status")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: get_order_status");
                        for(Table i:table){
                            System.out.println("Table: "+i.getID());
                            
                            System.out.println("\t"+i.getMaxOrders()+" order(s)");
                            
                            for(Order j:order){
                                if(j.getID()==i.getID()){
                                    System.out.println("\t\t"+j.getMaxItems()+" item(s)");
                                }
                            }
                        }
                    }
                    else if(firstCommand.equals("get_employer_salary")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: get_employer_salary");
                        for(Employer i:employer){
                            double salary=i.getSalary()+(i.getAllowedMaxTables()*i.getSalary()*(0.1));
                            System.out.printf("Salary for %s: %.1f\n",i.getName(),salary);       
                        }
                    }
                    else if(firstCommand.equals("get_waiter_salary")){
                        System.out.println("***********************************");
                        System.out.println("PROGRESSING COMMAND: get_waiter_salary");
                        for(Waiter i:waiter){
                            double salary=i.getSalary()+(i.getSettedTables()*i.getSalary()*(0.05));
                            System.out.printf("Salary for %s: %.1f\n",i.getName(),salary);
                        }
                    }
                }  
            }  
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
    }
}