// $ANTLR 3.2 Sep 23, 2009 12:02:23 TimedScenario.g 2011-11-14 14:06:03

package util;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimedScenarioLexer extends Lexer {
    public static final int REMCPP=16;
    public static final int OPENBRACK=4;
    public static final int SQCLOSEBRACK=14;
    public static final int INT=10;
    public static final int FLOAT=12;
    public static final int CLOSEBRACK=5;
    public static final int MINUS=13;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int REMC=17;
    public static final int TRANS=15;
    public static final int EQUA=9;
    public static final int WS=18;
    public static final int COMMA=6;
    public static final int DIGIT=19;
    public static final int DOT=8;
    public static final int SQOPENBRACK=11;

    // delegates
    // delegators

    public TimedScenarioLexer() {;} 
    public TimedScenarioLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TimedScenarioLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "TimedScenario.g"; }

    // $ANTLR start "REMCPP"
    public final void mREMCPP() throws RecognitionException {
        try {
            int _type = REMCPP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:198:7: ( '//' ( . )* ( '\\r' | '\\n' ) )
            // TimedScenario.g:198:15: '//' ( . )* ( '\\r' | '\\n' )
            {
            match("//"); 

            // TimedScenario.g:198:19: ( . )*
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
            	    // TimedScenario.g:198:19: .
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
            // TimedScenario.g:199:5: ( '/*' ( . )* '*/' )
            // TimedScenario.g:199:15: '/*' ( . )* '*/'
            {
            match("/*"); 

            // TimedScenario.g:199:19: ( . )*
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
            	    // TimedScenario.g:199:19: .
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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:200:3: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // TimedScenario.g:200:15: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // TimedScenario.g:200:15: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // TimedScenario.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "OPENBRACK"
    public final void mOPENBRACK() throws RecognitionException {
        try {
            int _type = OPENBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:201:10: ( '{' )
            // TimedScenario.g:201:15: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPENBRACK"

    // $ANTLR start "CLOSEBRACK"
    public final void mCLOSEBRACK() throws RecognitionException {
        try {
            int _type = CLOSEBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:202:11: ( '}' )
            // TimedScenario.g:202:15: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLOSEBRACK"

    // $ANTLR start "SQOPENBRACK"
    public final void mSQOPENBRACK() throws RecognitionException {
        try {
            int _type = SQOPENBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:203:12: ( '[' )
            // TimedScenario.g:203:15: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQOPENBRACK"

    // $ANTLR start "SQCLOSEBRACK"
    public final void mSQCLOSEBRACK() throws RecognitionException {
        try {
            int _type = SQCLOSEBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:204:13: ( ']' )
            // TimedScenario.g:204:15: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQCLOSEBRACK"

    // $ANTLR start "EQUA"
    public final void mEQUA() throws RecognitionException {
        try {
            int _type = EQUA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:205:5: ( '=' )
            // TimedScenario.g:205:15: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUA"

    // $ANTLR start "TRANS"
    public final void mTRANS() throws RecognitionException {
        try {
            int _type = TRANS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:206:6: ( '--->' )
            // TimedScenario.g:206:15: '--->'
            {
            match("--->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRANS"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:207:6: ( ',' )
            // TimedScenario.g:207:15: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:208:6: ( '-' )
            // TimedScenario.g:208:15: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:209:3: ( ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // TimedScenario.g:209:15: ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            // TimedScenario.g:209:15: ( '_' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='_') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // TimedScenario.g:209:16: '_'
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

            // TimedScenario.g:209:40: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // TimedScenario.g:
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

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:210:4: ( '.' )
            // TimedScenario.g:210:15: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // TimedScenario.g:212:6: ( ( '0' .. '9' ) )
            // TimedScenario.g:212:9: ( '0' .. '9' )
            {
            // TimedScenario.g:212:9: ( '0' .. '9' )
            // TimedScenario.g:212:10: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:213:6: ( ( DIGIT )+ '.' ( DIGIT )+ )
            // TimedScenario.g:213:9: ( DIGIT )+ '.' ( DIGIT )+
            {
            // TimedScenario.g:213:9: ( DIGIT )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // TimedScenario.g:213:9: DIGIT
            	    {
            	    mDIGIT(); 

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
            // TimedScenario.g:213:20: ( DIGIT )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // TimedScenario.g:213:20: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TimedScenario.g:214:4: ( ( DIGIT )+ )
            // TimedScenario.g:214:9: ( DIGIT )+
            {
            // TimedScenario.g:214:9: ( DIGIT )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // TimedScenario.g:214:9: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    public void mTokens() throws RecognitionException {
        // TimedScenario.g:1:8: ( REMCPP | REMC | WS | OPENBRACK | CLOSEBRACK | SQOPENBRACK | SQCLOSEBRACK | EQUA | TRANS | COMMA | MINUS | ID | DOT | FLOAT | INT )
        int alt9=15;
        alt9 = dfa9.predict(input);
        switch (alt9) {
            case 1 :
                // TimedScenario.g:1:10: REMCPP
                {
                mREMCPP(); 

                }
                break;
            case 2 :
                // TimedScenario.g:1:17: REMC
                {
                mREMC(); 

                }
                break;
            case 3 :
                // TimedScenario.g:1:22: WS
                {
                mWS(); 

                }
                break;
            case 4 :
                // TimedScenario.g:1:25: OPENBRACK
                {
                mOPENBRACK(); 

                }
                break;
            case 5 :
                // TimedScenario.g:1:35: CLOSEBRACK
                {
                mCLOSEBRACK(); 

                }
                break;
            case 6 :
                // TimedScenario.g:1:46: SQOPENBRACK
                {
                mSQOPENBRACK(); 

                }
                break;
            case 7 :
                // TimedScenario.g:1:58: SQCLOSEBRACK
                {
                mSQCLOSEBRACK(); 

                }
                break;
            case 8 :
                // TimedScenario.g:1:71: EQUA
                {
                mEQUA(); 

                }
                break;
            case 9 :
                // TimedScenario.g:1:76: TRANS
                {
                mTRANS(); 

                }
                break;
            case 10 :
                // TimedScenario.g:1:82: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 11 :
                // TimedScenario.g:1:88: MINUS
                {
                mMINUS(); 

                }
                break;
            case 12 :
                // TimedScenario.g:1:94: ID
                {
                mID(); 

                }
                break;
            case 13 :
                // TimedScenario.g:1:97: DOT
                {
                mDOT(); 

                }
                break;
            case 14 :
                // TimedScenario.g:1:101: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 15 :
                // TimedScenario.g:1:107: INT
                {
                mINT(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\10\uffff\1\20\3\uffff\1\21\6\uffff";
    static final String DFA9_eofS =
        "\23\uffff";
    static final String DFA9_minS =
        "\1\11\1\52\6\uffff\1\55\3\uffff\1\56\6\uffff";
    static final String DFA9_maxS =
        "\1\175\1\57\6\uffff\1\55\3\uffff\1\71\6\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\14\1\15\1\uffff"+
        "\1\1\1\2\1\11\1\13\1\17\1\16";
    static final String DFA9_specialS =
        "\23\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\2\2\uffff\1\2\22\uffff\1\2\13\uffff\1\11\1\10\1\13\1\1\12"+
            "\14\3\uffff\1\7\3\uffff\32\12\1\5\1\uffff\1\6\1\uffff\1\12\1"+
            "\uffff\32\12\1\3\1\uffff\1\4",
            "\1\16\4\uffff\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\17",
            "",
            "",
            "",
            "\1\22\1\uffff\12\14",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( REMCPP | REMC | WS | OPENBRACK | CLOSEBRACK | SQOPENBRACK | SQCLOSEBRACK | EQUA | TRANS | COMMA | MINUS | ID | DOT | FLOAT | INT );";
        }
    }
 

}