package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util;

public class VariableType {

    private final String type;
    private final String xsdPath;

    public VariableType(String type, String xsdPath) {
        this.type = type;
        this.xsdPath = xsdPath;
    }


    public String getType() {
        return type;
    }

    public String getXsdPath() {
        return xsdPath;
    }

}
