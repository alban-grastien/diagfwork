// //////////////////////////////////////////////
// (c) NICTA 2010
// Andreas Bauer <Andreas.Bauer@nicta.com.au>
// //////////////////////////////////////////////

grammar VisMap;

@header {
package lang;

import java.util.Map;
import java.util.HashMap;

import lang.VisMapShapeDef;
}

@members {
private Map<String, VisMapShapeDef> _shapeMap =
    new HashMap<String, VisMapShapeDef>();
}

@lexer::header {
package lang;
}

// /////////////////////////////////////
// Parser definition
// /////////////////////////////////////

mapping:
      (shape|rule_list)+
    ;

shape:
      { VisMapShapeDef sd = new VisMapShapeDef(); }
      'shape' '{' (shape_props[sd])* '}'
      { _shapeMap.put(sd.id(), sd); }
    ;
    
shape_props[VisMapShapeDef sd]:
      'id'        '=' id=ID ';'
      { sd.setId($id.getText()); }
    | 'image_path''=' uri=URI ';'
      { sd.setImgPath($uri.getText()); }
    | 'line'       '=' '[' beg=line_coords[sd]+'(' x=INT ',' y=INT ')' ']' ';'
      { 
        sd.pushLineCoords(Integer.parseInt($x.getText()),
                          Integer.parseInt($y.getText()));
        sd.createLine(sd.popLineCoords());
      }
    | 'line_width' '=' width=INT ';'
      { sd.setLineWidth(Integer.parseInt($width.getText())); }
    | 'width'      '=' width=INT ';'
      { sd.setWidth(Integer.parseInt($width.getText())); } 
    | 'height'    '=' height=INT ';'
      { sd.setHeight(Integer.parseInt($height.getText())); }
    | 'background''=' '(' r=INT ',' g=INT ',' b=INT ')' ';'
      { sd.setBackgroundColor(Integer.parseInt($r.getText()),
                              Integer.parseInt($g.getText()),
                              Integer.parseInt($b.getText())); }
    | 'foreground''=' '(' r=INT ',' g=INT ',' b=INT ')' ';'
      { sd.setForegroundColor(Integer.parseInt($r.getText()),
                              Integer.parseInt($g.getText()),
                              Integer.parseInt($b.getText())); }
    ;

line_coords[VisMapShapeDef sd]:
      ( '(' x=INT ',' y=INT ')' ',' )
      {
        sd.pushLineCoords(Integer.parseInt($x.getText()),
                          Integer.parseInt($y.getText()));
      }                 
    ;

rule_list:
      'rules' '{' (rule)* '}'
    /* TODO: add 'default' rule for components, if no rules match? */ 
    ;

// Example: Logical_Condition -> Component_name = Shape_ID ;
rule: 
      f=formula '->' comp_name=ID '=' shape_id=ID ';'
      {
        VisMapShapeDef shapeDef = _shapeMap.get($shape_id.getText());

        if (shapeDef != null) {
            YAMLDComponent comp =
                MMLDlightParser.net.getComponent($comp_name.getText());
    
            if (comp != null)
                VisMap.getSingletonObject().addMapping(f, shapeDef, comp); 
            else
                System.err.println("VisMapParser: Component " 
                                    + $comp_name.getText()
                                    + " does not exist "
                                    + "(line " 
                                    + $comp_name.getLine() 
                                    + ").");
        }
        else
            System.err.println("VisMapParser: Shape " 
                                + $shape_id.getText()
                                + " does not exist "        
                                + "(line " 
                                + $comp_name.getLine() 
                                + ").");
      }
    ;

formula returns [YAMLDFormula form]:
      co=conjunction f=f_prime[$co.form]
      { $form = $f.form; }
    ;

conjunction returns [YAMLDFormula form]: 
      at=atomic f=c_prime[$at.form]
      { $form = $f.form; }
    ;

atomic returns [YAMLDFormula form]:
      e1=expression '=' e2=expression 
      { $form = new YAMLDEqFormula($e1.expr, $e2.expr); }
    | '(' f=formula ')'
      { $form = $f.form; }
    | 'NOT' at=atomic
      { $form = new YAMLDNotFormula($at.form); }
    | 'FALSE'
      { $form = YAMLDFalse.FALSE; } 
    | 'TRUE'
      { $form = YAMLDTrue.TRUE; }
    ;

c_prime[YAMLDFormula f1] returns [YAMLDFormula form]:
      'AND' f2=conjunction
      { $form = (YAMLDAndFormula)(new YAMLDAndFormula($f1, $f2.form)); }
    | // epsilon
      { $form = $f1; }
    ;

f_prime[YAMLDFormula f1] returns [YAMLDFormula form]:
      'OR' f2=formula
      { $form = (YAMLDOrFormula)(new YAMLDOrFormula($f1, $f2.form)); }
    | // epsilon
      { $form = $f1; }
    ;
    
expression returns [YAMLDExpr expr]:
      s1=simple_expression e1=exp_prime[$s1.expr]
      { $expr = $e1.expr; }
    ;
    
exp_prime[YAMLDExpr e] returns [YAMLDExpr expr]:    
      '+' e2=expression
      { $expr = new YAMLDAddExpr($e, $e2.expr); }
    | // epsilon
      { $expr = $e; }
    ;
    
simple_expression returns [YAMLDExpr expr]:
      num=INT 
      { $expr = YAMLDValue.getValue(Integer.parseInt($num.getText())); }
    | 'true'
      { 
        System.out.println("true");
        $expr = YAMLDValue.getValue(1); 
      }
    | 'false'
      { $expr = YAMLDValue.getValue(0); }
    | comp_name=ID '::' var_name=ID
      {
        VariableValue newVarVal = 
            new VariableValue(MMLDlightParser.net.getComponent($comp_name.getText()).getVariable($var_name.getText()));
        $expr = newVarVal; 
      }
    | var_name=ID
      { 
        final String name = $var_name.getText();
        if (YAMLDValue.existsValue(name))
          $expr = YAMLDValue.getValue(name);
        else {
          YAMLDID newID = new YAMLDID(name);
          $expr = newID;
        } 
      }
    ;       
 
// /////////////////////////////////////
// Lexicographical definitions
// /////////////////////////////////////

REMCPP: '//'.*('\r'|'\n') { skip(); } ; 
REMC:   '/*'.*'*/' { skip(); } ;
fragment
DIGIT:  ('0'..'9') ;
INT:    DIGIT+ ;
WS:     ( ' ' | '\t' | '\r' | '\n' ) { $channel=HIDDEN; } ;
ID:     ('_')?('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
URI:    (ID|'-'|'/')+ '\.' ID ;
/* This is not really a URI as it only supports file:// for now.  */
// URI: 'file://'(ID ':'|'/')(ID|'-'|'/')+ '\.' ID ;
