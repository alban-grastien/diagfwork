// $ANTLR 3.2 Sep 23, 2009 12:02:23 TimedScenario.g 2011-11-14 14:06:03

package util;

import edu.supercom.util.Pair;
import java.util.HashMap;
import java.util.Map;
import lang.DelayedTimedState;
import lang.ExplicitState;
import lang.MapTimedState;
import lang.MMLDRule;
import lang.Network;
import lang.Period;
import lang.State;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDGenericVar;
import lang.YAMLDValue;
import lang.YAMLDVar;
import util.TimedScenario;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimedScenarioParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OPENBRACK", "CLOSEBRACK", "COMMA", "ID", "DOT", "EQUA", "INT", "SQOPENBRACK", "FLOAT", "MINUS", "SQCLOSEBRACK", "TRANS", "REMCPP", "REMC", "WS", "DIGIT"
    };
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


        public TimedScenarioParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TimedScenarioParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TimedScenarioParser.tokenNames; }
    public String getGrammarFileName() { return "TimedScenario.g"; }



    // $ANTLR start "scenario"
    // TimedScenario.g:33:1: scenario[Network net] returns [TimedScenario ts] : initialState= state[net] initialTimeInterval= timeinterval result= transitions[net,tsce2] ;
    public final TimedScenario scenario(Network net) throws RecognitionException {
        TimedScenario ts = null;

        State initialState = null;

        Pair<Float,Float> initialTimeInterval = null;

        TimedScenario result = null;


        try {
            // TimedScenario.g:33:49: (initialState= state[net] initialTimeInterval= timeinterval result= transitions[net,tsce2] )
            // TimedScenario.g:34:5: initialState= state[net] initialTimeInterval= timeinterval result= transitions[net,tsce2]
            {

                    if (net == null) {
                        throw new IllegalArgumentException("Null network.");
                    }
                
            pushFollow(FOLLOW_state_in_scenario48);
            initialState=state(net);

            state._fsp--;

            pushFollow(FOLLOW_timeinterval_in_scenario59);
            initialTimeInterval=timeinterval();

            state._fsp--;


                    TimedState ts1 = new MapTimedState(initialState);
                    TimedScenario tsce1 = new EmptyTimedScenario(ts1,new Time(initialTimeInterval.first()));
                    TimedScenario tsce2 = new DelayedScenario(tsce1,
                        new Period(initialTimeInterval.second() - initialTimeInterval.first()));
                
            pushFollow(FOLLOW_transitions_in_scenario75);
            result=transitions(net, tsce2);

            state._fsp--;

            ts = result;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ts;
    }
    // $ANTLR end "scenario"


    // $ANTLR start "state"
    // TimedScenario.g:51:1: state[Network net] returns [State st] : OPENBRACK assignments[net,ass] CLOSEBRACK ;
    public final State state(Network net) throws RecognitionException {
        State st = null;

        try {
            // TimedScenario.g:51:38: ( OPENBRACK assignments[net,ass] CLOSEBRACK )
            // TimedScenario.g:52:5: OPENBRACK assignments[net,ass] CLOSEBRACK
            {
            match(input,OPENBRACK,FOLLOW_OPENBRACK_in_state100); 

                    final Map<YAMLDVar,YAMLDValue> ass = new HashMap<YAMLDVar,YAMLDValue>();
                
            pushFollow(FOLLOW_assignments_in_state112);
            assignments(net, ass);

            state._fsp--;


                    st = new ExplicitState(net,ass);
                
            match(input,CLOSEBRACK,FOLLOW_CLOSEBRACK_in_state125); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return st;
    }
    // $ANTLR end "state"


    // $ANTLR start "assignments"
    // TimedScenario.g:63:1: assignments[Network net, Map<YAMLDVar,YAMLDValue> ass] : assignment[net,ass] ( COMMA assignment[net,ass] )* ;
    public final void assignments(Network net, Map<YAMLDVar,YAMLDValue> ass) throws RecognitionException {
        try {
            // TimedScenario.g:63:55: ( assignment[net,ass] ( COMMA assignment[net,ass] )* )
            // TimedScenario.g:64:9: assignment[net,ass] ( COMMA assignment[net,ass] )*
            {
            pushFollow(FOLLOW_assignment_in_assignments143);
            assignment(net, ass);

            state._fsp--;

            // TimedScenario.g:65:9: ( COMMA assignment[net,ass] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==COMMA) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // TimedScenario.g:66:13: COMMA assignment[net,ass]
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_assignments168); 
            	    pushFollow(FOLLOW_assignment_in_assignments183);
            	    assignment(net, ass);

            	    state._fsp--;


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
        return ;
    }
    // $ANTLR end "assignments"


    // $ANTLR start "assignment"
    // TimedScenario.g:71:1: assignment[Network net, Map<YAMLDVar,YAMLDValue> ass] : comp_name= ID DOT var_name= ID EQUA (val_id= ID | val_int= INT ) ;
    public final void assignment(Network net, Map<YAMLDVar,YAMLDValue> ass) throws RecognitionException {
        Token comp_name=null;
        Token var_name=null;
        Token val_id=null;
        Token val_int=null;

        try {
            // TimedScenario.g:71:54: (comp_name= ID DOT var_name= ID EQUA (val_id= ID | val_int= INT ) )
            // TimedScenario.g:72:5: comp_name= ID DOT var_name= ID EQUA (val_id= ID | val_int= INT )
            {
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_assignment211); 

                    final YAMLDComponent comp = net.getComponent(comp_name.getText());
                    if (comp == null) {
                        throw new IllegalStateException("Unknown component " + comp_name.getText());
                    }
                
            match(input,DOT,FOLLOW_DOT_in_assignment223); 
            var_name=(Token)match(input,ID,FOLLOW_ID_in_assignment231); 

                    final YAMLDGenericVar var = comp.getVariable(var_name.getText());
                    if (var == null) {
                        throw new IllegalStateException("Unknown variable " + var_name.getText()
                                                      + " for component " + comp.name());
                    }
                    if (ass.containsKey(var)) {
                        throw new IllegalStateException("Variable " + var_name.getText()
                                                      + " of component " + comp.name() 
                                                      + " already assigned");
                    }
                
            match(input,EQUA,FOLLOW_EQUA_in_assignment243); 

                    YAMLDValue val = null;
                    String stringVal = null;
                
            // TimedScenario.g:98:5: (val_id= ID | val_int= INT )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            else if ( (LA2_0==INT) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // TimedScenario.g:99:9: val_id= ID
                    {
                    val_id=(Token)match(input,ID,FOLLOW_ID_in_assignment267); 

                                stringVal = val_id.getText();
                                val = var.getValue(stringVal);
                            

                    }
                    break;
                case 2 :
                    // TimedScenario.g:104:9: val_int= INT
                    {
                    val_int=(Token)match(input,INT,FOLLOW_INT_in_assignment290); 

                                stringVal = val_int.getText();
                                val = var.getValue(Integer.parseInt(stringVal));
                            

                    }
                    break;

            }


                    if (val == null) {
                        throw new IllegalStateException("Unknown value " + stringVal 
                                                      + " for variable " + var.toFormattedString());
                    }
                    if (var instanceof YAMLDVar) {
                        final YAMLDVar v = (YAMLDVar)var;
                        ass.put(v,val);
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


    // $ANTLR start "timeinterval"
    // TimedScenario.g:122:1: timeinterval returns [Pair<Float,Float> interval] : SQOPENBRACK f1= FLOAT MINUS f2= FLOAT SQCLOSEBRACK ;
    public final Pair<Float,Float> timeinterval() throws RecognitionException {
        Pair<Float,Float> interval = null;

        Token f1=null;
        Token f2=null;

        try {
            // TimedScenario.g:122:50: ( SQOPENBRACK f1= FLOAT MINUS f2= FLOAT SQCLOSEBRACK )
            // TimedScenario.g:123:5: SQOPENBRACK f1= FLOAT MINUS f2= FLOAT SQCLOSEBRACK
            {
            match(input,SQOPENBRACK,FOLLOW_SQOPENBRACK_in_timeinterval329); 
            f1=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_timeinterval339); 
            match(input,MINUS,FOLLOW_MINUS_in_timeinterval345); 
            f2=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_timeinterval355); 
            match(input,SQCLOSEBRACK,FOLLOW_SQCLOSEBRACK_in_timeinterval361); 

                    interval = new Pair<Float,Float>(Float.parseFloat(f1.getText())
                                                    ,Float.parseFloat(f2.getText()));
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return interval;
    }
    // $ANTLR end "timeinterval"


    // $ANTLR start "transitions"
    // TimedScenario.g:134:1: transitions[Network net, TimedScenario inputTSce] returns [TimedScenario result] : ( transition[net,ruleMap] state[net] timeInterval= timeinterval )* ;
    public final TimedScenario transitions(Network net, TimedScenario inputTSce) throws RecognitionException {
        TimedScenario result = null;

        Pair<Float,Float> timeInterval = null;


        try {
            // TimedScenario.g:134:81: ( ( transition[net,ruleMap] state[net] timeInterval= timeinterval )* )
            // TimedScenario.g:135:5: ( transition[net,ruleMap] state[net] timeInterval= timeinterval )*
            {

                    TimedScenario tsce = inputTSce;
                
            // TimedScenario.g:138:5: ( transition[net,ruleMap] state[net] timeInterval= timeinterval )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==TRANS) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // TimedScenario.g:139:9: transition[net,ruleMap] state[net] timeInterval= timeinterval
            	    {

            	                Map<YAMLDComponent, MMLDRule> ruleMap = new HashMap<YAMLDComponent, MMLDRule>();
            	            
            	    pushFollow(FOLLOW_transition_in_transitions411);
            	    transition(net, ruleMap);

            	    state._fsp--;


            	                MMLDGlobalTransition glob = new ExplicitGlobalTransition(ruleMap);
            	                tsce = new IncrementalTimedScenario(tsce,glob);
            	            
            	    pushFollow(FOLLOW_state_in_transitions432);
            	    state(net);

            	    state._fsp--;

            	    pushFollow(FOLLOW_timeinterval_in_transitions447);
            	    timeInterval=timeinterval();

            	    state._fsp--;


            	                tsce = new DelayedScenario(tsce,
            	                    new Period(timeInterval.second() - timeInterval.first()));
            	            

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


                    result = tsce;
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "transitions"


    // $ANTLR start "transition"
    // TimedScenario.g:159:1: transition[Network net, Map<YAMLDComponent, MMLDRule> ruleMap] : TRANS OPENBRACK rules[net,ruleMap] CLOSEBRACK TRANS ;
    public final void transition(Network net, Map<YAMLDComponent, MMLDRule> ruleMap) throws RecognitionException {
        try {
            // TimedScenario.g:159:63: ( TRANS OPENBRACK rules[net,ruleMap] CLOSEBRACK TRANS )
            // TimedScenario.g:160:5: TRANS OPENBRACK rules[net,ruleMap] CLOSEBRACK TRANS
            {
            match(input,TRANS,FOLLOW_TRANS_in_transition483); 
            match(input,OPENBRACK,FOLLOW_OPENBRACK_in_transition489); 
            pushFollow(FOLLOW_rules_in_transition495);
            rules(net, ruleMap);

            state._fsp--;

            match(input,CLOSEBRACK,FOLLOW_CLOSEBRACK_in_transition502); 
            match(input,TRANS,FOLLOW_TRANS_in_transition508); 

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
    // $ANTLR end "transition"


    // $ANTLR start "rules"
    // TimedScenario.g:167:1: rules[Network net, Map<YAMLDComponent, MMLDRule> ruleMap] : rule[net,ruleMap] ( COMMA rule[net, ruleMap] )* ;
    public final void rules(Network net, Map<YAMLDComponent, MMLDRule> ruleMap) throws RecognitionException {
        try {
            // TimedScenario.g:167:58: ( rule[net,ruleMap] ( COMMA rule[net, ruleMap] )* )
            // TimedScenario.g:168:5: rule[net,ruleMap] ( COMMA rule[net, ruleMap] )*
            {
            pushFollow(FOLLOW_rule_in_rules522);
            rule(net, ruleMap);

            state._fsp--;

            // TimedScenario.g:169:5: ( COMMA rule[net, ruleMap] )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // TimedScenario.g:170:9: COMMA rule[net, ruleMap]
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rules539); 
            	    pushFollow(FOLLOW_rule_in_rules549);
            	    rule(net, ruleMap);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
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
        return ;
    }
    // $ANTLR end "rules"


    // $ANTLR start "rule"
    // TimedScenario.g:175:1: rule[Network net, Map<YAMLDComponent, MMLDRule> ruleMap] : comp_name= ID EQUA rule_name= ID ;
    public final void rule(Network net, Map<YAMLDComponent, MMLDRule> ruleMap) throws RecognitionException {
        Token comp_name=null;
        Token rule_name=null;

        try {
            // TimedScenario.g:175:57: (comp_name= ID EQUA rule_name= ID )
            // TimedScenario.g:176:5: comp_name= ID EQUA rule_name= ID
            {
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_rule575); 

                    YAMLDComponent comp = net.getComponent(comp_name.getText());
                    if (comp == null) {
                        throw new IllegalStateException("Unknown component " + comp_name.getText());
                    }
                
            match(input,EQUA,FOLLOW_EQUA_in_rule588); 
            rule_name=(Token)match(input,ID,FOLLOW_ID_in_rule599); 

                    MMLDRule rule = comp.getRule(rule_name.getText());
                    if (rule == null) {
                        throw new IllegalStateException("Unknown rule of name " + rule_name.getText() 
                            + " for component " + comp);
                    }
                    ruleMap.put(comp,rule);
                

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
    // $ANTLR end "rule"

    // Delegated rules


 

    public static final BitSet FOLLOW_state_in_scenario48 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_timeinterval_in_scenario59 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_transitions_in_scenario75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPENBRACK_in_state100 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_assignments_in_state112 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSEBRACK_in_state125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_assignments143 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_assignments168 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_assignment_in_assignments183 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ID_in_assignment211 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DOT_in_assignment223 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_assignment231 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUA_in_assignment243 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_ID_in_assignment267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_assignment290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SQOPENBRACK_in_timeinterval329 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_FLOAT_in_timeinterval339 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_MINUS_in_timeinterval345 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_FLOAT_in_timeinterval355 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_SQCLOSEBRACK_in_timeinterval361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_transition_in_transitions411 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_state_in_transitions432 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_timeinterval_in_transitions447 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_TRANS_in_transition483 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_OPENBRACK_in_transition489 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rules_in_transition495 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSEBRACK_in_transition502 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_TRANS_in_transition508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_rules522 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_rules539 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rule_in_rules549 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ID_in_rule575 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUA_in_rule588 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_rule599 = new BitSet(new long[]{0x0000000000000002L});

}