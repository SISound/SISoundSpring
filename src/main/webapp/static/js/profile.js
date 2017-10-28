/*notes

* mobile/tablet tweener size - new media query somewhere? Seems like we might benefit a lot in the banner. we could side-by-side the pic and name/title/id with the welcome message below the both of them @ 100%.

*/

//Hides all tab content and shows first tab by default.
$('.tab-content').hide();
$('.tab-content:first-of-type').show();

//Ensures first tab is the default tab
$('.tab').removeClass('tab-active');
$('.tab:first-of-type').addClass('tab-active');

$('.tab').click( function(e){
	e.preventDefault();
	var $this = $(this);
	var target = $this.attr('href');
	
	//Removes any active tab states and sets first tab as default
	$('.tab-group .tab-active').removeClass('tab-active');
	$this.addClass('tab-active');
	
	
	//Hides displayed tab content, shows clicked tab content
	$('.tab-items .tab-content').css('display', 'none');
	$(target).css('display', 'flex');
});