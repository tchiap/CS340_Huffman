/**
 * Tom Chiapete
 *  March 28, 2006
 *  Just holds the character value and the Huffman code for decode
 *  output.  I had somebody suggest this for simplification.
 *
 *  Known Bugs: none.
 */

public class HelperNode
{
    // Instance variables.
    
    private String huffmanCode; // Holds the Huffman code string.
    private char charValue; // Holds the character value.

    /**
     * setCode() method
     * Sets the string Huffman code to the huffmanCode instance variable.
     */
    public void setCode(String huffmanCode)
    {
        this.huffmanCode = huffmanCode;
    }
    
   /**
     * getValue() method
     * Returns the charValue.
     */
    public char getValue()
    {
        return charValue;
    }    

    /**
     * Constructor HelperNode
     * Takes in two parameters, a string Huffman code,
     * and a character value.
     * Stores them in the instance variables.
     */
    public HelperNode(String code, char characterValue)
    {
        this.huffmanCode = code;
        charValue = characterValue;
    }

    /**
     * getHuffmanCode() method
     * Returns the string Huffman Code.
     */
    public String getHuffmanCode()
    {
        return huffmanCode;
    }

    /**
     * setCharValue() method.
     * Takes in one parameter - a character - 
     * and stores it into the instance variable.
     */
    public void setCharValue(char character)
    {
        charValue = character;   
    }
}

