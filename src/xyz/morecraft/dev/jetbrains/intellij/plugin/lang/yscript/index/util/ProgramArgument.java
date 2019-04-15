package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util;

import java.util.Objects;

public class ProgramArgument extends VariableType {

    private final String name;

    public ProgramArgument(String name, String type, String xsdPath) {
        super(type, xsdPath);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProgramArgument that = (ProgramArgument) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

}
