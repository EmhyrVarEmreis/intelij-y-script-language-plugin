package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptProgramStubElementImpl extends StubBase<YScriptProgram> implements YScriptProgramStubElement {

    private final String packageName;

    public YScriptProgramStubElementImpl(final StubElement parent, final String packageName) {
        super(parent, YScriptElementTypes.PROGRAM);
        this.packageName = packageName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

}
