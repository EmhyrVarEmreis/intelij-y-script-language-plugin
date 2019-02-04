package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import java.util.Objects;

import static com.intellij.codeInsight.completion.CompletionUtil.DUMMY_IDENTIFIER_TRIMMED;

public class YScriptCompletionProviderHelper {

    public static final InsertHandler<LookupElement> STRUCTURAL_ITEM_HANDLER = (context, item) -> {
        context.setAddCompletionChar(false);
        EditorModificationUtil.insertStringAtCaret(context.getEditor(), ";");
        context.commitDocument();
    };

    public static String getTextUntil(PsiElement element, IElementType to) {
        ASTNode node = element.getNode();
        final StringBuilder txt = new StringBuilder();
        do {
            if (!TokenType.WHITE_SPACE.equals(node.getElementType())) {
                txt.insert(0, node.getText());
            }
        }
        while (Objects.nonNull(node = node.getTreePrev()) && !(to.equals(node.getElementType())));
        return txt.toString().trim().replaceFirst(DUMMY_IDENTIFIER_TRIMMED,"");
    }

}
