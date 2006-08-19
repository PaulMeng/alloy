package edu.mit.csail.sdg.alloy4;

import java_cup.runtime.*;

/** Autogenerated by JFlex 1.4.1 */

%%

// There are 3 sets of "special tokens" that the lexer will not output.
// But the Parser expects them.
// So a special Filter class is written that sits between Lexer and Parser.
// The Filter class observes the stream of tokens, and intelligently
// merges or changes some primitive tokens into special tokens.
// For more details, refer to the main documentation.
//
// But, very briefly, here are the 3 groups:
//
// (1) The lexer will generate only ALL, NO, LONE, ONE, SUM, SOME.
// It will not output ALL2, NO2, LONE2, ONE2, SUM2, SOME2.
// (The Filter class will change some ONE into ONE2, etc)
//
// (2) The lexer won't output NOTEQUALS, NOTIN, NOTLT, NOTLTE, NOTGT, NOTGTE.
// Instead it outputs them as separate tokens (eg. "NOT" "EQUALS").
// (The Filter class is used to merge them into a single "NOTEQUALS" token)
//
// (3) The lexer willn't output the 15 special arrows (eg. ONE_ARROW_ONE)
// Instead it outputs them as separate tokens (eg. "ONE", "ARROW", "ONE")
// (The Filter class is used to merge them into a single "ONE_ARROW_ONE" token)

%class AlloyLexer // The ordering of these directives is important
%cupsym AlloySym
%cup
%public
%final
%unicode
%line
%column
%pack

%{
 public String alloy_filename="";
 private final Pos alloy_here()               { return new Pos(alloy_filename,yycolumn+1,yyline+1); }
 private final Symbol alloy_sym(int type)     { return new Symbol(type, yycolumn+1, yyline+1, alloy_here()); }
 private final Symbol alloy_id(String value)  { return new Symbol(AlloySym.ID, yycolumn+1, yyline+1, new ExprName(alloy_here(),value)); }
 private final Symbol alloy_num(String value) { return new Symbol(AlloySym.NUMBER, yycolumn+1, yyline+1, ExprConstant.Op.NUMBER.make(alloy_here(),value)); }
 //private final Symbol alloy_str(String value) {
 //  value=value.substring(1, value.length()-1);
 //  return new Symbol(AlloySym.STRING, yycolumn+1, yyline+1, value);
 //}
%}

%%

