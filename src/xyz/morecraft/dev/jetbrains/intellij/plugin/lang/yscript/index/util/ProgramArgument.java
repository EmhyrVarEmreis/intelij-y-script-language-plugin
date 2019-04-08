package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util;

public class ProgramArgument extends VariableType {

    private final String name;

    public ProgramArgument(String name, String type, String xsdPath) {
        super(type, xsdPath);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
