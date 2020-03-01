package utep.cs3331.lab4.files;

import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;

public class DomEditor {
    private File inputFile;
    private SAXBuilder saxBuilder;
    private Document configFile;
    
//    public boolean createBuilders(String filePath) {
////        try {
////            this.inputFile = new File(filePath);
////
////            //Create a document builder
////            this.saxBuilder = new SAXBuilder();
////
////            //Create a DOM tree Obj
////            this.configFile = saxBuilder.build(inputFile);
////        }
////        catch (JDOMException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            System.out.println("A problem occured with the input/output. Would you: ");
////            System.out.println("1. Try again");
////            System.out.println("2. Quit");
////            try {
////                int menuNum = input.nextInt();
////                if (menuNum == 1) {
////                    this.collectFilePath(input);
////                }
////                else if (menuNum != 2) {
////                    throw new InputMismatchException();
////                }
////            }
////            catch(InputMismatchException o){
////                System.out.println("Invalid input. Program terminating.");
////            }
////        }
//    }
    

    
    public void openInputFile(String filePath) throws IOException {
        this.inputFile = new File(filePath);
    }
    
    public void createSaxBuilder() throws JDOMException {
        this.saxBuilder = new SAXBuilder();
    }
    
    public void buildConfigFile() throws JDOMException, IOException {
        this.configFile = saxBuilder.build(this.inputFile);
    }
    
    public File getInputFile(){
        return this.inputFile;
    }
    public SAXBuilder getSaxBuilder(){
        return this.saxBuilder;
    }
    public Document getConfigFile() {
        return this.configFile;
    }
    
}