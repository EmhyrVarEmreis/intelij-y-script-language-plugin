package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFile;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;

public class YScriptFileContentStubImpl extends StubBase<YScriptFileContent> implements YScriptFileContentStub {

    private final String packageName;

    public YScriptFileContentStubImpl(final StubElement parent, final String packageName) {
        super(parent, YScriptElementTypes.FILE_CONTENT);
        this.packageName = packageName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

}
