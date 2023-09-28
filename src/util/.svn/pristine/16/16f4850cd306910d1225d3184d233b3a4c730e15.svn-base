// //////////////////////////////////////////////
// (c) NICTA 2010
// Alban Grastien <Alban.Grastien@nicta.com.au>
// //////////////////////////////////////////////

grammar TimedScenario;

@header{
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
}

@lexer::header {
package util;
}

scenario[Network net] returns [TimedScenario ts]: 
    {
        if (net == null) {
            throw new IllegalArgumentException("Null network.");
        }
    }
    initialState = state[net]
    initialTimeInterval = timeinterval
    {
        TimedState ts1 = new MapTimedState(initialState);
        TimedScenario tsce1 = new EmptyTimedScenario(ts1,new Time(initialTimeInterval.first()));
        TimedScenario tsce2 = new DelayedScenario(tsce1,
            new Period(initialTimeInterval.second() - initialTimeInterval.first()));
    }
    result = transitions[net,tsce2]
    {ts = result;}
;

state[Network net] returns [State st]: 
    OPENBRACK
    {
        final Map<YAMLDVar,YAMLDValue> ass = new HashMap<YAMLDVar,YAMLDValue>();
    }
    assignments[net,ass]
    {
        st = new ExplicitState(net,ass);
    }
    CLOSEBRACK
;

assignments[Network net, Map<YAMLDVar,YAMLDValue> ass]: 
        assignment[net,ass]
        (
            COMMA 
            assignment[net,ass]
        )*
;

assignment[Network net, Map<YAMLDVar,YAMLDValue> ass]: 
    comp_name=ID
    {
        final YAMLDComponent comp = net.getComponent($comp_name.getText());
        if (comp == null) {
            throw new IllegalStateException("Unknown component " + $comp_name.getText());
        }
    }
    DOT
    var_name=ID
    {
        final YAMLDGenericVar var = comp.getVariable($var_name.getText());
        if (var == null) {
            throw new IllegalStateException("Unknown variable " + $var_name.getText()
                                          + " for component " + comp.name());
        }
        if (ass.containsKey(var)) {
            throw new IllegalStateException("Variable " + $var_name.getText()
                                          + " of component " + comp.name() 
                                          + " already assigned");
        }
    }
    EQUA
    {
        YAMLDValue val = null;
        String stringVal = null;
    }
    (
        val_id=ID 
        {
            stringVal = $val_id.getText();
            val = var.getValue(stringVal);
        }
      | val_int=INT
        {
            stringVal = $val_int.getText();
            val = var.getValue(Integer.parseInt(stringVal));
        }
    )
    {
        if (val == null) {
            throw new IllegalStateException("Unknown value " + stringVal 
                                          + " for variable " + var.toFormattedString());
        }
        if (var instanceof YAMLDVar) {
            final YAMLDVar v = (YAMLDVar)var;
            ass.put(v,val);
        }
    }
;

timeinterval returns [Pair<Float,Float> interval]: 
    SQOPENBRACK
    f1 = FLOAT
    MINUS
    f2 = FLOAT
    SQCLOSEBRACK
    {
        interval = new Pair<Float,Float>(Float.parseFloat($f1.getText())
                                        ,Float.parseFloat($f2.getText()));
    }
;

transitions[Network net, TimedScenario inputTSce] returns [TimedScenario result]: 
    {
        TimedScenario tsce = inputTSce;
    }
    (
        {
            Map<YAMLDComponent, MMLDRule> ruleMap = new HashMap<YAMLDComponent, MMLDRule>();
        }
        transition[net,ruleMap]
        {
            MMLDGlobalTransition glob = new ExplicitGlobalTransition(ruleMap);
            tsce = new IncrementalTimedScenario(tsce,glob);
        }
        state[net]
        timeInterval = timeinterval
        {
            tsce = new DelayedScenario(tsce,
                new Period(timeInterval.second() - timeInterval.first()));
        }
    )*
    {
        result = tsce;
    }
;

transition[Network net, Map<YAMLDComponent, MMLDRule> ruleMap]:
    TRANS
    OPENBRACK
    rules[net,ruleMap]
    CLOSEBRACK
    TRANS
;

rules[Network net, Map<YAMLDComponent, MMLDRule> ruleMap]: 
    rule[net,ruleMap]
    (
        COMMA
        rule[net, ruleMap]
    )*
;

rule[Network net, Map<YAMLDComponent, MMLDRule> ruleMap]: 
    comp_name = ID 
    {
        YAMLDComponent comp = net.getComponent($comp_name.getText());
        if (comp == null) {
            throw new IllegalStateException("Unknown component " + $comp_name.getText());
        }
    }
    EQUA 
    rule_name = ID
    {
        MMLDRule rule = comp.getRule($rule_name.getText());
        if (rule == null) {
            throw new IllegalStateException("Unknown rule of name " + $rule_name.getText() 
                + " for component " + comp);
        }
        ruleMap.put(comp,rule);
    }
;

// /////////////////////////////////////
// Lexicographical definitions
// /////////////////////////////////////
REMCPP:       '//'.*('\r'|'\n') { skip(); } ; 
REMC:         '/*'.*'*/' { skip(); } ;
WS:           (' '|'\t'|'\r'|'\n')+ { skip(); } ;
OPENBRACK:    '{';
CLOSEBRACK:   '}';
SQOPENBRACK:  '[';
SQCLOSEBRACK: ']';
EQUA:         '=';
TRANS:        '--->';
COMMA:        ',';
MINUS:        '-';
ID:           ('_')?('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
DOT:          '.';
fragment 
DIGIT:  ('0'..'9') ;
FLOAT:  DIGIT+ '.' DIGIT+; 
INT:    DIGIT+; 
