import java.util.ArrayList;

public class Table
{  

    private ArrayList<Ingredient> ingredients;
    private int sandwiches;
    
    // If true, no ingredients are on the table.    
    private boolean empty = true;
    
    public Table(){
    	ingredients = new ArrayList<Ingredient>();
    	sandwiches=0;
    }

    public synchronized void put(ArrayList<Ingredient> ing)
    {
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        for(Ingredient i:ing){
        	ingredients.add(i);
        }

        //Once agent puts ingredients on table, the table is not empty.
        empty = false;

        notifyAll();
    }

    public ArrayList<Ingredient> get()
    {
        ArrayList<Ingredient> sendIng = new ArrayList<Ingredient>();
        
       	sendIng.addAll(ingredients);
       	ingredients.clear();
       	sandwiches++;
       	empty=true;

        return sendIng;
    }
    
    public boolean hasIngredient(Ingredient i){
    	return ingredients.contains(i);
    }
    
    public boolean isEmpty(){
    	return empty;
    }
    
    public int getSandwichesMade(){
    	return sandwiches;
    }
    
}
