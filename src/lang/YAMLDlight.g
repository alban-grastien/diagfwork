// //////////////////////////////////////////////
// (c) NICTA 2010
// Andreas Bauer <Andreas.Bauer@nicta.com.au>
// //////////////////////////////////////////////

grammar YAMLDlight;

@header {
package lang;

import util.Coords;
import java.util.HashMap;
import java.util.Map;
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
      (synchro)+
      (observable)*
      (state[init])+
      {
        st = new ExplicitState(net,init);
      }
    ;
     
comp[YAMLDTempConnections tempConn]:
      { YAMLDComponent tempComp = new YAMLDComponent(); }
      'component' comp_name=ID EQUALS 
      {
        tempComp.setName($comp_name.getText());
      }
      '{' (comp_def[tempComp,tempConn])* '}'
      { 
        net.addComponent(tempComp);
      } 
    ;

comp_def[YAMLDComponent tempComp,YAMLDTempConnections tempConn]:
      'event' event_name=ID ';' 
      { tempComp.addEvent(new YAMLDEvent(tempComp,$event_name.getText())); }
    | 'coords' x=INT ',' y=INT ';'
      {
        tempComp.visOptions().
            coords().
                add(new Coords(Integer.parseInt($x.getText()),
                               Integer.parseInt($y.getText())));
      } 
    | 'var' var_name=ID ':' '[' var_dom_beg=INT '..' var_dom_end=INT ']' ';'
      { 
        YAMLDVar tempVar = new YAMLDVar($var_name.getText(),tempComp);
        tempVar.setRange(Integer.parseInt($var_dom_beg.getText()),
                         Integer.parseInt($var_dom_end.getText()));
        tempComp.addVar(tempVar); 
      } 
    | { ArrayList<String> domElems = new ArrayList<String>(); }
      'var' var_name=ID ':' '{' var_dom[domElems] '}' ';'
      {
        YAMLDVar tempVar = new YAMLDVar($var_name.getText(),tempComp);
        for (String varName : domElems)
            tempVar.domainPush(varName);
        tempComp.addVar(tempVar);
      }
    | 'dvar' var_name=ID ':' '[' var_dom_beg=INT '..' var_dom_end=INT ']' ';' 
      { 
        YAMLDDVar tempVar = new YAMLDDVar($var_name.getText(),tempComp);
        tempVar.setRange(Integer.parseInt($var_dom_beg.getText()),
                         Integer.parseInt($var_dom_end.getText()));
        tempComp.addDVar(tempVar); 
      } 
    | { ArrayList<String> domElems = new ArrayList<String>(); }
      'dvar' var_name=ID ':' '{' var_dom[domElems] '}' ';'
      {
        YAMLDDVar tempVar = new YAMLDDVar($var_name.getText(),tempComp);
        for (String varName : domElems)
            tempVar.domainPush(varName);
        tempComp.addDVar(tempVar);
      }
    | { ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); }
      'transition' event_name=ID ':' f=formula[tempComp] '->' assignment_list[tempComp,al] ';'
      { 
        YAMLDTrans tempTrans = 
            new YAMLDTrans(tempComp,tempComp.getEvent($event_name.getText()), $f.form);
        tempTrans.setAssignments(al);
        tempComp.addTrans(tempTrans);
      }
    | { ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); }
      'transition' f=formula[tempComp] '->' assignment_list[tempComp,al] ';'
      { 
        YAMLDTrans tempTrans = new YAMLDTrans(tempComp,$f.form);
        tempTrans.setAssignments(al);
        tempComp.addTrans(tempTrans);
      }
    | { ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); }
      'forced' 'transition' event_name=ID ':' f=formula[tempComp] '[' earliest=FLOAT '..' latest=FLOAT ']' '->' assignment_list[tempComp,al] ';'
      { 
        YAMLDTrans tempTrans = 
            new YAMLDTrans(tempComp,tempComp.getEvent($event_name.getText()), $f.form);
        tempTrans.setAssignments(al);
        tempTrans.makeForced(Double.parseDouble($earliest.getText()),
                             Double.parseDouble($latest.getText()));
        tempComp.addTrans(tempTrans);
      }
    | { ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); }
      'forced' 'transition' f=formula[tempComp] '[' earliest=FLOAT '..' latest=FLOAT ']' '->' assignment_list[tempComp,al] ';'
      { 
        YAMLDTrans tempTrans = new YAMLDTrans(tempComp,$f.form);
        tempTrans.setAssignments(al);
        tempTrans.makeForced(Double.parseDouble($earliest.getText()),
                             Double.parseDouble($latest.getText()));
        tempComp.addTrans(tempTrans);
      }
    | 'connection' conn_name=ID ':'  conn_type=ID EQUALS comp_name=ID ';'
      {
        tempConn.add(tempComp, $comp_name.getText(),
                     $conn_name.getText(),$conn_type.getText());
      }
    | 'constraint' f=formula[tempComp] ';'
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
      e1=expression[tempComp] EQUALS e2=expression[tempComp] 
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
          $expr = new VariableValue(var);
        } else {
          YAMLDID newID = new YAMLDID(name);
          $expr = newID;
        } 
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
        net.startSync(comp1,ev1);
      }
      (',' c2=ID '.' e2=ID                                                                                                                                                           
      {
        final YAMLDComponent comp2 = net.getComponent($c2.getText());
        final YAMLDEvent ev2 = comp2.getEvent($e2.getText());
        net.addSync(comp2,ev2);
      }
      )+ ';'
    ;

observable:
     'observable' cname=ID '.' ename=ID ';'
     {
       final YAMLDComponent comp = net.getComponent($cname.getText());
       final YAMLDEvent e = comp.getEvent($ename.getText());
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
