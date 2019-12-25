/*
 * Author:Jimco
 * Url:http://www.xiejiancong.com
 */
 
/* Toggle and Smiles */

$(function(){$(".title-list ul").children("li").each(function(a){$(this).mousemove(function(){$(".content-list").each(function(b){b==a?$(this).slideDown():$(this).slideUp()})})});$(".logo").click(function(){var a=$(this).css("left")=="20px"?($.browser.msie?document.compatMode=="CSS1Compat"?document.documentElement.clientWidth:document.body.clientWidth:self.innerWidth)-178:20;$(this).animate({left:a+"px"},1E3)});$("#smiles img").click(function(){var a=$(this).attr("id");$("#comment").insertAtCaret("|"+
a+"|")});$(document).keypress(function(a){(a.ctrlKey&&a.which==13||a.which==10)&&$("#comment_submit").click()})});function toggleInput(){$("#userinfo").toggle("normal");return!1};
(function(q){q.fn.extend({insertAtCaret:function(a){var c=
q(this)[0];if(document.selection)this.focus(),sel=document.selection.createRange(),sel.text=a,this.focus();else if(c.selectionStart||c.selectionStart=="0"){var h=c.selectionStart,g=c.selectionEnd,i=c.scrollTop;c.value=c.value.substring(0,h)+a+c.value.substring(g,c.value.length);this.focus();c.selectionStart=h+a.length;c.selectionEnd=h+a.length;c.scrollTop=i}else this.value+=a,this.focus()}})})(jQuery);

