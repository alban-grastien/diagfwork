// $ANTLR 3.2 Sep 23, 2009 12:02:23 YAMLDlight.g 2011-08-23 14:27:55

package lang;

import util.Coords;
import java.util.HashMap;
import java.util.Map;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class YAMLDlightParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "EQUALS", "INT", "FLOAT", "ASSGN", "REMCPP", "REMC", "WS", "DIGIT", "'component'", "'{'", "'}'", "'event'", "';'", "'coords'", "','", "'var'", "':'", "'['", "'..'", "']'", "'dvar'", "'transition'", "'->'", "'forced'", "'connection'", "'constraint'", "'FALSE'", "'TRUE'", "'exists'", "'('", "')'", "'NOT'", "'AND'", "'OR'", "'+'", "'true'", "'false'", "'.'", "'synchronize'", "'observable'"
    };
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
    public static final int EQUALS=5;
    public static final int FLOAT=7;
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


        public YAMLDlightParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public YAMLDlightParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return YAMLDlightParser.tokenNames; }
    public String getGrammarFileName() { return "YAMLDlight.g"; }


    public static Network net = null;
    public static State st = null;



    // $ANTLR start "net"
    // YAMLDlight.g:29:1: net : ( comp[tempConn] )+ ( synchro )+ ( observable )* ( state[init] )+ ;
    public final void net() throws RecognitionException {
        try {
            // YAMLDlight.g:29:4: ( ( comp[tempConn] )+ ( synchro )+ ( observable )* ( state[init] )+ )
            // YAMLDlight.g:30:7: ( comp[tempConn] )+ ( synchro )+ ( observable )* ( state[init] )+
            {

                    net = new Network();
                    final YAMLDTempConnections tempConn = new YAMLDTempConnections();
                  
            // YAMLDlight.g:34:7: ( comp[tempConn] )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==13) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // YAMLDlight.g:34:8: comp[tempConn]
            	    {
            	    pushFollow(FOLLOW_comp_in_net54);
            	    comp(tempConn);

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

             
                    tempConn.copyConnections(net); 
                    final Map<YAMLDVar,YAMLDValue> init = new HashMap<YAMLDVar,YAMLDValue>();
                  
            // YAMLDlight.g:39:7: ( synchro )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==43) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // YAMLDlight.g:39:8: synchro
            	    {
            	    pushFollow(FOLLOW_synchro_in_net74);
            	    synchro();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            // YAMLDlight.g:40:7: ( observable )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==44) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // YAMLDlight.g:40:8: observable
            	    {
            	    pushFollow(FOLLOW_observable_in_net85);
            	    observable();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // YAMLDlight.g:41:7: ( state[init] )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==ID) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // YAMLDlight.g:41:8: state[init]
            	    {
            	    pushFollow(FOLLOW_state_in_net96);
            	    state(init);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


                    st = new ExplicitState(net,init);
                  

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
    // $ANTLR end "net"


    // $ANTLR start "comp"
    // YAMLDlight.g:47:1: comp[YAMLDTempConnections tempConn] : 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,tempConn] )* '}' ;
    public final void comp(YAMLDTempConnections tempConn) throws RecognitionException {
        Token comp_name=null;

        try {
            // YAMLDlight.g:47:36: ( 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,tempConn] )* '}' )
            // YAMLDlight.g:48:7: 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,tempConn] )* '}'
            {
             YAMLDComponent tempComp = new YAMLDComponent(); 
            match(input,13,FOLLOW_13_in_comp139); 
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_comp143); 
            match(input,EQUALS,FOLLOW_EQUALS_in_comp145); 

                    tempComp.setName(comp_name.getText());
                  
            match(input,14,FOLLOW_14_in_comp162); 
            // YAMLDlight.g:53:11: ( comp_def[tempComp,tempConn] )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==16||LA5_0==18||LA5_0==20||(LA5_0>=25 && LA5_0<=26)||(LA5_0>=28 && LA5_0<=30)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // YAMLDlight.g:53:12: comp_def[tempComp,tempConn]
            	    {
            	    pushFollow(FOLLOW_comp_def_in_comp165);
            	    comp_def(tempComp, tempConn);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,15,FOLLOW_15_in_comp170); 
             
                    net.addComponent(tempComp);
                  

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
    // $ANTLR end "comp"


    // $ANTLR start "comp_def"
    // YAMLDlight.g:59:1: comp_def[YAMLDComponent tempComp,YAMLDTempConnections tempConn] : ( 'event' event_name= ID ';' | 'coords' x= INT ',' y= INT ';' | 'var' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'var' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'dvar' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'dvar' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'transition' event_name= ID ':' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'transition' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' event_name= ID ':' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';' | 'constraint' f= formula[tempComp] ';' );
    public final void comp_def(YAMLDComponent tempComp, YAMLDTempConnections tempConn) throws RecognitionException {
        Token event_name=null;
        Token x=null;
        Token y=null;
        Token var_name=null;
        Token var_dom_beg=null;
        Token var_dom_end=null;
        Token earliest=null;
        Token latest=null;
        Token conn_name=null;
        Token conn_type=null;
        Token comp_name=null;
        YAMLDFormula f = null;


        try {
            // YAMLDlight.g:59:64: ( 'event' event_name= ID ';' | 'coords' x= INT ',' y= INT ';' | 'var' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'var' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'dvar' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'dvar' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'transition' event_name= ID ':' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'transition' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' event_name= ID ':' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';' | 'constraint' f= formula[tempComp] ';' )
            int alt6=12;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // YAMLDlight.g:60:7: 'event' event_name= ID ';'
                    {
                    match(input,16,FOLLOW_16_in_comp_def198); 
                    event_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def202); 
                    match(input,17,FOLLOW_17_in_comp_def204); 
                     tempComp.addEvent(new YAMLDEvent(tempComp,event_name.getText())); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:62:7: 'coords' x= INT ',' y= INT ';'
                    {
                    match(input,18,FOLLOW_18_in_comp_def221); 
                    x=(Token)match(input,INT,FOLLOW_INT_in_comp_def225); 
                    match(input,19,FOLLOW_19_in_comp_def227); 
                    y=(Token)match(input,INT,FOLLOW_INT_in_comp_def231); 
                    match(input,17,FOLLOW_17_in_comp_def233); 

                            tempComp.visOptions().
                                coords().
                                    add(new Coords(Integer.parseInt(x.getText()),
                                                   Integer.parseInt(y.getText())));
                          

                    }
                    break;
                case 3 :
                    // YAMLDlight.g:69:7: 'var' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';'
                    {
                    match(input,20,FOLLOW_20_in_comp_def250); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def254); 
                    match(input,21,FOLLOW_21_in_comp_def256); 
                    match(input,22,FOLLOW_22_in_comp_def258); 
                    var_dom_beg=(Token)match(input,INT,FOLLOW_INT_in_comp_def262); 
                    match(input,23,FOLLOW_23_in_comp_def264); 
                    var_dom_end=(Token)match(input,INT,FOLLOW_INT_in_comp_def268); 
                    match(input,24,FOLLOW_24_in_comp_def270); 
                    match(input,17,FOLLOW_17_in_comp_def272); 
                     
                            YAMLDVar tempVar = new YAMLDVar(var_name.getText(),tempComp);
                            tempVar.setRange(Integer.parseInt(var_dom_beg.getText()),
                                             Integer.parseInt(var_dom_end.getText()));
                            tempComp.addVar(tempVar); 
                          

                    }
                    break;
                case 4 :
                    // YAMLDlight.g:76:7: 'var' var_name= ID ':' '{' var_dom[domElems] '}' ';'
                    {
                     ArrayList<String> domElems = new ArrayList<String>(); 
                    match(input,20,FOLLOW_20_in_comp_def297); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def301); 
                    match(input,21,FOLLOW_21_in_comp_def303); 
                    match(input,14,FOLLOW_14_in_comp_def305); 
                    pushFollow(FOLLOW_var_dom_in_comp_def307);
                    var_dom(domElems);

                    state._fsp--;

                    match(input,15,FOLLOW_15_in_comp_def310); 
                    match(input,17,FOLLOW_17_in_comp_def312); 

                            YAMLDVar tempVar = new YAMLDVar(var_name.getText(),tempComp);
                            for (String varName : domElems)
                                tempVar.domainPush(varName);
                            tempComp.addVar(tempVar);
                          

                    }
                    break;
                case 5 :
                    // YAMLDlight.g:84:7: 'dvar' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';'
                    {
                    match(input,25,FOLLOW_25_in_comp_def328); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def332); 
                    match(input,21,FOLLOW_21_in_comp_def334); 
                    match(input,22,FOLLOW_22_in_comp_def336); 
                    var_dom_beg=(Token)match(input,INT,FOLLOW_INT_in_comp_def340); 
                    match(input,23,FOLLOW_23_in_comp_def342); 
                    var_dom_end=(Token)match(input,INT,FOLLOW_INT_in_comp_def346); 
                    match(input,24,FOLLOW_24_in_comp_def348); 
                    match(input,17,FOLLOW_17_in_comp_def350); 
                     
                            YAMLDDVar tempVar = new YAMLDDVar(var_name.getText(),tempComp);
                            tempVar.setRange(Integer.parseInt(var_dom_beg.getText()),
                                             Integer.parseInt(var_dom_end.getText()));
                            tempComp.addDVar(tempVar); 
                          

                    }
                    break;
                case 6 :
                    // YAMLDlight.g:91:7: 'dvar' var_name= ID ':' '{' var_dom[domElems] '}' ';'
                    {
                     ArrayList<String> domElems = new ArrayList<String>(); 
                    match(input,25,FOLLOW_25_in_comp_def376); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def380); 
                    match(input,21,FOLLOW_21_in_comp_def382); 
                    match(input,14,FOLLOW_14_in_comp_def384); 
                    pushFollow(FOLLOW_var_dom_in_comp_def386);
                    var_dom(domElems);

                    state._fsp--;

                    match(input,15,FOLLOW_15_in_comp_def389); 
                    match(input,17,FOLLOW_17_in_comp_def391); 

                            YAMLDDVar tempVar = new YAMLDDVar(var_name.getText(),tempComp);
                            for (String varName : domElems)
                                tempVar.domainPush(varName);
                            tempComp.addDVar(tempVar);
                          

                    }
                    break;
                case 7 :
                    // YAMLDlight.g:99:7: 'transition' event_name= ID ':' f= formula[tempComp] '->' assignment_list[tempComp,al] ';'
                    {
                     ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); 
                    match(input,26,FOLLOW_26_in_comp_def415); 
                    event_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def419); 
                    match(input,21,FOLLOW_21_in_comp_def421); 
                    pushFollow(FOLLOW_formula_in_comp_def425);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,27,FOLLOW_27_in_comp_def428); 
                    pushFollow(FOLLOW_assignment_list_in_comp_def430);
                    assignment_list(tempComp, al);

                    state._fsp--;

                    match(input,17,FOLLOW_17_in_comp_def433); 
                     
                            YAMLDTrans tempTrans = 
                                new YAMLDTrans(tempComp,tempComp.getEvent(event_name.getText()), f);
                            tempTrans.setAssignments(al);
                            tempComp.addTrans(tempTrans);
                          

                    }
                    break;
                case 8 :
                    // YAMLDlight.g:107:7: 'transition' f= formula[tempComp] '->' assignment_list[tempComp,al] ';'
                    {
                     ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); 
                    match(input,26,FOLLOW_26_in_comp_def457); 
                    pushFollow(FOLLOW_formula_in_comp_def461);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,27,FOLLOW_27_in_comp_def464); 
                    pushFollow(FOLLOW_assignment_list_in_comp_def466);
                    assignment_list(tempComp, al);

                    state._fsp--;

                    match(input,17,FOLLOW_17_in_comp_def469); 
                     
                            YAMLDTrans tempTrans = new YAMLDTrans(tempComp,f);
                            tempTrans.setAssignments(al);
                            tempComp.addTrans(tempTrans);
                          

                    }
                    break;
                case 9 :
                    // YAMLDlight.g:114:7: 'forced' 'transition' event_name= ID ':' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';'
                    {
                     ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); 
                    match(input,28,FOLLOW_28_in_comp_def493); 
                    match(input,26,FOLLOW_26_in_comp_def495); 
                    event_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def499); 
                    match(input,21,FOLLOW_21_in_comp_def501); 
                    pushFollow(FOLLOW_formula_in_comp_def505);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,22,FOLLOW_22_in_comp_def508); 
                    earliest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_comp_def512); 
                    match(input,23,FOLLOW_23_in_comp_def514); 
                    latest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_comp_def518); 
                    match(input,24,FOLLOW_24_in_comp_def520); 
                    match(input,27,FOLLOW_27_in_comp_def522); 
                    pushFollow(FOLLOW_assignment_list_in_comp_def524);
                    assignment_list(tempComp, al);

                    state._fsp--;

                    match(input,17,FOLLOW_17_in_comp_def527); 
                     
                            YAMLDTrans tempTrans = 
                                new YAMLDTrans(tempComp,tempComp.getEvent(event_name.getText()), f);
                            tempTrans.setAssignments(al);
                            tempTrans.makeForced(Double.parseDouble(earliest.getText()),
                                                 Double.parseDouble(latest.getText()));
                            tempComp.addTrans(tempTrans);
                          

                    }
                    break;
                case 10 :
                    // YAMLDlight.g:124:7: 'forced' 'transition' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';'
                    {
                     ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>(); 
                    match(input,28,FOLLOW_28_in_comp_def551); 
                    match(input,26,FOLLOW_26_in_comp_def553); 
                    pushFollow(FOLLOW_formula_in_comp_def557);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,22,FOLLOW_22_in_comp_def560); 
                    earliest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_comp_def564); 
                    match(input,23,FOLLOW_23_in_comp_def566); 
                    latest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_comp_def570); 
                    match(input,24,FOLLOW_24_in_comp_def572); 
                    match(input,27,FOLLOW_27_in_comp_def574); 
                    pushFollow(FOLLOW_assignment_list_in_comp_def576);
                    assignment_list(tempComp, al);

                    state._fsp--;

                    match(input,17,FOLLOW_17_in_comp_def579); 
                     
                            YAMLDTrans tempTrans = new YAMLDTrans(tempComp,f);
                            tempTrans.setAssignments(al);
                            tempTrans.makeForced(Double.parseDouble(earliest.getText()),
                                                 Double.parseDouble(latest.getText()));
                            tempComp.addTrans(tempTrans);
                          

                    }
                    break;
                case 11 :
                    // YAMLDlight.g:133:7: 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';'
                    {
                    match(input,29,FOLLOW_29_in_comp_def595); 
                    conn_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def599); 
                    match(input,21,FOLLOW_21_in_comp_def601); 
                    conn_type=(Token)match(input,ID,FOLLOW_ID_in_comp_def606); 
                    match(input,EQUALS,FOLLOW_EQUALS_in_comp_def608); 
                    comp_name=(Token)match(input,ID,FOLLOW_ID_in_comp_def612); 
                    match(input,17,FOLLOW_17_in_comp_def614); 

                            tempConn.add(tempComp, comp_name.getText(),
                                         conn_name.getText(),conn_type.getText());
                          

                    }
                    break;
                case 12 :
                    // YAMLDlight.g:138:7: 'constraint' f= formula[tempComp] ';'
                    {
                    match(input,30,FOLLOW_30_in_comp_def630); 
                    pushFollow(FOLLOW_formula_in_comp_def634);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,17,FOLLOW_17_in_comp_def637); 
                     
                            final YAMLDConstraint con = new YAMLDConstraint(f, tempComp);
                            final YAMLDDVar var = con.getVariable();
                            var.addConstraint(con);
                          

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
    // $ANTLR end "comp_def"


    // $ANTLR start "var_dom"
    // YAMLDlight.g:146:1: var_dom[ArrayList<String> domElems] : (dom_elem= ID | dom_elem= ID ',' var_dom[domElems] );
    public final void var_dom(ArrayList<String> domElems) throws RecognitionException {
        Token dom_elem=null;

        try {
            // YAMLDlight.g:146:36: (dom_elem= ID | dom_elem= ID ',' var_dom[domElems] )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ID) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==19) ) {
                    alt7=2;
                }
                else if ( (LA7_1==15) ) {
                    alt7=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // YAMLDlight.g:147:7: dom_elem= ID
                    {
                    dom_elem=(Token)match(input,ID,FOLLOW_ID_in_var_dom665); 
                     domElems.add(dom_elem.getText()); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:149:7: dom_elem= ID ',' var_dom[domElems]
                    {
                    dom_elem=(Token)match(input,ID,FOLLOW_ID_in_var_dom684); 
                    match(input,19,FOLLOW_19_in_var_dom686); 
                    pushFollow(FOLLOW_var_dom_in_var_dom688);
                    var_dom(domElems);

                    state._fsp--;

                     domElems.add(dom_elem.getText()); 

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
    // $ANTLR end "var_dom"


    // $ANTLR start "formula"
    // YAMLDlight.g:153:1: formula[YAMLDComponent tempComp] returns [YAMLDFormula form] : co= conjunction[tempComp] f= f_prime[tempComp,$co.form] ;
    public final YAMLDFormula formula(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula co = null;

        YAMLDFormula f = null;


        try {
            // YAMLDlight.g:153:61: (co= conjunction[tempComp] f= f_prime[tempComp,$co.form] )
            // YAMLDlight.g:154:7: co= conjunction[tempComp] f= f_prime[tempComp,$co.form]
            {
            pushFollow(FOLLOW_conjunction_in_formula730);
            co=conjunction(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_f_prime_in_formula735);
            f=f_prime(tempComp, co);

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
    // YAMLDlight.g:158:1: conjunction[YAMLDComponent tempComp] returns [YAMLDFormula form] : at= atomic[tempComp] f= c_prime[tempComp,$at.form] ;
    public final YAMLDFormula conjunction(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula at = null;

        YAMLDFormula f = null;


        try {
            // YAMLDlight.g:158:65: (at= atomic[tempComp] f= c_prime[tempComp,$at.form] )
            // YAMLDlight.g:159:7: at= atomic[tempComp] f= c_prime[tempComp,$at.form]
            {
            pushFollow(FOLLOW_atomic_in_conjunction770);
            at=atomic(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_c_prime_in_conjunction775);
            f=c_prime(tempComp, at);

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
    // YAMLDlight.g:163:1: atomic[YAMLDComponent tempComp] returns [YAMLDFormula form] : (e1= expression[tempComp] EQUALS e2= expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] );
    public final YAMLDFormula atomic(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula form = null;

        Token connt=null;
        Token first=null;
        Token last=null;
        Token mid=null;
        YAMLDExpr e1 = null;

        YAMLDExpr e2 = null;

        YAMLDFormula fo = null;

        YAMLDFormula f = null;

        YAMLDFormula at = null;


        try {
            // YAMLDlight.g:163:60: (e1= expression[tempComp] EQUALS e2= expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] )
            int alt8=7;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // YAMLDlight.g:164:7: e1= expression[tempComp] EQUALS e2= expression[tempComp]
                    {
                    pushFollow(FOLLOW_expression_in_atomic809);
                    e1=expression(tempComp);

                    state._fsp--;

                    match(input,EQUALS,FOLLOW_EQUALS_in_atomic812); 
                    pushFollow(FOLLOW_expression_in_atomic816);
                    e2=expression(tempComp);

                    state._fsp--;

                    form = new YAMLDEqFormula(e1, e2);

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:167:7: 'FALSE'
                    {
                    match(input,31,FOLLOW_31_in_atomic840); 
                     form = YAMLDFalse.FALSE; 

                    }
                    break;
                case 3 :
                    // YAMLDlight.g:169:7: 'TRUE'
                    {
                    match(input,32,FOLLOW_32_in_atomic857); 
                     form = YAMLDTrue.TRUE; 

                    }
                    break;
                case 4 :
                    // YAMLDlight.g:171:7: 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')'
                    {
                    match(input,33,FOLLOW_33_in_atomic873); 
                    connt=(Token)match(input,ID,FOLLOW_ID_in_atomic877); 
                    match(input,34,FOLLOW_34_in_atomic879); 
                    first=(Token)match(input,ID,FOLLOW_ID_in_atomic883); 
                    match(input,23,FOLLOW_23_in_atomic885); 
                    last=(Token)match(input,ID,FOLLOW_ID_in_atomic889); 
                    match(input,35,FOLLOW_35_in_atomic891); 
                    match(input,34,FOLLOW_34_in_atomic893); 
                    pushFollow(FOLLOW_formula_in_atomic897);
                    fo=formula(null);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic900); 

                            String cf = first.getText();
                            if (cf.equals("this")) {
                              cf = tempComp.name();
                            }
                            String cl = last.getText();
                            if (cl.equals("this")) {
                              cl = tempComp.name();
                            }
                            form = new YAMLDStringExistsPath(cf, cl, YAMLDConnType.getType(connt.getText()),fo);
                          

                    }
                    break;
                case 5 :
                    // YAMLDlight.g:183:7: 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')'
                    {
                    match(input,33,FOLLOW_33_in_atomic916); 
                    connt=(Token)match(input,ID,FOLLOW_ID_in_atomic920); 
                    match(input,34,FOLLOW_34_in_atomic922); 
                    first=(Token)match(input,ID,FOLLOW_ID_in_atomic926); 
                    match(input,23,FOLLOW_23_in_atomic928); 
                    mid=(Token)match(input,ID,FOLLOW_ID_in_atomic932); 
                    match(input,23,FOLLOW_23_in_atomic934); 
                    last=(Token)match(input,ID,FOLLOW_ID_in_atomic938); 
                    match(input,35,FOLLOW_35_in_atomic940); 
                    match(input,34,FOLLOW_34_in_atomic942); 
                    pushFollow(FOLLOW_formula_in_atomic946);
                    fo=formula(null);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic949); 

                            String cf = first.getText();
                            if (cf.equals("this")) {
                              cf = tempComp.name();
                            }
                            String cl = last.getText();
                            if (cl.equals("this")) {
                              cl = tempComp.name();
                            }
                            String cm = mid.getText();
                            if (cm.equals("this")) {
                              cm = tempComp.name();
                            }
                            form = new YAMLDStringExistsPath(cf, cl, cm, YAMLDConnType.getType(connt.getText()),fo);
                          

                    }
                    break;
                case 6 :
                    // YAMLDlight.g:199:7: '(' f= formula[tempComp] ')'
                    {
                    match(input,34,FOLLOW_34_in_atomic965); 
                    pushFollow(FOLLOW_formula_in_atomic969);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic972); 
                     form = f; 

                    }
                    break;
                case 7 :
                    // YAMLDlight.g:201:7: 'NOT' at= atomic[tempComp]
                    {
                    match(input,36,FOLLOW_36_in_atomic988); 
                    pushFollow(FOLLOW_atomic_in_atomic992);
                    at=atomic(tempComp);

                    state._fsp--;

                     form = new YAMLDNotFormula(at); 

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
    // YAMLDlight.g:205:1: c_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form] : ( 'AND' f2= conjunction[tempComp] | );
    public final YAMLDFormula c_prime(YAMLDComponent tempComp, YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // YAMLDlight.g:205:77: ( 'AND' f2= conjunction[tempComp] | )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==37) ) {
                alt9=1;
            }
            else if ( (LA9_0==17||LA9_0==22||LA9_0==27||LA9_0==35||LA9_0==38) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // YAMLDlight.g:206:7: 'AND' f2= conjunction[tempComp]
                    {
                    match(input,37,FOLLOW_37_in_c_prime1024); 
                    pushFollow(FOLLOW_conjunction_in_c_prime1028);
                    f2=conjunction(tempComp);

                    state._fsp--;

                     form = (YAMLDAndFormula)(new YAMLDAndFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:209:7: 
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
    // YAMLDlight.g:212:1: f_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form] : ( 'OR' f2= formula[tempComp] | );
    public final YAMLDFormula f_prime(YAMLDComponent tempComp, YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // YAMLDlight.g:212:77: ( 'OR' f2= formula[tempComp] | )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==38) ) {
                alt10=1;
            }
            else if ( (LA10_0==17||LA10_0==22||LA10_0==27||LA10_0==35) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // YAMLDlight.g:213:7: 'OR' f2= formula[tempComp]
                    {
                    match(input,38,FOLLOW_38_in_f_prime1075); 
                    pushFollow(FOLLOW_formula_in_f_prime1079);
                    f2=formula(tempComp);

                    state._fsp--;

                     form = (YAMLDOrFormula)(new YAMLDOrFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:216:7: 
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
    // YAMLDlight.g:219:1: expression[YAMLDComponent tempComp] returns [YAMLDExpr expr] : s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr] ;
    public final YAMLDExpr expression(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr s1 = null;

        YAMLDExpr e1 = null;


        try {
            // YAMLDlight.g:219:61: (s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr] )
            // YAMLDlight.g:220:7: s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr]
            {
            pushFollow(FOLLOW_simple_expression_in_expression1132);
            s1=simple_expression(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_exp_prime_in_expression1137);
            e1=exp_prime(tempComp, s1);

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
    // YAMLDlight.g:224:1: exp_prime[YAMLDComponent tempComp,YAMLDExpr e] returns [YAMLDExpr expr] : ( '+' e2= expression[tempComp] | );
    public final YAMLDExpr exp_prime(YAMLDComponent tempComp, YAMLDExpr e) throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr e2 = null;


        try {
            // YAMLDlight.g:224:72: ( '+' e2= expression[tempComp] | )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==39) ) {
                alt11=1;
            }
            else if ( (LA11_0==EQUALS||LA11_0==17||LA11_0==19||LA11_0==22||LA11_0==27||LA11_0==35||(LA11_0>=37 && LA11_0<=38)) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // YAMLDlight.g:225:7: '+' e2= expression[tempComp]
                    {
                    match(input,39,FOLLOW_39_in_exp_prime1177); 
                    pushFollow(FOLLOW_expression_in_exp_prime1181);
                    e2=expression(tempComp);

                    state._fsp--;

                     expr = new YAMLDAddExpr(e, e2); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:228:7: 
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
    // YAMLDlight.g:231:1: simple_expression[YAMLDComponent tempComp] returns [YAMLDExpr expr] : (num= INT | 'true' | 'false' | comp_name= ID '.' var_name= ID | var_name= ID );
    public final YAMLDExpr simple_expression(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDExpr expr = null;

        Token num=null;
        Token comp_name=null;
        Token var_name=null;

        try {
            // YAMLDlight.g:231:68: (num= INT | 'true' | 'false' | comp_name= ID '.' var_name= ID | var_name= ID )
            int alt12=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt12=1;
                }
                break;
            case 40:
                {
                alt12=2;
                }
                break;
            case 41:
                {
                alt12=3;
                }
                break;
            case ID:
                {
                int LA12_4 = input.LA(2);

                if ( (LA12_4==42) ) {
                    alt12=4;
                }
                else if ( (LA12_4==EQUALS||LA12_4==17||LA12_4==19||LA12_4==22||LA12_4==27||LA12_4==35||(LA12_4>=37 && LA12_4<=39)) ) {
                    alt12=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // YAMLDlight.g:232:7: num= INT
                    {
                    num=(Token)match(input,INT,FOLLOW_INT_in_simple_expression1234); 
                     expr = YAMLDValue.getValue(Integer.parseInt(num.getText())); 

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:234:7: 'true'
                    {
                    match(input,40,FOLLOW_40_in_simple_expression1251); 
                     expr = YAMLDValue.getValue(1); 

                    }
                    break;
                case 3 :
                    // YAMLDlight.g:236:7: 'false'
                    {
                    match(input,41,FOLLOW_41_in_simple_expression1267); 
                     expr = YAMLDValue.getValue(0); 

                    }
                    break;
                case 4 :
                    // YAMLDlight.g:238:7: comp_name= ID '.' var_name= ID
                    {
                    comp_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1285); 
                    match(input,42,FOLLOW_42_in_simple_expression1287); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1291); 

                            YAMLDID newID = new YAMLDID(var_name.getText());
                            newID.setOwner(comp_name.getText());
                            expr = (YAMLDExpr)(newID); 
                          

                    }
                    break;
                case 5 :
                    // YAMLDlight.g:244:7: var_name= ID
                    {
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1309); 
                     
                            final String name = var_name.getText();
                            if (YAMLDValue.existsValue(name)) {
                              expr = YAMLDValue.getValue(name);
                            } else if (tempComp != null) {
                              YAMLDGenericVar var = tempComp.getVariable(name);
                              expr = new VariableValue(var);
                            } else {
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


    // $ANTLR start "assignment_list"
    // YAMLDlight.g:259:1: assignment_list[YAMLDComponent comp, ArrayList<YAMLDAssignment> al] : assignment[comp,al] ( ',' assignment[comp,al] )* ;
    public final void assignment_list(YAMLDComponent comp, ArrayList<YAMLDAssignment> al) throws RecognitionException {
        try {
            // YAMLDlight.g:259:68: ( assignment[comp,al] ( ',' assignment[comp,al] )* )
            // YAMLDlight.g:260:7: assignment[comp,al] ( ',' assignment[comp,al] )*
            {
            pushFollow(FOLLOW_assignment_in_assignment_list1337);
            assignment(comp, al);

            state._fsp--;

            // YAMLDlight.g:260:27: ( ',' assignment[comp,al] )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==19) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // YAMLDlight.g:260:28: ',' assignment[comp,al]
            	    {
            	    match(input,19,FOLLOW_19_in_assignment_list1341); 
            	    pushFollow(FOLLOW_assignment_in_assignment_list1343);
            	    assignment(comp, al);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
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
    // $ANTLR end "assignment_list"


    // $ANTLR start "assignment"
    // YAMLDlight.g:263:1: assignment[YAMLDComponent comp, ArrayList<YAMLDAssignment> al] : var_name= ID ASSGN e1= expression[comp] ;
    public final void assignment(YAMLDComponent comp, ArrayList<YAMLDAssignment> al) throws RecognitionException {
        Token var_name=null;
        YAMLDExpr e1 = null;


        try {
            // YAMLDlight.g:263:63: (var_name= ID ASSGN e1= expression[comp] )
            // YAMLDlight.g:264:7: var_name= ID ASSGN e1= expression[comp]
            {
            var_name=(Token)match(input,ID,FOLLOW_ID_in_assignment1367); 
            match(input,ASSGN,FOLLOW_ASSGN_in_assignment1369); 
            pushFollow(FOLLOW_expression_in_assignment1373);
            e1=expression(comp);

            state._fsp--;

             
                    al.add(new YAMLDAssignment(comp.getVariable(var_name.getText()),
                            e1)); 
                  

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


    // $ANTLR start "synchro"
    // YAMLDlight.g:271:1: synchro : 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';' ;
    public final void synchro() throws RecognitionException {
        Token c1=null;
        Token e1=null;
        Token c2=null;
        Token e2=null;

        try {
            // YAMLDlight.g:271:8: ( 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';' )
            // YAMLDlight.g:272:7: 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';'
            {
            match(input,43,FOLLOW_43_in_synchro1401); 
            c1=(Token)match(input,ID,FOLLOW_ID_in_synchro1405); 
            match(input,42,FOLLOW_42_in_synchro1407); 
            e1=(Token)match(input,ID,FOLLOW_ID_in_synchro1410); 

                    final YAMLDComponent comp1 = net.getComponent(c1.getText());
                    final YAMLDEvent ev1 = comp1.getEvent(e1.getText());
                    net.startSync(comp1,ev1);
                  
            // YAMLDlight.g:278:7: ( ',' c2= ID '.' e2= ID )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==19) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // YAMLDlight.g:278:8: ',' c2= ID '.' e2= ID
            	    {
            	    match(input,19,FOLLOW_19_in_synchro1427); 
            	    c2=(Token)match(input,ID,FOLLOW_ID_in_synchro1431); 
            	    match(input,42,FOLLOW_42_in_synchro1433); 
            	    e2=(Token)match(input,ID,FOLLOW_ID_in_synchro1437); 

            	            final YAMLDComponent comp2 = net.getComponent(c2.getText());
            	            final YAMLDEvent ev2 = comp2.getEvent(e2.getText());
            	            net.addSync(comp2,ev2);
            	          

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            match(input,17,FOLLOW_17_in_synchro1611); 

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
    // $ANTLR end "synchro"


    // $ANTLR start "observable"
    // YAMLDlight.g:287:1: observable : 'observable' cname= ID '.' ename= ID ';' ;
    public final void observable() throws RecognitionException {
        Token cname=null;
        Token ename=null;

        try {
            // YAMLDlight.g:287:11: ( 'observable' cname= ID '.' ename= ID ';' )
            // YAMLDlight.g:288:6: 'observable' cname= ID '.' ename= ID ';'
            {
            match(input,44,FOLLOW_44_in_observable1628); 
            cname=(Token)match(input,ID,FOLLOW_ID_in_observable1632); 
            match(input,42,FOLLOW_42_in_observable1634); 
            ename=(Token)match(input,ID,FOLLOW_ID_in_observable1638); 
            match(input,17,FOLLOW_17_in_observable1640); 

                   final YAMLDComponent comp = net.getComponent(cname.getText());
                   final YAMLDEvent e = comp.getEvent(ename.getText());
                   net.addObservableEvent(e);
                 

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
    // $ANTLR end "observable"


    // $ANTLR start "state"
    // YAMLDlight.g:296:1: state[Map<YAMLDVar,YAMLDValue> map] : (cname= ID '.' vname= ID ':=' 'true' ';' | cname= ID '.' vname= ID ':=' 'false' ';' | cname= ID '.' vname= ID ':=' valname= ID ';' | cname= ID '.' vname= ID ':=' valname= INT ';' );
    public final void state(Map<YAMLDVar,YAMLDValue> map) throws RecognitionException {
        Token cname=null;
        Token vname=null;
        Token valname=null;

        try {
            // YAMLDlight.g:296:36: (cname= ID '.' vname= ID ':=' 'true' ';' | cname= ID '.' vname= ID ':=' 'false' ';' | cname= ID '.' vname= ID ':=' valname= ID ';' | cname= ID '.' vname= ID ':=' valname= INT ';' )
            int alt15=4;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ID) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==42) ) {
                    int LA15_2 = input.LA(3);

                    if ( (LA15_2==ID) ) {
                        int LA15_3 = input.LA(4);

                        if ( (LA15_3==ASSGN) ) {
                            switch ( input.LA(5) ) {
                            case 40:
                                {
                                alt15=1;
                                }
                                break;
                            case 41:
                                {
                                alt15=2;
                                }
                                break;
                            case ID:
                                {
                                alt15=3;
                                }
                                break;
                            case INT:
                                {
                                alt15=4;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 15, 4, input);

                                throw nvae;
                            }

                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 15, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // YAMLDlight.g:297:7: cname= ID '.' vname= ID ':=' 'true' ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state1668); 
                    match(input,42,FOLLOW_42_in_state1670); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state1674); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state1676); 
                    match(input,40,FOLLOW_40_in_state1678); 
                    match(input,17,FOLLOW_17_in_state1680); 

                            final YAMLDComponent comp = net.getComponent(cname.getText());
                            final YAMLDGenericVar gvar = comp.getVariable(vname.getText());
                            if (gvar instanceof YAMLDVar) {
                                final YAMLDVar var = (YAMLDVar)gvar;
                                final YAMLDValue val = var.getValue(1);
                                map.put(var,val);
                            }
                            
                          

                    }
                    break;
                case 2 :
                    // YAMLDlight.g:308:7: cname= ID '.' vname= ID ':=' 'false' ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state1698); 
                    match(input,42,FOLLOW_42_in_state1700); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state1704); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state1706); 
                    match(input,41,FOLLOW_41_in_state1708); 
                    match(input,17,FOLLOW_17_in_state1710); 

                            final YAMLDComponent comp = net.getComponent(cname.getText());
                            final YAMLDGenericVar gvar = comp.getVariable(vname.getText());
                            if (gvar instanceof YAMLDVar) {
                                final YAMLDVar var = (YAMLDVar)gvar;
                                final YAMLDValue val = var.getValue(0);
                                map.put(var,val);
                            }
                            
                          

                    }
                    break;
                case 3 :
                    // YAMLDlight.g:319:7: cname= ID '.' vname= ID ':=' valname= ID ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state1728); 
                    match(input,42,FOLLOW_42_in_state1730); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state1734); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state1736); 
                    valname=(Token)match(input,ID,FOLLOW_ID_in_state1740); 
                    match(input,17,FOLLOW_17_in_state1742); 

                            final YAMLDComponent comp = net.getComponent(cname.getText());
                            final YAMLDGenericVar gvar = comp.getVariable(vname.getText());
                            if (gvar instanceof YAMLDVar) {
                                final YAMLDVar var = (YAMLDVar)gvar;
                                final YAMLDValue val = var.getValue(valname.getText());
                                map.put(var,val);
                            }
                            
                          

                    }
                    break;
                case 4 :
                    // YAMLDlight.g:330:7: cname= ID '.' vname= ID ':=' valname= INT ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state1760); 
                    match(input,42,FOLLOW_42_in_state1762); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state1766); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state1768); 
                    valname=(Token)match(input,INT,FOLLOW_INT_in_state1772); 
                    match(input,17,FOLLOW_17_in_state1774); 

                            final YAMLDComponent comp = net.getComponent(cname.getText());
                            final YAMLDGenericVar gvar = comp.getVariable(vname.getText());
                            if (gvar instanceof YAMLDVar) {
                                final YAMLDVar var = (YAMLDVar)gvar;
                                final YAMLDValue val = var.getValue(Integer.parseInt(valname.getText()));
                                map.put(var,val);
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
        return ;
    }
    // $ANTLR end "state"

    // Delegated rules


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA6_eotS =
        "\30\uffff";
    static final String DFA6_eofS =
        "\30\uffff";
    static final String DFA6_minS =
        "\1\20\2\uffff\3\4\1\32\2\uffff\2\25\1\5\1\uffff\1\4\2\16\1\uffff"+
        "\1\5\6\uffff";
    static final String DFA6_maxS =
        "\1\36\2\uffff\2\4\1\51\1\32\2\uffff\2\25\1\52\1\uffff\1\51\2\26"+
        "\1\uffff\1\52\6\uffff";
    static final String DFA6_acceptS =
        "\1\uffff\1\1\1\2\4\uffff\1\13\1\14\3\uffff\1\10\3\uffff\1\7\1\uffff"+
        "\1\12\1\3\1\4\1\5\1\6\1\11";
    static final String DFA6_specialS =
        "\30\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1\1\uffff\1\2\1\uffff\1\3\4\uffff\1\4\1\5\1\uffff\1\6\1\7"+
            "\1\10",
            "",
            "",
            "\1\11",
            "\1\12",
            "\1\13\1\uffff\1\14\30\uffff\4\14\1\uffff\1\14\3\uffff\2\14",
            "\1\15",
            "",
            "",
            "\1\16",
            "\1\17",
            "\1\14\17\uffff\1\20\21\uffff\1\14\2\uffff\1\14",
            "",
            "\1\21\1\uffff\1\22\30\uffff\4\22\1\uffff\1\22\3\uffff\2\22",
            "\1\24\7\uffff\1\23",
            "\1\26\7\uffff\1\25",
            "",
            "\1\22\17\uffff\1\27\21\uffff\1\22\2\uffff\1\22",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "59:1: comp_def[YAMLDComponent tempComp,YAMLDTempConnections tempConn] : ( 'event' event_name= ID ';' | 'coords' x= INT ',' y= INT ';' | 'var' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'var' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'dvar' var_name= ID ':' '[' var_dom_beg= INT '..' var_dom_end= INT ']' ';' | 'dvar' var_name= ID ':' '{' var_dom[domElems] '}' ';' | 'transition' event_name= ID ':' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'transition' f= formula[tempComp] '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' event_name= ID ':' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'forced' 'transition' f= formula[tempComp] '[' earliest= FLOAT '..' latest= FLOAT ']' '->' assignment_list[tempComp,al] ';' | 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';' | 'constraint' f= formula[tempComp] ';' );";
        }
    }
    static final String DFA8_eotS =
        "\16\uffff";
    static final String DFA8_eofS =
        "\16\uffff";
    static final String DFA8_minS =
        "\1\4\3\uffff\1\4\2\uffff\1\42\1\4\1\27\1\4\1\27\2\uffff";
    static final String DFA8_maxS =
        "\1\51\3\uffff\1\4\2\uffff\1\42\1\4\1\27\1\4\1\43\2\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\6\1\7\5\uffff\1\4\1\5";
    static final String DFA8_specialS =
        "\16\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\1\1\uffff\1\1\30\uffff\1\2\1\3\1\4\1\5\1\uffff\1\6\3\uffff"+
            "\2\1",
            "",
            "",
            "",
            "\1\7",
            "",
            "",
            "\1\10",
            "\1\11",
            "\1\12",
            "\1\13",
            "\1\15\13\uffff\1\14",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "163:1: atomic[YAMLDComponent tempComp] returns [YAMLDFormula form] : (e1= expression[tempComp] EQUALS e2= expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] );";
        }
    }
 

    public static final BitSet FOLLOW_comp_in_net54 = new BitSet(new long[]{0x0000080000002000L});
    public static final BitSet FOLLOW_synchro_in_net74 = new BitSet(new long[]{0x0000180000000010L});
    public static final BitSet FOLLOW_observable_in_net85 = new BitSet(new long[]{0x0000100000000010L});
    public static final BitSet FOLLOW_state_in_net96 = new BitSet(new long[]{0x0000100000000012L});
    public static final BitSet FOLLOW_13_in_comp139 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp143 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_comp145 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_comp162 = new BitSet(new long[]{0x0000000076158000L});
    public static final BitSet FOLLOW_comp_def_in_comp165 = new BitSet(new long[]{0x0000000076158000L});
    public static final BitSet FOLLOW_15_in_comp170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_comp_def198 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def202 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_comp_def221 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def225 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_comp_def227 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def231 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_comp_def250 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def254 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def256 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_comp_def258 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def262 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_comp_def264 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def268 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_comp_def270 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_comp_def297 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def301 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def303 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_comp_def305 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_var_dom_in_comp_def307 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_comp_def310 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_comp_def328 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def332 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def334 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_comp_def336 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def340 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_comp_def342 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_comp_def346 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_comp_def348 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_comp_def376 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def380 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def382 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_comp_def384 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_var_dom_in_comp_def386 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_comp_def389 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_comp_def415 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def419 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def421 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_comp_def425 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_comp_def428 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_list_in_comp_def430 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_comp_def457 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_comp_def461 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_comp_def464 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_list_in_comp_def466 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_comp_def493 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_comp_def495 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def499 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def501 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_comp_def505 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_comp_def508 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_comp_def512 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_comp_def514 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_comp_def518 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_comp_def520 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_comp_def522 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_list_in_comp_def524 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_comp_def551 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_comp_def553 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_comp_def557 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_comp_def560 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_comp_def564 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_comp_def566 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_comp_def570 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_comp_def572 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_comp_def574 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_list_in_comp_def576 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_comp_def595 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def599 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_comp_def601 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def606 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_comp_def608 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp_def612 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_comp_def630 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_comp_def634 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_comp_def637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_var_dom665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_var_dom684 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_var_dom686 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_var_dom_in_var_dom688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conjunction_in_formula730 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_f_prime_in_formula735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomic_in_conjunction770 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_c_prime_in_conjunction775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_atomic809 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_atomic812 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_expression_in_atomic816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_atomic840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_atomic857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_atomic873 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic877 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic879 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic883 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic885 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic889 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic891 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic893 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic897 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_atomic916 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic920 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic922 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic926 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic928 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic932 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic934 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic938 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic940 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic942 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic946 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_atomic965 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic969 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_atomic988 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_atomic_in_atomic992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_c_prime1024 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_conjunction_in_c_prime1028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_f_prime1075 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_f_prime1079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_expression_in_expression1132 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_exp_prime_in_expression1137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_exp_prime1177 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_expression_in_exp_prime1181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_simple_expression1234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_simple_expression1251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_simple_expression1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression1285 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_simple_expression1287 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_simple_expression1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_assignment_list1337 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_assignment_list1341 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_in_assignment_list1343 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_ID_in_assignment1367 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_assignment1369 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_expression_in_assignment1373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_synchro1401 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1405 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synchro1407 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1410 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_synchro1427 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1431 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synchro1433 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1437 = new BitSet(new long[]{0x00000000000A0000L});
    public static final BitSet FOLLOW_17_in_synchro1611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_observable1628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_observable1632 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_observable1634 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_observable1638 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_observable1640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state1668 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state1670 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1674 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state1676 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_state1678 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_state1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state1698 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state1700 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1704 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state1706 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_state1708 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_state1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state1728 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state1730 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1734 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state1736 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1740 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_state1742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state1760 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state1762 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1766 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state1768 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_state1772 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_state1774 = new BitSet(new long[]{0x0000000000000002L});

}