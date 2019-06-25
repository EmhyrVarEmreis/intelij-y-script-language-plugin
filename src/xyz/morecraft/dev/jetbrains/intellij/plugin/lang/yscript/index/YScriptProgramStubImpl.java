package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptProgramStubImpl extends StubBase<YScriptProgram> implements YScriptProgramStub {

    private final String name;

    public YScriptProgramStubImpl(final StubElement parent, final String name) {
        super(parent, YScriptElementTypes.PROGRAM);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
