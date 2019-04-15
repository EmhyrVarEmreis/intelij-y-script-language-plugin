package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes.*;

%%

%{
  public _YScriptLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _YScriptLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

X_ASSIGN=\??=
V_STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
V_NUMBER=-?[0-9]+(\.[0-9]+)?
SPACE=[ \t\n\x0B\f\r]+
COMMENT=\/*([^*]|(\*+[^*\/]))*\*+\/
IDENTIFIER=[:letter:][a-zA-Z_0-9]*

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "."                { return X_DOT; }
  ","                { return X_COMMA; }
  ":"                { return X_COLON; }
  ";"                { return X_SEMICOLON; }
  "::"               { return X_DOUBLE_COLON; }
  "("                { return X_OPEN_BRACKET; }
  ")"                { return X_CLOSE_BRACKET; }
  "["                { return X_OPEN_SQUARE_BRACKET; }
  "]"                { return X_CLOSE_SQUARE_BRACKET; }
  "IMPORT"           { return KEY_IMPORT; }
  "PROGRAM"          { return KEY_PROGRAM; }
  "DEFINE"           { return KEY_DEFINE; }
  "EXTENSION"        { return KEY_EXTENSION; }
  "EXTERNAL"         { return KEY_EXTERNAL; }
  "VAR"              { return KEY_VAR; }
  "AS"               { return KEY_AS; }
  "BEGIN"            { return KEY_BEGIN; }
  "END"              { return KEY_END; }
  "RETURN"           { return KEY_RETURN; }
  "RETURNS"          { return KEY_RETURNS; }
  "ARRAY"            { return KEY_ARRAY; }
  "OF"               { return KEY_OF; }
  "IF"               { return KEY_IF; }
  "THEN"             { return KEY_THEN; }
  "ELSE"             { return KEY_ELSE; }
  "WITH"             { return KEY_WITH; }
  "DO"               { return KEY_DO; }
  "WHILE"            { return KEY_WHILE; }
  "CREATE"           { return KEY_CREATE; }
  "SORT"             { return KEY_SORT; }
  "INDEX"            { return KEY_INDEX; }
  "USING"            { return KEY_USING; }
  "DELETE"           { return KEY_DELETE; }
  "TRY"              { return KEY_TRY; }
  "CATCH"            { return KEY_CATCH; }
  "THROW"            { return KEY_THROW; }
  "NEW"              { return KEY_NEW; }
  "FOR"              { return KEY_FOR; }
  "TO"               { return KEY_TO; }
  "MERGE"            { return KEY_MERGE; }
  "AND"              { return KEY_AND; }
  "OR"               { return KEY_OR; }
  "NOT"              { return KEY_NEGATION; }
  "TRUE"             { return KEY_TRUE; }
  "FALSE"            { return KEY_FALSE; }
  "NULL"             { return KEY_NULL; }
  "ISINSTANCE"       { return F_KEY_IS_INSTANCE; }
  "ISTYPE"           { return F_KEY_IS_TYPE; }
  "SIZEOF"           { return F_KEY_SIZE_OF; }
  "ISSET"            { return F_KEY_IS_SET; }
  "COPYOF"           { return F_KEY_COPY_OF; }
  "ISNULL"           { return F_KEY_IS_NULL; }
  "TYPE"             { return F_KEY_TYPE; }
  "STRLEN"           { return F_KEY_STRLEN; }
  "<"                { return OP_LT; }
  ">"                { return OP_GT; }
  "<="               { return OP_LTE; }
  ">="               { return OP_GTE; }
  "+"                { return OP_PLUS; }
  "-"                { return OP_MINUS; }
  "*"                { return OP_MUL; }
  "/"                { return OP_DIVIDE; }
  "%"                { return OP_MODULO; }
  "=="               { return OP_EQIALS; }
  "<>"               { return OP_NEQUALS; }

  {X_ASSIGN}         { return X_ASSIGN; }
  {V_STRING}         { return V_STRING; }
  {V_NUMBER}         { return V_NUMBER; }
  {SPACE}            { return SPACE; }
  {COMMENT}          { return COMMENT; }
  {IDENTIFIER}       { return IDENTIFIER; }

}

[^] { return BAD_CHARACTER; }
