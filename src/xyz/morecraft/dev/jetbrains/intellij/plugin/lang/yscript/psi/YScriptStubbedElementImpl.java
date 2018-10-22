package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;

public class YScriptStubbedElementImpl<T extends StubElement> extends StubBasedPsiElementBase<T> {

    public YScriptStubbedElementImpl(final T stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public YScriptStubbedElementImpl(final ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public Language getLanguage() {
        return YScript.INSTANCE;
    }

}
