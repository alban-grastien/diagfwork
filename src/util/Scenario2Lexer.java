// $ANTLR 3.2 Sep 23, 2009 12:02:23 YAMLDSim/src/util/Scenario2.g 2012-02-20 12:48:47

package util;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Scenario2Lexer extends Lexer {
    public static final int REMCPP=7;
    public static final int T__20=20;
    public static final int FLOAT=6;
    public static final int INT=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int REMC=8;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int WS=9;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int DIGIT=10;

    // delegates
    // delegators

    public Scenario2Lexer() {;} 
    public Scenario2Lexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public Scenario2Lexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "YAMLDSim/src/util/Scenario2.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDSim/src/util/Scenario2.g:7:7: ( 'State' )
            // YAMLDSim/src/util/Scenario2.g:7:9: 'State'
            {
            match("State"); 


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
            // YAMLDSim/src/util/Scenario2.g:8:7: ( '=' )
            // YAMLDSim/src/util/Scenario2.g:8:9: '='
            {
            match('='); 

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
            // YAMLDSim/src/util/Scenario2.g:9:7: ( '{' )
            // YAMLDSim/src/util/Scenario2.g:9:9: '{'
            {
            match('{'); 

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
            // YAMLDSim/src/util/Scenario2.g:10:7: ( '}' )
            // YAMLDSim/src/util/Scenario2.g:10:9: '}'
            {
            match('}'); 

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
            // YAMLDSim/src/util/Scenario2.g:11:7: ( ';' )
            // YAMLDSim/src/util/Scenario2.g:11:9: ';'
            {
            match(';'); 

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
            // YAMLDSim/src/util/Scenario2.g:12:7: ( '.' )
            // YAMLDSim/src/util/Scenario2.g:12:9: '.'
            {
            match('.'); 

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
            // YAMLDSim/src/util/Scenario2.g:13:7: ( ':=' )
            // YAMLDSim/src/util/Scenario2.g:13:9: ':='
            {
            match(":="); 


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
            // YAMLDSim/src/util/Scenario2.g:14:7: ( 'true' )
            // YAMLDSim/src/util/Scenario2.g:14:9: 'true'
            {
            match("true"); 


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
            // YAMLDSim/src/util/Scenario2.g:15:7: ( 'false' )
            // YAMLDSim/src/util/Scenario2.g:15:9: 'false'
            {
            match("false"); 


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
            // YAMLDSim/src/util/Scenario2.g:16:7: ( 'Transition' )
            // YAMLDSim/src/util/Scenario2.g:16:9: 'Transition'
            {
            match("Transition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "REMCPP"
    public final void mREMCPP() throws RecognitionException {
        try {
            int _type = REMCPP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDSim/src/util/Scenario2.g:96:7: ( '//' ( . )* ( '\\r' | '\\n' ) )
            // YAMLDSim/src/util/Scenario2.g:96:9: '//' ( . )* ( '\\r' | '\\n' )
            {
            match("//"); 

            // YAMLDSim/src/util/Scenario2.g:96:13: ( . )*
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
            	    // YAMLDSim/src/util/Scenario2.g:96:13: .
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
            // YAMLDSim/src/util/Scenario2.g:97:5: ( '/*' ( . )* '*/' )
            // YAMLDSim/src/util/Scenario2.g:97:9: '/*' ( . )* '*/'
            {
            match("/*"); 

            // YAMLDSim/src/util/Scenario2.g:97:13: ( . )*
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
            	    // YAMLDSim/src/util/Scenario2.g:97:13: .
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
            // YAMLDSim/src/util/Scenario2.g:98:3: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // YAMLDSim/src/util/Scenario2.g:98:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // YAMLDSim/src/util/Scenario2.g:98:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // YAMLDSim/src/util/Scenario2.g:
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDSim/src/util/Scenario2.g:99:3: ( ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // YAMLDSim/src/util/Scenario2.g:99:9: ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            // YAMLDSim/src/util/Scenario2.g:99:9: ( '_' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='_') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // YAMLDSim/src/util/Scenario2.g:99:10: '_'
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

            // YAMLDSim/src/util/Scenario2.g:99:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // YAMLDSim/src/util/Scenario2.g:
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

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // YAMLDSim/src/util/Scenario2.g:101:6: ( ( '0' .. '9' ) )
            // YAMLDSim/src/util/Scenario2.g:101:9: ( '0' .. '9' )
            {
            // YAMLDSim/src/util/Scenario2.g:101:9: ( '0' .. '9' )
            // YAMLDSim/src/util/Scenario2.g:101:10: '0' .. '9'
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
            // YAMLDSim/src/util/Scenario2.g:102:6: ( ( DIGIT )+ '.' ( DIGIT )+ )
            // YAMLDSim/src/util/Scenario2.g:102:9: ( DIGIT )+ '.' ( DIGIT )+
            {
            // YAMLDSim/src/util/Scenario2.g:102:9: ( DIGIT )+
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
            	    // YAMLDSim/src/util/Scenario2.g:102:9: DIGIT
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
            // YAMLDSim/src/util/Scenario2.g:102:20: ( DIGIT )+
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
            	    // YAMLDSim/src/util/Scenario2.g:102:20: DIGIT
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
            // YAMLDSim/src/util/Scenario2.g:103:4: ( ( DIGIT )+ )
            // YAMLDSim/src/util/Scenario2.g:103:9: ( DIGIT )+
            {
            // YAMLDSim/src/util/Scenario2.g:103:9: ( DIGIT )+
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
            	    // YAMLDSim/src/util/Scenario2.g:103:9: DIGIT
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
        // YAMLDSim/src/util/Scenario2.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | REMCPP | REMC | WS | ID | FLOAT | INT )
        int alt9=16;
        alt9 = dfa9.predict(input);
        switch (alt9) {
            case 1 :
                // YAMLDSim/src/util/Scenario2.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // YAMLDSim/src/util/Scenario2.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // YAMLDSim/src/util/Scenario2.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // YAMLDSim/src/util/Scenario2.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // YAMLDSim/src/util/Scenario2.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // YAMLDSim/src/util/Scenario2.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // YAMLDSim/src/util/Scenario2.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // YAMLDSim/src/util/Scenario2.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // YAMLDSim/src/util/Scenario2.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // YAMLDSim/src/util/Scenario2.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // YAMLDSim/src/util/Scenario2.g:1:70: REMCPP
                {
                mREMCPP(); 

                }
                break;
            case 12 :
                // YAMLDSim/src/util/Scenario2.g:1:77: REMC
                {
                mREMC(); 

                }
                break;
            case 13 :
                // YAMLDSim/src/util/Scenario2.g:1:82: WS
                {
                mWS(); 

                }
                break;
            case 14 :
                // YAMLDSim/src/util/Scenario2.g:1:85: ID
                {
                mID(); 

                }
                break;
            case 15 :
                // YAMLDSim/src/util/Scenario2.g:1:88: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 16 :
                // YAMLDSim/src/util/Scenario2.g:1:94: INT
                {
                mINT(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\1\uffff\1\15\6\uffff\3\15\3\uffff\1\25\4\15\4\uffff\5\15\1\40\2"+
        "\15\1\43\1\uffff\1\44\1\15\2\uffff\4\15\1\52\1\uffff";
    static final String DFA9_eofS =
        "\53\uffff";
    static final String DFA9_minS =
        "\1\11\1\164\6\uffff\1\162\1\141\1\162\1\52\2\uffff\1\56\1\141\1"+
        "\165\1\154\1\141\4\uffff\1\164\1\145\1\163\1\156\1\145\1\60\1\145"+
        "\1\163\1\60\1\uffff\1\60\1\151\2\uffff\1\164\1\151\1\157\1\156\1"+
        "\60\1\uffff";
    static final String DFA9_maxS =
        "\1\175\1\164\6\uffff\1\162\1\141\1\162\1\57\2\uffff\1\71\1\141\1"+
        "\165\1\154\1\141\4\uffff\1\164\1\145\1\163\1\156\1\145\1\172\1\145"+
        "\1\163\1\172\1\uffff\1\172\1\151\2\uffff\1\164\1\151\1\157\1\156"+
        "\1\172\1\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\4\uffff\1\15\1\16\5\uffff\1\13"+
        "\1\14\1\20\1\17\11\uffff\1\10\2\uffff\1\1\1\11\5\uffff\1\12";
    static final String DFA9_specialS =
        "\53\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\14\2\uffff\1\14\22\uffff\1\14\15\uffff\1\6\1\13\12\16\1\7"+
            "\1\5\1\uffff\1\2\3\uffff\22\15\1\1\1\12\6\15\4\uffff\1\15\1"+
            "\uffff\5\15\1\11\15\15\1\10\6\15\1\3\1\uffff\1\4",
            "\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\24\4\uffff\1\23",
            "",
            "",
            "\1\26\1\uffff\12\16",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "",
            "",
            "",
            "",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\41",
            "\1\42",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\45",
            "",
            "",
            "\1\46",
            "\1\47",
            "\1\50",
            "\1\51",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
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
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | REMCPP | REMC | WS | ID | FLOAT | INT );";
        }
    }
 

}
