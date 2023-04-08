/**
 *  Name: Tom Chiapete
 *  
 *  Project:  Huffman codes
 *
 *  This class with encode and decode a user specified
 *  file using the Huffman algorithm.
 *  
 *  Uses priority queues and tree.
 *
 *  Known Bugs:  None that I know of.
 */

import java.io.*;
import java.util.*;


public class Huffman
{
    // Encode the tree using a priority queue of generic type HuffNode
    private static PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
    
    private static HuffNode topNode; // Holds the root
    private static String filename = ""; // Holds the filename that was given by the user
    private static String encodeFilename = ""; // Encoded text filename
    private static String encodedString = ""; // String that holds a long encoded string
    private static String decodedString = ""; // String that holds a long decoded string
    
    // Declare and initialize an array of nodes - size 256
    private static HuffNode [] frequencyArr = new HuffNode[256];
    
    // Declare an array that is used for decoding - size 256 from ascii
    private static HelperNode [] nodesArr = new HelperNode [256];

    /**
     * fillInArray() method
     * Fills the array with temporary nodes until the length of the 
     * frequency array.
     */
    public static void fillInArray()
    {
        for(int x = 0; x < frequencyArr.length; x++)
        {
            // Create a new Huffman node
            HuffNode node = new HuffNode(x);
            
            // Place that Huffman node into the array.
            frequencyArr[x] = node; 
        }
    }
    
    /**
     * main() method
     * Main will call of off the methods necessary to encode and decode 
     * a file scanned in.
     */
    public static void main (String [] args)
    {
        encodeFilename = "encoded.txt";
        
        // Fills in the array
        fillInArray();
        
        // Loads in the user specified file.
        loadSpecifiedFile();
        
        // Creates the tree based on the priority queue
        createTree();
        
        // Encode method.
        encode(topNode, "");
        
        // Output encode info.
        outputToFile();
        
        // Prepare the encoded file.
        encodeFile();
        
        // Write out the encoded in the form of a file.
        writeOutEncoded();
        
        // Finally, decode the encoded file.
        decodeTheEncodedFile();
    }
    
    /**
     * loadFile() method
     * Scans in the file specified by the user.
     * Increment frequency as needed.
     */
    public static void loadSpecifiedFile() 
    { 
        // Prompt
        System.out.print("Please enter in the filename: "); 
        
        // Create scanner object
        Scanner input = new Scanner(System.in); 
        
        // Read in the filename
        filename = input.next();
        
        // Attempt to load in the file.
        // If file not found, throw an exception
        try 
        { 
            // Create a new scanner to bring in the file itself
            // Use the filename specified.
            Scanner scanLines = new Scanner(new File(filename)); 
            
            // Read while the scanner has another line.
            while(scanLines.hasNextLine())
            {
                // Store the current line into the line variable
                String line = scanLines.nextLine();
                
                // process the string setting the char and incrementing the frequency and 
                // after each line increment the node that holds the carriage return.
                
                // Go through the string.  Set the eachCharacter and increment the frequency.
                // After the line, increment the node when it holds a carriage return character.
                for(int x = 0; x < line.length(); x++)
                {            
                    // Get each character
                    char eachCharacter = line.charAt(x);
                    
                    // For Huffman frequency array at the character's int equivalent, 
                    // increment the frequency by 1.
                    frequencyArr[(int)eachCharacter].incrementFrequency();
                }
                frequencyArr[2].incrementFrequency();
            }
        } 
        
        // File not found, incorrect filename.
        catch(FileNotFoundException e) 
        { 
            System.out.print(filename); 
            System.out.println(" was not found."); 
            System.exit(1);
        } 
    }   
    
    /**
     * outputToFile() method
     * Output the encoding table.
     */
    public static void outputToFile()
    {
        // Try to do this block.  I don't know how this would fail, but apparently it can.
        try
        {
            // First, FileWriter requires us to specifiy a filename
            FileWriter output = new FileWriter("outputTable.txt");
            
            // Go through the frequency array.  Begin writing out the file.
            for(int x = 0; x < frequencyArr.length; x++)
            {
                 // When frequency at position x is NOT equal to zero, 
                 // call the toString method, throw a carriage return, 
                 // and write out.
                 if(!(frequencyArr[x].getFrequency() == 0))
                 {          
                     String outputString = frequencyArr[x].toString() 
                                                +     "\n";
                     
                     // Write contents of outputString to file
                     output.write(outputString);
                 }
            }
              
            // Flush the FileWriter...otherwise FileWriter does crazy things later on.
            output.flush();

        }
        
        // If you ran out of disk space, throw an I/O error.
        catch (IOException e)
        {
            System.out.println("Input/Output Error");
        }

        
    }
    
