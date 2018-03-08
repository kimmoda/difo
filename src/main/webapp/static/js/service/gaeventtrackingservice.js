var zionGAEventService = function(category, action, label) {
	var config = {
		category : category || '',
		action : action || '',
		label : label || ''
	},
	_track = function() {
		var _dataLayer = window.dataLayer || [];
		_dataLayer.push({
			'event' : 'ga-event',
			'ga-category' : config.category.toLowerCase(),
			'ga-action' : config.action.toLowerCase(),
			'ga-label' : config.label.toLowerCase()
		});
	};
	
	return {
		track : _track
	}
};