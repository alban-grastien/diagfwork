// //////////////////////////////////////////////
// (c) NICTA 2010
// Andreas Bauer <Andreas.Bauer@nicta.com.au>
// Alban Grastien <Alban.Grastien@nicta.com.au>
// //////////////////////////////////////////////

grammar MMLDlight;

@header {
package lang;

import util.Coords;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
}

@lexer::header {
package lang;
}

@members {
public static Network net = null;
public static State st = null;
}

// /////////////////////////////////////
// Parser definition
// /////////////////////////////////////

net:
      {
        net = new Network();
        final YAMLDTempConnections tempConn = new YAMLDTempConnections();
      }
      (comp[tempConn])+
      { 
        tempConn.copyConnections(net); 
        final Map<YAMLDVar,YAMLDValue> init = new HashMap<YAMLDVar,YAMLDValue>();
      }
      (synchro | observable | state[init])*
      {
        st = new ExplicitState(net,init);
      }
    ;
     
comp[YAMLDTempConnections tempConn]:
      { YAMLDComponent tempComp = new YAMLDComponent(); }
      'component' comp_name=ID EQUALS
      {
        tempComp.setName($comp_name.getText());
        Set<YAMLDEvent> in = new HashSet<YAMLDEvent>(); // input events
        Set<YAMLDEvent> out = new HashSet<YAMLDEvent>(); // output events
        Set<YAMLDEvent> u = new HashSet<YAMLDEvent>(); // unknown (so far)
      }
      '{' (comp_def[tempComp,in,out,u,tempConn])* '}'
      { 
        net.addComponent(tempComp);
      } 
    ;

comp_def[YAMLDComponent tempComp,
         Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u, 
         YAMLDTempConnections tempConn]:
    (
       coords_def[tempComp]
     | var_def[tempComp]
     | event_def[tempComp,u]
     | connection_def[tempComp,tempConn]
     | trans_def[tempComp,in,out,u]
     | constraint_def[tempComp]
    ) 
    {
      for (final YAMLDEvent e: u) {
        e.setInput(false);
      }
    }
	;

coords_def[YAMLDComponent tempComp]:
  'coords' x=INT ',' y=INT ';'
      {
        tempComp.visOptions().
            coords().
                add(new Coords(Integer.parseInt($x.getText()),
                               Integer.parseInt($y.getText())));
      }
;

var_def[YAMLDComponent tempComp]:
        {
          boolean isDependent = true;
          YAMLDGenericVar var; 
        } 
    (
      'var' {isDependent = false;}
      | 
      'dvar' {isDependent = true;}
    )
    var_name=ID ':'
        {
          String varName = $var_name.getText();
          if (isDependent) {
            YAMLDDVar v = new YAMLDDVar(varName,tempComp);
            tempComp.addDVar(v);
            var = v;
          } else {
            YAMLDVar v = new YAMLDVar(varName,tempComp);
            tempComp.addVar(v);
            var = v;
          }
        } 
    (
      ( 
        '[' var_dom_beg=INT '..' var_dom_end=INT ']' 
          {
            var.setRange(Integer.parseInt($var_dom_beg.getText()),
                             Integer.parseInt($var_dom_end.getText()));
          }
      )
      |
      (
          { 
            ArrayList<String> domElems = new ArrayList<String>(); 
          }
      '{' var_dom[domElems] '}' 
          {
            for (String val : domElems)
              var.domainPush(val);
          }
      )
    )
    ';'
;

event_def[YAMLDComponent tempComp, Set<YAMLDEvent> u]:
      'event' event_name=ID ';' 
      { 
        final YAMLDEvent e = new YAMLDEvent(tempComp,$event_name.getText()); 
        tempComp.addEvent(e);
        u.add(e); 
      }
;

connection_def[YAMLDComponent tempComp,YAMLDTempConnections tempConn]:
  'connection' conn_name=ID ':'  conn_type=ID EQUALS comp_name=ID ';'
      {
        tempConn.add(tempComp, $comp_name.getText(),
                     $conn_name.getText(),$conn_type.getText());
      }
;

