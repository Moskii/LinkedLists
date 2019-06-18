/*

Name: Mahir Hussain
Student ID: 201322661
University email address: M.Hussain8@liverpool.ac.uk

Time Complexity and explanation:

appendIfMiss(): O(np)
- Each variable represents a linked list i.e n = files, p = requests
- 2 Loops implemented for each linked list. One to iterate through the files and the other to iterate through the requests leading to a time complexity of np.
- Both iterate through the list at a linear time. So as the data size increase, the time increases at the same rate
  
moveToFront(): O(n^2p)
- Each variable represents a linked list i.e n = files, p = requests
- Few loops are implemented in order to handle with the linked list carefully. 2 basic loops to iterate through the files and the requests (similar to part a).
- Extra methods were made to remove the duplication of a file (so another loop till the key is found) and to delete the file from the linked list. 
  This occurs if the request is already in the files.
- Thus the time complexity for the files are n^2.

freqCount():

*/

import java.util.*;
import java.io.*;


// Implement a linked list from the object A2Node
class A2List {

  private static Scanner keyboardInput = new Scanner (System.in);
  public static A2Node head, tail; // head and tail of the linked list
  private static final int MaxInitCount = 10;
  private static final int MaxReqCount = 100;
  public static int initCount;
  public static int reqCount;

  public static int[] reqData = new int[MaxReqCount]; // store the requests, accessible to all methods


  // DO NOT change the main method
  public static void main(String[] args) throws Exception {
    A2Node curr;
    int tmp=-1;
    int[] initData = new int[MaxInitCount];

    initCount = 0;
    reqCount = 0;
    head = null;
    tail = null;

    try {
//      System.out.println();
//      System.out.print("Enter the initial number of files in the cabinet (1-" + MaxInitCount + "): ");
      initCount = keyboardInput.nextInt();
      if (initCount > MaxInitCount || initCount <= 0)
        System.exit(0);
//      System.out.print("Enter the initial file IDs in the cabinet (" + initCount + " different +ve integers): ");
      for (int i=0; i<initCount; i++)
        initData[i] = keyboardInput.nextInt();        
//      System.out.println();
//      System.out.print("Enter the number of file requests (1=" + MaxReqCount + "): ");
      reqCount = keyboardInput.nextInt();
      if (reqCount > MaxReqCount || reqCount <= 0)
        System.exit(0);
//      System.out.print("Enter the request file IDs (" + reqCount + " different +ve integers): ");
      for (int i=0; i<reqCount; i++)
        reqData[i] = keyboardInput.nextInt();       
    }
    catch (Exception e) {
      keyboardInput.next();
      System.exit(0);
    }


    
    try {
      System.out.println("appendIfMiss...");
      // create a list with the input data
      // call appendIfMiss()
      for (int i=initCount-1; i>=0; i--) {
        insertNodeHead(new A2Node(initData[i]));
      }
      appendIfMiss();
    }
    catch (Exception e) {
      //System.out.println("appendIfMiss exception! " + e);
      System.out.println(e.getMessage());
    }

    try {
      System.out.println("moveToFront...");
      // empty the previous list and restart with the input data
      // then call moveToFront()
      emptyList();
      for (int i=initCount-1; i>=0; i--) {
        insertNodeHead(new A2Node(initData[i]));
      }
      moveToFront();
    }
    catch (Exception e) {
      // System.out.println("moveToFront exception!");
      System.out.println(e.getMessage());
    }

    try {
      System.out.println("freqCount...");
      // empty the previous list and restart with the input data
      // then call freqCount()
      emptyList();
      for (int i=initCount-1; i>=0; i--) {
        insertNodeHead(new A2Node(initData[i]));
      }
      freqCount();
    }
    catch (Exception e) {
      // System.out.println("freqCount exception!");
      System.out.println(e.getMessage());
    }   
  }
  
  // append to end of list when miss
  // Complete
  static void appendIfMiss() {
    // declare variables with specific data types
    int hits = 0;
    String comparisons = "";
    
    int i = 0; // Declared to be used for the loop.
    do {
      A2Node curr; // Declare curr to have A2Node attributes. 
      curr = head; // Assign curr to the head of the linked list.
      int comp_Int = 0;
      boolean found = false;
      while(curr != null && !found) {
        if (curr.data == reqData[i]) { // Checks if the data in the file is equal to the request file.
          found = true;
        }
        else {
          curr = curr.next; // Otherwise check the next element in the linked list.
        }
        comp_Int++; // Increment the comparison integer.
      }
      comparisons += comp_Int + " "; // Concatenate the integer into a string

      if (found == true) {
        hits++; // If the element is found, increment the hit counter.
      }
      else {
        // Otherwise append the element to the end of the linked list.
        A2Node for_new_files = new A2Node(reqData[i]); // The element @ reqData[i] will have access to the A2Node attributes.
        //Adding to tail
        if (tail != null) {
          tail.next = for_new_files;
          tail = for_new_files;
        }
        else {
          head = for_new_files;
        } 
      }
    i++;
    } while (i < reqCount); // Iterates till the end of the request count.
    System.out.print(comparisons + "\n" + hits + " h" + "\n");
    printList();
  }

