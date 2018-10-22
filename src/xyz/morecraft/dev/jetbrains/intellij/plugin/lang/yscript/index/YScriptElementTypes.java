package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.IStubElementType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;

public class YScriptElementTypes {

    static YScript LANG = YScript.INSTANCE;

    static IStubElementType PROGRAM = new YScriptProgramStubElementType();

}
