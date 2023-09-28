// $ANTLR 3.2 Sep 23, 2009 12:02:23 VisMap.g 2011-08-01 17:24:42

package lang;

import java.util.Map;
import java.util.HashMap;

import lang.VisMapShapeDef;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class VisMapParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "URI", "INT", "REMCPP", "REMC", "DIGIT", "WS", "'shape'", "'{'", "'}'", "'id'", "'='", "';'", "'image_path'", "'line'", "'['", "'('", "','", "')'", "']'", "'line_width'", "'width'", "'height'", "'background'", "'foreground'", "'rules'", "'->'", "'NOT'", "'FALSE'", "'TRUE'", "'AND'", "'OR'", "'+'", "'true'", "'false'", "'::'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int REMCPP=7;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int INT=6;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int REMC=8;
    public static final int URI=5;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int WS=10;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__34=34;
    public static final int T__15=15;
    public static final int T__35=35;
    public static final int T__18=18;
    public static final int T__36=36;
    public static final int T__17=17;
    public static final int T__37=37;
    public static final int T__12=12;
    public static final int T__38=38;
    public static final int T__11=11;
    public static final int T__39=39;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int DIGIT=9;

    // delegates
    // delegators


        public VisMapParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public VisMapParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return VisMapParser.tokenNames; }
    public String getGrammarFileName() { return "VisMap.g"; }


    private Map<String, VisMapShapeDef> _shapeMap =
        new HashMap<String, VisMapShapeDef>();



    // $ANTLR start "mapping"
    // VisMap.g:30:1: mapping : ( shape | rule_list )+ ;
    public final void mapping() throws RecognitionException {
        try {
            // VisMap.g:30:8: ( ( shape | rule_list )+ )
            // VisMap.g:31:7: ( shape | rule_list )+
            {
            // VisMap.g:31:7: ( shape | rule_list )+
            int cnt1=0;
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }
                else if ( (LA1_0==29) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // VisMap.g:31:8: shape
            	    {
            	    pushFollow(FOLLOW_shape_in_mapping46);
            	    shape();

            	    state._fsp--;


            	    }
            	    break;
            	case 2 :
            	    // VisMap.g:31:14: rule_list
            	    {
            	    pushFollow(FOLLOW_rule_list_in_mapping48);
            	    rule_list();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
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
    // $ANTLR end "mapping"


    // $ANTLR start "shape"
    // VisMap.g:34:1: shape : 'shape' '{' ( shape_props[sd] )* '}' ;
    public final void shape() throws RecognitionException {
        try {
            // VisMap.g:34:6: ( 'shape' '{' ( shape_props[sd] )* '}' )
            // VisMap.g:35:7: 'shape' '{' ( shape_props[sd] )* '}'
            {
             VisMapShapeDef sd = new VisMapShapeDef(); 
            match(input,11,FOLLOW_11_in_shape76); 
            match(input,12,FOLLOW_12_in_shape78); 
            // VisMap.g:36:19: ( shape_props[sd] )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==14||(LA2_0>=17 && LA2_0<=18)||(LA2_0>=24 && LA2_0<=28)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // VisMap.g:36:20: shape_props[sd]
            	    {
            	    pushFollow(FOLLOW_shape_props_in_shape81);
            	    shape_props(sd);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match(input,13,FOLLOW_13_in_shape86); 
             _shapeMap.put(sd.id(), sd); 

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
    // $ANTLR end "shape"


    // $ANTLR start "shape_props"
    // VisMap.g:40:1: shape_props[VisMapShapeDef sd] : ( 'id' '=' id= ID ';' | 'image_path' '=' uri= URI ';' | 'line' '=' '[' (beg= line_coords[sd] )+ '(' x= INT ',' y= INT ')' ']' ';' | 'line_width' '=' width= INT ';' | 'width' '=' width= INT ';' | 'height' '=' height= INT ';' | 'background' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';' | 'foreground' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';' );
    public final void shape_props(VisMapShapeDef sd) throws RecognitionException {
        Token id=null;
        Token uri=null;
        Token x=null;
        Token y=null;
        Token width=null;
        Token height=null;
        Token r=null;
        Token g=null;
        Token b=null;

        try {
            // VisMap.g:40:31: ( 'id' '=' id= ID ';' | 'image_path' '=' uri= URI ';' | 'line' '=' '[' (beg= line_coords[sd] )+ '(' x= INT ',' y= INT ')' ']' ';' | 'line_width' '=' width= INT ';' | 'width' '=' width= INT ';' | 'height' '=' height= INT ';' | 'background' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';' | 'foreground' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';' )
            int alt4=8;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt4=1;
                }
                break;
            case 17:
                {
                alt4=2;
                }
                break;
            case 18:
                {
                alt4=3;
                }
                break;
            case 24:
                {
                alt4=4;
                }
                break;
            case 25:
                {
                alt4=5;
                }
                break;
            case 26:
                {
                alt4=6;
                }
                break;
            case 27:
                {
                alt4=7;
                }
                break;
            case 28:
                {
                alt4=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // VisMap.g:41:7: 'id' '=' id= ID ';'
                    {
                    match(input,14,FOLLOW_14_in_shape_props117); 
                    match(input,15,FOLLOW_15_in_shape_props126); 
                    id=(Token)match(input,ID,FOLLOW_ID_in_shape_props130); 
                    match(input,16,FOLLOW_16_in_shape_props132); 
                     sd.setId(id.getText()); 

                    }
                    break;
                case 2 :
                    // VisMap.g:43:7: 'image_path' '=' uri= URI ';'
                    {
                    match(input,17,FOLLOW_17_in_shape_props148); 
                    match(input,15,FOLLOW_15_in_shape_props149); 
                    uri=(Token)match(input,URI,FOLLOW_URI_in_shape_props153); 
                    match(input,16,FOLLOW_16_in_shape_props155); 
                     sd.setImgPath(uri.getText()); 

                    }
                    break;
                case 3 :
                    // VisMap.g:45:7: 'line' '=' '[' (beg= line_coords[sd] )+ '(' x= INT ',' y= INT ')' ']' ';'
                    {
                    match(input,18,FOLLOW_18_in_shape_props171); 
                    match(input,15,FOLLOW_15_in_shape_props179); 
                    match(input,19,FOLLOW_19_in_shape_props181); 
                    // VisMap.g:45:31: (beg= line_coords[sd] )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==20) ) {
                            int LA3_1 = input.LA(2);

                            if ( (LA3_1==INT) ) {
                                int LA3_2 = input.LA(3);

                                if ( (LA3_2==21) ) {
                                    int LA3_3 = input.LA(4);

                                    if ( (LA3_3==INT) ) {
                                        int LA3_4 = input.LA(5);

                                        if ( (LA3_4==22) ) {
                                            int LA3_5 = input.LA(6);

                                            if ( (LA3_5==21) ) {
                                                alt3=1;
                                            }


                                        }


                                    }


                                }


                            }


                        }


                        switch (alt3) {
                    	case 1 :
                    	    // VisMap.g:45:31: beg= line_coords[sd]
                    	    {
                    	    pushFollow(FOLLOW_line_coords_in_shape_props185);
                    	    line_coords(sd);

                    	    state._fsp--;


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

                    match(input,20,FOLLOW_20_in_shape_props188); 
                    x=(Token)match(input,INT,FOLLOW_INT_in_shape_props192); 
                    match(input,21,FOLLOW_21_in_shape_props194); 
                    y=(Token)match(input,INT,FOLLOW_INT_in_shape_props198); 
                    match(input,22,FOLLOW_22_in_shape_props200); 
                    match(input,23,FOLLOW_23_in_shape_props202); 
                    match(input,16,FOLLOW_16_in_shape_props204); 
                     
                            sd.pushLineCoords(Integer.parseInt(x.getText()),
                                              Integer.parseInt(y.getText()));
                            sd.createLine(sd.popLineCoords());
                          

                    }
                    break;
                case 4 :
                    // VisMap.g:51:7: 'line_width' '=' width= INT ';'
                    {
                    match(input,24,FOLLOW_24_in_shape_props220); 
                    match(input,15,FOLLOW_15_in_shape_props222); 
                    width=(Token)match(input,INT,FOLLOW_INT_in_shape_props226); 
                    match(input,16,FOLLOW_16_in_shape_props228); 
                     sd.setLineWidth(Integer.parseInt(width.getText())); 

                    }
                    break;
                case 5 :
                    // VisMap.g:53:7: 'width' '=' width= INT ';'
                    {
                    match(input,25,FOLLOW_25_in_shape_props244); 
                    match(input,15,FOLLOW_15_in_shape_props251); 
                    width=(Token)match(input,INT,FOLLOW_INT_in_shape_props255); 
                    match(input,16,FOLLOW_16_in_shape_props257); 
                     sd.setWidth(Integer.parseInt(width.getText())); 

                    }
                    break;
                case 6 :
                    // VisMap.g:55:7: 'height' '=' height= INT ';'
                    {
                    match(input,26,FOLLOW_26_in_shape_props274); 
                    match(input,15,FOLLOW_15_in_shape_props279); 
                    height=(Token)match(input,INT,FOLLOW_INT_in_shape_props283); 
                    match(input,16,FOLLOW_16_in_shape_props285); 
                     sd.setHeight(Integer.parseInt(height.getText())); 

                    }
                    break;
                case 7 :
                    // VisMap.g:57:7: 'background' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';'
                    {
                    match(input,27,FOLLOW_27_in_shape_props301); 
                    match(input,15,FOLLOW_15_in_shape_props302); 
                    match(input,20,FOLLOW_20_in_shape_props304); 
                    r=(Token)match(input,INT,FOLLOW_INT_in_shape_props308); 
                    match(input,21,FOLLOW_21_in_shape_props310); 
                    g=(Token)match(input,INT,FOLLOW_INT_in_shape_props314); 
                    match(input,21,FOLLOW_21_in_shape_props316); 
                    b=(Token)match(input,INT,FOLLOW_INT_in_shape_props320); 
                    match(input,22,FOLLOW_22_in_shape_props322); 
                    match(input,16,FOLLOW_16_in_shape_props324); 
                     sd.setBackgroundColor(Integer.parseInt(r.getText()),
                                                  Integer.parseInt(g.getText()),
                                                  Integer.parseInt(b.getText())); 

                    }
                    break;
                case 8 :
                    // VisMap.g:61:7: 'foreground' '=' '(' r= INT ',' g= INT ',' b= INT ')' ';'
                    {
                    match(input,28,FOLLOW_28_in_shape_props340); 
                    match(input,15,FOLLOW_15_in_shape_props341); 
                    match(input,20,FOLLOW_20_in_shape_props343); 
                    r=(Token)match(input,INT,FOLLOW_INT_in_shape_props347); 
                    match(input,21,FOLLOW_21_in_shape_props349); 
                    g=(Token)match(input,INT,FOLLOW_INT_in_shape_props353); 
                    match(input,21,FOLLOW_21_in_shape_props355); 
                    b=(Token)match(input,INT,FOLLOW_INT_in_shape_props359); 
                    match(input,22,FOLLOW_22_in_shape_props361); 
                    match(input,16,FOLLOW_16_in_shape_props363); 
                     sd.setForegroundColor(Integer.parseInt(r.getText()),
                                                  Integer.parseInt(g.getText()),
                                                  Integer.parseInt(b.getText())); 

                    }
                    break;

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
    // $ANTLR end "shape_props"


    // $ANTLR start "line_coords"
    // VisMap.g:67:1: line_coords[VisMapShapeDef sd] : ( '(' x= INT ',' y= INT ')' ',' ) ;
    public final void line_coords(VisMapShapeDef sd) throws RecognitionException {
        Token x=null;
        Token y=null;

        try {
            // VisMap.g:67:31: ( ( '(' x= INT ',' y= INT ')' ',' ) )
            // VisMap.g:68:7: ( '(' x= INT ',' y= INT ')' ',' )
            {
            // VisMap.g:68:7: ( '(' x= INT ',' y= INT ')' ',' )
            // VisMap.g:68:9: '(' x= INT ',' y= INT ')' ','
            {
            match(input,20,FOLLOW_20_in_line_coords392); 
            x=(Token)match(input,INT,FOLLOW_INT_in_line_coords396); 
            match(input,21,FOLLOW_21_in_line_coords398); 
            y=(Token)match(input,INT,FOLLOW_INT_in_line_coords402); 
            match(input,22,FOLLOW_22_in_line_coords404); 
            match(input,21,FOLLOW_21_in_line_coords406); 

            }


                    sd.pushLineCoords(Integer.parseInt(x.getText()),
                                      Integer.parseInt(y.getText()));
                  

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
    // $ANTLR end "line_coords"


    // $ANTLR start "rule_list"
    // VisMap.g:75:1: rule_list : 'rules' '{' ( rule )* '}' ;
    public final void rule_list() throws RecognitionException {
        try {
            // VisMap.g:75:10: ( 'rules' '{' ( rule )* '}' )
            // VisMap.g:76:7: 'rules' '{' ( rule )* '}'
            {
            match(input,29,FOLLOW_29_in_rule_list451); 
            match(input,12,FOLLOW_12_in_rule_list453); 
            // VisMap.g:76:19: ( rule )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID||LA5_0==INT||LA5_0==20||(LA5_0>=31 && LA5_0<=33)||(LA5_0>=37 && LA5_0<=38)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // VisMap.g:76:20: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_rule_list456);
            	    rule();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,13,FOLLOW_13_in_rule_list460); 

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
    // $ANTLR end "rule_list"


    // $ANTLR start "rule"
    // VisMap.g:81:1: rule : f= formula '->' comp_name= ID '=' shape_id= ID ';' ;
    public final void rule() throws RecognitionException {
        Token comp_name=null;
        Token shape_id=null;
        YAMLDFormula f = null;


        try {
            // VisMap.g:81:5: (f= formula '->' comp_name= ID '=' shape_id= ID ';' )
            // VisMap.g:82:7: f= formula '->' comp_name= ID '=' shape_id= ID ';'
            {
            pushFollow(FOLLOW_formula_in_rule489);
            f=formula();

            state._fsp--;

            match(input,30,FOLLOW_30_in_rule491); 
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_rule495); 
            match(input,15,FOLLOW_15_in_rule497); 
            shape_id=(Token)match(input,ID,FOLLOW_ID_in_rule501); 
            match(input,16,FOLLOW_16_in_rule503); 

                    VisMapShapeDef shapeDef = _shapeMap.get(shape_id.getText());

                    if (shapeDef != null) {
                        YAMLDComponent comp =
                            MMLDlightParser.net.getComponent(comp_name.getText());
                
                        if (comp != null)
                            VisMap.getSingletonObject().addMapping(f, shapeDef, comp); 
                        else
                            System.err.println("VisMapParser: Component " 
                                                + comp_name.getText()
                                                + " does not exist "
                                                + "(line " 
                                                + comp_name.getLine() 
                                                + ").");
                    }
                    else
                        System.err.println("VisMapParser: Shape " 
                                            + shape_id.getText()
                                            + " does not exist "        
                                            + "(line " 
                                            + comp_name.getLine() 
                                            + ").");
                  

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


    // $ANTLR start "formula"
    // VisMap.g:110:1: formula returns [YAMLDFormula form] : co= conjunction f= f_prime[$co.form] ;
    public final YAMLDFormula formula() throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula co = null;

        YAMLDFormula f = null;


        try {
            // VisMap.g:110:36: (co= conjunction f= f_prime[$co.form] )
            // VisMap.g:111:7: co= conjunction f= f_prime[$co.form]
            {
            pushFollow(FOLLOW_conjunction_in_formula535);
            co=conjunction();

            state._fsp--;

            pushFollow(FOLLOW_f_prime_in_formula539);
            f=f_prime(co);

            state._fsp--;

             form = f; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return form;
    }
    // $ANTLR end "formula"


    // $ANTLR start "conjunction"
    // VisMap.g:115:1: conjunction returns [YAMLDFormula form] : at= atomic f= c_prime[$at.form] ;
    public final YAMLDFormula conjunction() throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula at = null;

        YAMLDFormula f = null;


        try {
            // VisMap.g:115:40: (at= atomic f= c_prime[$at.form] )
            // VisMap.g:116:7: at= atomic f= c_prime[$at.form]
            {
            pushFollow(FOLLOW_atomic_in_conjunction573);
            at=atomic();

            state._fsp--;

            pushFollow(FOLLOW_c_prime_in_conjunction577);
            f=c_prime(at);

            state._fsp--;

             form = f; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return form;
    }
    // $ANTLR end "conjunction"


    // $ANTLR start "atomic"
    // VisMap.g:120:1: atomic returns [YAMLDFormula form] : (e1= expression '=' e2= expression | '(' f= formula ')' | 'NOT' at= atomic | 'FALSE' | 'TRUE' );
    public final YAMLDFormula atomic() throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDExpr e1 = null;

        YAMLDExpr e2 = null;

        YAMLDFormula f = null;

        YAMLDFormula at = null;


        try {
            // VisMap.g:120:35: (e1= expression '=' e2= expression | '(' f= formula ')' | 'NOT' at= atomic | 'FALSE' | 'TRUE' )
            int alt6=5;
            switch ( input.LA(1) ) {
            case ID:
            case INT:
            case 37:
            case 38:
                {
                alt6=1;
                }
                break;
            case 20:
                {
                alt6=2;
                }
                break;
            case 31:
                {
                alt6=3;
                }
                break;
            case 32:
                {
                alt6=4;
                }
                break;
            case 33:
                {
                alt6=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // VisMap.g:121:7: e1= expression '=' e2= expression
                    {
                    pushFollow(FOLLOW_expression_in_atomic610);
                    e1=expression();

                    state._fsp--;

                    match(input,15,FOLLOW_15_in_atomic612); 
                    pushFollow(FOLLOW_expression_in_atomic616);
                    e2=expression();

                    state._fsp--;

                     form = new YAMLDEqFormula(e1, e2); 

                    }
                    break;
                case 2 :
                    // VisMap.g:123:7: '(' f= formula ')'
                    {
                    match(input,20,FOLLOW_20_in_atomic633); 
                    pushFollow(FOLLOW_formula_in_atomic637);
                    f=formula();

                    state._fsp--;

                    match(input,22,FOLLOW_22_in_atomic639); 
                     form = f; 

                    }
                    break;
                case 3 :
                    // VisMap.g:125:7: 'NOT' at= atomic
                    {
                    match(input,31,FOLLOW_31_in_atomic655); 
                    pushFollow(FOLLOW_atomic_in_atomic659);
                    at=atomic();

                    state._fsp--;

                     form = new YAMLDNotFormula(at); 

                    }
                    break;
                case 4 :
                    // VisMap.g:127:7: 'FALSE'
                    {
                    match(input,32,FOLLOW_32_in_atomic675); 
                     form = YAMLDFalse.FALSE; 

                    }
                    break;
                case 5 :
                    // VisMap.g:129:7: 'TRUE'
                    {
                    match(input,33,FOLLOW_33_in_atomic692); 
                     form = YAMLDTrue.TRUE; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return form;
    }
    // $ANTLR end "atomic"


    // $ANTLR start "c_prime"
    // VisMap.g:133:1: c_prime[YAMLDFormula f1] returns [YAMLDFormula form] : ( 'AND' f2= conjunction | );
    public final YAMLDFormula c_prime(YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // VisMap.g:133:53: ( 'AND' f2= conjunction | )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==34) ) {
                alt7=1;
            }
            else if ( (LA7_0==22||LA7_0==30||LA7_0==35) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // VisMap.g:134:7: 'AND' f2= conjunction
                    {
                    match(input,34,FOLLOW_34_in_c_prime723); 
                    pushFollow(FOLLOW_conjunction_in_c_prime727);
                    f2=conjunction();

                    state._fsp--;

                     form = (YAMLDAndFormula)(new YAMLDAndFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // VisMap.g:137:7: 
                    {
                     form = f1; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return form;
    }
    // $ANTLR end "c_prime"


    // $ANTLR start "f_prime"
    // VisMap.g:140:1: f_prime[YAMLDFormula f1] returns [YAMLDFormula form] : ( 'OR' f2= formula | );
    public final YAMLDFormula f_prime(YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // VisMap.g:140:53: ( 'OR' f2= formula | )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==35) ) {
                alt8=1;
            }
            else if ( (LA8_0==22||LA8_0==30) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // VisMap.g:141:7: 'OR' f2= formula
                    {
                    match(input,35,FOLLOW_35_in_f_prime773); 
                    pushFollow(FOLLOW_formula_in_f_prime777);
                    f2=formula();

                    state._fsp--;

                     form = (YAMLDOrFormula)(new YAMLDOrFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // VisMap.g:144:7: 
                    {
                     form = f1; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return form;
    }
    // $ANTLR end "f_prime"


    // $ANTLR start "expression"
    // VisMap.g:147:1: expression returns [YAMLDExpr expr] : s1= simple_expression e1= exp_prime[$s1.expr] ;
    public final YAMLDExpr expression() throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr s1 = null;

        YAMLDExpr e1 = null;


        try {
            // VisMap.g:147:36: (s1= simple_expression e1= exp_prime[$s1.expr] )
            // VisMap.g:148:7: s1= simple_expression e1= exp_prime[$s1.expr]
            {
            pushFollow(FOLLOW_simple_expression_in_expression828);
            s1=simple_expression();

            state._fsp--;

            pushFollow(FOLLOW_exp_prime_in_expression832);
            e1=exp_prime(s1);

            state._fsp--;

             expr = e1; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return expr;
    }
    // $ANTLR end "expression"


    // $ANTLR start "exp_prime"
    // VisMap.g:152:1: exp_prime[YAMLDExpr e] returns [YAMLDExpr expr] : ( '+' e2= expression | );
    public final YAMLDExpr exp_prime(YAMLDExpr e) throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr e2 = null;


        try {
            // VisMap.g:152:48: ( '+' e2= expression | )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==36) ) {
                alt9=1;
            }
            else if ( (LA9_0==15||LA9_0==22||LA9_0==30||(LA9_0>=34 && LA9_0<=35)) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // VisMap.g:153:7: '+' e2= expression
                    {
                    match(input,36,FOLLOW_36_in_exp_prime872); 
                    pushFollow(FOLLOW_expression_in_exp_prime876);
                    e2=expression();

                    state._fsp--;

                     expr = new YAMLDAddExpr(e, e2); 

                    }
                    break;
                case 2 :
                    // VisMap.g:156:7: 
                    {
                     expr = e; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return expr;
    }
    // $ANTLR end "exp_prime"


    // $ANTLR start "simple_expression"
    // VisMap.g:159:1: simple_expression returns [YAMLDExpr expr] : (num= INT | 'true' | 'false' | comp_name= ID '::' var_name= ID | var_name= ID );
    public final YAMLDExpr simple_expression() throws RecognitionException {
        YAMLDExpr expr = null;

        Token num=null;
        Token comp_name=null;
        Token var_name=null;

        try {
            // VisMap.g:159:43: (num= INT | 'true' | 'false' | comp_name= ID '::' var_name= ID | var_name= ID )
            int alt10=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt10=1;
                }
                break;
            case 37:
                {
                alt10=2;
                }
                break;
            case 38:
                {
                alt10=3;
                }
                break;
            case ID:
                {
                int LA10_4 = input.LA(2);

                if ( (LA10_4==39) ) {
                    alt10=4;
                }
                else if ( (LA10_4==15||LA10_4==22||LA10_4==30||(LA10_4>=34 && LA10_4<=36)) ) {
                    alt10=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // VisMap.g:160:7: num= INT
                    {
                    num=(Token)match(input,INT,FOLLOW_INT_in_simple_expression927); 
                     expr = YAMLDValue.getValue(Integer.parseInt(num.getText())); 

                    }
                    break;
                case 2 :
                    // VisMap.g:162:7: 'true'
                    {
                    match(input,37,FOLLOW_37_in_simple_expression944); 
                     
                            System.out.println("true");
                            expr = YAMLDValue.getValue(1); 
                          

                    }
                    break;
                case 3 :
                    // VisMap.g:167:7: 'false'
                    {
                    match(input,38,FOLLOW_38_in_simple_expression960); 
                     expr = YAMLDValue.getValue(0); 

                    }
                    break;
                case 4 :
                    // VisMap.g:169:7: comp_name= ID '::' var_name= ID
                    {
                    comp_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression978); 
                    match(input,39,FOLLOW_39_in_simple_expression980); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression984); 

                            VariableValue newVarVal = 
                                new VariableValue(MMLDlightParser.net.getComponent(comp_name.getText()).getVariable(var_name.getText()));
                            expr = newVarVal; 
                          

                    }
                    break;
                case 5 :
                    // VisMap.g:175:7: var_name= ID
                    {
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1002); 
                     
                            final String name = var_name.getText();
                            if (YAMLDValue.existsValue(name))
                              expr = YAMLDValue.getValue(name);
                            else {
                              YAMLDID newID = new YAMLDID(name);
                              expr = newID;
                            } 
                          

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return expr;
    }
    // $ANTLR end "simple_expression"

    // Delegated rules


 

    public static final BitSet FOLLOW_shape_in_mapping46 = new BitSet(new long[]{0x0000000020000802L});
    public static final BitSet FOLLOW_rule_list_in_mapping48 = new BitSet(new long[]{0x0000000020000802L});
    public static final BitSet FOLLOW_11_in_shape76 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_shape78 = new BitSet(new long[]{0x000000001F066000L});
    public static final BitSet FOLLOW_shape_props_in_shape81 = new BitSet(new long[]{0x000000001F066000L});
    public static final BitSet FOLLOW_13_in_shape86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_shape_props117 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props126 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_shape_props130 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_shape_props148 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props149 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_URI_in_shape_props153 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_shape_props171 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props179 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_shape_props181 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_line_coords_in_shape_props185 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_shape_props188 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props192 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_shape_props194 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props198 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_shape_props200 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_shape_props202 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_shape_props220 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props222 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props226 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_shape_props244 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props251 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props255 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_shape_props274 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props279 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props283 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_shape_props301 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props302 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_shape_props304 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props308 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_shape_props310 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props314 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_shape_props316 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props320 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_shape_props322 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_shape_props340 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_shape_props341 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_shape_props343 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props347 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_shape_props349 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props353 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_shape_props355 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_shape_props359 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_shape_props361 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_shape_props363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_line_coords392 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_line_coords396 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_line_coords398 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_line_coords402 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_line_coords404 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_line_coords406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule_list451 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_rule_list453 = new BitSet(new long[]{0x0000006380102050L});
    public static final BitSet FOLLOW_rule_in_rule_list456 = new BitSet(new long[]{0x0000006380102050L});
    public static final BitSet FOLLOW_13_in_rule_list460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula_in_rule489 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_rule491 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_rule495 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rule497 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_rule501 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rule503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conjunction_in_formula535 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_f_prime_in_formula539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomic_in_conjunction573 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_c_prime_in_conjunction577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_atomic610 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_atomic612 = new BitSet(new long[]{0x0000006000000050L});
    public static final BitSet FOLLOW_expression_in_atomic616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_atomic633 = new BitSet(new long[]{0x0000006380100050L});
    public static final BitSet FOLLOW_formula_in_atomic637 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_atomic639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_atomic655 = new BitSet(new long[]{0x0000006380100050L});
    public static final BitSet FOLLOW_atomic_in_atomic659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_atomic675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_atomic692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_c_prime723 = new BitSet(new long[]{0x0000006380100050L});
    public static final BitSet FOLLOW_conjunction_in_c_prime727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_f_prime773 = new BitSet(new long[]{0x0000006380100050L});
    public static final BitSet FOLLOW_formula_in_f_prime777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_expression_in_expression828 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_exp_prime_in_expression832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_exp_prime872 = new BitSet(new long[]{0x0000006000000050L});
    public static final BitSet FOLLOW_expression_in_exp_prime876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_simple_expression927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_simple_expression944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_simple_expression960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression978 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_simple_expression980 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_simple_expression984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression1002 = new BitSet(new long[]{0x0000000000000002L});

}