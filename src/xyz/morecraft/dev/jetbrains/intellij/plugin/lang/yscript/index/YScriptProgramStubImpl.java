package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptProgramStubImpl extends StubBase<YScriptProgram> implements YScriptProgramStub {

    private final String name;
    private final String packageName;
    private final ProgramArgument[] arguments;
    private final VariableType returnType;

    public YScriptProgramStubImpl(final StubElement parent, final String name, final String packageName, final ProgramArgument[] argumentTypes, final VariableType returnType) {
        super(parent, YScriptElementTypes.PROGRAM);
        this.name = name;
        this.packageName = packageName;
        this.arguments = argumentTypes;
        this.returnType = returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public ProgramArgument[] getArguments() {
        return arguments;
    }

    @Override
    public VariableType getReturnType() {
        return returnType;
    }

}
