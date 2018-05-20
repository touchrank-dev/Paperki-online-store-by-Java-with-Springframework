(function() {
    var menuEl = document.getElementById('ml-menu'),
        mlmenu = new MLMenu(menuEl, {
            // breadcrumbsCtrl : true, // show breadcrumbs
            initialBreadcrumb : 'Все', // initial breadcrumb text
            backCtrl : false, // show back button
            itemsDelayInterval : 20,
        });

    // mobile menu toggle
    var openMenuCtrl = document.querySelector('.action--open'),
        closeMenuCtrl = document.querySelector('.action--close'),
        catalogOverlay = document.querySelector('.catalog_overlay');

    openMenuCtrl.addEventListener('click', openMenu);
    closeMenuCtrl.addEventListener('click', closeMenu);
    catalogOverlay.addEventListener('click', closeMenu);

    function openMenu() {
        classie.add(menuEl, 'menu--open');
        closeMenuCtrl.focus();
    }

    function closeMenu() {
        classie.remove(menuEl, 'menu--open');
        openMenuCtrl.focus();        
    }    
    document.onkeydown = function(evt) {
        evt = evt || window.event;
        if (evt.keyCode == 27) {
            closeMenu();
        }
    };     
    
})();