    /**
     * createTree() method
     * This will start creating the tree using the priority queue declared above.
     */
    public static void createTree()
    {
        
        // Start setting up the priority queue.
        // Take the minimum values and put them into the tree.
        // Go through the frequency array until the array ends.
        for(int x = 0; x < frequencyArr.length; x++)
        {
            // If at the position of the frequency array the frequency is not zero, 
            // add that Huffman node to the priority queue.
            if(!  (frequencyArr[x].getFrequency() == 0))
                pq.add(frequencyArr[x]);
        }  
        
        // Grab the size of the priority queue
        int priorityQueueLength = pq.size();
        
        // Poll the Huffman nodes from the priority queue until 
        // the end of the priority queue.  Start at position 1.
        for(int x = 1; x < priorityQueueLength; x++)
        {
            
            // "Poll" the first one in order.  Store it into node1 
            HuffNode node1 = (HuffNode)pq.poll();
            
            // Poll the second Huffman node and store it into node2.
            HuffNode node2 = (HuffNode)pq.poll();
            
            // Next create a 3rd node object using our node1 and node2 
            // that we just created.
            HuffNode node3 = new HuffNode(node1,node2);
            
            // Add the newly created node3 to the priority queue.
            pq.add(node3);
        }

        // For the encoding, I need to store off the top root node.
        // This can be done by peeking the priority queue.
        topNode = pq.peek(); 
        
    }
    
    /**
     * encode() method
     * This will encode the tree by recursively calling itself and 
     * sets the correct Huffman code for each node.
     */
    public static void encode(HuffNode rootNode, String huffmanCode)
    {
        // When the root node is null and the Huffman code is not going to be
        // set, call the encode method.
        if(rootNode.getCode() == null)
        {
            // For right nodes -- huffmanCode + "1"
            encode(rootNode.getRight(), (huffmanCode + "1"));
            
            // For left nodes -- huffmanCode + "0"
            encode(rootNode.getLeft(), (huffmanCode + "0"));
        }
        
        // Otherwise, just set the code.
        else
        {
            rootNode.setCode(huffmanCode);
        }
        
        
        // I used a simple helper node class so I could decode easier
        for(int x = 0; x < nodesArr.length; x++)
        {
            
            // If the frequency at this position is zero, 
            //the nodes array at position x is null
            if(frequencyArr[x].getFrequency() == 0)
                nodesArr[x] = null;

            // Otherwise, create that helper node using the code and character
            // value.
            else
                nodesArr[x] = new HelperNode(frequencyArr[x].getCode() , 
                                            frequencyArr[x].getValue());
        }

    }
    

    
    
