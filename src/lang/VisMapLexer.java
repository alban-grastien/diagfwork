// $ANTLR 3.2 Sep 23, 2009 12:02:23 VisMap.g 2011-08-01 17:24:43

package lang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class VisMapLexer extends Lexer {
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int REMCPP=7;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int INT=6;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int REMC=8;
    public static final int URI=5;
    public static final int T__19=19;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__16=16;
    public static final int T__33=33;
    public static final int WS=10;
    public static final int T__15=15;
    public static final int T__34=34;
    public static final int T__18=18;
    public static final int T__35=35;
    public static final int T__17=17;
    public static final int T__36=36;
    public static final int T__12=12;
    public static final int T__37=37;
    public static final int T__11=11;
    public static final int T__38=38;
    public static final int T__14=14;
    public static final int T__39=39;
    public static final int T__13=13;
    public static final int DIGIT=9;

    // delegates
    // delegators

    public VisMapLexer() {;} 
    public VisMapLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public VisMapLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "VisMap.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:7:7: ( 'shape' )
            // VisMap.g:7:9: 'shape'
            {
            match("shape"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:8:7: ( '{' )
            // VisMap.g:8:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:9:7: ( '}' )
            // VisMap.g:9:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:10:7: ( 'id' )
            // VisMap.g:10:9: 'id'
            {
            match("id"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:11:7: ( '=' )
            // VisMap.g:11:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:12:7: ( ';' )
            // VisMap.g:12:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:13:7: ( 'image_path' )
            // VisMap.g:13:9: 'image_path'
            {
            match("image_path"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:14:7: ( 'line' )
            // VisMap.g:14:9: 'line'
            {
            match("line"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:15:7: ( '[' )
            // VisMap.g:15:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:16:7: ( '(' )
            // VisMap.g:16:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:17:7: ( ',' )
            // VisMap.g:17:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:18:7: ( ')' )
            // VisMap.g:18:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:19:7: ( ']' )
            // VisMap.g:19:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:20:7: ( 'line_width' )
            // VisMap.g:20:9: 'line_width'
            {
            match("line_width"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:21:7: ( 'width' )
            // VisMap.g:21:9: 'width'
            {
            match("width"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:22:7: ( 'height' )
            // VisMap.g:22:9: 'height'
            {
            match("height"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:23:7: ( 'background' )
            // VisMap.g:23:9: 'background'
            {
            match("background"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:24:7: ( 'foreground' )
            // VisMap.g:24:9: 'foreground'
            {
            match("foreground"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:25:7: ( 'rules' )
            // VisMap.g:25:9: 'rules'
            {
            match("rules"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:26:7: ( '->' )
            // VisMap.g:26:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:27:7: ( 'NOT' )
            // VisMap.g:27:9: 'NOT'
            {
            match("NOT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:28:7: ( 'FALSE' )
            // VisMap.g:28:9: 'FALSE'
            {
            match("FALSE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:29:7: ( 'TRUE' )
            // VisMap.g:29:9: 'TRUE'
            {
            match("TRUE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:30:7: ( 'AND' )
            // VisMap.g:30:9: 'AND'
            {
            match("AND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:31:7: ( 'OR' )
            // VisMap.g:31:9: 'OR'
            {
            match("OR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:32:7: ( '+' )
            // VisMap.g:32:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:33:7: ( 'true' )
            // VisMap.g:33:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:34:7: ( 'false' )
            // VisMap.g:34:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:35:7: ( '::' )
            // VisMap.g:35:9: '::'
            {
            match("::"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "REMCPP"
    public final void mREMCPP() throws RecognitionException {
        try {
            int _type = REMCPP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:191:7: ( '//' ( . )* ( '\\r' | '\\n' ) )
            // VisMap.g:191:9: '//' ( . )* ( '\\r' | '\\n' )
            {
            match("//"); 

            // VisMap.g:191:13: ( . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\n'||LA1_0=='\r') ) {
                    alt1=2;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='\t')||(LA1_0>='\u000B' && LA1_0<='\f')||(LA1_0>='\u000E' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // VisMap.g:191:13: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REMCPP"

    // $ANTLR start "REMC"
    public final void mREMC() throws RecognitionException {
        try {
            int _type = REMC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:192:5: ( '/*' ( . )* '*/' )
            // VisMap.g:192:9: '/*' ( . )* '*/'
            {
            match("/*"); 

            // VisMap.g:192:13: ( . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='/') ) {
                        alt2=2;
                    }
                    else if ( ((LA2_1>='\u0000' && LA2_1<='.')||(LA2_1>='0' && LA2_1<='\uFFFF')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<=')')||(LA2_0>='+' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // VisMap.g:192:13: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match("*/"); 

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REMC"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // VisMap.g:194:6: ( ( '0' .. '9' ) )
            // VisMap.g:194:9: ( '0' .. '9' )
            {
            // VisMap.g:194:9: ( '0' .. '9' )
            // VisMap.g:194:10: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:195:4: ( ( DIGIT )+ )
            // VisMap.g:195:9: ( DIGIT )+
            {
            // VisMap.g:195:9: ( DIGIT )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // VisMap.g:195:9: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:196:3: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // VisMap.g:196:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:197:3: ( ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // VisMap.g:197:9: ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            // VisMap.g:197:9: ( '_' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='_') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // VisMap.g:197:10: '_'
                    {
                    match('_'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // VisMap.g:197:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // VisMap.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "URI"
    public final void mURI() throws RecognitionException {
        try {
            int _type = URI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // VisMap.g:198:4: ( ( ID | '-' | '/' )+ '\\.' ID )
            // VisMap.g:198:9: ( ID | '-' | '/' )+ '\\.' ID
            {
            // VisMap.g:198:9: ( ID | '-' | '/' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=4;
                switch ( input.LA(1) ) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt6=1;
                    }
                    break;
                case '-':
                    {
                    alt6=2;
                    }
                    break;
                case '/':
                    {
                    alt6=3;
                    }
                    break;

                }

                switch (alt6) {
            	case 1 :
            	    // VisMap.g:198:10: ID
            	    {
            	    mID(); 

            	    }
            	    break;
            	case 2 :
            	    // VisMap.g:198:13: '-'
            	    {
            	    match('-'); 

            	    }
            	    break;
            	case 3 :
            	    // VisMap.g:198:17: '/'
            	    {
            	    match('/'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            match('.'); 
            mID(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "URI"

    public void mTokens() throws RecognitionException {
        // VisMap.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | REMCPP | REMC | INT | WS | ID | URI )
        int alt7=35;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // VisMap.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // VisMap.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // VisMap.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // VisMap.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // VisMap.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // VisMap.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // VisMap.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // VisMap.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // VisMap.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // VisMap.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // VisMap.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // VisMap.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // VisMap.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // VisMap.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // VisMap.g:1:94: T__25
                {
                mT__25(); 

                }
                break;
            case 16 :
                // VisMap.g:1:100: T__26
                {
                mT__26(); 

                }
                break;
            case 17 :
                // VisMap.g:1:106: T__27
                {
                mT__27(); 

                }
                break;
            case 18 :
                // VisMap.g:1:112: T__28
                {
                mT__28(); 

                }
                break;
            case 19 :
                // VisMap.g:1:118: T__29
                {
                mT__29(); 

                }
                break;
            case 20 :
                // VisMap.g:1:124: T__30
                {
                mT__30(); 

                }
                break;
            case 21 :
                // VisMap.g:1:130: T__31
                {
                mT__31(); 

                }
                break;
            case 22 :
                // VisMap.g:1:136: T__32
                {
                mT__32(); 

                }
                break;
            case 23 :
                // VisMap.g:1:142: T__33
                {
                mT__33(); 

                }
                break;
            case 24 :
                // VisMap.g:1:148: T__34
                {
                mT__34(); 

                }
                break;
            case 25 :
                // VisMap.g:1:154: T__35
                {
                mT__35(); 

                }
                break;
            case 26 :
                // VisMap.g:1:160: T__36
                {
                mT__36(); 

                }
                break;
            case 27 :
                // VisMap.g:1:166: T__37
                {
                mT__37(); 

                }
                break;
            case 28 :
                // VisMap.g:1:172: T__38
                {
                mT__38(); 

                }
                break;
            case 29 :
                // VisMap.g:1:178: T__39
                {
                mT__39(); 

                }
                break;
            case 30 :
                // VisMap.g:1:184: REMCPP
                {
                mREMCPP(); 

                }
                break;
            case 31 :
                // VisMap.g:1:191: REMC
                {
                mREMC(); 

                }
                break;
            case 32 :
                // VisMap.g:1:196: INT
                {
                mINT(); 

                }
                break;
            case 33 :
                // VisMap.g:1:200: WS
                {
                mWS(); 

                }
                break;
            case 34 :
                // VisMap.g:1:203: ID
                {
                mID(); 

                }
                break;
            case 35 :
                // VisMap.g:1:206: URI
                {
                mURI(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\1\42\2\uffff\1\42\2\uffff\1\42\5\uffff\5\42\1\uffff\5\42"+
        "\1\uffff\1\42\5\uffff\3\42\2\uffff\2\42\1\71\10\42\1\uffff\4\42"+
        "\1\106\1\42\2\uffff\1\42\1\uffff\10\42\1\127\2\42\1\132\1\uffff"+
        "\1\42\6\uffff\2\42\1\144\6\42\1\uffff\1\42\1\154\1\uffff\1\155\1"+
        "\uffff\1\43\3\uffff\1\157\2\42\1\uffff\1\162\3\42\1\166\1\167\1"+
        "\170\2\uffff\1\43\1\uffff\2\42\1\uffff\1\173\2\42\3\uffff\2\42\1"+
        "\uffff\12\42\1\u008a\1\u008b\1\u008c\1\u008d\4\uffff";
    static final String DFA7_eofS =
        "\u008e\uffff";
    static final String DFA7_minS =
        "\1\11\1\55\2\uffff\1\55\2\uffff\1\55\5\uffff\13\55\1\uffff\1\55"+
        "\1\uffff\1\52\2\uffff\1\101\3\55\2\uffff\13\55\1\uffff\6\55\1\0"+
        "\1\uffff\1\55\1\uffff\14\55\1\uffff\1\55\5\0\1\uffff\11\55\1\uffff"+
        "\2\55\1\uffff\1\55\5\0\3\55\1\uffff\7\55\2\uffff\1\0\1\uffff\2\55"+
        "\1\uffff\3\55\3\uffff\2\55\1\uffff\16\55\4\uffff";
    static final String DFA7_maxS =
        "\1\175\1\172\2\uffff\1\172\2\uffff\1\172\5\uffff\13\172\1\uffff"+
        "\1\172\1\uffff\1\172\2\uffff\4\172\2\uffff\13\172\1\uffff\6\172"+
        "\1\uffff\1\uffff\1\172\1\uffff\14\172\1\uffff\1\172\5\uffff\1\uffff"+
        "\11\172\1\uffff\2\172\1\uffff\1\172\5\uffff\3\172\1\uffff\7\172"+
        "\2\uffff\1\uffff\1\uffff\2\172\1\uffff\3\172\3\uffff\2\172\1\uffff"+
        "\16\172\4\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\1\3\1\uffff\1\5\1\6\1\uffff\1\11\1\12\1\13\1\14\1\15"+
        "\13\uffff\1\32\1\uffff\1\35\1\uffff\1\40\1\41\4\uffff\1\42\1\43"+
        "\13\uffff\1\24\7\uffff\1\37\1\uffff\1\4\14\uffff\1\31\6\uffff\1"+
        "\36\11\uffff\1\25\2\uffff\1\30\11\uffff\1\10\7\uffff\1\27\1\33\1"+
        "\uffff\1\1\2\uffff\1\17\3\uffff\1\34\1\23\1\26\2\uffff\1\20\16\uffff"+
        "\1\7\1\16\1\21\1\22";
    static final String DFA7_specialS =
        "\66\uffff\1\6\21\uffff\1\3\1\11\1\0\1\4\1\5\17\uffff\1\12\1\13\1"+
        "\2\1\1\1\7\15\uffff\1\10\37\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\35\2\uffff\1\35\22\uffff\1\35\7\uffff\1\11\1\13\1\uffff\1"+
            "\30\1\12\1\22\1\uffff\1\33\12\34\1\32\1\6\1\uffff\1\5\3\uffff"+
            "\1\26\4\37\1\24\7\37\1\23\1\27\4\37\1\25\6\37\1\10\1\uffff\1"+
            "\14\1\uffff\1\36\1\uffff\1\37\1\17\3\37\1\20\1\37\1\16\1\4\2"+
            "\37\1\7\5\37\1\21\1\1\1\31\2\37\1\15\3\37\1\2\1\uffff\1\3",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\7\44\1\40\22"+
            "\44",
            "",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\3\44\1\46\10"+
            "\44\1\47\15\44",
            "",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\10\44\1\50\21"+
            "\44",
            "",
            "",
            "",
            "",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\10\44\1\51\21"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\52\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\1\53\31\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\1\55\15\44\1"+
            "\54\13\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\24\44\1\56\5"+
            "\44",
            "\3\43\16\uffff\1\57\2\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\3\43\12\45\7\uffff\16\44\1\60\13\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\1\61\31\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\21\44\1\62\10\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\15\44\1\63\14\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\21\44\1\64\10\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\21\44\1\65\10"+
            "\44",
            "",
            "\1\67\2\uffff\2\43\1\66\21\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "",
            "",
            "\32\37\6\uffff\32\37",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\1\70\31\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\1\72\31\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\15\44\1\73\14"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\3\44\1\74\26"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\10\44\1\75\21"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\2\44\1\76\27"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\21\44\1\77\10"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\13\44\1\100"+
            "\16\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\13\44\1\101"+
            "\16\44",
            "",
            "\3\43\12\45\7\uffff\23\44\1\102\6\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\13\44\1\103\16\44\4\uffff\1\41\1\uffff"+
            "\32\44",
            "\3\43\12\45\7\uffff\24\44\1\104\5\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\3\44\1\105\26\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\24\44\1\107"+
            "\5\44",
            "\55\115\1\113\1\110\1\114\21\115\32\112\4\115\1\111\1\115\32"+
            "\112\uff85\115",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\17\44\1\116"+
            "\12\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\6\44\1\117\23"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\120\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\23\44\1\121"+
            "\6\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\6\44\1\122\23"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\12\44\1\123"+
            "\17\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\124\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\22\44\1\125"+
            "\7\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\126\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\22\44\1\130\7\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\4\44\1\131\25\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\133\25"+
            "\44",
            "\101\115\32\135\4\115\1\134\1\115\32\135\uff85\115",
            "\101\115\32\112\6\115\32\112\uff85\115",
            "\55\115\1\113\1\110\1\114\12\140\7\115\32\137\4\115\1\136\1"+
            "\115\32\137\uff85\115",
            "\55\115\1\113\1\110\1\114\21\115\32\112\4\115\1\111\1\115\32"+
            "\112\uff85\115",
            "\55\115\1\113\1\110\1\114\21\115\32\112\4\115\1\111\1\115\32"+
            "\112\uff85\115",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\141\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\142\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\143\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\7\44\1\145\22"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\7\44\1\146\22"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\6\44\1\147\23"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\6\44\1\150\23"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\4\44\1\151\25"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\22\44\1\152"+
            "\7\44",
            "",
            "\3\43\12\45\7\uffff\4\44\1\153\25\44\4\uffff\1\41\1\uffff\32"+
            "\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\101\115\32\135\6\115\32\135\uff85\115",
            "\60\115\12\156\7\115\32\156\4\115\1\156\1\115\32\156\uff85"+
            "\115",
            "\55\115\1\113\1\110\1\114\12\140\7\115\32\137\4\115\1\136\1"+
            "\115\32\137\uff85\115",
            "\55\115\1\113\1\110\1\114\12\140\7\115\32\137\4\115\1\136\1"+
            "\115\32\137\uff85\115",
            "\55\115\1\113\1\110\1\114\12\140\7\115\32\137\4\115\1\136\1"+
            "\115\32\137\uff85\115",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\160\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\26\44\1\161"+
            "\3\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\23\44\1\163"+
            "\6\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\21\44\1\164"+
            "\10\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\21\44\1\165"+
            "\10\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "",
            "",
            "\60\115\12\156\7\115\32\156\4\115\1\156\1\115\32\156\uff85"+
            "\115",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\17\44\1\171"+
            "\12\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\10\44\1\172"+
            "\21\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\16\44\1\174"+
            "\13\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\16\44\1\175"+
            "\13\44",
            "",
            "",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\1\176\31\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\3\44\1\177\26"+
            "\44",
            "",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\24\44\1\u0080"+
            "\5\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\24\44\1\u0081"+
            "\5\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\23\44\1\u0082"+
            "\6\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\23\44\1\u0083"+
            "\6\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\15\44\1\u0084"+
            "\14\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\15\44\1\u0085"+
            "\14\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\7\44\1\u0086"+
            "\22\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\7\44\1\u0087"+
            "\22\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\3\44\1\u0088"+
            "\26\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\3\44\1\u0089"+
            "\26\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "\3\43\12\45\7\uffff\32\44\4\uffff\1\41\1\uffff\32\44",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | REMCPP | REMC | INT | WS | ID | URI );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_74 = input.LA(1);

                        s = -1;
                        if ( (LA7_74=='_') ) {s = 94;}

                        else if ( (LA7_74=='.') ) {s = 72;}

                        else if ( ((LA7_74>='A' && LA7_74<='Z')||(LA7_74>='a' && LA7_74<='z')) ) {s = 95;}

                        else if ( ((LA7_74>='0' && LA7_74<='9')) ) {s = 96;}

                        else if ( (LA7_74=='-') ) {s = 75;}

                        else if ( (LA7_74=='/') ) {s = 76;}

                        else if ( ((LA7_74>='\u0000' && LA7_74<=',')||(LA7_74>=':' && LA7_74<='@')||(LA7_74>='[' && LA7_74<='^')||LA7_74=='`'||(LA7_74>='{' && LA7_74<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_95 = input.LA(1);

                        s = -1;
                        if ( (LA7_95=='_') ) {s = 94;}

                        else if ( (LA7_95=='.') ) {s = 72;}

                        else if ( ((LA7_95>='A' && LA7_95<='Z')||(LA7_95>='a' && LA7_95<='z')) ) {s = 95;}

                        else if ( ((LA7_95>='0' && LA7_95<='9')) ) {s = 96;}

                        else if ( (LA7_95=='-') ) {s = 75;}

                        else if ( (LA7_95=='/') ) {s = 76;}

                        else if ( ((LA7_95>='\u0000' && LA7_95<=',')||(LA7_95>=':' && LA7_95<='@')||(LA7_95>='[' && LA7_95<='^')||LA7_95=='`'||(LA7_95>='{' && LA7_95<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_94 = input.LA(1);

                        s = -1;
                        if ( (LA7_94=='.') ) {s = 72;}

                        else if ( (LA7_94=='_') ) {s = 94;}

                        else if ( ((LA7_94>='A' && LA7_94<='Z')||(LA7_94>='a' && LA7_94<='z')) ) {s = 95;}

                        else if ( (LA7_94=='-') ) {s = 75;}

                        else if ( (LA7_94=='/') ) {s = 76;}

                        else if ( ((LA7_94>='0' && LA7_94<='9')) ) {s = 96;}

                        else if ( ((LA7_94>='\u0000' && LA7_94<=',')||(LA7_94>=':' && LA7_94<='@')||(LA7_94>='[' && LA7_94<='^')||LA7_94=='`'||(LA7_94>='{' && LA7_94<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_72 = input.LA(1);

                        s = -1;
                        if ( ((LA7_72>='\u0000' && LA7_72<='@')||(LA7_72>='[' && LA7_72<='^')||LA7_72=='`'||(LA7_72>='{' && LA7_72<='\uFFFF')) ) {s = 77;}

                        else if ( (LA7_72=='_') ) {s = 92;}

                        else if ( ((LA7_72>='A' && LA7_72<='Z')||(LA7_72>='a' && LA7_72<='z')) ) {s = 93;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_75 = input.LA(1);

                        s = -1;
                        if ( (LA7_75=='.') ) {s = 72;}

                        else if ( (LA7_75=='_') ) {s = 73;}

                        else if ( ((LA7_75>='A' && LA7_75<='Z')||(LA7_75>='a' && LA7_75<='z')) ) {s = 74;}

                        else if ( (LA7_75=='-') ) {s = 75;}

                        else if ( (LA7_75=='/') ) {s = 76;}

                        else if ( ((LA7_75>='\u0000' && LA7_75<=',')||(LA7_75>='0' && LA7_75<='@')||(LA7_75>='[' && LA7_75<='^')||LA7_75=='`'||(LA7_75>='{' && LA7_75<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA7_76 = input.LA(1);

                        s = -1;
                        if ( (LA7_76=='.') ) {s = 72;}

                        else if ( (LA7_76=='_') ) {s = 73;}

                        else if ( ((LA7_76>='A' && LA7_76<='Z')||(LA7_76>='a' && LA7_76<='z')) ) {s = 74;}

                        else if ( (LA7_76=='-') ) {s = 75;}

                        else if ( (LA7_76=='/') ) {s = 76;}

                        else if ( ((LA7_76>='\u0000' && LA7_76<=',')||(LA7_76>='0' && LA7_76<='@')||(LA7_76>='[' && LA7_76<='^')||LA7_76=='`'||(LA7_76>='{' && LA7_76<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA7_54 = input.LA(1);

                        s = -1;
                        if ( (LA7_54=='.') ) {s = 72;}

                        else if ( (LA7_54=='_') ) {s = 73;}

                        else if ( ((LA7_54>='A' && LA7_54<='Z')||(LA7_54>='a' && LA7_54<='z')) ) {s = 74;}

                        else if ( (LA7_54=='-') ) {s = 75;}

                        else if ( (LA7_54=='/') ) {s = 76;}

                        else if ( ((LA7_54>='\u0000' && LA7_54<=',')||(LA7_54>='0' && LA7_54<='@')||(LA7_54>='[' && LA7_54<='^')||LA7_54=='`'||(LA7_54>='{' && LA7_54<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA7_96 = input.LA(1);

                        s = -1;
                        if ( (LA7_96=='.') ) {s = 72;}

                        else if ( (LA7_96=='_') ) {s = 94;}

                        else if ( ((LA7_96>='A' && LA7_96<='Z')||(LA7_96>='a' && LA7_96<='z')) ) {s = 95;}

                        else if ( (LA7_96=='-') ) {s = 75;}

                        else if ( (LA7_96=='/') ) {s = 76;}

                        else if ( ((LA7_96>='0' && LA7_96<='9')) ) {s = 96;}

                        else if ( ((LA7_96>='\u0000' && LA7_96<=',')||(LA7_96>=':' && LA7_96<='@')||(LA7_96>='[' && LA7_96<='^')||LA7_96=='`'||(LA7_96>='{' && LA7_96<='\uFFFF')) ) {s = 77;}

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA7_110 = input.LA(1);

                        s = -1;
                        if ( ((LA7_110>='0' && LA7_110<='9')||(LA7_110>='A' && LA7_110<='Z')||LA7_110=='_'||(LA7_110>='a' && LA7_110<='z')) ) {s = 110;}

                        else if ( ((LA7_110>='\u0000' && LA7_110<='/')||(LA7_110>=':' && LA7_110<='@')||(LA7_110>='[' && LA7_110<='^')||LA7_110=='`'||(LA7_110>='{' && LA7_110<='\uFFFF')) ) {s = 77;}

                        else s = 35;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA7_73 = input.LA(1);

                        s = -1;
                        if ( ((LA7_73>='\u0000' && LA7_73<='@')||(LA7_73>='[' && LA7_73<='`')||(LA7_73>='{' && LA7_73<='\uFFFF')) ) {s = 77;}

                        else if ( ((LA7_73>='A' && LA7_73<='Z')||(LA7_73>='a' && LA7_73<='z')) ) {s = 74;}

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA7_92 = input.LA(1);

                        s = -1;
                        if ( ((LA7_92>='\u0000' && LA7_92<='@')||(LA7_92>='[' && LA7_92<='`')||(LA7_92>='{' && LA7_92<='\uFFFF')) ) {s = 77;}

                        else if ( ((LA7_92>='A' && LA7_92<='Z')||(LA7_92>='a' && LA7_92<='z')) ) {s = 93;}

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA7_93 = input.LA(1);

                        s = -1;
                        if ( ((LA7_93>='0' && LA7_93<='9')||(LA7_93>='A' && LA7_93<='Z')||LA7_93=='_'||(LA7_93>='a' && LA7_93<='z')) ) {s = 110;}

                        else if ( ((LA7_93>='\u0000' && LA7_93<='/')||(LA7_93>=':' && LA7_93<='@')||(LA7_93>='[' && LA7_93<='^')||LA7_93=='`'||(LA7_93>='{' && LA7_93<='\uFFFF')) ) {s = 77;}

                        else s = 35;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}