(function($){
  //slider
  $.fn.slider=function(options){
    var defaults={
      child:"child",
      slider_bar:"slider_bar",
      scrollTime:500,
      autoScroll:"true",
      autoTime:3000
    };
    var options=$.extend(defaults,options);
    var _this=$(this);
    var child=_this.find("."+options.child);
    var slider_bar=$("#"+options.slider_bar);
    var len=child.length-1;
    child.wrapAll("<div id=scroll_wrapper></div>");
    var width=child.width();
    var two_width=width*2;
    var thr_width=width*3;
    var wrap=$("#scroll_wrapper");
    wrap.css({width:thr_width+"px",left:-width+"px"});
    child.not(":first").hide();
    function noMove(){
      if(!wrap.is(":animated")){
        return true;
      }
      else{
        return false;
      };
    };
    child.each(function(index){
      if(index==0){
        slider_bar.append("<a href='javascript:void(0);' class='cur'></a>");
      }
      else{
        slider_bar.append("<a href='javascript:void(0);'></a>");
      };
    });
    var cur_a=slider_bar.find("a.cur");
    slider_bar.find("a").click(function(){
      var clickIndex=$(this).index();
      var nowIndex=slider_bar.find("a.cur").index();
      if(noMove()){
        if (clickIndex > nowIndex){
          scroll(clickIndex,two_width);
        }
        else if(clickIndex < nowIndex){
          scroll(clickIndex,"0");
        }
        else{
          return false;
        };
      };
      return false;
    });
    function scroll(num,scroll_width){
      slider_bar.find("a").eq(num).addClass("cur").siblings().removeClass("cur");
      child.eq(num).show().css({left:scroll_width+"px"});
      wrap.animate({left:-scroll_width+"px"},options.scrollTime,function(){
        child.eq(num).css({left:width+"px"}).siblings().hide();
        wrap.css({left:-width+"px"});
      });
    };
    $("#btn_prev").click(function(){
      var curIndex=slider_bar.find("a.cur").index();
      if(noMove()){
        if (curIndex == 0){
          scroll(len,"0");
        }
        else{
          slider_bar.find("a.cur").prev("a").trigger("click");
        };
      };
      return false;
    });
    $("#btn_next").click(function(){
      var curIndex=slider_bar.find("a.cur").index();
      if(noMove()){
        if (curIndex == len){
          scroll("0",two_width);
        }
        else{
          slider_bar.find("a.cur").next("a").trigger("click");
        };
      };
      return false;
    });
    if(options.autoScroll=="true"){
      autoScroll=setInterval(function(){
        $("#btn_next").trigger("click")
      },options.autoTime);
      autoPlay=function(){
        autoScroll=setInterval(function(){
        $("#btn_next").trigger("click")
        },options.autoTime);
      };
      stopPlay=function(){
        clearInterval(autoScroll);
      };
      _this.hover(stopPlay,autoPlay);
      $("#btn_prev,#btn_next").hover(stopPlay,autoPlay);
    };
  };
  //Tab
  $.fn.extend({
    "Tab":function(style,tabbox,className){
      var timer;
      if(style==1){
        this.click(function(){
          var me=$(this)
          var $tabbox=$(tabbox)
          me.addClass(className).siblings().removeClass(className);
          $(tabbox).eq(me.index()).show().siblings(tabbox).hide();
        })
      }
      if(style==2){
        this.mousemove(function(){
          var me=$(this)
          var $tabbox=$(tabbox)
          me.addClass(className).siblings().removeClass(className);
          $(tabbox).eq(me.index()).show().siblings(tabbox).hide();
        })
       }
    }
  });
  //下拉框
  $(".qside").hover(function(){
    $(".qside .side-out").show();
  },function(){
    $(".qside .side-out").hide();
  });
  //goToTop
  var scrolltotop={
    setting: {startline:350, scrollto: 0, scrollduration:500, fadeduration:[500, 100]},
    controlHTML: ' ', 
    controlattrs: {offsetx:10, offsety:10}, 
    anchorkeyword: '#top', 
    state: {isvisible:false, shouldvisible:false},
    scrollup:function(){
      if (!this.cssfixedsupport) 
        this.$control.css({opacity:0}) 
      var dest=isNaN(this.setting.scrollto)? this.setting.scrollto : parseInt(this.setting.scrollto)
      if (typeof dest=="string" && jQuery('#'+dest).length==1) 
        dest=jQuery('#'+dest).offset().top
      else
        dest=0
    this.$body.animate({scrollTop: dest}, this.setting.scrollduration);
    },
    keepfixed:function(){
      var $window=jQuery(window);
      var controlx=$window.scrollLeft() + $window.width() - this.$control.width() - this.controlattrs.offsetx;
      var controly=$window.scrollTop() + $window.height() - this.$control.height() - this.controlattrs.offsety;
      this.$control.css({left:controlx+'px', top:controly+'px'});
    },
    togglecontrol:function(){
      var scrolltop=jQuery(window).scrollTop()
      if (!this.cssfixedsupport)
        this.keepfixed()
      this.state.shouldvisible=(scrolltop>=this.setting.startline)? true : false
      if (this.state.shouldvisible && !this.state.isvisible){
        this.$control.stop().animate({opacity:1}, this.setting.fadeduration[0])
        this.state.isvisible=true
      }
      else if (this.state.shouldvisible==false && this.state.isvisible){
        this.$control.stop().animate({opacity:0}, this.setting.fadeduration[1])
        this.state.isvisible=false
      }
    },
    init:function(){
      jQuery(document).ready(function($){
        var mainobj=scrolltotop
        var iebrws=document.all
        mainobj.cssfixedsupport=!iebrws || iebrws && document.compatMode=="CSS1Compat" && window.XMLHttpRequest 
        mainobj.$body=(window.opera)? (document.compatMode=="CSS1Compat"? $('html') : $('body')) : $('html,body')
        mainobj.$control=$('<div id="gotop"><a>'+mainobj.controlHTML+'</a></div>')
          .css({position:mainobj.cssfixedsupport? 'fixed' : 'absolute', bottom:mainobj.controlattrs.offsety, right:mainobj.controlattrs.offsetx, opacity:0, cursor:'pointer'})
          .attr({title:'回到顶部'})
          .click(function(){mainobj.scrollup(); return false})
          .appendTo('body')
        if (document.all && !window.XMLHttpRequest && mainobj.$control.text()!='') 
          mainobj.$control.css({width:mainobj.$control.width()}) 
        mainobj.togglecontrol()
        $('a[href="' + mainobj.anchorkeyword +'"]').click(function(){
          mainobj.scrollup()
          return false
        })
        $(window).bind('scroll resize', function(e){
          mainobj.togglecontrol()
        })
      })
    }
  };
  scrolltotop.init()
})(jQuery);