// //////////////////////////////////////////////
// (c) NICTA 2010
// Alban Grastien <Alban.Grastien@nicta.com.au>
// //////////////////////////////////////////////

grammar Scenario2;

@header {
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
}

@lexer::header {
package util;
}

// /////////////////////////////////////
// Parser definition
// /////////////////////////////////////

scenario[Network net] returns [Scenario sce]: 
  {final Map<YAMLDVar,YAMLDValue> ass = new HashMap<YAMLDVar,YAMLDValue>();}
  state[net,ass]
  {$sce = new EmptyScenario(new ExplicitState(net,ass),Time.ZERO_TIME);}
  (
    {final Map<YAMLDComponent, MMLDRule> compToRule= new HashMap<YAMLDComponent, MMLDRule>();}
    trans[net,compToRule] 
    state[net,ass]
    {final State st = new ExplicitState(net,ass);
    final MMLDGlobalTransition gt = new ExplicitGlobalTransition(compToRule);
    //$sce = new IncrementalScenario(sce,gt,new Time($trans.time),st);}
    $sce = new IncrementalScenario(sce,gt,st);}
  )*
;

state[Network net,Map<YAMLDVar,YAMLDValue> ass]:
  'State' '=' '{' assignment[net,ass]* '}' ';'
;

assignment[Network net,Map<YAMLDVar,YAMLDValue> ass]: 
  cn=ID '.' vn=ID ':=' 
  {final YAMLDComponent comp = net.getComponent($cn.getText());
  final YAMLDGenericVar gvar = comp.getVariable($vn.getText());
  final YAMLDVar var = (gvar instanceof YAMLDVar)?((YAMLDVar)gvar):null;
  YAMLDValue val = null;}
  (
    'true' {val = gvar.getValue(1);}
  | 'false' {val = gvar.getValue(0);}
  | valn=ID {val = gvar.getValue($valn.getText());}
  | valn=INT {val = gvar.getValue(Integer.parseInt($valn.getText()));}
  ) ';'
  {if (var != null) {
    ass.put(var,val);
  }}
;

trans[Network net, Map<YAMLDComponent, MMLDRule> rules] returns [float time]: 
  'Transition' 
  (
    i=INT {$time = (float)Integer.parseInt($i.getText());}
  |
    f=FLOAT {$time = Float.parseFloat($f.getText());}
  ) 
  '=' '{' comp_trans[net, rules]* '}' ';'
;

comp_trans[Network net, Map<YAMLDComponent, MMLDRule> rules]:
  cn=ID '.' rulename=ID ';'
  {final YAMLDComponent comp = net.getComponent($cn.getText());
  MMLDRule rule = comp.getRule($rulename.getText());
  if (rule == null) {
    // rule == "something_default_rule"
    final String transName = $rulename.getText().substring(0,$rulename.getText().length()-13);
    final MMLDTransition trans = comp.getTransition(transName);
    rule = trans.getDefaultRule();
  }
  rules.put(comp,rule);
  }
;

// /////////////////////////////////////
// Lexicographical definitions
// /////////////////////////////////////
REMCPP: '//'.*('\r'|'\n') { skip(); } ; 
REMC:   '/*'.*'*/' { skip(); } ;
WS:     (' '|'\t'|'\r'|'\n')+ { skip(); } ;
ID:     ('_')?('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
fragment 
DIGIT:  ('0'..'9') ;
FLOAT:  DIGIT+ '.' DIGIT+; 
INT:    DIGIT+; 
