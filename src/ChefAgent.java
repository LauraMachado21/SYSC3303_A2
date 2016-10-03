import java.io.IOException;
import java.util.ArrayList;

public class ChefAgent
{	
	public static void main(String[] args)
    {
        Thread agent, breadChef, jamChef, pbChef;
        Table buffer;

        buffer = new Table(); 

        // Create the producer and consumer threads, passing each thread
        // a reference to the shared BoundedBuffer object.
        agent = new Thread(new Agent(buffer),"Agent");
        breadChef = new Thread(new Chef(buffer, Ingredient.BREAD),"Bread Chef");
        jamChef = new Thread(new Chef(buffer, Ingredient.JAM), "Jam Chef");
        pbChef = new Thread(new Chef(buffer, Ingredient.PB), "Peanut-butter Chef");
        agent.start();
        breadChef.start();
        jamChef.start();
        pbChef.start();
    }
}

//Agent acts as a producer.
class Agent implements Runnable
{ 
    private Table table;

    public Agent(Table buf)
    {
        table = buf;
    }

    public void run()
    {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient secondIng;
    	//Creates 20 sandwiches.
    	for(int i = 0; i < 20; i++) {
            ingredients.clear();
            ingredients.add(getRandomIngredient());
            
            do{
            	secondIng = getRandomIngredient();
            }while(ingredients.contains(secondIng));
            
            ingredients.add(secondIng);
            
            System.out.print(Thread.currentThread().getName() + " put ingredients: ");
            for(Ingredient ing:ingredients){
            	System.out.print(ing.name() +"  ");
            }
            System.out.println();
            
            table.put(ingredients);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
    	
    	System.exit(1); //End program when all sandwiches have been made.
    }
    
    // retrieve a random ingredient
 	private Ingredient getRandomIngredient() {
 		int index = (int)(Math.random() * Ingredient.values().length); 
 		return Ingredient.values()[index];
 	}
    
    
}

//Chef acts as a consumer. 
class Chef implements Runnable
{
    private Table table;
    private Ingredient ingredient;
    private int sandwichesMade;
    private ArrayList<Ingredient> groceryBag;
    
    public Chef(Table buf,Ingredient i)
    {
        table = buf;
        ingredient = i;
        sandwichesMade=0;
    }

    public void run()
    {
        for(;;){
        	synchronized(table){
            	try{
                	while(table.isEmpty()){
                		table.wait();
                	}
                	if(!table.hasIngredient(ingredient)){
                		groceryBag = table.get();
                		table.notifyAll();
                		eat();
                	}else{
                		table.wait();
                	}
            	}catch (InterruptedException e) {
    				e.printStackTrace();
    			}
            }
        }

    	
    }
    
    private void eat(){
    	sandwichesMade++;
    	System.out.println(Thread.currentThread().getName()+ " made sandwich: " + sandwichesMade + " with ingredient: " + ingredient.name());
       	System.out.println("Overall sandwich number: " + table.getSandwichesMade());
       	System.out.println();
    }
}
