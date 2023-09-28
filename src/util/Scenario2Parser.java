// $ANTLR 3.2 Sep 23, 2009 12:02:23 YAMLDSim/src/util/Scenario2.g 2012-02-20 12:48:47

package util;

import java.util.HashMap;
import java.util.Map;
import lang.ExplicitState;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDGenericVar;
import lang.MMLDRule;
import lang.MMLDTransition;
import lang.YAMLDValue;
import lang.YAMLDVar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Scenario2Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "FLOAT", "REMCPP", "REMC", "WS", "DIGIT", "'State'", "'='", "'{'", "'}'", "';'", "'.'", "':='", "'true'", "'false'", "'Transition'"
    };
    public static final int REMCPP=7;
    public static final int T__20=20;
    public static final int INT=5;
    public static final int FLOAT=6;
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


        public Scenario2Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public Scenario2Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return Scenario2Parser.tokenNames; }
    public String getGrammarFileName() { return "YAMLDSim/src/util/Scenario2.g"; }



    // $ANTLR start "scenario"
    // YAMLDSim/src/util/Scenario2.g:33:1: scenario[Network net] returns [Scenario sce] : state[net,ass] ( trans[net,compToRule] state[net,ass] )* ;
    public final Scenario scenario(Network net) throws RecognitionException {
        Scenario sce = null;

        float trans1 = 0.0f;


        try {
            // YAMLDSim/src/util/Scenario2.g:33:45: ( state[net,ass] ( trans[net,compToRule] state[net,ass] )* )
            // YAMLDSim/src/util/Scenario2.g:34:3: state[net,ass] ( trans[net,compToRule] state[net,ass] )*
            {
            final Map<YAMLDVar,YAMLDValue> ass = new HashMap<YAMLDVar,YAMLDValue>();
            pushFollow(FOLLOW_state_in_scenario45);
            state(net, ass);

            state._fsp--;

            sce = new EmptyScenario(new ExplicitState(net,ass),Time.ZERO_TIME);
            // YAMLDSim/src/util/Scenario2.g:37:3: ( trans[net,compToRule] state[net,ass] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==20) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // YAMLDSim/src/util/Scenario2.g:38:5: trans[net,compToRule] state[net,ass]
            	    {
            	    final Map<YAMLDComponent, MMLDRule> compToRule= new HashMap<YAMLDComponent, MMLDRule>();
            	    pushFollow(FOLLOW_trans_in_scenario66);
            	    trans1=trans(net, compToRule);

            	    state._fsp--;

            	    pushFollow(FOLLOW_state_in_scenario74);
            	    state(net, ass);

            	    state._fsp--;

            	    final State st = new ExplicitState(net,ass);
            	        final MMLDGlobalTransition gt = new ExplicitGlobalTransition(compToRule);
            	        //sce = new IncrementalScenario(sce,gt,new Time(trans1),st);}
            	        sce = new IncrementalScenario(sce,gt,st);

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return sce;
    }
    // $ANTLR end "scenario"


    // $ANTLR start "state"
    // YAMLDSim/src/util/Scenario2.g:48:1: state[Network net,Map<YAMLDVar,YAMLDValue> ass] : 'State' '=' '{' ( assignment[net,ass] )* '}' ';' ;
    public final void state(Network net, Map<YAMLDVar,YAMLDValue> ass) throws RecognitionException {
        try {
            // YAMLDSim/src/util/Scenario2.g:48:48: ( 'State' '=' '{' ( assignment[net,ass] )* '}' ';' )
            // YAMLDSim/src/util/Scenario2.g:49:3: 'State' '=' '{' ( assignment[net,ass] )* '}' ';'
            {
            match(input,11,FOLLOW_11_in_state97); 
            match(input,12,FOLLOW_12_in_state99); 
            match(input,13,FOLLOW_13_in_state101); 
            // YAMLDSim/src/util/Scenario2.g:49:19: ( assignment[net,ass] )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // YAMLDSim/src/util/Scenario2.g:49:19: assignment[net,ass]
            	    {
            	    pushFollow(FOLLOW_assignment_in_state103);
            	    assignment(net, ass);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match(input,14,FOLLOW_14_in_state107); 
            match(input,15,FOLLOW_15_in_state109); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "state"


    // $ANTLR start "assignment"
    // YAMLDSim/src/util/Scenario2.g:52:1: assignment[Network net,Map<YAMLDVar,YAMLDValue> ass] : cn= ID '.' vn= ID ':=' ( 'true' | 'false' | valn= ID | valn= INT ) ';' ;
    public final void assignment(Network net, Map<YAMLDVar,YAMLDValue> ass) throws RecognitionException {
        Token cn=null;
        Token vn=null;
        Token valn=null;

        try {
            // YAMLDSim/src/util/Scenario2.g:52:53: (cn= ID '.' vn= ID ':=' ( 'true' | 'false' | valn= ID | valn= INT ) ';' )
            // YAMLDSim/src/util/Scenario2.g:53:3: cn= ID '.' vn= ID ':=' ( 'true' | 'false' | valn= ID | valn= INT ) ';'
            {
            cn=(Token)match(input,ID,FOLLOW_ID_in_assignment123); 
            match(input,16,FOLLOW_16_in_assignment125); 
            vn=(Token)match(input,ID,FOLLOW_ID_in_assignment129); 
            match(input,17,FOLLOW_17_in_assignment131); 
            final YAMLDComponent comp = net.getComponent(cn.getText());
              final YAMLDGenericVar gvar = comp.getVariable(vn.getText());
              final YAMLDVar var = (gvar instanceof YAMLDVar)?((YAMLDVar)gvar):null;
              YAMLDValue val = null;
            // YAMLDSim/src/util/Scenario2.g:58:3: ( 'true' | 'false' | valn= ID | valn= INT )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt3=1;
                }
                break;
            case 19:
                {
                alt3=2;
                }
                break;
            case ID:
                {
                alt3=3;
                }
                break;
            case INT:
                {
                alt3=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // YAMLDSim/src/util/Scenario2.g:59:5: 'true'
                    {
                    match(input,18,FOLLOW_18_in_assignment146); 
                    val = gvar.getValue(1);

                    }
                    break;
                case 2 :
                    // YAMLDSim/src/util/Scenario2.g:60:5: 'false'
                    {
                    match(input,19,FOLLOW_19_in_assignment154); 
                    val = gvar.getValue(0);

                    }
                    break;
                case 3 :
                    // YAMLDSim/src/util/Scenario2.g:61:5: valn= ID
                    {
                    valn=(Token)match(input,ID,FOLLOW_ID_in_assignment164); 
                    val = gvar.getValue(valn.getText());

                    }
                    break;
                case 4 :
                    // YAMLDSim/src/util/Scenario2.g:62:5: valn= INT
                    {
                    valn=(Token)match(input,INT,FOLLOW_INT_in_assignment174); 
                    val = gvar.getValue(Integer.parseInt(valn.getText()));

                    }
                    break;

            }

            match(input,15,FOLLOW_15_in_assignment182); 
            if (var != null) {
                ass.put(var,val);
              }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "assignment"


    // $ANTLR start "trans"
    // YAMLDSim/src/util/Scenario2.g:69:1: trans[Network net, Map<YAMLDComponent, MMLDRule> rules] returns [float time] : 'Transition' (i= INT | f= FLOAT ) '=' '{' ( comp_trans[net, rules] )* '}' ';' ;
    public final float trans(Network net, Map<YAMLDComponent, MMLDRule> rules) throws RecognitionException {
        float time = 0.0f;

        Token i=null;
        Token f=null;

        try {
            // YAMLDSim/src/util/Scenario2.g:69:77: ( 'Transition' (i= INT | f= FLOAT ) '=' '{' ( comp_trans[net, rules] )* '}' ';' )
            // YAMLDSim/src/util/Scenario2.g:70:3: 'Transition' (i= INT | f= FLOAT ) '=' '{' ( comp_trans[net, rules] )* '}' ';'
            {
            match(input,20,FOLLOW_20_in_trans202); 
            // YAMLDSim/src/util/Scenario2.g:71:3: (i= INT | f= FLOAT )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==INT) ) {
                alt4=1;
            }
            else if ( (LA4_0==FLOAT) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // YAMLDSim/src/util/Scenario2.g:72:5: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_trans215); 
                    time = (float)Integer.parseInt(i.getText());

                    }
                    break;
                case 2 :
                    // YAMLDSim/src/util/Scenario2.g:74:5: f= FLOAT
                    {
                    f=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_trans229); 
                    time = Float.parseFloat(f.getText());

                    }
                    break;

            }

            match(input,12,FOLLOW_12_in_trans240); 
            match(input,13,FOLLOW_13_in_trans242); 
            // YAMLDSim/src/util/Scenario2.g:76:11: ( comp_trans[net, rules] )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // YAMLDSim/src/util/Scenario2.g:76:11: comp_trans[net, rules]
            	    {
            	    pushFollow(FOLLOW_comp_trans_in_trans244);
            	    comp_trans(net, rules);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,14,FOLLOW_14_in_trans248); 
            match(input,15,FOLLOW_15_in_trans250); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return time;
    }
    // $ANTLR end "trans"


    // $ANTLR start "comp_trans"
    // YAMLDSim/src/util/Scenario2.g:79:1: comp_trans[Network net, Map<YAMLDComponent, MMLDRule> rules] : cn= ID '.' rulename= ID ';' ;
    public final void comp_trans(Network net, Map<YAMLDComponent, MMLDRule> rules) throws RecognitionException {
        Token cn=null;
        Token rulename=null;

        try {
            // YAMLDSim/src/util/Scenario2.g:79:61: (cn= ID '.' rulename= ID ';' )
            // YAMLDSim/src/util/Scenario2.g:80:3: cn= ID '.' rulename= ID ';'
            {
            cn=(Token)match(input,ID,FOLLOW_ID_in_comp_trans263); 
            match(input,16,FOLLOW_16_in_comp_trans265); 
            rulename=(Token)match(input,ID,FOLLOW_ID_in_comp_trans269); 
            match(input,15,FOLLOW_15_in_comp_trans271); 
            final YAMLDComponent comp = net.getComponent(cn.getText());
              MMLDRule rule = comp.getRule(rulename.getText());
              if (rule == null) {
                // rule == "something_default_rule"
                final String transName = rulename.getText().substring(0,rulename.getText().length()-13);
                final MMLDTransition trans = comp.getTransition(transName);
                rule = trans.getDefaultRule();
              }
              rules.put(comp,rule);
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "comp_trans"

    // Delegated rules


 

    public static final BitSet FOLLOW_state_in_scenario45 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_trans_in_scenario66 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_state_in_scenario74 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_11_in_state97 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_state99 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_state101 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_assignment_in_state103 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_14_in_state107 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_state109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_assignment123 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_assignment125 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_assignment129 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_assignment131 = new BitSet(new long[]{0x00000000000C0030L});
    public static final BitSet FOLLOW_18_in_assignment146 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_19_in_assignment154 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_assignment164 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_INT_in_assignment174 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_assignment182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_trans202 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_INT_in_trans215 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_FLOAT_in_trans229 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_trans240 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_trans242 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_comp_trans_in_trans244 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_14_in_trans248 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_trans250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_comp_trans263 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_comp_trans265 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_trans269 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_comp_trans271 = new BitSet(new long[]{0x0000000000000002L});

}