  // Method to help find the position (For moveToFront)
  static int element_finder(int key) {
    // Helps find where the desired reqData is the linked list by incrementing position_count
    A2Node curr;
    boolean found = false;
    int position_count = 0;

    curr = head;
    while (curr != null && !found) {
      if (curr.data == key)
        found = true;
      curr = curr.next;
      position_count++;
    }
    return position_count-1;
  }

  // Method to delete a node (For moveToFront)
  static void deleteNode(int position) {
    // Deletes a node a given pointer. Carefully handled to ensure the Linked list stays intact.
    A2Node temp = head;

    // In case reqData[i] is at the head
    if (position == 0) {
      head = temp.next;
      return;
    }

    for (int i=0; temp!=null && i<position-1; i++) {
      temp = temp.next;
    }
    // Store pointer to the next of node to be deleted
    A2Node next = temp.next.next;
    temp.next = next; // Unlink the deleted node from list
  }

  // move the file requested to the beginning of the list
  // Complete
  static void moveToFront() {
    int hits = 0;
    String comparisons = "";
    
    int i = 0;
    do {
      A2Node curr;
      curr = head;
      int comp_Int = 0;
      boolean found = false;
      while(curr != null && !found) {
        if (curr.data == reqData[i]) {
          found = true;
        }
        else {
          curr = curr.next;
        }
        comp_Int++;
      }
      comparisons += comp_Int + " ";

      if (found == true) {
        hits++;
        // deletion of that element (prevent duplication)
        int find_element = element_finder(reqData[i]);
        deleteNode(find_element); // After finding the position of reqData[i], it deletes that node.
        // Add move to front structure here
        A2Node existing_files = new A2Node(reqData[i]);
        existing_files.next = head; //Assigns it to head
        head = existing_files; // Moves it to the head
      }
      else {
        A2Node new_files = new A2Node(reqData[i]);

        // Adding to head
        if (head != null) {
          new_files.next = head;
          head = new_files;
        }
        else {
          head = new_files;
        } 
      }
    i++;
    } while (i < reqCount);
    System.out.print(comparisons + "\n" + hits + " h" + "\n");
    printList();
  }
  
  // Methods to support freqCount

	static void swapNode(A2Node a, A2Node b) {
		int tmp;
		tmp = a.data;
		a.data = b.data;
		b.data = tmp;
	}	

  // Finding length of files array
  static int traverseArray() {
  	A2Node curr;
  	int length = 0;

  	curr = head;
  	while (curr != null) {
  		length++;
  		curr = curr.next;
  	}
  	return length;
  }

  // move the file requested so that order is by non-increasing frequency
  static void freqCount() {
    int length = traverseArray(); // Finds the length of files linked list.
  	int[] freqArray = new int[length]; // Initialise frequency array
  	// Setting up the array.
  	for (int i = 0;i < length;i++) {
  		freqArray[i] = 1;
  	} 
  	/* Test
  	for (int i=0;i < length;i++) {
  		System.out.print(freqArray[i]+ ",");
  	} */

  	// declare variables with specific data types
    /*int hits = 0;
    String comparisons = "";
    
    int i = 0; // Declared to be used for the loop.
    do {
      A2Node curr; // Declare curr to have A2Node attributes. 
      curr = head; // Assign curr to the head of the linked list.
      int comp_Int = 0;
      boolean found = false;
      while(curr != null && !found) {
        if (curr.data == reqData[i]) { // Checks if the data in the file is equal to the request file.
          found = true;
        }
        else {
          curr = curr.next; // Otherwise check the next element in the linked list.
        }
        comp_Int++; // Increment the comparison integer.
      }
      comparisons += comp_Int + " "; // Concatenate the integer into a string

      if (found == true) {
        hits++; // If the element is found, increment the hit counter.

      }
      else {
        // Otherwise append the element to the end of the linked list.
        A2Node for_new_files = new A2Node(reqData[i]); // The element @ reqData[i] will have access to the A2Node attributes.
        //Adding to tail
        if (tail != null) {
          tail.next = for_new_files;
          tail = for_new_files;
        }
        else {
          head = for_new_files;
        } 
      }
    i++;
    } while (i < reqCount); // Iterates till the end of the request count.
    System.out.print(comparisons + "\n" + hits + " h" + "\n");
    printList(); */
  }

  static void insertNodeHead(A2Node newNode) {

    newNode.next = head;
    if (head == null)
      tail = newNode;
    head = newNode;
  }

  // DO NOT change this method
  // delete the node at the head of the linked list
  static A2Node deleteHead() {
    A2Node curr;

    curr = head;
    if (curr != null) {
      head = head.next;
      if (head == null)
        tail = null;
    }
    return curr;
  }

  // DO NOT change this method
  // print the content of the list in two orders:
  // from head to tail
  static void printList() {
    A2Node curr;

    System.out.print("List: ");
    curr = head;
    while (curr != null) {
      System.out.print(curr.data + " ");
      curr = curr.next;
    }
    System.out.println();
  }

  
  // DO NOT change this method
  static void emptyList() {
    
    while (head != null)
      deleteHead();
  }

}