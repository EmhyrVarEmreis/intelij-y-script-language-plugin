package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubIndexKey;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptStubIndexKeys {

    public static StubIndexKey<String, YScriptProgram> PROGRAM_NAME =StubIndexKey.createIndexKey("yScript.program.name");

}
