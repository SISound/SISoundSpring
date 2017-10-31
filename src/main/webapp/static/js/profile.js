/*notes

* mobile/tablet tweener size - new media query somewhere? Seems like we might benefit a lot in the banner. we could side-by-side the pic and name/title/id with the welcome message below the both of them @ 100%.

*/

//Hides all tab content and shows first tab by default.
//$('.tab-content').hide();
//$('.tab-content:first-of-type').show();
//document.getElementById('tab-playlists').style.display = 'none';
//document.getElementById('tab-songs').style.display = 'block';
//
//
////Ensures first tab is the default tab
////$('.tab').removeClass('tab-active');
////$('.tab:first-of-type').addClass('tab-active');
//var y = document.getElementsByClassName('tab')
//y.remove('tab-active');
//y[0].addClass('tab-active');
//
//$('.tab').click( function(e){
//	e.preventDefault();
//	var $this = $(this);
//	var target = $this.attr('href');
//	
//	//Removes any active tab states and sets first tab as default
//	$('.tab-group .tab-active').removeClass('tab-active');
//	$this.addClass('tab-active');
//	
//	
//	//Hides displayed tab content, shows clicked tab content
//	$('.tab-items .tab-content').css('display', 'none');
//	$(target).css('display', 'flex');
//});

document.getElementById("defaultOpen").click();

function openTab(evt, tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}