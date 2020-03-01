package utep.cs3331.lab4.files;

public class DomEditorTools {
    private File inputFile;
    private SAXBuilder saxBuilder;
    private Document configFile;
    
    public DomEditorTools(String filePath) {
        
        this.inputFile = new File(this.filePath);

        //Create a document builder
        this.saxBuilder = new SAXBuilder();

        //Create a DOM tree Obj
        this.configFile = saxBuilder.build(inputFile);
        
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