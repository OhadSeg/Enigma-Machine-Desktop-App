# Enigma-Machine-Desktop-Application

1. Loading enigma machine details from resources folder
2. Choose the identity of the rotors and the starting position of each of them, reflector and wiring in the plug board (you can click on random and the system will choose for you randomly)
3. Words can be encrypted and decrypted in 2 ways:
- by entering words from the keyboard and then pressing process.
- By pressing character by character on the system keyboard and then pressing done.
Then click on reset and clear.
4. In the Brute Force panel we will want to encrypt text from the dictionary and let the machine go through all the machine's options and look for all the suspicious words that could be the text we encrypted.
  Choose a task size, difficulty level and number of agents (threads) to perform the task and then click on start.


https://user-images.githubusercontent.com/110668613/192724564-a6d3731d-0e67-4238-a6c0-25ea77552ded.mp4


BREAKING THE ENIGMA - Part.2

In this exercise we created 2 modules: DesktopUI, Engine

Engine Module - includes 7 packages.

Brute Foce:

In this package we have 4 class, 
1.	A custom made - threadFactory - creates the thread used for the description mission
2.	Static class supportTask - holds static members relevant to the tasks of the brute force mission.
3.	Task implements runnable class
4.	And the description manager class - that manages the engine bruteforce side.
Components : Contains the machine components implementation.
class Rotor - we chose to implement the rotor class with two ArrayList<Character> one for left and the other for right, used for representing the character mapping of each rotor. Rotor class also contains notch, index of window, rotor id and a boolean variable flag if the next rotor should be advanced.
Class Rotors - used to contain the collection of rotors, used in the enigma, the collection implemented as a map where the key is integer rotor id, and the value is the rotor object itself, there are also data members of the amount of all rotors, and another to arraylist contains the chosen rotors, and rotors starting position.
Class Reflector - used for implementing the reflector component in the enigma machine, contains a map with an integer key and value for the mapping of the reflector, and also two more data members, the amount of mapping used by the reflector and roman number as the ID.
Class Reflectors- used to contain the collection of reflectors, implemented by a map with the roman number reflector Id as the key and the reflector itself as the value. contains also the roman number of the current reflector in use and the amount of total reflector available to choose from.
Class PlugBoard- used to represent the plugboard component on the machine, an array list of pair of char that contains all available plugs, and a map of character key and value, represents the chosen inuse plugs.
Class Machine- represents the machine itself, assembles and contains objects of type plugboard,rotors, reflectors, and two maps used for the initial mapping of the letters in the machine abc. also contains machine language and the number of rotors as data members.
Enigma Machine- class contains only an object instance of the machine.
exceptions - we wrote 4 classes of exceptions under this package.
generated - Package contains the auto-generated CTE classes
resources - Package contains the XSD file and some XML files
utils - a package contains two enum classes - Roman numbers and direction right to left or left to right.
operation package - contains two classes, Tests and operator. Test is the class handling most of the logic input and machine details validation. Operator is the class handling the connection between the UI and the Engine models by moving back and forth dto objects.
UI Module - contains two packages
uiUtils package contains enum of the menu options.
enigmaUI package contains 3 classes.
Class Menu- class handling the menu of the enigma and his validity using boolean data members
Class ConsoleUI- class contains instances of the menu and the operator classes as data members. gets all the information the user has entered through the menu, managing, handling and checking it using the class operator instance.

In this exercise we have created new model for the desktop application interface - Using JAVAFX 
 
DesktopUI Module : contains a package of all the sub-components that the main component contains, designed using JAVAFX.
Our app contains 3 screens/tabs, the first screen is where the user is loading XML File, and Choosing/generating random machine details.
On the second screen the user can encrypt or decrypt the desired word on a specific enigma set.
The third newly developed screen contains a new feature - ‘brute force’ feature, where the user could decrypt an encrypted word, and to understand on which set of machine configurations it was processed.