trans_def[YAMLDComponent tempComp, Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u]:
  {
    final Set<YAMLDEvent> triggeringEvents = new HashSet<YAMLDEvent>();
    final Map<YAMLDFormula,PeriodInterval> precond = new HashMap<YAMLDFormula,PeriodInterval>(); 
    final Collection<MMLDRule> rules = new ArrayList<MMLDRule>();
  }
  'transition' trans_name=ID rule_def[tempComp,out,u,rules]* 
  ( 
    'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond]
    (',' triggering_cond[tempComp,in,u,triggeringEvents,precond])*
    ';' 
  )? 
  {
    final MMLDTransition trans = new MMLDTransition($trans_name.getText(), 
      tempComp, rules, triggeringEvents, precond);
    tempComp.addTransition(trans);
  }
;

rule_def[YAMLDComponent tempComp, 
  Set<YAMLDEvent> out, Set<YAMLDEvent> u, 
  Collection<MMLDRule> rules]: 
  rule_name=ID f=formula[tempComp]
    {
      ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>();
      Set<YAMLDEvent> emitted = new HashSet<YAMLDEvent>();
    } 
  ( 
    '->' assignment_or_event[tempComp,out,u,emitted,al] 
         ( ',' assignment_or_event[tempComp,out,u,emitted,al] )* 
  )? 
  ';'
    {
      MMLDRule rule = new MMLDRule(tempComp, $f.form, al, 
            emitted, $rule_name.getText());
      rules.add(rule);
    }
;

assignment_or_event[YAMLDComponent tempComp, 
    Set<YAMLDEvent> out, Set<YAMLDEvent> u, Collection<YAMLDEvent> emitted,  
    ArrayList<YAMLDAssignment> al]:
    assignment[tempComp,al]
  | 
    event_name=ID
    {
      final YAMLDEvent evt = tempComp.getEvent($event_name.getText());
      if (evt == null) {
        throw new org.antlr.runtime.FailedPredicateException(
            input, "Unknown event", $event_name.getText()
        );
      }
      if (!out.contains(evt)) {
        if (!u.remove(evt)) {
          throw new org.antlr.runtime.FailedPredicateException(
            input, "Effect of transition", $event_name.getText()
          );
        }
        evt.setInput(false);
        out.add(evt);
      }
      emitted.add(evt);
    }
;

triggering_cond[YAMLDComponent tempComp, 
  Set<YAMLDEvent> in, Set<YAMLDEvent> u, Set<YAMLDEvent> triggeringEvents, 
  Map<YAMLDFormula,PeriodInterval> precond]: 
    ( 
      event_name=ID
      {
        final YAMLDEvent evt = tempComp.getEvent($event_name.getText());
        if (evt == null) {
            throw new org.antlr.runtime.FailedPredicateException(
              input, "Unknown event", $event_name.getText()
            );
        }
        if (!in.contains(evt)) {
          if (!u.remove(evt)) {
            throw new org.antlr.runtime.FailedPredicateException(
              input, "Triggering condition", $event_name.getText()
            );
          }
        }
        evt.setInput(true);
        triggeringEvents.add(evt);
        in.add(evt);
      }
    ) 
  | 
    ( 
      '[' earliest=FLOAT '..' latest=FLOAT ']' f=formula[tempComp]
       {
         final PeriodInterval ti = new PeriodInterval(
           new Period(Double.parseDouble($earliest.getText())), 
           new Period(Double.parseDouble($latest.getText()))
           );
         final YAMLDFormula form = $f.form;
         precond.put(form,ti); 
       }
    )
;

constraint_def[YAMLDComponent tempComp]: 
    'constraint' f=formula[tempComp] ';'
      { 
        final YAMLDConstraint con = new YAMLDConstraint($f.form, tempComp);
        final YAMLDDVar var = con.getVariable();
        var.addConstraint(con);
      }
;

var_dom[ArrayList<String> domElems]:
      dom_elem=ID
      { domElems.add($dom_elem.getText()); } 
    | dom_elem=ID ',' var_dom[domElems] 
      { domElems.add($dom_elem.getText()); }       
    ;

formula[YAMLDComponent tempComp] returns [YAMLDFormula form]:
      co=conjunction[tempComp] f=f_prime[tempComp,$co.form]
      { $form = $f.form; }
    ;

conjunction[YAMLDComponent tempComp] returns [YAMLDFormula form]: 
      at=atomic[tempComp] f=c_prime[tempComp,$at.form]
      { $form = $f.form; }
    ;

