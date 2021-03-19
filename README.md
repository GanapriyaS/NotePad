# NotePad

A notepad desktop appliction created using java programming language and Swing(java GUI)

For frame JFrame,JLabel,JScrollPane,JTextArea,JmenuItem,JMenuBar,JMenu used

Functions implemented:

FILE
* new - Opens a new file with confirmation to save the existing file with heading "Untitled - Notepad".
* open - Open an existing file by using JFileChooser object.
* save - Save the existing file
* saveas - save the existing file newly as another file.

EDIT
* cut - cut the selected text
* copy - copy the selected text
* paste - paste the selected text
* find and replace - find or find and replace the selected text with new one
* print -  print the textarea content or save as file in computer or laptop

FORMAT
* font - Font class used to set fontfamily, fontstyle and fontsize
* font_color - change the font color using Color class
* background_color - change the background color of textarea using Color class

VIEW 
* zoomin - decrease the font size
* zoomout -increase the font size


When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, download this editor.jar file and run this command from the command line
**java -jar "Editor.jar" **
