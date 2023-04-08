/**
 *  Tom Chiapete
 *  Huffman Project
 *  March 28, 2006
 *  
 *  This class will create nodes that will form a tree in this project.
 *  
 *  This uses the common data members used in trees, but also has 
 *  data such as a Huffman code, a character value, and a frequency.
 *  
 *  This implements the comparable interface.
 *  I specified it to be a HuffNode generic to eliminate the warning.
 *  
 *  Known bugs:  none.
 */


public class HuffNode implements Comparable<HuffNode>
{
    // Instance variables.
    private int node; // Holds the node
    private HuffNode left; // Holds the left HuffNode object.
    private HuffNode right; // Holds the right HuffNode object.
    
    // Additions to normal node
    private String huffmanCode; // Holds the Huffman code string.
    private char charValue; // Holds one character value.
    private int frequency; // Stores an integer frequency.

    /**
     * HuffNode constructor
     * This constructor will create a blank node - frequency 0, 
     * with no huffman code data.
     */
    public HuffNode(int position)
    {
        huffmanCode = "";
        charValue = (char)position;
        frequency = 0; 
    }
    
    /**
     * incrementFrequency() method
     * Increments the frequency variable.
     */
    public void incrementFrequency()
    {
        frequency = frequency + 1;
    }
    

    /**
     * compareTo() method
     * Compares HuffNode's based on this frequency and the 
     * argument object's frequency.
     */
    public int compareTo(HuffNode node)
    {

        // If this object's frequency is greater than the argument's 
        // frequency, return 1.
        if(frequency > node.getFrequency())
            return 1;
        
        // If this object's frequency is less than the argument's 
        // frequency, return -1.    
        else if(frequency < node.getFrequency())
            return -1;
        
        // Otherwise, there is no difference... return 0.
        return 0;
    }
    
    
    /** 
     * HuffNode constructor
     * This constructor creates a Huffman node from a left and right node.
     * The frequency can be found by taking the frequencies returned from the
     * right and left and adding them together and storing it into this object's 
     * frequency.
     */
    public HuffNode(HuffNode leftNode, HuffNode rightNode)
    {
        // Store the right into right
        right = rightNode;
        
        // Store the left into left.
        left = leftNode;
        
        // Take the left and right node's frequencies and store that 
        // int into the frequency.
        frequency = left.getFrequency() + right.getFrequency();
    }
    
    /**
     * toString() method
     * This method is used to display character value,
     * frequency, and string Huffman code into a table.
     */
    public String toString()
    {
        return charValue + "  ,  " + frequency + "  ,  " + huffmanCode;
    }
    
    
    /**
     * HuffNode constructor
     * This construct creates leaf nodes.  It takes in three arguments: 
     * a frequency, a Huffman code, and a character value.
     * It should set the left and right nodes to null, and the arguments 
     * to the data members.
     */
    public HuffNode(int frequency, String code, char characterValue)
    {
        // Set the left to null.
        left = null;
        
        // Set the character value to the charValue data member.
        charValue = characterValue;
        
        // Set the frequency specified to the frequency data member.
        this.frequency = frequency;
        
        // Set the Huffman code specified to the huffmanCode data member.
        huffmanCode = code;      
        
        // Set the right to null.
        right = null;
    }
    
    /**
     * getRight() method
     * Returns the right Huffman Node.
     */
    public HuffNode getRight()
    {
        return right;
    }
    
    /**
     * getLeft() method
     * Returns the left Huffman Node.
     */
    public HuffNode getLeft()
    {
        return left;
    }
    
    /**
     * getFrequency() method
     * Returns the frequency of this Huffman Node.
     */
    public int getFrequency()
    {
        return frequency;
    }
    
    /**
     * getNodeInt() method
     * Returns Huffman Node integer data member.
     */
    public int getNode()
    {
        return node;
    }
    
    /**
     * getCode() method
     * Returns the string Huffman code.
     */
    public String getCode()
    {
        return huffmanCode;
    }

    /**
     * getValue() method
     * Returns the char value of this Huffman Node.
     */
    public char getValue()
    {
        return charValue;
    }

    /**
     * setRight() method
     * Sets the left Huffman Node to the right instance variable.
     */
    public void setRight(HuffNode right)
    {
        this.right = right;
    }
    
    /**
     * setCode() method
     * Sets the string Huffman code to the huffmanCode instance variable.
     */
    public void setCode(String huffmanCode)
    {
        this.huffmanCode = huffmanCode;
    }

    /**
     * setLeft() method
     * Sets the left Huffman Node to the left instance variable.
     */
    public void setLeft(HuffNode left)
    {
        this.left = left;
    }
}