atomic[YAMLDComponent tempComp] returns [YAMLDFormula form]:
      e1=expression[tempComp] EQUALS e2=value_expression[tempComp] 
      {$form = new YAMLDEqFormula($e1.expr, $e2.expr);}
    /* TODO: True/false can be both, formula and expression in the parser. */
    | 'FALSE'
      { $form = YAMLDFalse.FALSE; } 
    | 'TRUE'
      { $form = YAMLDTrue.TRUE; }
    | 'exists' connt=ID '(' first=ID '..' last=ID ')' '(' fo=formula[null] ')'
      {
        String cf = $first.getText();
        if (cf.equals("this")) {
          cf = tempComp.name();
        }
        String cl = $last.getText();
        if (cl.equals("this")) {
          cl = tempComp.name();
        }
        $form = new YAMLDStringExistsPath(cf, cl, YAMLDConnType.getType($connt.getText()),$fo.form);
      }
    | 'exists' connt=ID '(' first=ID '..' mid=ID '..' last=ID ')' '(' fo=formula[null] ')'
      {
        String cf = $first.getText();
        if (cf.equals("this")) {
          cf = tempComp.name();
        }
        String cl = $last.getText();
        if (cl.equals("this")) {
          cl = tempComp.name();
        }
        String cm = $mid.getText();
        if (cm.equals("this")) {
          cm = tempComp.name();
        }
        $form = new YAMLDStringExistsPath(cf, cl, cm, YAMLDConnType.getType($connt.getText()),$fo.form);
      }
    | '(' f=formula[tempComp] ')'
      { $form = $f.form; }
    | 'NOT' at=atomic[tempComp]
      { $form = new YAMLDNotFormula($at.form); }
    ;

c_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form]:
      'AND' f2=conjunction[tempComp]
      { $form = (YAMLDAndFormula)(new YAMLDAndFormula($f1, $f2.form)); }
    | // epsilon
      { $form = $f1; }
    ;

f_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form]:
      'OR' f2=formula[tempComp]
      { $form = (YAMLDOrFormula)(new YAMLDOrFormula($f1, $f2.form)); }
    | // epsilon
      { $form = $f1; }
    ;
    
expression[YAMLDComponent tempComp] returns [YAMLDExpr expr]:
      s1=simple_expression[tempComp] e1=exp_prime[tempComp,$s1.expr]
      { $expr = $e1.expr; }
    ;
    
exp_prime[YAMLDComponent tempComp,YAMLDExpr e] returns [YAMLDExpr expr]:    
      '+' e2=expression[tempComp]
      { $expr = new YAMLDAddExpr($e, $e2.expr); }
    | // epsilon
      { $expr = $e; }
    ;
    
simple_expression[YAMLDComponent tempComp] returns [YAMLDExpr expr]:
      num=INT 
      { $expr = YAMLDValue.getValue(Integer.parseInt($num.getText())); }
    | 'true'
      { $expr = YAMLDValue.getValue(1); }
    | 'false'
      { $expr = YAMLDValue.getValue(0); }
    | comp_name=ID '.' var_name=ID
      {
        YAMLDID newID = new YAMLDID($var_name.getText());
        newID.setOwner($comp_name.getText());
        $expr = (YAMLDExpr)(newID); 
      }
    | var_name=ID
      { 
        final String name = $var_name.getText();
        if (YAMLDValue.existsValue(name)) {
          $expr = YAMLDValue.getValue(name);
        } else if (tempComp != null) {
          YAMLDGenericVar var = tempComp.getVariable(name);          
          if (var == null) {
            throw new org.antlr.runtime.FailedPredicateException(
              input, "No variable " + name + " for component " + tempComp.name(), name
            );
          }

          $expr = new VariableValue(var);
        } else {
          YAMLDID newID = new YAMLDID(name);
          $expr = newID;
        } 
      }
    ; 

value_expression[YAMLDComponent tempComp] returns [YAMLDExpr expr]:
      num=INT 
      { $expr = YAMLDValue.getValue(Integer.parseInt($num.getText())); }
    | 'true'
      { $expr = YAMLDValue.getValue(1); }
    | 'false'
      { $expr = YAMLDValue.getValue(0); }
    | var_name=ID
      {
        final String name = $var_name.getText();
        $expr = YAMLDValue.getValue(name);
      }
    ;

