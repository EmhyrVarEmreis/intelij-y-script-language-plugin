package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;

public class YScriptElementType extends IElementType {

    public YScriptElementType(@NotNull @NonNls String debugName) {
        super(debugName, YScript.INSTANCE);
    }

}