    /**
     * writeOutEncoded() method
     * Simply writes out the encoded text into a file.
     */
    public static void writeOutEncoded()
    {
        // Try block - even though this should not fail.
        try
        {
            
            // Create a FileWriter using the encoding filename I used 
            // above in the constructor.
            FileWriter outputFile = new FileWriter(encodeFilename); 
            
            // Write out to file.
            outputFile.write(encodedString);
            
            // Flush the FileWriter, otherwise there might be a problem later on.
            outputFile.flush();
        }
        
        // If caught an exception, throw an I/O exception.
        catch (IOException e)
        {
            System.out.println("Iinput/Output Exception");
        }

    }
    
    
    /**
     * searchArray() method
     * This will search the array of Huffman nodes and will try to find the 
     * position in the array.  
     * If there is a position found, return that, otherwise, return -1
     * as a standard java default for not found.
     */
    public static int searchArray(String string, HelperNode [] array)
    {
        // Default return value.
        int returnVal = -1;
        
        // Go through the passed in array.
        for(int pos = 0; pos < array.length; pos++)
        {
           // If in the array at position 'pos' is NOT null, check to see if the 
           // string in the argument is equal to the array at position 'pos' Huffman code,
           // set the position equal to the return value and the array length is the new position.
           if(array[pos] != null)
           {
                if((string.equals(
                        array[pos].getHuffmanCode()
                                                    )))
                {
                    // Set the position equal to the return value.
                    returnVal = pos;
                    
                    // Set the size of the array to 'pos' variable.
                    pos = array.length;
                }
                
                // If the return value is greater than the default -1,
                // return that return value.
                if(returnVal >= 0)
                    return returnVal;
            }
        }
        // Not found.  Return -1.
        return -1;
    }
    
    
    /**
     * decodeTheEncodedFile()
     * This will search and decode the encoded file.
     * Output the decoded file so we know it works.
     */
    public static void decodeTheEncodedFile()
    {
        
        // Try block for scanner
        try
        {
            
            // I need a counter to know where I'm at in the string later on.
            int counter = 0;
            
            // Scan the encoded file.
            Scanner scan = new Scanner(new File(encodeFilename));
            
            // Read in the line - 1 line because there was no carriage returns.
            String line = scan.nextLine();
            
            // Go through until there are no characters left.
            while(counter < line.length())
            {
                // We need a StringBuffer, not a string for this.
                StringBuffer sb = new StringBuffer();
                
                // Boolean variable to see if we have a match
                boolean isFound = false;
                
                // Read character.
                char character = line.charAt(counter);

                // Append the character to the string buffer.
                sb = sb.append(character);
                
                // Now we can increment the counter.
                counter = counter + 1;
                
                // While the character is not yet found, keep going through and searching.
                while(isFound == false)
                {
                    
                    
                    // Search through the array and check if the the huffman code
                    // is in the node.
                    if(searchArray(sb.toString(), nodesArr)  >=  0)
                    {
                        // Find the position of the node.
                        int positionOfNode = searchArray(sb.toString() , nodesArr);
                        
                        // If a position of 2 is detected, it's a carriage return.  
                        // Add on a \n
                        if(positionOfNode == 2)
                            decodedString = decodedString 
                                                + "\n";
                        
                        // Otherwise, get the value.
                        else
                            decodedString = decodedString + 
                                nodesArr[positionOfNode].getValue();
                        
                        // Obviously, it has been found.
                        isFound = true;
                    }
                    
                    // Add the next character.
                    else
                    {
                        sb = sb.append(line.charAt(counter));
                        
                        // Increment the counter.
                        counter++;
                    }
                    
                    // Loop if necessary.
                }
            }
            

        }
        
        // Throw an exception if I/O error.
        catch(IOException e)
        {
            System.out.println("Input/Output Exception");
        }
        
        
        // ----- Output decoded file.
        
        // Try block for FileWriter.
        try
        {
            // Create FileWriter object.
            FileWriter output = new FileWriter("decodedText.txt");  
            
            // Write out the decoded string.
            output.write(decodedString);
            
            // Flush the FileWriter.
            output.flush();

        }
        
        // Catch block if necessary.
        catch(IOException e)
        {
            System.out.println("Input/Output Exception");
        }
    }
    
    /**
     * encodeFile() method 
     * encodeFile will create the long string of 0's and 1's and store 
     * it in the encodedString data member.
     */
    public static void encodeFile()
    {
        
        // Try block for Scanner
        try
        {
            // Create a scanner for the file that was specified before.
            Scanner code = new Scanner(new File(filename));
            
            // While there are still lines -- may be only one line -- read through.
            while(code.hasNextLine())
            {
            
                // Store the line
                String line = code.nextLine();
                
                // Retrieve the code for each character.
                for(int x = 0; x < line.length(); x++)        
                    encodedString = encodedString + 
                                        frequencyArr[(int)(line.charAt(x))].getCode();
                
                // Add on to the end.
                encodedString = encodedString + 
                                            frequencyArr[2].getCode();
            }

        }
        
        // Somehow we reached this point.  Throw an FileNotFound exception.
        catch(FileNotFoundException e)
        {
            System.out.println("Iinput/Output Exception");
        }
    }
}

