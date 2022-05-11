/**
 * Handles text based file input and output
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.File;

import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.lang.SecurityException;

public class FileIO {

  private File file;
  private Scanner reader;
  private boolean readonly = true;

  /**
   * @param  filepath  path to the file as String 
   * @throws  NullPointerException  passthrough exception from File class 
   */
  public FileIO(String filepath) throws NullPointerException {
    this.file = new File(filepath);
  }

  /**
   * @param  filepath  path to the file as String 
   * @param  readonly  <code>True</code> read only (default), 
   *                   <code>False</code> writable 
   * @throws  NullPointerException  passthrough exception from the File class 
   */
  public FileIO(String filepath, Boolean readonly) throws NullPointerException {
    this.file = new File(filepath);
    this.readonly = readonly;
  }

  /** reading ops */

  /**
   * Starts a scanner instance for file reading 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  private void startReader() throws IOExceptions {
    try {
      this.reader = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException | SecurityException e) {
      throw new IOExceptions("Could not open file for reading");
    }
  }

  /**
   * Checks if scanner instance has been started and starts one 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  private void checkAndLoadReader() throws IOExceptions {
    if (!(this.reader instanceof Scanner)) {
      this.startReader();
    }
  }

  /**
   * @return  whether there is another token to read from the file
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public boolean canReadNext() throws IOExceptions {
    this.checkAndLoadReader();
    return this.reader instanceof Scanner ? this.reader.hasNext() : false;
  }

  /**
   * @return  next token read from the file as String
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public String readNext() throws IOExceptions {
    this.checkAndLoadReader();
    return this.canReadNext() ? this.reader.next() : "";
  }

  /**
   * @return  whether this is another line to read from the file 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public boolean canReadNextLine() throws IOExceptions {
    this.checkAndLoadReader();
    return this.reader.hasNextLine();
  }

  /**
   * @return  the remainder of the current line as String 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public String readNextLine() throws IOExceptions {
    this.checkAndLoadReader();
    return this.canReadNextLine() ? this.reader.nextLine() : "";
  }

  /**
   * @return  the entire file contents as an ArrayList of Strings 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public ArrayList<String> readContentsAsArray() throws IOExceptions {
    this.resetReader();
    ArrayList<String> contents = new ArrayList<String>();
    while (this.canReadNextLine()) {
      contents.add(this.readNextLine());
    }
    return contents;
  }

  /**
   * Closes the Scanner instance for file reading 
   */
  public void closeReader() {
    if (this.reader instanceof Scanner) {
      this.reader.close();
    }
  }

  /**
   * Close and restarts the Scanner instance for file reading 
   * @throws  IOExceptions  if file could not be opened with FileInputStream 
   */
  public void resetReader() throws IOExceptions {
    this.closeReader();
    this.startReader();
  }

  /** writing ops */

  /**
   * Opens a file for writing
   * @param  append  append to the file rather than overwriting it 
   * @throws  IOExceptions  if read only operation is specified 
   * @throws  IOExceptions  if file could not be opened with FileOutputStream 
   */
  private PrintWriter openWritableFile(boolean append) throws IOExceptions {
    if (this.readonly) {
      throw new IOExceptions("File operation in readonly mode");
    }

    try {
      return new PrintWriter(new FileOutputStream(file, append));
    } catch (FileNotFoundException | SecurityException e) {
      throw new IOExceptions("Could not open file for writing");
    }
  }

  /**
   * Enables file writing operations by setting readonly to <code>False</code>
   * @return  FileIO to chain methods 
   */
  public FileIO setWritable() {
    this.readonly = false;
    return this;
  }

  /**
   * Writes to the file by replacing its current contents 
   * @param  text  the string text to write to the file 
   * @throws  IOExceptions  if file could not be opened with FileOutputStream 
   */
  public void overwrite(String text) throws IOExceptions {
    PrintWriter file = this.openWritableFile(false);
    file.println(text);
    file.close();
  }

  /**
   * Appends one line of string text to the file 
   * @param  text  the string text to write to the file 
   * @throws  IOExceptions  if file could not be opened with FileOutputStream 
   */
  public void writeLine(String text) throws IOExceptions {
    PrintWriter file = this.openWritableFile(true);
    file.println(text);
    file.close();
  }

  /** other file ops */

  /**
   * Clean up operations 
   */
  public void close() {
    this.closeReader();
  }

  /**
   * Delete the file if required 
   * @return  <code>True</code> if file is successfull deleted 
   * @throws  SecurityException  file could not be accessed due to security settings 
   */
  public boolean delete() throws SecurityException {
    return this.file.delete();
  }
  
}
