//Trevor Nichols
//HTML Manager
//Section: AF

import java.util.*;

/**
 * Creates a tag manager for a web page coded in HTML.
 * Manages a set of tags by fixing invalid HTML tags.
 * Ignores HTML tags with attributes.
 */
public class HTMLManager {
    private Queue<HTMLTag> tagManager;
    private int size;
    
    //Constructs a HTML tag manager for an HTML page.
    //throws IllegalArgumentException if a null page is passed.
    public HTMLManager(Queue<HTMLTag> page){
        if(page == null){
            throw new IllegalArgumentException("The queue is null.");
        }
        this.createManager(page);
    }
  
    //Adds an element to the set.
    //throws an IllegalArgumentEcxeption if a null element is passed.
    public void add(HTMLTag tag){
        this.determineLegality(tag);
        this.tagManager.add(tag);
        this.size++;
    }
  
    //Removes every instance of a given element from a set.
    //throws an IllegalArgumentEcxeption if a null element is passed.
    public void removeAll(HTMLTag tag){
        this.determineLegality(tag);
        int originalSize = this.size;
        for(int i = 0; i < originalSize; i++){
            if(!tag.equals(this.tagManager.peek())){
                this.traverseSet();
            } else{
                this.tagManager.remove();
                this.size--;
            } 
        }
        
    }
  
    //Returns a set of elements.
    public List<HTMLTag> getTags(){
        return copySet();
        
    }
  
    //Fixes HTML tags for a given page.
    public void fixHTML(){
        Stack<HTMLTag> openingTags = new Stack<HTMLTag>();
        Queue <HTMLTag> outputTags = new LinkedList<HTMLTag>();
        
        while(!this.tagManager.isEmpty() || !openingTags.isEmpty()){

            if(this.tagManager.peek().isOpening()){
                   openingTags.push(this.tagManager.peek());
                   outputTags.add(this.tagManager.remove());
            }
           
            if(this.tagManager.peek().isSelfClosing()){
                   outputTags.add(this.tagManager.remove());
            }
            
            if(this.tagManager.peek().isClosing()){
                 
                 if(!this.tagManager.isEmpty() && openingTags.isEmpty() ){
                     this.tagManager.remove();
                
                 } else if(!this.tagManager.peek()
                         .matches(openingTags.peek())){
                     outputTags.add(openingTags.peek().getMatching());
                     openingTags.pop();
                 
                 } else {
                     outputTags.add(this.tagManager.remove());
                     openingTags.pop();
                 }
            }
                       
            while(this.tagManager.isEmpty() && !openingTags.isEmpty()){
                outputTags.add(openingTags.peek().getMatching());
                openingTags.pop();
            }
        }
                 
        this.tagManager = outputTags;
        this.size = outputTags.size();
    }
  
    //Creates the manager.
    private void createManager(Queue<HTMLTag> page){
        Queue<HTMLTag> q = new LinkedList<HTMLTag>();
        for(int i = 0; i < page.size(); i++){
            q.add(page.peek());
            page.add(page.remove());            
        }
        this.size = page.size(); 
        this.tagManager = q;
    }
   
    //Copies a set of elements.
    private List<HTMLTag> copySet(){
        List<HTMLTag> copy = new LinkedList<HTMLTag>();
        for(int i = 0; i < this.size; i++){
            copy.add(this.tagManager.peek());
            this.traverseSet();
        }
        return copy;
    }
   
    //Determines the validity of an argument.
    private void determineLegality(HTMLTag tag){
        if(tag == null){
            throw new IllegalArgumentException
                ("The tag being added is null.");
        }
    }
    
    //Traverses through elements of a set.
    private void traverseSet (){
        this.tagManager.add(this.tagManager.remove());
    }
}