// $ANTLR 3.2 Sep 23, 2009 12:02:23 YAMLDlight.g 2011-08-23 14:27:55

package lang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class YAMLDlightLexer extends Lexer {
    public static final int T__29=29;
    public static final int ASSGN=8;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int FLOAT=7;
    public static final int EQUALS=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int DIGIT=12;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__44=44;
    public static final int REMCPP=9;
    public static final int INT=6;
    public static final int REMC=10;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int WS=11;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;

    // delegates
    // delegators

    public YAMLDlightLexer() {;} 
    public YAMLDlightLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public YAMLDlightLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "YAMLDlight.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:7:7: ( 'component' )
            // YAMLDlight.g:7:9: 'component'
            {
            match("component"); if (state.failed) return ;


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
            // YAMLDlight.g:8:7: ( '{' )
            // YAMLDlight.g:8:9: '{'
            {
            match('{'); if (state.failed) return ;

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
            // YAMLDlight.g:9:7: ( '}' )
            // YAMLDlight.g:9:9: '}'
            {
            match('}'); if (state.failed) return ;

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
            // YAMLDlight.g:10:7: ( 'event' )
            // YAMLDlight.g:10:9: 'event'
            {
            match("event"); if (state.failed) return ;


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
            // YAMLDlight.g:11:7: ( ';' )
            // YAMLDlight.g:11:9: ';'
            {
            match(';'); if (state.failed) return ;

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
            // YAMLDlight.g:12:7: ( 'coords' )
            // YAMLDlight.g:12:9: 'coords'
            {
            match("coords"); if (state.failed) return ;


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
            // YAMLDlight.g:13:7: ( ',' )
            // YAMLDlight.g:13:9: ','
            {
            match(','); if (state.failed) return ;

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
            // YAMLDlight.g:14:7: ( 'var' )
            // YAMLDlight.g:14:9: 'var'
            {
            match("var"); if (state.failed) return ;


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
            // YAMLDlight.g:15:7: ( ':' )
            // YAMLDlight.g:15:9: ':'
            {
            match(':'); if (state.failed) return ;

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
            // YAMLDlight.g:16:7: ( '[' )
            // YAMLDlight.g:16:9: '['
            {
            match('['); if (state.failed) return ;

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
            // YAMLDlight.g:17:7: ( '..' )
            // YAMLDlight.g:17:9: '..'
            {
            match(".."); if (state.failed) return ;


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
            // YAMLDlight.g:18:7: ( ']' )
            // YAMLDlight.g:18:9: ']'
            {
            match(']'); if (state.failed) return ;

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
            // YAMLDlight.g:19:7: ( 'dvar' )
            // YAMLDlight.g:19:9: 'dvar'
            {
            match("dvar"); if (state.failed) return ;


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
            // YAMLDlight.g:20:7: ( 'transition' )
            // YAMLDlight.g:20:9: 'transition'
            {
            match("transition"); if (state.failed) return ;


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
            // YAMLDlight.g:21:7: ( '->' )
            // YAMLDlight.g:21:9: '->'
            {
            match("->"); if (state.failed) return ;


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
            // YAMLDlight.g:22:7: ( 'forced' )
            // YAMLDlight.g:22:9: 'forced'
            {
            match("forced"); if (state.failed) return ;


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
            // YAMLDlight.g:23:7: ( 'connection' )
            // YAMLDlight.g:23:9: 'connection'
            {
            match("connection"); if (state.failed) return ;


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
            // YAMLDlight.g:24:7: ( 'constraint' )
            // YAMLDlight.g:24:9: 'constraint'
            {
            match("constraint"); if (state.failed) return ;


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
            // YAMLDlight.g:25:7: ( 'FALSE' )
            // YAMLDlight.g:25:9: 'FALSE'
            {
            match("FALSE"); if (state.failed) return ;


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
            // YAMLDlight.g:26:7: ( 'TRUE' )
            // YAMLDlight.g:26:9: 'TRUE'
            {
            match("TRUE"); if (state.failed) return ;


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
            // YAMLDlight.g:27:7: ( 'exists' )
            // YAMLDlight.g:27:9: 'exists'
            {
            match("exists"); if (state.failed) return ;


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
            // YAMLDlight.g:28:7: ( '(' )
            // YAMLDlight.g:28:9: '('
            {
            match('('); if (state.failed) return ;

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
            // YAMLDlight.g:29:7: ( ')' )
            // YAMLDlight.g:29:9: ')'
            {
            match(')'); if (state.failed) return ;

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
            // YAMLDlight.g:30:7: ( 'NOT' )
            // YAMLDlight.g:30:9: 'NOT'
            {
            match("NOT"); if (state.failed) return ;


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
            // YAMLDlight.g:31:7: ( 'AND' )
            // YAMLDlight.g:31:9: 'AND'
            {
            match("AND"); if (state.failed) return ;


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
            // YAMLDlight.g:32:7: ( 'OR' )
            // YAMLDlight.g:32:9: 'OR'
            {
            match("OR"); if (state.failed) return ;


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
            // YAMLDlight.g:33:7: ( '+' )
            // YAMLDlight.g:33:9: '+'
            {
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:34:7: ( 'true' )
            // YAMLDlight.g:34:9: 'true'
            {
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:35:7: ( 'false' )
            // YAMLDlight.g:35:9: 'false'
            {
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:36:7: ( '.' )
            // YAMLDlight.g:36:9: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:37:7: ( 'synchronize' )
            // YAMLDlight.g:37:9: 'synchronize'
            {
            match("synchronize"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:38:7: ( 'observable' )
            // YAMLDlight.g:38:9: 'observable'
            {
            match("observable"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "REMCPP"
    public final void mREMCPP() throws RecognitionException {
        try {
            int _type = REMCPP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:346:7: ( '//' ( . )* ( '\\r' | '\\n' ) )
            // YAMLDlight.g:346:9: '//' ( . )* ( '\\r' | '\\n' )
            {
            match("//"); if (state.failed) return ;

            // YAMLDlight.g:346:13: ( . )*
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
            	    // YAMLDlight.g:346:13: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( state.backtracking==0 ) {
               skip(); 
            }

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
            // YAMLDlight.g:347:5: ( '/*' ( . )* '*/' )
            // YAMLDlight.g:347:9: '/*' ( . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // YAMLDlight.g:347:13: ( . )*
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
            	    // YAMLDlight.g:347:13: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match("*/"); if (state.failed) return ;

            if ( state.backtracking==0 ) {
               skip(); 
            }

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
            // YAMLDlight.g:348:3: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // YAMLDlight.g:348:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // YAMLDlight.g:348:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // YAMLDlight.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            if ( state.backtracking==0 ) {
               skip(); 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "ASSGN"
    public final void mASSGN() throws RecognitionException {
        try {
            int _type = ASSGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:349:6: ( ':=' )
            // YAMLDlight.g:349:9: ':='
            {
            match(":="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSGN"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // YAMLDlight.g:351:6: ( ( '0' .. '9' ) )
            // YAMLDlight.g:351:9: ( '0' .. '9' )
            {
            // YAMLDlight.g:351:9: ( '0' .. '9' )
            // YAMLDlight.g:351:10: '0' .. '9'
            {
            matchRange('0','9'); if (state.failed) return ;

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
            // YAMLDlight.g:352:4: ( ( DIGIT )+ ( ( '.' ~ '.' )=> '.' ( DIGIT )* )? )
            // YAMLDlight.g:352:9: ( DIGIT )+ ( ( '.' ~ '.' )=> '.' ( DIGIT )* )?
            {
            // YAMLDlight.g:352:9: ( DIGIT )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // YAMLDlight.g:352:9: DIGIT
            	    {
            	    mDIGIT(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            // YAMLDlight.g:352:16: ( ( '.' ~ '.' )=> '.' ( DIGIT )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='.') && (synpred1_YAMLDlight())) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // YAMLDlight.g:352:18: ( '.' ~ '.' )=> '.' ( DIGIT )*
                    {
                    match('.'); if (state.failed) return ;
                    // YAMLDlight.g:352:35: ( DIGIT )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // YAMLDlight.g:352:35: DIGIT
                    	    {
                    	    mDIGIT(); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    if ( state.backtracking==0 ) {
                      _type=FLOAT;
                    }

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:353:6: ( '.' ( DIGIT )+ )
            // YAMLDlight.g:353:9: '.' ( DIGIT )+
            {
            match('.'); if (state.failed) return ;
            // YAMLDlight.g:353:13: ( DIGIT )+
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
            	    // YAMLDlight.g:353:13: DIGIT
            	    {
            	    mDIGIT(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
            	    if (state.backtracking>0) {state.failed=true; return ;}
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:354:3: ( ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // YAMLDlight.g:354:9: ( '_' )? ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            // YAMLDlight.g:354:9: ( '_' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='_') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // YAMLDlight.g:354:10: '_'
                    {
                    match('_'); if (state.failed) return ;

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // YAMLDlight.g:354:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='_'||(LA9_0>='a' && LA9_0<='z')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // YAMLDlight.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
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

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // YAMLDlight.g:355:7: ( '=' )
            // YAMLDlight.g:355:9: '='
            {
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    public void mTokens() throws RecognitionException {
        // YAMLDlight.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | REMCPP | REMC | WS | ASSGN | INT | FLOAT | ID | EQUALS )
        int alt10=40;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // YAMLDlight.g:1:10: T__13
                {
                mT__13(); if (state.failed) return ;

                }
                break;
            case 2 :
                // YAMLDlight.g:1:16: T__14
                {
                mT__14(); if (state.failed) return ;

                }
                break;
            case 3 :
                // YAMLDlight.g:1:22: T__15
                {
                mT__15(); if (state.failed) return ;

                }
                break;
            case 4 :
                // YAMLDlight.g:1:28: T__16
                {
                mT__16(); if (state.failed) return ;

                }
                break;
            case 5 :
                // YAMLDlight.g:1:34: T__17
                {
                mT__17(); if (state.failed) return ;

                }
                break;
            case 6 :
                // YAMLDlight.g:1:40: T__18
                {
                mT__18(); if (state.failed) return ;

                }
                break;
            case 7 :
                // YAMLDlight.g:1:46: T__19
                {
                mT__19(); if (state.failed) return ;

                }
                break;
            case 8 :
                // YAMLDlight.g:1:52: T__20
                {
                mT__20(); if (state.failed) return ;

                }
                break;
            case 9 :
                // YAMLDlight.g:1:58: T__21
                {
                mT__21(); if (state.failed) return ;

                }
                break;
            case 10 :
                // YAMLDlight.g:1:64: T__22
                {
                mT__22(); if (state.failed) return ;

                }
                break;
            case 11 :
                // YAMLDlight.g:1:70: T__23
                {
                mT__23(); if (state.failed) return ;

                }
                break;
            case 12 :
                // YAMLDlight.g:1:76: T__24
                {
                mT__24(); if (state.failed) return ;

                }
                break;
            case 13 :
                // YAMLDlight.g:1:82: T__25
                {
                mT__25(); if (state.failed) return ;

                }
                break;
            case 14 :
                // YAMLDlight.g:1:88: T__26
                {
                mT__26(); if (state.failed) return ;

                }
                break;
            case 15 :
                // YAMLDlight.g:1:94: T__27
                {
                mT__27(); if (state.failed) return ;

                }
                break;
            case 16 :
                // YAMLDlight.g:1:100: T__28
                {
                mT__28(); if (state.failed) return ;

                }
                break;
            case 17 :
                // YAMLDlight.g:1:106: T__29
                {
                mT__29(); if (state.failed) return ;

                }
                break;
            case 18 :
                // YAMLDlight.g:1:112: T__30
                {
                mT__30(); if (state.failed) return ;

                }
                break;
            case 19 :
                // YAMLDlight.g:1:118: T__31
                {
                mT__31(); if (state.failed) return ;

                }
                break;
            case 20 :
                // YAMLDlight.g:1:124: T__32
                {
                mT__32(); if (state.failed) return ;

                }
                break;
            case 21 :
                // YAMLDlight.g:1:130: T__33
                {
                mT__33(); if (state.failed) return ;

                }
                break;
            case 22 :
                // YAMLDlight.g:1:136: T__34
                {
                mT__34(); if (state.failed) return ;

                }
                break;
            case 23 :
                // YAMLDlight.g:1:142: T__35
                {
                mT__35(); if (state.failed) return ;

                }
                break;
            case 24 :
                // YAMLDlight.g:1:148: T__36
                {
                mT__36(); if (state.failed) return ;

                }
                break;
            case 25 :
                // YAMLDlight.g:1:154: T__37
                {
                mT__37(); if (state.failed) return ;

                }
                break;
            case 26 :
                // YAMLDlight.g:1:160: T__38
                {
                mT__38(); if (state.failed) return ;

                }
                break;
            case 27 :
                // YAMLDlight.g:1:166: T__39
                {
                mT__39(); if (state.failed) return ;

                }
                break;
            case 28 :
                // YAMLDlight.g:1:172: T__40
                {
                mT__40(); if (state.failed) return ;

                }
                break;
            case 29 :
                // YAMLDlight.g:1:178: T__41
                {
                mT__41(); if (state.failed) return ;

                }
                break;
            case 30 :
                // YAMLDlight.g:1:184: T__42
                {
                mT__42(); if (state.failed) return ;

                }
                break;
            case 31 :
                // YAMLDlight.g:1:190: T__43
                {
                mT__43(); if (state.failed) return ;

                }
                break;
            case 32 :
                // YAMLDlight.g:1:196: T__44
                {
                mT__44(); if (state.failed) return ;

                }
                break;
            case 33 :
                // YAMLDlight.g:1:202: REMCPP
                {
                mREMCPP(); if (state.failed) return ;

                }
                break;
            case 34 :
                // YAMLDlight.g:1:209: REMC
                {
                mREMC(); if (state.failed) return ;

                }
                break;
            case 35 :
                // YAMLDlight.g:1:214: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 36 :
                // YAMLDlight.g:1:217: ASSGN
                {
                mASSGN(); if (state.failed) return ;

                }
                break;
            case 37 :
                // YAMLDlight.g:1:223: INT
                {
                mINT(); if (state.failed) return ;

                }
                break;
            case 38 :
                // YAMLDlight.g:1:227: FLOAT
                {
                mFLOAT(); if (state.failed) return ;

                }
                break;
            case 39 :
                // YAMLDlight.g:1:233: ID
                {
                mID(); if (state.failed) return ;

                }
                break;
            case 40 :
                // YAMLDlight.g:1:236: EQUALS
                {
                mEQUALS(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_YAMLDlight
    public final void synpred1_YAMLDlight_fragment() throws RecognitionException {   
        // YAMLDlight.g:352:18: ( '.' ~ '.' )
        // YAMLDlight.g:352:19: '.' ~ '.'
        {
        match('.'); if (state.failed) return ;
        if ( (input.LA(1)>='\u0000' && input.LA(1)<='-')||(input.LA(1)>='/' && input.LA(1)<='\uFFFF') ) {
            input.consume();
        state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            recover(mse);
            throw mse;}


        }
    }
    // $ANTLR end synpred1_YAMLDlight

    public final boolean synpred1_YAMLDlight() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_YAMLDlight_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\uffff\1\35\2\uffff\1\35\2\uffff\1\35\1\44\1\uffff\1\47\1\uffff"+
        "\2\35\1\uffff\3\35\2\uffff\3\35\1\uffff\2\35\5\uffff\4\35\5\uffff"+
        "\10\35\1\104\2\35\2\uffff\5\35\1\115\7\35\1\125\1\126\1\uffff\10"+
        "\35\1\uffff\1\137\1\35\1\141\3\35\1\145\2\uffff\6\35\1\154\1\35"+
        "\1\uffff\1\35\1\uffff\1\35\1\160\1\161\1\uffff\3\35\1\165\2\35\1"+
        "\uffff\1\170\1\35\1\172\2\uffff\3\35\1\uffff\2\35\1\uffff\1\35\1"+
        "\uffff\10\35\1\u0089\5\35\1\uffff\1\u008f\1\u0090\1\u0091\1\35\1"+
        "\u0093\3\uffff\1\u0094\2\uffff";
    static final String DFA10_eofS =
        "\u0095\uffff";
    static final String DFA10_minS =
        "\1\11\1\157\2\uffff\1\166\2\uffff\1\141\1\75\1\uffff\1\56\1\uffff"+
        "\1\166\1\162\1\uffff\1\141\1\101\1\122\2\uffff\1\117\1\116\1\122"+
        "\1\uffff\1\171\1\142\1\52\4\uffff\1\155\1\145\1\151\1\162\5\uffff"+
        "\2\141\1\162\1\154\1\114\1\125\1\124\1\104\1\60\1\156\1\163\2\uffff"+
        "\1\160\1\162\2\156\1\163\1\60\1\162\1\156\1\145\1\143\1\163\1\123"+
        "\1\105\2\60\1\uffff\1\143\1\145\1\157\1\144\1\145\3\164\1\uffff"+
        "\1\60\1\163\1\60\2\145\1\105\1\60\2\uffff\1\150\1\162\1\156\1\163"+
        "\1\143\1\162\1\60\1\163\1\uffff\1\151\1\uffff\1\144\2\60\1\uffff"+
        "\1\162\1\166\1\145\1\60\1\164\1\141\1\uffff\1\60\1\164\1\60\2\uffff"+
        "\1\157\1\141\1\156\1\uffff\2\151\1\uffff\1\151\1\uffff\1\156\1\142"+
        "\1\164\1\157\1\156\1\157\1\151\1\154\1\60\1\156\1\164\1\156\1\172"+
        "\1\145\1\uffff\3\60\1\145\1\60\3\uffff\1\60\2\uffff";
    static final String DFA10_maxS =
        "\1\175\1\157\2\uffff\1\170\2\uffff\1\141\1\75\1\uffff\1\71\1\uffff"+
        "\1\166\1\162\1\uffff\1\157\1\101\1\122\2\uffff\1\117\1\116\1\122"+
        "\1\uffff\1\171\1\142\1\57\4\uffff\1\157\1\145\1\151\1\162\5\uffff"+
        "\1\141\1\165\1\162\1\154\1\114\1\125\1\124\1\104\1\172\1\156\1\163"+
        "\2\uffff\1\160\1\162\1\163\1\156\1\163\1\172\1\162\1\156\1\145\1"+
        "\143\1\163\1\123\1\105\2\172\1\uffff\1\143\1\145\1\157\1\144\1\145"+
        "\3\164\1\uffff\1\172\1\163\1\172\2\145\1\105\1\172\2\uffff\1\150"+
        "\1\162\1\156\1\163\1\143\1\162\1\172\1\163\1\uffff\1\151\1\uffff"+
        "\1\144\2\172\1\uffff\1\162\1\166\1\145\1\172\1\164\1\141\1\uffff"+
        "\1\172\1\164\1\172\2\uffff\1\157\1\141\1\156\1\uffff\2\151\1\uffff"+
        "\1\151\1\uffff\1\156\1\142\1\164\1\157\1\156\1\157\1\151\1\154\1"+
        "\172\1\156\1\164\1\156\1\172\1\145\1\uffff\3\172\1\145\1\172\3\uffff"+
        "\1\172\2\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\1\3\1\uffff\1\5\1\7\2\uffff\1\12\1\uffff\1\14\2\uffff"+
        "\1\17\3\uffff\1\26\1\27\3\uffff\1\33\3\uffff\1\43\1\45\1\47\1\50"+
        "\4\uffff\1\44\1\11\1\13\1\46\1\36\13\uffff\1\41\1\42\17\uffff\1"+
        "\32\10\uffff\1\10\7\uffff\1\30\1\31\10\uffff\1\15\1\uffff\1\34\3"+
        "\uffff\1\24\6\uffff\1\4\3\uffff\1\35\1\23\3\uffff\1\6\2\uffff\1"+
        "\25\1\uffff\1\20\16\uffff\1\1\5\uffff\1\21\1\22\1\16\1\uffff\1\40"+
        "\1\37";
    static final String DFA10_specialS =
        "\u0095\uffff}>";
    static final String[] DFA10_transitionS = {
            "\2\33\2\uffff\1\33\22\uffff\1\33\7\uffff\1\22\1\23\1\uffff\1"+
            "\27\1\6\1\16\1\12\1\32\12\34\1\10\1\5\1\uffff\1\36\3\uffff\1"+
            "\25\4\35\1\20\7\35\1\24\1\26\4\35\1\21\6\35\1\11\1\uffff\1\13"+
            "\1\uffff\1\35\1\uffff\2\35\1\1\1\14\1\4\1\17\10\35\1\31\3\35"+
            "\1\30\1\15\1\35\1\7\4\35\1\2\1\uffff\1\3",
            "\1\37",
            "",
            "",
            "\1\40\1\uffff\1\41",
            "",
            "",
            "\1\42",
            "\1\43",
            "",
            "\1\45\1\uffff\12\46",
            "",
            "\1\50",
            "\1\51",
            "",
            "\1\53\15\uffff\1\52",
            "\1\54",
            "\1\55",
            "",
            "",
            "\1\56",
            "\1\57",
            "\1\60",
            "",
            "\1\61",
            "\1\62",
            "\1\64\4\uffff\1\63",
            "",
            "",
            "",
            "",
            "\1\65\1\67\1\66",
            "\1\70",
            "\1\71",
            "\1\72",
            "",
            "",
            "",
            "",
            "",
            "\1\73",
            "\1\74\23\uffff\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\105",
            "\1\106",
            "",
            "",
            "\1\107",
            "\1\110",
            "\1\111\4\uffff\1\112",
            "\1\113",
            "\1\114",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\140",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\142",
            "\1\143",
            "\1\144",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\155",
            "",
            "\1\156",
            "",
            "\1\157",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\162",
            "\1\163",
            "\1\164",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\166",
            "\1\167",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\171",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "",
            "\1\173",
            "\1\174",
            "\1\175",
            "",
            "\1\176",
            "\1\177",
            "",
            "\1\u0080",
            "",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0092",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | REMCPP | REMC | WS | ASSGN | INT | FLOAT | ID | EQUALS );";
        }
    }
 

}