$(document).ready(function() {
    $('.products-list-page__dropdown').easyDropDown({
        wrapperClass: 'products-list-page__dropdown',
        onChange: function (selected) {
            sendSortType();
        }
    });
    $('.review-sort__dropdown').easyDropDown({
        wrapperClass: 'review-sort__dropdown'
    }); 
    $('.write-response-prod-freq__dropdown').easyDropDown({
        wrapperClass: 'write-response-prod-freq__dropdown'
    }); 
    $('.write-response-prod-term__dropdown').easyDropDown({
        wrapperClass: 'write-response-prod-term__dropdown'
    });
    $('.orderingCall__dropdown').easyDropDown({
        wrapperClass: 'products-list-page__dropdown orderingCall__dropdown'
    });    
});