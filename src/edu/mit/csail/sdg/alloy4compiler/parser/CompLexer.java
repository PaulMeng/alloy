

package edu.mit.csail.sdg.alloy4compiler.parser;

import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.ErrorSyntax;
import edu.mit.csail.sdg.alloy4.Pos;
import edu.mit.csail.sdg.alloy4compiler.ast.ExprConstant;
import java_cup_11a.runtime.*;

/** Autogenerated by JFlex 1.4.1 */


final class CompLexer implements java_cup_11a.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED =
    "\11\0\1\65\1\64\1\0\1\65\1\64\13\0\1\62\3\0\1\62"+
    "\2\0\1\65\1\1\1\62\1\2\1\63\1\0\1\3\1\62\1\4"+
    "\1\5\1\6\1\7\1\10\1\11\1\13\1\14\12\61\1\15\1\0"+
    "\1\16\1\17\1\12\1\0\1\20\10\63\1\56\21\63\1\21\1\0"+
    "\1\22\1\23\1\62\1\0\1\30\1\31\1\35\1\40\1\41\1\55"+
    "\1\60\1\43\1\45\1\46\1\44\1\36\1\50\1\37\1\47\1\54"+
    "\1\57\1\34\1\32\1\33\1\42\1\53\1\63\1\51\1\52\1\63"+
    "\1\24\1\25\1\26\1\27\uff81\0";

  /**
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\22\31\1\32\1\33\1\34\1\35\1\33\1\36"+
    "\1\37\1\40\1\14\1\0\1\41\1\42\1\43\1\44"+
    "\1\45\1\44\1\20\1\46\1\31\1\47\14\31\1\50"+
    "\5\31\1\51\3\31\1\46\11\31\1\52\1\53\1\0"+
    "\1\54\2\31\1\55\1\34\1\56\1\57\1\60\1\61"+
    "\1\62\2\31\1\63\1\31\1\64\1\31\1\2\6\31"+
    "\1\65\2\31\1\66\2\31\1\54\1\67\6\31\1\70"+
    "\1\71\1\72\2\31\1\73\1\74\1\31\1\75\1\76"+
    "\1\77\1\100\1\101\4\31\1\102\1\103\1\31\1\104"+
    "\1\31\1\105\1\106\1\31\1\107\2\31\1\110\12\31"+
    "\1\111\4\31\1\112\1\31\1\113\4\31\1\114\1\115"+
    "\1\31\1\45\1\31\1\116\1\117\1\77\3\31\1\105"+
    "\1\65";

  private static int [] zzUnpackAction() {
    int [] result = new int[199];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\66\0\66\0\66\0\154\0\66\0\66\0\66"+
    "\0\242\0\66\0\330\0\u010e\0\u0144\0\u017a\0\u01b0\0\u01e6"+
    "\0\u021c\0\66\0\66\0\66\0\66\0\66\0\u0252\0\66"+
    "\0\66\0\u0288\0\u02be\0\u02f4\0\u032a\0\u0360\0\u0396\0\u03cc"+
    "\0\u0402\0\u0438\0\u046e\0\u04a4\0\u04da\0\u0510\0\u0546\0\u057c"+
    "\0\u05b2\0\u05e8\0\u061e\0\u0654\0\66\0\66\0\66\0\u068a"+
    "\0\66\0\u06c0\0\66\0\66\0\u06f6\0\66\0\66\0\66"+
    "\0\u072c\0\66\0\66\0\66\0\66\0\u0762\0\u0798\0\u07ce"+
    "\0\u0804\0\u083a\0\u0870\0\u08a6\0\u08dc\0\u0912\0\u0948\0\u097e"+
    "\0\u09b4\0\u09ea\0\u0a20\0\u0a56\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e"+
    "\0\u0b64\0\u0b9a\0\u0bd0\0\u0c06\0\u0c3c\0\u04da\0\u0c72\0\u0ca8"+
    "\0\u0cde\0\u0d14\0\u0d4a\0\u0d80\0\u0db6\0\u0dec\0\u0e22\0\u0e58"+
    "\0\66\0\u0e8e\0\66\0\u0ec4\0\u0efa\0\u04da\0\u04da\0\u04da"+
    "\0\u04da\0\u04da\0\u04da\0\u04da\0\u0f30\0\u0f66\0\u04da\0\u0f9c"+
    "\0\u04da\0\u0fd2\0\u04da\0\u1008\0\u103e\0\u1074\0\u10aa\0\u10e0"+
    "\0\u1116\0\u114c\0\u1182\0\u11b8\0\u04da\0\u11ee\0\u1224\0\u04da"+
    "\0\u04da\0\u125a\0\u1290\0\u12c6\0\u12fc\0\u1332\0\u1368\0\u04da"+
    "\0\u04da\0\u04da\0\u139e\0\u13d4\0\u04da\0\u04da\0\u140a\0\u04da"+
    "\0\u04da\0\u1440\0\u04da\0\u04da\0\u1476\0\u14ac\0\u14e2\0\u1518"+
    "\0\u04da\0\u04da\0\u154e\0\u04da\0\u1584\0\u15ba\0\u04da\0\u15f0"+
    "\0\u04da\0\u1626\0\u165c\0\u04da\0\u1692\0\u16c8\0\u16fe\0\u1734"+
    "\0\u176a\0\u17a0\0\u17d6\0\u180c\0\u1842\0\u1878\0\u04da\0\u18ae"+
    "\0\u18e4\0\u191a\0\u1950\0\u04da\0\u1986\0\u04da\0\u19bc\0\u19f2"+
    "\0\u1a28\0\u1a5e\0\u04da\0\u04da\0\u1a94\0\u04da\0\u1aca\0\u04da"+
    "\0\u04da\0\u04da\0\u1b00\0\u1b36\0\u1b6c\0\u04da\0\u04da";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[199];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31"+
    "\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41"+
    "\1\42\1\43\1\44\2\45\1\46\1\45\1\47\1\50"+
    "\3\45\1\51\1\52\1\53\2\45\1\54\1\2\1\45"+
    "\2\55\71\0\1\56\71\0\1\57\67\0\1\60\1\61"+
    "\65\0\1\62\4\0\1\63\61\0\1\64\60\0\1\65"+
    "\5\0\1\60\63\0\1\66\2\0\1\64\65\0\1\67"+
    "\1\70\1\71\60\0\1\72\3\0\1\73\1\74\73\0"+
    "\1\75\70\0\1\45\1\76\1\77\3\45\1\100\1\101"+
    "\24\45\32\0\12\45\1\102\21\45\32\0\11\45\1\103"+
    "\1\104\2\45\1\105\1\45\1\106\14\45\32\0\13\45"+
    "\1\107\20\45\32\0\12\45\1\110\21\45\32\0\13\45"+
    "\1\111\20\45\32\0\11\45\1\112\5\45\1\113\14\45"+
    "\32\0\17\45\1\114\14\45\32\0\15\45\1\115\16\45"+
    "\32\0\6\45\1\116\1\117\11\45\1\120\12\45\32\0"+
    "\7\45\1\121\24\45\32\0\34\45\32\0\7\45\1\122"+
    "\1\123\7\45\1\124\4\45\1\125\6\45\32\0\4\45"+
    "\1\126\2\45\1\127\14\45\1\130\7\45\32\0\17\45"+
    "\1\131\14\45\32\0\1\132\3\45\1\133\27\45\32\0"+
    "\1\134\11\45\1\135\4\45\1\136\14\45\32\0\7\45"+
    "\1\137\24\45\32\0\31\140\1\54\2\140\2\0\64\60"+
    "\1\55\1\60\12\0\1\141\53\0\6\65\1\142\57\65"+
    "\12\0\1\143\103\0\2\45\1\144\31\45\32\0\2\45"+
    "\1\145\31\45\32\0\6\45\1\146\25\45\32\0\10\45"+
    "\1\147\23\45\32\0\3\45\1\150\30\45\32\0\3\45"+
    "\1\151\23\45\1\152\4\45\32\0\20\45\1\153\13\45"+
    "\32\0\30\45\1\154\3\45\32\0\20\45\1\155\13\45"+
    "\32\0\15\45\1\156\16\45\32\0\7\45\1\157\24\45"+
    "\32\0\11\45\1\160\22\45\32\0\3\45\1\161\30\45"+
    "\32\0\7\45\1\162\24\45\32\0\3\45\1\163\3\45"+
    "\1\164\24\45\32\0\2\45\1\165\31\45\32\0\2\45"+
    "\1\166\31\45\32\0\12\45\1\167\21\45\32\0\1\170"+
    "\2\45\1\171\7\45\1\172\10\45\1\173\7\45\32\0"+
    "\15\45\1\174\16\45\32\0\3\45\1\175\30\45\32\0"+
    "\11\45\1\176\22\45\32\0\24\45\1\177\7\45\32\0"+
    "\25\45\1\200\6\45\32\0\11\45\1\201\22\45\32\0"+
    "\11\45\1\202\22\45\32\0\10\45\1\203\23\45\32\0"+
    "\4\45\1\204\27\45\32\0\11\45\1\205\3\45\1\206"+
    "\16\45\32\0\5\45\1\207\26\45\32\0\7\45\1\210"+
    "\24\45\32\0\4\45\1\211\27\45\32\0\3\45\1\212"+
    "\30\45\32\0\34\140\2\0\6\65\1\142\5\65\1\55"+
    "\51\65\30\0\3\45\1\213\30\45\32\0\11\45\1\214"+
    "\22\45\32\0\11\45\1\215\22\45\32\0\2\45\1\216"+
    "\31\45\32\0\5\45\1\217\26\45\32\0\11\45\1\220"+
    "\22\45\32\0\11\45\1\221\22\45\32\0\16\45\1\222"+
    "\15\45\32\0\11\45\1\223\22\45\32\0\20\45\1\224"+
    "\13\45\32\0\5\45\1\225\26\45\32\0\11\45\1\226"+
    "\22\45\32\0\1\227\33\45\32\0\11\45\1\230\22\45"+
    "\32\0\23\45\1\231\10\45\32\0\7\45\1\232\24\45"+
    "\32\0\6\45\1\233\25\45\32\0\7\45\1\234\24\45"+
    "\32\0\12\45\1\235\21\45\32\0\3\45\1\236\30\45"+
    "\32\0\10\45\1\237\23\45\32\0\23\45\1\240\10\45"+
    "\32\0\3\45\1\241\30\45\32\0\4\45\1\242\27\45"+
    "\32\0\4\45\1\243\27\45\32\0\14\45\1\244\17\45"+
    "\32\0\17\45\1\245\14\45\32\0\3\45\1\246\30\45"+
    "\32\0\7\45\1\247\24\45\32\0\12\45\1\250\21\45"+
    "\32\0\5\45\1\251\26\45\32\0\15\45\1\252\16\45"+
    "\32\0\6\45\1\253\25\45\32\0\15\45\1\254\16\45"+
    "\32\0\1\255\33\45\32\0\1\256\33\45\32\0\3\45"+
    "\1\257\30\45\32\0\15\45\1\260\16\45\32\0\6\45"+
    "\1\261\25\45\32\0\10\45\1\262\23\45\32\0\2\45"+
    "\1\263\31\45\32\0\3\45\1\264\30\45\32\0\11\45"+
    "\1\265\22\45\32\0\11\45\1\266\22\45\32\0\3\45"+
    "\1\267\30\45\32\0\3\45\1\270\30\45\32\0\5\45"+
    "\1\271\26\45\32\0\7\45\1\272\24\45\32\0\22\45"+
    "\1\273\11\45\32\0\2\45\1\274\31\45\32\0\3\45"+
    "\1\275\30\45\32\0\2\45\1\276\31\45\32\0\15\45"+
    "\1\277\16\45\32\0\11\45\1\300\22\45\32\0\3\45"+
    "\1\301\30\45\32\0\3\45\1\302\30\45\32\0\15\45"+
    "\1\303\16\45\32\0\17\45\1\304\14\45\32\0\23\45"+
    "\1\305\10\45\32\0\7\45\1\306\24\45\32\0\11\45"+
    "\1\307\22\45\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[7074];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\3\11\1\1\3\11\1\1\1\11\7\1\5\11"+
    "\1\1\2\11\23\1\3\11\1\1\1\11\1\1\2\11"+
    "\1\0\3\11\1\1\4\11\43\1\1\11\1\0\1\11"+
    "\144\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[199];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */

  /**
   * the number of characters from the last newline up to the start of the
   * matched text
   */
  private int yycolumn;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
 public String alloy_filename="";
 public int alloy_lineoffset=0; // If not zero, it is added to the current LINE NUMBER
 public boolean alloy_dollar=false; // If true, we allow DOLLAR sign in identifiers
 private final Pos alloy_here(String txt) {
    return new Pos(alloy_filename,yycolumn+1,yyline+1+alloy_lineoffset,yycolumn+txt.length(),yyline+1);
 }
 private final Symbol alloy_sym(String txt, int type) {
    Pos p=alloy_here(txt); return new Symbol(type, p, p);
 }
 private final Symbol alloy_id(String txt) throws Err {
    Pos p=alloy_here(txt);
    if (!alloy_dollar) {
       int i=txt.indexOf('$');
       if (i==0) throw new ErrorSyntax(p,"The name cannot start with the $ symbol.");
       if (i>0) {
              if (i<txt.length()-1 && txt.indexOf('$', i+1) >= 0) throw new ErrorSyntax(p,"The name cannot contain two or more $ symbols.");
              char c=txt.charAt(i);
              if (c>='0' && c<='9') throw new ErrorSyntax(p,"The name cannot contain the $ symbol.");
       }
    }
    return new Symbol(CompSym.ID, p, new ExpName(p,txt));
 }
 private final Symbol alloy_num(String txt) throws Err {
    Pos p=alloy_here(txt);
    int n=0;
    try {
       n=Integer.parseInt(txt);
    } catch(NumberFormatException ex) {
       throw new ErrorSyntax(p, "The number "+txt+" is too large to be stored in a Java integer");
    }
    return new Symbol(CompSym.NUMBER, p, new ExpConstant(p, ExprConstant.Op.NUMBER, n));
 }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public CompLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public CompLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /**
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 150) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
yyline=yycolumn=0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup_11a.runtime.Symbol next_token() throws java.io.IOException,Err  {
    int zzInput;
    int zzAction;

    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = zzLexicalState;


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 4:
          { return alloy_sym(yytext(), CompSym.AMPERSAND   );
          }
        case 80: break;
        case 48:
          { return alloy_sym(yytext(), CompSym.SEQ         );
          }
        case 81: break;
        case 37:
          { return alloy_sym(yytext(), CompSym.IMPLIES     );
          }
        case 82: break;
        case 23:
          { return alloy_sym(yytext(), CompSym.RBRACE      );
          }
        case 83: break;
        case 76:
          { return alloy_sym(yytext(), CompSym.EXACTLY     );
          }
        case 84: break;
        case 51:
          { return alloy_sym(yytext(), CompSym.RUN         );
          }
        case 85: break;
        case 35:
          { return alloy_sym(yytext(), CompSym.SHL         );
          }
        case 86: break;
        case 34:
          { return alloy_sym(yytext(), CompSym.DOMAIN      );
          }
        case 87: break;
        case 58:
          { return alloy_sym(yytext(), CompSym.SIGINT      );
          }
        case 88: break;
        case 24:
          { return alloy_sym(yytext(), CompSym.TILDE       );
          }
        case 89: break;
        case 21:
          { return alloy_sym(yytext(), CompSym.LBRACE      );
          }
        case 90: break;
        case 7:
          { return alloy_sym(yytext(), CompSym.STAR        );
          }
        case 91: break;
        case 29:
          { return alloy_sym(yytext(), CompSym.PLUSPLUS    );
          }
        case 92: break;
        case 56:
          { return alloy_sym(yytext(), CompSym.FUN         );
          }
        case 93: break;
        case 53:
          { return alloy_sym(yytext(), CompSym.EXH         );
          }
        case 94: break;
        case 1:
          { throw new ErrorSyntax(alloy_here(" "),"The character "+yytext()+" cannot start a lexical token.");
          }
        case 95: break;
        case 54:
          { return alloy_sym(yytext(), CompSym.INT         );
          }
        case 96: break;
        case 50:
          { return alloy_sym(yytext(), CompSym.SIG         );
          }
        case 97: break;
        case 70:
          { return alloy_sym(yytext(), CompSym.PRED        );
          }
        case 98: break;
        case 25:
          { return alloy_id  (yytext());
          }
        case 99: break;
        case 61:
          { return alloy_sym(yytext(), CompSym.LONE        );
          }
        case 100: break;
        case 40:
          { return alloy_sym(yytext(), CompSym.NO          );
          }
        case 101: break;
        case 9:
          { return alloy_sym(yytext(), CompSym.COMMA       );
          }
        case 102: break;
        case 39:
          { return alloy_sym(yytext(), CompSym.AS          );
          }
        case 103: break;
        case 60:
          { return alloy_sym(yytext(), CompSym.THIS        );
          }
        case 104: break;
        case 13:
          { return alloy_sym(yytext(), CompSym.SLASH       );
          }
        case 105: break;
        case 46:
          { return alloy_sym(yytext(), CompSym.BUT         );
          }
        case 106: break;
        case 63:
          { return alloy_sym(yytext(), CompSym.DISJ        );
          }
        case 107: break;
        case 31:
          { return alloy_sym(yytext(), CompSym.SHA         );
          }
        case 108: break;
        case 38:
          { return alloy_sym(yytext(), CompSym.OR          );
          }
        case 109: break;
        case 43:
          { return alloy_sym(yytext(), CompSym.SHR         );
          }
        case 110: break;
        case 78:
          { return alloy_sym(yytext(), CompSym.PRIVATE     );
          }
        case 111: break;
        case 30:
          { return alloy_sym(yytext(), CompSym.ARROW       );
          }
        case 112: break;
        case 32:
          { return alloy_sym(yytext(), CompSym.GTE         );
          }
        case 113: break;
        case 20:
          { return alloy_sym(yytext(), CompSym.CARET       );
          }
        case 114: break;
        case 44:
          { return alloy_sym(yytext(), CompSym.IFF         );
          }
        case 115: break;
        case 64:
          { return alloy_sym(yytext(), CompSym.ELSE        );
          }
        case 116: break;
        case 3:
          { return alloy_sym(yytext(), CompSym.HASH        );
          }
        case 117: break;
        case 12:
          { return alloy_sym(yytext(), CompSym.DOT         );
          }
        case 118: break;
        case 22:
          { return alloy_sym(yytext(), CompSym.BAR         );
          }
        case 119: break;
        case 74:
          { return alloy_sym(yytext(), CompSym.EXPECT      );
          }
        case 120: break;
        case 8:
          { return alloy_sym(yytext(), CompSym.PLUS        );
          }
        case 121: break;
        case 36:
          { return alloy_sym(yytext(), CompSym.LTE         );
          }
        case 122: break;
        case 55:
          { return alloy_sym(yytext(), CompSym.ONE         );
          }
        case 123: break;
        case 68:
          { return alloy_sym(yytext(), CompSym.OPEN        );
          }
        case 124: break;
        case 11:
          { return alloy_sym(yytext(), CompSym.GT          );
          }
        case 125: break;
        case 75:
          { return alloy_sym(yytext(), CompSym.MODULE      );
          }
        case 126: break;
        case 14:
          { return alloy_sym(yytext(), CompSym.COLON       );
          }
        case 127: break;
        case 49:
          { return alloy_sym(yytext(), CompSym.SUM         );
          }
        case 128: break;
        case 69:
          { return alloy_sym(yytext(), CompSym.PART        );
          }
        case 129: break;
        case 16:
          { return alloy_sym(yytext(), CompSym.EQUALS      );
          }
        case 130: break;
        case 77:
          { return alloy_sym(yytext(), CompSym.EXTENDS     );
          }
        case 131: break;
        case 57:
          { return alloy_sym(yytext(), CompSym.FOR         );
          }
        case 132: break;
        case 26:
          { return alloy_num (yytext());
          }
        case 133: break;
        case 67:
          { return alloy_sym(yytext(), CompSym.IDEN        );
          }
        case 134: break;
        case 15:
          { return alloy_sym(yytext(), CompSym.LT          );
          }
        case 135: break;
        case 17:
          { return alloy_sym(yytext(), CompSym.AT          );
          }
        case 136: break;
        case 33:
          { return alloy_sym(yytext(), CompSym.RANGE       );
          }
        case 137: break;
        case 66:
          { return alloy_sym(yytext(), CompSym.UNIV        );
          }
        case 138: break;
        case 2:
          { return alloy_sym(yytext(), CompSym.NOT         );
          }
        case 139: break;
        case 47:
          { return alloy_sym(yytext(), CompSym.SET         );
          }
        case 140: break;
        case 6:
          { return alloy_sym(yytext(), CompSym.RPAREN      );
          }
        case 141: break;
        case 41:
          { return alloy_sym(yytext(), CompSym.IN          );
          }
        case 142: break;
        case 72:
          { return alloy_sym(yytext(), CompSym.CHECK       );
          }
        case 143: break;
        case 28:
          { return alloy_sym(yytext(), CompSym.AND         );
          }
        case 144: break;
        case 59:
          { return alloy_sym(yytext(), CompSym.SOME        );
          }
        case 145: break;
        case 71:
          { return alloy_sym(yytext(), CompSym.FACT        );
          }
        case 146: break;
        case 10:
          { return alloy_sym(yytext(), CompSym.MINUS       );
          }
        case 147: break;
        case 5:
          { return alloy_sym(yytext(), CompSym.LPAREN      );
          }
        case 148: break;
        case 62:
          { return alloy_sym(yytext(), CompSym.NONE        );
          }
        case 149: break;
        case 18:
          { return alloy_sym(yytext(), CompSym.LBRACKET    );
          }
        case 150: break;
        case 19:
          { return alloy_sym(yytext(), CompSym.RBRACKET    );
          }
        case 151: break;
        case 65:
          { return alloy_sym(yytext(), CompSym.ENUM        );
          }
        case 152: break;
        case 45:
          { return alloy_sym(yytext(), CompSym.ALL         );
          }
        case 153: break;
        case 79:
          { return alloy_sym(yytext(), CompSym.ABSTRACT    );
          }
        case 154: break;
        case 73:
          { return alloy_sym(yytext(), CompSym.ASSERT      );
          }
        case 155: break;
        case 52:
          { return alloy_sym(yytext(), CompSym.LET         );
          }
        case 156: break;
        case 27:
          {
          }
        case 157: break;
        case 42:
          { throw new ErrorSyntax(alloy_here(yytext()),"Name cannot start with a number.");
          }
        case 158: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {   return new Symbol(CompSym.EOF, alloy_here(" "), alloy_here(" "));
 }
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
