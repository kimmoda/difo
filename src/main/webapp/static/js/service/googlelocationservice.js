var zionGoogleLocationService = (function () {
    var autocomplete,
        placeService,
        placeChangeCallback,
        init = function () {
            placeService = new google.maps.places.PlacesService(jQuery('.zion-google-location-input').get(0));
            // Create the autocomplete object, need add class 'zion-google-location-input' to input box
            autocomplete = new google.maps.places.Autocomplete(
                (jQuery('.zion-google-location-input').get(0)),
                {types: ['geocode']});

            // When the user selects an address from the dropdown, get address object
            autocomplete.addListener('place_changed', setAddress);

        },
        placeChangeListener = function (callback) {
            placeChangeCallback = callback;
        },
        setAddress = function () {
            jQuery('.zion-google-location-input').data('location', autocomplete.getPlace());
            if (placeChangeCallback && jQuery.isFunction(placeChangeCallback)) {
                placeChangeCallback();
            }
        },

        resetAddress = function () {
            jQuery('.zion-google-location-input').data('location', '');
        },

        // Get the place details from the autocomplete object.
        getAddress = function () {
            var place = jQuery('.zion-google-location-input').data('location');
            if (place && place.address_components) {
                var address = {};
                jQuery.each(place.address_components, function (index, value) {
                    if (value.types) {
                        var type = value.types[0];
                        switch (type) {
                            case 'country': {
                                address.country = value.long_name;
                                address.countryCode = value.short_name;
                                break;
                            }
                            case 'locality': {
                                address.locality = value.long_name;
                                break;
                            }
                            case 'route': {
                                address.route = value.long_name;
                                break;
                            }
                            case 'postal_code': {
                                address.postal_code = value.long_name;
                                break;
                            }
                            case 'sublocality_level_1': {
                                address.sublocality = value.long_name;
                                break;
                            }
                            case 'administrative_area_level_1': {
                                address.administrative_area1 = value.long_name;
                                break;
                            }
                            case 'administrative_area_level_2': {
                                address.administrative_area2 = value.long_name;
                                break;
                            }
                            case 'postal_town':{
                                address.postal_town = value.long_name;
                                break;
                            }
                        }
                    }

                });
                address.city = address.locality;
                return address;
            }
        },
        getFormattedAddress = function () {
            var place = jQuery('.zion-google-location-input').data('location');
            if (place) {
                return place.formatted_address;
            }
        },
        getPlaceId = function () {
            var place = jQuery('.zion-google-location-input').data('location');
            if (place) {
                return place.place_id;
            }
        },
        getAddressById = function (placeId, callback) {
            if (!placeId) {
                return;
            }
            var request = {
                placeId: placeId
            };
            placeService.getDetails(request, function (place, status) {
                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    callback(place);
                }
            });
        };

    return {
        init: init,
        getAddress: getAddress,
        getPlaceId: getPlaceId,
        getFormattedAddress: getFormattedAddress,
        resetAddress: resetAddress,
        getAddressById: getAddressById,
        changePlaceListener: placeChangeListener
    }
})();