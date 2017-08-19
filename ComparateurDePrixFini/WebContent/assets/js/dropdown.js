/**
 * 
 */
		$('div.dropdown.dropdown-cart.mega-dropdown-menu a').on('click', function (event) {
		    $(this).parent().toggleClass('open');
		});

		$('body').on('click', function (e) {
		    if (!$('div.dropdown.dropdown-cart.mega-dropdown-menu').is(e.target) 
		        && $('div.dropdown.dropdown-cart.mega-dropdown-menu').has(e.target).length === 0 
		        && $('.open').has(e.target).length === 0
		    ) {
		        $('div.dropdown.dropdown-cart.mega-dropdown-menu').removeClass('open');
		    }
		});

		