package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptProgramStubImpl extends StubBase<YScriptProgram> implements YScriptProgramStub {

    private final String packageName;

    public YScriptProgramStubImpl(final StubElement parent, final String packageName) {
        super(parent, YScriptElementTypes.PROGRAM);
        this.packageName = packageName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

}
