package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.StubElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;

public interface YScriptFileContentStub extends StubElement<YScriptFileContent> {

    String getPackageName();

}
