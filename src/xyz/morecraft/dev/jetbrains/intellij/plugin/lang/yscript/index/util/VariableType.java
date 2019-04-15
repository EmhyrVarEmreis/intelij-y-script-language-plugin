package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableType that = (VariableType) o;
        return type.equals(that.type) &&
                Objects.equals(xsdPath, that.xsdPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, xsdPath);
    }

}