assignment_list[YAMLDComponent comp, ArrayList<YAMLDAssignment> al]:
      assignment[comp,al] (',' assignment[comp,al])*
    ;

assignment[YAMLDComponent comp, ArrayList<YAMLDAssignment> al]:
      var_name=ID ASSGN e1=expression[comp]
      { 
        $al.add(new YAMLDAssignment(comp.getVariable($var_name.getText()),
                $e1.expr)); 
      }
    ;

synchro: 
      'synchronize' c1=ID '.'e1=ID
      {
        final YAMLDComponent comp1 = net.getComponent($c1.getText());
        final YAMLDEvent ev1 = comp1.getEvent($e1.getText());
        final Collection<YAMLDEvent> events = new HashSet<YAMLDEvent>();
      }
      (',' c2=ID '.' e2=ID                                                                                                                                                           
      {
        final YAMLDComponent comp2 = net.getComponent($c2.getText());
        final YAMLDEvent ev2 = comp2.getEvent($e2.getText());
        events.add(ev2);
      }
      )+ ';'
      {
        final MMLDSynchro s = new MMLDSynchro(ev1,events);
        net.addSynchro(s);
        ev1.addSynchro(s);
        for (final YAMLDEvent e: events) {
          e.addSynchro(s);
        }
      }
;

observable:
     'observable' cname=ID '.' ename=ID ';'
     {
       final YAMLDComponent comp = net.getComponent($cname.getText());
       if (comp == null) {
         throw new org.antlr.runtime.FailedPredicateException(
            input, "Unknown component", $cname.getText()
        );
       }
       final YAMLDEvent e = comp.getEvent($ename.getText());
       // System.out.println("read observable " + e.toFormattedString());
       net.addObservableEvent(e);
     }
    ;

state[Map<YAMLDVar,YAMLDValue> map]:
      cname=ID '.' vname=ID ':=' 'true' ';'
      {
        final YAMLDComponent comp = net.getComponent($cname.getText());
        final YAMLDGenericVar gvar = comp.getVariable($vname.getText());
        if (gvar instanceof YAMLDVar) {
            final YAMLDVar var = (YAMLDVar)gvar;
            final YAMLDValue val = var.getValue(1);
            map.put(var,val);
        }
        
      }
    | cname=ID '.' vname=ID ':=' 'false' ';'
      {
        final YAMLDComponent comp = net.getComponent($cname.getText());
        final YAMLDGenericVar gvar = comp.getVariable($vname.getText());
        if (gvar instanceof YAMLDVar) {
            final YAMLDVar var = (YAMLDVar)gvar;
            final YAMLDValue val = var.getValue(0);
            map.put(var,val);
        }
        
      }
    | cname=ID '.' vname=ID ':=' valname=ID ';'
      {
        final YAMLDComponent comp = net.getComponent($cname.getText());
        final YAMLDGenericVar gvar = comp.getVariable($vname.getText());
        if (gvar instanceof YAMLDVar) {
            final YAMLDVar var = (YAMLDVar)gvar;
            final YAMLDValue val = var.getValue($valname.getText());
            map.put(var,val);
        }
        
      }
    | cname=ID '.' vname=ID ':=' valname=INT ';'
      {
        final YAMLDComponent comp = net.getComponent($cname.getText());
        final YAMLDGenericVar gvar = comp.getVariable($vname.getText());
        if (gvar instanceof YAMLDVar) {
            final YAMLDVar var = (YAMLDVar)gvar;
            final YAMLDValue val = var.getValue(Integer.parseInt($valname.getText()));
            map.put(var,val);
        }
        
      }
    ;
 
// /////////////////////////////////////
// Lexicographical definitions
// /////////////////////////////////////
REMCPP: '//'.*('\r'|'\n') { skip(); } ; 
REMC:   '/*'.*'*/' { skip(); } ;
WS:     (' '|'\t'|'\r'|'\n')+ { skip(); } ;
ASSGN:  ':=' ;
fragment 
DIGIT:  ('0'..'9') ;
INT:    DIGIT+ ( ('.' ~'.')=> '.' DIGIT* {$type=FLOAT;} )?;
FLOAT:  '.' DIGIT+; 
ID:     ('_')?('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
EQUALS: '=' ;
