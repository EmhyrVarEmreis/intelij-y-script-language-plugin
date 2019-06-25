package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.parser.YScriptParser;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFile;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

public class YScriptParserDefinition implements ParserDefinition {

    public static final TokenSet NO_SPACES = TokenSet.create(YScriptTypes.X_DOUBLE_COLON, YScriptTypes.X_DOT);
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(YScriptTypes.COMMENT);

    public static final IStubFileElementType FILE = new IStubYScriptFileElementType();

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new YScriptLexerAdapter();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new YScriptParser();
    }

    @Override
    public IStubFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new YScriptFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        if (NO_SPACES.contains(left.getElementType()) || NO_SPACES.contains(right.getElementType())) {
            return SpaceRequirements.MUST_NOT;
        }
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return YScriptTypes.Factory.createElement(node);
    }

    public static class IStubYScriptFileElementType extends IStubFileElementType {

        IStubYScriptFileElementType() {
            super(YScript.INSTANCE);
        }

        @Override
        public int getStubVersion() {
            return 667;
        }

    }

}
