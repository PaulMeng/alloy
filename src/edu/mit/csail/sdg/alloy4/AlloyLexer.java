/* The following code was generated by JFlex 1.4.1 on 8/16/06 12:46 AM */

package edu.mit.csail.sdg.alloy4;

import java_cup.runtime.*;


/**
 * This class is auto-generated by JFlex 1.4.1
 */
public final class AlloyLexer implements java_cup.runtime.Scanner {

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
    "\2\0\1\65\1\1\1\62\1\2\1\3\1\0\1\4\1\62\1\5"+
    "\1\6\1\7\1\10\1\11\1\12\1\14\1\15\12\61\1\16\1\0"+
    "\1\17\1\20\1\13\1\0\1\21\10\63\1\57\21\63\1\22\1\0"+
    "\1\23\1\24\1\62\1\0\1\31\1\32\1\36\1\41\1\42\1\55"+
    "\1\60\1\44\1\46\1\47\1\45\1\37\1\56\1\40\1\50\1\54"+
    "\1\63\1\35\1\33\1\34\1\43\1\53\1\63\1\51\1\52\1\63"+
    "\1\25\1\26\1\27\1\30\uff81\0";

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
    "\1\30\1\31\22\32\1\33\1\34\1\35\1\36\1\34"+
    "\1\37\1\40\1\15\1\0\1\41\1\42\1\0\1\43"+
    "\1\44\1\21\1\45\1\32\1\46\14\32\1\47\4\32"+
    "\1\50\3\32\1\45\11\32\1\51\1\0\1\52\2\32"+
    "\1\53\1\35\1\54\1\55\1\56\1\57\3\32\1\60"+
    "\1\32\1\61\1\32\1\2\5\32\1\62\2\32\1\63"+
    "\1\32\1\52\1\32\1\64\4\32\1\65\1\66\1\32"+
    "\1\67\2\32\1\70\1\43\1\71\1\32\1\72\1\73"+
    "\1\74\1\75\4\32\1\76\1\77\1\32\1\100\1\101"+
    "\1\102\1\103\3\32\1\104\11\32\1\105\4\32\1\106"+
    "\2\32\1\107\2\32\1\110\1\111\2\32\1\112\1\74"+
    "\3\32\1\101\1\62";

