// $ANTLR 3.2 Sep 23, 2009 12:02:23 MMLDlight.g 2011-11-14 11:09:58

package lang;

import util.Coords;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class MMLDlightParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "EQUALS", "INT", "FLOAT", "ASSGN", "REMCPP", "REMC", "WS", "DIGIT", "'component'", "'{'", "'}'", "'coords'", "','", "';'", "'var'", "'dvar'", "':'", "'['", "'..'", "']'", "'event'", "'connection'", "'transition'", "'triggeredby'", "'->'", "'constraint'", "'FALSE'", "'TRUE'", "'exists'", "'('", "')'", "'NOT'", "'AND'", "'OR'", "'+'", "'true'", "'false'", "'.'", "'synchronize'", "'observable'"
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


        public MMLDlightParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public MMLDlightParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return MMLDlightParser.tokenNames; }
    public String getGrammarFileName() { return "MMLDlight.g"; }


    public static Network net = null;
    public static State st = null;



    // $ANTLR start "net"
    // MMLDlight.g:33:1: net : ( comp[tempConn] )+ ( synchro | observable | state[init] )* ;
    public final void net() throws RecognitionException {
        try {
            // MMLDlight.g:33:4: ( ( comp[tempConn] )+ ( synchro | observable | state[init] )* )
            // MMLDlight.g:34:7: ( comp[tempConn] )+ ( synchro | observable | state[init] )*
            {

                    net = new Network();
                    final YAMLDTempConnections tempConn = new YAMLDTempConnections();
                  
            // MMLDlight.g:38:7: ( comp[tempConn] )+
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
            	    // MMLDlight.g:38:8: comp[tempConn]
            	    {
            	    pushFollow(FOLLOW_comp_in_net55);
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
                  
            // MMLDlight.g:43:7: ( synchro | observable | state[init] )*
            loop2:
            do {
                int alt2=4;
                switch ( input.LA(1) ) {
                case 43:
                    {
                    alt2=1;
                    }
                    break;
                case 44:
                    {
                    alt2=2;
                    }
                    break;
                case ID:
                    {
                    alt2=3;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // MMLDlight.g:43:8: synchro
            	    {
            	    pushFollow(FOLLOW_synchro_in_net75);
            	    synchro();

            	    state._fsp--;


            	    }
            	    break;
            	case 2 :
            	    // MMLDlight.g:43:18: observable
            	    {
            	    pushFollow(FOLLOW_observable_in_net79);
            	    observable();

            	    state._fsp--;


            	    }
            	    break;
            	case 3 :
            	    // MMLDlight.g:43:31: state[init]
            	    {
            	    pushFollow(FOLLOW_state_in_net83);
            	    state(init);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
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
    // MMLDlight.g:49:1: comp[YAMLDTempConnections tempConn] : 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,in,out,u,tempConn] )* '}' ;
    public final void comp(YAMLDTempConnections tempConn) throws RecognitionException {
        Token comp_name=null;

        try {
            // MMLDlight.g:49:36: ( 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,in,out,u,tempConn] )* '}' )
            // MMLDlight.g:50:7: 'component' comp_name= ID EQUALS '{' ( comp_def[tempComp,in,out,u,tempConn] )* '}'
            {
             YAMLDComponent tempComp = new YAMLDComponent(); 
            match(input,13,FOLLOW_13_in_comp126); 
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_comp130); 
            match(input,EQUALS,FOLLOW_EQUALS_in_comp132); 

                    tempComp.setName(comp_name.getText());
                    Set<YAMLDEvent> in = new HashSet<YAMLDEvent>(); // input events
                    Set<YAMLDEvent> out = new HashSet<YAMLDEvent>(); // output events
                    Set<YAMLDEvent> u = new HashSet<YAMLDEvent>(); // unknown (so far)
                  
            match(input,14,FOLLOW_14_in_comp148); 
            // MMLDlight.g:58:11: ( comp_def[tempComp,in,out,u,tempConn] )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16||(LA3_0>=19 && LA3_0<=20)||(LA3_0>=25 && LA3_0<=27)||LA3_0==30) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // MMLDlight.g:58:12: comp_def[tempComp,in,out,u,tempConn]
            	    {
            	    pushFollow(FOLLOW_comp_def_in_comp151);
            	    comp_def(tempComp, in, out, u, tempConn);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match(input,15,FOLLOW_15_in_comp156); 
             
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
    // MMLDlight.g:64:1: comp_def[YAMLDComponent tempComp,\n Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u, \n YAMLDTempConnections tempConn] : ( coords_def[tempComp] | var_def[tempComp] | event_def[tempComp,u] | connection_def[tempComp,tempConn] | trans_def[tempComp,in,out,u] | constraint_def[tempComp] ) ;
    public final void comp_def(YAMLDComponent tempComp, Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u, YAMLDTempConnections tempConn) throws RecognitionException {
        try {
            // MMLDlight.g:66:40: ( ( coords_def[tempComp] | var_def[tempComp] | event_def[tempComp,u] | connection_def[tempComp,tempConn] | trans_def[tempComp,in,out,u] | constraint_def[tempComp] ) )
            // MMLDlight.g:67:5: ( coords_def[tempComp] | var_def[tempComp] | event_def[tempComp,u] | connection_def[tempComp,tempConn] | trans_def[tempComp,in,out,u] | constraint_def[tempComp] )
            {
            // MMLDlight.g:67:5: ( coords_def[tempComp] | var_def[tempComp] | event_def[tempComp,u] | connection_def[tempComp,tempConn] | trans_def[tempComp,in,out,u] | constraint_def[tempComp] )
            int alt4=6;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt4=1;
                }
                break;
            case 19:
            case 20:
                {
                alt4=2;
                }
                break;
            case 25:
                {
                alt4=3;
                }
                break;
            case 26:
                {
                alt4=4;
                }
                break;
            case 27:
                {
                alt4=5;
                }
                break;
            case 30:
                {
                alt4=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // MMLDlight.g:68:8: coords_def[tempComp]
                    {
                    pushFollow(FOLLOW_coords_def_in_comp_def191);
                    coords_def(tempComp);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // MMLDlight.g:69:8: var_def[tempComp]
                    {
                    pushFollow(FOLLOW_var_def_in_comp_def201);
                    var_def(tempComp);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // MMLDlight.g:70:8: event_def[tempComp,u]
                    {
                    pushFollow(FOLLOW_event_def_in_comp_def211);
                    event_def(tempComp, u);

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // MMLDlight.g:71:8: connection_def[tempComp,tempConn]
                    {
                    pushFollow(FOLLOW_connection_def_in_comp_def221);
                    connection_def(tempComp, tempConn);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // MMLDlight.g:72:8: trans_def[tempComp,in,out,u]
                    {
                    pushFollow(FOLLOW_trans_def_in_comp_def231);
                    trans_def(tempComp, in, out, u);

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // MMLDlight.g:73:8: constraint_def[tempComp]
                    {
                    pushFollow(FOLLOW_constraint_def_in_comp_def241);
                    constraint_def(tempComp);

                    state._fsp--;


                    }
                    break;

            }


                  for (final YAMLDEvent e: u) {
                    e.setInput(false);
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
    // $ANTLR end "comp_def"


    // $ANTLR start "coords_def"
    // MMLDlight.g:82:1: coords_def[YAMLDComponent tempComp] : 'coords' x= INT ',' y= INT ';' ;
    public final void coords_def(YAMLDComponent tempComp) throws RecognitionException {
        Token x=null;
        Token y=null;

        try {
            // MMLDlight.g:82:36: ( 'coords' x= INT ',' y= INT ';' )
            // MMLDlight.g:83:3: 'coords' x= INT ',' y= INT ';'
            {
            match(input,16,FOLLOW_16_in_coords_def267); 
            x=(Token)match(input,INT,FOLLOW_INT_in_coords_def271); 
            match(input,17,FOLLOW_17_in_coords_def273); 
            y=(Token)match(input,INT,FOLLOW_INT_in_coords_def277); 
            match(input,18,FOLLOW_18_in_coords_def279); 

                    tempComp.visOptions().
                        coords().
                            add(new Coords(Integer.parseInt(x.getText()),
                                           Integer.parseInt(y.getText())));
                  

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
    // $ANTLR end "coords_def"


    // $ANTLR start "var_def"
    // MMLDlight.g:92:1: var_def[YAMLDComponent tempComp] : ( 'var' | 'dvar' ) var_name= ID ':' ( ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' ) | ( '{' var_dom[domElems] '}' ) ) ';' ;
    public final void var_def(YAMLDComponent tempComp) throws RecognitionException {
        Token var_name=null;
        Token var_dom_beg=null;
        Token var_dom_end=null;

        try {
            // MMLDlight.g:92:33: ( ( 'var' | 'dvar' ) var_name= ID ':' ( ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' ) | ( '{' var_dom[domElems] '}' ) ) ';' )
            // MMLDlight.g:93:9: ( 'var' | 'dvar' ) var_name= ID ':' ( ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' ) | ( '{' var_dom[domElems] '}' ) ) ';'
            {

                      boolean isDependent = true;
                      YAMLDGenericVar var; 
                    
            // MMLDlight.g:97:5: ( 'var' | 'dvar' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            else if ( (LA5_0==20) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // MMLDlight.g:98:7: 'var'
                    {
                    match(input,19,FOLLOW_19_in_var_def319); 
                    isDependent = false;

                    }
                    break;
                case 2 :
                    // MMLDlight.g:100:7: 'dvar'
                    {
                    match(input,20,FOLLOW_20_in_var_def338); 
                    isDependent = true;

                    }
                    break;

            }

            var_name=(Token)match(input,ID,FOLLOW_ID_in_var_def354); 
            match(input,21,FOLLOW_21_in_var_def356); 

                      String varName = var_name.getText();
                      if (isDependent) {
                        YAMLDDVar v = new YAMLDDVar(varName,tempComp);
                        tempComp.addDVar(v);
                        var = v;
                      } else {
                        YAMLDVar v = new YAMLDVar(varName,tempComp);
                        tempComp.addVar(v);
                        var = v;
                      }
                    
            // MMLDlight.g:115:5: ( ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' ) | ( '{' var_dom[domElems] '}' ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==22) ) {
                alt6=1;
            }
            else if ( (LA6_0==14) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // MMLDlight.g:116:7: ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' )
                    {
                    // MMLDlight.g:116:7: ( '[' var_dom_beg= INT '..' var_dom_end= INT ']' )
                    // MMLDlight.g:117:9: '[' var_dom_beg= INT '..' var_dom_end= INT ']'
                    {
                    match(input,22,FOLLOW_22_in_var_def392); 
                    var_dom_beg=(Token)match(input,INT,FOLLOW_INT_in_var_def396); 
                    match(input,23,FOLLOW_23_in_var_def398); 
                    var_dom_end=(Token)match(input,INT,FOLLOW_INT_in_var_def402); 
                    match(input,24,FOLLOW_24_in_var_def404); 

                                var.setRange(Integer.parseInt(var_dom_beg.getText()),
                                                 Integer.parseInt(var_dom_end.getText()));
                              

                    }


                    }
                    break;
                case 2 :
                    // MMLDlight.g:124:7: ( '{' var_dom[domElems] '}' )
                    {
                    // MMLDlight.g:124:7: ( '{' var_dom[domElems] '}' )
                    // MMLDlight.g:125:11: '{' var_dom[domElems] '}'
                    {
                     
                                ArrayList<String> domElems = new ArrayList<String>(); 
                              
                    match(input,14,FOLLOW_14_in_var_def461); 
                    pushFollow(FOLLOW_var_dom_in_var_def463);
                    var_dom(domElems);

                    state._fsp--;

                    match(input,15,FOLLOW_15_in_var_def466); 

                                for (String val : domElems)
                                  var.domainPush(val);
                              

                    }


                    }
                    break;

            }

            match(input,18,FOLLOW_18_in_var_def499); 

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
    // $ANTLR end "var_def"


    // $ANTLR start "event_def"
    // MMLDlight.g:138:1: event_def[YAMLDComponent tempComp, Set<YAMLDEvent> u] : 'event' event_name= ID ';' ;
    public final void event_def(YAMLDComponent tempComp, Set<YAMLDEvent> u) throws RecognitionException {
        Token event_name=null;

        try {
            // MMLDlight.g:138:54: ( 'event' event_name= ID ';' )
            // MMLDlight.g:139:7: 'event' event_name= ID ';'
            {
            match(input,25,FOLLOW_25_in_event_def514); 
            event_name=(Token)match(input,ID,FOLLOW_ID_in_event_def518); 
            match(input,18,FOLLOW_18_in_event_def520); 
             
                    final YAMLDEvent e = new YAMLDEvent(tempComp,event_name.getText()); 
                    tempComp.addEvent(e);
                    u.add(e); 
                  

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
    // $ANTLR end "event_def"


    // $ANTLR start "connection_def"
    // MMLDlight.g:147:1: connection_def[YAMLDComponent tempComp,YAMLDTempConnections tempConn] : 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';' ;
    public final void connection_def(YAMLDComponent tempComp, YAMLDTempConnections tempConn) throws RecognitionException {
        Token conn_name=null;
        Token conn_type=null;
        Token comp_name=null;

        try {
            // MMLDlight.g:147:70: ( 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';' )
            // MMLDlight.g:148:3: 'connection' conn_name= ID ':' conn_type= ID EQUALS comp_name= ID ';'
            {
            match(input,26,FOLLOW_26_in_connection_def540); 
            conn_name=(Token)match(input,ID,FOLLOW_ID_in_connection_def544); 
            match(input,21,FOLLOW_21_in_connection_def546); 
            conn_type=(Token)match(input,ID,FOLLOW_ID_in_connection_def551); 
            match(input,EQUALS,FOLLOW_EQUALS_in_connection_def553); 
            comp_name=(Token)match(input,ID,FOLLOW_ID_in_connection_def557); 
            match(input,18,FOLLOW_18_in_connection_def559); 

                    tempConn.add(tempComp, comp_name.getText(),
                                 conn_name.getText(),conn_type.getText());
                  

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
    // $ANTLR end "connection_def"


    // $ANTLR start "trans_def"
    // MMLDlight.g:155:1: trans_def[YAMLDComponent tempComp, Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u] : 'transition' trans_name= ID ( rule_def[tempComp,out,u,rules] )* ( 'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond] ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )* ';' )? ;
    public final void trans_def(YAMLDComponent tempComp, Set<YAMLDEvent> in, Set<YAMLDEvent> out, Set<YAMLDEvent> u) throws RecognitionException {
        Token trans_name=null;

        try {
            // MMLDlight.g:155:95: ( 'transition' trans_name= ID ( rule_def[tempComp,out,u,rules] )* ( 'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond] ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )* ';' )? )
            // MMLDlight.g:156:3: 'transition' trans_name= ID ( rule_def[tempComp,out,u,rules] )* ( 'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond] ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )* ';' )?
            {

                final Set<YAMLDEvent> triggeringEvents = new HashSet<YAMLDEvent>();
                final Map<YAMLDFormula,PeriodInterval> precond = new HashMap<YAMLDFormula,PeriodInterval>(); 
                final Collection<MMLDRule> rules = new ArrayList<MMLDRule>();
              
            match(input,27,FOLLOW_27_in_trans_def582); 
            trans_name=(Token)match(input,ID,FOLLOW_ID_in_trans_def586); 
            // MMLDlight.g:161:30: ( rule_def[tempComp,out,u,rules] )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // MMLDlight.g:161:30: rule_def[tempComp,out,u,rules]
            	    {
            	    pushFollow(FOLLOW_rule_def_in_trans_def588);
            	    rule_def(tempComp, out, u, rules);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // MMLDlight.g:162:3: ( 'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond] ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )* ';' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==28) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // MMLDlight.g:163:5: 'triggeredby' triggering_cond[tempComp,in,u,triggeringEvents,precond] ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )* ';'
                    {
                    match(input,28,FOLLOW_28_in_trans_def602); 
                    pushFollow(FOLLOW_triggering_cond_in_trans_def604);
                    triggering_cond(tempComp, in, u, triggeringEvents, precond);

                    state._fsp--;

                    // MMLDlight.g:164:5: ( ',' triggering_cond[tempComp,in,u,triggeringEvents,precond] )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==17) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // MMLDlight.g:164:6: ',' triggering_cond[tempComp,in,u,triggeringEvents,precond]
                    	    {
                    	    match(input,17,FOLLOW_17_in_trans_def612); 
                    	    pushFollow(FOLLOW_triggering_cond_in_trans_def614);
                    	    triggering_cond(tempComp, in, u, triggeringEvents, precond);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    match(input,18,FOLLOW_18_in_trans_def623); 

                    }
                    break;

            }


                final MMLDTransition trans = new MMLDTransition(trans_name.getText(), 
                  tempComp, rules, triggeringEvents, precond);
                tempComp.addTransition(trans);
              

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
    // $ANTLR end "trans_def"


    // $ANTLR start "rule_def"
    // MMLDlight.g:174:1: rule_def[YAMLDComponent tempComp, \n Set<YAMLDEvent> out, Set<YAMLDEvent> u, \n Collection<MMLDRule> rules] : rule_name= ID f= formula[tempComp] ( '->' assignment_or_event[tempComp,out,u,emitted,al] ( ',' assignment_or_event[tempComp,out,u,emitted,al] )* )? ';' ;
    public final void rule_def(YAMLDComponent tempComp, Set<YAMLDEvent> out, Set<YAMLDEvent> u, Collection<MMLDRule> rules) throws RecognitionException {
        Token rule_name=null;
        YAMLDFormula f = null;


        try {
            // MMLDlight.g:176:30: (rule_name= ID f= formula[tempComp] ( '->' assignment_or_event[tempComp,out,u,emitted,al] ( ',' assignment_or_event[tempComp,out,u,emitted,al] )* )? ';' )
            // MMLDlight.g:177:3: rule_name= ID f= formula[tempComp] ( '->' assignment_or_event[tempComp,out,u,emitted,al] ( ',' assignment_or_event[tempComp,out,u,emitted,al] )* )? ';'
            {
            rule_name=(Token)match(input,ID,FOLLOW_ID_in_rule_def648); 
            pushFollow(FOLLOW_formula_in_rule_def652);
            f=formula(tempComp);

            state._fsp--;


                  ArrayList<YAMLDAssignment> al = new ArrayList<YAMLDAssignment>();
                  Set<YAMLDEvent> emitted = new HashSet<YAMLDEvent>();
                
            // MMLDlight.g:182:3: ( '->' assignment_or_event[tempComp,out,u,emitted,al] ( ',' assignment_or_event[tempComp,out,u,emitted,al] )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==29) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // MMLDlight.g:183:5: '->' assignment_or_event[tempComp,out,u,emitted,al] ( ',' assignment_or_event[tempComp,out,u,emitted,al] )*
                    {
                    match(input,29,FOLLOW_29_in_rule_def671); 
                    pushFollow(FOLLOW_assignment_or_event_in_rule_def673);
                    assignment_or_event(tempComp, out, u, emitted, al);

                    state._fsp--;

                    // MMLDlight.g:184:10: ( ',' assignment_or_event[tempComp,out,u,emitted,al] )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==17) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // MMLDlight.g:184:12: ',' assignment_or_event[tempComp,out,u,emitted,al]
                    	    {
                    	    match(input,17,FOLLOW_17_in_rule_def688); 
                    	    pushFollow(FOLLOW_assignment_or_event_in_rule_def690);
                    	    assignment_or_event(tempComp, out, u, emitted, al);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,18,FOLLOW_18_in_rule_def705); 

                  MMLDRule rule = new MMLDRule(tempComp, f, al, 
                        emitted, rule_name.getText());
                  rules.add(rule);
                

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
    // $ANTLR end "rule_def"


    // $ANTLR start "assignment_or_event"
    // MMLDlight.g:194:1: assignment_or_event[YAMLDComponent tempComp, \n Set<YAMLDEvent> out, Set<YAMLDEvent> u, Collection<YAMLDEvent> emitted, \n ArrayList<YAMLDAssignment> al] : ( assignment[tempComp,al] | event_name= ID );
    public final void assignment_or_event(YAMLDComponent tempComp, Set<YAMLDEvent> out, Set<YAMLDEvent> u, Collection<YAMLDEvent> emitted, ArrayList<YAMLDAssignment> al) throws RecognitionException {
        Token event_name=null;

        try {
            // MMLDlight.g:196:35: ( assignment[tempComp,al] | event_name= ID )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==ASSGN) ) {
                    alt12=1;
                }
                else if ( ((LA12_1>=17 && LA12_1<=18)) ) {
                    alt12=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // MMLDlight.g:197:5: assignment[tempComp,al]
                    {
                    pushFollow(FOLLOW_assignment_in_assignment_or_event724);
                    assignment(tempComp, al);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // MMLDlight.g:199:5: event_name= ID
                    {
                    event_name=(Token)match(input,ID,FOLLOW_ID_in_assignment_or_event738); 

                          final YAMLDEvent evt = tempComp.getEvent(event_name.getText());
                          if (evt == null) {
                            throw new org.antlr.runtime.FailedPredicateException(
                                input, "Unknown event", event_name.getText()
                            );
                          }
                          if (!out.contains(evt)) {
                            if (!u.remove(evt)) {
                              throw new org.antlr.runtime.FailedPredicateException(
                                input, "Effect of transition", event_name.getText()
                              );
                            }
                            evt.setInput(false);
                            out.add(evt);
                          }
                          emitted.add(evt);
                        

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
    // $ANTLR end "assignment_or_event"


    // $ANTLR start "triggering_cond"
    // MMLDlight.g:220:1: triggering_cond[YAMLDComponent tempComp, \n Set<YAMLDEvent> in, Set<YAMLDEvent> u, Set<YAMLDEvent> triggeringEvents, \n Map<YAMLDFormula,PeriodInterval> precond] : ( (event_name= ID ) | ( '[' earliest= FLOAT '..' latest= FLOAT ']' f= formula[tempComp] ) );
    public final void triggering_cond(YAMLDComponent tempComp, Set<YAMLDEvent> in, Set<YAMLDEvent> u, Set<YAMLDEvent> triggeringEvents, Map<YAMLDFormula,PeriodInterval> precond) throws RecognitionException {
        Token event_name=null;
        Token earliest=null;
        Token latest=null;
        YAMLDFormula f = null;


        try {
            // MMLDlight.g:222:44: ( (event_name= ID ) | ( '[' earliest= FLOAT '..' latest= FLOAT ']' f= formula[tempComp] ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ID) ) {
                alt13=1;
            }
            else if ( (LA13_0==22) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // MMLDlight.g:223:5: (event_name= ID )
                    {
                    // MMLDlight.g:223:5: (event_name= ID )
                    // MMLDlight.g:224:7: event_name= ID
                    {
                    event_name=(Token)match(input,ID,FOLLOW_ID_in_triggering_cond769); 

                            final YAMLDEvent evt = tempComp.getEvent(event_name.getText());
                            if (evt == null) {
                                throw new org.antlr.runtime.FailedPredicateException(
                                  input, "Unknown event", event_name.getText()
                                );
                            }
                            if (!in.contains(evt)) {
                              if (!u.remove(evt)) {
                                throw new org.antlr.runtime.FailedPredicateException(
                                  input, "Triggering condition", event_name.getText()
                                );
                              }
                            }
                            evt.setInput(true);
                            triggeringEvents.add(evt);
                            in.add(evt);
                          

                    }


                    }
                    break;
                case 2 :
                    // MMLDlight.g:245:5: ( '[' earliest= FLOAT '..' latest= FLOAT ']' f= formula[tempComp] )
                    {
                    // MMLDlight.g:245:5: ( '[' earliest= FLOAT '..' latest= FLOAT ']' f= formula[tempComp] )
                    // MMLDlight.g:246:7: '[' earliest= FLOAT '..' latest= FLOAT ']' f= formula[tempComp]
                    {
                    match(input,22,FOLLOW_22_in_triggering_cond804); 
                    earliest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_triggering_cond808); 
                    match(input,23,FOLLOW_23_in_triggering_cond810); 
                    latest=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_triggering_cond814); 
                    match(input,24,FOLLOW_24_in_triggering_cond816); 
                    pushFollow(FOLLOW_formula_in_triggering_cond820);
                    f=formula(tempComp);

                    state._fsp--;


                             final PeriodInterval ti = new PeriodInterval(
                               new Period(Double.parseDouble(earliest.getText())), 
                               new Period(Double.parseDouble(latest.getText()))
                               );
                             final YAMLDFormula form = f;
                             precond.put(form,ti); 
                           

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
    // $ANTLR end "triggering_cond"


    // $ANTLR start "constraint_def"
    // MMLDlight.g:258:1: constraint_def[YAMLDComponent tempComp] : 'constraint' f= formula[tempComp] ';' ;
    public final void constraint_def(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula f = null;


        try {
            // MMLDlight.g:258:40: ( 'constraint' f= formula[tempComp] ';' )
            // MMLDlight.g:259:5: 'constraint' f= formula[tempComp] ';'
            {
            match(input,30,FOLLOW_30_in_constraint_def850); 
            pushFollow(FOLLOW_formula_in_constraint_def854);
            f=formula(tempComp);

            state._fsp--;

            match(input,18,FOLLOW_18_in_constraint_def857); 
             
                    final YAMLDConstraint con = new YAMLDConstraint(f, tempComp);
                    final YAMLDDVar var = con.getVariable();
                    var.addConstraint(con);
                  

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
    // $ANTLR end "constraint_def"


    // $ANTLR start "var_dom"
    // MMLDlight.g:267:1: var_dom[ArrayList<String> domElems] : (dom_elem= ID | dom_elem= ID ',' var_dom[domElems] );
    public final void var_dom(ArrayList<String> domElems) throws RecognitionException {
        Token dom_elem=null;

        try {
            // MMLDlight.g:267:36: (dom_elem= ID | dom_elem= ID ',' var_dom[domElems] )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ID) ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==17) ) {
                    alt14=2;
                }
                else if ( (LA14_1==15) ) {
                    alt14=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // MMLDlight.g:268:7: dom_elem= ID
                    {
                    dom_elem=(Token)match(input,ID,FOLLOW_ID_in_var_dom882); 
                     domElems.add(dom_elem.getText()); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:270:7: dom_elem= ID ',' var_dom[domElems]
                    {
                    dom_elem=(Token)match(input,ID,FOLLOW_ID_in_var_dom901); 
                    match(input,17,FOLLOW_17_in_var_dom903); 
                    pushFollow(FOLLOW_var_dom_in_var_dom905);
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
    // MMLDlight.g:274:1: formula[YAMLDComponent tempComp] returns [YAMLDFormula form] : co= conjunction[tempComp] f= f_prime[tempComp,$co.form] ;
    public final YAMLDFormula formula(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula co = null;

        YAMLDFormula f = null;


        try {
            // MMLDlight.g:274:61: (co= conjunction[tempComp] f= f_prime[tempComp,$co.form] )
            // MMLDlight.g:275:7: co= conjunction[tempComp] f= f_prime[tempComp,$co.form]
            {
            pushFollow(FOLLOW_conjunction_in_formula947);
            co=conjunction(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_f_prime_in_formula952);
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
    // MMLDlight.g:279:1: conjunction[YAMLDComponent tempComp] returns [YAMLDFormula form] : at= atomic[tempComp] f= c_prime[tempComp,$at.form] ;
    public final YAMLDFormula conjunction(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula at = null;

        YAMLDFormula f = null;


        try {
            // MMLDlight.g:279:65: (at= atomic[tempComp] f= c_prime[tempComp,$at.form] )
            // MMLDlight.g:280:7: at= atomic[tempComp] f= c_prime[tempComp,$at.form]
            {
            pushFollow(FOLLOW_atomic_in_conjunction987);
            at=atomic(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_c_prime_in_conjunction992);
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
    // MMLDlight.g:284:1: atomic[YAMLDComponent tempComp] returns [YAMLDFormula form] : (e1= expression[tempComp] EQUALS e2= value_expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] );
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
            // MMLDlight.g:284:60: (e1= expression[tempComp] EQUALS e2= value_expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] )
            int alt15=7;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // MMLDlight.g:285:7: e1= expression[tempComp] EQUALS e2= value_expression[tempComp]
                    {
                    pushFollow(FOLLOW_expression_in_atomic1026);
                    e1=expression(tempComp);

                    state._fsp--;

                    match(input,EQUALS,FOLLOW_EQUALS_in_atomic1029); 
                    pushFollow(FOLLOW_value_expression_in_atomic1033);
                    e2=value_expression(tempComp);

                    state._fsp--;

                    form = new YAMLDEqFormula(e1, e2);

                    }
                    break;
                case 2 :
                    // MMLDlight.g:288:7: 'FALSE'
                    {
                    match(input,31,FOLLOW_31_in_atomic1057); 
                     form = YAMLDFalse.FALSE; 

                    }
                    break;
                case 3 :
                    // MMLDlight.g:290:7: 'TRUE'
                    {
                    match(input,32,FOLLOW_32_in_atomic1074); 
                     form = YAMLDTrue.TRUE; 

                    }
                    break;
                case 4 :
                    // MMLDlight.g:292:7: 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')'
                    {
                    match(input,33,FOLLOW_33_in_atomic1090); 
                    connt=(Token)match(input,ID,FOLLOW_ID_in_atomic1094); 
                    match(input,34,FOLLOW_34_in_atomic1096); 
                    first=(Token)match(input,ID,FOLLOW_ID_in_atomic1100); 
                    match(input,23,FOLLOW_23_in_atomic1102); 
                    last=(Token)match(input,ID,FOLLOW_ID_in_atomic1106); 
                    match(input,35,FOLLOW_35_in_atomic1108); 
                    match(input,34,FOLLOW_34_in_atomic1110); 
                    pushFollow(FOLLOW_formula_in_atomic1114);
                    fo=formula(null);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic1117); 

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
                    // MMLDlight.g:304:7: 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')'
                    {
                    match(input,33,FOLLOW_33_in_atomic1133); 
                    connt=(Token)match(input,ID,FOLLOW_ID_in_atomic1137); 
                    match(input,34,FOLLOW_34_in_atomic1139); 
                    first=(Token)match(input,ID,FOLLOW_ID_in_atomic1143); 
                    match(input,23,FOLLOW_23_in_atomic1145); 
                    mid=(Token)match(input,ID,FOLLOW_ID_in_atomic1149); 
                    match(input,23,FOLLOW_23_in_atomic1151); 
                    last=(Token)match(input,ID,FOLLOW_ID_in_atomic1155); 
                    match(input,35,FOLLOW_35_in_atomic1157); 
                    match(input,34,FOLLOW_34_in_atomic1159); 
                    pushFollow(FOLLOW_formula_in_atomic1163);
                    fo=formula(null);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic1166); 

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
                    // MMLDlight.g:320:7: '(' f= formula[tempComp] ')'
                    {
                    match(input,34,FOLLOW_34_in_atomic1182); 
                    pushFollow(FOLLOW_formula_in_atomic1186);
                    f=formula(tempComp);

                    state._fsp--;

                    match(input,35,FOLLOW_35_in_atomic1189); 
                     form = f; 

                    }
                    break;
                case 7 :
                    // MMLDlight.g:322:7: 'NOT' at= atomic[tempComp]
                    {
                    match(input,36,FOLLOW_36_in_atomic1205); 
                    pushFollow(FOLLOW_atomic_in_atomic1209);
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
    // MMLDlight.g:326:1: c_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form] : ( 'AND' f2= conjunction[tempComp] | );
    public final YAMLDFormula c_prime(YAMLDComponent tempComp, YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // MMLDlight.g:326:77: ( 'AND' f2= conjunction[tempComp] | )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==37) ) {
                alt16=1;
            }
            else if ( ((LA16_0>=17 && LA16_0<=18)||LA16_0==29||LA16_0==35||LA16_0==38) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // MMLDlight.g:327:7: 'AND' f2= conjunction[tempComp]
                    {
                    match(input,37,FOLLOW_37_in_c_prime1241); 
                    pushFollow(FOLLOW_conjunction_in_c_prime1245);
                    f2=conjunction(tempComp);

                    state._fsp--;

                     form = (YAMLDAndFormula)(new YAMLDAndFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:330:7: 
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
    // MMLDlight.g:333:1: f_prime[YAMLDComponent tempComp,YAMLDFormula f1] returns [YAMLDFormula form] : ( 'OR' f2= formula[tempComp] | );
    public final YAMLDFormula f_prime(YAMLDComponent tempComp, YAMLDFormula f1) throws RecognitionException {
        YAMLDFormula form = null;

        YAMLDFormula f2 = null;


        try {
            // MMLDlight.g:333:77: ( 'OR' f2= formula[tempComp] | )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==38) ) {
                alt17=1;
            }
            else if ( ((LA17_0>=17 && LA17_0<=18)||LA17_0==29||LA17_0==35) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // MMLDlight.g:334:7: 'OR' f2= formula[tempComp]
                    {
                    match(input,38,FOLLOW_38_in_f_prime1292); 
                    pushFollow(FOLLOW_formula_in_f_prime1296);
                    f2=formula(tempComp);

                    state._fsp--;

                     form = (YAMLDOrFormula)(new YAMLDOrFormula(f1, f2)); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:337:7: 
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
    // MMLDlight.g:340:1: expression[YAMLDComponent tempComp] returns [YAMLDExpr expr] : s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr] ;
    public final YAMLDExpr expression(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr s1 = null;

        YAMLDExpr e1 = null;


        try {
            // MMLDlight.g:340:61: (s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr] )
            // MMLDlight.g:341:7: s1= simple_expression[tempComp] e1= exp_prime[tempComp,$s1.expr]
            {
            pushFollow(FOLLOW_simple_expression_in_expression1349);
            s1=simple_expression(tempComp);

            state._fsp--;

            pushFollow(FOLLOW_exp_prime_in_expression1354);
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
    // MMLDlight.g:345:1: exp_prime[YAMLDComponent tempComp,YAMLDExpr e] returns [YAMLDExpr expr] : ( '+' e2= expression[tempComp] | );
    public final YAMLDExpr exp_prime(YAMLDComponent tempComp, YAMLDExpr e) throws RecognitionException {
        YAMLDExpr expr = null;

        YAMLDExpr e2 = null;


        try {
            // MMLDlight.g:345:72: ( '+' e2= expression[tempComp] | )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==39) ) {
                alt18=1;
            }
            else if ( (LA18_0==EOF||LA18_0==EQUALS||(LA18_0>=17 && LA18_0<=18)) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // MMLDlight.g:346:7: '+' e2= expression[tempComp]
                    {
                    match(input,39,FOLLOW_39_in_exp_prime1394); 
                    pushFollow(FOLLOW_expression_in_exp_prime1398);
                    e2=expression(tempComp);

                    state._fsp--;

                     expr = new YAMLDAddExpr(e, e2); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:349:7: 
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
    // MMLDlight.g:352:1: simple_expression[YAMLDComponent tempComp] returns [YAMLDExpr expr] : (num= INT | 'true' | 'false' | comp_name= ID '.' var_name= ID | var_name= ID );
    public final YAMLDExpr simple_expression(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDExpr expr = null;

        Token num=null;
        Token comp_name=null;
        Token var_name=null;

        try {
            // MMLDlight.g:352:68: (num= INT | 'true' | 'false' | comp_name= ID '.' var_name= ID | var_name= ID )
            int alt19=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt19=1;
                }
                break;
            case 40:
                {
                alt19=2;
                }
                break;
            case 41:
                {
                alt19=3;
                }
                break;
            case ID:
                {
                int LA19_4 = input.LA(2);

                if ( (LA19_4==42) ) {
                    alt19=4;
                }
                else if ( (LA19_4==EOF||LA19_4==EQUALS||(LA19_4>=17 && LA19_4<=18)||LA19_4==39) ) {
                    alt19=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // MMLDlight.g:353:7: num= INT
                    {
                    num=(Token)match(input,INT,FOLLOW_INT_in_simple_expression1451); 
                     expr = YAMLDValue.getValue(Integer.parseInt(num.getText())); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:355:7: 'true'
                    {
                    match(input,40,FOLLOW_40_in_simple_expression1468); 
                     expr = YAMLDValue.getValue(1); 

                    }
                    break;
                case 3 :
                    // MMLDlight.g:357:7: 'false'
                    {
                    match(input,41,FOLLOW_41_in_simple_expression1484); 
                     expr = YAMLDValue.getValue(0); 

                    }
                    break;
                case 4 :
                    // MMLDlight.g:359:7: comp_name= ID '.' var_name= ID
                    {
                    comp_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1502); 
                    match(input,42,FOLLOW_42_in_simple_expression1504); 
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1508); 

                            YAMLDID newID = new YAMLDID(var_name.getText());
                            newID.setOwner(comp_name.getText());
                            expr = (YAMLDExpr)(newID); 
                          

                    }
                    break;
                case 5 :
                    // MMLDlight.g:365:7: var_name= ID
                    {
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_simple_expression1526); 
                     
                            final String name = var_name.getText();
                            if (YAMLDValue.existsValue(name)) {
                              expr = YAMLDValue.getValue(name);
                            } else if (tempComp != null) {
                              YAMLDGenericVar var = tempComp.getVariable(name);          
                              if (var == null) {
                                throw new org.antlr.runtime.FailedPredicateException(
                                  input, "No variable " + name + " for component " + tempComp.name(), name
                                );
                              }

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


    // $ANTLR start "value_expression"
    // MMLDlight.g:386:1: value_expression[YAMLDComponent tempComp] returns [YAMLDExpr expr] : (num= INT | 'true' | 'false' | var_name= ID );
    public final YAMLDExpr value_expression(YAMLDComponent tempComp) throws RecognitionException {
        YAMLDExpr expr = null;

        Token num=null;
        Token var_name=null;

        try {
            // MMLDlight.g:386:67: (num= INT | 'true' | 'false' | var_name= ID )
            int alt20=4;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt20=1;
                }
                break;
            case 40:
                {
                alt20=2;
                }
                break;
            case 41:
                {
                alt20=3;
                }
                break;
            case ID:
                {
                alt20=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // MMLDlight.g:387:7: num= INT
                    {
                    num=(Token)match(input,INT,FOLLOW_INT_in_value_expression1560); 
                     expr = YAMLDValue.getValue(Integer.parseInt(num.getText())); 

                    }
                    break;
                case 2 :
                    // MMLDlight.g:389:7: 'true'
                    {
                    match(input,40,FOLLOW_40_in_value_expression1577); 
                     expr = YAMLDValue.getValue(1); 

                    }
                    break;
                case 3 :
                    // MMLDlight.g:391:7: 'false'
                    {
                    match(input,41,FOLLOW_41_in_value_expression1593); 
                     expr = YAMLDValue.getValue(0); 

                    }
                    break;
                case 4 :
                    // MMLDlight.g:393:7: var_name= ID
                    {
                    var_name=(Token)match(input,ID,FOLLOW_ID_in_value_expression1611); 

                            final String name = var_name.getText();
                            expr = YAMLDValue.getValue(name);
                          

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
    // $ANTLR end "value_expression"


    // $ANTLR start "assignment_list"
    // MMLDlight.g:400:1: assignment_list[YAMLDComponent comp, ArrayList<YAMLDAssignment> al] : assignment[comp,al] ( ',' assignment[comp,al] )* ;
    public final void assignment_list(YAMLDComponent comp, ArrayList<YAMLDAssignment> al) throws RecognitionException {
        try {
            // MMLDlight.g:400:68: ( assignment[comp,al] ( ',' assignment[comp,al] )* )
            // MMLDlight.g:401:7: assignment[comp,al] ( ',' assignment[comp,al] )*
            {
            pushFollow(FOLLOW_assignment_in_assignment_list1638);
            assignment(comp, al);

            state._fsp--;

            // MMLDlight.g:401:27: ( ',' assignment[comp,al] )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==17) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // MMLDlight.g:401:28: ',' assignment[comp,al]
            	    {
            	    match(input,17,FOLLOW_17_in_assignment_list1642); 
            	    pushFollow(FOLLOW_assignment_in_assignment_list1644);
            	    assignment(comp, al);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
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
    // MMLDlight.g:404:1: assignment[YAMLDComponent comp, ArrayList<YAMLDAssignment> al] : var_name= ID ASSGN e1= expression[comp] ;
    public final void assignment(YAMLDComponent comp, ArrayList<YAMLDAssignment> al) throws RecognitionException {
        Token var_name=null;
        YAMLDExpr e1 = null;


        try {
            // MMLDlight.g:404:63: (var_name= ID ASSGN e1= expression[comp] )
            // MMLDlight.g:405:7: var_name= ID ASSGN e1= expression[comp]
            {
            var_name=(Token)match(input,ID,FOLLOW_ID_in_assignment1668); 
            match(input,ASSGN,FOLLOW_ASSGN_in_assignment1670); 
            pushFollow(FOLLOW_expression_in_assignment1674);
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
    // MMLDlight.g:412:1: synchro : 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';' ;
    public final void synchro() throws RecognitionException {
        Token c1=null;
        Token e1=null;
        Token c2=null;
        Token e2=null;

        try {
            // MMLDlight.g:412:8: ( 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';' )
            // MMLDlight.g:413:7: 'synchronize' c1= ID '.' e1= ID ( ',' c2= ID '.' e2= ID )+ ';'
            {
            match(input,43,FOLLOW_43_in_synchro1702); 
            c1=(Token)match(input,ID,FOLLOW_ID_in_synchro1706); 
            match(input,42,FOLLOW_42_in_synchro1708); 
            e1=(Token)match(input,ID,FOLLOW_ID_in_synchro1711); 

                    final YAMLDComponent comp1 = net.getComponent(c1.getText());
                    final YAMLDEvent ev1 = comp1.getEvent(e1.getText());
                    final Collection<YAMLDEvent> events = new HashSet<YAMLDEvent>();
                  
            // MMLDlight.g:419:7: ( ',' c2= ID '.' e2= ID )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==17) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // MMLDlight.g:419:8: ',' c2= ID '.' e2= ID
            	    {
            	    match(input,17,FOLLOW_17_in_synchro1728); 
            	    c2=(Token)match(input,ID,FOLLOW_ID_in_synchro1732); 
            	    match(input,42,FOLLOW_42_in_synchro1734); 
            	    e2=(Token)match(input,ID,FOLLOW_ID_in_synchro1738); 

            	            final YAMLDComponent comp2 = net.getComponent(c2.getText());
            	            final YAMLDEvent ev2 = comp2.getEvent(e2.getText());
            	            events.add(ev2);
            	          

            	    }
            	    break;

            	default :
            	    if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
            } while (true);

            match(input,18,FOLLOW_18_in_synchro1912); 

                    final MMLDSynchro s = new MMLDSynchro(ev1,events);
                    net.addSynchro(s);
                    ev1.addSynchro(s);
                    for (final YAMLDEvent e: events) {
                      e.addSynchro(s);
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
    // $ANTLR end "synchro"


    // $ANTLR start "observable"
    // MMLDlight.g:436:1: observable : 'observable' cname= ID '.' ename= ID ';' ;
    public final void observable() throws RecognitionException {
        Token cname=null;
        Token ename=null;

        try {
            // MMLDlight.g:436:11: ( 'observable' cname= ID '.' ename= ID ';' )
            // MMLDlight.g:437:6: 'observable' cname= ID '.' ename= ID ';'
            {
            match(input,44,FOLLOW_44_in_observable1933); 
            cname=(Token)match(input,ID,FOLLOW_ID_in_observable1937); 
            match(input,42,FOLLOW_42_in_observable1939); 
            ename=(Token)match(input,ID,FOLLOW_ID_in_observable1943); 
            match(input,18,FOLLOW_18_in_observable1945); 

                   final YAMLDComponent comp = net.getComponent(cname.getText());
                   if (comp == null) {
                     throw new org.antlr.runtime.FailedPredicateException(
                        input, "Unknown component", cname.getText()
                    );
                   }
                   final YAMLDEvent e = comp.getEvent(ename.getText());
                   // System.out.println("read observable " + e.toFormattedString());
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
    // MMLDlight.g:451:1: state[Map<YAMLDVar,YAMLDValue> map] : (cname= ID '.' vname= ID ':=' 'true' ';' | cname= ID '.' vname= ID ':=' 'false' ';' | cname= ID '.' vname= ID ':=' valname= ID ';' | cname= ID '.' vname= ID ':=' valname= INT ';' );
    public final void state(Map<YAMLDVar,YAMLDValue> map) throws RecognitionException {
        Token cname=null;
        Token vname=null;
        Token valname=null;

        try {
            // MMLDlight.g:451:36: (cname= ID '.' vname= ID ':=' 'true' ';' | cname= ID '.' vname= ID ':=' 'false' ';' | cname= ID '.' vname= ID ':=' valname= ID ';' | cname= ID '.' vname= ID ':=' valname= INT ';' )
            int alt23=4;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ID) ) {
                int LA23_1 = input.LA(2);

                if ( (LA23_1==42) ) {
                    int LA23_2 = input.LA(3);

                    if ( (LA23_2==ID) ) {
                        int LA23_3 = input.LA(4);

                        if ( (LA23_3==ASSGN) ) {
                            switch ( input.LA(5) ) {
                            case 40:
                                {
                                alt23=1;
                                }
                                break;
                            case 41:
                                {
                                alt23=2;
                                }
                                break;
                            case ID:
                                {
                                alt23=3;
                                }
                                break;
                            case INT:
                                {
                                alt23=4;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 23, 4, input);

                                throw nvae;
                            }

                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 23, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // MMLDlight.g:452:7: cname= ID '.' vname= ID ':=' 'true' ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state1973); 
                    match(input,42,FOLLOW_42_in_state1975); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state1979); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state1981); 
                    match(input,40,FOLLOW_40_in_state1983); 
                    match(input,18,FOLLOW_18_in_state1985); 

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
                    // MMLDlight.g:463:7: cname= ID '.' vname= ID ':=' 'false' ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state2003); 
                    match(input,42,FOLLOW_42_in_state2005); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state2009); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state2011); 
                    match(input,41,FOLLOW_41_in_state2013); 
                    match(input,18,FOLLOW_18_in_state2015); 

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
                    // MMLDlight.g:474:7: cname= ID '.' vname= ID ':=' valname= ID ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state2033); 
                    match(input,42,FOLLOW_42_in_state2035); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state2039); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state2041); 
                    valname=(Token)match(input,ID,FOLLOW_ID_in_state2045); 
                    match(input,18,FOLLOW_18_in_state2047); 

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
                    // MMLDlight.g:485:7: cname= ID '.' vname= ID ':=' valname= INT ';'
                    {
                    cname=(Token)match(input,ID,FOLLOW_ID_in_state2065); 
                    match(input,42,FOLLOW_42_in_state2067); 
                    vname=(Token)match(input,ID,FOLLOW_ID_in_state2071); 
                    match(input,ASSGN,FOLLOW_ASSGN_in_state2073); 
                    valname=(Token)match(input,INT,FOLLOW_INT_in_state2077); 
                    match(input,18,FOLLOW_18_in_state2079); 

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


    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA15_eotS =
        "\16\uffff";
    static final String DFA15_eofS =
        "\16\uffff";
    static final String DFA15_minS =
        "\1\4\3\uffff\1\4\2\uffff\1\42\1\4\1\27\1\4\1\27\2\uffff";
    static final String DFA15_maxS =
        "\1\51\3\uffff\1\4\2\uffff\1\42\1\4\1\27\1\4\1\43\2\uffff";
    static final String DFA15_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\6\1\7\5\uffff\1\4\1\5";
    static final String DFA15_specialS =
        "\16\uffff}>";
    static final String[] DFA15_transitionS = {
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

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "284:1: atomic[YAMLDComponent tempComp] returns [YAMLDFormula form] : (e1= expression[tempComp] EQUALS e2= value_expression[tempComp] | 'FALSE' | 'TRUE' | 'exists' connt= ID '(' first= ID '..' last= ID ')' '(' fo= formula[null] ')' | 'exists' connt= ID '(' first= ID '..' mid= ID '..' last= ID ')' '(' fo= formula[null] ')' | '(' f= formula[tempComp] ')' | 'NOT' at= atomic[tempComp] );";
        }
    }
 

    public static final BitSet FOLLOW_comp_in_net55 = new BitSet(new long[]{0x0000180000002012L});
    public static final BitSet FOLLOW_synchro_in_net75 = new BitSet(new long[]{0x0000180000000012L});
    public static final BitSet FOLLOW_observable_in_net79 = new BitSet(new long[]{0x0000180000000012L});
    public static final BitSet FOLLOW_state_in_net83 = new BitSet(new long[]{0x0000180000000012L});
    public static final BitSet FOLLOW_13_in_comp126 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_comp130 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_comp132 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_comp148 = new BitSet(new long[]{0x000000004E198000L});
    public static final BitSet FOLLOW_comp_def_in_comp151 = new BitSet(new long[]{0x000000004E198000L});
    public static final BitSet FOLLOW_15_in_comp156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_coords_def_in_comp_def191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_var_def_in_comp_def201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_def_in_comp_def211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_connection_def_in_comp_def221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_trans_def_in_comp_def231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constraint_def_in_comp_def241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_coords_def267 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_coords_def271 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_coords_def273 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_coords_def277 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_coords_def279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_var_def319 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_20_in_var_def338 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_var_def354 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_var_def356 = new BitSet(new long[]{0x0000000000404000L});
    public static final BitSet FOLLOW_22_in_var_def392 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_var_def396 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_var_def398 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_var_def402 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_var_def404 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14_in_var_def461 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_var_dom_in_var_def463 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_var_def466 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_var_def499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_event_def514 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_event_def518 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_event_def520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_connection_def540 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_connection_def544 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_connection_def546 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_connection_def551 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_connection_def553 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_connection_def557 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_connection_def559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_trans_def582 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_trans_def586 = new BitSet(new long[]{0x0000000010000012L});
    public static final BitSet FOLLOW_rule_def_in_trans_def588 = new BitSet(new long[]{0x0000000010000012L});
    public static final BitSet FOLLOW_28_in_trans_def602 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_triggering_cond_in_trans_def604 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_17_in_trans_def612 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_triggering_cond_in_trans_def614 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_18_in_trans_def623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_rule_def648 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_rule_def652 = new BitSet(new long[]{0x0000000020040000L});
    public static final BitSet FOLLOW_29_in_rule_def671 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_or_event_in_rule_def673 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_17_in_rule_def688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_or_event_in_rule_def690 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_18_in_rule_def705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_assignment_or_event724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_assignment_or_event738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_triggering_cond769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_triggering_cond804 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_triggering_cond808 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_triggering_cond810 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_triggering_cond814 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_triggering_cond816 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_triggering_cond820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_constraint_def850 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_constraint_def854 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_constraint_def857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_var_dom882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_var_dom901 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_var_dom903 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_var_dom_in_var_dom905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conjunction_in_formula947 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_f_prime_in_formula952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomic_in_conjunction987 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_c_prime_in_conjunction992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_atomic1026 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUALS_in_atomic1029 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_value_expression_in_atomic1033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_atomic1057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_atomic1074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_atomic1090 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1094 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic1096 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1100 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic1102 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1106 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic1108 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic1110 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic1114 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_atomic1133 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1137 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic1139 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1143 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic1145 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1149 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_atomic1151 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_atomic1155 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic1157 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_atomic1159 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic1163 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic1166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_atomic1182 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_atomic1186 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_atomic1189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_atomic1205 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_atomic_in_atomic1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_c_prime1241 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_conjunction_in_c_prime1245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_f_prime1292 = new BitSet(new long[]{0x0000031780000050L});
    public static final BitSet FOLLOW_formula_in_f_prime1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_expression_in_expression1349 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_exp_prime_in_expression1354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_exp_prime1394 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_expression_in_exp_prime1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_simple_expression1451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_simple_expression1468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_simple_expression1484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression1502 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_simple_expression1504 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_simple_expression1508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_expression1526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_value_expression1560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_value_expression1577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_value_expression1593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_value_expression1611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_assignment_list1638 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_17_in_assignment_list1642 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assignment_in_assignment_list1644 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_ID_in_assignment1668 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_assignment1670 = new BitSet(new long[]{0x0000030000000050L});
    public static final BitSet FOLLOW_expression_in_assignment1674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_synchro1702 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1706 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synchro1708 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1711 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_synchro1728 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1732 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synchro1734 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_synchro1738 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_18_in_synchro1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_observable1933 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_observable1937 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_observable1939 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_observable1943 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_observable1945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state1973 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state1975 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state1979 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state1981 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_state1983 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_state1985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state2003 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state2005 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state2009 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state2011 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_state2013 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_state2015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state2033 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state2035 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state2039 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state2041 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state2045 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_state2047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state2065 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_state2067 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_state2071 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSGN_in_state2073 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_state2077 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_state2079 = new BitSet(new long[]{0x0000000000000002L});

}