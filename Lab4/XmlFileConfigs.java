package utep.cs3331.lab4.files;

import org.jdom2.Element;

public interface XmlFileConfigs {
    public static Element createRootChessGame() {
        return new Element("ChessGame");
    }
    public static Element createRootUserInfo() {
        return new Element("UserInfo");
    }
    public static Element createUser() {
        return new Element("user");
    }
    public static Element createName() {
        return new Element("name");
    }
    public static Element createExpertiseLevel() {
        return new Element("expertise_level");
    }
    public static Element createUserColor() {
        return new Element("user_color");
    }
    public static Element createUserGameKey() {
        return new Element("user_game_key");
    }
}