"!"                   { return alloy_sym(AlloySym.NOT         );}
"#"                   { return alloy_sym(AlloySym.HASH        );}
"$"                   { return alloy_sym(AlloySym.DOLLAR      );}
"&"                   { return alloy_sym(AlloySym.AMPERSAND   );}
"&&"                  { return alloy_sym(AlloySym.AND         );}
"("                   { return alloy_sym(AlloySym.LPAREN      );}
")"                   { return alloy_sym(AlloySym.RPAREN      );}
"*"                   { return alloy_sym(AlloySym.STAR        );}
"+"                   { return alloy_sym(AlloySym.PLUS        );}
"++"                  { return alloy_sym(AlloySym.PLUSPLUS    );}
","                   { return alloy_sym(AlloySym.COMMA       );}
"-"                   { return alloy_sym(AlloySym.MINUS       );}
"->"                  { return alloy_sym(AlloySym.ARROW       );}
"."                   { return alloy_sym(AlloySym.DOT         );}
".."                  { return alloy_sym(AlloySym.DOT         );}
"/"                   { return alloy_sym(AlloySym.SLASH       );}
":"                   { return alloy_sym(AlloySym.COLON       );}
"::"                  { return alloy_sym(AlloySym.DOT         );}
":>"                  { return alloy_sym(AlloySym.RANGE       );}
"<"                   { return alloy_sym(AlloySym.LT          );}
"<:"                  { return alloy_sym(AlloySym.DOMAIN      );}
"=<"                  { return alloy_sym(AlloySym.LTE         );}
"<=>"                 { return alloy_sym(AlloySym.IFF         );}
"="                   { return alloy_sym(AlloySym.EQUALS      );}
"=="                  { return alloy_sym(AlloySym.EQUALS      );}
"=>"                  { return alloy_sym(AlloySym.IMPLIES     );}
">"                   { return alloy_sym(AlloySym.GT          );}
">="                  { return alloy_sym(AlloySym.GTE         );}
"@"                   { return alloy_sym(AlloySym.AT          );}
"["                   { return alloy_sym(AlloySym.LBRACKET    );}
"]"                   { return alloy_sym(AlloySym.RBRACKET    );}
"^"                   { return alloy_sym(AlloySym.CARET       );}
"{"                   { return alloy_sym(AlloySym.LBRACE      );}
"|"                   { return alloy_sym(AlloySym.BAR         );}
"||"                  { return alloy_sym(AlloySym.OR          );}
"}"                   { return alloy_sym(AlloySym.RBRACE      );}
"~"                   { return alloy_sym(AlloySym.TILDE       );}
"abstract"            { return alloy_sym(AlloySym.ABSTRACT    );}
"all"                 { return alloy_sym(AlloySym.ALL         );}
"and"                 { return alloy_sym(AlloySym.AND         );}
"as"                  { return alloy_sym(AlloySym.AS          );}
"assert"              { return alloy_sym(AlloySym.ASSERT      );}
"but"                 { return alloy_sym(AlloySym.BUT         );}
"check"               { return alloy_sym(AlloySym.CHECK       );}
"disj"                { return alloy_sym(AlloySym.DISJ        );}
"disjoint"            { return alloy_sym(AlloySym.DISJ        );}
"else"                { return alloy_sym(AlloySym.ELSE        );}
"exactly"             { return alloy_sym(AlloySym.EXACTLY     );}
"exh"                 { return alloy_sym(AlloySym.EXH         );}
"exhaustive"          { return alloy_sym(AlloySym.EXH         );}
"expect"              { return alloy_sym(AlloySym.EXPECT      );}
"extends"             { return alloy_sym(AlloySym.EXTENDS     );}
"fact"                { return alloy_sym(AlloySym.FACT        );}
"for"                 { return alloy_sym(AlloySym.FOR         );}
"fun"                 { return alloy_sym(AlloySym.FUN         );}
"iden"                { return alloy_sym(AlloySym.IDEN        );}
"iff"                 { return alloy_sym(AlloySym.IFF         );}
"implies"             { return alloy_sym(AlloySym.IMPLIES     );}
"in"                  { return alloy_sym(AlloySym.IN          );}
"int"                 { return alloy_sym(AlloySym.INT         );}
"Int"                 { return alloy_sym(AlloySym.SIGINT      );}
"let"                 { return alloy_sym(AlloySym.LET         );}
"lone"                { return alloy_sym(AlloySym.LONE        );}
"module"              { return alloy_sym(AlloySym.MODULE      );}
"no"                  { return alloy_sym(AlloySym.NO          );}
"none"                { return alloy_sym(AlloySym.NONE        );}
"not"                 { return alloy_sym(AlloySym.NOT         );}
"one"                 { return alloy_sym(AlloySym.ONE         );}
"open"                { return alloy_sym(AlloySym.OPEN        );}
"or"                  { return alloy_sym(AlloySym.OR          );}
"part"                { return alloy_sym(AlloySym.PART        );}
"partition"           { return alloy_sym(AlloySym.PART        );}
"pred"                { return alloy_sym(AlloySym.PRED        );}
"run"                 { return alloy_sym(AlloySym.RUN         );}
"set"                 { return alloy_sym(AlloySym.SET         );}
"sig"                 { return alloy_sym(AlloySym.SIG         );}
"some"                { return alloy_sym(AlloySym.SOME        );}
"sum"                 { return alloy_sym(AlloySym.SUM         );}
"then"                { return alloy_sym(AlloySym.IMPLIES     );}
"this"                { return alloy_sym(AlloySym.THIS        );}
"univ"                { return alloy_sym(AlloySym.UNIV        );}

[0-9][0-9]*[a-zA-Z_\'\"\u0019\u001d]   { throw new ErrorSyntax(alloy_here(),"An identifier cannot start with a digit!"); }
[0-9][0-9]*                            { return alloy_num (yytext()); }
[a-zA-Z][0-9a-zA-Z_\'\"\u0019\u001d]*  { return alloy_id  (yytext()); }

// \" [^\r\n\"]* \"      { return alloy_str(yytext()); }
//
// \" [^\r\n\"]*         { throw new ErrorSyntax(alloy_here(),"The string constant here is not properly terminated by a double quote character"); }
//                       // This rule is shorter than the previous rule, so it
//                       // will only apply if the string is not properly closed.

"/*" ~"*/"            { }

("//"|"--") [^\r\n]* [\r\n]  { }

("//"|"--") [^\r\n]*         { } // This rule is shorter than the previous rule,
                                 // so it will only apply if the last line
                                 // is missing the \n or \r character.

[ \t\f\r\n]           { }

. { throw new ErrorSyntax(alloy_here(),"The character \'"+yytext()+"\' cannot start a lexical token!"); }
