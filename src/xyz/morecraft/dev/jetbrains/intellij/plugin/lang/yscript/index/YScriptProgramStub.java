package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public interface YScriptProgramStub extends StubElement<YScriptProgram> {

    String getName();

    String getPackageName();

    ProgramArgument[] getArguments();

    VariableType getReturnType();

}
