// JavaScript Document
// JavaScript Document



$(document).ready(function(){
						   
	$('.common').find('ul').hide();					   
	$('.common').hover(
      function(){
		  $(this).find('ul').addClass('li01');
		  $(this).find('ul').show();
		  },
       function(){
		    $(this).find('ul').addClass('li01');
		   $(this).find('ul').hide();
		   }
												 
    );
	$('.current').find('ul').hide();					   
	$('.current').hover(
      function(){
		  $(this).find('ul').addClass('li01');
		  $(this).find('ul').show();
		  },
       function(){
		    $(this).find('ul').addClass('li01');
		   $(this).find('ul').hide();
		   }
												 
    );
});