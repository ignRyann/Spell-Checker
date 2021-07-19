# Spell Checker

#### [1. Description](#description)

## Description
The Spell Checker is a program, written in Java, that completes all the requirements set for UCL COMP0004: Object-Oriented Programming Coursework 1. The requirements were set out as the following:

### {

### Requirement 1.
Write a class 'StringArray' that can store a collection of String object referenecs (i.e. a collection of Strings). The data structure used in the class to store the Strings must be a Java array, you cannot use any other data structure like an ArrayList. Values stored within the StringArray should be stored sequentially and accessed via their index position like an array. There should be no gaps in the sequence and no unused index positions. It should be possible to store null references as values if a null is added to the StringArray or assigned to an index position.

The internal Java array does not have to be the exact size needed to store the references currently stored in the StringArray and can have unused space. It is up to you to decide how to implement the StringArray class.

Provide the following:
- public StringArray()
- public StringArray( StringArray a)
- public int size()
- public boolean isEmpty()
- public String get( int index )
- public void set( int index, String s )
- public void add( String s )
- public void insert( int index, String s )
- public void remove( int index )
- public boolean contains( String s )
- public boolean containsMatchingCase( String s )
- public int indexOf( String s )
- public int indexOfMatchingCase( String s )

### Requirement 2.
Using classes, write a program that can perform spelling checking on a sectino of text. Use your StringArray class from Requirement 1, you are not allowed to use other classes like ArrayList.

The input text and dictionary words will be Strings that must be stored using your StringArray class.

### Requirement 3.
Extend your program to show the user a list of possible correct spellings if an incorrectly spelt word is found, so that the preferred spelling can be selected. The corrected text after spell checking should be written to a file.