  private static int [] zzUnpackAction() {
    int [] result = new int[189];
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
    "\0\0\0\66\0\66\0\66\0\66\0\154\0\66\0\66"+
    "\0\66\0\242\0\66\0\330\0\u010e\0\u0144\0\u017a\0\u01b0"+
    "\0\u01e6\0\u021c\0\66\0\66\0\66\0\66\0\66\0\u0252"+
    "\0\66\0\66\0\u0288\0\u02be\0\u02f4\0\u032a\0\u0360\0\u0396"+
    "\0\u03cc\0\u0402\0\u0438\0\u046e\0\u04a4\0\u04da\0\u0510\0\u0546"+
    "\0\u057c\0\u05b2\0\u05e8\0\u061e\0\u0654\0\66\0\66\0\66"+
    "\0\u068a\0\66\0\66\0\66\0\u06c0\0\66\0\66\0\u06f6"+
    "\0\66\0\66\0\66\0\66\0\u072c\0\u0762\0\u0798\0\u07ce"+
    "\0\u0804\0\u083a\0\u0870\0\u08a6\0\u08dc\0\u0912\0\u0948\0\u097e"+
    "\0\u09b4\0\u09ea\0\u0a20\0\u0a56\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e"+
    "\0\u0b64\0\u0b9a\0\u0bd0\0\u04da\0\u0c06\0\u0c3c\0\u0c72\0\u0ca8"+
    "\0\u0cde\0\u0d14\0\u0d4a\0\u0d80\0\u0db6\0\66\0\u0dec\0\66"+
    "\0\u0e22\0\u0e58\0\u04da\0\u04da\0\u04da\0\u04da\0\u04da\0\u04da"+
    "\0\u0e8e\0\u0ec4\0\u0efa\0\u04da\0\u0f30\0\u04da\0\u0f66\0\u04da"+
    "\0\u0f9c\0\u0fd2\0\u1008\0\u103e\0\u1074\0\u10aa\0\u10e0\0\u1116"+
    "\0\u04da\0\u114c\0\u04da\0\u1182\0\u04da\0\u11b8\0\u11ee\0\u1224"+
    "\0\u125a\0\u04da\0\u04da\0\u1290\0\u04da\0\u12c6\0\u12fc\0\u04da"+
    "\0\u04da\0\u04da\0\u1332\0\u04da\0\u04da\0\u1368\0\u04da\0\u139e"+
    "\0\u13d4\0\u140a\0\u1440\0\u04da\0\u04da\0\u1476\0\u04da\0\u14ac"+
    "\0\u04da\0\u04da\0\u14e2\0\u1518\0\u154e\0\u04da\0\u1584\0\u15ba"+
    "\0\u15f0\0\u1626\0\u165c\0\u1692\0\u16c8\0\u16fe\0\u1734\0\u04da"+
    "\0\u176a\0\u17a0\0\u17d6\0\u180c\0\u04da\0\u1842\0\u1878\0\u04da"+
    "\0\u18ae\0\u18e4\0\u04da\0\u04da\0\u191a\0\u1950\0\u04da\0\u04da"+
    "\0\u1986\0\u19bc\0\u19f2\0\u04da\0\u04da";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[189];
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
    "\1\42\1\43\1\44\1\45\2\46\1\47\1\46\1\50"+
    "\3\46\1\51\1\52\1\53\1\54\1\46\1\55\1\2"+
    "\1\46\2\56\72\0\1\57\71\0\1\60\67\0\1\61"+
    "\1\62\72\0\1\63\61\0\1\64\60\0\1\65\5\0"+
    "\1\61\63\0\1\66\2\0\1\64\65\0\1\67\1\0"+
    "\1\70\60\0\1\71\3\0\1\72\1\73\73\0\1\74"+
    "\70\0\1\46\1\75\1\76\3\46\1\77\1\100\23\46"+
    "\33\0\12\46\1\101\20\46\33\0\11\46\1\102\1\103"+
    "\2\46\1\104\1\46\1\105\13\46\33\0\13\46\1\106"+
    "\17\46\33\0\12\46\1\107\20\46\33\0\13\46\1\110"+
    "\17\46\33\0\11\46\1\111\5\46\1\112\13\46\33\0"+
    "\17\46\1\113\13\46\33\0\15\46\1\114\15\46\33\0"+
    "\6\46\1\115\11\46\1\116\12\46\33\0\7\46\1\117"+
    "\23\46\33\0\33\46\33\0\7\46\1\120\1\121\13\46"+
    "\1\122\1\123\5\46\33\0\4\46\1\124\2\46\1\125"+
    "\13\46\1\126\7\46\33\0\1\127\3\46\1\130\26\46"+
    "\33\0\1\131\11\46\1\132\4\46\1\133\13\46\33\0"+
    "\17\46\1\134\13\46\33\0\7\46\1\135\23\46\33\0"+
    "\30\136\1\55\2\136\2\0\64\61\1\56\1\61\7\65"+
    "\1\137\56\65\13\0\1\140\103\0\2\46\1\141\30\46"+
    "\33\0\2\46\1\142\30\46\33\0\6\46\1\143\24\46"+
    "\33\0\10\46\1\144\22\46\33\0\3\46\1\145\27\46"+
    "\33\0\3\46\1\146\27\46\33\0\25\46\1\147\5\46"+
    "\33\0\27\46\1\150\3\46\33\0\25\46\1\151\5\46"+
    "\33\0\11\46\1\152\3\46\1\153\15\46\33\0\7\46"+
    "\1\154\23\46\33\0\11\46\1\155\21\46\33\0\3\46"+
    "\1\156\27\46\33\0\7\46\1\157\23\46\33\0\3\46"+
    "\1\160\3\46\1\161\23\46\33\0\2\46\1\162\30\46"+
    "\33\0\2\46\1\163\30\46\33\0\1\164\2\46\1\165"+
    "\7\46\1\166\7\46\1\167\7\46\33\0\15\46\1\170"+
    "\15\46\33\0\3\46\1\171\27\46\33\0\11\46\1\172"+
    "\21\46\33\0\24\46\1\173\6\46\33\0\23\46\1\174"+
    "\7\46\33\0\11\46\1\175\21\46\33\0\11\46\1\176"+
    "\21\46\33\0\4\46\1\177\26\46\33\0\11\46\1\200"+
    "\21\46\33\0\5\46\1\201\25\46\33\0\7\46\1\202"+
    "\23\46\33\0\4\46\1\203\26\46\33\0\10\46\1\204"+
    "\22\46\33\0\3\46\1\205\27\46\2\0\7\65\1\137"+
    "\5\65\1\56\50\65\31\0\3\46\1\206\27\46\33\0"+
    "\11\46\1\207\21\46\33\0\11\46\1\210\21\46\33\0"+
    "\7\46\1\211\23\46\33\0\2\46\1\212\30\46\33\0"+
    "\5\46\1\213\25\46\33\0\11\46\1\214\21\46\33\0"+
    "\11\46\1\215\21\46\33\0\16\46\1\216\14\46\33\0"+
    "\11\46\1\217\21\46\33\0\5\46\1\220\25\46\33\0"+
    "\11\46\1\221\21\46\33\0\1\222\32\46\33\0\11\46"+
    "\1\223\21\46\33\0\22\46\1\224\10\46\33\0\7\46"+
    "\1\225\23\46\33\0\6\46\1\226\24\46\33\0\7\46"+
    "\1\227\23\46\33\0\3\46\1\230\27\46\33\0\10\46"+
    "\1\231\22\46\33\0\3\46\1\232\27\46\33\0\12\46"+
    "\1\233\20\46\33\0\4\46\1\234\26\46\33\0\4\46"+
    "\1\235\26\46\33\0\14\46\1\236\16\46\33\0\17\46"+
    "\1\237\13\46\33\0\3\46\1\240\27\46\33\0\7\46"+
    "\1\241\23\46\33\0\12\46\1\242\20\46\33\0\5\46"+
    "\1\243\25\46\33\0\15\46\1\244\15\46\33\0\15\46"+
    "\1\245\15\46\33\0\6\46\1\246\24\46\33\0\1\247"+
    "\32\46\33\0\3\46\1\250\27\46\33\0\15\46\1\251"+
    "\15\46\33\0\6\46\1\252\24\46\33\0\10\46\1\253"+
    "\22\46\33\0\2\46\1\254\30\46\33\0\3\46\1\255"+
    "\27\46\33\0\11\46\1\256\21\46\33\0\3\46\1\257"+
    "\27\46\33\0\11\46\1\260\21\46\33\0\5\46\1\261"+
    "\25\46\33\0\7\46\1\262\23\46\33\0\21\46\1\263"+
    "\11\46\33\0\2\46\1\264\30\46\33\0\3\46\1\265"+
    "\27\46\33\0\2\46\1\211\30\46\33\0\15\46\1\266"+
    "\15\46\33\0\3\46\1\267\27\46\33\0\3\46\1\270"+
    "\27\46\33\0\15\46\1\271\15\46\33\0\17\46\1\272"+
    "\13\46\33\0\22\46\1\273\10\46\33\0\7\46\1\274"+
    "\23\46\33\0\11\46\1\275\21\46\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6696];
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
    "\1\0\4\11\1\1\3\11\1\1\1\11\7\1\5\11"+
    "\1\1\2\11\23\1\3\11\1\1\3\11\1\0\2\11"+
    "\1\0\4\11\41\1\1\11\1\0\1\11\135\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[189];
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

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
 public String alloy_filename="";
 private final Pos alloy_here()               { return new Pos(alloy_filename,yycolumn+1,yyline+1); }
 private final Symbol alloy_sym(int type)     { return new Symbol(type, yycolumn+1, yyline+1, alloy_here()); }
 private final Symbol alloy_id(String value)  { return new Symbol(AlloySym.ID, yycolumn+1, yyline+1, new ExprName(alloy_here(),value)); }
 private final Symbol alloy_num(String value) { return new Symbol(AlloySym.NUMBER, yycolumn+1, yyline+1, new ExprNumber(alloy_here(),value)); }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public AlloyLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public AlloyLexer(java.io.InputStream in) {
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
    yyline = yycolumn = 0;
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
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
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
        // peek one character ahead if it is \n (if we have counted one line too much)
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
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
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

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 58: 
          { return alloy_sym(AlloySym.LONE        );
          }
        case 75: break;
        case 39: 
          { return alloy_sym(AlloySym.NO          );
          }
        case 76: break;
        case 10: 
          { return alloy_sym(AlloySym.COMMA       );
          }
        case 77: break;
        case 38: 
          { return alloy_sym(AlloySym.AS          );
          }
        case 78: break;
        case 57: 
          { return alloy_sym(AlloySym.THIS        );
          }
        case 79: break;
        case 14: 
          { return alloy_sym(AlloySym.SLASH       );
          }
        case 80: break;
        case 44: 
          { return alloy_sym(AlloySym.BUT         );
          }
        case 81: break;
        case 60: 
          { return alloy_sym(AlloySym.DISJ        );
          }
        case 82: break;
        case 37: 
          { return alloy_sym(AlloySym.OR          );
          }
        case 83: break;
        case 31: 
          { return alloy_sym(AlloySym.ARROW       );
          }
        case 84: break;
        case 32: 
          { return alloy_sym(AlloySym.GTE         );
          }
        case 85: break;
        case 21: 
          { return alloy_sym(AlloySym.CARET       );
          }
        case 86: break;
        case 42: 
          { return alloy_sym(AlloySym.IFF         );
          }
        case 87: break;
        case 61: 
          { return alloy_sym(AlloySym.ELSE        );
          }
        case 88: break;
        case 3: 
          { return alloy_sym(AlloySym.HASH        );
          }
        case 89: break;
        case 13: 
          { return alloy_sym(AlloySym.DOT         );
          }
        case 90: break;
        case 23: 
          { return alloy_sym(AlloySym.BAR         );
          }
        case 91: break;
        case 70: 
          { return alloy_sym(AlloySym.EXPECT      );
          }
        case 92: break;
        case 9: 
          { return alloy_sym(AlloySym.PLUS        );
          }
        case 93: break;
        case 36: 
          { return alloy_sym(AlloySym.LTE         );
          }
        case 94: break;
        case 52: 
          { return alloy_sym(AlloySym.ONE         );
          }
        case 95: break;
        case 64: 
          { return alloy_sym(AlloySym.OPEN        );
          }
        case 96: break;
        case 26: 
          { return alloy_id  (yytext());
          }
        case 97: break;
        case 12: 
          { return alloy_sym(AlloySym.GT          );
          }
        case 98: break;
        case 71: 
          { return alloy_sym(AlloySym.MODULE      );
          }
        case 99: break;
        case 15: 
          { return alloy_sym(AlloySym.COLON       );
          }
        case 100: break;
        case 46: 
          { return alloy_sym(AlloySym.SUM         );
          }
        case 101: break;
        case 65: 
          { return alloy_sym(AlloySym.PART        );
          }
        case 102: break;
        case 17: 
          { return alloy_sym(AlloySym.EQUALS      );
          }
        case 103: break;
        case 73: 
          { return alloy_sym(AlloySym.EXTENDS     );
          }
        case 104: break;
        case 54: 
          { return alloy_sym(AlloySym.FOR         );
          }
        case 105: break;
        case 63: 
          { return alloy_sym(AlloySym.IDEN        );
          }
        case 106: break;
        case 16: 
          { return alloy_sym(AlloySym.LT          );
          }
        case 107: break;
        case 18: 
          { return alloy_sym(AlloySym.AT          );
          }
        case 108: break;
        case 33: 
          { return alloy_sym(AlloySym.RANGE       );
          }
        case 109: break;
        case 62: 
          { return alloy_sym(AlloySym.UNIV        );
          }
        case 110: break;
        case 2: 
          { return alloy_sym(AlloySym.NOT         );
          }
        case 111: break;
        case 45: 
          { return alloy_sym(AlloySym.SET         );
          }
        case 112: break;
        case 7: 
          { return alloy_sym(AlloySym.RPAREN      );
          }
        case 113: break;
        case 40: 
          { return alloy_sym(AlloySym.IN          );
          }
        case 114: break;
        case 68: 
          { return alloy_sym(AlloySym.CHECK       );
          }
        case 115: break;
        case 29: 
          { return alloy_sym(AlloySym.AND         );
          }
        case 116: break;
        case 56: 
          { return alloy_sym(AlloySym.SOME        );
          }
        case 117: break;
        case 4: 
          { return alloy_sym(AlloySym.DOLLAR      );
          }
        case 118: break;
        case 6: 
          { return alloy_sym(AlloySym.LPAREN      );
          }
        case 119: break;
        case 67: 
          { return alloy_sym(AlloySym.FACT        );
          }
        case 120: break;
        case 11: 
          { return alloy_sym(AlloySym.MINUS       );
          }
        case 121: break;
        case 59: 
          { return alloy_sym(AlloySym.NONE        );
          }
        case 122: break;
        case 19: 
          { return alloy_sym(AlloySym.LBRACKET    );
          }
        case 123: break;
        case 20: 
          { return alloy_sym(AlloySym.RBRACKET    );
          }
        case 124: break;
        case 43: 
          { return alloy_sym(AlloySym.ALL         );
          }
        case 125: break;
        case 74: 
          { return alloy_sym(AlloySym.ABSTRACT    );
          }
        case 126: break;
        case 69: 
          { return alloy_sym(AlloySym.ASSERT      );
          }
        case 127: break;
        case 49: 
          { return alloy_sym(AlloySym.LET         );
          }
        case 128: break;
        case 1: 
          { throw new ErrorSyntax(alloy_here(),"The character \'"+yytext()+"\' cannot start a lexical token!");
          }
        case 129: break;
        case 27: 
          { return alloy_num (yytext());
          }
        case 130: break;
        case 5: 
          { return alloy_sym(AlloySym.AMPERSAND   );
          }
        case 131: break;
        case 35: 
          { return alloy_sym(AlloySym.IMPLIES     );
          }
        case 132: break;
        case 24: 
          { return alloy_sym(AlloySym.RBRACE      );
          }
        case 133: break;
        case 72: 
          { return alloy_sym(AlloySym.EXACTLY     );
          }
        case 134: break;
        case 48: 
          { return alloy_sym(AlloySym.RUN         );
          }
        case 135: break;
        case 34: 
          { return alloy_sym(AlloySym.DOMAIN      );
          }
        case 136: break;
        case 55: 
          { return alloy_sym(AlloySym.SIGINT      );
          }
        case 137: break;
        case 25: 
          { return alloy_sym(AlloySym.TILDE       );
          }
        case 138: break;
        case 22: 
          { return alloy_sym(AlloySym.LBRACE      );
          }
        case 139: break;
        case 8: 
          { return alloy_sym(AlloySym.STAR        );
          }
        case 140: break;
        case 30: 
          { return alloy_sym(AlloySym.PLUSPLUS    );
          }
        case 141: break;
        case 53: 
          { return alloy_sym(AlloySym.FUN         );
          }
        case 142: break;
        case 41: 
          { throw new ErrorSyntax(alloy_here(),"An identifier cannot start with a digit!");
          }
        case 143: break;
        case 50: 
          { return alloy_sym(AlloySym.EXH         );
          }
        case 144: break;
        case 51: 
          { return alloy_sym(AlloySym.INT         );
          }
        case 145: break;
        case 47: 
          { return alloy_sym(AlloySym.SIG         );
          }
        case 146: break;
        case 66: 
          { return alloy_sym(AlloySym.PRED        );
          }
        case 147: break;
        case 28: 
          { 
          }
        case 148: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(AlloySym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
