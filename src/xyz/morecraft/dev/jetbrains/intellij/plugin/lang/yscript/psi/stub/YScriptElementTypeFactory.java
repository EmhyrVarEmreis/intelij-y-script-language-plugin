package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.stub;

import com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptElementTypes;

public class YScriptElementTypeFactory {

    public static IStubElementType stubFactory(@NotNull String name) {
        if ("PROGRAM".equals(name)) return YScriptElementTypes.PROGRAM;
        throw new RuntimeException("Unknown element type: " + name);
    }

}
