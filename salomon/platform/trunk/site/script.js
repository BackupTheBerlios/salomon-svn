  function load() {
      
//     menuK = document.getElementById("menuKursy") ;
//	menuK.style.top = "112px" ;
// 	menuK.style.left = "177px" ;
//	menuK.style.width = "56px" ;
//	menuK.style.height = "112px" ;

      menuW = document.getElementById("menuWyjazdy") ;
	menuW.style.top = "112px" ;
	menuW.style.left = "232px" ;
	menuW.style.width = "133px" ;
	menuW.style.height = "46px" ;


      menuS = document.getElementById("menuSklep") ;
	menuS.style.top = "112px" ;
	menuS.style.left = "121px" ;
  	menuS.style.width = "57px" ;
	menuS.style.height = "94px" ;

      btn1  = document.getElementById("BtnSklep") ;
      btn2  = document.getElementById("BtnKursy") ;
      btn3  = document.getElementById("BtnWyjazdy") ;
      btn4  = document.getElementById("BtnWyporzyczalnia") ;
      btn5  = document.getElementById("BtnKontakt") ;

      // menu Sklep
      msg   = document.getElementById("menuSklepG") ;
      msk   = document.getElementById("menuSklepK") ;
      msb   = document.getElementById("menuSklepB") ;
      mso   = document.getElementById("menuSklepO") ;
      //menu Wyjazdy 
      mwg   = document.getElementById("menuWyjazdyG") ;
      mwc   = document.getElementById("menuWyjazdyC") ;  
      
      if (navigator.userAgent.indexOf("Mozilla/5.0")>=0)
      {
	      ///// batony
	      btn1.addEventListener("mouseover",menuListener,false) ;
	      
	      btn2.addEventListener("mouseover",inListener,false) ;
	      btn2.addEventListener("mouseout",outListener,false) ;
	       
	      btn3.addEventListener("mouseover",menuListener,false) ;
	      
	      btn4.addEventListener("mouseover",inListener,false) ;
	      btn5.addEventListener("mouseover",inListener,false) ; 
	      btn4.addEventListener("mouseout",outListener,false) ; 
	      btn5.addEventListener("mouseout",outListener,false) ; 

	    // menu sklap
	      msg.addEventListener("mouseout",menuBtnOutListener,false) ;
	      msk.addEventListener("mouseover",menuBtnListener,false) ;
	      msk.addEventListener("mouseout",menuBtnOutListener,false) ;
	      msb.addEventListener("mouseover",menuBtnListener,false) ;
	      msb.addEventListener("mouseout",menuBtnOutListener,false) ;
	      mso.addEventListener("mouseover",menuBtnListener,false) ;
	      mso.addEventListener("mouseout",menuBtnOutListener,false) ;
	    // menu Wyjazdy 
	      mwg.addEventListener("mouseout",menuBtnOutListener,false) ;
	      mwc.addEventListener("mouseover",menuBtnListener,false) ;
	      mwc.addEventListener("mouseout",menuBtnOutListener,false) ;	      
      }
	else 
      {
	      btn1.onmouseover = menuListener ;
	     
	      btn2.onmouseover = inListener ;
	      btn2.onmouseout = outListener ;
	       
	      btn3.onmouseover = menuListener ;
	      
	      btn4.onmouseover = inListener ;
	      btn5.onmouseover = inListener ;
	      
	      btn4.onmouseout = outListener ; 
	      btn5.onmouseout = outListener ; 

	    // menu sklap
	      msg.onmouseout  = menuBtnOutListener ;
	      msk.onmouseover = menuBtnListener    ;
	      msk.onmouseout  = menuBtnOutListener ;
	      msb.onmouseover = menuBtnListener    ;
	      msb.onmouseout  = menuBtnOutListener ;
	      mso.onmouseover = menuBtnListener    ;
	      mso.onmouseout  = menuBtnOutListener ;
	    // menu Wyjazdy 
	      mwg.onmouseout  = menuBtnOutListener ;
	      mwc.onmouseover = menuBtnListener    ;
	      mwc.onmouseout  = menuBtnOutListener ;	 

      } 

    }
    function menuListener() {
	    menuHnd = "menu"+this.id.substring(3,this.id.length) ; 
	    
	    menu = document.getElementById(menuHnd) ;
	    menu.style.visibility = "visible" ;
    }

    function menuBtnListener() {
	    this.src = "images/menu/"+this.id+"_h.gif" ; 
	    
    }

    function menuBtnOutListener(e) {
	this.src = "images/menu/"+this.id+".gif" ;

	//ruznice 
	if (navigator.userAgent.indexOf("Mozilla/5.0")>=0) { 
	offset = (document.width/2) - 350 ;
	eHnd = e  ;
	}
	else
	{
	 //alert(" " + window.outerWidth + " ") ; 
	
	offset = (document.body.clientWidth/2) - 350 ;
	eHnd = event ;
	}
	
	menu = this.id.substring(0,this.id.length-1);

	g = document.getElementById(menu) ;
	

	if ( eHnd.clientY + document.body.scrollTop <= parseInt(g.style.top)) 
		{g.style.visibility = "hidden" ;}
	if ( eHnd.clientX + document.body.scrollLeft <= offset +(parseInt(g.style.left))) 
		{g.style.visibility = "hidden" ; }  
	if ( eHnd.clientX + document.body.scrollLeft >= offset + (parseInt(g.style.left)+parseInt(g.style.width))) 
		{g.style.visibility = "hidden" ; }  
	if ( eHnd.clientY + document.body.scrollTop >= (parseInt(g.style.top)+parseInt(g.style.height))) 
		{g.style.visibility = "hidden"; }
		
	 	
	//alert(g.id +" " + parseInt( g.style.top)) ; 
	
      
    }
    
    function hidListener() {
      this.style.visibility = "hidden" ;
    }

    function inListener() {
	  this.src = "images/"+this.id+"_h.gif" ;
    }

    function outListener() {
	  this.src = "images/"+this.id+".gif" ;
    }

